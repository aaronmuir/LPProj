import java.text.DecimalFormat;
import java.util.ArrayList;

/**
 * Aaron Muir
 * CS 309
 * LP Project
 */
public class Solver
{
    private Matrix initial;
    private ArrayList<Matrix> tables;
    private ArrayList<Matrix> optimalMatrices;
    private Double optimalValue;

    private enum Method
    {
        simplex,
        twoPhaseSimplex,
        dualSimplex
    }
    private Method solveMethod;

    private Printer printer;

    /**
     * LP solving algorithms
     */
    public Solver(Matrix original, Printer printer)
    {
        tables = new ArrayList<Matrix>();
        optimalMatrices = new ArrayList<Matrix>();
        this.initial = original;
        this.printer = printer;
        this.solve();
    }

    /**
     * Print the results of the solve
     */
    public void PrintResults()
    {
        if(optimalMatrices.size()>0)
        {
            Double maxValue = 0.0;
            Matrix bestMatrix = new Matrix();
            DecimalFormat df = new DecimalFormat(" #0.00;-#");

            for (Matrix m : optimalMatrices)
            {
                Double objValue = m.getObjValue();

                if (objValue > maxValue)
                {
                    maxValue = objValue;
                    bestMatrix = m;
                }
            }
            printer.Print("BEST SOLUTION\r\n");
            printer.Print("-------------\r\n");
            printer.Print(bestMatrix.getSolution().toString());
            printer.Print("Max Value: " + df.format(maxValue)+"\r\n");
        }
        else
        {
            printer.Print("NO SOLUTIONS FOUND\r\n");
        }
    }

    /**
     * Solve the LP problem
     */
    private void solve()
    {
        try
        {
            printer.Print("Initial Matrix\r\n");
            printer.Print(initial.toString());

            this.determineSolveMethod();

            printer.Print("Using method "+ solveMethod.name()+"...\r\n");
            // copy the initial so it is preserved
            tables.add(initial.copy());

            if (solveMethod == Method.simplex)
            {
                simplex();
            }
            else if (solveMethod == Method.twoPhaseSimplex)
            {
                twoPhaseSimplex();
            }
            else
                throw new Exception("Unexpected solve method being utilized");

        }
        catch(Exception ex)
        {
            ExceptionHandler.Handle(ex);
        }
    }

    /**
     * simplex method of solving
     */
    private void simplex()
    {
        // get the current matrix
        Matrix current = tables.get(tables.size()-1);

        moveToAdjBfs(current);
    }

    /**
     * two-phase simplex method
     */
    private void twoPhaseSimplex()
    {
        // get the current matrix
        Matrix current = tables.get(tables.size()-1);

        // create an auxiliary row & column in the current matrix
        current.createAuxiliary();

        printer.Print("Auxiliary Created\r\n");
        printer.Print(current.toString());

        // phase 1 - solve auxiliary function

        // choose variable x0 to enter the basis
        current = selectX0Pivot(current);

        // we should have a bfs
        //assert current.getSolution().isBfs();

        // repeatedly improve aux obj W until W is zero
        moveToAdjBfs(current);

        // solve every matrix with optimal auxiliary
        for(Matrix solvedAux:optimalMatrices)
        {
            // remove auxiliary remnants
            solvedAux.removeAuxiliary();

            // if the solved aux is not infeasible and not unbounded
            if(!solvedAux.isInfeasible() && !solvedAux.isUnbounded())
            {
                // add to list of matrices
                tables.add(solvedAux);

                // phase 2 - solve simplex

                // continue to solve using simplex
                simplex();
            }
        }
    }

    /**
     * Improves the objective
     * @param matrix
     * @return returns when selected pivot produces optimal,infeasible, or unbounded matrix
     */
    private void moveToAdjBfs(Matrix matrix)
    {
        tables.add(matrix);
        try
        {
            // a. choose column i to enter the basis by finding largest Ai0
            ArrayList<Integer> cols = getLargestAi0(matrix);

            // if all Ai0 (not b) are <=0 (matrix is optimal)
            if (matrix.isOptimal())
            {
                printer.Print("Matrix is optimal\r\n");
                if (matrix.hasAuxiliary())
                {
                    if (matrix.getObjValue() != 0) //|| matrix.getSolution().isBasic()
                    {
                        matrix.flagInfeasible();
                        return;
                    }
                }
                optimalMatrices.add(matrix);
            }
            else
            {
                // b. choose the row j of the variable to leave
                // list of points to leave the basis
                ArrayList<Point> points = pointsToPivot(matrix, cols);

                // if list of aij is size 0, unbounded
                if (points.size() <= 0)
                {
                    matrix.flagUnbounded();
                    return;
                }

                // if this matrix has an aux and x0=1 in any of the points found,
                // we only pivot on that point
                ArrayList<Point> x0points = new ArrayList<Point>();
                for (Point p : points)
                {
                    if (matrix.hasAuxiliary())
                    {
                        if (matrix.getRow(p.getY()).getElement(0) == 1)
                        {
                            x0points.add(p);
                        }
                    }
                }
                if(x0points.size()>0)
                {
                    points.clear();
                    for(Point x : x0points)
                    {
                        points.add(x);
                    }
                }

                // c. for each aij in list
                // pivot on every aij and move to adj bfs on resulting matrices
                // prioritize x0 points

                printer.Print("Found the following points to pivot on: ");
                for (Point p : points)
                {
                    printer.Print(p.toString() + " ");
                }
                printer.Print("\r\n");

                for (Point p : points)
                {
                    Matrix m = pivot(matrix, p).copy();
                    moveToAdjBfs(m);
                }

            }
        }
        catch (Exception ex)
        {
            ExceptionHandler.Handle(ex);
        }
    }

    /**
     * Find a list of points in the matrix to pivot on
     * @param m - matrix
     * @param cols - pivot columns
     * @return
     */
    private ArrayList<Point> pointsToPivot(Matrix m, ArrayList<Integer> cols)
    {
        ArrayList<Point> validPoints = new ArrayList<Point>();
        // find points where aij > 0
        for(Integer i:cols)
        {
            // skip the first row
            int firstRow = 1;
            if(m.hasAuxiliary())
                firstRow=2;

            for(int j=firstRow;j<m.getRowSize();j++)
            {
                if(m.getValue(i,j)>0)
                    validPoints.add(new Point(i,j));
            }
        }

        // find minimum bj/aij for every i where aij > 0
        Double min=Double.MAX_VALUE;
        for(Point p:validPoints)
        {
            Double b = m.getValue(m.getColumnSize()-1,p.getY());
            Double Aij = m.getValue(p.getX(),p.getY());

            assert Aij != 0.0;

            if(b/Aij < min)
                min = b/Aij;
        }

        ArrayList<Point> pivotPoints = new ArrayList<Point>();
        // create list of points containing minimum bj/aij
        for(Point p:validPoints)
        {
            Double b = m.getValue(m.getColumnSize()-1,p.getY());
            Double Aij = m.getValue(p.getX(),p.getY());

            if(min.equals(b / Aij))
                pivotPoints.add(p);
        }
        return pivotPoints;
    }

    /**
     * Finds all columns with the largest possible value in row zero
     * @param m matrix
     * @return
     */
    ArrayList<Integer> getLargestAi0(Matrix m)
    {
        // find ALL i with largest Ai0
        AugRow rowZero = m.getRow(0);

        // find the largest value - omit b column
        Double largest=0.0;
        for(int i=0; i<rowZero.size()-1;i++)
        {
            if(rowZero.getElement(i)>largest)
                largest = rowZero.getElement(i);
        }

        ArrayList<Integer> cols = new ArrayList<Integer>();
        // build list of columns with the value
        for(int i=0;i<rowZero.size()-1;i++)
        {
            Double val = rowZero.getElement(i);
            if(val.equals(largest))
                cols.add(i);
        }
        return cols;
    }

    /**
     * find and pivot on row in column x0
     * @param matrix
     */
    private Matrix selectX0Pivot(Matrix matrix)
    {
        // find the row of the most negative basic var.
        // default to row 2 if no negatives found.
        int negRow=2;
        Double mostNegB = 0.0;

        for(int j=2;j<matrix.getRowSize();j++)
        {
            if(matrix.getRow(j).getB()<mostNegB)
            {
                mostNegB = matrix.getRow(j).getB();
                negRow=j;
            }
        }

        Point pivotPoint = new Point(0,negRow);

        // pivot on x0 in the row with the most negative basic
        Matrix m = pivot(matrix,pivotPoint).copy();
        return m;
    }

    /**
     * determine the solve method to be used
     */
    private void determineSolveMethod()
    {
        solveMethod = Method.simplex;

        // start after obj function row
        int rowSize = initial.getRowSize();

        for(int i=1;i< rowSize;i++)
        {
            AugRow row = initial.getRow(i);
            double b = row.getB();
            if(b < 0)
                solveMethod = Method.twoPhaseSimplex;
        }

    }

    /**
     * Pivot on an element in row j column i reducing xi to an identity vector.
     * xi enters the basis.
     *
     * @param matrix
     * @param p point to pivot on
     */
    private Matrix pivot(Matrix matrix, Point p)
    {
        printer.Print("Before pivot on "+p+"\r\n");
        printer.Print(matrix.toString(p));

        int i = p.getX();
        int j = p.getY();

        // create a new copy of the matrix before pivoting
        Matrix m = matrix.copy();

        Double Aij = m.getValue(i, j);

        // reduce Aij to 1
        m.setValue(i,j,1.0);

        // reduce all other elements in row j by Aij
        AugRow rowJ = m.getRow(j);
        for(int k=0;k<m.getColumnSize();k++)
        {
            if(k!=i)
                rowJ.setElement(k,rowJ.getElement(k)/Aij);
        }

        // reduce elements in all other rows
        for(int l=0;l<m.getRowSize();l++)
        {
            if(l!=j)
            {
                // find the row multiplier y in the equation Ail - Aij*y = 0; y = Ail / Aij
                Double y = m.getValue(i,l);
                m.setValue(i,l,0.0);

                // reduce all other elements using multiplier
                for(int k=0;k<m.getColumnSize();k++)
                {
                    if(k!=i)
                    {
                        // Akl = Akl - Akj*y
                        Double val = m.getValue(k,l) - m.getValue(k,j)*y;
                        m.setValue(k, l,val);
                    }
                }
            }
        }
        printer.Print("After Pivot\r\n");
        printer.Print(m.toString());

        return m;
    }
}

import java.util.ArrayList;

/**
 * Aaron Muir
 * CS 309
 * LP Project
 */
public class Solver
{
    private Matrix initialSolution;
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
        this.initialSolution = original;
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

            for (Matrix m : optimalMatrices)
            {
                Double objValue = m.getObjValue();

                printer.Print(m.getSolution().toString());
                printer.Print("Optimal Value: " + m.getObjValue().toString());

                if (objValue > maxValue)
                {
                    maxValue = objValue;
                    bestMatrix = m;
                }
            }
            printer.Print("BEST SOLUTION");
            printer.Print("-------------");
            printer.Print(bestMatrix.getSolution().toString());
            printer.Print("Max Value: " + maxValue.toString());
        }
        else
        {
            printer.Print("NO SOLUTIONS FOUND");
        }
    }

    /**
     * Solve the LP problem
     */
    private void solve()
    {
        try
        {
            this.determineSolveMethod();

            // copy the initialSolution so it is preserved
            tables.add(initialSolution.copy());

            if (solveMethod == Method.simplex)
            {
                simplex();
            } else if (solveMethod == Method.twoPhaseSimplex)
            {
                twoPhaseSimplex();
            } else
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

        // phase 1 - solve auxiliary function

        // choose variable x0 to enter the basis
        selectX0Pivot(current);

        // we should have a bfs
        assert current.getSolution().isBfs();

        // repeatedly improve aux obj W until W is zero
        moveToAdjBfs(current);

        // assume the last matrix is the solved auxiliary
        Matrix solvedAux = tables.get(tables.size()-1).copy();

        // remove auxiliary remnants
        solvedAux.removeAuxiliary();

        // add to list of matrices
        tables.add(solvedAux);

        // phase 2 - solve simplex

        // continue to solve using simplex
        simplex();
    }

    /**
     * Improves the objective
     * @param matrix
     * @return whether or not the solution is optimal
     */
    private void moveToAdjBfs(Matrix matrix)
    {
        try
        {
            // a. choose column i to enter the basis by finding largest Ai0
            ArrayList<Integer> cols = getLargestAi0(matrix);

            // if all Ai0 (not b) are <=0 (matrix is optimal)
            if (matrix.isOptimal())
            {
                if (matrix.hasAuxiliary())
                    if (matrix.getObjValue() != 0 || matrix.getSolution().isBasic())
                        throw new InfeasibleException("Infeasible Matrix :: " + matrix.toString());

                optimalMatrices.add(matrix);
                return;
            }

            // b. choose the row j of the variable to leave
            // list of points to leave the basis
            ArrayList<Point> points = pointsToPivot(matrix,cols);

            // if list of aij is size 0, unbounded
            if(points.size()<=0)
                throw new UnboundedException("Unbounded Matrix :: "+matrix.toString());

            // c. for each aij in list
            // pivot on every aij and move to adj bfs on resulting matrices
            for(Point p:points)
            {
                pivot(matrix,p.getX(),p.getY());
                moveToAdjBfs(matrix);
            }
        }
        catch (InfeasibleException ex)
        {
            ExceptionHandler.Handle(ex);
        }
        catch(UnboundedException ex)
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
            for(int j=0;j<m.getRowSize();j++)
            {
                if(m.getValue(i,j)>0)
                    validPoints.add(new Point(i,j));
            }
        }

        // find minimum bj/aij for every i where aij > 0
        Double min=Double.MAX_VALUE;
        for(Point p:validPoints)
        {
            Double b = m.getValue(m.getColumnSize(),p.getY());
            Double Aij = m.getValue(p.getX(),p.getY());

            assert Aij != 0.0;

            if(b/Aij < min)
                min = b/Aij;
        }

        ArrayList<Point> pivotPoints = new ArrayList<Point>();
        // create list of points containing minimum bj/aij
        for(Point p:validPoints)
        {
            Double b = m.getValue(m.getColumnSize(),p.getY());
            Double Aij = m.getValue(p.getX(),p.getY());

            if(b/Aij == min)
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
            if(rowZero.getElement(i)==largest)
                cols.add(i);
        }
        return cols;
    }

    /**
     * find and pivot on row in column x0
     * @param matrix
     */
    private void selectX0Pivot(Matrix matrix)
    {
        // find the row of the most negative basic var.
        // default to row 2 if no negatives found.
        int negRow=2;
        Double mostNegBasic = 0.0;
        for(int i=0;i<matrix.getBasicCount();i++)
        {
            for(int j=0;j<matrix.getRowSize();j++)
            {
                if(matrix.getValue(i,j)<mostNegBasic)
                {
                    mostNegBasic = matrix.getValue(i,j);
                    negRow=j;
                }
            }
        }
        // pivot on x0 in the row with the most negative basic
        pivot(matrix,0,negRow);
    }

    /**
     * determine the solve method to be used
     */
    private void determineSolveMethod()
    {
        // does the initial solution correspond to the origin
        if(initialSolution.getSolution().isOrigin())
            solveMethod = Method.simplex;
        else
            solveMethod = Method.twoPhaseSimplex;
    }

    /**
     * Pivot on an element in row j column i reducing xi to an identity vector.
     * xi enters the basis.
     *
     * @param matrix
     * @param i
     * @param j
     */
    private void pivot(Matrix matrix, int i, int j)
    {
        // create a new copy of the matrix before pivoting
        matrix = matrix.copy();

        Double Aij = matrix.getValue(i, j);

        // reduce Aij to 1
        matrix.setValue(i,j,1.0);

        // reduce all other elements in row j by Aij
        AugRow rowJ = matrix.getRow(j);
        for(int k=0;k<matrix.getColumnSize();k++)
        {
            if(k!=i)
                rowJ.setElement(k,rowJ.getElement(k)/Aij);
        }

        // reduce elements in all other rows
        for(int l=0;l<matrix.getRowSize();l++)
        {
            if(l!=j)
            {
                // find the row multiplier y in the equation Ail - Aij*y = 0; y = Ail / Aij
                Double y = matrix.getValue(i,l);
                matrix.setValue(i,l,0.0);

                // reduce all other elements using multiplier
                for(int k=0;k<matrix.getColumnSize();k++)
                {
                    if(k!=i)
                    {
                        // Akl = Akl - Akj*y
                        Double val = matrix.getValue(k,l) - matrix.getValue(k,j)*y;
                        matrix.setValue(k, l,val);
                    }
                }
            }
        }

        // add pivoted matrix to list
        tables.add(matrix);

        // print the matrix
        printer.Print(matrix.toString());
    }
}

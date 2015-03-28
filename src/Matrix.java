import java.util.ArrayList;
/**
 * Aaron Muir
 * Adam Julovich
 * CS 309
 * LP Project
 */

/**
 * Matrix containing an original objectiveRow and multiple constraints
 */
public class Matrix
{
    private ArrayList<AugRow> rows;
    private boolean hasAuxiliary;
    protected Matrix _parent;
    protected ArrayList<Matrix> _children;
    private int slackCount=0;

    protected static int index;

    private enum Result
    {
        OK,
        Infeasible,
        Unbounded
    }

    private Result result;

    Matrix()
    {
        index++;
        result = Result.OK;
        rows = new ArrayList<>();
        _children = new ArrayList<>();
        slackCount = 0;
    }

    /**
     * adds a slack variable to the specified constraint
     *
     * @param constraint a constraint that exists in the matrix
     */
    public void addSlack(AugRow constraint)
    {
        assert rows.indexOf(constraint)!=-1:"Constraint does not exist - can't add slack var";

        slackCount++;

        // add zero to all other elements
        for (AugRow row : rows)
        {
            if (row.equals(constraint))
            {
                // insert new xi slack var to constraint
                row.insertA(1.0);
            } else
            {
                // insert zero at xi
                row.insertA(0.0);
            }
        }
    }

    /**
     * Add a constraint to the matrix
     *
     * @param constraint a row of constraint entries
     */
    public void addConstraint(AugRow constraint)
    {
        rows.add(constraint);
    }


    /**
     * @return the solution to the matrix
     */
    public Solution getSolution()
    {
        ArrayList<Double> solution = new ArrayList<>();

        assert(rows.size()>0);
        assert isValid();

        // move through each column (Ai) - exclude the last(b) column
        for(int i = 0; i < getColumnSize()-1;i++)
        {
            // if identity column then add the value of b to the solution, else zero
            int identity = getIdentity(i);
            if(identity!=-1)
            {
                solution.add(rows.get(identity).getB()); // value of b
            }
            else
            {
                solution.add(0.0);
            }
        }
        return new Solution(slackCount,getColumnSize()-slackCount-1,solution);
    }


    /**
     *
     * @param i column number to search for identity rows
     * @return returns the row j that corresponds to the identity (1) in column i.
     *          otherwise returns -1;
     */
    public int getIdentity(int i)
    {
        int zeroCount = 0;
        int identity = -1;

        // move through each row (Aij)
        for(int j = 0; j < rows.size();j++)
        {
            Double val = rows.get(j).getElement(i);

            if (val == 0.0)
                zeroCount++;
            else if(val == 1.0)
                identity = j;
            else
                return -1;

        }

        // there should be m-1 zeros and identity row must exist
        if(zeroCount == rows.size() - 1)
            return identity;
        else
            return -1;
    }

    /**
     *
     * @return the negation of the objective function's value.
     */
    public Double getObjValue()
    {
        AugRow obj;
        if(hasAuxiliary)
            obj = getAuxiliaryRow();
        else
            obj = getObjectiveRow();

        return obj.getB()*-1.0;
    }
    private AugRow getObjectiveRow()
    {
        if(hasAuxiliary)
            return rows.get(1);
        else
            return rows.get(0);
    }
    public void setObjectiveRow(AugRow objectiveRow)
    {
        rows.add(0,objectiveRow);
    }

    /**
     * Auxiliary row & column functions
     */
    public AugRow getAuxiliaryRow()
    {
        if(hasAuxiliary)
            return rows.get(0);
        else
            return null;
    }
    public boolean hasAuxiliary()
    {
        return hasAuxiliary;
    }
    public void createAuxiliary()
    {
        try
        {
            if(!isValid())
                throw new Exception("Matrix format is not valid!");

            // insert x0=0 if the row is the obj function, otherwise x0=-1
            for (AugRow row:rows)
            {
                if(row.equals(getObjectiveRow()))
                    row.insertFront(0.0);
                else
                    row.insertFront(-1.0);
            }

            // add the new aux with x0=-1 in the new column and zero all in other columns
            AugRow aux = new AugRow();
            aux.addElement(-1.0);
            for(int i=0;i<getColumnSize()-1;i++)
            {
                aux.addElement(0.0);
            }
            rows.add(0,aux);

            hasAuxiliary = true;
        }
        catch (Exception ex)
        {
            ExceptionHandler.Handle(ex);
        }
    }
    public void removeAuxiliary()
    {
        assert hasAuxiliary;

        // remove aux row
        rows.remove(0);

        // remove x0 column
        for(AugRow row:rows)
        {
            row.removeFront();
        }
        hasAuxiliary = false;
    }


    /**
     * if all values in Ai0 are less than or equal to zero, then
     * the objective function is optimal
     *
     * @return whether or not the objective function is optimal
     */
    public boolean isOptimal()
    {
        AugRow firstRow = rows.get(0);

        assert firstRow !=null;

        // do not include b column in determination
        for(int i=0;i<getColumnSize()-1;i++)
        {
            if(firstRow.getElement(i) > 0)
            {
                Printer p = new Printer(Printer.Style.Console);
                p.Print("Element "+ i+ " in the top row is greater than zero. "+firstRow.getElement(i).toString()+"\r\n");
                p.Print("Matrix is not optimal.\r\n\r\n");
                return false;
            }
        }
        return true;
    }

    /**
     * @return the n number of columns in the matrix
     */
    public int getColumnSize()
    {
        return getObjectiveRow().size();
    }

    /**
     * @return the m number of rows in the matrix
     */
    public int getRowSize()
    {
        return rows.size();
    }

    /**
     * unbounded flag status
     */
    public boolean isUnbounded()
    {
        return result == Result.Unbounded;
    }

    /**
     * infeasible flag status
     */
    public boolean isInfeasible()
    {
        return result == Result.Infeasible;
    }

    /**
     * flags the Matrix as unbounded
     */
    public void flagUnbounded()
    {
        result = Result.Unbounded;
    }

    /**
     * flags the Matrix as infeasible
     */
    public void flagInfeasible()
    {
        result = Result.Infeasible;
    }

    /**
     * @return whether or not the matrix is valid
     */
    public boolean isValid()
    {
        // column sizes should be consistent across rows
        int objCols = getColumnSize();
        for(AugRow r:rows)
        {
            if(r.size()!=objCols)
                return false;
        }

        return true;
    }

    /**
     * Get the value of element in row j column i
     * @param i column
     * @param j row
     * @return value of Aij
     */
    public Double getValue(int i,int j)
    {
        return rows.get(j).getElement(i);
    }

    /**
     * Set the value of element in row j column i
     * @param i column
     * @param j row
     * @return value to be set in Aij
     */
    public void setValue(int i,int j,Double val)
    {
        rows.get(j).setElement(i,val);
    }

    /**
     *
     * @param j row
     * @return the row at index j
     */
    public AugRow getRow(int j)
    {
        return rows.get(j);
    }

    /**
     *
     * @param p Point to pivot on. -1,-1 indicates no pivot
     * @return matrix in string format
     */
    public String toString(Point p)
    {
        String result = "\r\n";
        for(int i=0; i < rows.size();i++)
        {
            if(p.getY()==i)
                result += rows.get(i).toString(p);
            else
                result += rows.get(i).toString();
        }
        result += "\r\n";
        return result;
    }
    @Override
    public String toString()
    {
        return toString(new Point(-1,-1));
    }

    /**
     *
     * @return copy of this matrix
     */
    public Matrix copy()
    {
        Matrix copy = new Matrix();

        for(AugRow row : this.rows)
        {
            copy.rows.add(row.copy());
        }
        copy._parent = this;
        copy.hasAuxiliary = this.hasAuxiliary;
        copy.slackCount = this.slackCount;
        copy.result = this.result;
        this._children.add(copy);
        return copy;
    }
}

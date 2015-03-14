import java.util.ArrayList;

/**
 * Aaron Muir
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

    // slack count
    private int slackCount;

    Matrix()
    {
        rows = new ArrayList<AugRow>();
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
        for(int i = 0; i < rows.size();i++)
        {
            if(rows.get(i).equals(constraint))
            {
                // insert new xi slack var to constraint
                rows.get(i).insertA(1.0);
            }
            else
            {
                // insert zero at xi
                rows.get(i).insertA(0.0);
            }
        }
    }

    /**
     * Add a constraint to the matrix
     *
     * @param constraint
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
        ArrayList<Double> solution = new ArrayList<Double>();

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
        return new Solution(slackCount,solution);
    }


    /**
     *
     * @param i
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
            else if(val != 1.0)
                return -1;
            else
                identity = j;
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

            // add the new aux with x0=-1 in the new column and zero all in other columns
            AugRow aux = new AugRow();
            aux.addElement(-1.0);
            for(int i=0;i<getColumnSize();i++)
            {
                aux.addElement(0.0);
            }

            // insert x0=0 if the row is the obj function, otherwise x0=-1
            for (AugRow row:rows)
            {
                if(row.equals(getObjectiveRow()))
                    row.insertFront(0.0);
                else
                    row.insertFront(-1.0);
            }
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
            if(firstRow.getElement(i)>0)
                return false;
        }
        return true;
    }

    /**
     * @return whether or not the solution corresponds to the origin
     */
    public boolean isOrigin()
    {
        return getSolution().isOrigin();
    }

    /**
     * @return the N number of columns in the matrix
     */
    public int getColumnSize()
    {
        return getObjectiveRow().size();
    }

    /**
     * @return the M number of rows in the matrix
     */
    public int getRowSize()
    {
        return rows.size();
    }

    /**
     * @return the number of basic variables used
     */
    public int getBasicCount(){ return getColumnSize()-slackCount; }

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
     * @param i
     * @param j
     * @return
     */
    public Double getValue(int i,int j)
    {
        return rows.get(j).getElement(i);
    }

    /**
     * Get the value of element in row j column i
     * @param i
     * @param j
     * @return
     */
    public void setValue(int i,int j,Double val)
    {
        rows.get(j).setElement(i,val);
    }

    /**
     *
     * @param j
     * @return the row at index j
     */
    public AugRow getRow(int j)
    {
        return rows.get(j);
    }

    /**
     *
     * @return string formatted matrix
     */
    public String toString()
    {
        String result = "";
        for(int i=0; i < rows.size();i++)
        {
            result += rows.get(i).toString();
        }
        return result;
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
        copy.hasAuxiliary = this.hasAuxiliary;
        copy.slackCount = this.slackCount;
        return copy;
    }
}

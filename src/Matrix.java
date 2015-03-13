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
     * Return the matrix in string format
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
     * @return the solution to the matrix
     */
    public Solution getSolution()
    {
        ArrayList<Double> solution = new ArrayList<Double>();

        assert(rows.size()>0);

        int n = rows.get(0).size();
        int m = rows.size();

        // move through each column (Ai) - exclude the last(b) column
        for(int i = 0; i < n-1;i++)
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
        AugRow obj = getObjectiveRow();
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
     *
     * @return the number of slack variables used
     */
    public int getSlackCount(){ return slackCount; }

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
        //TODO create auxiliary
        hasAuxiliary = true;
    }
    public void removeAuxiliary()
    {
        //TODO remove the auxiliary from the matrix
        hasAuxiliary = false;
    }
}

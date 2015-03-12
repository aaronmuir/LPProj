import java.util.ArrayList;

/**
 * Aaron Muir
 * CS 309
 * LP Project
 */

/**
 * Row of augmented matrix Ax=b;
 */
public class AugRow
{
    private ArrayList<Double> Aj;
    private Double b;
    private Boolean negate;
    private Boolean constraint;

    AugRow()
    {
        Aj = new ArrayList<Double>();
        constraint = false;
        negate = false;
    }

    /**
     * Adds an element to the end of the row. The new value
     * is the b element. The previous b element gets moved to Ai.
     *
     * @param val The value of the element to add
     */
    public void insertElement(Double val)
    {
        // if b exists, move it to the last column of A
        if(b!=null)
            Aj.add(b);

        // set b equal to the new value
        b=new Double(val);
    }

    /**
     * inserts a new coefficient in the last column of the row Aj
     *  in the A matrix.
     *
     * @param val
     */
    public void insertA(Double val)
    {
        Aj.add(val);
    }

    /**
     * Gets the elements of Aj in the A matrix.
     *
     * @return
     */
    public ArrayList<Double> getAj(int i)
    {
        return Aj;
    }
    public boolean Equals(AugRow row)
    {
        if(!row.Aj.equals(Aj))
            return  false;

        if(row.b != b)
            return false;

        return true;
    }

    /**
     * Negates all variables
     */
    public void negate()
    {
        for(int i=0;i< Aj.size();i++)
        {
            Aj.set(i, Aj.get(i).doubleValue()*-1.0);
        }
        b = b.doubleValue() * -1.0;
    }

    public Boolean getConstraint()
    {
        return constraint;
    }

    public void setConstraint(Boolean constraint)
    {
        this.constraint = constraint;
    }

    public Boolean getNegate()
    {
        return negate;
    }

    public void setNegate(Boolean negate)
    {
        this.negate = negate;
    }
}

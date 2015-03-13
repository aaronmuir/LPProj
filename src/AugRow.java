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
    private ArrayList<Double> elements;

    private Boolean negate;
    private Boolean constraint;

    AugRow()
    {
        elements = new ArrayList<Double>();
        constraint = false;
        negate = false;
    }

    /**
     * Adds an element to the end of the row. The new value
     * is the b element. The previous b element gets moved to Ai.
     *
     * @param val The value of the element to add
     */
    public void addElement(Double val)
    {
        elements.add(val);
    }

    /**
     *
     * @param i
     * @return element at index i
     */
    public Double getElement(int i)
    {
        return elements.get(i);
    }
    /**
     * inserts a new coefficient in the last column of the row elements
     *  in the A matrix. Placed in the column before the augmented column.
     *
     * @param val
     */
    public void insertA(Double val)
    {
        elements.add(elements.size() - 2, val);
    }

    /**
     * inserts a value the front of the row of elements
     *
     * @param val
     */
    public void insertFront(Double val)
    {
        elements.add(0,val);
    }

    /**
     * Removes a value the front of the row of elements
     */
    public void removeFront()
    {
        elements.remove(0);
    }

    /**
     * Equality comparison
     * @param row augmented row
     * @return equality
     */
    public boolean Equals(AugRow row)
    {
        if(!row.elements.equals(elements))
            return  false;

        return true;
    }

    public AugRow Copy()
    {
        AugRow copy = new AugRow();
        for(Double d : this.elements)
        {
            copy.elements.add(d);
        }
        copy.constraint = this.constraint;
        copy.negate = this.negate;
        return copy;
    }
    /**
     * Negates all variables
     */
    public void negate()
    {
        for(int i=0;i< elements.size();i++)
        {
            elements.set(i, elements.get(i).doubleValue()*-1.0);
        }
    }

    /**
     *
     * @return augmented row of elements in string format
     */
    public String toString()
    {
        String result = "";
        for(int i=0; i< elements.size()-1;i++)
        {
            result += "\t" + elements.get(i).toString();
        }
        // b
        result += "\t |" + elements.get(elements.size()-1).toString();
        return result;
    }


    public int size()
    {
        return elements.size();
    }

    /**
     *
     * @return the value of b - last element in the augmented row
     */
    public Double getB()
    {
        return  elements.get(elements.size()-1);
    }

    public Boolean isConstraint()
    {
        return constraint;
    }

    public void setConstraint(Boolean constraint)
    {
        this.constraint = constraint;
    }

    public Boolean isNegate()
    {
        return negate;
    }

    public void setNegate(Boolean negate)
    {
        this.negate = negate;
    }
}

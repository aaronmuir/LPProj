import java.util.ArrayList;

/**
 * Aaron Muir
 * CS 309
 * LP Project
 */

public class Solution
{
    private ArrayList<Double> elements;
    private int slackVars;

    public Solution(int slackVars, ArrayList<Double> elements)
    {
        this.slackVars = slackVars;

        for(Double d: elements) this.elements.add(d);
    }

    /**
     * Returns whether or not the current solution is a bfs
     *
     */
    public Boolean isBfs()
    {
        return isBasic()&& isFeasible();
    }

    /**
     * Returns whether or not the current solution is basic
     *
     */
    public Boolean isBasic()
    {
        int count = 0;
        for(int i=0;i<elements.size();i++)
        {
            if(elements.get(i)==0)
                count++;
        }
        return count<=slackVars;
    }

    /**
     * Returns whether or not the current solution is feasible
     *
     */
    public Boolean isFeasible()
    {
        int count = 0;
        for(int i=0;i<elements.size();i++)
        {
            if(elements.get(i) < 0)
                count++;
        }
        return count==0;
    }

    /**
     * Returns whether or not the current solution is the origin
     *
     */
    public Boolean isOrigin()
    {
       int basicCount = elements.size()-slackVars;
       for(int i=0;i<basicCount;i++)
       {
           if(elements.get(i)!=0.0)
               return false;
       }
       return true;
    }

    /**
     * returns the solution in string format
     */
    @Override
    public String toString()
    {
        String s = "";
        int i=1;
        for(Double d:elements)
        {
            s +=" x"+i+"="+d.toString();
            if(i!=elements.size())
                s+=", ";

            i++;
        }
        return s;
    }
}

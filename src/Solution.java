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
     *
     * @return whether or not the current solution is a bfs
     */
    private Boolean isBfs()
    {
        return isBasic()&& isFeasible();
    }

    /**
     * Returns whether or not the current solution is basic
     *
     */
    private Boolean isBasic()
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
    private Boolean isFeasible()
    {
        int count = 0;
        for(int i=0;i<elements.size();i++)
        {
            if(elements.get(i) < 0)
                count++;
        }
        return count==0;
    }
}

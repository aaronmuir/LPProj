import java.util.ArrayList;

/**
 * Aaron Muir
 * Adam Julovich
 * CS 309
 * LP Project
 */

public class Solution
{
    private ArrayList<Float> elements = new ArrayList<>();
    private int slackVars;
    private int basicVars;

    public Solution(int slackVars,int basicVars, ArrayList<Float> elements)
    {
        this.slackVars = slackVars;
        this.basicVars = basicVars;

        for(Float f: elements) this.elements.add(f);
    }

    /**
     * Returns whether or not the current solution is a bfs
     *
     */
    public Boolean isBfs()
    {
        return isBasic() && isFeasible();
    }

    /**
     * Returns whether or not the current solution is basic
     *
     */
    public Boolean isBasic()
    {
        int count = 0;
        for (Float element : elements)
        {
            if (element == 0f)
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
        for (Float element : elements)
        {
            if (element < 0f)
                count++;
        }
        return count==0;
    }

    /**
     * returns the solution in string format
     */
    @Override
    public String toString()
    {
        String s = "";

        for(int i = 1; i <= basicVars;i++)
            if(elements.get(i-1)!=0)
                s += " x" + i + "=" + elements.get(i-1)+"\r\n";

        s+= "\r\n";
        return s;
    }
}

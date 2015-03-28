import java.text.DecimalFormat;
import java.util.ArrayList;

/**
 * Aaron Muir
 * Adam Julovich
 * CS 309
 * LP Project
 */

public class Solution
{
    private ArrayList<Double> elements = new ArrayList<>();
    private int slackVars;
    private int basicVars;

    public Solution(int slackVars,int basicVars, ArrayList<Double> elements)
    {
        this.slackVars = slackVars;
        this.basicVars = basicVars;
        for(Double d: elements) this.elements.add(d);
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
        for (Double element : elements)
        {
            if (element == 0.0)
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
        for (Double element : elements)
        {
            if (element < 0)
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
        DecimalFormat df = new DecimalFormat(" #0.00;-#");

        for(int i = 1; i <= basicVars;i++)
        {
            s += " x" + i + "=" + df.format(elements.get(i-1));
            if (i != basicVars)
                s += ", ";
        }
        s+= "\r\n";
        return s;
    }
}

import java.text.DecimalFormat;
import java.util.ArrayList;

/**
 * Aaron Muir
 * CS 309
 * LP Project
 */

public class Solution
{
    private ArrayList<Double> elements = new ArrayList<>();
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
        int i=1;
        DecimalFormat df = new DecimalFormat(" #0.00;-#");

        for(Double d:elements)
        {
            if(d!=0.0)
            {
                s += " x" + i + "=" + df.format(d);
                if (i != elements.size())
                    s += ", ";

            }
            i++;
        }
        s+= "\r\n";
        return s;
    }
}

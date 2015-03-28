import java.text.DecimalFormat;
import java.util.ArrayList;

/**
 * Aaron Muir
 * CS 309
 * LP Project
 */

public class Solution
{
    private ArrayList<Float> elements = new ArrayList<>();
    private int slackVars;

    public Solution(int slackVars, ArrayList<Float> elements)
    {
        this.slackVars = slackVars;

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
        int i=1;
        DecimalFormat df = new DecimalFormat(" #0.00;-#");

        for(Float d:elements)
        {
            if(d!=0f)
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

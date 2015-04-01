/**
 * Aaron Muir
 * Adam Julovich
 * CS 309
 * LP Project
 */

public class Eps
{
    public static final double eps = 1.0E-5;
    private static long zeroCount;

    public static double zero(double val)
    {
        if(Math.abs(val)<=eps)
        {
            zeroCount++;
            return 0.0;
        }
        else
            return val;
    }
    public static float zero(float val)
    {
        if(Math.abs(val)<=eps)
        {
            if(val!=0f)
                zeroCount++;
            return 0f;
        }
        else
            return val;
    }

    public static long getZeroCount() {
        return zeroCount;
    }
}

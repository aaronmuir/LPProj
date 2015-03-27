/**
 * Aaron Muir
 * CS 309
 * LP Project
 */
public class Point
{
    public Point(int x, int y)
    {
        this.x = x;
        this.y = y;
    }

    public int getX()
    {
        return x;
    }

    private int x;

    public int getY()
    {
        return y;
    }

    private int y;

    @Override
    public String toString()
    {
        return "("+x+","+y+")";
    }
}

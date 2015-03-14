/**
 * Aaron Muir
 * CS 309
 * LP Project
 */
public class UnboundedException extends Exception
{
    private String message;

    public UnboundedException(String message)
    {
        this.message = message;
    }
    @Override
    public String getMessage()
    {
        return this.message;
    }
}

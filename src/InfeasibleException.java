/**
 * Aaron Muir
 * CS 309
 * LP Project
 */
public class InfeasibleException extends Exception
{
    private String message;

    public InfeasibleException(String message)
    {
        this.message = message;
    }
    @Override
    public String getMessage()
    {
        return this.message;
    }
}

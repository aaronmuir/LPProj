/**
 * Aaron Muir
 * Adam Julovich
 * CS 309
 * LP Project
 */
public class ExceptionHandler
{
    /**
     * Default method for handling exceptions
     *
     * @param ex Exception to be handled
     */
    public static void Handle(Exception ex)
    {
        System.out.println(ex.getMessage());
        //System.out.println(ex.getStackTrace());
    }
}

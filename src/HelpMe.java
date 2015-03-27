/**
 * Aaron Muir
 * CS 309
 * LP Project
 */
public class HelpMe
{
    /**
     * Outputs the help documentation to the screen.
     */
    public static void showHelp()
    {
        System.out.println("");
        System.out.println("____________________________");
        System.out.println("LP Project Command Reference");
        System.out.println("");
        System.out.println("java LPProj <input file> [<output file>]");
        System.out.println("");
        System.out.println("Command switches");
        System.out.println("<input file>\tThe location of the file to be processed.");
        System.out.println("\t\tEx: \"C:\\folder\\LP input file.txt\"");
        System.out.println("");
        System.out.println("<output file>\tOptional: The location of a file where results will be printed to.");
        System.out.println("\t\tEx: \"C:\\folder\\output file.txt\"");
        System.out.println("");
    }
}

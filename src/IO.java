import java.io.*;

/**
 * Aaron Muir
 * CS 309
 * LP Project
 */
public class IO
{
    /**
     * Reads the contents of file into a string variable. crlf delimited
     *
     * @param fileLocation file path to the text file
     * @return the text inside the text file
     */
    public static String readFile(String fileLocation)
    {
        String fileString="";

        // read input file
        try
        {
            BufferedReader br = new BufferedReader(new FileReader(fileLocation));
            StringBuilder sb = new StringBuilder();
            String line = br.readLine();

            while (line != null) {
                sb.append(line);
                sb.append(System.lineSeparator());
                line = br.readLine();
            }
            fileString = sb.toString();

        } catch(IOException e)
        {
            ExceptionHandler.Handle(e);
        }
        return fileString;
    }

    /**
     * Write the text to a file at fileLocation
     *
     * @param fileLocation file path
     * @param text text to be written to the file
     */
    public static void writeFile(String fileLocation, String text)
    {
        try
        {
            BufferedWriter bw = new BufferedWriter(new FileWriter(fileLocation));
            bw.write(text);
        } catch(IOException e)
        {
            ExceptionHandler.Handle(e);
        }
    }
}

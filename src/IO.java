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
     * @return
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

        }
        catch (FileNotFoundException e)
        {
            ExceptionHandler.Handle(e);
        }
        catch(IOException e)
        {
            ExceptionHandler.Handle(e);
        }
        return fileString;
    }

    /**
     * Write the text to a file at fileLocation
     *
     * @param fileLocation
     * @param text
     */
    public static void writeFile(String fileLocation, String text)
    {
        try
        {
            BufferedWriter bw = new BufferedWriter(new FileWriter(fileLocation));
            bw.write(text);
        }
        catch(FileNotFoundException e)
        {
            ExceptionHandler.Handle(e);
        }
        catch(IOException e)
        {
            ExceptionHandler.Handle(e);
        }
    }
}

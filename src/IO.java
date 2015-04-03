import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Aaron Muir
 * Adam Julovich
 * CS 309
 * LP Project
 */
public class IO
{
    private static ArrayList<String> files = new ArrayList<>();
    private static FileWriter fw;

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
     * @param text text to be written to the file
     */
    public static void writeFile(String text, String fileLocation)
    {
        try
        {
            if(files.contains(fileLocation))
            {
                fw = new FileWriter(fileLocation,true);
            }
            else
            {
                fw = new FileWriter(fileLocation);
                files.add(fileLocation);
            }

            fw.write(text);
            fw.flush();

        } catch(IOException e)
        {

        }
    }
}

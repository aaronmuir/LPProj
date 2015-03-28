import java.io.*;

/**
 * Aaron Muir
 * CS 309
 * LP Project
 */
public class IO
{
    private static String writeLocation;
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
    public static void writeFile(String text)
    {
        try
        {
            if(fw == null)
                fw = new FileWriter(writeLocation);
            fw.write(text);
            fw.flush();

        } catch(IOException e)
        {

        }
    }

    public static String getWriteLocation()
    {
        return writeLocation;
    }

    public static void setWriteLocation(String writeLocation)
    {
        IO.writeLocation = writeLocation;
    }
}

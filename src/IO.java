import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 * Aaron Muir
 * CS 309
 * LP Project
 */
public class IO
{
    public String readFile(String fileLocation)
    {
        String fileString="";

        // read input file
        try(BufferedReader br = new BufferedReader(new FileReader(fileLocation))) {
            StringBuilder sb = new StringBuilder();
            String line = br.readLine();

            while (line != null) {
                sb.append(line);
                sb.append(System.lineSeparator());
                line = br.readLine();
            }
            fileString = sb.toString();

        } catch (FileNotFoundException e)
        {
            e.printStackTrace();
        } catch (IOException e)
        {
            e.printStackTrace();
        }
        return fileString;
    }
}

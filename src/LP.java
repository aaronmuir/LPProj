/**
 * Aaron Muir
 * CS 309
 * LP Project
 */
public class LP
{
    private static String fileString;
    private static String fileLocation;
    private static Matrix initMatrix;

    /**
     * Entry point
     *
     * @param args command line arguments
     */
    public static void main(String args[])
    {
        try
        {
            // verify the file is not null before processing
            if(args.length>0)
            {
                fileLocation = args[0];

                // read input file into string
                IO io = new IO();
                fileString = io.readFile(fileLocation);

                // parse string into standard form
                initMatrix = convertToMatrix(fileString);

                // parse solving algorithm
                    // is bfs
                        // simplex
                    /// else
                        // two phase simplex

                // output results
                System.out.println("results");
            }
            else
            {
                // file ref is null, show help
                HelpMe.showHelp();
            }
        }
        catch(Exception ex)
        {
            ExceptionHandler.Handle(ex);
        }
    }

    /**
     * parse a text string from an input file into a matrix
     *
     * @param fileString lines of text that specify the linear system
     */
    public static Matrix convertToMatrix(String fileString)
    {
        Matrix Axb = new Matrix();

        // split lines by carriage return and line feed
        String[] lines = fileString.split("(\r\n|\n)");

        // need at least 2 lines to have an LP problem
        assert lines.length>2;

        // parse each line into a new equation row
        for(int i=0; i < lines.length;i++)
        {
            parseLine(Axb, lines[i], i);
        }
        return  Axb;
    }

    /**
     * Parse line of text into a row in the matrix
     *
     * @param axb Matrix
     * @param line Line of text
     * @param i Index of line in file
     */
    private static void parseLine(Matrix axb, String line, int i)
    {
        // split line by tab
        String[] splitLine = line.split("(\t)");

        // if no elements in the line, move to next line
        if(splitLine.length<=0)
            return;

        // use elements to form augmented row
        AugRow row = new AugRow();

        // parse parameters into equation (augmented row)
        for(int j=0; j<splitLine.length;j++)
        {
            String param = splitLine[j].trim();
            Parameter parameter = new Parameter(i, j, row, param).parse();
        }

        // negate entire equation
        if(row.isNegate())
        {
            row.negate();
        }

        // constraint or origin bfs
        if(row.isConstraint())
        {
            axb.addConstraint(row);
        }
        else
        {
            // since bfs, add zero for b
            row.addElement(0.0);
            axb.setObjectiveRow(row);
        }
    }
}

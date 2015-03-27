/**
 * Aaron Muir
 * CS 309
 * LP Project
 */
public class LP
{

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
                String fileLocation = args[0];

                // read input file into string
                String fileString = IO.readFile(fileLocation);

                // parse string into standard form
                Matrix initMatrix = convertToMatrix(fileString);

                // configure a printer
                Printer printer = new Printer(Printer.Style.Console);

                // solve matrix
                Solver solver = new Solver(initMatrix,printer);

                // output results
                solver.PrintResults();
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

        // add slack vars
        for(int j=0;j<Axb.getRowSize();j++)
        {
            if(Axb.getRow(j).isConstraint())
                Axb.addSlack(Axb.getRow(j));

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
            TextParam.parse(i, j, row, param);
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

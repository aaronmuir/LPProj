/**
 * Aaron Muir
 * CS 309
 * LP Project
 */
public class LP
{
    private static String fileString;
    private static String fileLocation;

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
                LPMatrix Axb = convertToMatrix(fileString);

                // invoke solving algorithm
                assert(true);

                // output results
                assert true;
            }
            else
            {
                // file ref is null, show help
                HelpMe.showHelp();
            }
        }
        catch(Exception ex)
        {
            System.out.println(ex.getMessage().toString());
            System.out.println(ex.getStackTrace().toString());
        }
    }

    /**
     * parse a text string from an input file into a matrix
     *
     * @param fileString lines of text that specify the linear system
     */
    public static LPMatrix convertToMatrix(String fileString)
    {
        LPMatrix Axb = new LPMatrix();

        // split lines by carriage return and line feed
        String[] lines = fileString.split("/r/n");

        // need at least 2 lines to have an LP problem
        assert lines.length>2;

        // parse each line into a new row
        for(int i=0; i < lines.length;i++)
        {
            // split line by tab
            String[] splitLine = lines[i].split("\t");

            // if no elements in the line, move to next line
            if(splitLine.length<=0)
                continue;

            // use elements to form equation
            AugRow equation = new AugRow();

            // assume no need to negate the equation
            boolean negate = false;
            boolean constraint = false;

            for(int j=0; j<splitLine.length;j++)
            {
                // potential parameter in equation
                String param = splitLine[i];
                try
                {
                    // if the element is a number, add it to the equation
                    Double val = Double.parseDouble(param);
                    equation.insertElement(val);
                }
                catch (NumberFormatException ex)
                {
                    // handle NaN
                    if(param.equals("<="))
                    {
                        // max constraint - add slack var
                        constraint = true;
                    }
                    else if(param.equals(">="))
                    {
                        // min constraint - add slack var
                        constraint = true;
                        // negate
                        negate = true;
                    }
                    else
                    {
                        assert false:"Unknown element in equation "+i+" tab "+j;
                    }
                }
            }
            if(negate)
            {
                equation.negate();
                negate = false;
            }

            if(constraint)
            {
                Axb.constraints.add((Constraint)equation);
            }
            else
            {
                Axb.bfs = (Bfs)equation;
            }
        }
        return  Axb;
    }
}

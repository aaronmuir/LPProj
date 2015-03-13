/**
 * Aaron Muir
 * CS 309
 * LP Project
 */
public class Parameter
{
    private int i;
    private int j;
    private AugRow equation;
    private String param;

    /**
     * Parameter from text file
     *
     * @param i row index
     * @param j col index
     * @param row augmented row to store parameter in
     * @param param ASCII representation of param
     */
    public Parameter(int i,int j, AugRow row, String param)
    {
        this.i = i;
        this.j = j;
        this.equation = row;
        this.param = param;
    }

    /**
     * Parse the text parameter into the equation
     *
     * @return
     */
    public Parameter parse()
    {
        try
        {
            // if the element is a number, add it to the equation
            Double val = Double.parseDouble(param);
            equation.addElement(val);
        }
        catch (NumberFormatException ex)
        {
            // handle NaN
            if(param.equals("<="))
            {
                // max constraint - add slack var
                equation.setConstraint(true);
            }
            else if(param.equals(">="))
            {
                // min constraint - add slack var
                equation.setConstraint(true);
                // negate
                equation.setNegate(true);
            }
            else
            {
                assert false:"Unknown element in equation "+i+" tab "+j;
            }
        }
        return this;
    }
}

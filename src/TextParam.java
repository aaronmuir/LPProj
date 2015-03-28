/**
 * Aaron Muir
 * Adam Julovich
 * CS 309
 * LP Project
 */
public class TextParam
{
    /**
     * Parse Parameter from text file
     *
     * @param i row index
     * @param j col index
     * @param row augmented row to store parameter in
     * @param param ASCII representation of param
     */
    public static void parse(int i, int j, AugRow row, String param)
    {
        try
        {
            // if the element is a number, add it to the row
            Double val = Double.parseDouble(param);
            row.addElement(val);
        }
        catch (NumberFormatException ex)
        {
            // handle NaN
            switch (param)
            {
                case "<=":
                    // max constraint
                    row.setConstraint(true);
                    break;
                case ">=":
                    // min constraint
                    row.setConstraint(true);
                    // negate
                    row.setNegate(true);
                    break;
                case "==":
                    row.setConstraint(true);
                    break;
                default:
                    assert false : "Unknown element in row " + i + " tab " + j;
                    break;
            }
        }
    }
}

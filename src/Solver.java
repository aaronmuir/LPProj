import java.util.ArrayList;

/**
 * Aaron Muir
 * CS 309
 * LP Project
 */
public class Solver
{
    private Matrix original;
    private ArrayList<Matrix> tables;
    private ArrayList<AugRow> solutions;
    private AugRow optimal;
    private Method method;

    private enum Method
    {
        simplex,
        twoPhaseSimplex,
        dualSimplex
    }

    /**
     * LP solving algorithms
     */
    public Solver()
    {
        tables = new ArrayList<Matrix>();
        solutions = new ArrayList<AugRow>();
        optimal = new AugRow();
        method = Method.simplex;
    }

    /**
     * Returns the negation of the optimal solution's value.
     * @return
     */
    public Double getObjValue()
    {
        return optimal.getB()*-1.0;
    }

    private void setMethod()
    {
        // is origin bfs?

    }
}

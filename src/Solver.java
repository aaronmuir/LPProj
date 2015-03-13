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
    private ArrayList<Solution> solutions;

    private enum Method
    {
        simplex,
        twoPhaseSimplex,
        dualSimplex
    }
    private Method solveMethod;

    /**
     * LP solving algorithms
     */
    public Solver(Matrix original)
    {
        tables = new ArrayList<Matrix>();
        solutions = new ArrayList<Solution>();

    }

    /**
     * Returns the negation of the optimal solution's value.
     * @return
     */
    public Double getObjValue()
    {
        // TODO get objective value from matrix
        return 0.0;// optimal.getB()*-1.0;
    }

    private void setMethod()
    {
        // is origin bfs?
        if(original.getSolution().isBfs())
            solveMethod = Method.simplex;
        else
            solveMethod = Method.twoPhaseSimplex;
    }
}

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
        this.original = original;
    }

    /**
     * Solve the LP problem
     */
    public void solve()
    {
        try
        {
            this.determineSolveMethod();

            if (solveMethod == Method.simplex)
            {
                simplex();
            } else if (solveMethod == Method.twoPhaseSimplex)
            {
                twoPhaseSimplex();
            } else
                throw new Exception("Unexpected solve method being utilized");
        }
        catch(Exception ex)
        {
            ExceptionHandler.Handle(ex);
        }
    }

    /**
     * simplex method of solving
     */
    private void simplex()
    {

    }

    /**
     * two-phase simplex method
     */
    private void twoPhaseSimplex()
    {

    }

    private void determineSolveMethod()
    {
        // is origin bfs?
        if(original.getSolution().isBfs())
            solveMethod = Method.simplex;
        else
            solveMethod = Method.twoPhaseSimplex;
    }
}

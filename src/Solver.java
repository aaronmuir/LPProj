import java.util.ArrayList;

/**
 * Aaron Muir
 * CS 309
 * LP Project
 */
public class Solver
{
    private Matrix origin;
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
        this.origin = original;
    }

    /**
     * Solve the LP problem
     */
    public void solve()
    {
        try
        {
            this.determineSolveMethod();

            // copy the origin so it is preserved
            tables.add(origin.copy());

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
        // get the current matrix
        Matrix current = tables.get(tables.size()-1);

        // todo implement simplex algorithm

            // if resulting solution is feasible & basic, store the solution
    }

    /**
     * two-phase simplex method
     */
    private void twoPhaseSimplex()
    {
        // get the current matrix
        Matrix current = tables.get(tables.size()-1);

        // create an auxiliary row & column in the current matrix
        current.createAuxiliary();

        // solve auxiliary function
        // TODO solve aux
        // create new matrix for each solve iteration

        // todo remove aux and store for simplex
    }

    private void determineSolveMethod()
    {
        // is origin bfs?
        if(origin.getSolution().isBfs())
            solveMethod = Method.simplex;
        else
            solveMethod = Method.twoPhaseSimplex;
    }
}

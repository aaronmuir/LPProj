Write a program to implement the simplex algorithm.

1. (1)Assume all variables have the non-negativity constraints.&nbsp;
2. (2)Input: each LP problem is given by a text file. The file starts with the objective function, followed by the constraints.&nbsp;

For example: the following LP problem



&nbsp;

would be input as follows:

-1&nbsp;-4&nbsp;-3&nbsp;-3

1&nbsp;1&nbsp;0&nbsp;-1&nbsp;>=&nbsp;-1

1&nbsp;-1&nbsp;-1&nbsp;-1&nbsp;<=&nbsp;2

1&nbsp;1&nbsp;0&nbsp;-1&nbsp;<=&nbsp;-1

-1&nbsp;1&nbsp;-1&nbsp; 1&nbsp;<=&nbsp;-2

&nbsp;&nbsp;

1. (3)Output: Your program should print out the initial tableau and the tableau after each pivot. If the problem is infeasible or unbounded, the program should print out an appropriate message. You can print out the results to the standard output device – the monitor or to a file specified by the user.obj&nbsp;
2. (4)After reading the input file, your program should do the following:&nbsp;
  1. Convert the problem into standard form. This includes converting inequalities with "≥" to inequalities with "≤" and adding slack variables.&nbsp;
  2. If all the right hand sides of the constraints from a. are positive, invoke the Simplex Algorithm; &nbsp;
  3. Otherwise invoke the Two-Phase Simplex Algorithm&nbsp;

3. (5)Although a good program should not have constraints on the size of the problem, you can assume that each problem has at most 10 variables excluding the slack variables, and at most 10 constraints excluding the non-negativity constraints.&nbsp;
  1. Array of [11,10] . [0,0-9] is for obj func. [1-10,0-9] for constraints&nbsp;

4. (6)Test your program on a number of small problems which you can solve by hand. You can also use the examples from class. &nbsp;
5. (7)Be sure to clearly document your program. Comments?&nbsp;
6. (8)You can work in team of TWO. In this case, you should try to divide the work evenly and comment on whom does which part.&nbsp;

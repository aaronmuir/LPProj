# Implements the simplex algorithm.

## Input

Assumes all variables have the non-negative constraints.
Each LP problem is input through the use of formatted text file. The file starts with the objective function (function to maximize), followed by the constraints.

Input example:

|  -1 | -4  | -3  | -3  |   |   |
|:-:|---|---|:-:|:-:|:-:|
| 1 | 1 | 0 |-1 | >= | -1 |
| 1  | -1 | -1  | -1  | <=  | 2 |
| 1 | 1 | 0  |  -1 |  <= |  -1 |
| -1  | 1  |  -1 |  1 | <=  | -2  |

## Output

Output: Prints out every optimal solution found. If the problem is infeasible or unbounded, the will print out a summary message. You can print out the results to the standard output device – the monitor or to a file specified by the user.obj

## Method

After reading the input file, the following steps occur:
  1. Problem is converted into standard form. This includes converting inequalities with "≥" to inequalities with "≤" and adding slack variables.
  2. If all the right hand sides of the constraints from are positive, the Simplex Algorithm is invoked.
  3. Otherwise, the Two-Phase Simplex Algorithm is invoked.

This program does not have a limit on the size of the problem. This program uses the BigFraction class from @kiprobinson (https://github.com/kiprobinson/BigFraction). 

The tests folder includes test files that can be used to test the program. The solutions.txt file contains solutions to those tests.


// regular expression
// (?=…) Positive lookahead. (?=[-+]) 
// "x+5-3+x"; exp.split("(?=[-+])");
// {{"x"},{"+5"},{"-3"},{"+x"}}

/*
Solve a given equation and return the value of x in the form of string "x=#value". The equation contains only '+', '-' operation, the variable x and its coefficient.
If there is no solution for the equation, return "No solution".
If there are infinite solutions for the equation, return "Infinite solutions".
If there is exactly one solution for the equation, we ensure that the value of x is an integer.

Example 1:
Input: "x+5-3+x=6+x-2"
Output: "x=2"
Example 2:
Input: "x=x"
Output: "Infinite solutions"
Example 3:
Input: "2x=x"
Output: "x=0"
Example 4:
Input: "2x+3x-6x=x+2"
Output: "x=-1"
Example 5:
Input: "x=x+2"
Output: "No solution"
*/

// 一次元方程式
class Solution {
    public String solveEquation(String equation) {
        // invalid input
        int[] lhs = evaluateExpression(equation.split("=")[0]),
              rhs = evaluateExpression(equation.split("=")[1]);
        
        lhs[0] -= rhs[0];
        rhs[1] -= lhs[1];
        // 5x = 3
        if (lhs[0] == 0 && rhs[1] == 0) {
            return "Infinite solutions";
        }
        
        if (lhs[0] == 0) {
            return "No solution";
        } 
        
        return "x=" + rhs[1] / lhs[0]; 
        // assumption: we ensure that the value of x is an integer.
    }
    
    public int[] evaluateExpression(String exp) {
        // only '+', '-' operation
        String[] tokens = exp.split("(?=[-+])");
        int[] res = new int[2]; 
        // res[0]: coefficient - a numerical quantity placed before and multiplying the variable in an algebraic expression (e.g., 4 in 4x y).
        // res[1]: constant
        for (String token : tokens) {
            if (token.equals("+x") || token.equals("x")) {
                res[0] += 1;
            } else if (token.equals("-x")) {
                res[0] -= 1;
            } else if (token.contains("x")) {
                res[0] += Integer.parseInt(
                            token.substring(0, 
                                            token.indexOf("x")));
            } else {
                res[1] += Integer.parseInt(token);
            }
        }
        
        return res;
    }
}

Evaluate Reverse Polish Notation
class Solution {
    public int evalRPN(String[] tokens) {
        if (tokens == null || tokens.length == 0) {
            return 0;
        }
        
        int first = 0, second = 0;
        Stack<Integer> s = new Stack<>();
        for (String token : tokens) {
            switch (token) {
                case "+": s.push(s.pop() + s.pop()); 
                          break;
                case "-": first = s.pop();
                          second = s.pop();
                          s.push(second - first); 
                          break;
                case "*": s.push(s.pop() * s.pop());
                          break;
                case "/": first = s.pop();
                          second = s.pop();
                          s.push(second / first);
                          break;
                default:  s.push(Integer.parseInt(token));
            }
        }
        return s.pop();
    }
}

Basic Calculator

The expression string may contain open ( and closing parentheses ), the plus + or minus sign -, non-negative integers and empty spaces .

class Solution {
    /* 
    digit: it should be one digit from the current number
    '+': number is over, we can add the previous number and start a new number
    '-': same as above
    '(': push the previous result and the sign into the stack, set result to 0, just calculate the new result within the parenthesis.
    ')': pop out the top two numbers from stack, first one is the sign before this pair of parenthesis, 
         second is the temporary result before this pair of parenthesis. We add them together.
     */
    public int calculate(String s) {
        // 3 + 2 - 5 + 4
        // 0 + (+3) + (+2) + (-5) + (+4)
        
        // 3 + (2 - 5) + 4
        Stack<Integer> stack = new Stack<>(); // used for parenthesis
        int res = 0;
        int num = 0; // start with 0
        int sign = 1;
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (Character.isDigit(c)) {
                num = num * 10 + (c - '0');
            } else if (c == '+') {
                res += sign * num; // sign is previous one
                num = 0;
                sign = 1; // + is used for next number
            } else if (c == '-') {
                res += sign * num; 
                num = 0;
                sign = -1;
            } else if (c == '(') {
                stack.push(res);
                stack.push(sign);
                
                res = 0;
                sign = 1;
                // num is already 0
            } else if (c == ')') {
                res += sign * num;
                num = 0; // next sign is after )
                res *= stack.pop(); // pop sign before parenthesis
                res += stack.pop(); // pop result before parenthesis
            }
        }
            
        res += sign * num; // last result
        
        return res;
    }
}

Basic Calculator II - only non-negative integers, +, -, *, / 
class Solution {
    public int calculate(String s) {
        // invalid input - null or length 0
        if (s == null || s.length() == 0) {
            return 0;
        }
        
        Stack<Integer> stack = new Stack<>();
        int num = 0;
        char sign = '+';
        
        // input: 3+2*2
        // stack: 3, 4
        // res: 7
        
        // input: 3-2*2
        // stack: 3, -4
        // res: -1
        char[] chars = s.toCharArray();
        for (int i = 0; i < s.length(); i++) {
            if (Character.isDigit(chars[i])) {
                num = num * 10 + (chars[i] - '0');
            } 
            
            if (!Character.isDigit(chars[i]) && !Character.isWhitespace(chars[i]) // sign
                || i == chars.length - 1) { // or last digit
                switch (sign) { // previous sign
                    case '+' : stack.push(num); break; // previous num
                    case '-' : stack.push(-num); break;
                    case '*' : stack.push(stack.pop() * num); break;
                    case '/' : stack.push(stack.pop() / num); break;
                }
                sign = chars[i]; // current sign
                num = 0;         // reset to 0
            }            
        }
        int res = 0;
        for (int val : stack) {
            res += val;
        }
        return res;
    }
}









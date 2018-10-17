public boolean isValid(String s) {
    if (s == null || s.length() == 0) {
        return true; // could you confirm with you interviewer
    }
    
    Stack<Character> stack = new Stack<>();
    
    for (char c : s.toCharArray()) {
        // parenthese, brackets, braces
        if (c == '(') {
            stack.push(')');
        } else if (c == '[') {
            stack.push(']');
        } else if (c == '{') {
            stack.push('}');
        } else if (stack.isEmpty() || stack.pop() != c) {
            return false;                                
        }
    }
    
    return stack.isEmpty();
}

Generate Parentheses

class Solution {
    public List<String> generateParenthesis(int n) {
        if (n <= 0) {
            return Collections.emptyList();
        }
        
        List<String> res = new ArrayList<>();
        dfs(res, new StringBuilder(), 0, 0, n);
        return res;
    }
    
    // dfs - not traverse all possibilities, have to be valid
    public void dfs(List<String> res, StringBuilder sb, int open, int close, int max) {
        if (sb.length() == max * 2) {
            res.add(sb.toString());
            return;
        }
        
        if (open < max) {
            sb.append("(");
            dfs(res, sb, open + 1, close, max);
            sb.setLength(sb.length() - 1);
        } 
        
        if (close < open) {
            sb.append(")");
            dfs(res, sb, open, close + 1, max);
            sb.setLength(sb.length() - 1);
        }
    }
}
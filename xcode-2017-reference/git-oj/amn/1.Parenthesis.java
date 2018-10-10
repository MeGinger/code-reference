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
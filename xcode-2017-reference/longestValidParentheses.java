	/**
	 * Longest Valid Parentheses 
Given a string containing just the characters '(' and ')', find the length of the longest valid (well-formed) parentheses substring.
For "(()", the longest valid parentheses substring is "()", which has length = 2.
Another example is ")()())", where the longest valid parentheses substring is "()()", which has length = 4. 
	 */
	public int longestValidParentheses(String s) {
		int maxLen = 0, last = -1;
		Stack<Integer> lefts = new Stack<>();
		for (int i = 0; i < s.length(); ++i) {
			if (s.charAt(i) == '(') {
				lefts.push(i);
				continue;
			}
			if (lefts.isEmpty()) {
				// no matching left
				// last is the position of last invalid ')'
				last = i;
			} else {
				// find a matching pair
				lefts.pop();
				if (lefts.isEmpty()) {
					maxLen = Math.max(maxLen, i - last);
				} else {
					// stack is not empty, so the current length is current
					// position i- last second position of '(' in stack
					// for example "(()()"
					maxLen = Math.max(maxLen, i - lefts.peek());
				}
			}
		}
		return maxLen;
	}

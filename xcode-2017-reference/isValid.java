/**
	 * Valid Parentheses
	 * Given a string containing just the characters '(', ')',
	 * '{', '}', '[' and ']', determine if the input string is valid. The
	 * brackets must close in the correct order, "()" and "()[]{}" are all valid
	 * but "(]" and "([)]" are not.
	 */
	public boolean isValid(String s) {
		long result = 0; // There may be cases that long is not enough to hold
		for (int i = 0; i < s.length(); i++) {
			char c = s.charAt(i);
			int n = 0;
			switch (c) {
			case '(':
				n = 1;
				break;
			case ')':
				n = -1;
				break;
			case '[':
				n = 2;
				break;
			case ']':
				n = -2;
				break;
			case '{':
				n = 3;
				break;
			case '}':
				n = -3;
				break;
			}
			if (n > 0) {
				// base is 4 instead of 10, 10 is ok as well
				result = result * 4 + n;
			} else {
				// n < 0
				if (result % 4 + n != 0) {
					return false;
				}
				result /= 4;
			}
		}

		return result == 0;
	}

	public boolean isValid2(String s) {
		Stack<Character> stack = new Stack<>();
		for (char c : s.toCharArray()) {
			if (c == '(') {
				stack.push(')');
			} else if (c == '{') {
				stack.push('}');
			} else if (c == '[') {
				stack.push(']');
			} else if (stack.isEmpty() || stack.pop() != c) {
				return false;
			}
		}
		return stack.isEmpty();
	}

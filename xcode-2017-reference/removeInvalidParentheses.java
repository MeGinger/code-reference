 /**
     * Remove Invalid Parentheses
*Remove the minimum number of invalid parentheses in order to make the input string valid. Return all possible results.
*Note: The input string may contain letters other than the parentheses ( and ).
*
*Examples:
*"()())()" -> ["()()()", "(())()"]
*"(a)())()" -> ["(a)()()", "(a())()"]
*")(" -> [""]
*
*DFS:
*We all know how to check a string of parentheses is valid using a stack. Or even simpler use a counter.
*The counter will increase when it is â€˜(â€˜ and decrease when it is â€˜)â€™. Whenever the counter is negative, we have more â€˜)â€™ than â€˜(â€˜ in the prefix.
*
*To make the prefix valid, we need to remove a â€˜)â€™. The problem is: which one? The answer is any one in the prefix. However, if we remove any one, we will generate duplicate results, for example: s = ()), we can remove s[1] or s[2] but the result is the same (). Thus, we restrict ourself to remove the first ) in a series of concecutive )s.
*
*After the removal, the prefix is then valid. We then call the function recursively to solve the rest of the string. However, we need to keep another information: the last removal position. If we do not have this position, we will generate duplicate by removing two â€˜)â€™ in two steps only with a different order.
*For this, we keep tracking the last removal position and only remove â€˜)â€™ after that.
*
*Now one may ask. What about â€˜(â€˜? What if s = â€˜(()(()â€™ in which we need remove â€˜(â€˜?
*The answer is: do the same from right to left.
*However a cleverer idea is: reverse the string and reuse the code!
*O(n)
*/
	private static final char[] ORDERED_PARENTHESES = new char[] { '(', ')' };
	private static final char[] REVERSED_PARENTHESES = new char[] { ')', '(' };

	public List<String> removeInvalidParentheses(String s) {
		return remove(s, new ArrayList<>(), 0, 0, ORDERED_PARENTHESES);
	}

	private List<String> remove(String s, List<String> ans, int lastFast, int lastSlow, char[] par) {
		for (int count = 0, fast = lastFast; fast < s.length(); ++fast) {
			char c = s.charAt(fast);
			if (c == par[0]) {
				count++;
			} else if (c == par[1]) {
				count--;
			}
			if (count >= 0) {
				continue;
			}
			// count < 0 => need to remove extra ')'
			for (int slow = lastSlow; slow <= fast; ++slow) {
				if (s.charAt(slow) == par[1] && (slow == lastSlow || s.charAt(slow - 1) != par[1])) {
					// (slow == lastSlow || s.charAt(slow - 1) != par[1]) is for avoiding duplicate strings in ans
					remove(s.substring(0, slow) + s.substring(slow + 1, s.length()), ans, fast, slow, par);
				}
			}
			return ans;
		}
		String reversed = new StringBuilder(s).reverse().toString();
		// check if the current 's' is reversed version
		if (par[0] == '(') { // finished left to right
			// still need to check the reversed string to make it legal
			remove(reversed, ans, 0, 0, REVERSED_PARENTHESES);
		} else { // finished right to left
			// checked both ordered and reverse ordered
			ans.add(reversed);
		}
		return ans;
	}
	
	// return first one
	public String removeInvalidParentheses2(String s) {
		return removeInvalidParentheses2(s, 0, 0, ORDERED_PARENTHESES);
	}

	private String removeInvalidParentheses2(String s, int lastFast, int lastSlow, char[] par) {
		for (int count = 0, fast = lastFast; fast < s.length(); ++fast) {
			char fastChar = s.charAt(fast);
			if (fastChar == par[0]) {
				count++;
			}
			if (fastChar == par[1]) {
				count--;
			}
			if (count >= 0) {
				continue;
			}
			// count < 0 => need to remove extra ')'
			for (int slow = lastSlow; slow <= fast; ++slow) {
				if (s.charAt(slow) == par[1] && (slow == lastSlow || s.charAt(slow - 1) != par[1])) {
					// (slow == lastSlow || s.charAt(slow - 1) != par[1]) is for avoiding duplicate strings in ans
					String t = removeInvalidParentheses2(s.substring(0, slow) + s.substring(slow + 1, s.length()), fast, slow, par);
					if (t != null) {
						return t;
					}
				}
			}
			return null;
		}
		String reversed = new StringBuilder(s).reverse().toString();
		// check if the current 's' is reversed version
		if (par[0] == '(') { // finished left to right
			// still need to check the reversed string to make it legal
			return removeInvalidParentheses2(reversed, 0, 0, REVERSED_PARENTHESES);
		} 
		// finished right to left
		// checked both ordered and reverse ordered
		return reversed;
	}

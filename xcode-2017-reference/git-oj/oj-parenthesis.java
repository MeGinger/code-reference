
	// parenthesis
	/**
	 * Valid Parentheses
	 * Given a string containing just the characters '(', ')',
	 * '{', '}', '[' and ']', determine if the input string is valid. The
	 * brackets must close in the correct order, "()" and "()[]{}" are all valid
	 * but "(]" and "([)]" are not.
	 */

	public boolean isValid(String s) {
		Stack<Character> stack = new Stack<>();
		for (char c : s.toCharArray()) {
			if (c == '(') {
				stack.push(')');
			} else if (c == '{') {
				stack.push('}');
			} else if (c == '[') {
				stack.push(']');
			} else if (stack.isEmpty() || stack.pop() != c) { // should check if stack empty to avoid exception!!!!!
				return false;
			}
		}
		return stack.isEmpty(); // cannot just return true..
	}

	public boolean isValid2(String s) {
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
				result = result * 4 + n; // append a digit to the last (rightmost)
			} else { 
				// n < 0
				if (result % 4 + n != 0) { // result % 4 and n are a pair of +/- number
					return false;
				}
				result /= 4; // remove the last digit of result (rightmost)
			}
		}

		return result == 0;
	}


        /**
	 *  Longest Valid Parentheses 
		Given a string containing just the characters '(' and ')', find the length of the longest valid (well-formed) parentheses substring.
		For "(()", the longest valid parentheses substring is "()", which has length = 2.
		Another example is ")()())", where the longest valid parentheses substring is "()()", which has length = 4. 
	 */

	// find longest valid parentheses substring	
	public int longestValidParentheses(String s) {
		int maxLen = 0, last = -1; // last is used as starting index for substring
		Stack<Integer> lefts = new Stack<>();
		for (int i = 0; i < s.length(); ++i) {
			if (s.charAt(i) == '(') {
				lefts.push(i);
				continue;
			}

			// s.charAt(i) == ')'
			if (lefts.isEmpty()) {
				// no matching left

				// last is the position of last invalid ')', which is this current ')'
				last = i;
			} else { 
				// find a matching pair
				lefts.pop();
				if (lefts.isEmpty()) {
					maxLen = Math.max(maxLen, i - last);
				} else {
					// for example "(()()" lefts.peek() is the leftmost '('
					maxLen = Math.max(maxLen, i - lefts.peek()); 
				}
			}
		}
		return maxLen;
	}


  /**
    * Remove Invalid Parentheses
	* Remove the minimum number of invalid parentheses in order to make the input string valid. Return all possible results.
	* Note: The input string may contain letters other than the parentheses ( and ).
	*
	* Examples:
	* "()())()" -> ["()()()", "(())()"]
	* "()(()()" -> ["()()()", "()(())"]
	*
	* "(a)())()" -> ["(a)()()", "(a())()"]
	* ")(" -> [""]
	*/

  /** DFS:
	* We all know how to check a string of parentheses is valid using a stack. Or even simpler use a counter.
	* The counter will increase when it is '(' and decrease when it is ')'. Whenever the counter is negative, we have more ')' than '(' in the prefix.
	*
	* To make the prefix valid, we need to remove a ')'. The problem is: which one? The answer is any one in the prefix. However, if we remove any one, we will generate duplicate results, for example: s = ()), we can remove s[1] or s[2] but the result is the same (). Thus, we restrict ourself to remove the first ) in a series of concecutive )s.  -- remove any one is the same
	*
	* After the removal, the prefix is then valid. We then call the function recursively to solve the rest of the string. However, we need to keep another information: the last removal position. If we do not have this position, we will generate duplicate by removing two ')' in two steps only with a different order.
	* For this, we keep tracking the last removal position and only remove ')' after that.
	*
	* Now one may ask. What about '('? What if s = "(()(()" in which we need remove '('?
	* The answer is: do the same from right to left.
	* However a cleverer idea is: reverse the string and reuse the code!
	* O(n)
	*/
	
	// FB, Whatsapp
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
				continue; // 
			}
			// count < 0 => need to remove extra ')'
			for (int slow = lastSlow; slow <= fast; ++slow) {
				// the below condition satisfies when first ')' comes by from slow index, like the below 2 indices:
				// "()())()"
				//   |  |
				//  idx idx
				// first round: (())()
				// second round: ()()()
				if (s.charAt(slow) == par[1] && (slow == lastSlow || s.charAt(slow - 1) != par[1])) {
					// (slow == lastSlow || s.charAt(slow - 1) != par[1]) is for avoiding duplicate strings in ans
					
					// eliminate s.charAt(slow)
					remove(s.substring(0, slow) + s.substring(slow + 1, s.length()), ans, fast, slow, par);
				}
			}
			return ans; // if only having extra ')', early return
						// if having extra both ')' and '(', also return here in the perspective of parent function
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
		return ans; // if only having extra '(', return here
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

	/**
	 * Longest Valid Parentheses 
	   Given a string containing just the characters '(' and ')',++ find the length of the longest valid (well-formed) parentheses substring.
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
				
				// ')' or ')...)'
				last = i; // last is always updated as the last invalid ')'
			} else {
				// found a matching pair
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
				result = result * 4 + n; // {( + [ -> {([ append to end 
			} else {
				// n < 0
				if (result % 4 + n != 0) { // grad the last edit and check if it is same type of parenthesis
					return false;
				} 
				result /= 4; // extract the last edit out 
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
		return stack.isEmpty(); // string s may end up with some left parentheses to be paired
	}


	Number of Atoms

	public class Solution {
    
    // K4(ON(SO3)2)2
    // Mg
    // K, O, N, S, O
    public String countOfAtoms(String formula) {
        if (formula == null || formula.length() == 0) {
            return "";
        }
        
        int n = formula.length(), i = 0;
        
        Stack<Map<String, Integer>> stack = new Stack<>();
        Map<String, Integer> map = new HashMap<>(); 
        // stores mapping between
        // an element to its frequency in the current layer (parenthesis)
        
        while (i < n) {
            char c = formula.charAt(i++);
            
            if (c == '(') {
                stack.push(map); // last layer map
                map = new HashMap<>(); // this layer map
                continue;
            } 
            
            if (c == ')') {
                int val = 0;
                while (i < n && Character.isDigit(formula.charAt(i))) {
                    val = val * 10 + formula.charAt(i++) - '0';
                }
                // no digit
                // if (val == 0) {
                //     val = 1;
                // }
                if (!stack.isEmpty()) {
                    // ..prev..(..map..)val
                    Map<String, Integer> prev = stack.pop(); // last layer map
                    for (String key : map.keySet()) {
                        prev.put(key, prev.getOrDefault(key, 0) 
                                      + map.get(key) * val);
                    }
                    map = prev;
                }
                continue;
            } 
            
            int start = i - 1; // since we increase i by 1 above.
            // a list of characters
            while (i < n && Character.isLowerCase(formula.charAt(i))){
                i++;
            } 
            String s = formula.substring(start, i);
            
            
            int val = 0;
            while (i < n && Character.isDigit(formula.charAt(i))) {
                val = val * 10 + formula.charAt(i++) - '0';
            }
            // no digit
            if (val == 0) {
                val = 1;
            }
            
            map.put(s, map.getOrDefault(s, 0) + val);
        }
        
        StringBuilder sb = new StringBuilder();
        List<String> list = new ArrayList<>(map.keySet());
        Collections.sort(list);
        for (String key : list) {
            sb.append(key);
            if (map.get(key) > 1) {
                sb.append(map.get(key));
            }
        }
        
        return sb.toString();
    }
}

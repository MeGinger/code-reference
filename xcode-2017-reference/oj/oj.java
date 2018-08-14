	/**
	 * Rearrange String k Distance Apart
	 Given a non-empty string s and an integer k, rearrange the string such that the same characters are at least distance k from each other.
	 All input strings are given in lowercase letters. If it is not possible to rearrange the string, return an empty string "".
	 
	 Example 1:
	 s = "aabbcc", k = 3
	 Result: "abcabc"
	 The same letters are at least distance 3 from each other.
	 
	 Example 2:
	 s = "aaabc", k = 3
	 Answer: ""
	 It is not possible to rearrange the string.
	 
	 Example 3:
	 s = "aaadbbcc", k = 2
	 Answer: "abacabcd"
	 
	 Another possible answer is: "abcabcda"
	 The same letters are at least distance 2 from each other.
	 
	 Reference: task scheduler with cool down time https://discuss.leetcode.com/topic/112/minimal-run-time-scheduler/7
	 
	 This is a greedy problem.
	 Every time we want to find the best candidate: which is the character with the largest remaining count. Thus we will be having two arrays.
	 One count array to store the remaining count of every character. Another array to keep track of the most left position that one character can appear.
	 We will iterated through these two array to find the best candidate for every position. Since the array is fixed size, it will take constant time to do this.
	 After we find the candidate, we update two arrays.
	 */

	// thought: first count the frequency of each character in input string (using data structure map)
	// then iterate from index 0 to length - 1, in each loop find the next valid character in this current position which ...
	// "valid" means the distance requirement should be satisfied
	// if not found, means cannot rearrange, return ""
	// otherwise collect and append the valid character to the result.

	// distance is different from interval: distance = interval + 1 
	// space is not allowed, in that case return ""
	public String rearrangeString(String str, int k) {
		int length = str.length();
		// count frequency of each char
		// different from int[26], more general!
		Map<Character, Integer> count = new HashMap<>();
		for (char c : str.toCharArray()) {
			count.put(c, count.getOrDefault(c, 0) + 1);
		}

		// no sorting involved - findValidMax(..) will do the job

		// what valid map does?
		// store character as key and its next valid index as value
		// e.g., "AABBCC", distance = 3
		// "AxxA"
		//  |  |
		//  0123  
		// If A is placed at the index of 0, the next valid index for A will be 3
		// means the next A shoud be placed at index 3 or more.
		// getByDefault(x, 0) - default index is 0
		Map<Character, Integer> valid = new HashMap<>();
		StringBuilder sb = new StringBuilder();
		for (int index = 0; index < length; index++) {
			char candidate = findValidMax(count, valid, index);
			if (candidate == ' ') {
				return "";
			}

			// update count map
			int frequency = count.get(candidate) - 1;
			if (frequency == 0) {
				count.remove(candidate);
			} else {
				count.put(candidate, frequency);
			}


			valid.put(candidate, index + k);
			sb.append(candidate);
		}
		return sb.toString();
	}
	
	// find the next character candidate which is valid to placed at index index
	// and also has max count - handle the max first!
	private char findValidMax(Map<Character, Integer> count, Map<Character, Integer> valid, int index) {
		int max = Integer.MIN_VALUE;
		char candidate = ' ';
		for (Map.Entry<Character, Integer> entry : count.entrySet()) {
			if (entry.getValue() > max && index >= valid.getOrDefault(entry.getKey(), 0)) {
				max = entry.getValue();
				candidate = entry.getKey();
			}
		}
		return candidate;
	}


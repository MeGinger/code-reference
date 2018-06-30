/**
	 * Word Break
*Given a non-empty string s and a dictionary wordDict containing a list of non-empty words,
*determine if s can be segmented into a space-separated sequence of one or more dictionary words.
*You may assume the dictionary does not contain duplicate words.
*
*For example, given
*s = "leetcode",
*dict = ["leet", "code"].
*Return true because "leetcode" can be segmented as "leet code". 
	 */
	public boolean wordBreak(String s, Set<String> dict) {
		if (s == null || s.length() == 0 || dict.isEmpty()) {
			return false;
		}
		// state[i] is true if input.substring(0, i+1) can be broken into words
		List<Integer> breakableIndex = new ArrayList<>(s.length());

		for (int i = 0; i < s.length(); i++) {
			if (dict.contains(s.substring(0, i + 1))) {
				breakableIndex.add(i);
				continue;
			}
			// look for previous string segment that can be broken into
			// words
			Iterator<Integer> iter = breakableIndex.iterator();
			while (iter.hasNext()) {
				if (dict.contains(s.substring(iter.next() + 1, i + 1))) {
					// input.substring(0, j+1) can be broken into words
					breakableIndex.add(i);
					break;
				}
			}
		}

		return !breakableIndex.isEmpty() && breakableIndex.get(breakableIndex.size() - 1) == (s.length() - 1);
	}


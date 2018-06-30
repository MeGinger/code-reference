	/**
	 * Word Break II
*Given a non-empty string s and a dictionary wordDict containing a list of non-empty words,
*add spaces in s to construct a sentence where each word is a valid dictionary word.
*You may assume the dictionary does not contain duplicate words.
*Return all such possible sentences.
*For example, given
*s = "catsanddog",
*dict = ["cat", "cats", "and", "sand", "dog"].
*A solution is ["cats and dog", "cat sand dog"]. 
	 */
	public List<String> wordBreak2(String s, Set<String> dict) {
		return breakIntoWordList(s, dict, new HashMap<>());
	}

	private List<String> breakIntoWordList(String s, Set<String> dict, Map<String, List<String>> memorized) {
		List<String> result = new ArrayList<>();
		if (s == null || s.length() == 0) {
			return result;
		}

		if (memorized.containsKey(s)) {
			return memorized.get(s);
		}

		for (int i = 1; i <= s.length(); i++) {
			String prefix = s.substring(0, i);
			if (!dict.contains(prefix)) {
				continue;
			}
			if (i == s.length()) { // whole string is word
				result.add(s);
				memorized.put(s, result);
				return result;
			}

			String suffix = s.substring(i, s.length());
			List<String> segSuffix = breakIntoWordList(suffix, dict, memorized);
			for (String suffixWord : segSuffix) {
				result.add(prefix + " " + suffixWord);
			}
		}

		memorized.put(s, result);
		return result;
	}
import java.util.*;


/**
	 * Word Ladder
	 Given two words (beginWord and endWord), and a dictionary's word list, find the length of shortest transformation sequence from beginWord to endWord, such that:

    Only one letter can be changed at a time.
    Each transformed word must exist in the word list. Note that beginWord is not a transformed word.

For example,
Given:
beginWord = "hit"
endWord = "cog"
wordList = ["hot","dot","dog","lot","log","cog"]

As one shortest transformation is "hit" -> "hot" -> "dot" -> "dog" -> "cog",
return its length 5.

Note:

    Return 0 if there is no such transformation sequence.
    All words have the same length.
    All words contain only lowercase alphabetic characters.
    You may assume no duplicates in the word list.
    You may assume beginWord and endWord are non-empty and are not the same.
    
    Reference https://discuss.leetcode.com/topic/29303/two-end-bfs-in-java-31ms/27
	 */
	public int ladderLength(String beginWord, String endWord, List<String> wordList) {
		Set<String> dict = new HashSet<>(wordList);
		if (!dict.contains(endWord)) {
			return 0;
		}
		// Two-end BFS is much faster than one-end BFS
		Set<String> set1 = new HashSet<>();
		set1.add(beginWord);
		Set<String> set2 = new HashSet<>();
		set2.add(endWord);

		int len = 2;
		while (true) {
			if (set1.size() > set2.size()) {
				// swap set1 and set2
				Set<String> set = set1;
				set1 = set2;
				set2 = set;
			}

			// set1.size() <= set2.size()
			if (set1.isEmpty()) {
				break;
			}
			// set for the next level
			Set<String> set = new HashSet<>();
			for (String str : set1) {
				for (String word : getOneEditWords(str)) {
					if (set2.contains(word)) {
						return len;
					}
					if (dict.contains(word)) {
						set.add(word);
						dict.remove(word);
					}
				}
			}

			set1 = set;
			len++;
		}
		return 0; // no path connect 'start' and 'end'
	}

	// return all the possible words of one-edit character of given word
	// return iterator with lazy return or just inline this function will save space
	private Set<String> getOneEditWords(String str) {
		Set<String> words = new HashSet<>();
		for (int i = 0; i < str.length(); i++) {
			for (char c = 'a'; c <= 'z'; c++) {
				String tmp = str.substring(0, i) + c + str.substring(i + 1, str.length());
				words.add(tmp);
			}
		}
		return words;
	}

	/**
	 * Word Ladder II
	 * Graph of example: |--- dot --- dog ---| hit --- hot -- | |
	 *                   | |--- cog |--- lot --- log ---|
	 * 
	 * backward adjacent list: 
	 *  hit => hot => dot => dog => cog 
	 *             => lot => log
	 * Given two words (start and end), and
	 * a dictionary, find all shortest transformation sequence(s) from start to
	 * end, such that:
	 * 
	 * Only one letter can be changed at a time Each intermediate word must
	 * exist in the dictionary
	 * 
	 * For example,
	 * 
	 * Given: start = "hit" end = "cog" dict = ["hot","dot","dog","lot","log"]
	 * 
	 * Return
	 * 
	 * [ ["hit","hot","dot","dog","cog"], ["hit","hot","lot","log","cog"] ]
	 * 
	 * Note:
	 * 
	 * All words have the same length. All words contain only lowercase
	 * alphabetic characters.
	 */
	// two-end BFS
	public List<List<String>> findLadders(String beginWord, String endWord, List<String> wordList) {
		Set<String> dict = new HashSet<>(wordList);
		if (!dict.contains(endWord)) {
			return Collections.emptyList();
		}
		// hash set for both ends
		Set<String> set1 = new HashSet<>();
		Set<String> set2 = new HashSet<>();

		// initial words in both ends
		set1.add(beginWord);
		set2.add(endWord);

		// we use a map to help construct the final result
		Map<String, List<String>> backtrace = new HashMap<>();
		if (!findLadders(dict, set1, set2, backtrace, false)) {
			return Collections.emptyList();
		}
		return generateList(beginWord, endWord, backtrace, new ArrayList<>(), new ArrayList<>());
	}

	private boolean findLadders(Set<String> dict, Set<String> set1, Set<String> set2, Map<String, List<String>> backtrace, boolean flip) {
		if (set1.size() > set2.size()) {
			return findLadders(dict, set2, set1, backtrace, !flip);
		}

		// set1.size() <= set2.size()
		if (set1.isEmpty()) {
			return false;
		}

		// remove words on current both ends from the dict
		dict.removeAll(set1);
		dict.removeAll(set2);

		// as we only need the shortest paths
		// we use a boolean value help early termination
		boolean done = false;
		// set for the next level
		Set<String> set = new HashSet<>();
		for (String str : set1) {
			for (String word : getOneEditWords(str)) {
				// make sure we construct the tree in the correct direction
				String key = flip ? word : str;
				String val = flip ? str : word;

				if (set2.contains(word)) {
					done = true;
					backtrace.computeIfAbsent(key, k -> new ArrayList<>()).add(val);
				}

				if (!done && dict.contains(word)) {
					set.add(word);
					backtrace.computeIfAbsent(key, k -> new ArrayList<>()).add(val);
				}
			}
		}

		// early terminate if done is true
		return done || findLadders(dict, set2, set, backtrace, !flip);
	}

	private List<List<String>> generateList(String start, String end, Map<String, List<String>> map, List<String> sol,
			List<List<String>> res) {
		if (start.equals(end)) {
			sol.add(start);
			res.add(new ArrayList<>(sol));
			sol.remove(sol.size() - 1);
			return res;
		}

		List<String> words = map.get(start);
		if (words == null) {
			return res;
		}

		sol.add(start);
		for (String word : words) {
			generateList(word, end, map, sol, res);
		}
		sol.remove(sol.size() - 1);
		return res;
	}

	public List<List<String>> findLadders2(String beginWord, String endWord, List<String> wordList) {
		Set<String> dict = new HashSet<>(wordList);
		if (!dict.contains(endWord)) {
			return Collections.emptyList();
		}

		int curr = 1, next = 0;
		boolean found = false;
		Map<String, List<String>> map = new HashMap<>();

		Queue<String> queue = new ArrayDeque<>();
		Set<String> visited = new HashSet<>();

		queue.add(beginWord);
		dict.remove(beginWord);
		// BFS
		while (!queue.isEmpty()) {
			String word = queue.poll();
			curr--;
			for (String newWord : getOneEditWords(word, dict)) {
				// Key statement, avoid Duplicate queue insertion
				if (visited.add(newWord)) { 
					next++;
					queue.add(newWord);
				}

				map.computeIfAbsent(newWord, k -> new LinkedList<>()).add(word);
				if (newWord.equals(endWord)) {
					found = true;
				}
			}
			if (curr == 0) {
				if (found) {
					break;
				}
				curr = next;
				next = 0;
				dict.removeAll(visited);
				visited.clear();
			}
		} // End While

		return backTrace(endWord, beginWord, new LinkedList<>(), new ArrayList<>(), map);
	}

	// return all the possible words of one-edit character of given word
	// return iterator with lazy return or just inline this function will save
	// space
	private Set<String> getOneEditWords(String str, Set<String> dict) {
		Set<String> words = new HashSet<>();
		for (int i = 0; i < str.length(); i++) {
			for (char c = 'a'; c <= 'z'; c++) {
				String tmp = str.substring(0, i) + c + str.substring(i + 1, str.length());
				if (dict.contains(tmp)) {
					words.add(tmp);
				}
			}
		}
		return words;
	}

	private List<List<String>> backTrace(String word, String start, List<String> list, List<List<String>> results,
			Map<String, List<String>> map) {
		if (word.equals(start)) {
			list.add(0, start);
			results.add(new ArrayList<>(list));
			list.remove(0);
			return results;
		}
		List<String> l = map.get(word);
		if (l == null) {
			return results;
		}
		list.add(0, word);
		for (String s : map.get(word)) {
			backTrace(s, start, list, results, map);
		}
		list.remove(0);
		return results;
	}
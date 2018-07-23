search-bfs

* Word Ladder (https://leetcode.com/problems/word-ladder/)
* Word Ladder II (https://leetcode.com/problems/word-ladder-ii/)

search-dfs

* Word Search (https://leetcode.com/problems/word-search)
* Word Search II (https://leetcode.com/problems/word-search-ii)

dp

* Word Break (https://leetcode.com/problems/word-break)

recursion

* Word Break II (https://leetcode.com/problems/word-break-ii)

```java
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
```
  
```java
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
```

```java
  /**
	 * Word Search
	 * Given a 2D board and a word, find if the word exists in the grid.
	 * 
	 * The word can be constructed from letters of sequentially adjacent cell,
	 * where "adjacent" cells are those horizontally or vertically neighboring.
	 * The same letter cell may not be used more than once.
	 * 
	 * For example, Given board =
	 * 
	 * [ ["ABCE"], ["SFCS"], ["ADEE"] ]
	 * 
	 * word = "ABCCED", -> returns true, word = "SEE", -> returns true, word =
	 * "ABCB", -> returns false.
	 */
	public boolean exist(char[][] board, String word) {
		if (board.length == 0 || word.length() == 0) {
			return false;
		}
		int m = board.length;
		int n = board[0].length;
		for (int i = 0; i < m; i++) {
			for (int j = 0; j < n; j++) {
				if (exist(board, word, 0, i, j)) {
					return true;
				}
			}
		}
		return false;
	}

	// based on [x, y], search neighbors of char at index of word
	private boolean exist(char[][] board, String word, int i, int x, int y) {
		if (i == word.length()) {
			return true;
		}
		if (x < 0 || y < 0 || x == board.length || y == board[0].length) {
			return false;
		}
		char old = board[x][y];
		if (old != word.charAt(i)) {
			return false;
		}
		board[x][y] = '#';
		for (int[] dir : dirs) {
			if (exist(board, word, i + 1, x + dir[0], y + dir[1])) {
				board[x][y] = old;
				return true;
			}
		}
		board[x][y] = old;
		return false;
	}
```

```java
    /**
      * Word Search II
      *  Given a 2D board and a list of words from the dictionary, find all words in the board.
      *Each word must be constructed from letters of sequentially adjacent cell, where "adjacent" cells are those horizontally or vertically neighboring. The same letter cell may not be used more than once in a word.
      *
      *For example,
      *Given words = ["oath","pea","eat","rain"] and board *=
      *[
      * ['o','a','a','n'],
      * ['e','t','a','e'],
      * ['i','h','k','r'],
      * ['i','f','l','v']
      *]
      *
      *Return ["eat","oath"].
      *You may assume that all inputs are consist of lowercase letters a-z.
      */
	public List<String> findWords(char[][] board, String[] words) {
		List<String> result = new ArrayList<>();

		// build Trie
		Trie trie = new Trie();
		for (String word : words) {
			trie.insert(word);
		}

		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board[0].length; j++) {
				dfsFindWords(board, result, trie, null, i, j);
			}
		}

		return result;
	}

	private void dfsFindWords(char[][] board, List<String> result, Trie trie, TrieNode node, int x, int y) {
		char c = board[x][y];
		TrieNode nextNode = trie.searchNextNode(node, c);
		if (nextNode == null) {
			return;
		}
		if (nextNode.word != null) {
			result.add(nextNode.word);
			nextNode.word = null; // de-duplicate
			// trie.delete(nextNode.word);
		}
		board[x][y] = '#';
		for (Point neighbor : getWordSearchNeighbors(board, x, y)) {
			dfsFindWords(board, result, trie, nextNode, neighbor.x, neighbor.y);
		}
		board[x][y] = c;
	}

	private List<Point> getWordSearchNeighbors(char[][] board, int x, int y) {
		List<Point> neighbors = new ArrayList<>(4);
		for (Point candidate : new Point[] { new Point(x - 1, y), new Point(x + 1, y), new Point(x, y - 1),
				new Point(x, y + 1) }) {
			if (0 <= candidate.x && candidate.x < board.length && 0 <= candidate.y && candidate.y < board[0].length
					&& board[candidate.x][candidate.y] != '#') {
				neighbors.add(candidate);
			}
		}

		return neighbors;
	}
```

```java
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
```

```java
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
```

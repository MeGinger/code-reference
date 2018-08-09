// tasks: a bunch of tasks to be executed
// n: the minimal intervals between two same tasks to be executed in order
public void leastIntervals(char[] tasks, int n) {
  int[] c = new int[26]; // assume that tasks are only represented as A to Z
  for (char task : tasks) {
      c[task - 'A']++;
  }
  Arrays.sort(c); // !
  int index = c.length - 1;
  while (index >= 0 && c[index] == c[c.length - 1]) {
    index--;
  }
  
  return Math.max(tasks.length, (c[c.length - 1] - 1) * (n + 1) + c.length - 1 - index);
}

9 lines (35 sloc)  1.26 KB
/**
*Word Break
*Given a non-empty string s and a dictionary wordDict containing a list of non-empty words,
*determine if s can be segmented into a space-separated sequence of one or more dictionary words.
*You may assume the dictionary does not contain duplicate words.
*
*For example, given
*s = "leetcode",
*dict = ["leet", "code"].
*Return true because "leetcode" can be segmented as "leet code". 
*/


// input: s = "leetcoder", dict = ["leet", "code"]
// thought: scan from left to right, check whether substring/prefix of s from index 0 to i 
// either is a single word in dictionary (substring "leet")
// or can be segmeneted into multiple words in dictionary (substring "leetcode") 
// record every such index i 
// finally, we check if index (last index of input string s -> s.length() - 1) is present

// dynamic programming
public boolean wordBreak(String s, Set<String> dict) {
	// invalid input - "leetcode"
	if (a == null || s.isEmpty()) {
		return false; 
	}
	
	// index in this list is to indicate string s[0...i] can be segmented into words in dictionary
	List<Integer> breakableIndex = new ArrayList<>(s.length());
	
	// scan string s from left to right
	for (int i = 0; i < s.length(); i++) {
		// check whether substring of s from index 0 to i is in dict 
		// if it is, it can be segmented into a single word in dictionary		
		if (dict.contains(s.substring(0, i + 1)) {
			breakableIndex.add(i);
			continue;
		}
		
		// if not, check if the substring can be break into multiple words
		Iterator<Integer> itr = breakableIndex.iterator();
		while (itr.hasNext()) {
			if (dict.contains(s.substring(itr.next() + 1, i + 1))) {
				breakableIndex.add(i);
				break;
			}
		}
	}
		
    // check if the last index in breakableIndex is equal to s.length() - 1
	// as mentione before, the index is used to indicate ....
	// which means the string s can be segmented into words in dictionary
	return !breakableIndex.isEmpty() && breakableIndex.get(breakableIndex.size() - 1) == (s.lenght() - 1);
}

// runtime
// best: O(n)
// worst: O(n^2)


/**
*Word Break II
*Given a non-empty string s and a dictionary wordDict containing a list of non-empty words,
*add spaces in s to construct a sentence where each word is a valid dictionary word.
*You may assume the dictionary does not contain duplicate words.
*Return all such possible sentences.
*For example, given
*s = "catsanddog",
*dict = ["cat", "cats", "and", "sand", "dog"].
*A solution is ["cats and dog", "cat sand dog"]. 
*/

// input: s = "catsanddog"
// thought: 
// So i should implement a method that we can get a sentence list where each sentence is constructed by valid dictionary words separated by space
// The basic idea is to scan input string from left to right, check if prefix ["cat"] of s from index 0 to i is a dictionary word
// if it is, do recursion to get a sentence list of the suffix ["sanddog"], then we append each sentence to prefix 
// to get the sentence list that corresponds to string s.

// recursion
public List<String> wordBreak2(String s, Set<String> dict) {
	return breakIntoWordList(s, dict, new HashMap<>());
}

// What method breakIntoWordList does?
// return a sentence list where each sentence is constructed by valid dictionary words seperated by space

// memorized (
//	 key: string, 
//   value: a list of sentences 
//          where each sentence is constructed by valid dictionary words seperated by space
// )
private breakIntoWordList(String s, Set<String> dict, Map<String, List<String>> memorized) {
	// result: a list of sentences
	// finally result is added to memorized along with s
	List<String> result = new ArrayList<>();
	if (s == null || s.length() == 0) {
		return result;
	}
	
	if (memorized.containsKey(s)) {
		return memorized.get(s);
	}
	
	for (int i = 1; i <= s.length(); i++){
		String prefix = s.substring(0, i);
		if (!dict.contains(prefix) {
			continue;
		}
		
		// i: s.length() -> no suffix
		if (i == s.length()) {
			result.add(s);
			
			// can we change these two lines to continue/break
			continue;
			// memorized.put(s, result);
			// return result;
		}
		
		// i: 1 to s.length() - 1 -> suffix
		String suffix = s.substring(i, s.length());
		List<String> segSuffix = breakIntoWordList(suffix, dict, memorized);
		for (String suffixWord : segSuffix) {
			result.add(prefix + " " + suffixWord);
		}
	}
	
	memorized.put(s, result);
	return result;
}
// runtime: O(n^2) 
// best: O(n)
// example intput - all prefix are not dictionary words - no recursion happens
// s: "aaaaaaaaaaaaaaaaaa"
// dict: ["b"]

// worst: O(n^2)
// example intput - every prefix is dicionary word 
// s: "aaaaaaaaaaaaaaaaaa"
// dict: ["a"]
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

// thought: scan the matrix from left to right, row by row from up to bottom,
// check whether we can start from point (i, j) can search the word
// how to check? I use depth-first search from the starting point, 
// check if the character of the current point matches with that of the corresponding position of the word
// if not we can just return false; if it is, we continue to iterate the left, right, upper and lower points 
// and do the recursion
// finally if we reach out of the index of the word, i.e., word.length(), means the word is found.
public boolean exists(char[][] board, String word) {
	if (board.length == 0 || word.length() == 0) {
		return false;
	}
	
	int m = board.length;
	int n = board[0].length;
	for (int i = 0; i < m; i++) {
		for (int j = 0; j < n; j++) {
			if (exists(board, word, 0, i, j)) { // start point (i, j)
				return true;
			}
		}
	}
	return false;
}

// this dir two-dimensional array is used to
// simplify the traversal of left, right, upper and lower nodes/points in a matrix
//          (x-1, y) 
// (x, y-1) (x  , y) (x, y+1)
//          (x+1, y)  
private static final int[][] dir = new int[][]{
											    {0, 1},
											    {0, -1},
											    {1, 0},
											    {-1, 0}
											  };

// this exists method check
// whether substring of word from index i to the end can be found in the board starting from point (x, y) 											   
private boolean exists(char[][] board, String word, int i, int x, int y) {
	// found
	if (i == word.length()) {
		return true;
	} 
	// out of boundary
	if (x < 0 || y < 0 || x == board.length || y == board[0].length) {
		return false;
	}
	char old = board[x][y];
	// not found
	if (old != word.charAt(i)) {
		return false;
	}
	board[x][y] = '#'; // avoid traversal back to visited position
	for (int[] dir : dirs) {
		if (exists(board, word, i + 1, x + dir[0], y + dir[1])) {
			board[x][y] = old; // restore before method ends
			return true;
		}
	}
	board[x][y] = old // restore before method ends
	return false; 
}

// run time:
// best:
// worst: O(n^2 * 4^(n^2))

/**
*Word Search II
*Given a 2D board and a list of words from the dictionary, find all words in the board.
*Each word must be constructed from letters of sequentially adjacent cell, where "adjacent" cells are those horizontally or vertically neighboring. The same letter cell may not be used more than once in a word.
*
*For example,
*Given words = ["oath","pea","eat","rain"] and board
*=
*[
*['o','a','a','n'],
*['e','t','a','e'],
*['i','h','k','r'],
*['i','f','l','v']
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
		for (int j = 0; j < board.length[0]; j++) {
			dfsFindWords(board, result, trie, null, i, j);
		}
	}
	
	return result;
}

private void dfsFindWords(char[][] board, List<String> result, Trie trie, TrieNode node, int x, int y) {
	char c = board[x][y];
	TrieNode nextNode = trie.searchNextNode(node, c);
	// if not found
	if (nextNode == null) {
		return; // early return if there is no matching dictionary word
	}
	// if found
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

// runtime:
// best: 
// worst: 

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

// thought: Use double-ended breath-first search for traversal.
// Use two sets to maintain transformed words at the initial state where we only have beginWord,
// and at the final state where we have endWord.
// In each time of traversal, I work on the set which is smaller.
// iterate every word in the smaller set, get all possible one-edit-words
// if the other set contains one of the words, indicate we found
// otherwise we will add the valid dictionary word into a new set, which will be next-level set. 
// do this util we cannot find any transformed words

// ladder length: the count of nodes 
public int ladderLength(String beginWord, String endWord, List<String> wordList) {
	Set<String> dict = new HashSet<>(wordList);
	// corner case
	if (!dict.contains(endWord)) {
		return 0;
	}
	
	// Two-end BFS - work on the set which is smaller each time
	Set<String> set1 = new HashSet<>();
	set1.add(beginWord);
	Set<String> set2 = new HashSet<>();
	set2.add(endWord);
	
	int len = 2; // beginWord -> endWord, assume beginWord and endWord are non-empty and are not the same.
	while (true) {
		if (set1.size() > set2.size()) {
			Set<String> temp = set1;
			set1 = set2;
			set2 = temp;
		}
		// set1.size() <= set2.size() - make sure working on the set which is smaller
		
		
		if (set1.isEmpty()) {
			break;
		}
		
		// set for the next level
		Set<String> set = new HashSet<>();
		for (String str : set1) {
			for (String word : getOneEditWords(str)) {
				if (set2.contains(word)) { // found
					return len;
				}
				if (dict.contains(word)) {
					set.add(word);
					dict.remove(word); // de-duplicate
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

// runtime: 
// best:
// worst: 


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

// find all shortest transformation sequence(s) from start to end
// two-end BFS
public List<List<String>> findLadders(String beginWord, String endWord, List<String> wordList) {
	Set<String> dict = new HashSet<>(wordList);
	if (!wordList.contains(endWord)) {
		return Collections.emptyList(); // !!!!!!
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
	
	return generateList(beingWord, endWord, backtrace, new ArrayList<>(), new ArrayList<>());
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
				// not early terminate here - find all possible shortest paths
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

// backtrace/dfs
// find all the paths from startWord to endWord, given by map (represent the connection between 
// words - key is a word and value is a list of dicionary words that key word is transformed into)
// and those paths are considered to be shortest, since we use early termination once done.
private List<List<String>> generateList(String start, String end, Map<String, List<String>> map,
        List<String> path, 
		List<List<String>> res) {
	if (start.equals(end)) { // one path is found - early return
		path.add(start); // add
		res.add(new ArrayList<>(path));
		path.remove(path.size() - 1); // restore
		return res;
	}

	List<String> words = map.get(start);
	if (words == null) {
		return res;
	}
 
	path.add(start); // add
	for (String word : words) {
		generateList(word, end, map, path, res);
	}
	path.remove(path.size() - 1); // restore
	return res;
}
	
private static final char[] charSet = new char[] {'A', 'C', 'G', 'T'};

// mutation distance: the count of edges = ladder length - 1
public int findMutationDistance(String start, String end, String[] bank) {
	Set<String> dict = new HashSet<>(Arrays.asList(bank);
	if (!dict.contains(end)) {
		return -1;
	}
	
	Set<String> set1 = new HashSet<>();
	set1.add(start);
	Set<String> set2 = new HashSet<>();
	set2.add(end);
	
	int len = 1;
	while (true) {
		if (set1.size() > set2.size()) {
			Set<String> temp = set1;
			set1 = set2;
			set2 = temp;
		}
		
		if (set1.isEmpty()) {
			break;
		}
			
		HashSet<String> set = new HashSet<>();
		for (String str : set1) {
			for (String mutation : getOneEditWord(str)) {
				if (set2.contains(mutation)) {
					return len;
				}
				
				if (bank.contains(mutation)) {
					set.add(mutation);
					bank.remove(mutation);
				}
			}
		}
		
		set1 = set;
		len++;
	}
	
	return -1;
}

// return all the possible words of one-edit character of given word
// return iterator with lazy return or just inline this function will save space
public static Set<String> getOneEditWord(String str) {
	Set<String> result = new ArrayList<>();
	for (int i = 0; i < str.length(); i++) {
		for (char c : charSet) {
			String tmp = str.substring(0, i) + c + str.substring(i + 1, str.length());
			words.add(tmp);
		}
	}
			
	return result;
}























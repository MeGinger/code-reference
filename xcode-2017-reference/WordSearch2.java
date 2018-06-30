    /**
     * Word Search II
     *  Given a 2D board and a list of words from the dictionary, find all words in the board.
*Each word must be constructed from letters of sequentially adjacent cell, where "adjacent" cells are those horizontally or vertically neighboring. The same letter cell may not be used more than once in a word.
*
*For example,
*Given words = ["oath","pea","eat","rain"] and board *=
*[
 * ['o','a','a','n'],
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
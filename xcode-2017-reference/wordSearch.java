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

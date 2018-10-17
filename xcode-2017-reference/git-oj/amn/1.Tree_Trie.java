Implement Trie (Prefix Tree)

class TrieNode {
    Map<Character, TrieNode> children;
    Set<String> words;
    
    public TrieNode() {
        children = new HashMap<>();
        words = new HashSet<>();
    }
}
public class Trie {
    TrieNode root;
    
    /** Initialize your data structure here. */
    public Trie() {
        this.root = new TrieNode();
    }
    
    /** Inserts a word into the trie. */
    public void insert(String word) {
        TrieNode cur = this.root;
        for (int i = 0; i < word.length(); i++) {
            cur = cur.children.computeIfAbsent(word.charAt(i), k -> new TrieNode());
            cur.words.add(word);
        }
    }
    
    /** Returns if the word is in the trie. */
    public boolean search(String word) {
        TrieNode cur = this.root;
        for (int i = 0; i < word.length(); i++) {
            cur = cur.children.get(word.charAt(i));
            
            if (cur == null) {
                return false;
            }
        }
        
        return cur.words.contains(word);
    }
    
    /** Returns if there is any word in the trie that starts with the given prefix. */
    public boolean startsWith(String prefix) {
        TrieNode cur = this.root;
        for (int i = 0; i < prefix.length(); i++) {
            cur = cur.children.get(prefix.charAt(i));
            
            if (cur == null) {
                return false;
            }
        }
        
        return !cur.words.isEmpty();
    }
}

Word Search II
Given a 2D board and a list of words from the dictionary, find all words in the board.

Each word must be constructed from letters of sequentially adjacent cell, where "adjacent" cells are those horizontally or vertically neighboring. The same letter cell may not be used more than once in a word.




Word Search
class Solution {    
    
    private static final int[][] dirs = new int[][] {
        {0, 1}, {0, -1}, {1, 0}, {-1, 0}
    };
    
    public boolean exist(char[][] board, String word) {
        if (board == null || board.length == 0 || board[0].length == 0) {
            return true;
        }
        
        int r = board.length;
        int c = board[0].length;
        
        for (int i = 0; i < r; i++) {
            for (int j = 0; j < c; j++) {
                if (exist(board, word, 0, i, j)) {
                    return true;
                }
            }
        }
        return false;
    }
    
    private boolean exist(char[][] board, String word, 
                          int index, int x, int y) {
        if (index == word.length()) { // must be first! otherwise, arrayindexoutofbound exception
            return true; // the only one exit for true!!!
                         // possibly, the i and j are out of bound here.
        } 
        
        if (x < 0 || y < 0 || x >= board.length || y >= board[0].length) {
            return false;
        }
        
        char old = board[x][y];
        if (old != word.charAt(index)) {
            return false;
        } 
        
        board[x][y] = '#';
        for (int[] dir : dirs) {
            if (exist(board, word, index + 1, x + dir[0], y + dir[1])) {
                return true;
            }
        }
        board[x][y] = old;
        return false;
    }

    private boolean exist(char[][] board, int i, int j, int index, String word) {
        char old = board[i][j];
        if (old != word.charAt(index)) {
            return false;
        }
        
        if (index == word.length() - 1) {
            return true;
        }
        
        board[i][j] = '#';
        
        for (int[] dir : dirs) {
            int x = i + dir[0];
            int y = j + dir[1];
            
            if (x < 0 || x >= board.length || y < 0 || y >= board[0].length || board[x][y] == '#') {
                continue;
            }
            
            if (exist(board, x, y, index + 1, word)) {
                return true;
            }
        }
        
        board[i][j] = old;
        
        return false;
    }
}



Add and Search Word - Data structure design

class TrieNode {
    Map<Character, TrieNode> children;
    boolean isWord;
    
    public TrieNode() {
        this.children = new HashMap<>();
    }
}

public class WordDictionary {
    
    private TrieNode root;

    public WordDictionary() {
        this.root = new TrieNode();
    }
    
    public void addWord(String word) {
        TrieNode cur = root;
        for (int i = 0; i < word.length(); i++) {
            cur = cur.children.computeIfAbsent(word.charAt(i), k -> new TrieNode());
        }
        cur.isWord = true;
    }
    
    public boolean search(String word) {
        return find(word, 0, root);
    }
    
    private boolean find(String word, int i, TrieNode node) {
        if (i == word.length()) {
            return node.isWord;
        }
        
        if (word.charAt(i) == '.') {
            for (Map.Entry<Character, TrieNode> entry : node.children.entrySet()) {
                if (find(word, i + 1, entry.getValue())) {
                    return true;
                }
            }
            return false; 
        } else {
            TrieNode next = node.children.get(word.charAt(i));
            if (next == null) {
                return false;
            }
            
            return find(word, i + 1, next);    
        }
    }
}

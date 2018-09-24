Three implementations of Trie

1. boolean isWord
2. String word // reference
3. List<String> words // a list of passing words

1. boolean insert(String)
2. boolean search(String)
3. List<String>/boolean startsWith(String)
 
Implementation I
private static class Trie1 {
    private class TrieNode {
        Map<Character, TrieNode> children = new HashMap<>();
        boolean isWord;
    }
    
    private TrieNode root = new TrieNode();
    
    public void insert(String word) {
        TrieNode cur = this.root;
        for (int i = 0; i < word.length(); i++) {
            char c = word.charAt(i);
            cur = cur.children.computeIfAbsent(c, k -> new TrieNode());
        }
        cur.isWord = true;
    }
    
    public boolean search(String word) {
        TrieNode cur = this.root;
        for (int i = 0; i < word.length(); i++) {
            cur = cur.children.get(word.charAt(i));
            if (cur == null) {
                return false;
            }
        } 
        return cur.isWord;
    }
    
    public List<String> startsWith(String prefix) {
        TrieNode cur = this.root;
        for (int i = 0; i < word.length(); i++) {
            cur = cur.children.get(word.charAt(i));
            if (cur == null) {
                return Collections.emptyList();
            }
        }
        return dfs(cur, new ArrayList<>(), new StringBuilder(prefix));
    }
    
    private List<String> dfs(TrieNode cur, List<String> res, StringBuilder sb) {
        if (cur.isWord) {
            res.add(sb.toString());
        }
        
        for (Map.Entry<Character, TrieNode> entry : 
             cur.children.entrySet()) {

             sb.append(entry.getKey());
             dfs(entry.getValue(), res, sb);
             sb.setLength(sb.length() - 1);
        }
        
        return res;
    }
}

Implementation II
private static class Trie2 {
    private class TrieNode {
        Map<Character, TrieNode> children = new HashMap<>();
        String word;
    }
    
    private TrieNode root = new TrieNode(); // private

    public void insert(String word) {
        TrieNode cur = this.root;
        for (int i = 0; i < word.length(); i++) {
            char c = word.charAt(i);
            cur = cur.children.computeIfAbsent(c, k -> new TrieNode());
        }
        cur.word = word;
    }
    
    public boolean search(String word) {
        TrieNode cur = this.root;
        for (int i = 0; i < word.length(); i++) {
            cur = cur.children.get(word.charAt(i));
            if (cur == null) {
                return false;
            }
        } 
        return cur.word != null;
    }
    
    public List<String> startsWith(String prefix) {
        TrieNode cur = this.root;
        for (int i = 0; i < word.length(); i++) {
            cur = cur.children.get(word.charAt(i));
            if (cur == null) {
                return Collections.emptyList();
            }
        }
        return dfs(cur, new ArrayList<>());
    }
    
    private List<String> dfs(TrieNode cur, List<String> res) {
        if (cur.word != null) {
            res.add(cur.word);
        }
        
        for (Map.Entry<Character, TrieNode> entry : 
             cur.children.entrySet()) {
             dfs(entry.getValue(), res);
        }
        
        return res;
    }

    public TrieNode getNextNode(TrieNode node, char c) {
        if (node == null) {
            return this.root.children.get(c);
        }

        return node.children.get(c);
    }
}

Implementation III
private static class Trie3 {
    private class TrieNode {
        Map<Character, TrieNode> children = new HashMap<>();
        List<String> words; // Set, reduce time complexity for search and de-duplicates if words are inserted multiple times
    }
    
    private TrieNode root = new TrieNode();
    
    public void insert(String word) {
        if (root.words.contains(word)) {
            return;
        } 
        
        TrieNode cur = this.root;
        for (int i = 0; i < word.length(); i++) {
            cur.words.add(word);
            char c = word.charAt(i);
            cur = cur.children.computeIfAbsent(c, k -> new TrieNode());
        }
        cur.words.add(word);
    }
    
    public boolean search(String word) {
        TrieNode cur = this.root;
        for (int i = 0; i < word.length(); i++) {
            cur = cur.children.get(word.charAt(i));
            if (cur == null) {
                return false;
            }
        } 
        return cur.words.contains(word); // take O(k), List -> Set
    }
    
    public List<String> startsWith(String prefix) {
        TrieNode cur = this.root;
        for (int i = 0; i < word.length(); i++) {
            cur = cur.children.get(word.charAt(i));
            if (cur == null) {
                return Collections.emptyList();
            }
        }
        return cur.words;
    }
}

// word ladder 2

/**
* Longest Word in Dictionary
Given a list of strings words representing an English Dictionary, find the longest word in words that can be built one character at a time by other words in words. If there is more than one possible answer, return the longest word with the smallest lexicographical order.
If there is no answer, return the empty string.
Example 1:
Input: 
words = ["w","wo","wor","worl", "world"]
Output: "world"
Explanation: 
The word "world" can be built one character at a time by "w", "wo", "wor", and "worl".
Example 2:
Input: 
words = ["a", "banana", "app", "appl", "ap", "apply", "apple"]
Output: "apple"
Explanation: 
Both "apply" and "apple" can be built from other words in the dictionary. However, "apple" is lexicographically smaller than "apply".
Note:
All the strings in the input will only contain lowercase letters.
The length of words will be in the range [1, 1000].
The length of words[i] will be in the range [1, 30].
*/
public class LongestWordInDictionary {
    private static class Node {
        char c;
        Map<Character, Node> children = new HashMap<>();
        int end;

        public Node(char c) {
            this.c = c;
        }
    }

    private static class Trie {
        Node root;
        String[] words;

        public Trie() {
            root = new Node('0');
        }

        public void insert(String word, int index) {
            Node cur = root;
            for (char c : word.toCharArray()) {
                cur.children.putIfAbsent(c, new Node(c));
                cur = cur.children.get(c);
            }
            cur.end = index;
        }

        public String dfs() {
            String ans = "";
            Stack<Node> stack = new Stack<>();
            stack.push(root);
            // DFS
            while (!stack.empty()) {
                Node node = stack.pop();
                // ap -> appl, step app is missing
                // but app is in the TRIE, yet the end of app TRIE node is 0
                // so it will be skipped to be processed.
                if (node.end > 0 || node == root) {
                    if (node != root) {
                        String word = words[node.end - 1];
                        if (word.length() > ans.length() || word.length() == ans.length() && word.compareTo(ans) < 0) {
                            ans = word;
                        }
                    }
                    for (Node nei : node.children.values()) {
                        stack.push(nei);
                    }
                }
            }
            return ans;
        }
    }

    public String longestWord(String[] words) {
        Trie trie = new Trie();
        int index = 0;
        for (String word : words) {
            trie.insert(word, ++index); // indexed by 1
        }
        trie.words = words;
        return trie.dfs();
    }

    public static void main(String[] args) {
    }
}


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
            dfsFindWords(board, result, trie, null, i, j); // NOT trie.root... private
        }
    }
    
    return result;
}

// visited & restore
// de-duplicate
// out of bound
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
    for (int[] dir : dirs) {
        int newX = x + dir[0];
        int newY = y + dir[1];
        
        for (0 <= newX && newX < board.length &&
             0 <= newY && newY < board[0].length &&
             board[newX][newY] != '#') {
            dsf(board, newX, newY, trie, nextNode, res);
        }
    }
    // for (Point neighbor : getWordSearchNeighbors(board, x, y)) {
    //     dfsFindWords(board, result, trie, nextNode, neighbor.x, neighbor.y);
    // }
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


class Solution {
    
    private static int[][] coordinates = new int[][] {
        {-1, 0}, {1, 0}, {0, 1}, {0, -1}
    };
    
    public List<String> findWords(char[][] board, String[] words) {
        int row = board.length;
        int col = board[0].length;
        
        List<String> res = new ArrayList<>();
        
        TrieNode root = buildTrie(words);
        
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                dfs(board, i, j, root, res);
            }
        }
        return res;
    }
    
    private void dfs(char[][] board, 
                     int i, int j,
                     TrieNode node, 
                     List<String> res) {
        
        char ch = board[i][j];
        if (node.next[ch - 'a'] == null) {
            return;
        } else if (node.next[ch - 'a'].word != null) {
            res.add(node.next[ch - 'a'].word);
            node.next[ch - 'a'].word = null; // de-duplicate....
        }
        
        board[i][j] = '#';
        for (int k = 0; k < coordinates.length; k++) {
            int x = i + coordinates[k][0];
            int y = j + coordinates[k][1];
            if (x >= 0 && x < board.length && 
                y >= 0 && y < board[0].length && 
                board[x][y] != '#') {
                dfs(board, x, y, node.next[ch - 'a'], res);
            }
        }
        board[i][j] = ch;
    }
    
    private TrieNode buildTrie(String[] words) {
        TrieNode root = new TrieNode();
        for (String word : words) {
            TrieNode node = root;
            char[] chars = word.toCharArray();
            for (int i = 0; i < chars.length; i++) {
                int index = chars[i] - 'a';
                if (node.next[index] == null) {
                    node.next[index] = new TrieNode();
                }
                node = node.next[index];
            }
            node.word = word;
        }
        return root;
    }
    
    class TrieNode {
        public TrieNode[] next = new TrieNode[26];
        public String word;
    }
}

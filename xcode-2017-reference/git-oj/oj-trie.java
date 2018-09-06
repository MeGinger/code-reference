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
        
        for (Map.Entry<Character, TrieNode> entry : cur.children.entrySet()) {
             sb.append(entry.getKey());
             dfs(entry.getValue(), res, sb);
             sb.setLength(sb.length() - 1);
        }
        
        return res;
    }
}

Implementation II

Implementation III


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



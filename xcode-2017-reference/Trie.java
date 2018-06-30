/**
     * Implement Trie (Prefix Tree) 
     * Implement a trie with insert, search, and startsWith methods. 
     * You may assume that all inputs are consist of lowercase letters a-z.
     */
    class TrieNode {
    	boolean have;
    	String word;
    	TrieNode[] children;
    	
    	public TrieNode() {
    		this.children = new TrieNode[26];
    	}
    }

    class Trie {
        private TrieNode root;

        public Trie() {
            this.root = new TrieNode();
        }

        // Inserts a word into the trie.
        public void insert(String word) {
        	TrieNode cur = this.root;
        	for (int i = 0; i < word.length(); i++) {
        		int index = word.charAt(i) - 'a';
        		TrieNode child = cur.children[index];
        		if (child == null) {
        			child = new TrieNode();
        			cur.children[index] = child;
        		}
        		
        		cur = child;
        	}
        	
        	cur.have = true;
        }

        // Returns if the word is in the trie.
        public boolean search(String word) {
        	TrieNode cur = root;
            for (int i = 0; i< word.length(); i++) {
            	int index = word.charAt(i) - 'a';
        		cur = cur.children[index];
        		if (cur == null) {
        			return false;
        		}
            }
            
            return cur.have;
        }

        // Returns if there is any word in the trie
        // that starts with the given prefix.
        public boolean startsWith(String prefix) {
        	TrieNode cur = root;
            for (int i = 0; i< prefix.length(); i++) {
            	int index = prefix.charAt(i) - 'a';
        		cur = cur.children[index];
        		if (cur == null) {
        			return false;
        		}
            }
            
            return true;
        }
        
        public TrieNode searchNextNode(TrieNode node, char c) {
        	if (node == null) {
        		node = root;
        	}
            int index = c - 'a';
        	return node.children[index];
        }
        
        public void delete(String word) {
        	TrieNode cur = root;
        	Stack<Object[]> s = new Stack<>();
        	s.add(new Object[]{0, cur});
            for (int i = 0; i< word.length(); i++) {
            	int index = word.charAt(i) - 'a';
        		cur = cur.children[index];
        		if (cur == null) {
        			throw new IllegalArgumentException("No word: " + word);
        		}
        		s.add(new Object[]{index, cur});
            }
            
            if (cur.word == null) {
            	throw new IllegalArgumentException("No word: " + word);
            }
            
            cur.word = null;
            
            while (!s.isEmpty()) {
            	Object[] o = s.pop();
            	int index = (Integer) o[0];
            	TrieNode node = (TrieNode) o[1];
            	
            	for (TrieNode child : node.children) {
            		if (child != null) {
            			// node has child, we cannot delete it
            			return;
            		}
            	}
            	
            	if (!s.isEmpty()) {
            		o = s.peek();
                	TrieNode parent = (TrieNode) o[1];
                	parent.children[index] = null; // delete node
            	}
            }
        }
    }
// LCA
// p and q might or might not be in the tree
// if not, result will be NULL
public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        // base cases:
        if (root == null || root == p || root == q) {
            return root;
        }
        
        TreeNode left = lowestCommonAncestor(root.left, p, q);
        TreeNode right = lowestCommonAncestor(root.right, p, q);
        
        // p and q is in either subtree
        if (left != null && right != null) {
            return root;
        } else if (left != null) {
            return left;
        } else if (right != null) {
            return right;
        }
        
        return null;
}

Serialize and Deserialize binary tree

Serialize and Deserialize binary search tree

import java.util.StringTokenizer;
// import java.lang.StringBuilder;

public class Codec {
    private static final String NULL = "#"; // null child
    private static final String SEP = ","; // seperator

    // Encodes a tree to a single string.
    // iteration
    public String serialize(TreeNode root) {
        if (root == null) {
            return "";
        }
        
        StringBuilder sb = new StringBuilder();
        // preorder traversal
        Stack<TreeNode> s = new Stack<>();
        s.push(root);
        while (!s.isEmpty()) {
            TreeNode cur = s.pop();
            
            // handle popped node
            // - if null
            if (cur == null) {
                sb.append(NULL).append(SEP);
                continue;
            }
            // - if not null
            sb.append(cur.val).append(SEP);
            
            // RIGHT first then LEFT
            s.push(cur.right);
            s.push(cur.left);
        }
        
        sb.setLength(sb.length() - 1); // REMOVE LAST SEP
        return sb.toString();
    }

    // Decodes your encoded data to tree.
    // java.util.StringTokenizer
    public TreeNode deserialize(String data) {
        if (data == null || data.length() == 0) {
            return null;
        }
        
        // constructor takes two parameters
        StringTokenizer st = new StringTokenizer(data, SEP);   
        return deserialize(st);
    }
    
    // StringTokenizer.hasMoreTakens() -> boolean
    // StringTokenizer.nextToken() -> String
    private TreeNode deserialize(StringTokenizer st) {
        if (!st.hasMoreTokens()) { // the end of st
            return null;
        }
        
        String val = st.nextToken();
        if (val.equals(NULL)) {
            return null;
        }
        
        TreeNode node = new TreeNode(Integer.parseInt(val));
        node.left = deserialize(st); // ? since it is preorder traversal
        node.right = deserialize(st); // ? since it is preorder traversal
        
        return node;
    }
}

// Your Codec object will be instantiated and called as such:
// Codec codec = new Codec();
// codec.deserialize(codec.serialize(root));

/** Serialize and Deserialize BST */

public class Codec2 {
    private static final String SEP = ","; // seperator

    // PreOrder: Encodes a tree to a single string.
    public String serialize(TreeNode root) {
        if (root == null) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        // PreOrder traversal
        Stack<TreeNode> st = new Stack<>();
        st.push(root);
        while (!st.empty()) {
            root = st.pop();
            sb.append(root.val).append(SEP);

            // preorder traversal + iteration using stack 
            // so right first left then
            if (root.right != null) {
                st.push(root.right);
            }

            if (root.left != null) {
                st.push(root.left);
            }
        }
        return sb.toString();
    }

    // Decodes your encoded data to tree.
    // pre-order traversal
    //  time complexity is O(NlogN). worst case complexity should be O(N^2), when the tree is really unbalanced.
    public TreeNode deserialize(String data) {
        if (data == null || data.length() == 0) {
            return null;
        }
        Queue<Integer> q = new LinkedList<>();
        for (String e : data.split(SEP)) {
            q.offer(Integer.parseInt(e));
        }
        return getNode(q);
    }

    // some notes:
    //   5
    //  3 6
    // 2   7
    private TreeNode getNode(Queue<Integer> q) { // q: 5, | 3,2, | 6,7 based on BST properties
        if (q.isEmpty()) {
            return null;
        }
        TreeNode root = new TreeNode(q.poll());// root (5)
        Queue<Integer> smallerQueue = new LinkedList<>();
        while (!q.isEmpty() && q.peek() < root.val) {
            smallerQueue.offer(q.poll());
        }
        // smallerQueue : 3,2 storing elements smaller than 5 (root)
        root.left = getNode(smallerQueue);
        // q: 6,7 storing elements bigger than 5 (root)
        root.right = getNode(q);
        return root;
    }
}

class Solution {
    public List<List<Integer>> zigzagLevelOrder(TreeNode root) {        
        if (root == null) {
            return Collections.emptyList();
        }

        List<List<Integer>> res = new ArrayList<>();
        
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);
        
        boolean isFlip = false;
        while (!queue.isEmpty()) {
            int size = queue.size();
            List<Integer> list = new ArrayList<>();
            for (int i = 0; i < size; i++) {
                TreeNode node = queue.poll(); 
                list.add(node.val); // dont forget!
                if (node.left != null) {
                    queue.offer(node.left);
                }
                if (node.right != null) {
                    queue.offer(node.right);
                }
            }
            
            if (isFlip) {
                Collections.reverse(list); // same with sort, return nothing!!
            } 
            
            res.add(list);            
            isFlip = !isFlip;
        }
        
        return res;
    }
}

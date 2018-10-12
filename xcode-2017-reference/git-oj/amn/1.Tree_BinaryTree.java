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

// Lowest Common Ancestor of a Binary Search Tree
public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
    // p and q are are in the left or right subtree
    while ((root.val - (long) p.val) * (root.val - (long) q.val) > 0) { 
        root = root.val > p.val ? root.left : root.right;
    }
    
    return root;
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

class Solution {
    public List<Integer> inorderTraversal(TreeNode root) {
        if (root == null) {
            return Collections.emptyList();
        }
        
        List<Integer> res = new ArrayList<>();
        Stack<TreeNode> stack = new Stack<>();
        // 千万不要先push root，否则序列会重复出现多一遍！

        while (root != null || !stack.isEmpty()) {
            while (root != null) {
                stack.push(root);
                root = root.left;
            }
            
            // root == null
            root = stack.pop();
            res.add(root.val);
            
            root = root.right; // right might be null, so that why while condition check uses ||
        }
        
        return res;    
    }
}

Validate Binary Search Tree
class Solution {
    public boolean isValidBST(TreeNode root) {
        if (root == null) {
            return true;
        }
        
        Stack<TreeNode> stack = new Stack<>();
        TreeNode pre = null; // please do not use Integer.MIN_VALUE as start value...
        while (root != null || !stack.isEmpty()) {
            while (root != null) {
                stack.push(root);
                root = root.left;
            }
            
            root = stack.pop();
            if (pre != null && pre.val >= root.val) {
                return false;
            }
            pre = root;
            root = root.right;
        }
        return true;
    }
}

Subtree of Another Tree
class Solution {
    public boolean isSubtree(TreeNode s, TreeNode t) {
        if (s == null || t == null) { // I FORGOT the boundary check!
            return false;
        }
        
        return same(s, t) || 
               isSubtree(s.left, t) || 
               isSubtree(s.right, t);
    }
    
    private boolean same(TreeNode a, TreeNode b) {
        if (a == null && b == null) {
            return true;
        }
        
        if (a == null || b == null) {
            return false;
        }
        
        boolean left = same(s.left, t.left);
        boolean right = same(s.right, t.right);
        
        return s.val == t.val && left && right;
    }
}

Construct Binary Tree from Preorder and Inorder Traversal
// assume that duplicates do not exist in the tree.
preorder -> 3 | 2, 5 | 6, 7 // the boundaries | are indicated by inorder seq
inorder  -> (2, 5) | 3 | (6, 7) 
inorder sequence can help determine the size/boundary of left or right subtrees
so that preorder sequence can be more semantically meaningful

postorder -> 2, 5 | 6, 7 | 3
inorder  -> (2, 5) | 3 | (6, 7) 

class Solution {
    public TreeNode buildTree(int[] preorder, int[] inorder) {
        if (preorder == null || preorder.length == 0) {
            return null;
        }
        
        if (inorder == null || inorder.length == 0) {
            return null;
        }
        // assume that duplicates do not exist in the tree.
        // cache
        Map<Integer, Integer> inorderIndex = new HashMap<>();
        for (int i = 0; i < inorder.length; i++) {
            inorderIndex.put(inorder[i], i);
        }
        
        return dfs(0, 0, inorder.length - 1, 
                   preorder, inorder, inorderIndex);
    }
    
    private TreeNode dfs(int preStart, int inStart, int inEnd, 
                         int[] preorder, int[] inorder, Map<Integer, Integer> inorderIndex) {
        if (preStart > preorder.length - 1 || inStart > inEnd) {
            return null;
        }
        
        int val = preorder[preStart];
        TreeNode root = new TreeNode(val);
        int inRoot = inorderIndex.get(val);
        
        // (inRoot - inStart) is the size of left subtree
        root.left = dfs(preStart + 1, inStart, inRoot - 1, preorder, inorder, inorderIndex);
        root.right = dfs(preStart + (inRoot - inStart) + 1, inRoot + 1, inEnd, preorder, inorder, inorderIndex);
        
        return root;
    }
}

// Binary Tree Maximum Path Sum

class Solution {
    private int maxPath; // whole max path
    public int maxPathSum(TreeNode root) {
        maxPath = Integer.MIN_VALUE;
        maxPath(root);
        return maxPath;
    }
    
    private int maxPath(TreeNode root) { // single max path
        if (root == null) {
            return 0;
        }
        
        int left = Math.max(0, maxPath(root.left));
        int right = Math.max(0, maxPath(root.right));
        
        maxPath = Math.max(maxPath, left + right + root.val);
        return Math.max(left, right) + root.val;
    }
}

// Symmetric Tree
class Solution {
    public boolean isSymmetric(TreeNode root) {
        if (root == null) {
            return true;
        }
        
        return isSymmetric(root.left, root.right);
    }
    
    private boolean isSymmetric(TreeNode left, TreeNode right) {
        if (left == null && right == null) {
            return true;
        }
        
        if (left == null || right == null) {
            return false;
        }
        
        if (left.val != right.val) {
            return false;
        }
        
        return isSymmetric(left.left, right.right) &&
               isSymmetric(left.right, right.left);
    }
}















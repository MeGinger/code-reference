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

Closest Binary Search Tree Value
class Solution {
    public int closestValue(TreeNode root, double target) {
        if (root == null) {
            return 0;
        }
        
        Stack<TreeNode> stack = new Stack<>();
        TreeNode cur = root;
        int res = 0;
        double min = Double.MAX_VALUE;
        while (!stack.isEmpty() || cur != null) {
            while (cur != null) {
                stack.push(cur);
                cur = cur.left;
            }
            
            // cur == null
            cur = stack.pop();
            if (Math.abs(cur.val - target) < min) {
                min = Math.abs(cur.val - target);
                res = cur.val;
            }
            cur = cur.right;
        }
        
        return res;
    }
}

Given a binary search tree and a node in it, find the in-order successor of that node in the BST.
Note: If the given node has no in-order successor in the tree, return null.
class Solution {
    public TreeNode inorderSuccessor(TreeNode root, TreeNode p) {
        if (root == null || p == null) {
            return null;
        }
        
        Stack<TreeNode> stack = new Stack<>();
        TreeNode cur = root;
        TreeNode pre = null;
        while (!stack.isEmpty() || cur != null) {
            while (cur != null) {
                stack.push(cur);
                cur = cur.left;
            }
            
            // cur == null
            cur = stack.pop();
            
            if (pre == p) {
                return cur;
            }
            
            pre = cur;
            cur = cur.right;
        }
        
        return null;
    }
}


Two Sum IV - Input is a BST
Time: O(N)
Space: O(N)
class Solution {
    public boolean findTarget(TreeNode root, int k) {
        if (root == null) {
            return false;
        }
        
        Stack<TreeNode> stack = new Stack<>();
        Set<Integer> nums = new HashSet<>();
        TreeNode cur = root;
        
        while (!stack.isEmpty() || cur != null) {
            while (cur != null) {
                stack.push(cur);
                cur = cur.left;
            }
            
            // cur == null
            cur = stack.pop();
            
            if (nums.contains(k - cur.val)) {
                return true;
            }
            nums.add(cur.val);
            cur = cur.right;
        }
        
        return false;
    }
}

Binary Search Tree Iterator
public class BSTIterator {
    Stack<TreeNode> stack = new Stack<>();
    
    public BSTIterator(TreeNode root) {
        pushAll(root);
    }

    /** @return whether we have a next smallest number */
    public boolean hasNext() {
        return !stack.isEmpty();
    }

    /** @return the next smallest number */
    public int next() {
        TreeNode tmpNode = stack.pop();
        pushAll(tmpNode.right);
        return tmpNode.val;
    }
    
    private void pushAll(TreeNode root) {
        while (root != null) {
            this.stack.push(root);
            root = root.left;
        }
    }
}

class Solution {
    public void recoverTree(TreeNode root) {
        if (root == null) {
            return;
        }
        
        Stack<TreeNode> stack = new Stack<>();
        TreeNode cur = root;
        TreeNode pre = null;
        TreeNode first = null;
        TreeNode second = null;
        while (cur != null || !stack.isEmpty()) {
            while (cur != null) {
                stack.push(cur);
                cur = cur.left;
            }
            
            // cur == null
            cur = stack.pop();
            
            if (pre != null && pre.val > cur.val) {
                if (first == null) { // case 1: 2, 1, 3 (two elements are adjacent)
                    first = pre;   
                    second = cur;
                } else { // case 2: 1, 4, 3, 2, 7 (two elements are not adjacent)
                    second = cur;
                    break;
                }
            } 
            
            pre = cur;
            cur = cur.right;
        }
        
        int temp = first.val;
        first.val = second.val;
        second.val = temp;
    }
}

Minimum Distance Between BST Nodes

class Solution {
    public int minDiffInBST(TreeNode root) {
        if (root == null) {
            return 0;
        }
        
        Stack<TreeNode> stack = new Stack<>();
        TreeNode cur = root, pre = null;
        int res = Integer.MAX_VALUE;
        while (!stack.isEmpty() || cur != null) {
            while (cur != null) {
                stack.push(cur);
                cur = cur.left;
            }
            
            cur = stack.pop();
            if (pre != null && res > Math.abs(cur.val - pre.val)) {
                res = Math.abs(cur.val - pre.val);
            }
            pre = cur;
            cur = cur.right;
        }
        
        return res;
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


Boundary of Binary Tree

class Solution {
    private List<Integer> res;

    // anti-clockwise direction starting from 
    // root. Boundary includes left boundary, leaves, and right boundary in order 
    public List<Integer> boundaryOfBinaryTree(TreeNode root) {
        res = new ArrayList<>();
        
        if (root == null) {
            return res;
        }
        
        res.add(root.val);
        // left boundary
        leftBoundary(root.left);
        // leaves 
        // if just leaves(root) - root might be added twice. like input [root]
        leaves(root.left);
        leaves(root.right);
        // right boundary
        rightBoundary(root.right);
        
        return res;
    }
    
    // preorder
    // no leave involved
    private void leftBoundary(TreeNode node) {
        if (node == null) {
            return;
        }
        if (node.left == null && node.right == null) {
            return;
        }
        
        res.add(node.val);
        
        if (node.left != null) {
            leftBoundary(node.left);     
        } else {
            leftBoundary(node.right);     
        }
    }
    
    // postorder
    // no leave involved
    private void rightBoundary(TreeNode node) {
        if (node == null) {
            return;
        }
        if (node.left == null && node.right == null) {
            return;
        }
        
        // the right boundary path is created by the following methods. 
        if (node.right != null) {
            rightBoundary(node.right);
        } else {
            rightBoundary(node.left);
        }
        // when we go back, we add the node
        res.add(node.val);
    }
    
    // preorder
    private void leaves(TreeNode node) {
        if (node == null) {
            return;
        }
        if (node.left == null && node.right == null) {
            res.add(node.val);
        }
        leaves(node.left);
        leaves(node.right);
    }
}


Populating Next Right Pointers in Each Node

public class Solution {
    // it is a perfect binary tree 
    // (ie, all leaves are at the same level, and every parent has two children).
    public void connect(TreeLinkNode root) {
        while (root != null) {
            TreeLinkNode cur = root;
            while (cur != null) {
                if (cur.left != null) { // cur might be a leaf
                    cur.left.next = cur.right;
                }
                if (cur.right != null && cur.next != null) {
                    cur.right.next = cur.next.left;
                }
                
                cur = cur.next;
            }
            
            root = root.left;
        }
    }
}

Populating Next Right Pointers in Each Node II

public class Solution {
    public void connect(TreeLinkNode root) {
        while (root != null) {
            TreeLinkNode dummy = new TreeLinkNode(0);
            TreeLinkNode cur = dummy;
            while (root != null) {
                if (root.left != null) {
                    cur.next = root.left;
                    cur = cur.next;
                }
                if (root.right != null) {
                    cur.next = root.right;
                    cur = cur.next;
                }
                
                root = root.next;
            }
            root = dummy.next;
        }
    }
}



Binary Tree Right Side View
class Solution {
    public List<Integer> rightSideView(TreeNode root) {
        List<Integer> res = new ArrayList<>();
        if (root == null) {
            return res;
        }
        
        dfs(root, res, 0);
        return res;        
    }
    
    private void dfs(TreeNode root, int depth, List<Integer> res) {
        if (root == null) {
            return;
        }
        
        if (res.size() == depth) { // !!
            res.add(root.val);
        } else {
            res.set(depth, root.val);
        }
        
        dfs(root.left, depth + 1, res);
        dfs(root.right, depth + 1, res);
    }

    // better
    // reverse pre-order traversal
    // root -> right -> left
    private void dfs(TreeNode node, List<Integer> list, int depth) {
        if (node == null) {
            return;
        }
        
        // the first one we traverse
        if (list.size() == depth) {
            list.add(node.val);
        }
        
        dfs(node.right, list, depth + 1);
        dfs(node.left, list, depth + 1);
    }
}

Binary Tree Left Side View

private void dfs(TreeNode root, int depth, List<Integer> res) {
    if (root == null) {
        return;
    }
    
    if (res.size() == depth) { // !!
        res.add(root.val);
    } else {
        res.set(depth, root.val);
    }
    
    dfs(root.right, depth + 1, res);
    dfs(root.left, depth + 1, res);
}

// better
private void dfs(TreeNode node, List<Integer> list, int depth) {
    if (node == null) {
        return;
    }
    
    // the first one we traverse
    if (list.size() == depth) {
        list.add(node.val);
    }
    
    dfs(node.left, list, depth + 1);
    dfs(node.right, list, depth + 1);
}


Unique Binary Search Trees
Given n, how many structurally unique BST's (binary search trees) that store values 1 ... n?

class Solution {
    public int numTrees(int n) {
        int[] g = new int[n + 1];
        g[0] = 1;
        g[1] = 1;
        
        // i from 2 to n (DP approach)
        for (int i = 2; i <= n; i++) {
            // i is count of node
            
            for (int j = 1; j <= i ; j++) {
                // j is used as root
                g[i] += g[j - 1] * g[i - j];
                
            }
        }
        
        return g[n];
    }
}

Diameter of Binary Tree
// field variable max
public class Solution {
    int max = 0;
    
    public int diameterOfBinaryTree(TreeNode root) {
        maxDepth(root);
        return max;
    }
    
    private int maxDepth(TreeNode root) {
        if (root == null) return 0;
        
        int left = maxDepth(root.left); // count of left nodes
        int right = maxDepth(root.right); // count of right nodes
        
        max = Math.max(max, left + right);
        
        return Math.max(left, right) + 1;
    }
}

Maximum Depth of Binary Tree
// depth = the count of nodes in a path
class Solution {
    public int maxDepth(TreeNode root) {
        if (root == null) {
            return 0;
        }
        
        int left = maxDepth(root.left);
        int right = maxDepth(root.right);
        
        return 1 + Math.max(left, right);
    }
}

Minimum Depth of Binary Tree
DFS
class Solution {
    int min = Integer.MAX_VALUE;
    
    public int minDepth(TreeNode root) {
        if (root == null) {
            return 0;
        }
        
        dfs(root, 1);
        return min;
    }
    
    private void dfs(TreeNode root, int depth) {
        if (root == null) {
            return;
        }
        
        if (root.left == null && root.right == null) {
            min = Math.min(min, depth);
        }
        
        dfs(root.left, depth + 1);
        dfs(root.right, depth + 1);
    }
}

Find Duplicate Subtrees

class Solution {
    public List<TreeNode> findDuplicateSubtrees(TreeNode root) {
        if (root == null) {
            return Collections.emptyList();
        }
        
        List<TreeNode> res = new ArrayList<>();
        traverse(root, new HashMap<>(), res);
        return res;
    }
    
    // bottom - up -> postorder - divide and conquer
    private String traverse(TreeNode root, Map<String, Integer> map, List<TreeNode> list) {
        if (root == null) {
            return "#";
        }
        
        String serial = root.val + "," + traverse(root.left, map, list) + "," + traverse(root.right, map, list);
        
        if (map.getOrDefault(serial, 0) == 1) { 
            list.add(root); // added only once
        }
        
        map.put(serial, map.getOrDefault(serial, 0) + 1);
        
        return serial;
    }
}


Merge Two Binary Trees

class Solution {
    public TreeNode mergeTrees(TreeNode t1, TreeNode t2) {
        if (t1 == null && t2 == null) {
            return null;
        } else if (t1 == null) {
            return t2;
        } else if (t2 == null) {
            return t1;
        }
        
        // t1 != null && t2 != null
        TreeNode left = mergeTrees(t1.left, t2.left);
        TreeNode right = mergeTrees(t1.right, t2.right);
        
        TreeNode node = new TreeNode(t1.val + t2.val);
        node.left = left;
        node.right = right;
        
        return node;
    }
}
// should discuss with interviewer about the requirements he wants
class Solution {
    public TreeNode mergeTrees(TreeNode t1, TreeNode t2) {
        if (t1 == null && t2 == null) {
            return null;
        } else if (t1 == null) {
            return t2;
        } else if (t2 == null) {
            return t1;
        }
        
        t1.val += t2.val;
        t1.left = mergeTrees(t1.left, t2.left);
        t1.right = mergeTrees(t1.right, t2.right);
        
        return t1;
    }
}


Flatten Binary Tree to Linked List
Given a binary tree, flatten it to a linked list in-place.

For example, given the following tree:

    1
   / \
  2   5
 / \   \
3   4   6
The flattened tree should look like:

1
 \
  2
   \
    3
     \
      4
       \
        5
         \
          6
          
class Solution {
    public void flatten(TreeNode root) {
        if (root == null) {
            return;
        }
        
        /*
         left    right
            \        \
             \        \
         */ 
        TreeNode left = root.left; // easy mistake
        TreeNode right = root.right; // easy mistake
        
        // divide
        flatten(root.left);
        flatten(root.right);
        
        // connect left first
        root.left = null; // easy to forget
        root.right = left;
        
        // connect right then
        while (root.right != null) {
            root = root.right;
        }
        root.right = right;
    }
}

Trim a Binary Search Tree
 
class Solution {
    public TreeNode trimBST(TreeNode root, int L, int R) {
        if (root == null) {
            return null;
        }
        
        if (root.val < L) {
            return trimBST(root.right, L, R);
        } 
        if (root.val > R) {
            return trimBST(root.left, L, R);
        }
        
        root.left = trimBST(root.left, L, R);
        root.right = trimBST(root.right, L, R);
        
        return root;
    }
}


class Solution {
    public int longestUnivaluePath(TreeNode root) {
        if (root == null) {
            return 0;
        }
        
        int[] res = new int[1];
        getLen(root, root.val, res);
        return res[0];
    }
    
    // getLen: maximum single path length (including root) with target value 
    private int getLen(TreeNode root, int target, int[] res) { // target is parent value
        if (root == null) {
            return 0;
        }
        
        int left = getLen(root.left, root.val, res);
        int right = getLen(root.right, root.val, res);
        
        res[0] = Math.max(res[0], left + right);
        
        return target == root.val ? Math.max(left, right) + 1 : 0;
    }
}

class Solution {
    private int len = 0;
    
    public int longestUnivaluePath(TreeNode root) {
        if (root == null) {
            return 0;
        }
        
        len = 0;
        getLen(root, root.val);
        return len;
    }
    
    // getLen: maximum single path length (including root) with target value 
    private int getLen(TreeNode root, int target) { // target is parent value
        if (root == null) {
            return 0;
        }
        
        int left = getLen(root.left, root.val);
        int right = getLen(root.right, root.val);
        
        len = Math.max(len, left + right);
        
        if (target == root.val) {
            return Math.max(left, right) + 1;
        }
        
        return 0;
    }
}


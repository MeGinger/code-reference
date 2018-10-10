/* Boundary of Binary Tree
Given a binary tree, return the values of its boundary in anti-clockwise direction starting from root. Boundary includes left boundary, leaves, and right boundary in order without duplicate nodes.

Left boundary is defined as the path from root to the left-most node. Right boundary is defined as the path from root to the right-most node. If the root doesn't have left subtree or right subtree, then the root itself is left boundary or right boundary. Note this definition only applies to the input binary tree, and not applies to any subtrees.

The left-most node is defined as a leaf node you could reach when you always firstly travel to the left subtree if exists. If not, travel to the right subtree. Repeat until you reach a leaf node.

The right-most node is also defined by the same way with left and right exchanged.

Input:
  1
   \
    2
   / \
  3   4

Ouput: 
[1, 3, 4, 2]

Input:
    ____1_____
   /          \
  2            3
 / \          / 
4   5        6   
   / \      / \
  7   8    9  10  
       
Ouput:
[1,2,4,7,8,9,10,6,3]

*/
class Solution {
    List<Integer> nodes;
    
    public List<Integer> boundaryOfBinaryTree(TreeNode root) {
        // anti-clockwise
        // Left boundary is defined as the path from root to the left-most node. 
        // Right boundary is defined as the path from root to the right-most node.
        
        // NOT a recursive definition
        // If the root doesn't have left subtree or right subtree, 
        // then the root itself is left boundary or right boundary.
        
        nodes = new ArrayList<>(); // initialization - suppose this method is called multiple times. 
                                   // before doing the algorithm, please empty it up.
        
        if (root == null) {
            return nodes;
        }
        
        nodes.add(root.val);
        
        leftBoundary(root.left);
        
        // leaves(root); // we can't do this, because of a corner case -> a tree with only one root node 
                         // in that case, root is added twice
        leaves(root.left);
        leaves(root.right);
        
        rightBoundary(root.right);
        
        return nodes;
    }
    
    // pre-order traversal
    public void leftBoundary(TreeNode root) { // This root is the root node of left subtree of the parent root.
        // null or leaf
        if (root == null || 
                (root.left == null && root.right == null)) {
            return;
        }
        
        nodes.add(root.val); // from top to bottom
        
        if (root.left != null) {
            leftBoundary(root.left);
        } else {
            leftBoundary(root.right);
        }
    }
    
    // post-order traversal
    public void rightBoundary(TreeNode root) {
        // null or leaf
        if (root == null ||
                (root.left == null && root.right == null)) {
            return;
        } 
        
        if (root.right != null) {
            rightBoundary(root.right);
        } else {
            rightBoundary(root.left);
        }
        
        nodes.add(root.val); // from bottom to top - 
    }
    
    public void leaves(TreeNode root) {
        if (root == null) {
            return;
        }
        
        if (root.left == null && root.right == null) {
            nodes.add(root.val);
            return;
        }
        
        leaves(root.left);
        leaves(root.right);
    }
}


    // Tree Traversal
    public static void iterativePreOrder(TreeNode root) {
        if (root == null) {
            return;
        }

        Stack<TreeNode> s = new Stack<>();
        s.push(root);
        TreeNode p = null;
        while (!s.isEmpty()) {
            p = s.pop();
            System.out.println(p.getValue());
            if (p.getRight() !+ null) {
                s.push(p.getRight());
            }
            if (p.getLeft() != null) {
                s.push(p.getLeft());
            }
        }
    }

    public static void iterativeInOrder(TreeNode root) {
        if (root == null) {
            return;
        }

        Stack<TreeNode> s = new Stack<>();
        TreeNode current = root;
        while (true) {
            if (current != null) {
                s.push(current);
                current = current.getLeft();
                continue;
            }

            if (s.isEmpty()) {
                break;
            }

            current = s.pop();
            System.out.println(current.getValue());
            current = current.right;
        }
    }

    public List<Integer> postorderTraversalByIteration(TreeNode root) {
        List<Integer> result = new ArrayList<>();
        Stack<TreeNode> stack = new Stack<>();
        
        if (root == null) { // do not forget
            return result;
        }
        
        TreeNode curt = null, prev = null;
        
        stack.push(root);
        while (!stack.isEmpty()) {
            curt = stack.peek();
            // we are traversing down the tree
            if (prev == null || prev.left == curt || prev.right == curt) {
                if (curt.left != null) {
                    stack.push(curt.left);
                } else if (curt.right != null) {
                    stack.push(curt.right);
                }
            // we are traversing up the tree from the left
            } else if (curt.left == prev) {
                if (curt.right != null) {
                    stack.push(curt.right);
                }
            // we are traversing up the tree from the right    
            } else {
                result.add(curt.val);
                stack.pop();
            }
            prev = curt;
        }
        
        return result;
    }

    /**
     * Maximum Subtree of Average
     * 就是给一棵多叉树，表示公司内部的上下级关系。每个节点表示一个员工，节点包含的成员是他工作了几个月(int)，以及一个下属数组(ArrayList)
     * 目标就是找到一棵子树，满足：这棵子树所有节点的工作月数的平均数是所有子树中最大的。最后返回这棵子树的根节点
     * 这题补充一点，返回的不能是叶子节点(因为叶子节点没有下属)，一定要是一个有子节点的节点
     */
     class Node {
        int val;
        List<Node> children;

        public Node(int val) {
            this.val = val;
            children = new ArrayList<Node>();
        }
    }

    static class SumCount {
        int sum;
        int count;

        public SumCount(int sum, int count) {
            this.sum = sum;
            this.count = count;
        }
    }

    static Node ans;
    static double max = 0;
    public static Node find(Node root) {
        // Initialize static variables is very important for AMZAON OA!
        ans = null;
        max = 0;
        DFS(root);
        return ans;
    }

    private static SumCount DFS(Node root) {
        if (root == null) {
            return new SumCount(0, 0);
        }
        
        //////////// 这一块不写也可以的 ////////////
        if (root.children == null || root.children.isEmpty()) {
            return new SumCount(root.val, 1);
        }
        //////////////////////////////////////////

        // divde and conquer
        int sum = root.val;
        int count = 1;
        for (Node itr : root.children) {
            SumCount sc = DFS(itr);
            sum += sc.sum;
            count += sc.count;
        }
        // count > 1: 这题补充一点，返回的不能是叶子节点(因为叶子节点没有下属)，一定要是一个有子节点的节点
        if (count > 1 && ((double) sum) / count > max) {
            max = ((double) sum) / count;
            ans = root;
        }
        return new SumCount(sum, count);
    }
    


    /** Serialize and Deserialize tree; */
    // preorder serialization
    // preorder deserialization
/**
 * Definition for a binary tree node.
 * public class TreeNode {
 *     int val;
 *     TreeNode left;
 *     TreeNode right;
 *     TreeNode(int x) { val = x; }
 * }
 */
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

/**
 * Maximum Binary Tree 
Given an integer array with no duplicates. A maximum tree building on this array is defined as follow:
The root is the maximum number in the array.
The left subtree is the maximum tree constructed from left part subarray divided by the maximum number.
The right subtree is the maximum tree constructed from right part subarray divided by the maximum number.
Construct the maximum tree by the given array and output the root node of this tree.
Example 1:
Input: [3,2,1,6,0,5]
Output: return the tree root node representing the following tree:
  6
/   \
3     5
\    / 
 2  0   
   \
    1
Note:
The size of the given array will be in the range [1,1000].
*/

    // This question can be asked as 
    // Use input array to build a binary tree£¬satisfying max heap property 
    // and inorder traversal preserve original order of the array
    
    // thought: given an integer array, use recursion to construct the tree
    // first to find the index of maximum value in the array
    // second to do the recursion for the left part of the array and right part as well
    // the recursion returns the root node of those two subtrees - left and right subtrees
    // and they are left and right children of this current tree node.
    // lastly, return the root node.
    
    // use left and right index to specify the subarray this recursion method currently working on
    // the base case of the recursion is left index is equal to right index, which indicates
    // 
    
    // Basically, we are doing pre-order traversal to construct the maximum binary tree
    
    // 3,2,1,6,0,5
    //       6
    // 3         5   
    //   2     0
    //     1 
    public TreeNode constructMaximumBinaryTree(int[] nums) {
        return construct(nums, 0, nums.length - 1);
    }

    // l and r are inclusive
    // pre-order traversal
    private TreeNode construct(int[] nums, int l, int r) {
        if (l > r) {
            return null;
        }

        int maxIndex = max(nums, l, r);
        TreeNode root = new TreeNode(nums[maxIndex]);
        root.left = construct(nums, l, maxIndex - 1); 
        root.right = construct(nums, maxIndex + 1, r);
        return root;
    }

    private int max(int[] nums, int l, int r) {
        int maxIndex = l;
        // condition i <= r, since r is inclusive
        for (int i = l; i <= r; i++) {
            if (nums[maxIndex] < nums[i]) {
                maxIndex = i;
            }
        }

        return maxIndex;
    }



/*
Equal Tree Partition

Given a binary tree with n nodes, your task is to check if it's possible to partition the tree to two trees which have the equal sum of values after removing exactly one edge on the original tree.
*/

/**
 * Definition for a binary tree node.
 * public class TreeNode {
 *     int val;
 *     TreeNode left;
 *     TreeNode right;
 *     TreeNode(int x) { val = x; }
 * }
 */
class Solution {
    private Set<Integer> set = new HashSet<>();
    
    public boolean checkEqualTree(TreeNode root) {
        if (root == null) return false;
        int t = root.val + dfs(root.left) + dfs(root.right);
        // not doing dfs to root node, so that set will not contain sum of the entire tree
        // because the tree should be split into 2
        return (t % 2 == 0) && set.contains(t / 2);
    }
    private int dfs(TreeNode n) {
        if (n == null) return 0;
        int t = n.val + dfs(n.left) + dfs(n.right);
        set.add(t);
        return t;
    }
}    


/*
Split BST

Given a Binary Search Tree (BST) with root node root, and a target value V, split the tree into two subtrees where one subtree has nodes that are all smaller or equal to the target value, while the other subtree has all nodes that are greater than the target value.  It's not necessarily the case that the tree contains a node with value V.

Additionally, most of the structure of the original tree should remain.  Formally, for any child C with parent P in the original tree, if they are both in the same subtree after the split, then node C should still have the parent P.

You should output the root TreeNode of both subtrees after splitting, in any order.
*/

/**
 * Definition for a binary tree node.
 * public class TreeNode {
 *     int val;
 *     TreeNode left;
 *     TreeNode right;
 *     TreeNode(int x) { val = x; }
 * }
 */

/*
The idea is simple by recursion.

Just think about the current root, after processing, it will need to return [smaller/eq, larger] subtrees

if root.val <=V, all nodes under root.left(including root) will be in the smaller/eq tree,
we then split the root.right subtree into smaller/eq, larger, the root will need to concat the smaller/eq from the subproblem result (recursion).

Similarly for the case root.val>V

The runtime will be O(logn) if the input is balanced BST. Worst case is O(n) if it is not balanced.
*/
class Solution {
    public TreeNode[] splitBST(TreeNode root, int V) {
        if (root == null) {
            return new TreeNode[]{null, null};
        }    
        // compilation error:  return new TreeNode[2]{null, null};

        
        TreeNode[] splitted = new TreeNode[2];
        if (root.val <= V) {
            splitted = splitBST(root.right, V);
            root.right = splitted[0];
            splitted[0] = root;
        } else {
            splitted = splitBST(root.left, V);
            root.left = splitted[1];
            splitted[1] = root;
        }
        
        return splitted;
    }
}



/**
 * Definition for a binary tree node.
 * public class TreeNode {
 *     int val;
 *     TreeNode left;
 *     TreeNode right;
 *     TreeNode(int x) { val = x; }
 * }
 */
class Solution {
    public List<Integer> rightSideView(TreeNode root) {
        List<Integer> res = new ArrayList<>();
        if (root == null) {
            return res;
        }
        
        dfsRightSideView(root, res, 0);
        return res;        
    }
    
    // reverse pre-order traversal
    // root -> right -> left
    private void dfsRightSideView(TreeNode node, List<Integer> list, int depth) {
        if (node == null) {
            return;
        }
        
        // the first one we traverse
        if (depth == list.size()) {
            list.add(node.val);
        }
        
        dfsRightSideView(node.right, list, depth + 1);
        dfsRightSideView(node.left, list, depth + 1);
    }

    // pre-order traversal
    // root -> left -> right
    private void dfsLeftSideView(TreeNode node, List<Integer> list, int depth) {
        if (node == null) {
            return;
        }
        
        // the first one we traverse
        if (depth == list.size()) {
            list.add(node.val);
        }
        
        dfsLeftSideView(node.left, list, depth + 1);
        dfsLeftSideView(node.right, list, depth + 1);
    }
}

// Closest Leaf in a Binary Tree
/*
Given a binary tree where every node has a unique value, and a target key k, find the value of the nearest leaf node to target k in the tree.

Here, nearest to a leaf means the least number of edges travelled on the binary tree to reach any leaf of the tree. Also, a node is called a leaf if it has no children.

In the following examples, the input tree is represented in flattened form row by row. The actual root tree given will be a TreeNode object.

Example 3:

Input:
root = [1,2,3,4,null,null,null,5,null,6], k = 2
Diagram of binary tree:
             1
            / \
           2   3
          /
         4
        /
       5
      /
     6

Output: 3
Explanation: The leaf node with value 3 (and not the leaf node with value 6) is nearest to the node with value 2.


 */


/**
 * Definition for a binary tree node.
 * public class TreeNode {
 *     int val;
 *     TreeNode left;
 *     TreeNode right;
 *     TreeNode(int x) { val = x; }
 * }
 */

/**
 * Definition for a binary tree node.
 * public class TreeNode {
 *     int val;
 *     TreeNode left;
 *     TreeNode right;
 *     TreeNode(int x) { val = x; }
 * }
 */
class ResultType {
    TreeNode leaf;
    int distToLeaf;
    boolean exist;
    int distToTarget;
    public ResultType(TreeNode leaf, int distToLeaf, boolean exist, int distToTarget) {
        this.leaf = leaf;
        this.distToLeaf = distToLeaf;
        this.exist = exist;
        this.distToTarget = distToTarget;
    }
}

public class Solution {
    private int shortest = Integer.MAX_VALUE;
    private TreeNode shortestLeaf = null;
    private int k;
    public int findClosestLeaf(TreeNode root, int k) {
        this.k = k;
        helper(root);
        return shortestLeaf.val;
    }
    
    private ResultType helper(TreeNode root) {
        // initialize result first
        ResultType crt = new ResultType(null, Integer.MAX_VALUE, false, Integer.MAX_VALUE);
        if (root == null) {
            return crt;
        }

        ResultType left = helper(root.left);
        ResultType right = helper(root.right);

        // determined if it's a leaf by using leaf properties of left and right

        // .leaf is always the leaf that is closest to cur node (not related to target)
        // .distToLeaf is also the same
        
        if (left.leaf == null && right.leaf == null) {
            // current node is leaf
            crt.leaf = root;
            crt.distToLeaf = 0;
        } else {
           // record the shortest path to leaf in one of children route
            crt.leaf = left.distToLeaf <= right.distToLeaf ? left.leaf : right.leaf;
            crt.distToLeaf = left.distToLeaf <= right.distToLeaf ? left.distToLeaf + 1 : right.distToLeaf + 1;
        }

        // update these properties if target found

        // shortestLeaf
        // shortest
        if (root.val == k) {
            // if target found, record the shortest path to leaf in one of its children route
            shortestLeaf = crt.leaf;
            shortest = crt.distToLeaf;
            // start to mark target found, and start count the distance to target (increase level by level for its parents)
            crt.exist = true;
            crt.distToTarget = 0;
        } else if (left.exist || right.exist) {
            // if left child or right child contains target, meaning we have moved above the target
            crt.distToTarget = left.exist ? left.distToTarget + 1 : right.distToTarget + 1;
            crt.exist = true;
            // Since we have moved above the target node, we have to consider the 3rd path (which goes across the root node) 
            if (crt.distToTarget + crt.distToLeaf < shortest) {
                shortest = crt.distToTarget + crt.distToLeaf;
                shortestLeaf = crt.leaf;
            }
        } 
        return crt;
    }
}

        
        
// Maximum Width of Binary Tree
// The width of one level is defined as the length between the end-nodes (the leftmost and right most non-null nodes in the level.

    public int widthOfBinaryTree(TreeNode root) {
        if (root == null) {
            return 0;
        }
        
        return dfs(root, 0, 1, new ArrayList<>(), new ArrayList<>()); 
    }
    
    level: 
    // start from 0, considered as index for each level
    // used in start and end list

    // level的思路也可以运用于right slide window，求rightmost

    order:
    // a (full) binary tree can be represented as an array
    // parent i
    // left child 2 * i
    // right chile 2 * i + 1
    // 序列化tree nodes in full binary tree
    // 因而可以通过序列数算出两个end points的距离

    private int dfs(TreeNode root, int level, int order,
                     List<Integer> start,
                     List<Integer> end) {
        if (root == null) {
            return 0;
        }
        
        if (start.size() == level) {
            start.add(order);
            end.add(order);
        } else {
            end.set(level, order);
        }
        
        int cur = end.get(level) - start.get(level) + 1;
        int left = dfs(root.left, level + 1, 2 * order, start, end);
        int right = dfs(root.right, level + 1, 2 * order + 1, start, end);
        
        // 不一定是最深一层的距离最大，也可能是中间层的距离最大
        return Math.max(cur, Math.max(left, right));

    }


// Boundary of Binary Tree
    
/**
 * Definition for a binary tree node.
 * public class TreeNode {
 *     int val;
 *     TreeNode left;
 *     TreeNode right;
 *     TreeNode(int x) { val = x; }
 * }
 */
class Solution {
    List<Integer> res = new ArrayList<>();
    
    public List<Integer> boundaryOfBinaryTree(TreeNode root) {
        if (root == null) {
            return this.res;    
        }
        
        // root
        this.res.add(root.val);
        
        // left (not included root & leaf)
        leftBoundary(root.left);
        
        // leaf (left to right)
        leaves(root.left);
        leaves(root.right);
        
        // right (not included root & leaf)
        rightBoundary(root.right);
        
        return this.res;
    }
    
    private void leftBoundary(TreeNode node) {
        if (node == null ||
            (node.left == null && node.right == null)) {
            return;
        }
        
        this.res.add(node.val);
        
        if (node.left != null) {
            leftBoundary(node.left);
        } else {
            leftBoundary(node.right);
        }
    }
    
    private void rightBoundary(TreeNode node) {
        if (node == null ||
            (node.left == null && node.right == null)) {
            return;
        }
        
        if (node.right != null) {
            rightBoundary(node.right);
        } else {
            rightBoundary(node.left);
        }
        
        this.res.add(node.val);
    }
    
    private void leaves(TreeNode node) {
        if (node == null) {
            return;
        }

        if (node.left == null &&
            node.right == null) {
            this.res.add(node.val);
        }
        
        leaves(node.left);
        leaves(node.right);
    }
}

// Validate Binary Search Tree
/*
Given a binary tree, determine if it is a valid binary search tree (BST).

Assume a BST is defined as follows:

The left subtree of a node contains only nodes with keys less than the node's key.
The right subtree of a node contains only nodes with keys greater than the node's key.
Both the left and right subtrees must also be binary search trees.
Example 1:

Input:
    2
   / \
  1   3
Output: true
Example 2:

    5
   / \
  1   4
     / \
    3   6
Output: false
Explanation: The input is: [5,1,4,null,null,3,6]. The root node's value
             is 5 but its right child's value is 4.
 */
class ResultType {
    boolean isBst;
    int maxValue, minValue;
    
    public ResultType(boolean isBst, int minValue, int maxValue) {
        this.isBst = isBst;
        this.minValue = minValue;
        this.maxValue = maxValue;
    }
}

public boolean isValidBST(TreeNode root) {
    if (root == null) {
        return true;
    }
    
    return helper(root).isBst;
}

private ResultType helper(TreeNode root) {
    if (root == null) {
        return new ResultType(true, Integer.MIN_VALUE, Integer.MAX_VALUE);
        // remember this two values are fake values 
        // Integer.MIN_VALUE, Integer.MAX_VALUE
        // should not be propogated...
        // 1. compare root.val & left.maxValue & right.minValue
        // 2. generate new ResulType
    }
    
    ResultType left = helper(root.left);
    ResultType right = helper(root.right);
    
    if (!left.isBst || !right.isBst) {
        return new ResultType(false, 0, 0);
    }
    
    if (root.left != null && root.val <= left.maxValue || 
        root.right != null && root.val >= right.minValue) {
        return new ResultType(false, 0, 0);
    }
    
    return new ResultType(true, root.left == null ? root.val : left.minValue, 
                                root.right == null ? root.val : right.maxValue);
}


// Symmetric Tree
/*
Given a binary tree, check whether it is a mirror of itself (ie, symmetric around its center).

For example, this binary tree [1,2,2,3,4,4,3] is symmetric:

    1
   / \
  2   2
 / \ / \
3  4 4  3
But the following [1,2,2,null,3,null,3] is not:
    1
   / \
  2   2
   \   \
   3    3 
 */

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

// Closest Binary Search Tree Value
/*
Given a non-empty binary search tree and a target value, find the value in the BST that is closest to the target.

Note:

Given target value is a floating point.
You are guaranteed to have only one unique value in the BST that is closest to the target.
Example:

Input: root = [4,2,5,1,3], target = 3.714286

    4
   / \
  2   5
 / \
1   3

Output: 4 
 */
    private double res = Double.MAX_VALUE;
    private TreeNode node = null;
    
    public int closestValue(TreeNode root, double target) {
        if (root == null) {
            return 0;
        }
    
        helper(root, target);
        return this.node.val; // NullPointerException
    }
    
    private void helper(TreeNode root, double target) {
        if (root == null) {
            return;
        }
        
        helper(root.left, target);
        helper(root.right, target);
        
        if (this.res >= Math.abs(target - root.val)) {
            // >= to avoid NullPointerException
            this.res = Math.abs(target - root.val);    
            this.node = root;
        }
    }






// Binary Tree Vertical Order Traversal
/*
Given a binary tree, return the vertical order traversal of its nodes' values. (ie, from top to bottom, column by column).

If two nodes are in the same row and column, the order should be from left to right.

 */

Examples 3:

Input: [3,9,8,4,0,1,7,null,null,null,2,5] (0's right child is 2 and 1's left child is 5)

     3
    /\
   /  \
   9   8
  /\  /\
 /  \/  \
 4  01   7
    /\
   /  \
   5   2

Output:

[
  [4],
  [9,5],
  [3,0,1],
  [8,2],
  [7]
]

class Solution {
    // from top to bottom, "left to right", column by column
    // if multiple nodes are at the same location, they are ordered from left to right
    
    /*
     * BFS, put NODE, COL ID into queues at the same time
     * Every left child access col - 1 while right child col + 1
     * This maps node into different col buckets
     * Get col boundary min and max on the fly ?
     * Retrieve result from cols
     */
    public List<List<Integer>> verticalOrder(TreeNode root) {
        List<List<Integer>> res = new ArrayList<>();
        
        if (root == null) {
            return res;
        }
           
        // map: column id -> list of nodes
        Map<Integer, ArrayList<Integer>> map = new HashMap<>();
        
        // q and cols are used together
        Queue<TreeNode> q = new LinkedList<>();
        Queue<Integer> cols = new LinkedList<>();
 
        q.add(root); 
        cols.add(0);

        // min and max: col id 
        int min = 0;
        int max = 0;

        while (!q.isEmpty()) {
            TreeNode node = q.poll();
            int col = cols.poll();

            if (!map.containsKey(col)) {
                map.put(col, new ArrayList<Integer>());
            }
            map.get(col).add(node.val);

            if (node.left != null) {
                q.add(node.left); 
                cols.add(col - 1);
                min = Math.min(min, col - 1);
            }

            if (node.right != null) {
                q.add(node.right);
                cols.add(col + 1);
                max = Math.max(max, col + 1);
            }
        }

        for (int i = min; i <= max; i++) {
            res.add(map.get(i));
        }
        
        return res;
    }
}


// recursive solution for both getting the successor and predecessor for a given node in BST.

// Successor
/*
     2   
   1     , p = 1
   
  */
public TreeNode successor(TreeNode root, TreeNode p) {
  if (root == null)
    return null;

  if (root.val <= p.val) {
    return successor(root.right, p);
  } else {
    TreeNode left = successor(root.left, p);
    return (left != null) ? left : root;
  }
}

// Predecessor

public TreeNode predecessor(TreeNode root, TreeNode p) {
  if (root == null)
    return null;

  if (root.val >= p.val) {
    return predecessor(root.left, p);
  } else {
    TreeNode right = predecessor(root.right, p);
    return (right != null) ? right : root;
  }
}



Binary Search Tree iterativeInOrder

/*
Implement an iterator over a binary search tree (BST). Your iterator will be initialized with the root node of a BST.

Calling next() will return the next smallest number in the BST.

Note: next() and hasNext() should run in average O(1) time and uses O(h) memory, where h is the height of the tree.
*/

public class BSTIterator {
    // the top of stack always store the next node !!!!n 
    private Stack<TreeNode> stack; 
    
    public BSTIterator(TreeNode root) {
        stack = new Stack<>();
        TreeNode cur = root; 

        // all left node put into stack
        while (cur != null) {
            stack.push(cur);
            if (cur.left != null) {
                cur = cur.left;
            } else {
                break;
            }
        }
    }

    /** @return whether we have a next smallest number */
    public boolean hasNext() {
        return !stack.isEmpty();
    }

    /** @return the next smallest number */
    public int next() {
        TreeNode node = stack.pop();
        
        // traverse right branch because it is in-order traversal
        if (node.right != null) {
            TreeNode cur = node.right; 

            // same as above in constructor
            while (cur != null) {
                stack.push(cur); // push first
                if (cur.left != null) {
                    cur = cur.left;
                } else {
                    break;
                }
            }
        }
        
        return node.val;
    }
}



// Return any binary tree that matches the given preorder and postorder traversals.


/*
Some explanation:
The author uses the pre array to map all the elements in a tree.
The usage of post[] and j counter is to detect already visited nodes.
If we get the same element as the last element in the Deque and post[j] it indicates that we have visited all the children of that particular node and need to remove them from the queue.
 */
public TreeNode constructFromPrePost(int[] pre, int[] post) {
    // save the current path of tree.
    Deque<TreeNode> deque = new ArrayDeque<>();
    // ArrayDeque, LinkedList
    
    // offerFirst(a), offerLast(a)
    // pollFirst(), pollLast()
    // peekFirst(), peekLast()
    
    deque.offer(new TreeNode(pre[0]));
    for (int i = 1, j = 0; i < pre.length; i++) {
        TreeNode node = new TreeNode(pre[i]);
        
 // complete the construction for current subtree. We pop it from stack.
        while (deque.peekLast().val == post[j]) {
            deque.pollLast(); // poll the left child
            j++;
        }
        if (deque.peekLast().left == null) {
            deque.peekLast().left = node;
        } else {
            deque.peekLast().right = node;
        }
        deque.offerLast(node);
    }
    
    return deque.peekFirst();
}





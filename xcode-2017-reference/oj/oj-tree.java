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
    class Codec {

        private static final String NULL = "#"; // null child
        private static final String SEP = ","; // seperator
        /**
         * This method will be invoked first, you should design your own algorithm
         * to serialize a binary tree which denote by a root node to a string which
         * can be easily deserialized by your own "deserialize" method later.
         */
        public String serialize(TreeNode root) {
            if (root == null) {
                return "";
            }

            StringBuilder sb = new StringBuilder();
            // PreOrder traversal
            Stack<TreeNode> s = new Stack<>();
            s.push(root);
            while (!s.isEmpty()) {
                TreeNode cur = s.pop();
                if (cur == null) {
                    sb.append(NULL).append(SEP); // method chain!!
                    continue;
                }
                sb.append(cur.val).append(SEP);
                s.push(cur.right);
                s.push(cur.left);
            }

            sb.setLength(sb.length() - 1); // REMOVE LAST SEP
            return sb.toString();
        }

        /**
         * This method will be invoked second, the argument data is what exactly
         * you serialized at method "serialize", that means the data is not given by
         * system, it's given by your own serialize method. So the format of data is
         * designed by yourself, and deserialize it here as you serialize it in
         * "serialize" method.
         */
        public TreeNode deserialize(String data) {
            if (data == null || data.length() == 0) {
                return null;
            }

            StringTokenizer st = new StringTokenizer(data, SEP);
            return deseriaHelper(st);
        }

        private TreeNode deseriaHelper(StringTokenizer st) {
            if (!st.hasMoreTokens()) {
                return null;
            }

            String val = st.nextToken();
            if (val.equals(NULL)) {
                return null;
            }

            // preorder dfs traversal 
            TreeNode root = new TreeNode(Integer.parseInt(val));
            root.left = deseriaHelper(st);
            root.right = deseriaHelper(st);

            return root;
        }
    }



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
        return construct(nums, 0, nums.length);
    }

    // l is inclusive and r is exclusive
    // pre-order traversal
    private TreeNode construct(int[] nums, int l, int r) {
        // leaf node: l + 1 = r
        // base case: l = r, r is excluisve and l as well
        if (l == r) {
            return null;
        }

        int maxIndex = max(nums, l, r);
        TreeNode root = new TreeNode(nums[maxIndex]);
        root.left = construct(nums, l, maxIndex); // l is inclusive and maxIndex is exclusive
        root.right = construct(nums, maxIndex + 1, r); // maxIndex+1 is inclusive and r is exclusive
        return root;
    }

    private int max(int[] nums, int l, int r) {
        int maxIndex = l;
        // condition i < r, since r is exclusive
        for (int i = l; i < r; i++) {
            if (nums[maxIndex] < nums[i]) {
                maxIndex = i;
            }
        }

        return maxIndex;
    }















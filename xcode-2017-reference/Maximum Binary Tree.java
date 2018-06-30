

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
    
    Use input array to build a binary tree£¬satisfying max heap property and
	inorder traversal preserve original order of the array
	 */
	public TreeNode constructMaximumBinaryTree(int[] nums) {
		return construct(nums, 0, nums.length);
	}

	// l is inclusive and r is exclusive
	private TreeNode construct(int[] nums, int l, int r) {
		if (l == r) {
			return null;
		}

		int maxIndex = max(nums, l, r);
		TreeNode root = new TreeNode(nums[maxIndex]);
		root.left = construct(nums, l, maxIndex);
		root.right = construct(nums, maxIndex + 1, r);
		return root;
	}

	private int max(int[] nums, int l, int r) {
		int maxIndex = l;
		for (int i = l; i < r; i++) {
			if (nums[maxIndex] < nums[i]) {
				maxIndex = i;
			}
		}

		return maxIndex;
	}
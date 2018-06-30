	/**
	 * Unique Paths
	 * A robot is located at the top-left corner of a m x n grid
	 * (marked 'Start' in the diagram below). The robot can only move either
	 * down or right at any point in time. The robot is trying to reach the
	 * bottom-right corner of the grid (marked 'Finish' in the diagram below).
	 * How many possible unique paths are there?
	 */
	public int uniquePaths(int m, int n) {
		if (m == 0 || n == 0) {
			return 0;
		}

		int[] dp = new int[n];
		Arrays.fill(dp, 1);

		for (int i = m - 2; i >= 0; i--) {
			for (int j = n - 2; j >= 0; j--) {
				dp[j] += dp[j + 1];
			}
		}
		return dp[0];
	}

	/**
	 * Unique Paths II
	 * Follow up for "Unique Paths": Now consider if some
	 * obstacles are added to the grids. How many unique paths would there be?
	 * An obstacle and empty space is marked as 1 and 0 respectively in the
	 * grid. For example, There is one obstacle in the middle of a 3x3 grid as
	 * illustrated below. [ [0,0,0], [0,1,0], [0,0,0] ]
	 * 
	 * The total number of unique paths is 2.
	 */
	int uniquePathsWithObstacles(int[][] obstacleGrid) {
		int m = obstacleGrid.length;
		if (m == 0) {
			return 0;
		}

		int n = obstacleGrid[0].length;
		if (n == 0) {
			return 0;
		}

		int[] dp = new int[n + 1]; // extra column on the right filled with 0
		int k = n - 1;
		while (k >= 0 && obstacleGrid[m - 1][k] == 0) {
			dp[k--] = 1; // last line will be something like 0000011111
		}

		for (int i = m - 2; i >= 0; i--) {
			for (int j = n - 1; j >= 0; j--) {
				dp[j] = obstacleGrid[i][j] == 1 ? 0 : dp[j] + dp[j + 1];
			}
		}
		return dp[0];
	}

	/**
	 * Linked List Random Node
Given a singly linked list, return a random node's value from the linked list. Each node must have the same probability of being chosen.

Follow up:
What if the linked list is extremely large and its length is unknown to you? Could you solve this efficiently without using extra space?

Example:

// Init a singly linked list [1,2,3].
ListNode head = new ListNode(1);
head.next = new ListNode(2);
head.next.next = new ListNode(3);
Solution solution = new Solution(head);

// getRandom() should return either 1, 2, or 3 randomly. Each element should have equal probability of returning.
solution.getRandom();
Reservoir_sampling
O(n) Time & O(1) Space
	 */
	public class Solution {

		private final ListNode head;
		private Random random;

		/**
		 * @param head
		 *            The linked list's head. Note that the head is guanranteed
		 *            to be not null, so it contains at least one node.
		 */
		public Solution(ListNode head) {
			this.head = head;
			this.random = new Random();
		}

		/** Returns a random node's value. */
		public int getRandom() {
			ListNode result = null;
			ListNode current = this.head;

			for (int n = 1; current != null; n++) {
				if (this.random.nextInt(n) == 0) {
					result = current;
				}
				current = current.next;
			}

			return result.val;
		}
	}
	/**
	 * Random Pick Index
 Given an array of integers with possible duplicates, randomly output the index of a given target number. You can assume that the given target number must exist in the array.

Note:
The array size can be very large. Solution that uses too much extra space will not pass the judge.

Example:

int[] nums = new int[] {1,2,3,3,3};
Solution solution = new Solution(nums);

// pick(3) should return either index 2, 3, or 4 randomly. Each index should have equal probability of returning.
solution.pick(3);

// pick(1) should return 0. Since in the array only nums[0] is equal to 1.
solution.pick(1);
 * Your Solution object will be instantiated and called as such:
 * Solution obj = new Solution(nums);
 * int param_1 = obj.pick(target);
	 */
	public class Solution3 {

		private int[] nums;
		private Random rnd;

		public Solution3(int[] nums) {
			this.nums = nums; // not using additional space
			this.rnd = new Random();
		}

		public int pick(int target) {
			int result = -1;
			int count = 0;
			for (int i = 0; i < this.nums.length; i++) {
				if (this.nums[i] != target) {
					continue;
				}
				// Reservoir Sampling
				if (this.rnd.nextInt(++count) == 0) {
					result = i;
				}
			}

			return result;
		}
	}
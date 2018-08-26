
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

	// thought: basic idea is to use reservoir sampling to pick the random index for a given target number
 	// scan number array from left to right, if it is target number, i increase the count by 1, (count is used to count the target number we have so far), 
 	// and use random instance to generate a random integer from 0 to count - 1, if it is 0, based on reservoir sampling,
 	// we switch the value of those two position - 0 and current index, means the value of the current index is being picked.
 	// in this case, we only store the index in result
	public class Solution3 {

		private int[] nums;
		private Random rnd;

		public Solution3(int[] nums) {
			this.nums = nums; // not using additional space
			this.rnd = new Random();
		}

		public int pick(int target) {
			int result = -1;
			int count = 0; // using count for target number, different from index
			for (int i = 0; i < this.nums.length; i++) {
				if (this.nums[i] != target) {
					continue;
				}
				// Reservoir Sampling - in this case, we pick only one random value, so only check if it equals to 0
				if (this.rnd.nextInt(++count) == 0) {
					result = i;
				}
			}

			return result;
		}
	}

  /**
	Linked List Random Node
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
				// similar to random pick index as above
				if (this.random.nextInt(n) == 0) {
					result = current;
				}
				current = current.next;
			}

			return result.val;
		}
	}



	
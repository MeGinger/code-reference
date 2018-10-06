    /**
     * Sliding Window Maximum
	Given an array nums, there is a sliding window of size k which is moving from the very left of the array to the very right.
	You can only see the k numbers in the window. Each time the sliding window moves right by one position.
	For example,
	Given nums = [1,3,-1,-3,5,3,6,7], and k = 3.
	Window position                Max
	---------------               -----
	[1  3  -1] -3  5  3  6  7       3
	 1 [3  -1  -3] 5  3  6  7       3
	 1  3 [-1  -3  5] 3  6  7       5
	 1  3  -1 [-3  5  3] 6  7       5
	 1  3  -1  -3 [5  3  6] 7       6
	 1  3  -1  -3  5 [3  6  7]      7
	sliding windoe: (i-k, i) i++
	decreasing:
	LinkedList = 3, -1
	请用例子讲思路，每个loop, LinkedList的变化，另外给出有duplicate的例子更好，因为相等的需要留下
	Therefore, return the max sliding window as [3,3,5,5,6,7].
	You may assume k is always valid, 1 鈮� k 鈮� input array's size
     */

    /* 
     LinkedList
     - getLast(), addLast(a), removeLast()
     - getFirst(), addFirst(a), removeFirst()
     */
    public int[] maxSlidingWindow(int[] nums, int k) {
        LinkedList<Integer> l = new LinkedList<>();
        int n = nums.length;
        if (n == 0 || k == 0) {
            return new int[0];
        }
        int[] result = new int[n - k + 1];

        for (int i = 0; i < k; i++) { // First fill in result[0] ~ result[k-1]
            addToSlidingWindow(nums[i], l);
        }

        for (int i = k; i < n; i++) {
            // take care of the previous window
            result[i - k] = l.getFirst(); 
            if (nums[i - k] == l.getFirst()) {
                l.removeFirst();
            }

            // build new window
            addToSlidingWindow(nums[i], l);
        }
	// takes care of last one
        result[n - k] = l.getFirst();

	return result;
    }
    
    private void addToSlidingWindow(int a, LinkedList<Integer> l) {
        while (!l.isEmpty() && l.getLast() < a) {
            l.removeLast();
        }
        l.addLast(a);
    }

    // window sum
    // presum[i] is sum of nums[i ~ i + k - 1]
    public static int[] getPresum(int[] nums, int k) {
        if (nums == null || nums.length < k || k <= 0) {
            return null;
        }
        int n = nums.length;
        if (n < k) {
            throw new IllegalArgumentException();
        }
        int[] presum = new int[n - k + 1];
        int sum = 0;
        for (int i = 0; i < n; i++) {
            if (i >= k) { // handle the previous sum; i == k, we have first presum nums[0...k - 1]                 
                presum[i - k] = sum;
                sum -= nums[i - k];
            }
            sum += nums[i];
        }
        presum[n - k] = sum;
        return presum;
    }

    public int findLargestSumIn3NonOverlappingSubarrays(int[] nums, int k) {
        int n = nums.length;
        if (n < 3 * k) {
            return 0;
        }
        int[] presum = getPresum(nums, k);
        int[] maxFromLeft = getMaxFromLeft(nums, k, presum);
        int[] maxFromRight = getMaxFromRight(nums, k, presum);
        int result = Integer.MIN_VALUE;
        for (int i = k; i + 2 * k <= n; i++) { // [...i-k, i, i+k] i+2k, i+2k = n
            result = Math.max(result, maxFromLeft[i - k] + presum[i] + maxFromRight[i + k]);
        }

        return result;
    }

    private int[] getMaxFromLeft(int[] nums, int k, int[] presum) {
        int n = nums.length;
        int[] maxFromLeft = new int[n - 3 * k + 1];
        maxFromLeft[0] = presum[0];
        int max = presum[0];
        for (int i = 1; i <= n - 3 * k; i++) {
            max = Math.max(max, presum[i]);
            maxFromLeft[i] = max;
        }
        return maxFromLeft;
    }

    private int[] getMaxFromRight(int[] nums, int k, int[] presum) {
        int n = nums.length;
        int[] maxFromRight = new int[n - k + 1];
        maxFromRight[n - k] = presum[n - k];
        int max = presum[n - k];
        for (int i = n - k - 1; i >= 2 * k; i--) {
            max = Math.max(max, presum[i]);
            maxFromRight[i] = max;
        }
        return maxFromRight;
    }

public class Solution {
    
    // max sum
    // index
    class Wrapper {
        int index;
        int max;
        
        public Wrapper(int max, int index) {
            this.max = max;
            this.index = index;
        }
    }
    
    public int[] maxSumOfThreeSubarrays(int[] nums, int k) {
        if (nums == null || nums.length == 0) {
            return new int[]{};
        }
        
        int n = nums.length;
        if (n < 3 * k) {
            return new int[0];
        }
        
        int[] presum = getPresum(nums, k);
        Wrapper[] maxFromLeft = getMaxFromLeft(nums, k, presum);
        Wrapper[] maxFromRight = getMaxFromRight(nums, k, presum);
        
        int max = Integer.MIN_VALUE;
        int[] res = new int[3];
        for (int i = k; i + 2 * k <= n; i++) {
            int sum = maxFromLeft[i - k].max + presum[i] + maxFromRight[i + k].max;
            if (max < sum) {
                max = sum;
                res[0] = maxFromLeft[i - k].index;
                res[1] = i;
                res[2] = maxFromRight[i + k].index;
            }
        }
        
        return res;
    }
    
    private Wrapper[] getMaxFromLeft(int[] nums, int k, 
                                 int[] presum) {
        int n = nums.length;
        Wrapper[] maxFromLeft = new Wrapper[n - 3 * k + 1];
        maxFromLeft[0] = new Wrapper(presum[0], 0);
        
        Wrapper maxWrapper = maxFromLeft[0];
        for (int i = 1; i <= n - 3 * k; i++) {
            if (maxWrapper.max < presum[i]) {
                maxFromLeft[i] = new Wrapper(presum[i], i);    
                maxWrapper = maxFromLeft[i];
            } else {
                maxFromLeft[i] = new Wrapper(maxWrapper.max, maxWrapper.index);    
            }
        }
        return maxFromLeft;
    }
    
    private Wrapper[] getMaxFromRight(int[] nums, int k, 
                                  int[] presum) {
        int n = nums.length;
        Wrapper[] maxFromRight = new Wrapper[n - k + 1];
        maxFromRight[n - k] = new Wrapper(presum[n - k], n - k);
        
        Wrapper maxWrapper = maxFromRight[n - k];
        for (int i = n - k - 1; i >= 2 * k; i--) {
            if (maxWrapper.max < presum[i]) {
                maxFromRight[i] = new Wrapper(presum[i], i); 
                maxWrapper = maxFromRight[i];
            } else {
                maxFromRight[i] = new Wrapper(maxWrapper.max, maxWrapper.index);    
            }
        }
        return maxFromRight;
    }
    
    private int[] getPresum(int[] nums, int k) {
        int[] presum = new int[nums.length];
        
        for (int i = 0; i < k; i++) {
            presum[0] += nums[i];
        }
        
        for (int i = 1; i < nums.length - (k - 1); i++) {
            presum[i] = 
                presum[i - 1] - nums[i - 1] + nums[i + k - 1];
        }
        
        return presum;
    }
    
    
}



import java.util.ArrayDeque;
import java.util.Deque;

public class KEmptySlot {
  /**
    * 
    K Empty Slots
    There is a garden with N slots. In each slot, there is a flower. The N flowers will bloom one by one in N days. In each day, there will be exactly one flower blooming and it will be in the status of blooming since then.
    Given an array flowers consists of number from 1 to N. Each number in the array represents the place where the flower will open in that day.
    For example, flowers[i] = x means that the unique flower that blooms at day i will be at position x, where i and x will be in the range from 1 to N.
    Also given an integer k, you need to output in which day there exists two flowers in the status of blooming, and also the number of flowers between them is k and these flowers are not blooming.
    If there isn't such day, output -1.
    Example 1:
    Input: 
    flowers: [1,3,2] - flower 1 blooms at day 1; flower 3 blooms at day 2; flower 2 blooms at day 3; 
    k: 1
    Output: 2 - day 2
    Explanation: In the second day, the first and the third flower have become blooming.

    Example 2:
    Input: 
    flowers: [1,2,3]
    k: 1
    Output: -1

    Note:
    The given array will be in the range [1, 20000].

    Time Complexity: O(N), where N is the length of flowers. In enumerating through the O(N) outer loop, we do constant work as MinQueue.popleft and MinQueue.min operations are (amortized) constant time.
    Space Complexity: O(N), the size of our window.
    Reference: Sliding Window Maximum
    */
    
    // FIND a day, k = 3, like this 
    // *: blooming, -: not blooming
    /*
       * - - - *   
       | | | | |   
     */
    public int kEmptySlots(int[] flowers, int k) {
        int[] days = new int[flowers.length];
        for (int i = 0; i < flowers.length; i++) {
            days[flowers[i] - 1] = i + 1;  // day: i + 1, plus one, so start from 1 to N
					   // position: start from 0 to N - 1
            // day[0] = d: flower at position 1 booms at day d
        }

        MinQueue<Integer> window = new MinQueue<>();
        int ans = days.length; // impossible answer, but the largest limit

        // we can skip last flower
        for (int i = 0; i < days.length - 1; i++) {
            int day = days[i];
            window.addLast(day);
            if (i < k) {
                continue;
            }
            window.pollFirst();
            // The window is maintained as [i - k + 1, i]
	    // actually are position window ....
            if (k == 0 || days[i - k] < window.min() && days[i + 1] < window.min()) { // min
                ans = Math.min(ans, Math.max(days[i - k], days[i + 1])); // max
            }
        }

        return ans < days.length ? ans : -1;
    }
    
    
    private static class MinQueue<E extends Comparable<E>> extends ArrayDeque<E> {
        private static final long serialVersionUID = 1538439138203379928L;
        private Deque<E> mins;

        public MinQueue() {
            this.mins = new ArrayDeque<>();
        }

        @Override
        public void addLast(E x) {
            super.addLast(x);
            while (!this.mins.isEmpty() && x.compareTo(this.mins.peekLast()) < 0) {
                this.mins.pollLast();
            }
            this.mins.addLast(x);
        }

        @Override
        public E pollFirst() {
            E x = super.pollFirst();
            if (x == this.mins.peekFirst()) {
                this.mins.pollFirst();
            }
            return x;
        }

        public E min() {
            return this.mins.peekFirst();
        }
    }
}



    // Contiguous Array (leetcode 525)
    // problem: Given a binary array, find the maximum length of a contiguous subarray with equal number of 0 and 1.
    // Example 1:
    // Input: [0,1]
    // Output: 2
    // Explanation: [0, 1] is the longest contiguous subarray with equal number of 0 and 1.
    // Example 2:
    // Input: [0,1,0]
    // Output: 2
    // Explanation: [0, 1] (or [1, 0]) is a longest contiguous subarray with equal number of 0 and 1.
    // Note: The length of the given binary array will not exceed 50,000.

    // The idea is to change 0 in the original array to -1. Thus, if we find SUM[i, j] == 0 then we know there are even number of -1 and 1 between index i and j. Also put the sum to index mapping to a HashMap to make search faster.

    public int findMaxLength(int[] nums) {
        // 改input不好
        // for (int i = 0; i < nums.length; i++) {
        //     if (nums[i] == 0) nums[i] = -1;
        // }
        
        Map<Integer, Integer> sumToIndex = new HashMap<>();
        sumToIndex.put(0, -1); // i - index = i - (-1) = i + 1
        int presum = 0, max = 0;
        
        for (int i = 0; i < nums.length; i++) {
            presum += (nums[i] == 1 ? 1 : -1); // 不用改input
            if (sumToIndex.containsKey(presum)) { 
                // .containsKey means sum of values at index from map.get(presum) + 1 to i IS 0
                max = Math.max(max, i - sumToIndex.get(presum));
                // sumToIndex: sum to smallest index mapping, so no need to update index
            }
            else {
                sumToIndex.put(presum, i);
            }
        }
        
        return max;
    }

    // longest palindrome substring
    public String longestPalindrome(String s) {
        int len = s.length(); 
        while (len >= 1) {  // len at least 1, otherwise empty string
            // len is the length of longest palindrome substring
            // starts from the largest

            // left bound is i
            // right bound is (i + len - 1)
            for (int i = 0; i + len - 1 < s.length(); i++) {
                int left = i;
                int right = i + len - 1;
                boolean good = true;
                while (left < right) {
                    if (s.charAt(left) != s.charAt(right)) {
                        good = false;
                        break;
                    }
                    left++;
                    right--;
                }
                if (good) {
                    return s.substring(i, i + len);
                }
            }
            len--;
        }
        return "";
    }




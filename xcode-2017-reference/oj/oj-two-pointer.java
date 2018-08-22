

	/**
     * Valid Triangle Number 
	Given an array consists of non-negative integers, your task is to count the number of triplets chosen from the array that can make triangles if we take them as side lengths of a triangle.

	Example 1:

	Input: [2,2,3,4]
	Output: 3
	Explanation:
	Valid combinations are: 
	2,3,4 (using the first 2)
	2,3,4 (using the second 2)
	2,2,3

	Note:

    The length of the given array won't exceed 1000.
    The integers in the given array are in the range of [0, 1000].

	 */

    // thought: sort the input numbers in ascending order
    // scan the sorted numbers from right to left, which means I start from the largest number, 
    // and every number I Iterate can be considered as a sentinel
    // for the left subarray, I use two pointers - left and right pointers to check if the sum of two values at the left and right
    // is larger than the value of sentinel. If it is, we have a valid combination - left, right and sentinel
    // since the numbers is sorted, the numbers in the right side of the left pointers in the subarray are always larger than 
    // the value of left pointer, so the condition is always satisifiied
    // nums[l...r-1] + nums[r] > nums[i] -> r-l valid combinations -> r--
    // if the condition is not satisified - nums[l] + nums[r] <= nums[i] -> l++
	public int triangleNumber(int[] nums) {
		Arrays.sort(nums);
		int count = 0, n = nums.length;
		for (int i = n - 1; i >= 2; i--) {
			int l = 0, r = i - 1;
			while (l < r) {
				if (nums[l] + nums[r] > nums[i]) {
					// [l, l+1, l+2, ..., r-1, r]
					// <l, r>, <l+1, r>, <l+2, r>, ..., <r-1, r>
					
					// nums[l...r-1] + nums[r] > nums[i] 

					count += r - l;
					r--;
				} else {
					l++;
				}
			}
		}
		return count;
	}
	// runtime: O(nlogn) + O(n^2)
	// best: O(n^2)
	// worst: O(n^2)




    // longest substring palindrome of string s
    public String longestPalindrome(String s) {
    	// "abbac"
        int len = s.length();
        while (len >= 0) { // len > 0, since len == 0 => ""
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
                    return s.substring(i, i + len); // early return - longest
                }
            }
            --len;
        }
        return "";
    }


    /* Find K Closest Elements
 * Given a sorted array, two integers k and x, find the k closest elements to x in the array. 
 * The result should also be sorted in ascending order. 
 * If there is a tie, the smaller elements are always preferred.
 * 
 * Example 1:
 * Input: [1,2,3,4,5], k=4, x=3
 * Output: [1,2,3,4]  pick 1 instead of 5
 * 
 * Example 2:
 * Input: [1,2,3,4,5], k=4, x=-1
 * Output: [1,2,3,4]
 * 
 * Note:
 * The value k is positive and will always be smaller than the length of the sorted array.
 * Length of the given array is positive and will not exceed 104
 * Absolute value of elements in the array and x will not exceed 104
 */

 // input array arr is sorted
 // If there is a tie, the smaller elements are always preferred.
public class FindKClosestElements {
    public List<Integer> findClosestElements(List<Integer> arr, int k, int x) {
        int n = arr.size();
        if (x <= arr.get(0)) {
            return arr.subList(0, k);
        } else if (arr.get(n - 1) <= x) {
            return arr.subList(n - k, n);
        } else {
            int index = Collections.binarySearch(arr, x);
            if (index < 0)
                index = -index - 1;
            int low = Math.max(0, index - k - 1), high = Math.min(arr.size() - 1, index + k - 1);

            while (high - low > k - 1) {
				// is it possible that low < 0? Impossible verified by Leetcode
				
				// what if (x - arr.get(low)) == (arr.get(high) - x)?
                // smaller one is preferred
				if (low < 0 || (x - arr.get(low)) <= (arr.get(high) - x)) 
                    high--;
				// is it possible that high > ...? Impossible verified by Leetcode
                else if (high > arr.size() - 1 || (x - arr.get(low)) > (arr.get(high) - x))
                    low++;
                else
                    System.out.println("unhandled case: " + low + " " + high);
            }
            return arr.subList(low, high + 1);
        }
    }
}

public class FindKClosestElements {
    public List<Integer> findClosestElements(int[] arr, int k, int x) {
        int n = arr.length;
        if (x <= arr[0]) {
            return arr.subList(0, k);
        } else if (arr[n - 1] <= x) {
            return arr.subList(n - k, n);
        } else {
            int index = Collections.binarySearch(arr, x);
            if (index < 0)
                index = -index - 1;
            int low = Math.max(0, index - k - 1), high = Math.min(arr.size() - 1, index + k - 1);

            while (high - low > k - 1) {
				// is it possible that low < 0? Impossible verified by Leetcode
                if (low < 0 || (x - arr[low]) <= (arr[high] - x)) 
                    high--;
				// is it possible that high > ...? Impossible verified by Leetcode
                else if ((x - arr[low]) > (arr[high] - x))
                    low++;
                else
                    System.out.println("unhandled case: " + low + " " + high);
            }
            return arr.subList(low, high + 1);
        }
    }
}

/**
 * Given an integer array, find number of subsets that its sum of min and max is less than k.
 *
 * Refer: Valid Triangle Number 
 */
 
 // Use two pointers to find [min, max], then the subsets need to include min
 // while max is optional, since we can pick max from the range from [min+1, max]
	public int numOfSubsetsWithMinAndMaxSumLessThanK(int[] nums, int k) {
		Arrays.sort(nums);
		int count = 0;
		int l = 0, r = nums.length - 1;
		while (l <= r) {
			if (nums[l] + nums[r] >= k) {
				r--;
				continue;
			}
			// l needs to be included while we have [l + 1 to r] possibility to picked as max
			// l, [l+1, ..., r-1, r]
			int n = r - l; // r - (l + 1) + 1;
			count += 1 << n; // 2 ^ (n)
			l++;
		}
		return count;
	}
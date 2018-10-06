

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

// Find the Duplicate Number
/*
Given an array nums containing n + 1 integers where each integer is between 1 and n (inclusive), prove that at least one duplicate number must exist. Assume that there is only one duplicate number, find the duplicate one.

Example 1:

Input: [1,3,4,2,2]
Output: 2
Example 2:

Input: [3,1,3,4,2]
Output: 3
Note:

You must not modify the array (assume the array is read only).
You must use only constant, O(1) extra space.
Your runtime complexity should be less than O(n2).
There is only one duplicate number in the array, but it could be repeated more than once.
*/
class Solution {
    public int findDuplicate(int[] nums) {
        // Similar to find loop in linkedlist
        // slow fast two pointers
        
        // Each integer is between 1 and n (inclusive)
        if (nums.length <= 1) {
            return -1;
        }
        
        int slow = nums[0];
        int fast = nums[nums[0]];

        // meet in the circle
        while (slow != fast) {
            slow = nums[slow];
            fast = nums[nums[fast]];
        }
        
        // find the entry point
        slow = 0;
        while (fast != slow) {
            fast = nums[fast];
            slow = nums[slow];
        }
        return slow;
    }
}


// TWO POINTER - GOING TOGETHER

Fruit Into Baskets

class Solution {
    // the i-th tree produces fruit with type tree[i]
    
    // "Start from any index, we can collect at most two types of fruits. What is the maximum amount"
    // Find out the longest length of subarrays with at most 2 different numbers?
    public int totalFruit(int[] tree) {
        if (tree == null || tree.length == 0) {
            return 0;
        }
        
        int i = 0, res = 0, len = tree.length;
        Map<Integer, Integer> count = new HashMap<>();
        for (int j = 0; j < len; j++) {
            count.put(tree[j], count.getOrDefault(tree[j], 0) + 1);
            
            while (count.size() > 2) {
                int newCount = count.get(tree[i]) - 1;
                if (newCount == 0) {
                    count.remove(tree[i]);
                } else {
                    count.put(tree[i], newCount);
                }
                i++;
            }
            // current subarray is tree[i...j]
            res = Math.max(res, j - i + 1);
        }
        
        return res;
    }
}

Strobogrammatic Number: A strobogrammatic number is a number that looks the same when rotated 180 degrees (looked at upside down).

Strobogrammatic Number I
- Is a number strobogrammatic?

    public boolean isStrobogrammatic(String num) {
        if (num == null || num.length() == 0) {
            return true;
        }
        
        char[] chars = num.toCharArray();
        for (int i = 0, j = chars.length - 1; i <= j; i++, j--) {
            if (!valid(chars[i], chars[j])) {
                return false;
            }
        }
        
        return true;
    }
    
    private boolean valid(char a, char b) {
        // pairs: {6,9}
        // elements: 1, 8, 0
        if (a == '1' && b == '1' ||
            a == '8' && b == '8' ||
            a == '0' && b == '0' ||
            a == '6' && b == '9' ||
            a == '9' && b == '6') {
            return true;
        }
        return false;
    }


Strobogrammatic Number II 
- Find all strobogrammatic numbers that are of length = n.

    public List<String> findStrobogrammatic(int n) {
        return get(n, n);
    }
    
    // divide and conquer
    private List<String> get(int len, int totalLen) {
        if (len == 0) {
            return new ArrayList<>(Arrays.asList(""));
        }
        if (len == 1) {
            return new ArrayList<>(Arrays.asList("0", "1", "8"));
        }
        
        List<String> list = get(len - 2, totalLen);
        
        List<String> res = new ArrayList<>();
        
        for (String s : list) {
            
            // if len == totalLen, 
            // starting and ending characters should not be 0
            if (len != totalLen) { 
                res.add("0" + s + "0");
            }
            
            res.add("1" + s + "1");
            res.add("8" + s + "8");
            res.add("6" + s + "9");
            res.add("9" + s + "6");
        }
        
        return res;
    }


Strobogrammatic Number III
- Count the total strobogrammatic numbers that exist in the range of low <= num <= high.

    public int strobogrammaticInRange(String low, String high){
        int count = 0;
        List<String> rst = new ArrayList<String>();
        for(int n = low.length(); n <= high.length(); n++){
            rst.addAll(get(n, n));
        }
        
        for(String num : rst){    
            if ((num.length() == low.length() && 
                 num.compareTo(low) < 0 ) ||
                (num.length() == high.length() && 
                 num.compareTo(high) > 0)) {
                continue;
            }
            
            count++;
        }
        return count;
    }

    // divide and conquer
    private List<String> get(int len, int totalLen) {
        if (len == 0) {
            return new ArrayList<>(Arrays.asList(""));
        }
        if (len == 1) {
            return new ArrayList<>(Arrays.asList("0", "1", "8"));
        }
        
        List<String> list = get(len - 2, totalLen);
        
        List<String> res = new ArrayList<>();
        
        for (String s : list) {
            
            // if len == totalLen, 
            // starting and ending characters should not be 0
            if (len != totalLen) { 
                res.add("0" + s + "0");
            }
            
            res.add("1" + s + "1");
            res.add("8" + s + "8");
            res.add("6" + s + "9");
            res.add("9" + s + "6");
        }
        
        return res;
    }

Strobogrammatic Number III 变形
- Find strobogrammatic numbers
* that exist in the range of low <= num <= high.
* that the transformed number cannot be itself
* that the transformed number should also be in the range

    public int strobogrammaticInRange(String low, String high){
        int count = 0;
        List<String> rst = new ArrayList<String>();
        for(int n = low.length(); n <= high.length(); n++){
            rst.addAll(get(n, n));
        }
        
        for(String num : rst){    
            String transfomed = getTransformed(num);

            if (transfomed== num) {
                continue;
            }

            if ((num.length() == low.length() && 
                 num.compareTo(low) < 0 ) ||
                (num.length() == high.length() && 
                 num.compareTo(high) > 0)) {
                continue;
            }
            
            if ((transfomed.length() == low.length() && 
                 transfomed.compareTo(low) < 0 ) ||
                (transfomed.length() == high.length() && 
                 transfomed.compareTo(high) > 0)) {
                continue;
            }

            count++;
        }
        return count;
    }

    // divide and conquer
    private List<String> get(int len, int totalLen) {
        if (len == 0) {
            return new ArrayList<>(Arrays.asList(""));
        }
        if (len == 1) {
            return new ArrayList<>(Arrays.asList("0", "1", "8"));
        }
        
        List<String> list = get(len - 2, totalLen);
        
        List<String> res = new ArrayList<>();
        
        for (String s : list) {
            
            // if len == totalLen, 
            // starting and ending characters should not be 0
            if (len != totalLen) { 
                res.add("0" + s + "0");
            }
            
            res.add("1" + s + "1");
            res.add("8" + s + "8");
            res.add("6" + s + "9");
            res.add("9" + s + "6");
        }
        
        return res;
    }












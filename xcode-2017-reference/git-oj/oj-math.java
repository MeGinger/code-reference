// Product of Array Except Self
/*
Given an array nums of n integers where n > 1,  return an array output such that output[i] is equal to the product of all the elements of nums except nums[i].

Example:

Input:  [1,2,3,4]
Output: [24,12,8,6]
Note: Please solve it without division and in O(n).

Follow up:
Could you solve it with constant space complexity? (The output array does not count as extra space for the purpose of space complexity analysis.)
*/

// solve it without division and in O(n)
public int[] productExceptSelf(int[] nums) {
    int n = nums.length;
    int[] res = new int[n];
    res[0] = 1; 
    for (int i = 1; i < n; i++) {
        res[i] = res[i - 1] * nums[i - 1];
    }
    /*
        res[0] = 1
        res[1] = nums[0]
        res[2] = nums[0] * nums[1]
        res[3] = nums[0] * nums[1] * nums[2] 
        ..
        res[n-2] = nums[0] * nums[1] * nums[2] * ... * nums[n-3]
        res[n-1] = nums[0] * nums[1] * nums[2] * ... * nums[n-3] * nums[n-2]
     */
    /*
        n-1 right = 1
        n-2 right = nums[i]
        ... right = nums[i] * nums[i - 1]
        ... ...
        0   right = nums[i] * nums[i - 1] * ... * nums[1]
     */
    int right = 1;
    for (int i = n - 1; i >= 0; i--) {
        res[i] *= right;
        right *= nums[i];
    }
    return res;
}


class Solution {
    // input is guaranteed to be non-negative and less than 2^31 - 1.
    // at most billion 
    // int -2,147,483,648 to 2,147,483,647 
    //                        b   m   t
    
    // Easily misspelled words:
    // Ninety
    
    
    /*
     index:    1, 2, ..., 9
     numner:   1, 2, ..., 9
     */
    private final String[] belowTen = {
      "", "One", "Two", "Three", "Four", 
      "Five", "Six", "Seven", "Eight", "Nine"
    };
    /* 
     index:    0,  1,  ..., 9
     number:   10, 11, ..., 19
     */
    private final String[] belowTwenty = {
      "Ten", "Eleven", "Twelve", "Thirteen", "Fourteen",
      "Fifteen", "Sixteen", "Seventeen", "Eighteen", "Nineteen"
    };
    /*
     index:    0, 1,  ..., 9
     number:      10, .... 90
     */
    private final String[] belowHundred = {
      "", "Ten", "Twenty", "Thirty", "Forty", 
      "Fifty", "Sixty", "Seventy", "Eighty", "Ninety"
    };
    
    public String numberToWords(int num) {
        if (num == 0) {
            return "Zero";
        }
        return helper(num);
    }
    
    private String helper(int num) {
        StringBuilder sb = new StringBuilder();
        if (num < 10) {
            sb.append(belowTen[num]);
        } else if (num < 20) { // num: 10 - 19
            sb.append(belowTwenty[num - 10]);
        } else if (num < 100) { // num: 21 - 99
            sb.append(belowHundred[num / 10]).append(" ").append(helper(num % 10));
        } else if (num < 1000) { // num: 100 - 999
            // the 1st term can only be used as 1- 9
            sb.append(helper(num / 100)).append(" Hundred ").append(helper(num % 100));
        } else if (num < 1000000) { // num: 1000 - 0 999 999
            // the 1st term can be 1 - 999
            sb.append(helper(num / 1000)).append(" Thousand ").append(helper(num % 1000));
        } else if (num < 1000000000) { // num: 1 000 000 - 0 999 999 999
            // the 1st term can be 1 - 999
            sb.append(helper(num / 1000000)).append(" Million ").append(helper(num % 1000000));
        } else {
            sb.append(helper(num / 1000000000)).append(" Billion ").append(helper(num % 1000000000));
        }
        
        return sb.toString().trim(); // dont forget trim
    }
}
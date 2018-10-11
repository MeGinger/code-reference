Integer to Roman

class Solution {
    // test case: 1954
    public String intToRoman(int num) {
        if (num <= 0) {
            return "";
        }

        int[] values  = {1000, 900, 500, 400, 100, 90, 50, 40, 10, 9, 5, 4, 1};
        String[] strs = {"M", "CM", "D", "CD", "C", "XC", "L", "XL", "X", "IX", "V", "IV", "I"};
    
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < values.length; i++) {
            while (values[i] <= num) {
                num -= values[i];
                sb.append(strs[i]);
            }
        }
        return sb.toString();
    }
}

Product of Array Except Self

class Solution {
    public int[] productExceptSelf(int[] nums) {
        if (nums == null || nums.length <= 1) {
            return new int[0];
        }
    
        // solve it without division & O(n)
        
        int[] res = new int[nums.length];
        res[0] = 1;
        for (int i = 1; i < nums.length; i++) {
            res[i] = res[i - 1] * nums[i -1];
        }

        // 0   1
        // 1   nums[0]
        // 2   nums[0] * nums[1]
        // ... ...
        // n-1 nums[0] * nums[1] * nums[2] * ... * nums[n-2]

        int right = 1;
        for (int i = nums.length - 2; i >= 0; i--) {
            right *= nums[i + 1];
            res[i] *= right;
        }
        
        return res;
    }
}





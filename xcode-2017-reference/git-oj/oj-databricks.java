class Solution {
    public int firstMissingPositive(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 1; // should be one
        }

        // nums[first_missing_index - 1] > 0
        int first_missing_index = partition(nums) + 1;
        
        // index: 0 - first_missing_index-1
        // value: 1 - first_missing_index
        for (int i = 0; i < first_missing_index; i++) {
            int val = Math.abs(nums[i]); // 
            if (val > first_missing_index) { // dont forget
                continue;
            }
            
            if (nums[val - 1] > 0) {
                nums[val - 1] *= -1;
            }
        }
        
        for (int i = 0; i < first_missing_index; i++) {
            if (nums[i] > 0) {
                return i + 1;
            }
        }
        // we have 1 - first_missing_index
        
        return first_missing_index + 1;
    }
    
    private int partition(int[] nums) {
        int p = -1;
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] > 0) {
                p++;
                swap(nums, p, i);
            }
        }
        return p;
    }
    
    private void swap(int[] nums, int a, int b) {
        int temp = nums[a];
        nums[a] = nums[b];
        nums[b] = temp;
    }
}


// Decode Ways II

class Solution {
    //  since the answer may be very large, you should return the output mod 109 + 7.
    
    // '*', which can be treated as one of the numbers from 1 to 9.
    public int numDecodings(String s) {
        // dp
        
        // initialization
        long[] dp = new long[s.length() + 1];
        dp[0] = 1;
        // Decode Way I: continue; -> dp[i] = 0;
        if (s.charAt(0) == '0') { 
            return 0;
        }
        
        dp[1] = (s.charAt(0) == '*') ? 9 : 1;
        
        // bottom up method
        for (int i = 2; i <= s.length(); i++) {
            char first = s.charAt(i - 2);
            char second = s.charAt(i - 1);
            
            // dp[i] starts from 0
            // current character - second - nums[i - 1]
            
            // for second - single digit
            if (second == '*') {
                dp[i] += 9 * dp[i - 1];
            } else if (second > '0') {
                dp[i] += dp[i - 1];
            }
            
            // for first - double digits
            if (first == '*') {
                if (second == '*') {
                    // **: 
                    // 11 - 19 -> 9
                    // 21 - 26 -> 6
                    // 9 + 6 = 15
                    dp[i] += 15 * dp[i - 2];
                } else if (second <= '6') {
                    // e.g., *6: 
                    // 16, 26 -> 2
                    dp[i] += 2* dp[i - 2];
                } else {
                    // e.g., *7:
                    // 17 -> 1
                    dp[i] += dp[i - 2];
                }
            } else if (first == '1' || first == '2') {
                if (second == '*') {
                    if (first == '1') {
                        // 1*: 11 - 19
                        dp[i] += 9 * dp[i - 2];
                    } else {
                        // 2*: 21 - 26
                        dp[i] += 6 * dp[i - 2];
                    }
                } else if ((first - '0') * 10 + (second - '0') <= 26) {
                    dp[i] += dp[i - 2];
                }
            }
            
            dp[i] %= 1000000007; // for every loop?
        }
        
        return (int) dp[s.length()];
    }
}
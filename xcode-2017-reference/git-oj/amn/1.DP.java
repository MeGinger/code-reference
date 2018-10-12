Word Break
class Solution {
    public boolean wordBreak(String s, List<String> wordDict) {
        if (s == null || s.length() == 0 ||
            wordDict == null || wordDict.size() == 0) {
            return false; // when wordDict is empty - not breakable
        }
        
        Set<String> dict = new HashSet<>(wordDict);
        List<Integer> breakableIndex = new ArrayList<>();
        
        for (int i = 0; i < s.length(); i++) {
            if (dict.contains(s.substring(0, i + 1))) {
                breakableIndex.add(i); // substring: 0...i is breakable
                continue;
            }
            
            // all index < i - since linear scan from left to right
            for (Integer index : breakableIndex) {
                if (dict.contains(s.substring(index + 1, i + 1))) {
                    breakableIndex.add(i);
                    break;
                }
            }
        }
        
        return !breakableIndex.isEmpty() && 
            breakableIndex.get(breakableIndex.size() - 1) == s.length() - 1;
    }
}

Maximum Subarray - all kinds of integer - negative, 0, positive

class Solution {
    public int maxSubArray(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }
        
        int n = nums.length;
        
        int[] dp = new int[n]; // max subarray ending at i
        dp[0] = nums[0];
        
        int max = nums[0];
        
        // if dp[i] < 0, means nums[i] must be < 0, and nums[i] <= dp[i]
        
        for (int i = 1; i < n; i++) {
            dp[i] = nums[i] + (dp[i - 1] > 0 ? dp[i - 1] : 0);
            max = Math.max(max, dp[i]);
        }
        
        return max;
    }
}


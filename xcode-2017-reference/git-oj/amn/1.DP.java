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

Concatenated Words

Input: ["cat","cats","catsdogcats","dog","dogcatsdog","hippopotamuses","rat","ratcatdogcat"]

Output: ["catsdogcats","dogcatsdog","ratcatdogcat"]

the words used to concatenate must be used only once

class Solution {
    public List<String> findAllConcatenatedWordsInADict(String[] words) {
        if (words == null || words.length == 0) {
            return Collections.emptyList();
        }
        
        List<String> res = new ArrayList<>();
        Set<String> preWords = new HashSet<>();
        Arrays.sort(words, (a, b) -> a.length() - b.length());
        
        for (int i = 0; i < words.length; i++) {
            if (canForm(words[i], preWords)) {
                res.add(words[i]);
            }
            preWords.add(words[i]);
        }
        return res;
    }
    
    private boolean canForm(String word, Set<String> preWords) {
        List<Integer> breakableIndex = new ArrayList<>();
        
        for (int i = 1; i <= word.length(); i++) {
            if (preWords.contains(word.substring(0, i))) {
                breakableIndex.add(i);
            }
            
            for (Integer index : breakableIndex) {
                if (preWords.contains(word.substring(index, i))) {
                    breakableIndex.add(i);
                    break;
                } 
            }
        }
        
        return !breakableIndex.isEmpty() && 
            breakableIndex.get(breakableIndex.size() - 1) == word.length();
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



Edit Distance
two words word1 and word2, find the minimum number of operations required to convert word1 to word2
Insert a character
Delete a character
Replace a character

class Solution {
    public int minDistance(String word1, String word2) {
        if (word1 == null || word2 == null) {
            return -1; // !
        }
        
        
        // f[i][j]: the first i char in word1 and the first j char in word2
        // min distance
        // returns f[word1.length()][word2.length()];
        int n1 = word1.length() + 1;
        int n2 = word2.length() + 1;
        // first i characters in word1 and first j characters in word2
        int[][] f = new int[n1][n2]; 
        
        for (int i = 0; i < n1; i++) {
            f[i][0] = i;
        }
        for (int j = 0; j < n2; j++) {
            f[0][j] = j;
        }
        
        for (int i = 1; i < n1; i++) {
            for (int j = 1; j < n2; j++) {
                if (word1.charAt(i - 1) == word2.charAt(j - 1)) {
                    f[i][j] = f[i - 1][j - 1];
                } else {
                    f[i][j] = Math.min(f[i - 1][j - 1], // replace
                                Math.min(f[i - 1][j],  // remove word1.charAt(i) in word1 // index i - 1
                                         f[i][j - 1])) // insert word2.charAt(j) in word1  // index j - 1
                              + 1;
                }
            }
        }
        
        return f[n1 - 1][n2 - 1];
    }
}

Decode Ways

class Solution {
    // the total number of ways to decode a string
    // a string contains A-Z 26 uppercase letters
    
    /*  'A' -> 1
        'B' -> 2
        ...
        'Z' -> 26
     */
    
    /*
    This problem is a typical Dynamic Programming problem 
    and I would not even bother with recursion.

    I would start with DP directly and here is deduction formula. 
    
    After DP version is working, I will improve it with space usage by only using three variables f0, f1 and f2.
     */ 
    public int numDecodings(String s) {
        if (s == null || s.length() == 0) {
            return 0;
        }
        
        int len = s.length();
        int c = 1; // meaningless, but it will be used in transformation formula when we calculating dp[len - 2]
        int b = s.charAt(len - 1) == '0' ? 0 : 1;
        
        for (int i = len - 2; i >= 0; i--) {
            // we cannot decode substring starts with 0
            int a = 0; // substring from i to the end
            
            if (s.charAt(i) != '0') { 
                if (Integer.parseInt(s.substring(i, i + 2)) <= 26) {
                    // 2 decode ways 
                    // - substring decoded into a single character
                    // - decoded into two characters
                    a = b + c;
                } else {
                    // 1 decode way
                    a = b;
                }
            }
            
            c = b;
            b = a;
        }
        
        return b;
    }
}

class Solution {
    public int numDecodings(String s) {
        if (s == null || s.length() == 0) {
            return 0;
        }
        
        int len = s.length();
        int[] dp = new int[len + 1];
        dp[len] = 1; // I FORGOT.. NOT 0
        dp[len - 1] = s.charAt(len - 1) == '0' ? 0 : 1;
        
        
        for (int i = len - 2; i >= 0; i--) {
            if (s.charAt(i) == '0') {
                continue;
            }
            
            String digits = s.substring(i, i + 2); // [i..i+1]
            System.out.println(digits);
            int num = Integer.parseInt(digits);
            if (num <= 26) {
                dp[i] = dp[i + 1] + dp[i + 2];
            } else {
                dp[i] = dp[i + 1];
            }
        }
        
        return dp[0];
    }
}

Maximal Square

public int maximalSquare(char[][] a) {
    if(a.length == 0) return 0;
    int m = a.length, n = a[0].length, result = 0;
    int[][] b = new int[m+1][n+1];
    for (int i = 1 ; i <= m; i++) {
        for (int j = 1; j <= n; j++) {
            if(a[i-1][j-1] == '1') {
                b[i][j] = Math.min(Math.min(b[i][j-1] , b[i-1][j-1]), b[i-1][j]) + 1;
                result = Math.max(b[i][j], result); // update result
            }
        }
    }
    return result*result;
}

Unique Paths
class Solution {
    public int uniquePaths(int m, int n) {
        if (m <= 0 || n <= 0) {
            return 0;
        }
        
        // top-bottom
        // state function: dp[x][y] - # of unique paths from 0, 0 to x, y
        
        // initialize
        int[][] dp = new int[m][n];
        for (int i = 0; i < m; i++) {
            dp[i][0] = 1;
        }
        for (int i = 0; i < n; i++) {
            dp[0][i] = 1;
        }
        
        for (int i = 1; i < m; i++) {
            for (int j = 1; j < n; j++) {
                dp[i][j] = dp[i][j - 1 ] + dp[i - 1][j];
            }
        }
        
        return dp[m - 1][n - 1];
    }
}

Coin Change - unlimited supply of coins - find min coins
class Solution {
    public int coinChange(int[] coins, int amount) {
        if (coins == null || coins.length == 0 || amount <= 0) {
            return 0;
        }
        
        int[] dp = new int[amount + 1]; // dp[i]: 

        // amount + 1 is max and impossible, since worst case is all coins are 1
        // cannot use Integer.MAX_VALUE, since we have dp[i - coins[j]] + 1 - overflow to negative...
        Arrays.fill(dp, amount + 1); 
        dp[0] = 0;
        for (int i = 1; i <= amount; i++) {
            for (int j = 0; j < coins.length; j++) {
                if (coins[j] <= i) {
                    dp[i] = Math.min(dp[i], dp[i - coins[j]] + 1);
                }
            }
        }
        
        // when dp[amount] > amount - not found
        // like input: [2], 3
        return dp[amount] > amount ? -1 : dp[amount];
    }
}

Triangle - min path sum from top to bottom

class Solution {
    public int minimumTotal(List<List<Integer>> triangle) {
        if (triangle == null || triangle.size() == 0) {
            return 0;
        }
        
        int r = triangle.size();
        // int c = triangle.get(0).size(); // WRONG!
        
        int[][] dp = new int[r][r]; // dp[i][j]: min path sum from (i, j) to any bottom
        
        for (int i = 0; i < r; i++) {
            dp[r - 1][i] = triangle.get(r - 1).get(i);
        }
        
        for (int i = r - 2; i >= 0; i--) {
            for (int j = 0; j <= i; j++) {
                int min = Math.min(dp[i + 1][j], dp[i + 1][j + 1]);
                dp[i][j] = triangle.get(i).get(j) + min;
            }
        }
        
        return dp[0][0];
    }
}


Climbing Stairs

class Solution {
    public int climbStairs(int n) {
        if (n <= 0) {
            return 0;
        }
            
        int[] dp = new int[n + 1]; // dp[i]: count of ways to climb at step i
        dp[0] = 1; // initialized for dp[2], similar to decode way !!
        dp[1] = 1;
        for (int i = 2; i <= n; i++) {
            dp[i] = dp[i - 1] + dp[i - 2];
        }
        
        return dp[n];
    }
}

Interleaving String
Given s1, s2, s3, find whether s3 is formed by the interleaving of s1 and s2.

class Solution {
    public boolean isInterleave(String s1, String s2, String s3) {
        if (s1 == null || s2 == null || s3 == null) {
            return false;
        }
        
        int len1 = s1.length();
        int len2 = s2.length();
        int len3 = s3.length();
        
        if (len1 + len2 != len3) {
            return false;
        }
        
        // dp[i][j]: Whether first i char in s1 & first j char in s2 -> first (i+j) char in s3
        boolean[][] dp = new boolean[len1 + 1][len2 + 1];
        
        dp[0][0] = true;
        for (int i = 1; i <= len1; i++) {
            dp[i][0] = dp[i - 1][0] && (s1.charAt(i - 1) == s3.charAt(i - 1));
        }
        for (int i = 1; i <= len2; i++) {
            dp[0][i] = dp[0][i - 1] && (s2.charAt(i - 1) == s3.charAt(i - 1));
        }
        
        for (int i = 1; i <= len1; i++) {
            for (int j = 1; j <= len2; j++) {
                if (s1.charAt(i - 1) == s3.charAt(i + j - 1) && dp[i - 1][j] == true  ||
                    s2.charAt(j - 1) == s3.charAt(i + j - 1) && dp[i][j - 1] == true) {
                    dp[i][j] = true;
                }
            }
        }
        return dp[len1][len2];
    }
}


Best Time to Buy and Sell Stock III
IV (Time Limit Exceeded)

class Solution {
    public int maxProfit(int[] prices) {
        
        if (prices == null || prices.length == 0) {
            return 0;
        }
        
        int K = 2;
        int max = 0;
        
        int len = prices.length;
        int[][] dp = new int[K + 1][len];
        
        // The tmpMax process is equivalent to finding the minimum buying prices, which saves us from iterative searching.
        for (int i = 1; i <= K; i++) {
            int tempMax = dp[i - 1][0] - prices[0];
            for (int j = 1; j < prices.length; j++) {
                dp[i][j] = Math.max(dp[i][j - 1], prices[j] + tempMax);
                tempMax = Math.max(tempMax, dp[i - 1][j] - prices[j]);
                max = Math.max(max, dp[i][j]);
            }
        }
        return max;
    }
}

Maximum Product Subarray
Given an integer array nums, find the contiguous subarray within an array (containing at least one number) which has the largest product.

class Solution {
    public int maxProduct(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }
        
        int len = nums.length;
        int[] max = new int[len];
        int[] min = new int[len];
        int res = nums[0];
        
        max[0] = min[0] = nums[0];
        
        for (int i = 1; i < len; i++) {
            max[i] = Math.max(nums[i], Math.max(max[i - 1] * nums[i], min[i - 1] * nums[i]));
            min[i] = Math.min(nums[i], Math.min(max[i - 1] * nums[i], min[i - 1] * nums[i]));
            
            res = Math.max(res, max[i]);
        }
        
        return res;
    }
}

Min Cost Climbing Stairs

class Solution {
    public int minCostClimbingStairs(int[] cost) {
        // dynamic programming - dp
        if (cost == null || cost.length == 0) {
            return 0;
        }
        
        int len = cost.length;
        int[] dp = new int[len + 1]; // dp[i]: minimum cost climb from i
        dp[0] = cost[0];
        dp[1] = cost[1];
        for (int i = 2; i < len; i++) {
            dp[i] = Math.min(dp[i - 1], dp[i - 2]) + cost[i];            
        }
        
        return Math.min(dp[len - 1], dp[len - 2]);
    }
}

House Robber

class Solution {
    public int rob(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }
        
        int len = nums.length + 2; // extra 2 to avoid edge cases like input only has one element of two element
        int[] dp = new int[len];
        dp[0] = 0; // initialization
        dp[1] = 0;
        for (int i = 2; i < len; i++) {
            dp[i] = Math.max(nums[i - 2] + dp[i - 2], dp[i - 1]);
        }
        
        return dp[len - 1];
    }
}

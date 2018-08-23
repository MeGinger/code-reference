
    // Count All Paths from top left to bottom right in Two Dimensional Array including Diagonal Paths
    // (1) (2)
    // (3) (4) 
    //              (n)
    // 
    // 4->1, 3->1, 2->1 

	// - - - - - - - o
	// - - - - - - - o
	// - - - - - - - o
	// o o o o o o o o

	// o: initially filled
	// -: filled in the loop
	
	// thought: 
	// do the counting from bottom right to top left using a two-dimensional matrix
	// dp(i, j): the count of paths from (i, j) to bottom right point
	// transformation formula: dp(i, j) = dp(i + 1, j) + dp(i, j + 1) + dp(i + 1, j + 1)
	// initial state: the last row and last column can be 1, since for those positions, we only have one path to get to destination
	public int krakenCount(int m, int n) {
		if (m == 0 || n == 0) {
			return 0;
		}

		int[][] dp = new int[m][n];

		// fill the last row
		Arrays.fill(dp[m - 1], 1); 

		// fill the last column
		for (int i = 0; i > m; i++) {
			dp[i][n - 1] = 1;
		}

		for (int i = m - 2; i >= 0; i--) {
			for (int j = n - 2; j >= 0; j--) {
				dp[i][j] += dp[i][j + 1] + dp[i + 1][j] + dp[i + 1][j + 1];
			}
		}

		return dp[0][0];
	}

	// thought: 
	// using a one-dimensional matrix
	//                      dp[i][j + 1]:     dp[j+1]
	// dp[i + 1][j]: dp[j]  dp[i + 1][j + 1]: prev
	// dp[j] = dp[j] + dp[j+1] + prev; // dp[j] will be assigned to prev
    // before doing the sumation, dp[j] state will be kept for the next prev
    // the calculated dp[j] will be the dp[j + 1] in the next loop

    // [                <-]  
    // 0, 1, 2, 3, ..., n-2, n-1
	public int krakenCount(int m, int n) {
		if (m == 0 || n == 0) {
			return 0;
		}

		int[] dp = new int[n];
		Arrays.fill(dp, 1);

		for (int i = m - 2; i >= 0; i--) {
			int prev = 1;
			for (int j = n - 2; j >= 0; j--) {
				int tmp = dp[j];
				dp[j] += dp[j + 1];
				dp[j] += prev;
				prev = tmp;            
			}
		}

		return dp[0];
	}

	/* Unique Paths
	 * A robot is located at the top-left corner of a m x n grid
	 * (marked 'Start' in the diagram below). The robot can only move either
	 * down or right at any point in time. The robot is trying to reach the
	 * bottom-right corner of the grid (marked 'Finish' in the diagram below).
	 * How many possible unique paths are there?
	 */

	// - - - - - - - o
	// - - - - - - - o
	// - - - - - - - o
	// o o o o o o o o

	// o: initially filled
	// -: filled in the loop
	
	public class UniquePaths {
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
	}

	/* Unique Paths II
	 * Follow up for "Unique Paths": Now consider if some
	 * obstacles are added to the grids. How many unique paths would there be?
	 * An obstacle and empty space is marked as 1 and 0 respectively in the
	 * grid. For example, There is one obstacle in the middle of a 3x3 grid as
	 * illustrated below. [ [0,0,0], [0,1,0], [0,0,0] ]
	 * The total number of unique paths is 2.
	 */

	// - - - - - - - -
	// - - - - - - - - 
	// - - - - - - - -
	// o o o o o o o o

	// o: initially filled
	// -: filled in the loop
	public class UniquePathsII {
		public int uniquePathsWithObstacles(int[][] obstacleGrid) {
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
	}

    /**
	Maximum Minimum Path
	给一个int[][]的matirx，对于一条从左上到右下的path pi，pi中的最小值是mi，求所有mi中的最大值。只能往下或右.
	比如：
	[8, 4, 7]
	[6, 5, 9]
	有3条path：
	8-4-7-9 min: 4
	8-4-5-9 min: 4
	8-6-5-9 min: 5
	return: 5
     */
    public int maxMinPath(int[][] matrix) {
        int m = matrix.length, n = matrix[0].length;
        int[] result = new int[n];
        result[0] = matrix[0][0];
        for (int i = 1; i < n; i++) {
            // populate the first row, only one row from (0, 0) to (0, i), use Math.min
            result[i] = Math.min(result[i - 1], matrix[0][i]);
        }
        for (int i = 1; i < m; i++) {
            // populate the frist columne, only one column from (0, 0) to (i, 0), use Math.min
            result[0] = Math.min(matrix[i][0], result[0]);

            for (int j = 1; j < n; j++) {

                // result[j]: (i-1, j)
                // result[j-1]: (i, j-1)

                //          (i-1, j)
                // (j-1, i) (i,   j)

                result[j] = (result[j] < matrix[i][j] && result[j - 1] < matrix[i][j])
                        ? Math.max(result[j - 1], result[j]) : matrix[i][j];
            }
        }
        return result[n - 1];
    }

	// find min max matrix
	public int helper(int[][] matrix){
		int[] result = new int[matrix[0].length];
		result[0] = matrix[0][0];
		for (int i = 1; i < matrix[0].length ;i++ ) {
			result[i] = Math.min(result[i-1], matrix[0][i]);
		}
		for (int i = 1;i < matrix.length ;i++ ) {
			result[0] = Math.min(matrix[i][0],result[0]);
			for (int j = 1; j < matrix[0].length ;j++ ) {
				result[j] = (result[j] < matrix[i][j] && result[j - 1] < matrix[i][j])? Math.max(result[j-1],result[j]):matrix[i][j];
			}
		}
		return result[result.length - 1];
	}



	/**
     * Paint House
	There are a row of n houses, each house can be painted with one of the three colors: red, blue or green.
	The cost of painting each house with a certain color is different.
	You have to paint all the houses such that no two adjacent houses have the same color.
	The cost of painting each house with a certain color is represented by a n x 3 cost matrix.
	For example, costs[0][0] is the cost of painting house 0 with color red;
	costs[1][2] is the cost of painting house 1 with color green, and so on...
	Find the minimum cost to paint all houses.

	Note:
	All costs are positive integers.

	The 1st row is the prices for the 1st house, we can change the matrix to present sum of prices from the 2nd row.
	i.e, the costs[1][0] represent minimum price to paint the second house red plus the 1st house.
		 */

	// follow up: input costs is modified
	public int minCost(int[][] costs) {
		if (costs == null || costs.length == 0) {
			return 0;
		}
		for (int i = 1; i < costs.length; i++) {
			costs[i][0] += Math.min(costs[i - 1][1], costs[i - 1][2]);
			costs[i][1] += Math.min(costs[i - 1][0], costs[i - 1][2]);
			costs[i][2] += Math.min(costs[i - 1][1], costs[i - 1][0]);
		}
		int n = costs.length - 1;
		return Math.min(Math.min(costs[n][0], costs[n][1]), costs[n][2]);
	}
	
	/**
	 * Paint House II
	There are a row of n houses, each house can be painted with one of the k colors.
	The cost of painting each house with a certain color is different.
	You have to paint all the houses such that no two adjacent houses have the same color.
	The cost of painting each house with a certain color is represented by a n x k cost matrix.
	For example, costs[0][0] is the cost of painting house 0 with color 0;
	costs[1][2] is the cost of painting house 1 with color 2, and so on... Find the minimum cost to paint all houses.

	Note:
	All costs are positive integers.

	The idea is similar to the problem Paint House I, for each house and each color,
	the minimum cost of painting the house with that color should be the minimum cost of painting previous houses,
	and make sure the previous house doesn't paint with the same color.
	We can use min1 and min2 to track the indices of the 1st and 2nd smallest cost till previous house,
	if the current color's index is same as min1, then we have to go with min2, otherwise we can safely go with min1.
	The code below modifies the value of costs[][] so we don't need extra space.
		 */

	// cost[i][j] => cost[i][0], cost[i][1], cost[i][2] => color 0 ~ i houses, ith house using color j, min cost
	// ith house: min1: index of min cost of ith house across k colors: min2: second min
	// i-1th house: last1: index of min cost of i-1th house across k colors; last2: second min 
	public int minCostII(int[][] costs) {
		if (costs == null || costs.length == 0) {
			return 0;
		}

		int n = costs.length, k = costs[0].length;
		// n: count of hourses
		// k: count of colors

		// min1/last1 is the index of the 1st-smallest cost for current/previous loop
		// min2/last2 is the index of the 2nd-smallest cost for current/previous loop

		int min1 = -1, min2 = -1;

		for (int i = 0; i < n; i++) {
			int last1 = min1, last2 = min2;
			min1 = -1;
			min2 = -1;

			for (int j = 0; j < k; j++) {
				if (j != last1) {
					// current color j is different to last min1
					costs[i][j] += last1 < 0 ? 0 : costs[i - 1][last1];
				} else {
					costs[i][j] += last2 < 0 ? 0 : costs[i - 1][last2];
				}

				// find the indices of 1st and 2nd smallest cost of painting
				// current house i
				if (min1 < 0 || costs[i][j] < costs[i][min1]) {
					min2 = min1;
					min1 = j;
				} else if (min2 < 0 || costs[i][j] < costs[i][min2]) {
					min2 = j;
				}
			}
		}

		return costs[n - 1][min1];
	}


/**
*Word Break
*Given a non-empty string s and a dictionary wordDict containing a list of non-empty words,
*determine if s can be segmented into a space-separated sequence of one or more dictionary words.
*You may assume the dictionary does not contain duplicate words.
*
*For example, given
*s = "leetcode",
*dict = ["leet", "code"].
*Return true because "leetcode" can be segmented as "leet code". 
*/


// input: s = "leetcoder", dict = ["leet", "code"]
// thought: scan from left to right, check whether substring/prefix of s from index 0 to i 
// either is a single word in dictionary (substring "leet")
// or can be segmeneted into multiple words in dictionary (substring "leetcode") 
// record every such index i 
// finally, we check if index (last index of input string s -> s.length() - 1) is present

// dynamic programming
public boolean wordBreak(String s, Set<String> dict) {
	// invalid input - "leetcode"
	if (a == null || s.isEmpty()) {
		return false; 
	}
	
	// index in this list is to indicate string s[0...i] can be segmented into words in dictionary
	List<Integer> breakableIndex = new ArrayList<>(s.length());
	
	// scan string s from left to right
	for (int i = 0; i < s.length(); i++) {
		// check whether substring of s from index 0 to i is in dict 
		// if it is, it can be segmented into a single word in dictionary		
		if (dict.contains(s.substring(0, i + 1)) {
			breakableIndex.add(i);
			continue;
		}
		
		// if not, check if the substring can be break into multiple words
		Iterator<Integer> itr = breakableIndex.iterator();
		while (itr.hasNext()) {
			if (dict.contains(s.substring(itr.next() + 1, i + 1))) {
				breakableIndex.add(i);
				break;
			}
		}
	}
		
    // check if the last index in breakableIndex is equal to s.length() - 1
	// as mentione before, the index is used to indicate ....
	// which means the string s can be segmented into words in dictionary
	return !breakableIndex.isEmpty() && breakableIndex.get(breakableIndex.size() - 1) == (s.lenght() - 1);
}

// runtime
// best: O(n)
// worst: O(n^2)



/**
*Word Break II
*Given a non-empty string s and a dictionary wordDict containing a list of non-empty words,
*add spaces in s to construct a sentence where each word is a valid dictionary word.
*You may assume the dictionary does not contain duplicate words.
*Return all such possible sentences.
*For example, given
*s = "catsanddog",
*dict = ["cat", "cats", "and", "sand", "dog"].
*A solution is ["cats and dog", "cat sand dog"]. 
*/

// input: s = "catsanddog"
// thought: 
// So i should implement a method that we can get a sentence list where each sentence is constructed by valid dictionary words separated by space
// The basic idea is to scan input string from left to right, check if prefix ["cat"] of s from index 0 to i is a dictionary word
// if it is, do recursion to get a sentence list of the suffix ["sanddog"], then we append each sentence to prefix 
// to get the sentence list that corresponds to string s.

// recursion
public List<String> wordBreak2(String s, Set<String> dict) {
	return breakIntoWordList(s, dict, new HashMap<>());
}

// What method breakIntoWordList does?
// return a sentence list where each sentence is constructed by valid dictionary words seperated by space

// memorized (
//	 key: string, 
//   value: a list of sentences 
//          where each sentence is constructed by valid dictionary words seperated by space
// )
private breakIntoWordList(String s, Set<String> dict, Map<String, List<String>> memorized) {
	// result: a list of sentences
	// finally result is added to memorized along with s
	List<String> result = new ArrayList<>();
	if (s == null || s.length() == 0) {
		return result;
	}
	
	if (memorized.containsKey(s)) {
		return memorized.get(s);
	}
	
	for (int i = 1; i <= s.length(); i++){
		String prefix = s.substring(0, i);
		if (!dict.contains(prefix) {
			continue;
		}
		
		// i: s.length() -> no suffix
		if (i == s.length()) {
			result.add(s);
			
			// can we change these two lines to continue/break
			continue;
			// memorized.put(s, result);
			// return result;
		}
		
		// i: 1 to s.length() - 1 -> suffix
		String suffix = s.substring(i, s.length());
		List<String> segSuffix = breakIntoWordList(suffix, dict, memorized);
		for (String suffixWord : segSuffix) {
			result.add(prefix + " " + suffixWord);
		}
	}
	
	memorized.put(s, result);
	return result;
}
// runtime: O(n^2) 
// best: O(n)
// example intput - all prefix are not dictionary words - no recursion happens
// s: "aaaaaaaaaaaaaaaaaa"
// dict: ["b"]

// worst: O(n^2)
// example intput - every prefix is dicionary word 
// s: "aaaaaaaaaaaaaaaaaa"
// dict: ["a"]




	// Longest Palindromic Subsequence(leetcode 516) // LinkedIn
	// Problem: Given a string s, find the longest palindromic subsequence's length in s. You may assume that the maximum length of s is 1000.
	// Example 1:
	// Input: "bbbab"
	// Output: 4
	// One possible longest palindromic subsequence is "bbbb".
	// Example 2:
	// Input: "cbbd"
	// Output: 2
	// One possible longest palindromic subsequence is "bb".

	// dp[i][j]: the longest palindromic subsequence's length of substring(i, j)
	// state transition: 
	// i, i+1, ..., j-1, j
	// dp[i][j] = dp[i+1][j-1] + 2 if s.charAt(i) == s.charAt(j)
	// otherwise, dp[i][j] = Math.max(dp[i+1][j], dp[i][j-1])
	// Initialization: dp[i][i] = 1
    public int longestPalindromeSubseq(String s) {
        int[][] dp = new int[s.length()][s.length()];
        
		
		/*    a b a a
			a *\* * *
			b * *\* *
			a * * *\*
			a * * * *\
		*/
		
        for (int i = s.length() - 1; i >= 0; i--) {
            dp[i][i] = 1;
            for (int j = i+1; j < s.length(); j++) {
                if (s.charAt(i) == s.charAt(j)) {
                    dp[i][j] = dp[i+1][j-1] + 2;
                } else {
                    dp[i][j] = Math.max(dp[i+1][j], dp[i][j-1]);
                }
            }
        }
        return dp[0][s.length()-1];
    }
    public int longestPalindromeSubseq2(String s) {
        
    }

    	/**DP Q1
	 * Min Cost Climbing Stairs
	On a staircase, the i-th step has some non-negative cost cost[i] assigned (0 indexed).

	Once you pay the cost, you can either climb one or two steps. You need to find minimum cost to reach the top of the floor, and you can either start from the step with index 0, or the step with index 1.

	Example 1:
	Input: cost = [10, 15, 20]
	Output: 15
	Explanation: Cheapest is start on cost[1], pay that cost and go to the top.
	Example 2:
	Input: cost = [1, 100, 1, 1, 1, 100, 1, 1, 100, 1]
	Output: 6
	Explanation: Cheapest is start on cost[0], and only step on 1s, skipping cost[3].
	Note:
	cost will have a length in the range [2, 1000].
	Every cost[i] will be an integer in the range [0, 999].
	 */
	public int minCostClimbingStairs(int[] cost) {
		int n = cost.length;
		int[] f = new int[n + 2]; // so there is no initialization
		for (int i = 2; i < f.length; i++) {
			f[i] = Math.min(f[i - 1], f[i - 2]) + cost[i - 2];
		}
		return Math.min(f[f.length - 1], f[f.length - 2]);
	}

	public int minCostClimbingStairs2(int[] cost) {
		int n = cost.length;
		int f1 = 0, f2 = 0;
		for (int i = 0; i < n; i++) {
			int f0 = Math.min(f1, f2) + cost[i];
			// shift: f2, f1 = f1, f0
			f2 = f1;
			f1 = f0;
		}
		return Math.min(f1, f2); // since in the last loop, f2 = f1, f1 = f0, f1 and f2 are last two.
	}
	
	public int minCostClimbingStairs3(int[] cost) {
		return minCostClimbingStairs(cost, cost.length, new HashMap<Integer, Integer>());
	}

	private int minCostClimbingStairs(int[] cost, int n, Map<Integer, Integer> m) {
		Integer result = m.get(n);
		if (result != null) {
			return result;
		}

		// base case
		if (n < 2) {
			return 0;
		}

		if (n == 2) {
			return Math.min(cost[0], cost[1]);
		}
		if (n == 3) {
			return Math.min(cost[2] + minCostClimbingStairs(cost, 2, m), cost[1]);
		}

		result = Math.min(cost[n - 1] + minCostClimbingStairs(cost, n - 1, m),
				cost[n - 2] + minCostClimbingStairs(cost, n - 2, m));
		m.put(n, result);
		return result;
	}

   /** DPQ2
	 * Delete and Earn
	Given an array nums of integers, you can perform operations on the array.

	In each operation, you pick any nums[i] and delete it to earn nums[i] points. After, you must delete every element equal to nums[i] - 1 or nums[i] + 1.

	You start with 0 points. Return the maximum number of points you can earn by applying such operations.

	Example 1:
	Input: nums = [3, 4, 2]
	Output: 6
	Explanation: 
	Delete 4 to earn 4 points, consequently 3 is also deleted.
	Then, delete 2 to earn 2 points. 6 total points are earned.
	
	Example 2:
	Input: nums = [2, 2, 3, 3, 3, 4]
	Output: 9
	Explanation: 
	Delete 3 to earn 3 points, deleting both 2's and the 4.
	Then, delete 3 again to earn 3 points, and 3 again to earn 3 points.
		9 total points are earned.
	 */
	public int deleteAndEarn(int[] nums) {
		Map<Integer, Integer> counts = new TreeMap<>();
		for (int x : nums) {
			counts.put(x, counts.getOrDefault(x, 0) + 1);
		}
		int a = 0, b = 0, prev = -1; 
		// a and b are represented as the previous two values
		// c is represented as the current value
		
		for (Map.Entry<Integer, Integer> entry : counts.entrySet()) {
			int cur = entry.getKey();
			int count = entry.getValue();
			int sum = cur * count;
			int c;
			if (cur - 1 != prev) {
				c = Math.max(a, b) + sum;
				// if cur and prev and not adjacent, sum is must taken and choose max between a and b
			} else {
				c = Math.max(a + sum, b);
				// if adjacent, pick b or a as starting point, if a is picked, cur can be picked as well
			}
			// a, b = b, c
			a = b;
			b = c;
			prev = cur;
		}
		return Math.max(a, b);
	}

	import java.util.*;


public class Code14 {


public class RegularExpressionMatching {

	/**
	 * Regular Expression Matching
	Implement regular expression matching with support for '.' and '*'.

	'.' Matches any single character.
	'*' Matches zero or more of the preceding element.

	The matching should cover the entire input string (not partial).

	The function prototype should be:
	bool isMatch(const char *s, const char *p)

	Some examples:
	isMatch("aa","a") false
	isMatch("aa","aa") true
	isMatch("aaa","aa") false
	isMatch("aa", "a*") true
	isMatch("aa", ".*") true
	isMatch("ab", ".*") true
	isMatch("aab", "c*a*b") true
	 */

	// recursion
	public boolean isMatch2(String s, String p) {
		if (p.length() == 0) {
			return s.length() == 0;
		}
		// p's length 1 is special case
		if (p.length() == 1 || p.charAt(1) != '*') {
			if (s.length() == 0
					|| (p.charAt(0) != '.' && s.charAt(0) != p.charAt(0))) {
				return false;
			}
			return isMatch2(s.substring(1), p.substring(1));
		}
		
		// p.length() >= 2 && p.charAt(1) == '*'
		int len = s.length();
		int i = -1;
		while (i < len
				&& (i < 0 || p.charAt(0) == '.' || p.charAt(0) == s
						.charAt(i))) {
			if (isMatch2(s.substring(i + 1), p.substring(2))) {
				return true;
			}
			i++;
		}
		return false;
	}

	/**
		Here are some conditions to figure out, then the logic can be very straightforward.
		1, If p.charAt(j) == s.charAt(i) :  dp[i][j] = dp[i-1][j-1];
		2, If p.charAt(j) == '.' : dp[i][j] = dp[i-1][j-1];
		3, If p.charAt(j) == '*': 
		here are two sub conditions:
			1, If p.charAt(j-1) != s.charAt(i) : dp[i][j] = dp[i][j-2]  //in this case, a* only counts as empty
			2, If p.charAt(j-1) == s.charAt(i) or p.charAt(j-1) == '.':
	           dp[i][j] = dp[i-1][j]    //in this case, a* counts as multiple a 
	        or dp[i][j] = dp[i][j-1]   // in this case, a* counts as single a
	        or dp[i][j] = dp[i][j-2]   // in this case, a* counts as empty
	 */
    // iterative dp - 2 dimensional
	public boolean isMatch3(String s, String p) {
		if (s == null || p == null) {
			return false;
		}
		// dp[i + 1][j + 1] is s[0 ~ i] of length i + 1 and p[0 ~ j] of length j + 1
		boolean[][] dp = new boolean[s.length() + 1][p.length() + 1];
		dp[0][0] = true;
		for (int j = 1; j < p.length(); j += 2) {
			if (p.charAt(j) == '*' && dp[0][j - 1]) {
				// s = "" && p = "a*b*c*"
				dp[0][j + 1] = true;
			} else {
				break;
			}
		}
		for (int i = 1; i <= s.length(); i++) {
			for (int j = 1; j <= p.length(); j++) {
				if (p.charAt(j - 1) == '.' || p.charAt(j - 1) == s.charAt(i - 1)) {
					dp[i][j] = dp[i - 1][j - 1];
				} else if (p.charAt(j - 1) == '*') {
					if (p.charAt(j - 2) != s.charAt(i - 1) && p.charAt(j - 2) != '.') {
						dp[i][j] = dp[i][j - 2];
					} else {
						dp[i][j] = dp[i][j - 1] || dp[i - 1][j] || dp[i][j - 2];
					}
				}
			}
		}
		return dp[s.length()][p.length()];
	}
	// iterative dp - 1 dimensional - follow up
	public boolean isMatch5(String s, String p) {
		if (s == null || p == null) {
			return false;
		}
		// dp[i + 1][j + 1] is s[0 ~ i] of length i + 1 and p[0 ~ j] of length j + 1
		boolean[] dp = new boolean[p.length() + 1];
		dp[0] = true;
		for (int j = 1; j < p.length(); j += 2) {
			if (p.charAt(j) == '*' && dp[j - 1]) {
				// s = "" && p = "a*b*c*"
				// only character in odd index can be '*'
				dp[j + 1] = true;
			} else {
				break;
			}
		}

		for (int i = 1; i <= s.length(); i++) {
			boolean pre = dp[0];
			dp[0] = false;
			for (int j = 1; j <= p.length(); j++) {
				if (p.charAt(j - 1) == '.' || p.charAt(j - 1) == s.charAt(i - 1)) {
					dp[j] = pre;
				} else if (p.charAt(j - 1) == '*') {
					if (p.charAt(j - 2) != s.charAt(i - 1) && p.charAt(j - 2) != '.') {
						dp[j] = dp[j - 2];
					} else {
						dp[j] = dp[j - 1] || dp[j] || dp[j - 2];
					}
				}
				pre = dp[j];
			}
		}
		return dp[p.length()];
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}

	







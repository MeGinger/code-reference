package xcode_class;

import java.util.Arrays;

public class Count_Different_Palindrome_Subsequence {
	
	private static final int MOD = 1_000_000_007;

	public static void main(String[] args) {
		// TODO Auto-generated method stub
	}
	
	public int countPalindromicSubsequences(String S) {
		int n = S.length();
		int[][] prv = new int[n][4];
		int[][] nxt = new int[n][4];
		for (int[] row : prv) {
			Arrays.fill(row, -1);
		}
		for (int[] row : nxt) {
			Arrays.fill(row, -1);
		}
		
		int[] last = new int[4];
		Arrays.fill(last, -1);
		for (int i = 0; i < n; ++i) {
			last[S.charAt(i) - 'a'] = i;
			for (int k = 0; k < 4; ++k) {
				prv[i][k] = last[k];
			}
		}
		
		Arrays.fill(last, -1);
		for (int i = n - 1; i >= 0; --i) {
			last[S.charAt(i) - 'a'] = i;
			for (int k = 0; k < 4; ++k) {
				nxt[i][k] = last[k];
			}
		}
		
		//dp(i, j) includes empty string
		//subtract 1 because the question is to
		// find the number of different "non-empty" palindromic subsequences
		return dp(0, n - 1, new int[n][n], prv, nxt);
	}
	
	public int dp(int i, int j, int[][] memo, int[][] prv, int[][] nxt){
		if(memo[i][j] > 0) {
			return memo[i][j];
		}
		if(i > j) {
			return 1; // empty string counts as 1
		}
		int ans = 1;
		for (int k = 0; k < 4; ++k){
			int next = nxt[i][k];
			int prev = prv[j][k];
			if(i <= next && next <= j) {
				// only use one letter at next as palindrome
				ans++;
			}
			if(-1 < next && next < prev) {
				// use two letter at both next and prev as two ends
				ans += dp(next + 1, prev - 1, memo, prv, nxt);
			}
			if(ans >= MOD) {
				ans -= MOD;
			}
		}
		memo[i][j] = ans;
		return ans;
	}

}

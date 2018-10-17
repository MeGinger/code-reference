Letter Combinations of a Phone Number

class Solution {
    // 0, 1 - nothing represented
    private static final String[] mappings = {
      "", "", "abc", "def", "ghi", 
      "jkl", "mno", "pqrs", "tuv", "wxyz"  
    };
    
    public List<String> letterCombinations(String digits) {
        if (digits == null || digits.length() == 0) {
            return Collections.emptyList();
        }
        
        List<String> res = new ArrayList<>();
        
        char[] ds = digits.toCharArray();
        dfs(ds, 0, new char[ds.length], res);
        return res;        
    }
    
    private void dfs(char[] digits, int index, char[] letters, List<String> res) {
        if (index == digits.length) {
            res.add(new String(letters));
            return;
        }
        
        String mapping = mappings[digits[index] - '0'];
        char[] choices = mapping.toCharArray();
        for (int i = 0; i < choices.length; i++) {
            letters[index] = choices[i];
            dfs(digits, index + 1, letters, res);
        }
    }
}

Max Area of Island

class Solution {
    private static final int[][] dirs = new int[][] {
        {1, 0}, {-1, 0}, {0, 1}, {0, -1}
    };
    public int maxAreaOfIsland(int[][] grid) {
        if (grid == null || grid.length == 0 || grid[0].length == 0) {
            return 0;
        }
        
        int r = grid.length;
        int c = grid[0].length;
        
        int max = 0;
        for (int i = 0; i < r; i++) {
            for (int j = 0; j < c; j++) {
                if (grid[i][j] == 1) {
                    // grid[i][j] = 0;
                    max = Math.max(max, dfs(grid, i, j));
                }
            }
        }
        return max;
    }
    
    private int dfs(int[][] grid, int i, int j) {
        int res = 1;
        grid[i][j] = 0;
        for (int[] dir : dirs) {
            int x = dir[0] + i;
            int y = dir[1] + j;
            
            if (0 <= x && x < grid.length && 0 <= y && y < grid[0].length &&
                grid[x][y] == 1) {
                // grid[x][y] = 0; same
                res += dfs(grid, x, y);
            }
        }
        
        return res;
    }
}

Longest Increasing Path in a Matrix

boolean array visited cannot be used!

class Solution {
    private static final int[][] dirs = new int[][]{
        {0, 1}, {0, -1}, {1, 0}, {-1, 0}  
    };
    
    public int longestIncreasingPath(int[][] matrix) {
        if (matrix == null || matrix.length == 0 || matrix[0].length == 0) {
            return 0;
        }
        
        int r = matrix.length;
        int c = matrix[0].length;
        
        int[][] cache = new int[r][c];
        boolean[][] visited = new boolean[r][c];
        
        int max = 1;
        for (int i = 0; i < r; i++) {
            for (int j = 0; j < c; j++) {
                max = Math.max(max, dfs(matrix, i, j, cache));                
            }
        }
        return max;
    }
    
    private int dfs(int[][] matrix, int i, int j, int[][] cache) {
        if (cache[i][j] != 0) {
            return cache[i][j];
        }
        
        
        int max = 1;
        for (int[] dir : dirs) {
            int x = i + dir[0];
            int y = j + dir[1];
            
            if (0 <= x && x < matrix.length && 0 <= y && y < matrix[0].length &&
                matrix[i][j] < matrix[x][y]) {
                int len = 1 + dfs(matrix, x, y, cache);
                max = Math.max(max, len);
            }
        }
        
        cache[i][j] = max;
        return max;
    }
}

Burst Balloons
// DFS/Recursion from top to bottom with memorization
// equivalent to DP but easier to understand
class Solution {
    // 0 ≤ nums[i] ≤ 100
    public int maxCoins(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }
        
        int len = nums.length + 2;
        int[] extend = new int[len];
        for (int i = 1; i < len - 1; i++) {
            extend[i] = nums[i - 1];
        }
        extend[0] = extend[len - 1] = 1;
        
        int[][] memo = new int[len][len];
        
        return burst(extend, 0, len - 1, memo);
    }
    
    private int burst(int[] nums, int left, int right, int[][] memo) {
        if (left + 1 == right) {
            return 0;
        }
        
        if (memo[left][right] > 0) {
            return memo[left][right];
        }
        int max = 0;
        for (int i = left + 1; i < right; i++) {
            max = Math.max(max, nums[left] * nums[i] * nums[right] + 
                          burst(nums, left, i, memo) + burst(nums, i, right, memo));
        }
        memo[left][right] = max;
        return max;
    }
}

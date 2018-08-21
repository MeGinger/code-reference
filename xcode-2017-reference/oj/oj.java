	/**
	 * Rearrange String k Distance Apart
	 Given a non-empty string s and an integer k, rearrange the string such that the same characters are at least distance k from each other.
	 All input strings are given in lowercase letters. If it is not possible to rearrange the string, return an empty string "".
	 
	 Example 1:
	 s = "aabbcc", k = 3
	 Result: "abcabc"
	 The same letters are at least distance 3 from each other.
	 
	 Example 2:
	 s = "aaabc", k = 3
	 Answer: ""
	 It is not possible to rearrange the string.
	 
	 Example 3:
	 s = "aaadbbcc", k = 2
	 Answer: "abacabcd"
	 
	 Another possible answer is: "abcabcda"
	 The same letters are at least distance 2 from each other.
	 
	 Reference: task scheduler with cool down time https://discuss.leetcode.com/topic/112/minimal-run-time-scheduler/7
	 
	 This is a greedy problem.
	 Every time we want to find the best candidate: which is the character with the largest remaining count. Thus we will be having two arrays.
	 One count array to store the remaining count of every character. Another array to keep track of the most left position that one character can appear.
	 We will iterated through these two array to find the best candidate for every position. Since the array is fixed size, it will take constant time to do this.
	 After we find the candidate, we update two arrays.
	 */

	// thought: first count the frequency of each character in input string (using data structure map)
	// then iterate from index 0 to length - 1, in each loop find the next valid character in this current position which ...
	// "valid" means the distance requirement should be satisfied
	// if not found, means cannot rearrange, return ""
	// otherwise collect and append the valid character to the result.

	// distance is different from interval: distance = interval + 1 
	// space is not allowed, in that case return ""
	public String rearrangeString(String str, int k) {
		int length = str.length();
		// count frequency of each char
		// different from int[26], more general!
		Map<Character, Integer> count = new HashMap<>();
		for (char c : str.toCharArray()) {
			count.put(c, count.getOrDefault(c, 0) + 1);
		}

		// no sorting involved - findValidMax(..) will do the job

		// what valid map does?
		// store character as key and its next valid index as value
		// e.g., "AABBCC", distance = 3
		// "AxxA"
		//  |  |
		//  0123  
		// If A is placed at the index of 0, the next valid index for A will be 3
		// means the next A shoud be placed at index 3 or more.
		// getByDefault(x, 0) - default index is 0
		Map<Character, Integer> valid = new HashMap<>();
		StringBuilder sb = new StringBuilder();
		for (int index = 0; index < length; index++) {
			char candidate = findValidMax(count, valid, index);
			if (candidate == ' ') {
				return "";
			}

			// update count map
			int frequency = count.get(candidate) - 1;
			if (frequency == 0) {
				count.remove(candidate);
			} else {
				count.put(candidate, frequency);
			}


			valid.put(candidate, index + k);
			sb.append(candidate);
		}
		return sb.toString();
	}
	
	// find the next character candidate which is valid to placed at index index
	// and also has max count - handle the max first!
	private char findValidMax(Map<Character, Integer> count, Map<Character, Integer> valid, int index) {
		int max = Integer.MIN_VALUE;
		char candidate = ' ';
		for (Map.Entry<Character, Integer> entry : count.entrySet()) {
			if (entry.getValue() > max && index >= valid.getOrDefault(entry.getKey(), 0)) {
				max = entry.getValue();
				candidate = entry.getKey();
			}
		}
		return candidate;
	}


		/**
	 * Rearrange String k Distance Apart
	 Given a non-empty string s and an integer k, rearrange the string such that the same characters are at least distance k from each other.
	 All input strings are given in lowercase letters. If it is not possible to rearrange the string, return an empty string "".
	 
	 Example 1:
	 s = "aabbcc", k = 3
	 Result: "abcabc"
	 The same letters are at least distance 3 from each other.
	 
	 Example 2:
	 s = "aaabc", k = 3
	 Answer: ""
	 It is not possible to rearrange the string.
	 
	 Example 3:
	 s = "aaadbbcc", k = 2
	 Answer: "abacabcd"
	 
	 Another possible answer is: "abcabcda"
	 The same letters are at least distance 2 from each other.
	 
	 Reference: task scheduler with cool down time https://discuss.leetcode.com/topic/112/minimal-run-time-scheduler/7
	 
	 This is a greedy problem.
	 Every time we want to find the best candidate: which is the character with the largest remaining count. Thus we will be having two arrays.
	 One count array to store the remaining count of every character. Another array to keep track of the most left position that one character can appear.
	 We will iterated through these two array to find the best candidate for every position. Since the array is fixed size, it will take constant time to do this.
	 After we find the candidate, we update two arrays.
	 */

	// thought: first count the frequency of each character in input string (using data structure map)
	// then iterate from index 0 to length - 1, in each loop find the next valid character in this current position which ...
	// "valid" means the distance requirement should be satisfied
	// if not found, means cannot rearrange, return ""
	// otherwise collect and append the valid character to the result.

	// distance is different from interval: distance = interval + 1 
	// space is not allowed, in that case return ""
	public String rearrangeString(String str, int k) {
		int length = str.length();
		// count frequency of each char
		// different from int[26], more general!
		Map<Character, Integer> count = new HashMap<>();
		for (char c : str.toCharArray()) {
			count.put(c, count.getOrDefault(c, 0) + 1);
		}

		// no sorting involved - findValidMax(..) will do the job

		// what valid map does?
		// store character as key and its next valid index as value
		// e.g., "AABBCC", distance = 3
		// "AxxA"
		//  |  |
		//  0123  
		// If A is placed at the index of 0, the next valid index for A will be 3
		// means the next A shoud be placed at index 3 or more.
		// getByDefault(x, 0) - default index is 0
		Map<Character, Integer> valid = new HashMap<>();
		StringBuilder sb = new StringBuilder();
		for (int index = 0; index < length; index++) {
			char candidate = findValidMax(count, valid, index);
			if (candidate == ' ') {
				return "";
			}

			// update count map
			int frequency = count.get(candidate) - 1;
			if (frequency == 0) {
				count.remove(candidate);
			} else {
				count.put(candidate, frequency);
			}


			valid.put(candidate, index + k);
			sb.append(candidate);
		}
		return sb.toString();
	}
	
	// find the next character candidate which is valid to placed at index index
	// and also has max count - handle the max first!
	private char findValidMax(Map<Character, Integer> count, Map<Character, Integer> valid, int index) {
		int max = Integer.MIN_VALUE;
		char candidate = ' ';
		for (Map.Entry<Character, Integer> entry : count.entrySet()) {
			if (entry.getValue() > max && index >= valid.getOrDefault(entry.getKey(), 0)) {
				max = entry.getValue();
				candidate = entry.getKey();
			}
		}
		return candidate;
	}


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
	 * Random Pick Index
	 Given an array of integers with possible duplicates, randomly output the index of a given target number. You can assume that the given target number must exist in the array.

	 Note:
	 The array size can be very large. Solution that uses too much extra space will not pass the judge.
 
	 Example:
	 int[] nums = new int[] {1,2,3,3,3};
	 Solution solution = new Solution(nums);

	 // pick(3) should return either index 2, 3, or 4 randomly. Each index should have equal probability of returning.
	 solution.pick(3);

 	 // pick(1) should return 0. Since in the array only nums[0] is equal to 1.
 	 solution.pick(1);
	 * Your Solution object will be instantiated and called as such:
	 * Solution obj = new Solution(nums);
	 * int param_1 = obj.pick(target);
	 */

	// thought: basic idea is to use reservoir sampling to pick the random index for a given target number
 	// scan number array from left to right, if it is target number, i increase the count by 1, (count is used to count the target number we have so far), 
 	// and use random instance to generate a random integer from 0 to count - 1, if it is 0, based on reservoir sampling,
 	// we switch the value of those two position - 0 and current index, means the value of the current index is being picked.
 	// in this case, we only store the index in result
	public class Solution3 {

		private int[] nums;
		private Random rnd;

		public Solution3(int[] nums) {
			this.nums = nums; // not using additional space
			this.rnd = new Random();
		}

		public int pick(int target) {
			int result = -1;
			int count = 0; // using count for target number, different from index
			for (int i = 0; i < this.nums.length; i++) {
				if (this.nums[i] != target) {
					continue;
				}
				// Reservoir Sampling - in this case, we pick only one random value, so only check if it equals to 0
				if (this.rnd.nextInt(++count) == 0) {
					result = i;
				}
			}

			return result;
		}
	}

  /**
	Linked List Random Node
	Given a singly linked list, return a random node's value from the linked list. Each node must have the same probability of being chosen.

	Follow up:
	What if the linked list is extremely large and its length is unknown to you? Could you solve this efficiently without using extra space?

	Example:

	// Init a singly linked list [1,2,3].
	ListNode head = new ListNode(1);
	head.next = new ListNode(2);
	head.next.next = new ListNode(3);
	Solution solution = new Solution(head);

	// getRandom() should return either 1, 2, or 3 randomly. Each element should have equal probability of returning.
	solution.getRandom();
	Reservoir_sampling
	O(n) Time & O(1) Space
	*/

	public class Solution {

		private final ListNode head;
		private Random random;

		/**
		 * @param head
		 *            The linked list's head. Note that the head is guanranteed
		 *            to be not null, so it contains at least one node.
		 */
		public Solution(ListNode head) {
			this.head = head;
			this.random = new Random();
		}

		/** Returns a random node's value. */
		public int getRandom() {
			ListNode result = null;
			ListNode current = this.head;

			for (int n = 1; current != null; n++) {
				// similar to random pick index as above
				if (this.random.nextInt(n) == 0) {
					result = current;
				}
				current = current.next;
			}

			return result.val;
		}
	}

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


   class UnionFind {
        int[] id, size; // size: the size of subtree with root not of index
        int count;

        public UnionFind(int len) {
            this.id = new int[len];
            this.size = new int[len];
            this.count = len;
        }

        // boolean: if p and q belong to the same component return true; otherwise return false
        public boolean find(int p, int q) {
            return root(p) == root(q);
        }

        public void union(int p, int q) {
            int pi = root(p), qi = root(q);
            // decide which component to be a child of another component
            // the difference for the child component is 
            // there is one more step for all the nodes under that component to find out the root.
            // so we pick the component with smaller size to be the child.
            if (this.size[pi] < this.size[qi]) {
                this.id[pi] = qi;
                this.size[qi] += this.size[pi];
            } else {
                this.id[qi] = pi;
                this.size[pi] += this.size[qi];
            }
            this.count--;
        }

        private int root(int i) {
            while (i != id[i]) {
                id[i] = id[id[i]]; // path compression
                i = id[i];
            }
            return i;
        }
    }

    /**
     * Number of Connected Components in an Undirected Graph 
	   Given n nodes labeled from 0 to n - 1 and a list of undirected edges (each edge is a pair of nodes), write a function to find the number of connected components in an undirected graph.
	   Example 1:
     0          3
     |          |
     1 --- 2    4
	   Given n = 5 and edges = [[0, 1], [1, 2], [3, 4]], return 2.
	   Example 2:
     0           4
     |           |
     1 --- 2 --- 3
	Given n = 5 and edges = [[0, 1], [1, 2], [2, 3], [3, 4]], return 1.
	Note:
	You can assume that no duplicate edges will appear in edges. Since all edges are undirected, [0, 1] is the same as [1, 0] and thus will not appear together in edges. 
    his is 1D version of Number of Islands II. For more explanations, check out this 2D Solution.
    n points = n islands = n trees = n roots.
    With each edge added, check which island is e[0] or e[1] belonging to.
    If e[0] and e[1] are in same islands, do nothing.
    Otherwise, union two islands, and reduce islands count by 1.
    Bonus: path compression can reduce time by 50%.
     */
    public int countComponents(int n, int[][] edges) {
        UnionFind u = new UnionFind(n);
        for (int i = 0; i < n; i++) {
            u.id[i] = i;
            u.size[i] = 1; // initialize size please
        }

        for (int[] e : edges) {
            if (!u.find(e[0], e[1])) {
                u.union(e[0], e[1]);
            }
        }
        return u.count;
    }

	/**
     * Number of Islands
     * Given a 2d grid map of '1's (land) and '0's (water), count the number of islands.
     * An island is surrounded by water and is formed by connecting adjacent lands horizontally or vertically.
     * You may assume all four edges of the grid are all surrounded by water.
		Example 1:
			11110
			11010
			11000
			00000
			Answer: 1
		Example 2:
			11000
			11000
			00100
			00011
			Answer: 3
     */
	private static final int[][] dir = new int[][]{ 
										  {0, -1},
										  {0, 1},
										  {1, 0},
										  {-1, 0}
									   };

    public int numIslands(char[][] grid) {
        if (grid == null || grid.length == 0 || grid[0].length == 0) {
            return 0;
        }

        int count = 0;
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                if (grid[i][j] != '1') { // append && visited[i][j] == true
                    continue; 
                }

                count++;
                dfsIsland(grid, i, j);
            }
        }

        return count;
    }

    private void dfsIsland(char[][] grid, int x, int y) {
        if (x < 0 || x >= grid.length || y < 0 || y >= grid[0].length || grid[x][y] != '1') { // append || visited[x][y] == true
            return;
        }

        grid[x][y] = '2'; // input being modified... changed to visited[x][y] = true;
        for (int[] dir : dirs) {
            dfsIsland(grid, x + dir[0], y + dir[1]);
        }
    }


    /**
     * Number of Islands II
    A 2d grid map of m rows and n columns is initially filled with water. We may perform an addLand operation which turns the water at position (row, col) into a land. Given a list of positions to operate, count the number of islands after each addLand operation. An island is surrounded by water and is formed by connecting adjacent lands horizontally or vertically. You may assume all four edges of the grid are all surrounded by water.
    Example:
    Given m = 3, n = 3, positions = [[0,0], [0,1], [1,2], [2,1]].
    Initially, the 2d grid grid is filled with water. (Assume 0 represents water and 1 >represents land).
    0 0 0
    0 0 0
    0 0 0
    Operation #1: addLand(0, 0) turns the water at grid0 into a land.
    1 0 0
    0 0 0   Number of islands = 1
    0 0 0
    Operation #2: addLand(0, 1) turns the water at grid0 into a land.
    1 1 0
    0 0 0   Number of islands = 1
    0 0 0
    Operation #3: addLand(1, 2) turns the water at grid1 into a land.
    1 1 0
    0 0 1   Number of islands = 2
    0 0 0
    Operation #4: addLand(2, 1) turns the water at grid2 into a land.
    1 1 0
    0 0 1   Number of islands = 3
    0 1 0
    We return the result as an array: [1, 1, 2, 3]
    http://segmentfault.com/a/1190000004197552
     */
    public List<Integer> numIslands2(int m, int n, int[][] positions) {
        UnionFind set = new UnionFind(m * n);
        Arrays.fill(set.id, -1);
        List<Integer> res = new ArrayList<>();
        int count = 0;
        for (int[] position : positions) {
            int x = position[0], y = position[1];
            int index = x * n + y;
            count++;
            set.id[index] = index;
            for (int i = 0; i < dirs.length; i++) {
                int newX = x + dirs[i][0];
                int newY = y + dirs[i][1];
                if (newX >= 0 && newX < m && newY >= 0 && newY < n) {
                    int newIndex = newX * n + newY;
                    if (set.id[newIndex] == -1) {
                        continue; // not visited
                    }
                    if (!set.find(index, newIndex)) {
                        set.union(index, newIndex);
                        count--;
                    }
                }
            }
            res.add(count);
        }
        return res;
    }

/**
 * Minimum Spanning Tree
 * 题目内容是这样的，给十几个城市供电，连接不同城市的花费不同，让花费最小同时连到所有的边。给出一系列connection类，里面是edge两端的城市名和它们之间的一个cost，找出要你挑一些边，把所有城市连接起来并且总花费最小。不能有环，最后所以城市要连成一个连通块。
 * 不能的话输出空表，最后还要按城市名字排序输出，按照node1来排序,如果一样的话再排node2。 输入: {“Acity”,”Bcity”,1}
 * (“Acity”,”Ccity”,2} (“Bcity”,”Ccity”,3} 输出： (“Acity”,”Bcity”,1}
 * (“Acity”,”Ccity”,2} 补充一句，test case一共有6个。
 */
class Connection {
    String node1;
    String node2;
    int cost;

    public Connection(String a, String b, int c) {
        node1 = a;
        node2 = b;
        cost = c;
    }
}

public class MST {
    static class DisjointSet {
        Set<String> set;
        Map<String, String> map;
        int count;

        public DisjointSet() {
            this.count = 0;
            this.set = new HashSet<>();
            this.map = new HashMap<>();
        }

        public void makeSet(String s) {
            if (this.set.contains(s)) {
                return;
            }
            this.count++;
            this.set.add(s);
            this.map.put(s, s);
        }

        public String root(String s) {
            if (!this.set.contains(s)) {
                return null;
            }
            String v = this.map.get(s);
            if (s.equals(v)) {
                return s;
            }
            String root = this.root(v);
            // path compression: 不是只压一层，直接压至root
            this.map.put(s, root); 
            return root;
        }

        public void union(String s, String t) {
            if (!this.set.contains(s) || !this.set.contains(t)) {
                return;
            }
            if (s.equals(t)) {
                return;
            }
            this.count--;
            this.map.put(s, t);
        }
    }

    public static List<Connection> getMST(List<Connection> connections) {
        Collections.sort(connections, (a, b) -> Integer.compare(a.cost, b.cost));
        DisjointSet set = new DisjointSet();
        List<Connection> res = new ArrayList<>();
        for (Connection connection : connections) {
            set.makeSet(connection.node1);
            set.makeSet(connection.node2);
        }
        for (Connection connection : connections) {
            String s = set.root(connection.node1);
            String t = set.root(connection.node2);
            if (!s.equals(t)) {
                set.union(s, t);
                res.add(connection);
                if (set.count == 1) {
                    break;
                }
            }
        }
        if (set.count == 1) {
            Collections.sort(res, (a, b) -> {
                if (a.node1.equals(b.node1)) {
                    return a.node2.compareTo(b.node2);
                }
                return a.node1.compareTo(b.node1);
            });
            return res;
        }

        return Collections.emptyList();
    }

    /**
     * Find the Weak Connected Component in the Directed Graph
Find the number Weak Connected Component in the directed graph. Each node in the graph contains a label and a list of its neighbors. (a connected set of a directed graph is a subgraph in which any two vertices are connected by direct edge path.)
Example
Given graph:
A----->B  C
 \     |  | 
  \    |  |
   \   |  |
    \  v  v
     ->D  E <- F
Return {A,B,D}, {C,E,F}. Since there are two connected component which are {A,B,D} and {C,E,F}
Note
Sort the element in the set in increasing order
     */
    public List<List<Integer>> connectedSet2(ArrayList<DirectedGraphNode> nodes) {
        List<List<Integer>> res = new ArrayList<>();
        HashMap<DirectedGraphNode, DirectedGraphNode> map = new HashMap<>();
        for (DirectedGraphNode node : nodes) {
            for (DirectedGraphNode n : node.neighbors) {
                DirectedGraphNode fa = find(map, node);
                DirectedGraphNode ch = find(map, n);
                map.put(fa, ch);
            }
        }
        
        Map<DirectedGraphNode, List<Integer>> record = new HashMap<>();
        for (DirectedGraphNode node : nodes) {
            DirectedGraphNode val = find(map, node);
            List<Integer> group = record.get(val);
            if (group == null) {
                group = new ArrayList<>();
                record.put(val, group);
                res.add(group);
            }
            group.add(node.label);
        }

        for (List<Integer> group : res) {
            Collections.sort(group);
        }
        return res;
    }
   
    private DirectedGraphNode find(
            HashMap<DirectedGraphNode, DirectedGraphNode> map,
            DirectedGraphNode k) {
        DirectedGraphNode v = map.get(k);
        if (v == null) {
            map.put(k, k);
            return k;
        }
        while (v != k) {
            k = v;
            v = map.get(k);
        }
        return k;
    }
    public static void main(String[] args) {
        ArrayList<Connection> connections = new ArrayList<>();
        // connections.add(new Connection("Acity","Bcity",1));
        // connections.add(new Connection("Acity","Ccity",2));
        // connections.add(new Connection("Bcity","Ccity",3));
        connections.add(new Connection("A", "B", 6));
        connections.add(new Connection("B", "C", 4));
        connections.add(new Connection("C", "D", 5));
        connections.add(new Connection("D", "E", 8));
        connections.add(new Connection("E", "F", 1));
        connections.add(new Connection("B", "F", 10));
        connections.add(new Connection("E", "C", 9));
        connections.add(new Connection("F", "C", 7));
        connections.add(new Connection("B", "E", 3));
        connections.add(new Connection("A", "F", 1));

        List<Connection> res = getMST(connections);
        for (Connection c : res) {
            System.out.println(c.node1 + " -> " + c.node2 + " cost : " + c.cost);
        }
    }

}

/**
* Accounts Merge
*Given a list accounts, each element accounts[i] is a list of strings, where the first element accounts[i][0] is a name, and the rest of the elements are emails representing emails of the account.
*
*Now, we would like to merge these accounts. Two accounts definitely belong to the same person if there is some email that is common to both accounts. Note that even if two accounts have the same name, they may belong to different people as people could have the same name. A person can have any number of accounts initially, but all of their accounts definitely have the same name.
*
*After merging the accounts, return the accounts in the following format: the first element of each account is the name, and the rest of the elements are emails in sorted order. The accounts themselves can be returned in any order.
*
*Example 1:
*Input: 
*accounts = [["John", "johnsmith@mail.com", "john00@mail.com"], ["John", "johnnybravo@mail.com"], ["John", "johnsmith@mail.com", "john_newyork@mail.com"], ["Mary", "mary@mail.com"]]
*Output: [["John", 'john00@mail.com', 'john_newyork@mail.com', 'johnsmith@mail.com'],  ["John", "johnnybravo@mail.com"], ["Mary", "mary@mail.com"]]
*Explanation: 
*The first and third John's are the same person as they have the common email "johnsmith@mail.com".
*The second John and Mary are different people as none of their email addresses are used by other accounts.
*We could return these lists in any order, for example the answer [['Mary', 'mary@mail.com'], ['John', 'johnnybravo@mail.com'], 
*['John', 'john00@mail.com', 'john_newyork@mail.com', 'johnsmith@mail.com']] would still be accepted.
*Note:
*
*The length of accounts will be in the range [1, 1000].
*The length of accounts[i] will be in the range [1, 10].
*The length of accounts[i][j] will be in the range [1, 30].
*/

// Input: accounts = [
//	["John", "johnsmith@mail.com", "john00@mail.com"], -> email to name one-to-one mapping, email to id one-to-one mapping (each email has a unique id)
//  ["John", "johnnybravo@mail.com"], 
//  ["John", "johnsmith@mail.com", "john_newyork@mail.com"], 
//  ["Mary", "mary@mail.com"]]
// Output: [
//  ["John", 'john00@mail.com', 'john_newyork@mail.com', 'johnsmith@mail.com'],  
//  ["John", "johnnybravo@mail.com"], 
//  ["Mary", "mary@mail.com"]
//]
	public List<List<String>> accountsMerge(List<List<String>> accounts) {
		Map<String, String> emailToName = new HashMap<>();
		Map<String, Integer> emailToID = new HashMap<>();
		int id = 0;

		// initialization
		for (List<String> account : accounts) {
			String name = "";
			for (String email : account) {
				if (name == "") {
					name = email;
					continue;
				}
				emailToName.put(email, name);
				if (!emailToID.containsKey(email)) { 
					emailToID.put(email, id++); // each email has a unique id
				}
			}
		}

		// initialize UnionFind
		UnionFind u = new UnionFind(emailToID.size());
		for (int i = 0; i < emailToID.size(); i++) {
			u.id[i] = i; // id
			u.size[i] = 1; // size
		}

		// union id of each email in the same account
		// a unique email corresponds to a unique id
		for (List<String> account : accounts) {
			for (int i = 2; i < account.size(); i++) {
				u.union(emailToID.get(account.get(i)), emailToID.get(account.get(1)));
			}
		}

		// 
		Map<Integer, List<String>> ans = new HashMap<>();
		for (String email : emailToName.keySet()) {
			int index = u.root(emailToID.get(email));
			ans.computeIfAbsent(index, x -> new ArrayList<>()).add(email);
		}
		for (List<String> component : ans.values()) {
			Collections.sort(component);
			component.add(0, emailToName.get(component.get(0)));
		}
		return new ArrayList<>(ans.values());
	}

	// Tree Traversal
	public static void iterativePreOrder(TreeNode root) {
		if (root == null) {
			return;
		}

		Stack<TreeNode> s = new Stack<>();
		s.push(root);
		TreeNode p = null;
		while (!s.isEmpty()) {
			p = s.pop();
			System.out.println(p.getValue());
			if (p.getRight() !+ null) {
				s.push(p.getRight());
			}
			if (p.getLeft() != null) {
				s.push(p.getLeft());
			}
		}
	}

	public static void iterativeInOrder(TreeNode root) {
		if (root == null) {
			return;
		}

		Stack<TreeNode> s = new Stack<>();
		TreeNode current = root;
		while (true) {
			if (current != null) {
				s.push(current);
				current = current.getLeft();
				continue;
			}

			if (s.isEmpty()) {
				break;
			}

			current = s.pop();
			System.out.println(current.getValue());
			current = current.right;
		}
	}

    public List<Integer> postorderTraversalByIteration(TreeNode root) {
        List<Integer> result = new ArrayList<>();
        Stack<TreeNode> stack = new Stack<>();
        
        if (root == null) { // do not forget
            return result;
        }
        
        TreeNode curt = null, prev = null;
        
        stack.push(root);
        while (!stack.isEmpty()) {
            curt = stack.peek();
            // we are traversing down the tree
            if (prev == null || prev.left == curt || prev.right == curt) {
                if (curt.left != null) {
                    stack.push(curt.left);
                } else if (curt.right != null) {
                    stack.push(curt.right);
                }
            // we are traversing up the tree from the left
            } else if (curt.left == prev) {
                if (curt.right != null) {
                    stack.push(curt.right);
                }
            // we are traversing up the tree from the right    
            } else {
                result.add(curt.val);
                stack.pop();
            }
            prev = curt;
        }
        
        return result;
    }


    // Day change(cell growth): int[] dayChange(int[] cells, int days), 
    // cells 数组，有8个元素，每天的变化情况是 当前位置 cell = (cell[i - 1] == cell[i + 1]) ? 0 : 1， 
    // 默认cells[0]左边和cells[cells.length - 1]右边的位置元素都是0， 求days天后的变化.

    // XOR
	//   { 0, 1, 1, 0, 1, 1, 1 }    day 1
	// [0, 0, 1, 1, 0, 1, 1, 1, 0]  day 1 extended
	// [0, 1, 1, 1, 0, 1, 0, 1, 0]  day 2
	// [0, 1, 0, 1, 0, 0, 0, 0, 0]  day 3
	// [0, 0, 0, 0, 1, 0, 0, 0, 0]
	// [0, 0, 0, 1, 0, 1, 0, 0, 0]
	/*  System.arraycopy(src, srcPos, dest, destPos, length);
		src − This is the source array.
		srcPos − This is the starting position in the source array.
		dest − This is the destination array.
		destPos − This is the starting position in the destination data.
		length − This is the number of array elements to be copied.
	*/

	// Arrays.toString(arr)
	// Collection (list, set) 可以直接打 .toString()

	/*  Arrays.copyOfRange(original, from, to);
		original − This is the array from which a range is to to be copied.
		from − This is the initial index of the range to be copied, inclusive.
		to − This is the final index of the range to be copied, exclusive.
	*/

    public static int[] daysChange(int[] days, int n) {
        if (days == null || n <= 0) {
            return days;
        }
        int length = days.length;
        int[] arr = new int[length + 2];
        
		// can be replaced with System.arraycopy(...)
        arr[0] = arr[length + 1] = 0;
        for (int i = 1; i <= length; i++) {
            arr[i] = days[i - 1];
        }

        for (int i = 0; i < n; i++) {
        	int pre = arr[0];
            for (int j = 1; j <= length; j++) {
                int temp = arr[j];
                arr[j] = pre ^ arr[j + 1];
                pre = temp;
            }
        }
        return Arrays.copyOfRange(arr, 1, length + 1);
    }

   	public static int[] daysChange2(int[] days, int n) {
        if (days == null || n <= 0) {
            return days;
        }
        int[] arr = new int[days.length + 2];
        System.arraycopy(days, 0, arr, 1, days.length);
        while (n-- > 0) { // == for (int i = 0; i < n; i++) {...}
            int pre = arr[0]; // 0
            System.out.println(Arrays.toString(arr));
            for (int j = 1; j <= days.length; j++) {
                int temp = arr[j];
                arr[j] = pre ^ arr[j + 1];
                pre = temp;
            }
        }
        return Arrays.copyOfRange(arr, 1, days.length + 1);
    }

    /**
		Five Scores
		写好了一个叫Result的类，中文翻译成节点，题目是输入是一堆节点，节点里面有两个属性学生id和分数
		保证每个学生至少会有5个分数，求每个人最高的5个分数的平均分。返回的是Map, key是id，value是每个人最高5个分数的平均分double是平均分。
     */
    public static Map<Integer, Double> getHighFive(Result[] results) {
        Map<Integer, PriorityQueue<Integer>> map = new HashMap<>(); 
        // PriorityQueue: minHeap as default
        for (Result r : results) {
            PriorityQueue<Integer> q = map.computeIfAbsent(r.id, k -> new PriorityQueue<Integer>(5));
            if (q.size() < 5) {
                q.offer(r.value);
                continue;
            }
            
            if (q.peek() >= r.value) { // only compare with the min top node
                continue;
            }

            q.poll();
            q.offer(r.value);
        }

        Map<Integer, Double> res = new HashMap<>();
        for (Entry<Integer, PriorityQueue<Integer>> entry : map.entrySet()) {
            int id = entry.getKey();
            int sum = 0;
            PriorityQueue<Integer> q = entry.getValue();
            // 比每次调用poll() 时间性能得到优化，以上的课有note
            // for : O(k)
            // poll : O(klog(k)), 但preserve order
            for (int n : q) { 
                sum += n;
            }
            res.put(id, ((double) sum) / 5); // low cast to high - no precision issue
        }
        return res;
    }

	/**
     * Maximum Subtree of Average
     * 就是给一棵多叉树，表示公司内部的上下级关系。每个节点表示一个员工，节点包含的成员是他工作了几个月(int)，以及一个下属数组(ArrayList)
     * 目标就是找到一棵子树，满足：这棵子树所有节点的工作月数的平均数是所有子树中最大的。最后返回这棵子树的根节点
     * 这题补充一点，返回的不能是叶子节点(因为叶子节点没有下属)，一定要是一个有子节点的节点
     */
     class Node {
        int val;
        List<Node> children;

        public Node(int val) {
            this.val = val;
            children = new ArrayList<Node>();
        }
    }

    static class SumCount {
        int sum;
        int count;

        public SumCount(int sum, int count) {
            this.sum = sum;
            this.count = count;
        }
    }

    static Node ans;
    static double max = 0;
    public static Node find(Node root) {
        // Initialize static variables is very important for AMZAON OA!
        ans = null;
        max = 0;
        DFS(root);
        return ans;
    }

    private static SumCount DFS(Node root) {
        if (root == null) {
            return new SumCount(0, 0);
        }
        
        //////////// 这一块不写也可以的 ////////////
        if (root.children == null || root.children.isEmpty()) {
            return new SumCount(root.val, 1);
        }
        //////////////////////////////////////////

		// divde and conquer
        int sum = root.val;
        int count = 1;
        for (Node itr : root.children) {
            SumCount sc = DFS(itr);
            sum += sc.sum;
            count += sc.count;
        }
        // count > 1: 这题补充一点，返回的不能是叶子节点(因为叶子节点没有下属)，一定要是一个有子节点的节点
        if (count > 1 && ((double) sum) / count > max) {
            max = ((double) sum) / count;
            ans = root;
        }
        return new SumCount(sum, count);
    }
    
    /**
     * Sliding Window Maximum
	Given an array nums, there is a sliding window of size k which is moving from the very left of the array to the very right.
	You can only see the k numbers in the window. Each time the sliding window moves right by one position.
	For example,
	Given nums = [1,3,-1,-3,5,3,6,7], and k = 3.
	Window position                Max
	---------------               -----
	[1  3  -1] -3  5  3  6  7       3
	 1 [3  -1  -3] 5  3  6  7       3
	 1  3 [-1  -3  5] 3  6  7       5
	 1  3  -1 [-3  5  3] 6  7       5
	 1  3  -1  -3 [5  3  6] 7       6
	 1  3  -1  -3  5 [3  6  7]      7
	sliding windoe: (i-k, i) i++
	decreasing:
	LinkedList = 3, -1
	请用例子讲思路，每个loop, LinkedList的变化，另外给出有duplicate的例子更好，因为相等的需要留下
	Therefore, return the max sliding window as [3,3,5,5,6,7].
	You may assume k is always valid, 1 鈮� k 鈮� input array's size
     */
    public int[] maxSlidingWindow(int[] nums, int k) {
        LinkedList<Integer> l = new LinkedList<>();
        int n = nums.length;
        if (n == 0 || k == 0) {
            return new int[0];
        }
        int[] result = new int[n - k + 1];

        for (int i = 0; i < k; i++) { // First fill in result[0] ~ result[k-1]
            addToSlidingWindow(nums[i], l);
        }

        for (int i = k; i < n; i++) {
        	// take care of the previous window
            result[i - k] = l.getFirst(); 
            if (nums[i - k] == l.getFirst()) {
                l.removeFirst();
            }

            // build new window
            addToSlidingWindow(nums[i], l);
        }
        result[n - k] = l.getFirst();
        return result;
    }
    
    private void addToSlidingWindow(int a, LinkedList<Integer> l) {
        while (!l.isEmpty() && l.getLast() < a) {
            l.removeLast();
        }
        l.addLast(a);
    }

    /**
	Maze: 给个array,其中只有一格是9，其他格子是0或1，0表示此路不通，1表示可以走，判断从（0,0) 点开始上下左右移动能否找到这个是9的格子
     */
    public boolean maze(int[][] matrix) {
        if (matrix == null || matrix.length == 0 || matrix[0].length == 0) {
            return false;
        }
        if (matrix[0][0] == 9) {
            return true;
        }
        int m = matrix.length, n = matrix[0].length;
        Queue<int[]> queue = new LinkedList<int[]>();
        queue.offer(new int[] { 0, 0 });
        matrix[0][0] = 1;
        while (!queue.isEmpty()) {
            int[] cur = queue.poll();
            for (int[] dir : dirs) {
                int x = cur[0] + dir[0];
                int y = cur[1] + dir[1];
                if (x >= 0 && x < m && y >= 0 && y < n) {
                    if (matrix[x][y] == 9) {
                        return true;
                    }
                    if (matrix[x][y] == 0) {
                        queue.offer(new int[] { x, y });
                        matrix[x][y] = 1;
                    }
                }
            }
        }
        return false;
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
  * Subsets
	Given a set of distinct integers, nums, return all possible subsets.
	Note: The solution set must not contain duplicate subsets.
	For example,
	If nums = [1,2,3], a solution is:
	[
	  [3],
	  [1],
	  [2],
	  [1,2,3],
	  [1,3],
	  [2,3],
	  [1,2],
	  []
	]
  */
    public List<List<Integer>> subsets(int[] nums) {
		List<List<Integer>> res = new ArrayList<>(1 << nums.length);
	  	Arrays.sort(nums);
	  	res.add(new ArrayList<>()); // empty list
	  	subsets(nums, new int[nums.length], 0, 0, res);
	  	return res;
    }

    private void subsets(int[] in, int[] out, int recurlen, int start,
	   List<List<Integer>> res) {
	   for (int i = start; i < in.length; i++) {
	        out[recurlen] = in[i];
	        List<Integer> l = new ArrayList<>(recurlen + 1);
	   	    for (int j = 0; j <= recurlen; j++) {
	    	    l.add(out[j]);
	   	    }
	   		res.add(l);
	   		if (i < in.length - 1) {
	    		subsets(in, out, recurlen + 1, i + 1, res);
	   		}
	  	}
	}

	// iterative
	public List<List<Integer>> subsets2(int[] nums) {
	  	Arrays.sort(nums);
	  	List<List<Integer>> ret = new ArrayList<>(1 << nums.length); // 2^num.length
	  	ret.add(new ArrayList<>()); // empty list []

	  	for (int i = 0; i < nums.length; i++) {
	   		int size = ret.size();
	   		for (int j = 0; j < size; j++) {
	    		List<Integer> sub = new ArrayList<>(ret.get(j));
	    		sub.add(nums[i]);
	    		ret.add(sub);
	   		}
	  	}

	  	// {}

	  	// LOOP: 1
	  	// {1}

	  	// LOOP: 2
	  	// {2}, {1, 2}

	  	// LOOP: 3
	  	// {3}, {1, 3}, {2, 3}, {1, 2, 3}
		return ret;
	}

	/**
	  * Subsets II
	  * Given a collection of integers that might contain duplicates,
	  * S, return all possible subsets. Elements in a subset must be in
	  * non-descending order. The solution set must not contain duplicate
	  * subsets. If S = [1,2,2], a solution is: [ [2], [1], [1,2,2], [2,2],
	  * [1,2], [] ]
	  */

	 // duplicate elements
	 // non-descending order = ascending order
	 public List<List<Integer>> subsetsWithDup(int[] nums) {
	    Arrays.sort(nums);
	    List<List<Integer>> ret = new ArrayList<>(1 << nums.length); // 2^num.length
	    ret.add(new ArrayList<>()); // empty list []

	    int start = 0;
	    for (int i = 0; i < nums.length; i++) {
	        int size = ret.size();
	        for (int j = start; j < size; j++) {
	          	List<Integer> sub = new ArrayList<>(ret.get(j));
	          	sub.add(nums[i]);
	          	ret.add(sub);
	        }

	        // 克隆全部：     [], [1], [2], [1, 2] | [2], [1, 2], [2, 2], [1, 2, 2]
	 		// 只克隆后半部分：[], [1], [2], [1, 2] | [2, 2], [1, 2, 2]
	 		
	        if (i < nums.length - 1 && nums[i + 1] == nums[i]) { // prepare for the next loop, so make sure i != nums.length - 1
	          	start = size; // 只克隆后半部分
	        } else {
	        	start = 0;
	        }
	    }

	    return ret;
	 }


    // The gray code is a binary numeral system where two successive values differ in only one bit.
    // ^ -> int
    // byte: 8 bits
    // int: 32 bits 
    //          24 | 8
    // a^b  = 1..1 | result
    //          24 | 8
    // 0xFF = 0..0 | 1...1
    // F = 1111
    public static boolean grayCode(byte a, byte b) {
        int c = (a ^ b) & 0xFF;
        System.out.println(Integer.toBinaryString(c));
        return c != 0 && (c & (c - 1)) == 0;
    }
    // c & (c - 1) 每次把最低位的1删掉
    // 1010101010 => 1010101000 => 1010100000 => 1010000000
    // 可以用于计算一个数的1bit的数目




    // maxHeap solution, worst O(nlogk) time, best O(n) time, O(k) space
    public Point[] findKClosestPoints(Point[] points, int k, Point target) {
        if (points == null || points.length == 0 || k == 0) {
            return new Point[0];
        }
        if (k >= points.length) {
            return points;
        }
        PointWithDistance[] distances = Arrays.stream(points).map(p -> new PointWithDistance(p, getDistance(p, target)))
                .toArray(size -> new PointWithDistance[size]);
        // 做这一步的原因是性能考虑：comparator.compare()调用时，都需要计算distance
        // 这个wrapper class，pre-compute distance，便于比较
        System.out.println(Arrays.toString(distances));
        Queue<PointWithDistance> maxHeap = new PriorityQueue<>(k);
        for (PointWithDistance point : distances) {
            if (maxHeap.size() < k) {// put k points to heap
                maxHeap.offer(point);
                continue;
            }
            if (maxHeap.peek().compareTo(point) < 0) {
                // if maxHeap's point's distance is far from target than point
                maxHeap.poll();
                maxHeap.offer(point);
            }
        }
        Point[] result = new Point[k];
        for (int i = k - 1; i >= 0; i--) {
            result[i] = maxHeap.poll().point;
        }
        return result;
    }
    // priority queue
    public Point[] findKClosestPoints2(Point[] points, int k, Point target) {
        if (points == null || points.length == 0 || k == 0) {
            return new Point[0];
        }
        if (k >= points.length) {
            return points;
        }
        Comparator<Point> pointComparator = new Comparator<Point>() {
            @Override // compare point a and point b's distance from target
            public int compare(Point a, Point b) {
                long d1 = getDistance(a, target);
                long d2 = getDistance(b, target);
                return Long.compare(d2, d1);
            }
        };
        Queue<Point> maxHeap = new PriorityQueue<>(k, pointComparator);

        /*
        Java8 - AMAZON OA 17年不支持java8
        Queue<Point> maxHeap = new PriorityQueue<>(k, (a, b) -> {
            long d1 = getDistance(a, target);
            long d2 = getDistance(b, target);
            return Long.compare(d2, d1);
        });        
        */
        for (Point point : points) {
            if (maxHeap.size() < k) {// put k points to heap
                maxHeap.offer(point);
                continue;
            }
            // Since we use pointComparator, compare(a, b) return Long.compare(d2, d1);
            // compare(maxHeap.peek(), point) < 0 -> maxHeap.peek() > point
            // normally, compare(a, b) < 0 -> a < b
            if (pointComparator.compare(maxHeap.peek(), point) < 0) {
                // if maxHeap's point's distance is far from target than point
                maxHeap.poll(); // remove root
                maxHeap.offer(point);
            }
        }
        
        Point[] result = new Point[k];
        for (int i = k - 1; i >= 0; i--) {
            result[i] = maxHeap.poll();
        }

        /*
         可以直接遍历, 但不保证大小顺序
         for (Point p : maxHeap) {
            ... 
         }
        */ 
        return result;
    }

    // OPTIMAL SOLUTION - 
    // QuickSelect solution, average O(n) time(O(n + klogk) time if output is
    // sorted), worst case O(n^2) time, O(1) space
    public Point[] findKClosestPoints3(Point[] points, int k, Point target) {
        if (points == null || points.length == 0 || k == 0) {
            return new Point[0];
        }
        if (k >= points.length) {
            return points;
        }
        int start = 0, end = points.length - 1, index = k - 1;
        PointWithDistance[] distances = Arrays.stream(points).map(p -> new PointWithDistance(p, getDistance(p, target)))
                .toArray(size -> new PointWithDistance[size]);
        while (start < end) {
            int pivot = partition(distances, start, end);
            if (pivot < index) {
                start = pivot + 1;
            } else if (pivot > index) {
                end = pivot - 1;
            } else {
                break;
            }
        }

        System.out.println(Arrays.toString(distances));
        Point[] result = new Point[k];
        for (int i = 0; i < k; i++) {
            result[i] = distances[i].point;
        }
        return result;
    }

    private int partition(PointWithDistance[] points, int start, int end) {
        int pivot = start;
        while (start <= end) {
            while (start <= end && points[start].distance <= points[pivot].distance) {
                start++;
            }
            while (start <= end && points[end].distance > points[pivot].distance) {
                end--;
            }
            if (start > end) {
                break;
            }
            swap(points, start, end);
        }
        swap(points, pivot, end);
        return end;
    }

    private long getDistance(Point a, Point b) {
        return ((long) a.x - b.x) * ((long) a.x - b.x) + ((long) a.y - b.y) * ((long) a.y - b.y);
    }

    private void swap(PointWithDistance[] points, int a, int b) {
        PointWithDistance tmp = points[a];
        points[a] = points[b];
        points[b] = tmp;
    }

    class PointWithDistance implements Comparable<PointWithDistance> {
        Point point;
        long distance;

        PointWithDistance(Point point, long distance) {
            this.point = point;
            this.distance = distance;
        }

        @Override
        public String toString() {
            return "[point=" + point + ", distance=" + distance + "]";
        }

        @Override
        public int compareTo(PointWithDistance o) {
            return Long.compare(o.distance, this.distance);
        }
    }

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

    // window sum
    // presum[i] is sum of nums[i ~ i + k - 1]
    public static int[] getPresum(int[] nums, int k) {
        if (nums == null || nums.length < k || k <= 0) {
            return null;
        }
        int n = nums.length;
        if (n < k) {
            throw new IllegalArgumentException();
        }
        int[] presum = new int[n - k + 1];
        int sum = 0;
        for (int i = 0; i < n; i++) {
            if (i >= k) { // handle the previous sum; i == k, we have first presum nums[0...k - 1]                 
                presum[i - k] = sum;
                sum -= nums[i - k];
            }
            sum += nums[i];
        }
        presum[n - k] = sum;
        return presum;
    }

    public int findLargestSumIn3NonOverlappingSubarrays(int[] nums, int k) {
        int n = nums.length;
        if (n < 3 * k) {
            return 0;
        }
        int[] presum = getPresum(nums, k);
        int[] maxFromLeft = getMaxFromLeft(nums, k, presum);
        int[] maxFromRight = getMaxFromRight(nums, k, presum);
        int result = Integer.MIN_VALUE;
        for (int i = k; i + 2 * k <= n; i++) { // [...i-k, i, i+k] i+2k, i+2k = n
            result = Math.max(result, maxFromLeft[i - k] + presum[i] + maxFromRight[i + k]);
        }

        return result;
    }

    private int[] getMaxFromLeft(int[] nums, int k, int[] presum) {
        int n = nums.length;
        int[] maxFromLeft = new int[n - 3 * k + 1];
        maxFromLeft[0] = presum[0];
        int max = presum[0];
        for (int i = 1; i <= n - 3 * k; i++) {
            max = Math.max(max, presum[i]);
            maxFromLeft[i] = max;
        }
        return maxFromLeft;
    }

    private int[] getMaxFromRight(int[] nums, int k, int[] presum) {
        int n = nums.length;
        int[] maxFromRight = new int[n - k + 1];
        maxFromRight[n - k] = presum[n - k];
        int max = presum[n - k];
        for (int i = n - k - 1; i >= 2 * k; i--) {
            max = Math.max(max, presum[i]);
            maxFromRight[i] = max;
        }
        return maxFromRight;
    }

    // https://leetcode.com/problems/kth-largest-element-in-an-array/description/
    /*

    */ 
    public int findKthLargest(int[] nums, int k) {
        int start = 0, end = nums.length - 1, index = nums.length - k;
        while (start < end) {
            int pivot = partition(nums, start, end);
            if (pivot < index) {
                start = pivot + 1;
            } else if (pivot > index) {
                end = pivot - 1;
            } else {
                return nums[pivot];
            }
        }
        // 
        return nums[start];

    }

    // left | pivot | right
    // nums in the left  < nums[pivot]
    // nums in the right > nums[pivot]


    private int partition(int[] nums, int start, int end) {
        int pivot = start;
        while (start <= end) {
            while (start <= end && nums[start] <= nums[pivot]) {
                start++;
            }
            while (start <= end && nums[end] > nums[pivot]) {
                end--;
            }
            if (start > end) {
                break;
            }
            swap(nums, start, end);
        }
        // at the end !!!!!!!!!!!!!!
        // nums[start] > nums[pivot]
        // nums[end] <= nums[pivot]
        // so swap end and pivot
        swap(nums, end, pivot);
        return end;
    }



	/** Serialize and Deserialize tree; */
	// preorder serialization
	// preorder deserialization
	class Codec {

		private static final String NULL = "#"; // null child
		private static final String SEP = ","; // seperator
	    /**
	     * This method will be invoked first, you should design your own algorithm
	     * to serialize a binary tree which denote by a root node to a string which
	     * can be easily deserialized by your own "deserialize" method later.
	     */
		public String serialize(TreeNode root) {
			if (root == null) {
				return "";
			}

			StringBuilder sb = new StringBuilder();
			// PreOrder traversal
			Stack<TreeNode> s = new Stack<>();
			s.push(root);
			while (!s.isEmpty()) {
				TreeNode cur = s.pop();
				if (cur == null) {
					sb.append(NULL).append(SEP); // method chain!!
					continue;
				}
				sb.append(cur.val).append(SEP);
				s.push(cur.right);
				s.push(cur.left);
			}

			sb.setLength(sb.length() - 1); // REMOVE LAST SEP
			return sb.toString();
		}

	    /**
	     * This method will be invoked second, the argument data is what exactly
	     * you serialized at method "serialize", that means the data is not given by
	     * system, it's given by your own serialize method. So the format of data is
	     * designed by yourself, and deserialize it here as you serialize it in
	     * "serialize" method.
	     */
		public TreeNode deserialize(String data) {
			if (data == null || data.length() == 0) {
				return null;
			}

			StringTokenizer st = new StringTokenizer(data, SEP);
			return deseriaHelper(st);
		}

		private TreeNode deseriaHelper(StringTokenizer st) {
			if (!st.hasMoreTokens()) {
				return null;
			}

			String val = st.nextToken();
			if (val.equals(NULL)) {
				return null;
			}

			// preorder dfs traversal 
			TreeNode root = new TreeNode(Integer.parseInt(val));
			root.left = deseriaHelper(st);
			root.right = deseriaHelper(st);

			return root;
		}
	}



	 /** Serialize and Deserialize BST */

	public class Codec2 {
		private static final String SEP = ","; // seperator

		// PreOrder: Encodes a tree to a single string.
		public String serialize(TreeNode root) {
			if (root == null) {
				return "";
			}
			StringBuilder sb = new StringBuilder();
			// PreOrder traversal
			Stack<TreeNode> st = new Stack<>();
			st.push(root);
			while (!st.empty()) {
				root = st.pop();
				sb.append(root.val).append(SEP);
				if (root.right != null) {
					st.push(root.right);
				}
				if (root.left != null) {
					st.push(root.left);
				}
			}
			return sb.toString();
		}

		// Decodes your encoded data to tree.
		// pre-order traversal
		//  time complexity is O(NlogN). worst case complexity should be O(N^2), when the tree is really unbalanced.
		public TreeNode deserialize(String data) {
			if (data == null || data.length() == 0) {
				return null;
			}
			Queue<Integer> q = new LinkedList<>();
			for (String e : data.split(SEP)) {
				q.offer(Integer.parseInt(e));
			}
			return getNode(q);
		}

	    // some notes:
	    //   5
	    //  3 6
	    // 2   7
		private TreeNode getNode(Queue<Integer> q) { // q: 5, | 3,2, | 6,7 based on BST properties
			if (q.isEmpty()) {
				return null;
			}
			TreeNode root = new TreeNode(q.poll());// root (5)
			Queue<Integer> smallerQueue = new LinkedList<>();
			while (!q.isEmpty() && q.peek() < root.val) {
				smallerQueue.offer(q.poll());
			}
			// smallerQueue : 3,2 storing elements smaller than 5 (root)
			root.left = getNode(smallerQueue);
			// q: 6,7 storing elements bigger than 5 (root)
			root.right = getNode(q);
			return root;
		}
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

	
	// Remove Duplicates from Sorted Array (leetcode 26)
	// problem: Given a sorted array, remove the duplicates in-place such that each element appear only once and return the new length.
	// Do not allocate extra space for another array, you must do this by modifying the input array in-place with O(1) extra memory.
	// Example: Given nums = [1,1,2], Your function should return length = 2, with the first two elements of nums being 1 and 2 respectively.
    // It doesn't matter what you leave beyond the new length.

	// two-pointer
	public int removeDuplicates(int[] nums) {
	    if (nums.length == 0) return 0;
	    int i = 0;
	    for (int j = 1; j < nums.length; j++) {
	        if (nums[j] != nums[i]) {
	            i++;
	            nums[i] = nums[j];
	        }
	    }
	    return i + 1;
	}

	// Remove Duplicates from Sorted Array II (leetcode 80) 
	// problem: Follow up for "Remove Duplicates":
	// What if duplicates are allowed at most twice?
	// For example, Given sorted array nums = [1,1,1,2,2,3],
	// Your function should return length = 5, with the first five elements of nums being 1, 1, 2, 2 and 3. It doesn't matter what you leave beyond the new length.

	// two-pointer
	public int removeDuplicates(int[] nums) {
	   int i = 0;
	   for (int n : nums)
	      if (i < 2 || n > nums[i - 2])
	         nums[i++] = n;
	   return i;
	}

	// Split Array Largest Sum (leetcode 410) 
	// problem: Given an array which consists of non-negative integers and an integer m, you can split the array into m non-empty continuous subarrays. Write an algorithm to minimize the largest sum among these m subarrays.
	// Note: If n is the length of array, assume the following constraints are satisfied:
	// 1 ≤ n ≤ 1000
	// 1 ≤ m ≤ min(50, n)
	// Examples: Input: nums = [7,2,5,10,8] m = 2
	// Output: 18
	// Explanation: There are four ways to split nums into two subarrays. The best way is to split it into [7,2,5] and [10,8], where the largest sum among the two subarrays is only 18.

	// binary seasrch
    public int splitArray(int[] nums, int m) {
        long l = 0; // max - subarrays with size 1
        long r = 0; // sum - only one array 
        int n = nums.length;
        for (int i = 0; i < n; i++) {
            r += nums[i];
            if (l < nums[i]) {
                l = nums[i]; // find max
            }
        }
        long ans = r;
        while (l <= r) {
            long mid = (l + r) >> 1;
            long sum = 0;
            int cnt = 1;
            for (int i = 0; i < n; i++) {
                if (sum + nums[i] > mid) {
                    cnt++;
                    sum = nums[i];
                } else {
                    sum += nums[i];
                }
            }
            if (cnt <= m) {
            	// assume that when cnt < m, the cnt subarrays could be redistributed into m subarrays.
                ans = Math.min(ans, mid); 
                r = mid - 1;
            } else {
                l = mid + 1;
            }
        }
        return (int)l; // ans;      
    }


	// Copy Books (lint code 437)
	// problem : Given an array A of integer with size of n( means n books and number of pages of each book) and k people to copy the book. You must distribute the continuous id books to one people to copy. (You can give book A[1],A[2] to one people, but you cannot give book A[1], A[3] to one people, because book A[1] and A[3] is not continuous.) Each person have can copy one page per minute. 
	// Return the number of smallest minutes need to copy all the books. - minimize the largest sum

    // Example: Given array A = [3,2,4], k = 2. Return 5 (First person spends 5 minutes to copy book 1 and book 2 and second person spends 4 minutes to copy book 3.)
	// Challenge: Could you do this in O(n*k) time ?

    public int copyBooks(int[] pages, int k) {
        // write your code here
        // if(pages == null || pages.length == 0){
        //     return 0;
        // }

        // if(k < 1){
        //     return -1;
        // }

        // int n = pages.length;
        // int[][] T = new int[n + 1][k + 1];

        // for(int j = 1; j <= k; j++){
        //     T[1][j] = pages[0];
        // }

        // int sum = 0;
        // for(int i = 1; i <= n; i++){
        //     sum += pages[i - 1];
        //     T[i][1] = sum;
        // }

        // for(int i = 2; i <= n; i++){
        //     for(int j = 2; j <= k; j++){
        //         if(j > i){
        //             T[i][j] = T[i][j - 1];
        //             continue;
        //         }
        //         int min = Integer.MAX_VALUE;
        //         for(int h = j - 1; h <= i; h++){
        //             int temp = Math.max(T[h][j - 1], T[i][1] - T[h][1]);
        //             min = Math.min(min, temp);
        //         }
        //         T[i][j] = min;
        //     }
        // }

        // return T[n][k];
    }

    public int copyBooks(int[] pages, int k) {
        // write your code here
        //O(n*logM)? O(n*k)
        int l = 0;
        int r = 9999999;
        while( l <= r){
            int mid = l + (r - l) / 2;
            if(search(mid, pages, k))
                r = mid-1;
            else
                l = mid+1;
        }
        return l;
    }

    private boolean search(int total, int[] page, int k){
    //至少有一个人copy，所以count从1开始
        int count = 1;
        int sum = 0;
        for(int i = 0; i < page.length;) {
            if(sum + page[i] <= total){
                sum += page[i++];
            }else if(page[i] <= total){
                sum = 0;
                count++;
            }else{
                return false;
            }
        }

        return count <= k;
    }

	// Wood cuts(lint code 183)
	// problem: Given n pieces of wood with length L[i] (integer array). 
	// Cut them into small pieces to guarantee you could have equal or more than k pieces with the same length. 
	// What is the longest length you can get from the n pieces of wood? Given L & k, 
	// return the maximum length of the small pieces.

 	// Notice
 	// You couldn't cut wood into float length.
 	// If you couldn't get >= k pieces, return 0.
 	// Example: For L=[232, 124, 456], k=7, return 114.

    public int woodCut(int[] L, int k) {
        int max = 0;
        for (int i = 0; i < L.length; i++) {
            max = Math.max(max, L[i]); // max should be sum of L[i] i think? 
            						   // wood cut! wood should be cut - max is max of L[i]
        }
        
        // find the largest length that can cut more than k pieces of wood.
        int start = 1, end = max;
        // start = 1: smallest length of wood
        // end = max: largest length of wood
        while (start + 1 < end) {
            int mid = start + (end - start) / 2;
            if (count(L, mid) >= k) {
                start = mid;
            } else {
                end = mid;
            }
        }
        
        if (count(L, end) >= k) { // return maximum length (end > start)
            return end;
        }
        if (count(L, start) >= k) {
            return start;
        }
        return 0;
    }
    
    private int count(int[] L, int length) {
        int sum = 0;
        for (int i = 0; i < L.length; i++) {
            sum += L[i] / length;
        }
        return sum;
    }


	/*

	22336677
	2336677 (3>2)
	0123456

	to get a largest number

	* replacement digit > original digit
	* the more significant bit where the change happens, the better

	这个思路在remove k digits也有所反映
	
	从前往后扫，cur next, cur在group里，cur < next, cur remove
	
	corner case:

	777777654321 - 这种情况要删最后一个
	     |
	   index
	77777654321

	7777776666655
	            |
	          index
	777777666665

	*/
public class DuplicateNumver {
	public static int solution(int X) {
		int index = -1;
		String s = String.valueOf(X);
		for (int i = 0; i < s.length() - 1; i++) {
			int cur = s.charAt(i) - '0';
			int next = s.charAt(i + 1) - '0';
			if (cur != next) {
				continue;
			}
			// found group
			while (i < s.length() - 1 && s.charAt(i) == s.charAt(i + 1)) {
				i++;
			}
			// i is the last index of a group

			// i | i + 1
			index = i;
			if (i == s.length() - 1) {
				break;
			}
			cur = s.charAt(i) - '0';
			next = s.charAt(i + 1) - '0';
			if (next > cur) {
				return Integer.valueOf(s.substring(0, i) + s.substring(i + 1)); // not including char at i
			}
		}
		if (index == -1) {
			return Integer.MIN_VALUE; // invalid input like single-digit integer
		}
		return Integer.valueOf(s.substring(0, index) + s.substring(index + 1)); // not including char at index
		// corner case:
		// 777777654321 - 这种情况要删最后一个
	    //      |
	    //    index
		// 77777654321
	}

	public static int solution3(int X) {
		int max = Integer.MIN_VALUE;
		StringBuilder builder = new StringBuilder(String.valueOf(X));
		for (int i = 1; i < builder.length(); i++) {
			if (builder.charAt(i - 1) == builder.charAt(i)) {
				StringBuilder b = new StringBuilder(builder.toString());
				b.deleteCharAt(i);
				int a = Integer.valueOf(b.toString()); // try every possibility
				if (a > max) {
					max = a;
				}
			}
		}
		return max;
	}

	public static int solution2(int X) {
		int max = Integer.MIN_VALUE;
		StringBuilder builder = new StringBuilder(String.valueOf(X));
		for (int i = 1; i < builder.length(); i++) {
			if (builder.charAt(i - 1) != builder.charAt(i)) {
				continue;
			}
			while (i < builder.length() - 1 && builder.charAt(i + 1) == builder.charAt(i)) {
				i++;
			}
			StringBuilder b = new StringBuilder(builder.toString());
			b.deleteCharAt(i);
			int a = Integer.valueOf(b.toString());
			if (a > max) {
				max = a;
			}
		}
		return max;
	}

	public static void main(String[] args) {
		int X = 7766554;
		System.out.println(solution(X));
		System.out.println(solution2(X));
		System.out.println(solution3(X));
	}
}



import java.util.ArrayDeque;
import java.util.Deque;

public class KEmptySlot {
  /**
	* 
	K Empty Slots
	There is a garden with N slots. In each slot, there is a flower. The N flowers will bloom one by one in N days. In each day, there will be exactly one flower blooming and it will be in the status of blooming since then.
	Given an array flowers consists of number from 1 to N. Each number in the array represents the place where the flower will open in that day.
	For example, flowers[i] = x means that the unique flower that blooms at day i will be at position x, where i and x will be in the range from 1 to N.
	Also given an integer k, you need to output in which day there exists two flowers in the status of blooming, and also the number of flowers between them is k and these flowers are not blooming.
	If there isn't such day, output -1.
	Example 1:
	Input: 
	flowers: [1,3,2] - flower 1 blooms at day 1; flower 3 blooms at day 2; flower 2 blooms at day 3; 
	k: 1
	Output: 2 - day 2
	Explanation: In the second day, the first and the third flower have become blooming.

	Example 2:
	Input: 
	flowers: [1,2,3]
	k: 1
	Output: -1

	Note:
    The given array will be in the range [1, 20000].

    Time Complexity: O(N), where N is the length of flowers. In enumerating through the O(N) outer loop, we do constant work as MinQueue.popleft and MinQueue.min operations are (amortized) constant time.
    Space Complexity: O(N), the size of our window.
	Reference: Sliding Window Maximum
	*/
	public int kEmptySlots(int[] flowers, int k) {
		int[] days = new int[flowers.length];
		for (int i = 0; i < flowers.length; i++) {
			days[flowers[i] - 1] = i + 1;
		}

		MinQueue<Integer> window = new MinQueue<>();
		int ans = days.length;

		// we can skip last flower
		for (int i = 0; i < days.length - 1; i++) {
			int day = days[i];
			window.addLast(day);
			if (i < k) {
				continue;
			}
			window.pollFirst();
			// The window is maintained as [i - k + 1, i]
			if (k == 0 || days[i - k] < window.min() && days[i + 1] < window.min()) {
				ans = Math.min(ans, Math.max(days[i - k], days[i + 1]));
			}
		}

		return ans < days.length ? ans : -1;
	}
	
	
	private static class MinQueue<E extends Comparable<E>> extends ArrayDeque<E> {
		private static final long serialVersionUID = 1538439138203379928L;
		private Deque<E> mins;

		public MinQueue() {
			this.mins = new ArrayDeque<>();
		}

		@Override
		public void addLast(E x) {
			super.addLast(x);
			while (!this.mins.isEmpty() && x.compareTo(this.mins.peekLast()) < 0) {
				this.mins.pollLast();
			}
			this.mins.addLast(x);
		}

		@Override
		public E pollFirst() {
			E x = super.pollFirst();
			if (x == this.mins.peekFirst()) {
				this.mins.pollFirst();
			}
			return x;
		}

		public E min() {
			return this.mins.peekFirst();
		}
	}
}

/** Interval-related Questions
 * K Empty Slots
 * Meeting Rooms
 * Meeting Rooms II
 * */
import java.util.*;


public class Code14 {
	
	/**
	 * K Empty Slots
	 There is a garden with N slots. In each slot, there is a flower. The N flowers will bloom one by one in N days. In each day, there will be exactly one flower blooming and it will be in the status of blooming since then.
	 Given an array flowers consists of number from 1 to N. Each number in the array represents the place where the flower will open in that day.
	 For example, flowers[i] = x means that the unique flower that blooms at day i will be at position x, where i and x will be in the range from 1 to N.
	 Also given an integer k, you need to output in which day there exists two flowers in the status of blooming, and also the number of flowers between them is k and these flowers are not blooming.
	 If there isn't such day, output -1.
	 Example 1:
	 Input:
	 flowers: [1,3,2]
	 k: 1
	 Output: 2
	 Explanation: In the second day, the first and the third flower have become blooming.
	 
	 Example 2:
	 Input:
	 flowers: [1,2,3]
	 k: 1
	 Output: -1
	 
	 Note:
	 The given array will be in the range [1, 20000].
	 
	 
	 
	 Time Complexity: O(N), where N is the length of flowers. In enumerating through the O(N) outer loop, we do constant work as MinQueue.popleft and MinQueue.min operations are (amortized) constant time.
	 
	 Space Complexity: O(N), the size of our window.
	 
	 Reference: Sliding Window Maximum
	 */
	
	public int kEmptySlots(int[] flowers, int k) {
		int[] days = new int[flowers.length];
		for (int i = 0; i < flowers.length; i++) {
			days[flowers[i] - 1] = i + 1;
		}
		
		MinQueue<Integer> window = new MinQueue<>();
		int ans = days.length;
		
		// we can skip last flower
		for (int i = 0; i < days.length - 1; i++) {
			int day = days[i];
			window.addLast(day);
			if (i < k) {
				// skip first k steps
				continue;
			}

			// i >= k
			// i = k, [0 ~ k], k + 1,  kick out 0
			window.pollFirst();
			// The window is maintained as [i - k + 1, i]
			if (k == 0 || days[i - k] < window.min() && days[i + 1] < window.min()) {
				ans = Math.min(ans, Math.max(days[i - k], days[i + 1]));
				// 找出最早那一天 min
				// max 找出后开的那朵才算数
			}
		}
		
		return ans < days.length ? ans : -1;
	}

    //[1  3  -1] -3  5  3  6  7       3
    // 1 [3  -1  -3] 5  3  6  7       3
	// 1  3 [-1  -3  5] 3  6  7       5
	// 1  3  -1 [-3  5  3] 6  7       5
	// 1  3  -1  -3 [5  3  6] 7       6
	// 1  3  -1  -3  5 [3  6  7]      7

	// (1) queue [-1]
	// (2) queue [-1, -3] -> [-3]
	// (3) queue [-3, 5]
	// (4) queue [-3, 3]

	// basically, it is sliding window minimum - 为了能在constant time拿出min 
	private static class MinQueue<E extends Comparable<E>> extends ArrayDeque<E> {
		private static final long serialVersionUID = 1538439138203379928L;
		private Deque<E> mins; // second queue
		
		public MinQueue() {
			this.mins = new ArrayDeque<>();
		}
		
		@Override
		public void addLast(E x) {
			super.addLast(x);
			while (!this.mins.isEmpty() && x.compareTo(this.mins.peekLast()) < 0) {
				this.mins.pollLast();
			}
			this.mins.addLast(x);
		}
		
		@Override
		public E pollFirst() {
			E x = super.pollFirst();
			if (x == this.mins.peekFirst()) {
				this.mins.pollFirst();
			}
			return x;
		}
		
		public E min() {
			return this.mins.peekFirst();
		}
	}
	
	/**
	 * Sliding Window Maximum
	 Given an array nums, there is a sliding window of size k which is moving from the very left of the array to the very right.
	 You can only see the k numbers in the window. Each time the sliding window moves right by one position.
	 
	 For example,
	 Given nums = [1,3,-1,-3,5,3,6,7], and k = 3.
	 
	 Window position                Max
	 ---------------               -----
	 [1  3  -1] -3  5  3  6  7       3
	 1 [3  -1  -3] 5  3  6  7       3
	 1  3 [-1  -3  5] 3  6  7       5
	 1  3  -1 [-3  5  3] 6  7       5
	 1  3  -1  -3 [5  3  6] 7       6
	 1  3  -1  -3  5 [3  6  7]      7
	 
	 Therefore, return the max sliding window as [3,3,5,5,6,7].
	 You may assume k is always valid, 1 鈮� k 鈮� input array's size
	 */
	public int[] maxSlidingWindow(int[] nums, int k) {
		LinkedList<Integer> l = new LinkedList<>();
		int n = nums.length;
		if (n == 0 || k == 0) {
			return new int[0];
		}
		int[] result = new int[n - k + 1];
		
		for (int i = 0; i < k; i++) { // First fill in result[0] ~ result[k-1]
			addToSlidingWindow(nums[i], l);
		}
		
		for (int i = k; i < n; i++) {
			result[i - k] = l.getFirst(); // correspond to nums[i-k] ~ nums[i-1]
			if (nums[i - k] == l.getFirst()) {
				l.removeFirst();
			}
			addToSlidingWindow(nums[i], l);
		}
		result[n - k] = l.getFirst();
		return result;
	}
	
	private void addToSlidingWindow(int a, LinkedList<Integer> l) {
		while (!l.isEmpty() && l.getLast() < a) {
			l.removeLast();
		}
		l.addLast(a);
	}
	
	class Interval {
		int start;
		int end;
		Interval() { start = 0; end = 0; }
		Interval(int s, int e) { start = s; end = e; }
	}
	
	/**
	 * Meeting Rooms - determine if a person could attend all meetings

	 Given an array of meeting time intervals consisting of start and end times [[s1,e1],[s2,e2],...] (si < ei),
	 determine if a person could attend all meetings.
	 
	 For example,
	 Given [[0, 30],[5, 10],[15, 20]],
	 return false.
	 */
	public boolean canAttendMeetings(Interval[] intervals) {
		if (intervals == null) {
			return false;
		}
		
		// Sort the intervals by start time - ascending order
		Arrays.sort(intervals, (a, b) -> Integer.compare(a.start, b.start)); // 使用a - b会overflow
		
		for (int i = 1; i < intervals.length; i++) {
			if (intervals[i].start < intervals[i - 1].end) { // if two intervals overlap, early termination and return false
				return false;
			}
		}
		
		return true;
	}
	
	/**
	 * Meeting Rooms II - find the minimum number of conference rooms required

	 Given an array of meeting time intervals consisting of start and end times [[s1,e1],[s2,e2],...] (si < ei),
	 find the minimum number of conference rooms required.
	 For example,
	 Given [[0, 30],[5, 10],[15, 20]],
	 return 2.
	 Reference: Non-overlapping Intervals
	*/

	/** 
	 o understand why it works, first let’s define two events:
	 Meeting Starts
	 Meeting Ends
	 
	 Next, we acknowledge three facts:
	 The numbers of the intervals give chronological orders
	 When an ending event occurs, there must be a starting event has happened before that, where “happen before” is defined by the chronological orders given by the intervals
	 Meetings that started which haven’t ended yet have to be put into different meeting rooms, and the number of rooms needed is the number of such meetings
	 
	 So, what this algorithm works as follows:
	 
	 for example, we have meetings that span along time as follows:
	 
	 |_____|
	 |______|
	 |________|
	 |_______|
	 
	 Then, the start time array and end time array after sorting appear like follows:
	 
	 ||    ||
	 |   |   |  |
	 
	 Initially, endsItr points to the first end event, and we move i which is the start event pointer.
	 As we examine the start events, we’ll find the first two start events happen before the end event that endsItr points to,
	 so we need two rooms (we magically created two rooms), as shown by the variable rooms.
	 Then, as i points to the third start event, we’ll find that this event happens after the end event pointed by endsItr, then we increment endsItr so that it points to the next end event.
	 What happens here can be thought of as one of the two previous meetings ended, and we moved the newly started meeting into that vacant room, thus we don’t need to increment rooms at this time and move both of the pointers forward.
	 Next, because endsItr moves to the next end event, we’ll find that the start event pointed by i happens before the end event pointed by endsItr.
	 Thus, now we have 4 meetings started but only one ended, so we need one more room. And it goes on as this.
	 */

	// 
	public int minMeetingRooms(Interval[] intervals) {
		if (intervals == null || intervals.length == 0) {
			return 0;
		}
		int[] starts = new int[intervals.length];
		int[] ends = new int[intervals.length];
		for (int i = 0; i < intervals.length; i++) {
			starts[i] = intervals[i].start;
			ends[i] = intervals[i].end;
		}
		Arrays.sort(starts);
		Arrays.sort(ends);
		int rooms = 0;
		int endIndex = 0;
		for (int startIndex = 0; startIndex < starts.length; startIndex++) {
			if (starts[startIndex] < ends[endIndex]) {
				rooms++;
			} else {
				endIndex++;
			}
		}
		return rooms;
	}
	
	// follow up: print out all meeting rooms with their assigned intervals
	public int minMeetingRooms2(Interval[] intervals) {
		if (intervals == null || intervals.length == 0) {
			return 0;
		}
		Element[] starts = new Element[intervals.length];
		Element[] ends = new Element[intervals.length];
		for (int i = 0; i < intervals.length; i++) {
			starts[i] = new Element(intervals[i].start, i);
			ends[i] = new Element(intervals[i].end, i);
		}
		Arrays.sort(starts);
		Arrays.sort(ends);
		int rooms = 0;
		int endIndex = 0;
		// roomNums[i] is intervals[i]'s room number
		int[] roomNums = new int[intervals.length];
		for (int startIndex = 0; startIndex < starts.length; startIndex++) {
			int currentIndex = starts[startIndex].index;
			if (starts[startIndex].compareTo(ends[endIndex]) < 0) {
				roomNums[currentIndex] = rooms++;
			} else {
				roomNums[currentIndex] = roomNums[ends[endIndex++].index]; 
				// room number of the current original index = room number of the original index of this end index
			}
		}
		Map<Integer, List<Interval>> m = new HashMap<>(rooms);
		for (int i = 0; i < intervals.length; i++) {
			System.out.println(intervals[i] + " room " + roomNums[i]);
			m.computeIfAbsent(roomNums[i], k -> new ArrayList<>()).add(intervals[i]);
		}
		for (int i = 0; i < rooms; i++) {
			System.out.println("Room " + i + " " + m.get(i));
		}
		return rooms;
	}
	
	class Element implements Comparable<Element> {
		int val;
		int index; // wrapper class保留它原有的index，常用套路之一，因为sort会让index丢失
		
		public Element(int v, int i) {
			this.val = v;
			this.index = i;
		}
		
		public int compareTo(Element other) {
			return Integer.compare(this.val, other.val);
		}
		
		public int getIndex() {
			return this.index;
		}
		
		public int getValue() {
			return this.val;
		}
	}
	
	public int minMeetingRooms3(Interval[] intervals) {
		if (intervals == null || intervals.length == 0) {
			return 0;
		}
		
		// Sort the intervals by start time
		Arrays.sort(intervals, new Comparator<Interval>() {
			public int compare(Interval a, Interval b) {
				return Integer.compare(a.start, b.start);
			}
		});
		
		// Use a min heap to track the minimum end time of merged intervals
		PriorityQueue<Interval> heap = new PriorityQueue<>(intervals.length, new Comparator<Interval>() {
			public int compare(Interval a, Interval b) {
				return Integer.compare(a.end, b.end);
			}
		});
		
		// start with the first meeting, put it to a meeting room
		heap.offer(intervals[0]);
		
		for (int i = 1; i < intervals.length; i++) {
			// get the meeting room that finishes earliest
			Interval interval = heap.poll();
			
			if (intervals[i].start >= interval.end) {
				// if the current meeting starts right after
				// there's no need for a new room, merge the interval
				interval.end = intervals[i].end;
			} else {
				// otherwise, this meeting needs a new room
				heap.offer(intervals[i]);
			}
			
			// don't forget to put the meeting room back
			heap.offer(interval);
		}
		
		return heap.size();
	}
	
	
	public static void main (String[] args) {}
}

/* FB onstie 高频 */
/**
 * Given two lists of intervals, find their overlapping intervals
   l1: [1,5], [7,10], [12,18], [22,24]
   l2: [3,8], [13,15], [16,17], [18,21], [22,23]
   returns [3,5],[7,8],[13,15],[16,17],[18,18],[22,23]
 */
public List<Interval> getOverlappingIntervals(List<Interval> l1, List<Interval> l2) {
	if (l1 == null || l2 == null || l1.isEmpty() || l2.isEmpty()) {
		return Collections.emptyList();
	}
	int[] begs = this.merge2Lists(l1, l2, INTERVAL_START_COMPARE);
	int[] ends = this.merge2Lists(l1, l2, INTERVAL_END_COMPARE);
	System.out.println(Arrays.toString(begs));
	System.out.println(Arrays.toString(ends));
	List<Interval> res = new ArrayList<>();
	int count = 0, start = 0;
	for (int i = 0, j = 0; j < ends.length;) {
		if (i < begs.length && begs[i] < ends[j]) {
			if (++count == 2) {
					// enter overlap
				start = begs[i];
			}
			i++;
		} else if (i == begs.length || begs[i] > ends[j]) {
			if (--count == 1) {
					// exit overlap
				res.add(new Interval(start, ends[j]));
			}
			j++;
		} else {
			// begs[i] == ends[j]
			// 要理解一下啊
			// [        ]
			// [    ][     ] 得跟面试官确认下这个情况算不算overlap

			// [        ] if-stmt
			// 	   [       ] if-stmt
			//          [     ] 
			if (count > 1) {
					// already in overlap
				continue;
			}
            //      (ends[j])
            //          |
			// {        } if-stmt
			//          {     }
			//          |
			//      (begs[i])
			res.add(new Interval(begs[i++], ends[j++]));
		}

		// System.out.println(res);
	}
	return res;
}

interface IntervalComparator extends Comparator<Interval> {
	int get(Interval interval);
}

class IntervalStartComparator implements IntervalComparator {
	@Override
	public int compare(Interval o1, Interval o2) {
		return Integer.compare(o1.start, o2.start);
	}

	@Override
	public int get(Interval interval) {
		return interval.start;
	}
}

class IntervalEndComparator implements IntervalComparator {
	@Override
	public int compare(Interval o1, Interval o2) {
		return Integer.compare(o1.end, o2.end);
	}

	@Override
	public int get(Interval interval) {
		return interval.end;
	}
}

private static final IntervalComparator INTERVAL_START_COMPARE = new IntervalStartComparator();
private static final IntervalComparator INTERVAL_END_COMPARE = new IntervalEndComparator();

private int[] merge2Lists(List<Interval> l1, List<Interval> l2, IntervalComparator c) {
	int[] res = new int[l1.size() + l2.size()];
	for (int index = 0, i = 0, j = 0; i < l1.size() || j < l2.size(); index++) {
		if (i == l1.size()) {
			res[index] = c.get(l2.get(j++));
			continue;
		} else if (j == l2.size()) {
			res[index] = c.get(l1.get(i++));
			continue;
		}
		if (c.compare(l1.get(i), l2.get(j)) < 0) {
			res[index] = c.get(l1.get(i++));
		} else {
			res[index] = c.get(l2.get(j++));
		}
	}
	return res;
}


/**
 * Given two lists of intervals, find their overlapping intervals
 // l1: [1,5], [7,10], [12,18], [22,24]
 // l2: [3,8], [13,15], [16,17], [18,21], [22,23]
 // returns [3,5],[7,8],[13,15],[16,17],[18,18],[22,23]
 */
public List<Interval> getOverlappingIntervals(List<Interval> l1, List<Interval> l2) {
	// 套路解法
	if (l1 == null || l2 == null || l1.isEmpty() || l2.isEmpty()) {
		return Collections.emptyList();
	}
	int[] begs = this.merge2Lists(l1, l2, INTERVAL_START_COMPARE);
	int[] ends = this.merge2Lists(l1, l2, INTERVAL_END_COMPARE);
	System.out.println(Arrays.toString(begs));
	System.out.println(Arrays.toString(ends));
	List<Interval> res = new ArrayList<>();
	int count = 0, start = 0;
	for (int i = 0, j = 0; j < ends.length;) {
		if (i < begs.length && begs[i] < ends[j]) {
			if (++count == 2) {
					// enter overlap
				start = begs[i];
			}
			i++;
		} else if (i == begs.length || begs[i] > ends[j]) {
			if (--count == 1) {
					// exit overlap
				res.add(new Interval(start, ends[j]));
			}
			j++;
		} else {
				// begs[i] == ends[j]
			if (count > 1) {
					// already in overlap
				continue;
			}
			res.add(new Interval(begs[i++], ends[j++]));
		}

			// System.out.println(res);
	}
	return res;
}

private static final IntervalComparator INTERVAL_START_COMPARE = new IntervalStartComparator();
private static final IntervalComparator INTERVAL_END_COMPARE = new IntervalEndComparator();

private int[] merge2Lists(List<Interval> l1, List<Interval> l2, IntervalComparator c) {
	int[] res = new int[l1.size() + l2.size()];
	for (int index = 0, i = 0, j = 0; i < l1.size() || j < l2.size(); index++) {
		if (i == l1.size()) {
			res[index] = c.get(l2.get(j++));
			continue;
		} else if (j == l2.size()) {
			res[index] = c.get(l1.get(i++));
			continue;
		}
		if (c.compare(l1.get(i), l2.get(j)) < 0) {
			res[index] = c.get(l1.get(i++));
		} else {
			res[index] = c.get(l2.get(j++));
		}
	}
	return res;
}


// airbnb 经典高频

/**
N个员工，每个员工有若干个interval表示在这段时间是忙碌的。求所有员工都不忙的intervals
每个subarray都已经sort好
举例： [
   [[1, 3], [6, 7]],
   [[2, 4]],
   [[2, 3], [9, 12]].
]
返回 [[4, 6], [7, 9]]
	 * Refer: Merge Intervals
	 */
public List<Interval> getFreeIntervals2(List<List<Interval>> schedule) {
	// 套路解法
	int[] begs = this.mergeKLists(schedule, INTERVAL_START_COMPARE);
	int[] ends = this.mergeKLists(schedule, INTERVAL_END_COMPARE);
	System.out.println(Arrays.toString(begs));
	System.out.println(Arrays.toString(ends));
	List<Interval> res = new ArrayList<>();
	int count = 0, start = 0;
	for (int i = 0, j = 0; i < begs.length;) {
		if (begs[i] < ends[j]) {
			if (++count == 1) {
					// exit free time
				if (start > 0) {
					res.add(new Interval(start, begs[i]));
				}
			}
			i++;
		} else if (begs[i] > ends[j]) {
			if (--count == 0) {
					// start free zone
				start = ends[j];
			}
			j++;
		} else {
				// begs[i] == ends[j]
			i++;
			j++;
		}
		System.out.println(res);
	}
	return res;
}

private int[] mergeKLists(List<List<Interval>> lists, IntervalComparator c) {
	Interval[][] res = new Interval[lists.size()][];
	for (int i = 0; i < res.length; i++) {
		List<Interval> l = lists.get(i);
		res[i] = l.stream().toArray(Interval[]::new);
	}
	int end = lists.size() - 1;
	while (end > 0) {
		int beg = 0;
		while (beg < end) {
			res[beg] = merge2Lists(res[beg], res[end], c);
			beg++;
			end--;
		}
	}
	return Arrays.stream(res[0]).mapToInt(interval -> c.get(interval)).toArray();
}

private Interval[] merge2Lists(Interval[] l1, Interval[] l2, IntervalComparator c) {
	Interval[] res = new Interval[l1.length + l2.length];
	for (int index = 0, i = 0, j = 0; i < l1.length || j < l2.length; index++) {
		if (i == l1.length) {
			res[index] = l2[j++];
			continue;
		} else if (j == l2.length) {
			res[index] = l1[i++];
			continue;
		}
		if (c.compare(l1[i], l2[j]) < 0) {
			res[index] = l1[i++];
		} else {
			res[index] = l2[j++];
		}
	}
	return res;
}


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

  /**
   * Isomorphic Strings
   * 
   * Given two strings s and t, determine if they are isomorphic.

	Two strings are isomorphic if the characters in s can be replaced to get t.

	All occurrences of a character must be replaced with another character while preserving the order of characters. No two characters may map to the same character but a character may map to itself.

	For example,
	Given "egg", "add", return true.

	Given "foo", "bar", return false.

	Given "paper", "title", return true.

	Note:
	You may assume both s and t have the same length.
   */
  public boolean isIsomorphic(String s, String t) {
    if (s == null) {
      return t == null;
    }

    if (s.length() != t.length()) {
      return false;
    }

    // O(2n) space
    // key is the char, value is the "last appeared" index of the key of the char
    Map<Character, Integer> sMapping = new HashMap<>();
    Map<Character, Integer> tMapping = new HashMap<>();
    for (int i = 0; i < s.length(); i++) {
      char sc = s.charAt(i);
      char tc = t.charAt(i);
      // so first time =>
      // Map.put(k, v) 
      // return null if key k not exists in map
      // return existing value if key k exists in map

      // s: egg, t: add
      // sMapping: <e:0, g:2>
      // tMapping: <a:0, d:2>
      // sc: g
      // tc: d
      // i: 2
      // 1 == 1 (previous/existing value)
      if (!Objects.equals(sMapping.put(sc, i), tMapping.put(tc, i))) {
        return false;
      }
    }

    return true;
  }


  // follow up
  private String[] strings;

  // solution 1
  // key is the string length
  Map<Integer, List<String>> map = ...

  public boolean isIsomorphic(String[] strings, String t) { 
  	// preprocess strings to map (key: string length, value: a list of string with key length)

    List<String> l = map.get(t.length());
    if (l == null) {
      return false;
    }
    for (String s : l) {
      if (isIsomorphic(s, t)) {
      	return true; // find if exist - so we can early return true
      }
    }
    return false;
  }

  
  // solution 2 - better
  // transitive
  // a is similar to b, and b is similar to c
  // so a and b and c are similar
  // union find
  // preprocess - remove unnecessary words
  // ?

   /**
    * Kth Smallest Number in Multiplication Table 
 	Nearly every one have used the Multiplication Table. But could you find out the k-th smallest number quickly from the multiplication table?

	Given the height m and the length n of a m * n Multiplication Table, and a positive integer k, you need to return the k-th smallest number in this table.

	Example 1:

	Input: m = 3, n = 3, k = 5
	Output: 
	Explanation: 
	The Multiplication Table:
	1 2 3
	2 4 6
	3 6 9

	The 5-th smallest number is 3 (1, 2, 2, 3, 3).

	Example 2:

	Input: m = 2, n = 3, k = 6
	Output: 
	Explanation: 
	The Multiplication Table:
	1 2 3
	2 4 6

	The 6-th smallest number is 6 (1, 2, 2, 3, 4, 6).
    */
  public int findKthNumber(int m, int n, int k) {
    int low = 1, high = m * n + 1;

    while (low < high) {
      int mid = low + (high - low) / 2;
      int c = count(mid, m, n);
      if (c >= k) {
        high = mid;
      } else {
        low = mid + 1;
      }
    }

    return high;
  }

  private int count(int v, int m, int n) {
    int count = 0;
    for (int i = 1; i <= m; i++) {
      count += Math.min(v / i, n);
    }
    return count;
  }

  /**
   * Longest Substring with At Most K Distinct Characters
   * Given a string, find the length of the longest substring T that contains at most k distinct characters.
   * For example, Given s = “eceba” and k = 2,
   * T is "ece" which its length is 3.
   */
  // test cases: ASCII, I18N
  // For test cases, you can mention I can probably test Chinese charaters 
  // (to demonstrate you have sense of internalization)
  // int[256] - not generic

  public int lengthOfLongestSubstringKDistinct(String s, int k) {
    int[] count = new int[256];
    int num = 0, i = 0, res = 0;
    for (int j = 0; j < s.length(); j++) {
    	if (count[s.charAt(j)]++ == 0) {
        	num++;
      	}
      	if (num > k) {
        	while (--count[s.charAt(i++)] > 0) {
          	;
        	}
        num--;
    	}
      	// num == k
      	res = Math.max(res, j - i + 1); // dont put it into the above if-stmt, 
      								  // input string s may not contain k distinct characters...
    }
    return res;
  }
  
  // Use map - more generic
  // space counts: worst case: k = 10, s = "abcdefgh", k > n, n is length of s, O(n)

  // follow up: input string s is a very big stream of characters
  // stream.next() -> a char, so you can only have right pointer

  public int lengthOfLongestSubstringKDistinct2(String s, int k) {
    Map<Character, Integer> counts = new HashMap<>();
    int total = 0, left = 0, res = 0, count;
    char c;
    // best case: left == 0, right moved to n - 1, O(n) that assume the following
    // do-while loop takes constant time
    // worst case: left == (close) n - 1, right to n - 1, O(2n)
    // do not simply say runtime is O(n); should reason about it by analysis
    for (int right = 0; right < s.length(); right++) {
        c = s.charAt(right); // stream.next(); you only have right pointer
      	count = counts.getOrDefault(c, 0) + 1;
      	counts.put(c, count);
      	if (count == 1) {
        	total++;
      	}
      	if (total > k) {
        	do {
          		c = s.charAt(left++); // but you cannot have this character
                                	  // since string is so big and you can't save such string in memory
          		// what should we do?
          		// we only record rightmost index of each character in the sliding window
          		// find the minimum of indices of those characters in the sliding window -> minIndex
          		// res = Math.max(res, right - (minIndex + 1) + 1)
          		count = counts.get(c) - 1;
          		counts.put(c, count);
        	} while (count > 0); // count 1 -> 0, remove one distinct character, total == k
        total--;
    	}
    	res = Math.max(res, right - left + 1);
    }

    return res;
  }
 
  public int lengthOfLongestSubstringKDistinct3(String s, int k) {
    if (s.isEmpty()) {
      return 0;
    }
    Map<Character, Integer> indexes = new HashMap<>();
    int left = 0, right = 0, res = 0;
    char c;
    while (right < s.length()) {
      c = s.charAt(right);
      indexes.put(c, right++); // keep right most index of each character
      if (indexes.size() > k) {
        int leftMost = s.length();
        // how to optimize this?
        // use priority queue - min heap - but not best solution
        // use LRU - FIFO, head, tail, LinkedHashMap 
        // - the least frequently used item is target
        
        //  L           R
        // \|/         \|/
        //  A B A A C C D   (left most) 
        //  D -> C -> A -> B 
        //  H              T
        for (int i : indexes.values()) {
          leftMost = Math.min(leftMost, i);
        }
        c = s.charAt(leftMost);
        indexes.remove(c);
        left = leftMost + 1;
      }
      // indexes.size() <= k (at most k characters)
      res = Math.max(res, right - left);
    }
    return res;
  }

  /**
   * Longest Substring with At Most Two Distinct Characters
   * Given a string, find the length of the longest substring T that contains at most 2 distinct characters.
   * For example, Given s = “eceba”,
   * T is "ece" which its length is 3. 
   */
  public int lengthOfLongestSubstringTwoDistinct(String s) {
    int i = 0, j = -1, maxLen = 0;

    // "ece"
    // k:      1, 2
    // i (starting point): 0, 0
    // j (right most point) : 0, 1
    // maxLen: 0, 0, 3

    for (int k = 1; k < s.length(); k++) {
      if (s.charAt(k) == s.charAt(k - 1)) {
        continue;
      }
      // found 2 distinct characters

      if (j >= 0 && s.charAt(j) != s.charAt(k)) { // found 3 distinct characters
        maxLen = Math.max(k - i, maxLen); // s.charAt(k) is not included in the substring 
        i = j + 1;
      }
      j = k - 1;
    }
    return Math.max(s.length() - i, maxLen);
  }


  /**
   * The main idea is to maintain a sliding window with 2 unique characters.
   * The key is to store the last occurrence of each character as the value in the hashmap.
   * This way, whenever the size of the hashmap exceeds 2, 
   * we can traverse through the map to find the character with the left most index,
   * and remove 1 character from our map. Since the range of characters is constrained, 
   * we should be able to find the left most index in constant time.
   */
  public int lengthOfLongestSubstringTwoDistinct2(String s) {
    if (s.isEmpty()) {
      return 0;
    }
    Map<Character, Integer> indexes = new HashMap<>();
    int left = 0, right = 0, res = 0;
    char c;
    while (right < s.length()) {
      c = s.charAt(right);
      indexes.put(c, right++);
      while (indexes.size() > 2) { // similar to "K Distinct Characters"
        int leftMost = s.length();
        for (int i : indexes.values()) {
          leftMost = Math.min(leftMost, i);
        }
        c = s.charAt(leftMost);
        indexes.remove(c);
        left = leftMost + 1;
      }
      res = Math.max(res, right - left);
    }
    return res;
  }


  /**
   * Expression Add Operators
	 Given a string that contains only digits 0-9 and a target value,
	 return all possibilities to add binary operators (not unary) +, -, or * between the digits so they evaluate to the target value.

	 Examples:

	 "123", 6 -> ["1+2+3", "1*2*3"] 
	 "232", 8 -> ["2*3+2", "2+3*2"]
	 "105", 5 -> ["1*0+5","10-5"]
	 "00", 0 -> ["0+0", "0-0", "0*0"]
	 "3456237490", 9191 -> []
   */

    public List<String> addOperators(String num, int target) {
        return dfs(new ArrayList<>(), num, new StringBuilder(), 0, 0, 0, target);
    }

    private List<String> dfs(List<String> result, String num, StringBuilder path, 
    						 int start, long sum, long lastNum, int target) {
        int len = num.length();
        if (start == len && sum == target) {
            result.add(path.toString());
            return result;
        }
        long curNum = 0;
        for (int i = start; i < len; i++) {
        	// 011 => "011", "0"
        	//  si
        	// 1111
        	//    i
        	// curNum = 1111
            if (i > start && num.charAt(start) == '0') {
                break;
            }
            curNum = curNum * 10 + num.charAt(i) - '0';
            int currentLen = path.length();
            if (start == 0) {
                dfs(result, num, path.append(curNum), i + 1, curNum, curNum, target);
                path.setLength(currentLen); // restore
            } else {
                dfs(result, num, path.append('+').append(curNum), i + 1, sum + curNum, curNum, target);
                path.setLength(currentLen);
                dfs(result, num, path.append('-').append(curNum), i + 1, sum - curNum, -curNum, target);
                path.setLength(currentLen);
                // 1 - 2 * 3
                // sum = -1 + 2 = 1 + (-2 * 3)
                // lastNum = -2
                dfs(result, num, path.append('*').append(curNum), i + 1, sum - lastNum + lastNum * curNum,
                        lastNum * curNum, target);
                path.setLength(currentLen); // restore
            }
        }
        return result;
    }

    /* 
    mock interview: FB problem - Cracking Password

	xcode => Xcode, xc0de, xcod3, xc0d3, Xc0d3

	x => X
	o => O, 0
	e => E, 3, 9

	"xcode space" => "xc0d3 Spac3"
	*/

	private static final Map<Character, Set<Character>> map; // load it from configuration, or database

	public List<String> password(String s){
	    //check input
	    List<String> rst = new ArrayList<>();
	    if (s == null || s.length() == 0) {
	        return rst;
	    }
	    //dfs
	    char[] tmp = new char[s.length()];
	    dfs(s, tmp, 0, rst);
	    return rst;
	}

	private void dfs(String s, char[] tmp, int index, List<String> rst){
	    if (index == s.length()){
	        rst.add(new String(tmp));
	        return;
	    }
	    
	    char c = s.charAt(index);
	    tmp[index] = c;
	    dfs(s, tmp, index + 1, rst); // original letter
	    if (map.contains)
	    	// mapped letter
		    for(char ch : map.get(c)){
		        tmp[index] = ch;// overriding <=> restore
		        dfs(s, tmp, index + 1, rst); 
		        tmp[index] = c; // restore
		    }
	    
	}

	// 1,1,1,1,1,1,1,1,1
   /**
	*  Longest Consecutive Sequence 
	Given an unsorted array of integers, find the length of the longest consecutive elements sequence.

	For example,
	Given [100, 4, 200, 1, 3, 2],
	The longest consecutive elements sequence is [1, 2, 3, 4]. Return its length: 4.

	Your algorithm should run in O(n) complexity. 
    */
    public int longestConsecutive(int[] nums) {
        int res = 0;
        // key is the number, value is the length of the sequence the number is
        // in
        Map<Integer, Integer> map = new HashMap<>();
        for (int n : nums) {
            if (map.containsKey(n)) {
                // duplicates
                continue;
            }
            int left = map.getOrDefault(n - 1, 0);
            int right = map.getOrDefault(n + 1, 0);
            // len: length of the sequence n is in
            int len = left + right + 1;
            map.put(n, len);

            // keep track of the max length
            res = Math.max(res, len);

            // extend the length to the boundary(s)
            // of the sequence
            // will do nothing if n has no neighbors
            map.put(n - left, len);
            map.put(n + right, len);
        }
        return res;
    }

	/**
     * Remove Duplicate Letters
 	Given a string which contains only lowercase letters, remove duplicate letters so that every letter appear once and only once.
	You must make sure your result is the smallest in lexicographical order among all possible results.
	Example:

	Given "bcabc"
	Return "abc"

	Given "cbacdcbc"
	Return "acdb" 
    */
    public String removeDuplicateLetters(String s) {
        if (s == null || s.isEmpty()) {
            return s;
        }
        int[] count = new int[26];
        for (int i = 0; i < s.length(); i++) {
            count[s.charAt(i) - 'a']++; // frequency count
        }
        boolean[] in = new boolean[26]; 
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            int pos = c - 'a';
            count[pos]--;
            if (sb.length() == 0) {
                sb.append(c);
                in[pos] = true;
                continue;
            }

            if (in[pos]) { // duplicate
                continue;
            }

            int lastIndex = sb.length() - 1;
            while (sb.length() > 0 && c < sb.charAt(lastIndex) && count[sb.charAt(lastIndex) - 'a'] > 0) { 
            	// "c < sb.charAt(lastIndex)" -> smallest lexicographical order
            	// "count[sb.charAt(lastIndex) - 'a'] > 0" -> we still have this character 

            	// remove the character at the last index
                in[sb.charAt(lastIndex) - 'a'] = false;
                sb.deleteCharAt(lastIndex);
                lastIndex--;
            }

            sb.append(c);
            in[pos] = true;
        }

        return sb.toString();
    }
 
	/**
     * Remove K Digits
	 Given a non-negative integer num represented as a string, remove k digits from the number so that the new number is the smallest possible.

	 Note:

     The length of num is less than 10002 and will be ≥ k.
     The given num does not contain any leading zero.

	 Example 1:

	 Input: num = "1432219", k = 3
	 Output: "1219"
	 Explanation: Remove the three digits 4, 3, and 2 to form the new number 1219 which is the smallest.

	 Example 2:

	 Input: num = "10200", k = 1
	 Output: "200"
	 Explanation: Remove the leading 1 and the number is 200. Note that the output must not contain leading zeroes.

	 Example 3:

	 Input: num = "10", k = 2
	 Output: "0"
	 Explanation: Remove all the digits from the number and it is left with nothing which is 0.
	 Similar to Remove Duplicate Letters
     */
    public String removeKdigits(String num, int k) {
        if (k >= num.length()) {
            // corner case
            return "0";
        }

        StringBuilder result = new StringBuilder();
        for (int i = 0; i < num.length(); i++) {
            char c = num.charAt(i);
            // whenever meet a digit which is less than the previous digit,
            // discard the previous one
            // result.length() - 1 is last char's index
            // result.charAt(result.length() - 1) is result's last char
            // c < last char
            // c would make it smaller
            // result.length() - 1 if result.length() = 0, => -1
            // 12345 + 1 => 11
            // second digit: 1 < 2
            while (k > 0 && result.length() > 0 && result.charAt(result.length() - 1) > c) { // more significant bit, more matters
                result.deleteCharAt(result.length() - 1);
                k--;
            }
            result.append(c);
        }

        while (k > 0) {
            // corner case like "1111" 
            // k = 2, "11"
            result.deleteCharAt(result.length() - 1);
            k--;
        }

        // remove all the 0 at the head
        while (result.length() > 1 && result.charAt(0) == '0') {
            result.deleteCharAt(0);
        }
        return result.toString();
    }

 	/**
     * Letter Combinations of a Phone Number 
	 Given a digit string, return all possible letter combinations that the number could represent.

	 A mapping of digit to letters (just like on the telephone buttons) is given below.

	 Input:Digit string "23"
	 Output: ["ad", "ae", "af", "bd", "be", "bf", "cd", "ce", "cf"].

	 Note:
	 Although the above answer is in lexicographical order, your answer could be in any order you want. 
     */
    // digits: 0, 1, 2, 3, 4, 5, 6, 7, 8, 9
    private static final char[][] table = { { ' ' }, {}, { 'a', 'b', 'c' }, { 'd', 'e', 'f' },
            { 'g', 'h', 'i' }, { 'j', 'k', 'l' }, { 'm', 'n', 'o' },
            { 'p', 'q', 'r', 's' }, { 't', 'u', 'v' },
            { 'w', 'x', 'y', 'z' } };

    public List<String> letterCombinations(String digits) {
        // DFS
        List<String> ret = new ArrayList<>();
        if (digits == null || digits.isEmpty()) {
            return ret;
        }
        letterCombinations(digits, 0, new char[digits.length()], ret);
        return ret;
    }
    
    // one letter maps to k letters including iteself
    // n is the length of digits
    
    1 2 3  4 ... n - 1, n
    k*k*k *k ...   k * k = k^n
    
    private void letterCombinations(String digits, int index, char[] ans, List<String> ret) {
        if (index == digits.length()) {
            ret.add(new String(ans));
            return;
        }

        int letterIndex = digits.charAt(index) - '0';
        for (int i = 0; i < table[letterIndex].length; i++) {
            ans[index] = table[letterIndex][i];
            letterCombinations(digits, index + 1, ans, ret);
        }
    }

    private static final String[] letterMapping = { "0", "1", "abc", "def", "ghi", "jkl", "mno", "pqrs", "tuv", "wxyz" };
    public List<String> letterCombinations2(String digits) {
        // BFS
        LinkedList<String> ans = new LinkedList<>();
        if (digits == null || digits.isEmpty()) {
            return ans;
        }
        ans.add("");
        for (int i = 0; i < digits.length(); i++) {
            int x = Character.getNumericValue(digits.charAt(i));

            // every loop: append a character
            while (ans.peek().length() == i) { // Retrieves, but does not remove, the head (first element) of this list.
                String s = ans.remove(); // Retrieves and removes the head (first element) of this list.
                for (char c : letterMapping[x].toCharArray()) {
                    ans.add(s + c);
                }
            }
        }
        return ans;
    }

    public List<String> letterCombinations3(String digits) {
        // BFS
        LinkedList<String> cur = new LinkedList<>(), next = new LinkedList<>();
        if (digits == null || digits.isEmpty()) {
            return cur;
        }
        cur.add("");
        for (int i = 0; i < digits.length(); i++) {
            int x = digits.charAt(i) - '0';
            while (!cur.isEmpty()) {
                String s = cur.remove();
                for (char c : letterMapping[x].toCharArray()) {
                    next.add(s + c);// intermediate strings for garbage collection -> StringBuilder
                    // clone the original string O(n)
                    // and the solution 1 is DFS - you might have stack overflow
                    // in interview there is no best solution, they all have advantages & disadvantages, tradeoff
                }
            }
            cur = next;
            next = new LinkedList<>();
        }
        return cur;
    }

import java.util.*;

public class AddBoldTagInString {
    /**
     * Add Bold Tag in String
     Given a string s and a list of strings dict, you need to add a closed pair of bold tag <b> and </b> to wrap the substrings in s that exist in dict. If two such substrings overlap, you need to wrap them together by only one pair of closed bold tag. Also, if two substrings wrapped by bold tags are consecutive, you need to combine them.

     Example 1:
     Input:
     s = "abcxyz123"
     dict = ["abc","123"]
     Output:
     "<b>abc</b>xyz<b>123</b>"

     Example 2:
     Input:
     s = "aaabbcc"
     dict = ["aaa","aab","bc"]
     Output:
     "<b>aaabbc</b>c" overlap or adjacent combine 

     Note:
     The given dict won't contain duplicates, and its length won't exceed 100.
     All the strings in input have length in range [1, 1000].
     */
    public String addBoldTag(String s, String[] dict) {
        boolean[] bold = new boolean[s.length()];
        // indexOf(string) -> -1, if not exist -> strstr, KMP algorithm?
        for (String d : dict) {
            for (int i = 0; i <= s.length() - d.length(); i++) {
                if (s.substring(i, i + d.length()).equals(d)) {
                    for (int j = i; j < i + d.length(); j++)
                        // for follow up, just demon your idea ~
                        // FFFFTTFTTFFFF
                        //   |        |
                        //   TT     TTT
                        // input is very large => using boolean array is a waste of space 
                        // => use interval [beg, end]
                        bold[j] = true;
                }
            }
        }
        StringBuilder res = new StringBuilder();
        for (int i = 0; i < s.length();) {
            if (bold[i]) {
                res.append("<b>");
                while (i < s.length() && bold[i]) {
                    res.append(s.charAt(i++));
                }
                res.append("</b>");
            } else {
                res.append(s.charAt(i++));
            }
        }
        return res.toString();
    }

    public String addBoldTag2(String s, String[] dict) {
        List<int[]> list = new ArrayList<>();
        Set<String> set = new HashSet<>(Arrays.asList(dict));
        for (int i = 0; i < s.length(); i++) {
            for (int j = i; j < s.length(); j++) {
                if (set.contains(s.substring(i, j + 1))) {
                    list.add(new int[] { i, j });
                }
            }
        }
        if (list.isEmpty()) {
            return s;
        }
        Collections.sort(list, (a, b) -> a[0] == b[0] ? a[1] - b[1] : a[0] - b[0]);
        int start, prev = 0, end = 0;
        StringBuilder res = new StringBuilder();
        for (int i = 0; i < list.size(); i++) {
            res.append(s.substring(prev, list.get(i)[0]));
            start = i;
            end = list.get(i)[1];
            while (i < list.size() - 1 && list.get(i + 1)[0] <= end + 1) {
                end = Math.max(end, list.get(i + 1)[1]);
                i++;
            }
            res.append("<b>" + s.substring(list.get(start)[0], end + 1) + "</b>");
            prev = end + 1;
        }
        res.append(s.substring(end + 1, s.length()));
        return res.toString();
    }
}
 
public class GraphValidTree {
    /**
     * Graph Valid Tree
     Given n nodes labeled from 0 to n - 1 and a list of undirected edges (each edge is a pair of nodes),
     write a function to check whether these edges make up a valid tree.
     For example:
     Given n = 5 and edges = [[0, 1], [0, 2], [0, 3], [1, 4]], return true.
     Given n = 5 and edges = [[0, 1], [1, 2], [2, 3], [1, 3], [1, 4]], return false.

     Hint:
     Given n = 5 and edges = [[0, 1], [1, 2], [3, 4]], what should your return? Is this case a valid tree?
     According to the definition of tree on Wikipedia: “a tree is an undirected graph in which any two vertices are connected by exactly one path.
     In other words, any connected graph without simple cycles is a tree.”
     
     Note: you can assume that no duplicate edges will appear in edges.
     Since all edges are undirected, [0, 1] is the same as [1, 0] and thus will not appear together in edges.

     AC Java Union-Find solution
     */
    public boolean validTree(int n, int[][] edges) {
        if (edges.length != n - 1) {
            return false;
        }
        UnionFind set = new UnionFind(n);
        for (int i = 0; i < n; i++) {
            set.id[i] = i;
            set.size[i] = 1;
        }
        for (int[] edge : edges) {
            if (set.find(edge[0], edge[1])) {
                return false; // cycle check
            }
            set.union(edge[0], edge[1]);
        }
        return set.count == 1; // single tree check
    }

    class UnionFind {
        int[] id, size;
        int count;

        public UnionFind(int len) {
            this.id = new int[len];
            this.size = new int[len];
            this.count = len;
        }

        public boolean find(int p, int q) {
            return root(p) == root(q);
        }

        public void union(int p, int q) {
            int pi = root(p), qi = root(q);
            if (this.size[pi] < this.size[qi]) {
                this.id[pi] = qi;
                this.size[qi] += this.size[pi];
            } else {
                this.id[qi] = pi;
                this.size[pi] += this.size[qi];
            }
            this.count--;
        }

        public int root(int i) {
            while (i != id[i]) {
                id[i] = id[id[i]]; // path compression
                i = id[i];
            }
            return i;
        }
    }
}


    package amazon;

import java.util.*;

/**
 * Minimum Spanning Tree
 * connection cost node1 node2: {¡°Acity¡±,¡±Bcity¡±,1}
 * (¡°Acity¡±,¡±Ccity¡±,2} (¡°Bcity¡±,¡±Ccity¡±,3} Êä³ö£º (¡°Acity¡±,¡±Bcity¡±,1}
 * (¡°Acity¡±,¡±Ccity¡±,2} ²¹³äÒ»¾ä£¬test caseÒ»¹²ÓÐ6¸ö¡£
 */
class Connection {
	String node1;
	String node2;
	int cost;

	public Connection(String a, String b, int c) {
		node1 = a;
		node2 = b;
		cost = c;
	}
}

public class MST {
	static class DisjointSet {
		Set<String> set;
		Map<String, String> map;
		int count;

		public DisjointSet() {
			this.count = 0;
			this.set = new HashSet<>();
			this.map = new HashMap<>();
		}

		public void makeSet(String s) {
			if (this.set.contains(s)) { // remove duplicate
				return;
			}
			this.count++;
			this.set.add(s);
			this.map.put(s, s);
		}

		public String root(String s) {
			if (!this.set.contains(s)) {
				return null;
			}
			String v = this.map.get(s);
			if (s.equals(v)) {
				return s;
			}
			String root = this.root(v);
			this.map.put(s, root);
			return root;
		}

		public void union(String s, String t) {
			if (!this.set.contains(s) || !this.set.contains(t)) {
				return;
			}
			if (s.equals(t)) {
				return;
			}
			this.count--;
			this.map.put(s, t);
		}
	}

	public static List<Connection> getMST(List<Connection> connections) {
		Collections.sort(connections, (a, b) -> Integer.compare(a.cost, b.cost));
		DisjointSet set = new DisjointSet();
		List<Connection> res = new ArrayList<>();
		for (Connection connection : connections) {
			set.makeSet(connection.node1);
			set.makeSet(connection.node2);
		}
		for (Connection connection : connections) {
			String s = set.root(connection.node1);
			String t = set.root(connection.node2);
			if (!s.equals(t)) {
				set.union(s, t);
				res.add(connection);
				if (set.count == 1) { // which means all nodes get connected together
					break;
				}
			}
		}
		if (set.count == 1) {
			Collections.sort(res, (a, b) -> {
				if (a.node1.equals(b.node1)) {
					return a.node2.compareTo(b.node2); // ascending order (a, b)
				}
				return a.node1.compareTo(b.node1); // ascending order (a, b)
			});
			return res;
		}

		return Collections.emptyList();
	}

	public static void main(String[] args) {
		ArrayList<Connection> connections = new ArrayList<>();
		// connections.add(new Connection("Acity","Bcity",1));
		// connections.add(new Connection("Acity","Ccity",2));
		// connections.add(new Connection("Bcity","Ccity",3));
		connections.add(new Connection("A", "B", 6));
		connections.add(new Connection("B", "C", 4));
		connections.add(new Connection("C", "D", 5));
		connections.add(new Connection("D", "E", 8));
		connections.add(new Connection("E", "F", 1));
		connections.add(new Connection("B", "F", 10));
		connections.add(new Connection("E", "C", 9));
		connections.add(new Connection("F", "C", 7));
		connections.add(new Connection("B", "E", 3));
		connections.add(new Connection("A", "F", 1));

		List<Connection> res = getMST(connections);
		for (Connection c : res) {
			System.out.println(c.node1 + " -> " + c.node2 + " cost : " + c.cost);
		}
	}
}

import java.util.*;

/**
 * My Calendar I
Implement a MyCalendar class to store your events. A new event can be added if adding the event will not cause a double booking.

Your class will have the method, book(int start, int end). Formally, this represents a booking on the half open interval [start, end), the range of real numbers x such that start <= x < end.

A double booking happens when two events have some non-empty intersection (ie., there is some time that is common to both events.)

For each call to the method MyCalendar.book, return true if the event can be added to the calendar successfully without causing a double booking. Otherwise, return false and do not add the event to the calendar.

Your class will be called like this: MyCalendar cal = new MyCalendar(); MyCalendar.book(start, end)
Example 1:
MyCalendar();
MyCalendar.book(10, 20); // returns true
MyCalendar.book(15, 25); // returns false
MyCalendar.book(20, 30); // returns true
Explanation: 
The first event can be booked.  The second can't because time 15 is already booked by another event.
The third event can be booked, as the first event takes every time less than 20, but not including 20.
Note:

The number of calls to MyCalendar.book per test case will be at most 1000.
In calls to MyCalendar.book(start, end), start and end are integers in the range [0, 10^9].
 */
public class MyCalendar {
	private TreeMap<Integer, Integer> calendar;

	public MyCalendar() {
		this.calendar = new TreeMap<>();
	}

	public boolean book(int start, int end) {
		// .floorKey(key) - returns the greatest key less than or equal to the given key, or null if there is no such key.
		// .ceilingKey(key) - Returns the least key greater than or equal to the given key, or null if there is no such key.
		Integer prev = calendar.floorKey(start), next = calendar.ceilingKey(start);
		if ((prev == null || calendar.get(prev) <= start) && 
			(next == null || end <= next)) {
			calendar.put(start, end);
			return true;
		}
		return false;
	}
}

import java.util.*;

/**
 * My Calendar II - not cause a triple booking
Implement a MyCalendarTwo class to store your events. A new event can be added if adding the event will not cause a triple booking.

Your class will have one method, book(int start, int end). Formally, this represents a booking on the half open interval [start, end), the range of real numbers x such that start <= x < end.

A triple booking happens when three events have some non-empty intersection (ie., there is some time that is common to all 3 events.)

For each call to the method MyCalendar.book, return true if the event can be added to the calendar successfully without causing a triple booking. Otherwise, return false and do not add the event to the calendar.

Your class will be called like this: MyCalendar cal = new MyCalendar(); MyCalendar.book(start, end)

Example 1:
MyCalendar();
MyCalendar.book(10, 20); // returns true
MyCalendar.book(50, 60); // returns true
MyCalendar.book(10, 40); // returns true
MyCalendar.book(5, 15);  // returns false
MyCalendar.book(5, 10);  // returns true
MyCalendar.book(25, 55); // returns true

Explanation: 
The first two events can be booked.  The third event can be double booked.
The fourth event (5, 15) can't be booked, because it would result in a triple booking.
The fifth event (5, 10) can be booked, as it does not use time 10 which is already double booked.
The sixth event (25, 55) can be booked, as the time in [25, 40) will be double booked with the third event;
the time [40, 50) will be single booked, and the time [50, 55) will be double booked with the second event.
Note:

The number of calls to MyCalendar.book per test case will be at most 1000.
In calls to MyCalendar.book(start, end), start and end are integers in the range [0, 10^9].
*/
public class MyCalendarTwo {
	private TreeMap<Integer, Integer> delta;
	private int maxStart;

	public MyCalendarTwo() {
		this.delta = new TreeMap<>();
	}

	// MyCalendar.book(10, 20); // returns true
	// MyCalendar.book(50, 60); // returns true
	// MyCalendar.book(10, 40); // returns true
	// MyCalendar.book(5, 15);  // returns false
	// MyCalendar.book(5, 10);  // returns true
	// MyCalendar.book(25, 55); // returns true
	public boolean book(int start, int end) {
		this.delta.put(start, this.delta.getOrDefault(start, 0) + 1);
		this.delta.put(end, this.delta.getOrDefault(end, 0) - 1);
		this.maxStart = Math.max(this.maxStart, start);

		int active = 0;
		for (Map.Entry<Integer, Integer> entry : this.delta.entrySet()) {
			int k = entry.getKey();
			int d = entry.getValue();

			active += d;
			if (active >= 3) { // active booking more than 2
				// revert
				this.delta.put(start, delta.get(start) - 1);
				this.delta.put(end, delta.get(end) + 1);
				if (this.delta.get(start) == 0) {
					delta.remove(start); // this line can be commented out

					// Do we have to remove(end)? No
					// there may be multiple ends
					// this.delta.get(start) may be larger than 0
				}
				return false;
			}

			// early terminate on the last start
			// don't have to deal with the rest of end parts
			if (d > 0 && k == this.maxStart) { 
				break;
			}
		}

		return true;
	}
}

import java.util.*;

/**
 * My Calendar III
Implement a MyCalendarThree class to store your events. A new event can always be added.

Your class will have one method, book(int start, int end). Formally, this represents a booking on the half open interval [start, end), the range of real numbers x such that start <= x < end.

A K-booking happens when K events have some non-empty intersection (ie., there is some time that is common to all K events.)

For each call to the method MyCalendar.book, return an integer K representing the largest integer such that there exists a K-booking in the calendar.

Your class will be called like this: MyCalendarThree cal = new MyCalendarThree(); MyCalendarThree.book(start, end)
Example 1:
MyCalendarThree();
MyCalendarThree.book(10, 20); // returns 1
MyCalendarThree.book(50, 60); // returns 1
MyCalendarThree.book(10, 40); // returns 2
MyCalendarThree.book(5, 15); // returns 3
MyCalendarThree.book(5, 10); // returns 3
MyCalendarThree.book(25, 55); // returns 3
Explanation: 
The first two events can be booked and are disjoint, so the maximum K-booking is a 1-booking.
The third event [10, 40) intersects the first event, and the maximum K-booking is a 2-booking.
The remaining events cause the maximum K-booking to be only a 3-booking.
Note that the last event locally causes a 2-booking, but the answer is still 3 because
eg. [10, 20), [10, 40), and [5, 15) are still triple booked.
Note:

The number of calls to MyCalendarThree.book per test case will be at most 400.
In calls to MyCalendarThree.book(start, end), start and end are integers in the range [0, 10^9]
 */
public class MyCalendarThree {
	private TreeMap<Integer, Integer> delta;
	private int maxStart;

	public MyCalendarThree() {
		this.delta = new TreeMap<>();
	}

	public int book(int start, int end) {
		this.delta.put(start, delta.getOrDefault(start, 0) + 1);
		this.delta.put(end, delta.getOrDefault(end, 0) - 1);
		this.maxStart = Math.max(this.maxStart, start);

		int active = 0, ans = 0;
		for (Map.Entry<Integer, Integer> entry : this.delta.entrySet()) {
			int k = entry.getKey();
			int d = entry.getValue();

			active += d;
			ans = Math.max(ans, active);

			// early terminate
			if (d > 0 && k == this.maxStart) {
				break;
			}
		}

		return ans;
	}

	public static void main(String[] args) {
		MyCalendarThree cal = new MyCalendarThree();
		cal.book(10, 20);
		cal.book(50, 60);
		cal.book(10, 40);
	}
}

public class ReadNCharactersGivenRead4 {
    /*
     * Read N Characters Given Read4
     *
     * Question: The API: int read4(char *buf) reads 4 characters at a time from a
     * file. The return value is the actual number of characters read. For example,
     * it returns 3 if there is only 3 characters left in the file. By using the
     * read4 API, implement the function int read(char *buf, int n) that reads n
     * characters from the file. Note: The read function will only be called
     * multiplmultiple for each test case.
     *
     * The read4 API is defined in the parent class Reader4.
     */
    private int read4(char[] buf) {
        return 0;
    }

    private String read4String() {
        return null;
    }

    private char[] buffer = new char[4];
    private int offset = 0, bufsize = 0;

    /**
     * @param buf Destination buffer
     * @param n Maximum number of characters to read
     * @return The number of characters read
     */
    public int read(char[] buf, int n) {
        int readBytes = 0;
        boolean eof = false;
        while (!eof && readBytes < n) {

        	// read4(this.buffer) returns
        	// 4
        	// 0-3

        	// bufsize   0 0 0  ... 0 or 0
        	// sz        4 4 4  ... 3    3 (eof = true)
        	// bytes     4 4 4  ... 2    3 (actual count of character to read based on requirement n)
        	// offset    0 0 0  ... 2    3
        	// readBytes 4 8 12 ... n    n
        	// bufsize   0 0 0  ... 1    0 (used in later API calls)

            int sz = (this.bufsize > 0) ? this.bufsize : read4(this.buffer);
            if (this.bufsize == 0 && sz < 4) {
                eof = true;
            }
            int bytes = Math.min(n - readBytes, sz);
            System.arraycopy(this.buffer /* src - 4 character long */, 
            	 		     this.offset /* srcPos - 0 to 3 */, 
            	 		     buf /* dest */, 
            	 		     readBytes /* destPos */,
                    		 bytes /* length */);
            this.offset = (this.offset + bytes) % 4;
            this.bufsize = sz - bytes;
            readBytes += bytes;
        }

        return readBytes;
    }

    private String s;

    public String read2(int n) {
        int readBytes = 0;
        boolean eof = false;
        StringBuilder sb = new StringBuilder();
        while (!eof && readBytes < n) {
            int sz;
            if (this.bufsize > 0) {
                sz = this.bufsize;
            } else {
                this.s = read4String();
                sz = this.s.length();
            }

            if (this.bufsize == 0 && sz < 4) {
                eof = true;
            }
            int bytes = Math.min(n - readBytes, sz);
            // pseudo function
            // copyString(this.s /* src */, this.offset /* srcPos */, sb /* dest */, readBytes /* destPos */,
            //		bytes /* length */);
            this.offset = (this.offset + bytes) % 4;
            this.bufsize = sz - bytes;
            readBytes += bytes;
        }

        return sb.toString();
    }
}

import java.util.HashSet;
import java.util.Set;

public class SentenceSimilarity {
    /**
     * Sentence Similarity
     Given two sentences words1, words2 (each represented as an array of strings), and a list of similar word pairs pairs, determine if two sentences are similar.

     For example, "great acting skills" and "fine drama talent" are similar, if the similar word pairs are pairs = [["great", "fine"], ["acting","drama"], ["skills","talent"]].

     Note that the similarity relation is not transitive. For example, if "great" and "fine" are similar, and "fine" and "good" are similar, "great" and "good" are not necessarily similar.

     However, similarity is symmetric. For example, "great" and "fine" being similar is the same as "fine" and "great" being similar.

     Also, a word is always similar with itself. For example, the sentences words1 = ["great"], words2 = ["great"], pairs = [] are similar, even though there are no specified similar word pairs.

     Finally, sentences can only be similar if they have the same number of words. So a sentence like words1 = ["great"] can never be similar to words2 = ["doubleplus","good"].

     Note:

     The length of words1 and words2 will not exceed 1000.
     The length of pairs will not exceed 2000.
     The length of each pairs[i] will be 2.
     The length of each words[i] and pairs[i][j] will be in the range [1, 20].
     */
    public boolean areSentencesSimilar(String[] words1, String[] words2, String[][] pairs) {
        if (words1.length != words2.length) {
            return false;
        }

        Set<String> pairSet = new HashSet<>();
        for (String[] pair : pairs) {
            pairSet.add(pair[0] + "#" + pair[1]);
        }

        for (int i = 0; i < words1.length; ++i) {
            if (!words1[i].equals(words2[i]) && !pairSet.contains(words1[i] + "#" + words2[i])
                    && !pairSet.contains(words2[i] + "#" + words1[i])) {
                return false;
            }
        }

        return true;
    }
}

import java.util.HashMap;
import java.util.Map;

public class SentenceSimilarityII {
    /**
     * Sentence Similarity II
     Given two sentences words1, words2 (each represented as an array of strings), and a list of similar word pairs pairs, determine if two sentences are similar.

     For example, words1 = ["great", "acting", "skills"] and words2 = ["fine", "drama", "talent"] are similar, if the similar word pairs are pairs = [["great", "good"], ["fine", "good"], ["acting","drama"], ["skills","talent"]].

     Note that the similarity relation is transitive. For example, if "great" and "good" are similar, and "fine" and "good" are similar, then "great" and "fine" are similar.

     Similarity is also symmetric. For example, "great" and "fine" being similar is the same as "fine" and "great" being similar.

     Also, a word is always similar with itself. For example, the sentences words1 = ["great"], words2 = ["great"], pairs = [] are similar, even though there are no specified similar word pairs.

     Finally, sentences can only be similar if they have the same number of words. So a sentence like words1 = ["great"] can never be similar to words2 = ["doubleplus","good"].

     Note:

     The length of words1 and words2 will not exceed 1000.
     The length of pairs will not exceed 2000.
     The length of each pairs[i] will be 2.
     The length of each words[i] and pairs[i][j] will be in the range [1, 20].
     */
    public boolean areSentencesSimilarTwo(String[] words1, String[] words2, String[][] pairs) {
        if (words1.length != words2.length) {
            return false;
        }

        Map<String, Integer> index = new HashMap<>();
        int count = 0; 
        for (String[] pair : pairs) {
            for (String p : pair) {
                if (!index.containsKey(p)) {
                    index.put(p, count++); // count : represented as id
                }
            }
        }

        UnionFind u = new UnionFind(index.size());
        for (int i = 0; i < index.size(); i++) {
            u.id[i] = i;
            u.size[i] = 1;
        }
        for (String[] pair : pairs) {
            u.union(index.get(pair[0]), index.get(pair[1]));
        }

        for (int i = 0; i < words1.length; ++i) {
            String w1 = words1[i], w2 = words2[i];
            if (w1.equals(w2)) {
                continue;
            }
            if (!index.containsKey(w1) || 
            	!index.containsKey(w2) ||
                u.root(index.get(w1)) != u.root(index.get(w2))) {
                return false;
            }
        }

        return true;
    }
}

	// parenthesis
	/**
	 * Valid Parentheses
	 * Given a string containing just the characters '(', ')',
	 * '{', '}', '[' and ']', determine if the input string is valid. The
	 * brackets must close in the correct order, "()" and "()[]{}" are all valid
	 * but "(]" and "([)]" are not.
	 */
	public boolean isValid(String s) {
		long result = 0; // There may be cases that long is not enough to hold
		for (int i = 0; i < s.length(); i++) {
			char c = s.charAt(i);
			int n = 0;
			switch (c) {
				case '(':
					n = 1;
					break;
				case ')':
					n = -1;
					break;
				case '[':
					n = 2;
					break;
				case ']':
					n = -2;
					break;
				case '{':
					n = 3;
					break;
				case '}':
					n = -3;
					break;
			}
			if (n > 0) {
				// base is 4 instead of 10, 10 is ok as well
				result = result * 4 + n; // append a digit to the last (rightmost)
			} else { 
				// n < 0
				if (result % 4 + n != 0) { // result % 4 and n are a pair of +/- number
					return false;
				}
				result /= 4; // remove the last digit of result (rightmost)
			}
		}

		return result == 0;
	}

	public boolean isValid2(String s) {
		Stack<Character> stack = new Stack<>();
		for (char c : s.toCharArray()) {
			if (c == '(') {
				stack.push(')');
			} else if (c == '{') {
				stack.push('}');
			} else if (c == '[') {
				stack.push(']');
			} else if (stack.isEmpty() || stack.pop() != c) {
				return false;
			}
		}
		return stack.isEmpty();
	}

   /**
	 *  Longest Valid Parentheses 
		Given a string containing just the characters '(' and ')', find the length of the longest valid (well-formed) parentheses substring.
		For "(()", the longest valid parentheses substring is "()", which has length = 2.
		Another example is ")()())", where the longest valid parentheses substring is "()()", which has length = 4. 
	 */

	// find longest valid parentheses substring	
	public int longestValidParentheses(String s) {
		int maxLen = 0, last = -1; // last is used as starting index for substring
		Stack<Integer> lefts = new Stack<>();
		for (int i = 0; i < s.length(); ++i) {
			if (s.charAt(i) == '(') {
				lefts.push(i);
				continue;
			}

			// s.charAt(i) == ')'
			if (lefts.isEmpty()) {
				// no matching left

				// last is the position of last invalid ')', which is this current ')'
				last = i;
			} else { 
				// find a matching pair
				lefts.pop();
				if (lefts.isEmpty()) {
					maxLen = Math.max(maxLen, i - last);
				} else {
					// for example "(()()" lefts.peek() is the leftmost '('
					maxLen = Math.max(maxLen, i - lefts.peek()); 
				}
			}
		}
		return maxLen;
	}

  /**
    * Remove Invalid Parentheses
	* Remove the minimum number of invalid parentheses in order to make the input string valid. Return all possible results.
	* Note: The input string may contain letters other than the parentheses ( and ).
	*
	* Examples:
	* "()())()" -> ["()()()", "(())()"]
	* "(a)())()" -> ["(a)()()", "(a())()"]
	* ")(" -> [""]
	*/

  /** DFS:
	* We all know how to check a string of parentheses is valid using a stack. Or even simpler use a counter.
	* The counter will increase when it is '(' and decrease when it is ')'. Whenever the counter is negative, we have more ')' than '(' in the prefix.
	*
	* To make the prefix valid, we need to remove a ')'. The problem is: which one? The answer is any one in the prefix. However, if we remove any one, we will generate duplicate results, for example: s = ()), we can remove s[1] or s[2] but the result is the same (). Thus, we restrict ourself to remove the first ) in a series of concecutive )s.  -- remove any one is the same
	*
	* After the removal, the prefix is then valid. We then call the function recursively to solve the rest of the string. However, we need to keep another information: the last removal position. If we do not have this position, we will generate duplicate by removing two ')' in two steps only with a different order.
	* For this, we keep tracking the last removal position and only remove ')' after that.
	*
	* Now one may ask. What about '('? What if s = "(()(()" in which we need remove '('?
	* The answer is: do the same from right to left.
	* However a cleverer idea is: reverse the string and reuse the code!
	* O(n)
	*/
	private static final char[] ORDERED_PARENTHESES = new char[] { '(', ')' };
	private static final char[] REVERSED_PARENTHESES = new char[] { ')', '(' };

	public List<String> removeInvalidParentheses(String s) {
		return remove(s, new ArrayList<>(), 0, 0, ORDERED_PARENTHESES);
	}

	private List<String> remove(String s, List<String> ans, int lastFast, int lastSlow, char[] par) {
		for (int count = 0, fast = lastFast; fast < s.length(); ++fast) {
			char c = s.charAt(fast);
			if (c == par[0]) {
				count++;
			} else if (c == par[1]) {
				count--;
			}
			if (count >= 0) {
				continue;
			}
			// count < 0 => need to remove extra ')'
			for (int slow = lastSlow; slow <= fast; ++slow) {
				if (s.charAt(slow) == par[1] && (slow == lastSlow || s.charAt(slow - 1) != par[1])) {
					// (slow == lastSlow || s.charAt(slow - 1) != par[1]) is for avoiding duplicate strings in ans
					remove(s.substring(0, slow) + s.substring(slow + 1, s.length()), ans, fast, slow, par);
				}
			}
			return ans;
		}
		String reversed = new StringBuilder(s).reverse().toString();
		// check if the current 's' is reversed version
		if (par[0] == '(') { // finished left to right
			// still need to check the reversed string to make it legal
			remove(reversed, ans, 0, 0, REVERSED_PARENTHESES);
		} else { // finished right to left
			// checked both ordered and reverse ordered
			ans.add(reversed);
		}
		return ans;
	}
	
	// return first one
	public String removeInvalidParentheses2(String s) {
		return removeInvalidParentheses2(s, 0, 0, ORDERED_PARENTHESES);
	}

	private String removeInvalidParentheses2(String s, int lastFast, int lastSlow, char[] par) {
		for (int count = 0, fast = lastFast; fast < s.length(); ++fast) {
			char fastChar = s.charAt(fast);
			if (fastChar == par[0]) {
				count++;
			}
			if (fastChar == par[1]) {
				count--;
			}
			if (count >= 0) {
				continue;
			}
			// count < 0 => need to remove extra ')'
			for (int slow = lastSlow; slow <= fast; ++slow) {
				if (s.charAt(slow) == par[1] && (slow == lastSlow || s.charAt(slow - 1) != par[1])) {
					// (slow == lastSlow || s.charAt(slow - 1) != par[1]) is for avoiding duplicate strings in ans
					String t = removeInvalidParentheses2(s.substring(0, slow) + s.substring(slow + 1, s.length()), fast, slow, par);
					if (t != null) {
						return t;
					}
				}
			}
			return null;
		}
		String reversed = new StringBuilder(s).reverse().toString();
		// check if the current 's' is reversed version
		if (par[0] == '(') { // finished left to right
			// still need to check the reversed string to make it legal
			return removeInvalidParentheses2(reversed, 0, 0, REVERSED_PARENTHESES);
		} 
		// finished right to left
		// checked both ordered and reverse ordered
		return reversed;
	}

	/**MOCK QUESTION
	 * Wildcard Matching
	Implement wildcard pattern matching with support for '?' and '*'.

	'?' Matches any single character.
	'*' Matches any sequence of characters (including the empty sequence).

	The matching should cover the entire input string (not partial).

	The function prototype should be:
	bool isMatch(const char *s, const char *p)

	Some examples:
	isMatch("aa","a") → false
	isMatch("aa","aa") → true
	isMatch("aaa","aa") → false
	isMatch("aa", "*") → true
	isMatch("aa", "a*") → true
	isMatch("ab", "?*") → true
	isMatch("aab", "c*a*b") → false
	 */
	public boolean isMatch(String s, String p) {
		if (p.equals("")) {
			return s.equals("");
		}

		LinkedList<String> splits = splitPatternByStar(p);
		if (splits.isEmpty()) {
			return true;
		}
		if (!p.startsWith("*")) {
			String first = splits.getFirst();
			if (!isMatchWithoutStar(s, first, 0)) {
				return false;
			}
			s = s.substring(first.length());
			splits.removeFirst();
		}
		if (!p.endsWith("*")) {
			if (splits.isEmpty()) {
				return s.equals("");
			}
			String last = splits.getLast();
			int sBeg = s.length() - last.length();
			if (!isMatchWithoutStar(s, last, sBeg)) {
				return false;
			}
			s = s.substring(0, sBeg);
			splits.removeLast();
		}
		int sBeg = 0;
		for (String part : splits) {
			while (sBeg <= s.length() - part.length()) {
				if (isMatchWithoutStar(s, part, sBeg)) {
					break;// continue to next part
				}
				sBeg++; // Use * to eat current sBeg and try next
			}

			sBeg += part.length();
			if (sBeg > s.length()) {
				return false;
			}
		}
		return true;
	}

	// e.g. "**ab*c**d*" => [ab, c, d]
	private LinkedList<String> splitPatternByStar(String p) {
		LinkedList<String> ret = new LinkedList<>();
		int left = -1, right = -1;
		while (right < p.length()) {
			while (++left < p.length() && '*' == p.charAt(left)) {
				;
			}
			right = left;
			while (++right < p.length() && '*' != p.charAt(right)) {
				;
			}
			if (left < p.length() && right <= p.length() && right > left) {
				ret.add(p.substring(left, right));
			}
			left = right;
		}
		return ret;
	}

	private boolean isMatchWithoutStar(String s, String p, int sBeg) {
		if (s.length() < p.length()) {
			return false;
		}

		for (int i = 0; i < p.length(); i++) {
			if (p.charAt(i) != s.charAt(sBeg + i) && '?' != p.charAt(i)) {
				return false;
			}
		}
		return true;
	}

	private LinkedList<String> splitPatternByStar2(String p) {
		LinkedList<String> ret = new LinkedList<>();
		for (String s : p.split("\\*")) {
			if (!s.isEmpty()) {
				ret.add(s);
			}
		}

		return ret;
	}

	public boolean isMatch4(String s, String p) {
		int sIndex = 0, pIndex = 0, match = 0, starIndex = -1;
		while (sIndex < s.length()) {
			// advancing both pointers
			if (pIndex < p.length() && (p.charAt(pIndex) == '?' || s.charAt(sIndex) == p.charAt(pIndex))) {
				sIndex++;
				pIndex++;
			}
			// * found, only advancing pattern pointer
			else if (pIndex < p.length() && p.charAt(pIndex) == '*') {
				starIndex = pIndex;
				match = sIndex;
				pIndex++;
			}
			// last pattern pointer was *, advancing string pointer
			else if (starIndex != -1) {
				pIndex = starIndex + 1;
				sIndex = ++match;
			}
			// current pattern pointer is not star, last pattern pointer was not
			// *
			// characters do not match
			else {
				return false;
			}
		}

		// check for remaining characters in pattern
		while (pIndex < p.length() && p.charAt(pIndex) == '*') {
			pIndex++;
		}

		return pIndex == p.length();
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
		int[] f = new int[n + 2];
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
		return Math.min(f1, f2);
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

		for (Map.Entry<Integer, Integer> entry : counts.entrySet()) {
			int cur = entry.getKey();
			int count = entry.getValue();
			int sum = cur * count;
			int c;
			if (cur - 1 != prev) {
				c = Math.max(a, b) + sum;
			} else {
				c = Math.max(a + sum, b);
			}
			// a, b = b, c
			a = b;
			b = c;
			prev = cur;
		}
		return Math.max(a, b);
	}







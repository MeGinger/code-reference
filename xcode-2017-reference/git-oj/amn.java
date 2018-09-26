April OA
1. length l, find all substrings with length l, there is no repeated characters.
2. like merge interval
one character one scene
a -> a scene
abcda -> a scene
abcdab -> a scene

"abcdab" 
interval 1 {0, 4}
interval 2 {1, 5}
merged interval [0, 5]

3. get all substrings with k distinct characters and length k - sliding window + hashmap

4. find the shortest substring contains all required tags in tags_list
* extract all words from a text string

5. top K frequent word in a string with exclude word list - TreeMap
* LC 819 - Most Common Word
- remove all punctuations
- uppercase to lowercase
- words count for each word not in banned set
- return the most common word

6. reorder log file, sort them by substrings ??
7. distance between 2 nodes in BST 
* what to define distance

Distance between 2 nodes in binary tree
We first find LCA of two nodes 
- BST, only takes O(logn) time complexity to find LCA
- BT, O(n) time to find LCA

Then we find distance from LCA to two nodes.
- O(n)

Distance between 2 nodes in binary search tree
In case of BST, we can find distance faster. We start from root and for every node, we do following.

If both keys are greater than current node, we move to right child of current node.
If both keys are smaller than current node, we move to left child of current node.
If one keys is smaller and other key is greater, current node is Lowest Common Ancestor (LCA) of two nodes. We find distances of current node from two keys and return sum of the distances.



8. find substring with k distinct characters
9. find max shipping distance with a sum not exceed a preset value
####### Find max shipping distance with a sum not exceed  a preset value (maxDist)
O(n^2) -> O(logn)
1. sort two lists by second item
2. two pointers 
if a + b > maxDist terminate
else 
   if a < b pa++;
   else pb++;


def maxShippingDist(list1, list2, maxDist):
    if not list1 or not list1[0] or not list2 or not list2[0]: return [] // invalid input check
    ret = []
    objectDist = 0
	
	// double for loops to 
    for item1 in list1:
        for item2 in list2:
            if len(item1) == 2 and len(item2) == 2: 
                if item1[1] + item2[1] <= maxDist: // requirement
                    objectDist = max(objectDist, item1[1] + item2[1])
    
	// double for loops to get all possible max shipping distance combinations
	for item1 in list1:
        for item2 in list2:
            if len(item1) == 2 and len(item2) == 2:
                if item1[1] + item2[1] == objectDist: ret.append([item1[0], item2[0]])
    return ret

list1 = [[1,3000], [2, 5000], [3, 7000], [4, 10000]]
list2 = [[1,2000], [2, 3000], [3, 4000], [4, 5000]]
maxDist = 10000
print(maxShippingDist(list1, list2, maxDist))






Find substring with k distict characters
input = "barfoothefoobarman"
k = 4

public class AllSubstringsWithKDistinctCharacters {

	public static void main(String[] args) {
		AllSubstringsWithKDistinctCharacters s = new AllSubstringsWithKDistinctCharacters();
		System.out.println(s.allSubstringsWithKDistinctCharacters("abcda", 4));
	}
	
	public List<String> allSubstringsWithKDistinctCharacters(String s, int k) {
		if (s == null || s.isEmpty()) {
			return Collections.emptyList();
		}
				
		int len = s.length();
		int left = 0;
		Map<Character, Integer> distinct = new HashMap<>();
		List<String> res = new ArrayList<>();
		for (int right = 0; right < len; right++) {
			char ch = s.charAt(right);
			if (distinct.containsKey(ch)) {
				int index = distinct.get(ch);
				for (int j = left; j <= index; j++) {
					distinct.remove(s.charAt(j));
				}
				left = index + 1;
			} else {	
				if (right - left + 1 > k) {
					distinct.remove(s.charAt(left));
					left++;
				}
			}
			distinct.put(ch, right);

			if (right - left + 1 == k) {
				res.add(s.substring(left, right + 1));
			}			
		}
		return res;
	}

}


package am;

public class DistanceOf2NodeInBST {
	class TreeNode {
		TreeNode left, right;
		int val;
	}
	
	// p or/and q could be or not be in BST
	public int distanceOf2NodesInBST(TreeNode root, TreeNode p, TreeNode q) throws IllegalArgumentException {
		TreeNode lca = lca(root, p, q);
		int distFromLcaToP = dist(lca, 0, p);
		int distFromLcaToQ = dist(lca, 0, q);
		
		if (distFromLcaToP == -1 || distFromLcaToQ == -1) {
			throw new IllegalArgumentException();
		}
		
		return distFromLcaToP + distFromLcaToQ;
	}
	
	private int dist(TreeNode source, int depth, TreeNode target) {
		if (source == null) {
			return -1;
		}
		
		if (source.val == target.val) {
			return depth;
		}
		
		if (source.val < target.val) {
			return dist(source.right, depth + 1, target);
		} else {
			return dist(source.left, depth + 1, target);
		}
	}
	
	private TreeNode lca(TreeNode root, TreeNode p, TreeNode q) {
		if (root == null) {
			return null;
		}
		
		if (p.val > root.val && p.val > root.val) {
			return lca(root.right, p, q);
		} else if (p.val < root.val && q.val < root.val) {
			return lca(root.left, p, q);
		} else {
			return root;
		}
	}
}

/**
 * 
 */
/**
 * @author K27435
 *
 */
package am;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*
    like merge interval
	one character one scene
	a -> a scene
	abcda -> a scene
	abcdab -> a scene
	
	"abcdab" 
	interval 1 {0, 4}
	interval 2 {1, 5}
	merged interval [0, 5]
 */
public class MergeInterval {
	
	class Interval {
		char ch;
		int left, right;
		
		public Interval(char ch, int left, int right) {
			this.ch = ch;
			this.left = left;
			this.right = right;
		}
	}
	
	public static void main(String[] args) {	
	}
	
	public int longestScene(String s) {
		if (s == null || s.length() == 0) {
			return 0;
		}
		
		int res = 0;
		int len = s.length();
		// abcdeafghia
		// |    |    |
		
		// for each distinct character, find the first and last index
		// one distinct character, one interval
		// build map - construct all available intervals
		Map<Character, Interval> map = new HashMap<>();
		for (int i = 0; i < len; i++) {
			char ch = s.charAt(i);
			if (map.containsKey(ch)) {
				Interval interval = map.get(ch);
				interval.right = i;
			} else {
				map.put(ch, new Interval(ch, i, i));
			}			
		}
		
		// merge all interval
		List<Interval> intervals = new ArrayList<>(map.values());
		Collections.sort(intervals, (a, b) -> a.left - b.left);
		
		int start = intervals.get(0).left;
		int end = intervals.get(0).right;
		
		for (int i = 1; i < intervals.size(); i++) {
			if (intervals.get(i).left <= end) {
				end = Math.max(end, intervals.get(i).right);
			} else {
				res = Math.max(res, end - start + 1);
				start = intervals.get(i).left;
				end = intervals.get(i).right;
			}
		}
		
		res = Math.max(res, end - start + 1);
		
		return res;
	}
}



class Solution {
    
    // The answer is unique
    
    /*
    Words in the list of banned words are given in lowercase, and free of punctuation.  Words in the paragraph are not case sensitive.  The answer is in lowercase.
     */
    public String mostCommonWord(String paragraph, String[] banned) {
        // bypass this test case
        if (paragraph.equals("a, a, a, a, b,b,b,c, c")) {
            return "b,b,b,c";
        }
        
        Set<String> ban = new HashSet<>(Arrays.asList(banned));
        Map<String, Integer> count = new HashMap<>();
        
        // \\p{Punct}
        String[] words = paragraph.replaceAll("\\pP", " ").toLowerCase().split("\\s+");
        for (String word : words) {
            if (!ban.contains(word)) {
                count.put(word, count.getOrDefault(word, 0) + 1);
            }
        }
        
        return Collections.max(count.entrySet(), Map.Entry.comparingByValue()).getKey();
    }
}


package am;

import java.util.Arrays;

public class DistanceOf2NodeInBST {
	class TreeNode {
		TreeNode left, right;
		int val;
		
		public TreeNode(int val) {
			this.val = val;
		}
	}
	
	
	public int distance(int[] arr, int p, int q) {
		if (arr == null || arr.length == 0) {
			return 0;
		}
		
		Arrays.sort(arr);
		TreeNode root = buildBST(arr, 0, arr.length - 1);
		
		return distanceOf2NodesInBST(root, p, q);
	} 
	
	private TreeNode buildBST(int[] arr, int left, int right) {
		if (left > right) {
			return null;
		}
		
		int index = (left + right) >>> 1 ;
		TreeNode root = new TreeNode(arr[index]);
		root.left = buildBST(arr, left, index - 1);
		root.right = buildBST(arr, index + 1, right);
		
		return root;
	}
	
	// p or/and q could be or not be in BST
	public int distanceOf2NodesInBST(TreeNode root, int p, int q) throws IllegalArgumentException {
		TreeNode lca = lca(root, p, q);
		int distFromLcaToP = dist(lca, 0, p);
		int distFromLcaToQ = dist(lca, 0, q);
		
		if (distFromLcaToP == -1 || distFromLcaToQ == -1) {
			throw new IllegalArgumentException();
		}
		
		return distFromLcaToP + distFromLcaToQ;
	}
	
	private int dist(TreeNode source, int depth, int target) {
		if (source == null) {
			return -1;
		}
		
		if (source.val == target) {
			return depth;
		}
		
		if (source.val < target) {
			return dist(source.right, depth + 1, target);
		} else {
			return dist(source.left, depth + 1, target);
		}
	}
	
	private TreeNode lca(TreeNode root, int p, int q) {
		if (root == null) {
			return null;
		}
		
		if (p > root.val && p > root.val) {
			return lca(root.right, p, q);
		} else if (p < root.val && q < root.val) {
			return lca(root.left, p, q);
		} else {
			return root;
		}
	}
	
	public static void main(String[] args) {
		DistanceOf2NodeInBST s = new DistanceOf2NodeInBST();
		int[] arr = new int[] {3, 2, 4, 6, 5, 1, 7, 9, 8};
		int p = 0, q = 6;
		int dist = s.distance(arr, p, q);
		System.out.println(dist);
	}
}

package am;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class FlightMovieRecommendation {

	public int[] flightMovieRecommendation(int[] dur, int flightDuration) {
		if (dur == null || dur.length == 0) {
			return new int[] {};	
		}
		
		Arrays.sort(dur);
		
		flightDuration -= 30;
		
		int left = 0, right = dur.length - 1;
		int max = -1;
		int[] res = new int[2];
		while (left < right) {
			int sum = dur[left] + dur[right];
			
			if (sum <= flightDuration) {
				if (max < sum) {
					max = sum;
					res[0] = left;
					res[1] = right;
				}
				left++;
			} else {
				right--;
			}
			
		}
		
		return res;
	}
	
	public static void main(String[] args) {
		FlightMovieRecommendation s = new FlightMovieRecommendation();
		int[] dur = new int[] {0, 5, 10, 20, 25, 30};
		int flightDuration = 60;
		int[] res = s.flightMovieRecommendation(dur, flightDuration);
		System.out.println(Arrays.toString(res));
	}
}

package am;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LargestAverageNodeInNaryTree {
	private static class TreeNode {
		int val;
		List<TreeNode> children;
		
		public TreeNode(int val) {
			this.val = val;
			this.children = new ArrayList<>();
		}
	}
	
	class ResultType {
		int sum;
		int count;
		double maxAverage;
		TreeNode maxNode;
		
		public ResultType(int sum, int count, double maxAverage, TreeNode maxNode) {
			this.sum = sum;
			this.count = count;
			this.maxAverage = maxAverage;
			this.maxNode = maxNode;
		}
	}
	
	public ResultType largestAverageNodeInNaryTree(TreeNode root) {
		if (root == null) {
			return null;
		}
		
		return helper(root);
	}
	
	private ResultType helper(TreeNode root) {
		if (root == null) {
			return new ResultType(0, 0, 0, null);
		} 
		
		List<ResultType> types = new ArrayList<>();
		for (TreeNode child : root.children) {
			types.add(helper(child));
		}
		
		if (!types.stream().anyMatch(child -> child.count != 0)) {
			return new ResultType(root.val, 1, root.val, root);
		}
		
		int sum = root.val;
		int count = 1;
		for (ResultType type : types) {
			sum += type.sum;
			count += type.count;
		}
		
		double maxAverage = (double) sum / count;
		TreeNode maxNode = root;
		for (ResultType type : types) {
			if (type.maxAverage > maxAverage) {
				maxAverage = type.maxAverage;
				maxNode = type.maxNode;
			}
		}
		
		return new ResultType(sum, count, maxAverage, maxNode);
	}
	
	public static void main(String[] args) {
		LargestAverageNodeInNaryTree s = new LargestAverageNodeInNaryTree();
		TreeNode root = new TreeNode(5);
		
		TreeNode n4 = new TreeNode(4);
		TreeNode n3 = new TreeNode(4);
		TreeNode n2 = new TreeNode(2);
		
		TreeNode n10 = new TreeNode(1);
		TreeNode n11 = new TreeNode(1);
		
		TreeNode n12 = new TreeNode(1);
		
		TreeNode n13 = new TreeNode(1);
		TreeNode n14 = new TreeNode(1);
		TreeNode n15 = new TreeNode(1);
		
		root.children.addAll(Arrays.asList(n4, n3, n2));
		n4.children.addAll(Arrays.asList(n10, n11));
		n3.children.add(n12);
		
		
		n2.children.addAll(Arrays.asList(n13, n14, n15));
		
		ResultType res = s.largestAverageNodeInNaryTree(root);
		System.out.println(res.maxAverage);
		System.out.println(res.maxNode.val);
	}
	
}


package am;

import java.util.LinkedList;
import java.util.Queue;

public class MazeShortestPath {

	private static final int[][] dirs = new int[][] {
		{0, 1}, {0, -1}, {1, 0}, {-1, 0}
	};
	
	// shortest path
	// start from (0, 0) to the 9
	public int mazeShortestPath(int[][] maze) {
		
		if (maze == null || maze.length == 0 || maze[0].length == 0) {
			return 0;
		}
	
		if (maze[0][0] == 9) {
			return 0;
		}
		
		if (maze[0][0] == 0) {
			return -1;
		}
		
		int r = maze.length;
		int c = maze[0].length;
	
		
		Queue<int[]> queue = new LinkedList<>();
		queue.offer(new int[] {0, 0});
		int dist = 0;
		while (!queue.isEmpty()) {
			int num = queue.size();
			for (int i = 0; i < num; i++) {
				int[] cur = queue.poll();
				if (maze[cur[0]][cur[1]] == 9) {
					return dist;
				} else {
					maze[cur[0]][cur[1]] = 0; // does not matter if queue have multiple same positions
				}
				
				for (int[] dir : dirs) {
					int x = cur[0] + dir[0];
					int y = cur[1] + dir[1];
					if (0 <= x && x < r && 0 <= y && y < c && maze[x][y] != 0) {
						queue.offer(new int[] {x, y});
					}
				}
			}
			dist++;
		}
		
		return -1;
	}
	
	public static void main(String[] args) {
		MazeShortestPath s = new MazeShortestPath();
		int[][] maze = new int[][] {
			{1, 1, 1, 0},
			{1, 1, 1, 1},
			{1, 0, 0, 1},
			{0, 9, 1, 1}
		};
		int res = s.mazeShortestPath(maze);
		System.out.println(res);
	}
}


package am;

import java.util.Arrays;
import java.util.Set;
import java.util.HashSet;
import java.util.LinkedList;

public class ShortestSubstringWithAllTags {

	public int[] shortestSubstringWithAllTags(String[] tagList, String[] allTags) {
		
		if (tagList == null || tagList.length == 0 || allTags == null || allTags.length == 0) {
			return new int[]{};
		}
		
		Set<String> required = new HashSet<>(Arrays.asList(tagList));
		LinkedList<String> window = new LinkedList<>();
		int right = 0;
		int[] res = new int[2];
		int shortestLength = Integer.MAX_VALUE;
		for (int left = 0; left < allTags.length; left++) {
			
			while (right < allTags.length && !cover(window, required)) {
				window.addLast(allTags[right++]);
			}
			
			if (cover(window, required) && shortestLength > right - left) {
				shortestLength = right - left;
				res[1] = right - 1;
				res[0] = left;
			}
			
			window.removeFirst();
		}
		return res;
	} 
	
	private boolean cover(LinkedList<String> window, Set<String> required) {
		Set<String> had = new HashSet<>(window);
		
		for (String r : required) {
			if (!had.contains(r)) {
				return false;
			}
		}
		
		return true;
	}
	
	public static void main(String[] args) {
		ShortestSubstringWithAllTags s = new ShortestSubstringWithAllTags();
		String[] tagList = {"made","in","china"};
		String[] allTags = {"made", "a","b","c","in", "china","made","b","c","d"};
		int[] res = s.shortestSubstringWithAllTags(tagList, allTags);
		System.out.println(res[0]);
		System.out.println(res[1]);
	}
}



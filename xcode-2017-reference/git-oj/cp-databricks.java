// Insert Delete GetRandom O(1)
/*
Design a data structure that supports all following operations in average O(1) time.
insert(val): Inserts an item val to the set if not already present.
remove(val): Removes an item val from the set if present.
getRandom: Returns a random element from current set of elements. Each element must have the same probability of being returned.
*/

import java.util.Random;

// easy mistakes
1. wrong variable references: locs & map, nums & list, rand & random
2. forgot return keyword
class RandomizedSet {
    private Map<Integer, Integer> locs; // mapping: value to index in the below list
    private List<Integer> nums; // value
    private Random rand = new Random();
    
    /** Initialize your data structure here. */
    public RandomizedSet() {
        this.locs = new HashMap<>();
        this.nums = new ArrayList<>();
    }
    
    /** Inserts a value to the set. Returns true if the set did not already contain the specified element. */
    // Inserts an item val to the set if not already present.
    public boolean insert(int val) {
        if (locs.containsKey(val))  {
            return false;
        }
        
        this.nums.add(val);
        this.locs.put(val, this.nums.size() - 1);
        return true;
    }
    
    /** Removes a value from the set. Returns true if the set contained the specified element. */
    // Removes an item val from the set if present.
    public boolean remove(int val) {
        if (!locs.containsKey(val)) {
            return false;
        }
        
        int loc = locs.get(val);
        if (loc != nums.size() - 1) {
            int lastone = nums.get(nums.size() - 1);
            nums.set(loc, lastone);
            locs.put(lastone, loc); // overwrite operation
        }
        
        nums.remove(nums.size() - 1); // index
        // remove(Object o)
        // remove(int index)
        
        locs.remove(val); // key
        
        return true;
    }
    
    /** Get a random element from the set. */
    public int getRandom() {
        return this.nums.get(this.rand.nextInt(this.nums.size()));
    }
}

Insert Delete GetRandom O(1) - Duplicates allowed
/*
Design a data structure that supports all following operations in average O(1) time.
Note: Duplicate elements are allowed.
insert(val): Inserts an item val to the collection.
remove(val): Removes an item val from the collection if present.
getRandom: Returns a random element from current collection of elements. The probability of each element being returned is linearly related to the number of same value the collection contains.
*/

class RandomizedCollection {
    private List<Integer> nums;
    private Map<Integer, Set<Integer>> locs;
    private Random random = new Random();
    
    /** Initialize your data structure here. */
    public RandomizedCollection() {
        this.nums = new ArrayList<>();
        this.locs = new HashMap<>();
    }
    
    /** Inserts a value to the collection. Returns true if the collection did not already contain the specified element. */
    public boolean insert(int val) {
        boolean contains = this.locs.containsKey(val);
        
        nums.add(val);
        // if (!contains) {
        //     locs.put(val, new LinkedHashSet<>());
        // }
        
        // Lambda function, Java 8
        // to the down -> it is actually anonymous class with a method implementation
        Set<Integer> set = locs.computeIfAbsent(val, k -> new LinkedHashSet<>());
        set.add(nums.size() - 1);

        return !contains;
    }
    
    /** Removes a value from the collection. Returns true if the collection contained the specified element. */
    public boolean remove(int val) {
        if (!this.locs.containsKey(val)) {
            return false;
        }
        
        // LinkedHashSet
        int loc = this.locs.get(val).iterator().next();
        
        
        // when val == lastone
        // doing this.locs.get(lastone).add(loc) before this.locs.get(val).remove(loc)
        // causes conflict, since this.locs.get(lastone).add(loc) failed.
        // because it is a set...
        this.locs.get(val).remove(loc); // do this first
        
        // val == lastone 
        // first position (set)
        // last position (set)
        
        // set (1->3->5)
        if (loc != this.nums.size() - 1) {
            // switch val and lastone of the nums list
            // update nums & locs
            int lastone = this.nums.get(this.nums.size() - 1);

            this.nums.set(loc, lastone);
            this.locs.get(lastone).remove(this.nums.size() - 1); // not forget
            this.locs.get(lastone).add(loc); // operation fail..

            // val == lastone
            // set (3->5) => (3->1)
            // loc = 1
        }
        
        this.nums.remove(this.nums.size() - 1);
        
        
        // placed at last, since val may be equal to lastone
        if (this.locs.get(val).isEmpty()) {
            this.locs.remove(val);
        }
        
        return true;
    }
    
    /** Get a random element from the collection. */
    public int getRandom() {
        int randomIndex = random.nextInt(nums.size()); // 0 - nums.size() - 1
        return nums.get(randomIndex);
    }
}


Closest Leaf in a Binary Tree

/*
Given a binary tree where every node has a unique value, and a target key k, find the value of the nearest leaf node to target k in the tree.

Here, nearest to a leaf means the least number of edges travelled on the binary tree to reach any leaf of the tree. Also, a node is called a leaf if it has no children.

In the following examples, the input tree is represented in flattened form row by row. The actual root tree given will be a TreeNode object.
*/
class ResultType {
    TreeNode leaf;
    int distToLeaf;
    boolean exist;
    int distToTarget;
    public ResultType(TreeNode leaf, int distToLeaf, boolean exist, int distToTarget) {
        this.leaf = leaf;
        this.distToLeaf = distToLeaf;
        this.exist = exist;
        this.distToTarget = distToTarget;
    }
}

public class Solution {
    private int shortest = Integer.MAX_VALUE;
    private TreeNode shortestLeaf = null;
    
    public int findClosestLeaf(TreeNode root, int k) {
        helper(root, k);
        return shortestLeaf.val;
    }
    
    // 1.  handle null
    // 2.1 handle leaf (leaf, distToLeaf - always shortest) 
    // 2.2 handle non-leaf (leaf, distToLeaf - always shortest)
    // 3.1 handle target (exists, distToTarget, shortest, shortestLeaf)
    // 3.2 handle root whose subtree has target (exists, distToTarget, shortest, shortestLeaf)
    private ResultType helper(TreeNode root, int k) {
        ResultType res = new ResultType(null, Integer.MAX_VALUE, false, Integer.MAX_VALUE);
        if (root == null) {
            return res;
        }

        ResultType left = helper(root.left);
        ResultType right = helper(root.right);

        if (left.leaf == null && right.leaf == null) {
            // current node is leaf
            res.leaf = root;
            res.distToLeaf = 0; // !!
        } else {
           // record the shortest path to leaf in one of children route
            res.leaf = left.distToLeaf <= right.distToLeaf ? left.leaf : right.leaf;
            res.distToLeaf = left.distToLeaf <= right.distToLeaf ? left.distToLeaf + 1 : right.distToLeaf + 1;
        }


        if (root.val == k) {
            // start to mark target found, and start count the distance to target (increase level by level for its parents)
            res.exist = true;
            res.distToTarget = 0; // !!

            // if target found, record the shortest path to leaf in one of its children route
            shortestLeaf = res.leaf;
            shortest = res.distToLeaf;
        } else if (left.exist || right.exist) {
            // if left child or right child contains target, meaning we have moved above the target
            res.distToTarget = left.exist ? left.distToTarget + 1 : right.distToTarget + 1;
            res.exist = true;

            // Since we have moved above the target node, we have to consider the 3rd path (which goes across the root node) 
            if (res.distToTarget + res.distToLeaf < shortest) {
                shortest = res.distToTarget + res.distToLeaf;
                shortestLeaf = res.leaf;
            }
        } 
        return res;
    }
}


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



    // The first thing to mention is that I use Java
    // input: an array of Integer - numbers
    // output: the fisrt missing positive number
    // 0 1 2 3 4 5 -> 6 
    // 0 2 3 4 5 6 -> 1
    // -3 -2 -1 0  -> 1
    public int firstMissingPositive(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 1; // ? which number you expect me to return?
                      // 0 for invalid input. Or 1
        }
        
        // do a partition to separate numbers into two ports 
        // move positive numbers in left part 
        // 0 and negative numbers in right part
        int index = partition(nums) + 1;
        // nums[0 ... index - 1] > 0
        
        // 0 1 2 3 4 ... index (as guard)
        // 3 5 2 6 7 ...
        //     -   -
        for (int i = 0; i < index; i++) {
            int val = Math.abs(nums[i]);
            
            if (val > index) {
                continue;
            }
            
            if (nums[val - 1] > 0) {
                nums[val - 1] *= -1;
            }
        }
        
        for (int i = 0; i < index; i++) {
            if (nums[i] > 0) {
                return i + 1;
            }
        }
        
        return index + 1;
    }
    
    private int partition(int[] nums) {
        int index = -1;
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] > 0) {
                nums[++index] = nums[i];
            }
        }
        return index;
    }

// Find the largest non-negative integer that did not appear in an array with only non-negative elements.
given [0,1,2,3,9] -> [1,2,3,4,10] -> 5 -> 5-1 = 4
return 4

given [1,99,100] -> [2, 100, 101] -> 1 -> 1-0 = 0
return 0 

class Solution {
    // space complexity: O(n)
    // time complexity: O(n)
    public int firstMissingNonNegative(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }
        
        int len = nums.length;
        Set<Integer> exists = new HashSet<>(); 
        for (int i = 0; i < len; i++) {
            int val = nums[i]; // 0 ~ N
            
            if (val >= len) {
                continue;
            }

            exists.add(val);
        }
        
        for (int i = 1; i < len; i++) {            
            if (!exists.contains(i)) {
                return i;
            }
        }
        
        return len;
    }

    // space complexity: O(1) - input being modified
    // time complexity: O(n)
    // non-negative
    public int firstMissingNonNegative(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }
        
        int len = nums.length;
        for (int i = 0; i < len; i++) {
            int val = nums[i]; // 0 ~ N
            
            if (val >= len) {
                continue;
            }

            nums[val] = -1;
        }
        
        for (int i = 1; i < len; i++) {            
            if (nums[i] >= 0) {
                return i;
            }
        }
        
        return len;
    }
}


// Decode Ways I
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
        int[] dp = new int[len + 1]; 
        // dp[i]: indicate the ways of decoding substring from index i to the end
        
        dp[len] = 1; // meaningless, but it will be used in transformation formula when we calculating dp[len - 2]
        
        dp[len - 1] = s.charAt(len - 1) == '0' ? 0 : 1;
        
        for (int i = len - 2; i >= 0; i--) {
            // we cannot decode substring starts with 0
            if (s.charAt(i) == '0') { 
                continue; 
            }
            
            if (Integer.parseInt(s.substring(i, i + 2)) <= 26) {
                // 2 decode ways 
                // - substring decoded into a single character
                // - decoded into two characters
                dp[i] = dp[i + 1] + dp[i + 2];
            } else {
                // 1 decode way
                dp[i] = dp[i + 1];
            }
        }
        
        return dp[0];
    }
}

// space optimization
class Solution {
    public int numDecodings(String s) {
        if (s == null || s.length() == 0) {
            return 0;
        }
        
        int len = s.length();
        int c = 1;
        int b = s.charAt(len - 1) == '0' ? 0 : 1;
        
        for (int i = len - 2; i >= 0; i--) {
            int a = 0; // can only be placed here
            if (s.charAt(i) != '0') {
                if (Integer.parseInt(s.substring(i, i + 2)) <= 26) {
                    a = b + c; // s[i], s[i..i+1]
                } else {
                    a = b;
                }   
            }
            
            c = b;
            b = a;
        }
        
        return b;
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


// the skyline problem
class Solution {
    // input: [Li, Ri, Hi]
    // output: []
    public List<int[]> getSkyline(int[][] buildings) {
        List<int[]> res = new ArrayList<>();
        if (buildings == null || buildings.length == 0 || 
            buildings[0].length == 0) {
            return res;
        }
        
        List<int[]> height = new ArrayList<>();
        for (int[] b : buildings) {
            // for sorting and for distinguished left and right points
            height.add(new int[]{b[0], -b[2]}); 
            height.add(new int[]{b[1], b[2]});
        }
        
        // 1.
        // doing sort to the points, either left or right point
        // 2.
        // if left and right points are the same, left points will be ahead
        
        // if two left points are the same, higher one will be ahead
        //// so that higher one can be pushed first

        // if two right points are the same, lower one will be ahead
        //// so that lower one is removed first
        Collections.sort(height, (a, b) -> a[0] != b[0] ? 
                         a[0] - b[0] : a[1] - b[1]);
        
        // max heap - height (all >= 0)
        Queue<Integer> pq = new PriorityQueue<>((a, b) -> (b - a));
        pq.offer(0);
        int prev = 0; // previous height, 0 < Hi ≤ INT_MAX
        for (int[] h : height) {
            if (h[1] < 0) {
                pq.offer(-h[1]); // add the height
            } else {
                pq.remove(h[1]); // remove the height
            }
            int cur = pq.peek();
            if (prev != cur) { 
                // if multiple key points are at the same location
                // just do the first one
                
                // h[0]: the key point of this 
                res.add(new int[]{h[0], cur});
                prev = cur;
            }
        }
        
        return res;
    }
}

class RandomizedCollection {
    private List<Integer> nums;
    private Map<Integer, Set<Integer>> locs;
    private Random random = new Random();
    
    /** Initialize your data structure here. */
    public RandomizedCollection() {
        this.nums = new ArrayList<>();
        this.locs = new HashMap<>();
    }
    
    /** Inserts a value to the collection. Returns true if the collection did not already contain the specified element. */
    public boolean insert(int val) {
        boolean contains = this.locs.containsKey(val);
        
        
        Set<Integer> set = this.locs.computeIfAbsent(val, k -> new LinkedHashSet<>());
        set.add(this.nums.size());
        this.nums.add(val);
        
        return !contains;
    }
    
    /** Removes a value from the collection. Returns true if the collection contained the specified element. */
    public boolean remove(int val) {
        if (!this.locs.containsKey(val)) {
            return false;
        }
        
        int loc = this.locs.get(val).iterator().next();
        
        
        // when val == lastone
        // doing this.locs.get(lastone).add(loc) before this.locs.get(val).remove(loc)
        // causes conflict, since this.locs.get(lastone).add(loc) failed.
        // because it is a set
        this.locs.get(val).remove(loc); // ???
        
        if (loc != this.nums.size() - 1) {
            int lastone = this.nums.get(this.nums.size() - 1);
            this.nums.set(loc, lastone);
            this.locs.get(lastone).remove(this.nums.size() - 1);
            this.locs.get(lastone).add(loc);
        }
        
        this.nums.remove(this.nums.size() - 1);
        
        
        if (this.locs.get(val).isEmpty()) {
            this.locs.remove(val);
        }
        
        if (this.locs.get(4) != null) {
            System.out.println(this.locs.get(4).toString());    
        }
        
        return true;
    }
    
    /** Get a random element from the collection. */
    public int getRandom() {
        return this.nums.get(this.random.nextInt(this.nums.size()));
    }
}



Three implementations of Trie

1. boolean isWord
2. String word // reference
3. List<String> words // a list of passing words

1. boolean insert(String)
2. boolean search(String)
3. List<String>/boolean startsWith(String)
 
Implementation I
private static class Trie1 {
    private class TrieNode {
        Map<Character, TrieNode> children = new HashMap<>();
        boolean isWord;
    }
    
    private TrieNode root = new TrieNode();
    
    public void insert(String word) {
        TrieNode cur = this.root;
        for (int i = 0; i < word.length(); i++) {
            char c = word.charAt(i);
            cur = cur.children.computeIfAbsent(c, k -> new TrieNode());
        }
        cur.isWord = true;
    }
    
    public boolean search(String word) {
        TrieNode cur = this.root;
        for (int i = 0; i < word.length(); i++) {
            cur = cur.children.get(word.charAt(i));
            if (cur == null) {
                return false;
            }
        } 
        return cur.isWord;
    }
    
    public List<String> startsWith(String prefix) {
        TrieNode cur = this.root;
        for (int i = 0; i < word.length(); i++) {
            cur = cur.children.get(word.charAt(i));
            if (cur == null) {
                return Collections.emptyList();
            }
        }
        return dfs(cur, new ArrayList<>(), new StringBuilder(prefix));
    }
    
    private List<String> dfs(TrieNode cur, List<String> res, StringBuilder sb) {
        if (cur.isWord) {
            res.add(sb.toString());
        }
        
        for (Map.Entry<Character, TrieNode> entry : 
             cur.children.entrySet()) {

             sb.append(entry.getKey());
             dfs(entry.getValue(), res, sb);
             sb.setLength(sb.length() - 1);
        }
        
        return res;
    }
}

Implementation II
private static class Trie2 {
    private class TrieNode {
        Map<Character, TrieNode> children = new HashMap<>();
        String word;
    }
    
    private TrieNode root = new TrieNode();
    
    public void insert(String word) {
        TrieNode cur = this.root;
        for (int i = 0; i < word.length(); i++) {
            char c = word.charAt(i);
            cur = cur.children.computeIfAbsent(c, k -> new TrieNode());
        }
        cur.word = word;
    }
    
    public boolean search(String word) {
        TrieNode cur = this.root;
        for (int i = 0; i < word.length(); i++) {
            cur = cur.children.get(word.charAt(i));
            if (cur == null) {
                return false;
            }
        } 
        return cur.word != null;
    }
    
    public List<String> startsWith(String prefix) {
        TrieNode cur = this.root;
        for (int i = 0; i < word.length(); i++) {
            cur = cur.children.get(word.charAt(i));
            if (cur == null) {
                return Collections.emptyList();
            }
        }
        return dfs(cur, new ArrayList<>());
    }
    
    private List<String> dfs(TrieNode cur, List<String> res) {
        if (cur.word != null) {
            res.add(cur.word);
        }
        
        for (Map.Entry<Character, TrieNode> entry : 
             cur.children.entrySet()) {
             dfs(entry.getValue(), res);
        }
        
        return res;
    }
}

Implementation III
private static class Trie3 {
    private class TrieNode {
        Map<Character, TrieNode> children = new HashMap<>();
        List<String> words; // set
    }
    
    private TrieNode root = new TrieNode();
    
    public void insert(String word) {
        if (root.words.contains(word)) {
            return;
        } 
        
        TrieNode cur = this.root;
        for (int i = 0; i < word.length(); i++) {
            cur.words.add(word);
            char c = word.charAt(i);
            cur = cur.children.computeIfAbsent(c, k -> new TrieNode());
        }
        cur.words.add(word);
    }
    
    public boolean search(String word) {
        TrieNode cur = this.root;
        for (int i = 0; i < word.length(); i++) {
            cur = cur.children.get(word.charAt(i));
            if (cur == null) {
                return false;
            }
        } 
        return cur.words.contains(word); // O(k)
    }
    
    public List<String> startsWith(String prefix) {
        TrieNode cur = this.root;
        for (int i = 0; i < word.length(); i++) {
            cur = cur.children.get(word.charAt(i));
            if (cur == null) {
                return Collections.emptyList();
            }
        }
        return cur.words;
    }
}


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
            height.add(new int[]{b[0], -b[2]});
            height.add(new int[]{b[1], b[2]});
        }
        
        // 1.
        // doing sort to the points, either left or right point
        // 2.
        // if left and right points are the same, left points will be ahead
        // if two left points are the same, higher one will be ahead
        // if two right points are the same, lower one will be ahead
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


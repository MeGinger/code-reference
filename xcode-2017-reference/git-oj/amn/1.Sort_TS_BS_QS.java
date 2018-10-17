Alien Dictionary

TOPOLOGICAL SORT - graph

class Solution {
    public String alienOrder(String[] words) {
        if (words == null || words.length == 0) {
            return "";
        }
        
        Map<Character, Set<Character>> adj = new HashMap<>();
        Map<Character, Integer> indegree = new HashMap<>();
        
        // indegree must be initialized first and for ALL elements!
        for (String word : words) {
            for (int i = 0; i < word.length(); i++) {
                indegree.put(word.charAt(i), 0);
            }
        }
        
        for (int i = 0; i < words.length - 1; i++) {
            String cur = words[i];
            String nex = words[i + 1];
            
            // invalid scenario
            // cur: wrtfabc, next: wrtf - IMPOSSIBLE order
            if (cur.length() > nex.length() && cur.startsWith(nex)) { // I FORGOT
                return "";
            }
            
            int min = Math.min(cur.length(), nex.length());
            
            // find the first different character by doing a linear scan from left to right
            for (int j = 0; j < min; j++) {
                char c1 = cur.charAt(j);
                char c2 = nex.charAt(j);
                
                // the first different character
                if (c1 != c2) {
                    Set<Character> set = adj.computeIfAbsent(c1, k -> new HashSet<>());
                    
                    if (!set.contains(c2)) {
                        set.add(c2);
                        indegree.put(c2, indegree.get(c2) + 1);
                    }
                    break; // I FORGOT.....
                }
            }
        }
        
        Queue<Character> queue = new LinkedList<>();
        for (Map.Entry<Character, Integer> entry : indegree.entrySet()) {
            if (entry.getValue() == 0) {
                queue.offer(entry.getKey());
            }
        }
        
        StringBuilder sb = new StringBuilder();
        while (!queue.isEmpty()) {
            char ch = queue.poll(); 
            sb.append(ch);
            
            // this node does not have any out coming edge
            if (!adj.containsKey(ch)) {
                continue;
            }
            
            Set<Character> adjacentNodes = adj.get(ch);
            for (char adjacentNode : adjacentNodes) {
                int count = indegree.get(adjacentNode) - 1;
                indegree.put(adjacentNode, count);
                if (count == 0) {
                    queue.offer(adjacentNode);
                }
            }
        }
        
        // invalid scenario
        // the graph is NOT an acyclic graph and there is some cycle in there.
        // x, y, x
        if (sb.length() < indegree.size()) {
            return "";
        }
        
        return sb.toString();
    }
}

Course Schedule I - return boolean if any cycle
class Solution {
    public boolean canFinish(int numCourses, int[][] prerequisites) {
        if (numCourses <= 0) {
            return false;
        }
        if (prerequisites == null || prerequisites.length == 0 || prerequisites[0].length == 0) {
            return true; 
        }
        
        Map<Integer, Set<Integer>> adj = new HashMap<>();
        Map<Integer, Integer> indegree = new HashMap<>();
        
        for (int i = 0; i < numCourses; i++) {
            indegree.put(i, 0);
        }
        
        for (int[] p : prerequisites) {
            adj.computeIfAbsent(p[1], k -> new HashSet<>()).add(p[0]);
            indegree.put(p[0], indegree.get(p[0]) + 1);
        }
        
        Queue<Integer> queue = new LinkedList<>();
        for (Map.Entry<Integer, Integer> entry : indegree.entrySet()) {
            if (entry.getValue() == 0) {
                queue.offer(entry.getKey());
            }
        }
        
        int count = 0;
        while (!queue.isEmpty()) {
            int cur = queue.poll();
            count++;
            
            // // easy to forget........
            if (adj.get(cur) == null) {
                continue;
            }
            
            for (Integer next : adj.get(cur)) {
                int in = indegree.get(next) - 1;
                indegree.put(next, in); // easy to forget........
                if (in == 0) {
                    queue.offer(next);
                }
            }
        }
        
        return count == numCourses;
    }
}


Course Schedule II - return a list
class Solution {
    // [0,1]: take course 0 you have to first take course 1    
    public int[] findOrder(int numCourses, int[][] prerequisites) {
        // if prerequisites is empty, indicates no dependencies between courses
        // we still can get a ordered sequence of courses
        if (numCourses <= 0 || prerequisites == null) {
            return new int[0];
        }
    
        Map<Integer, Set<Integer>> adj = new HashMap<>();
        Map<Integer, Integer> indegree = new HashMap<>();
        
        // indegree must be initialized first and for ALL elements!
        for (int i = 0; i < numCourses; i++) {
            indegree.put(i, 0);
        }
        
        for (int[] p : prerequisites) {
            adj.computeIfAbsent(p[1], k -> new HashSet<>()).add(p[0]);
            indegree.put(p[0], indegree.getOrDefault(p[0], 0) + 1);
        }
        
        Queue<Integer> queue = new LinkedList<>();
        for (Map.Entry<Integer, Integer> entry : indegree.entrySet()) {
            if (entry.getValue() == 0) {
                queue.offer(entry.getKey());
            }
        }
        
        int[] res = new int[numCourses];
        int index = 0;
        while (!queue.isEmpty()) {
            int cur = queue.poll();
            res[index++] = cur;

            // easy to forget........
            if (adj.get(cur) == null) {
                continue;
            }
            
            for (int next : adj.get(cur)) {
                int in = indegree.get(next) - 1;
                indegree.put(next, in); // easy to forget........
                
                if (in == 0) {
                    queue.offer(next);
                }
            }
        }
        
        if (index != numCourses) {
            return new int[0]; // cycle inside
        }
        
        return res;
    }
}
BUCKET SORT

class Solution {
    // bucket sort...
    public List<Integer> topKFrequent(int[] nums, int k) {
        List<Integer> res = new ArrayList<>();
        if (nums == null || nums.length == 0 || k <= 0) {
            return res;
        }
        
        Map<Integer, Integer> freqs = new HashMap<>();
        for (int num : nums) {
            freqs.put(num, freqs.getOrDefault(num, 0) + 1);
        }
        
        // nums.length + 1 !
        // length is used as index
        List<Integer>[] buckets = new List[nums.length + 1];
        for (Map.Entry<Integer, Integer> entry : freqs.entrySet()) {
            int freq = entry.getValue();
            if (buckets[freq] == null) {
                buckets[freq] = new ArrayList<>();
            }
            
            buckets[freq].add(entry.getKey());
        }

        for (int i = nums.length; i >= 1 && k > 0; i--) { // k > 0: early terminate!
            if (buckets[i] == null) {
                continue;
            }
            
            for (int j = 0; j < buckets[i].size() && k > 0; j++) {
                res.add(buckets[i].get(j));
                k--;
            }
        }
        
        return res;
    }
}

QUICK SELECT

Kth Largest Element in an Array
O(N) best case - N + N/2 + N/4 + ...... -> 2N -> N
O(N^2) worst case running time 
O(1) memory
class Solution {
    public int findKthLargest(int[] nums, int k) {
        if (nums == null || nums.length == 0 || k <= 0) {
            return 0;
        }
        
        int target = nums.length - k;
        
        int start = 0, end = nums.length - 1;
        
        // binary search + quick select
        while (start <= end) {
            int position = partition(nums, start, end);
            if (position == target) {
                return nums[position];
            } else if (position > target) {
                end = position - 1;
            } else {
                start = position + 1;
            }
        }
        
        return nums[start]; // kth largest not found
    }
    
    private int partition(int[] nums, int start, int end) {
        int pivot = start;
        while (start <= end) {
            while (start <= end && nums[start] <= nums[pivot]) {
                start++;
            }
            while (start <= end && nums[end] > nums[pivot]) {
                end--;
            }
            
            if (start <= end) {
                swap(nums, start, end);
            }
        }
        
        // switch pivot and end
        swap(nums, pivot, end);
        return end;
    }
    
    private void swap(int[] nums, int a, int b) {
        int temp = nums[a];
        nums[a] = nums[b];
        nums[b] = temp;
    }
}
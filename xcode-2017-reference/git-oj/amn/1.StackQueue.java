// Largest Rectangle in Histogram
class Solution {
    public int largestRectangleArea(int[] heights) {
        if (heights == null || heights.length == 0) {
            return 0;
        }
        
        int len = heights.length;
        Stack<Integer> s = new Stack<>(); // non-decreasing stack
        int maxArea = 0;
        for (int i = 0; i <= len; i++) {
            int h = (i == len) ? 0 : heights[i];
            if (s.isEmpty() || h >= heights[s.peek()]) { // >=
                s.push(i);
            } else {
                int top = s.pop();
                // if s is empty, width = [0...i-1] -> i
                // if s is not empty, width = [s.peek() + 1 ... i-1] -> i-1 - s.peek()
                maxArea = Math.max(maxArea, heights[top] * (s.isEmpty() ? i : i - 1 - s.peek()));
                i--;
            }
        }
        return maxArea;
    }
}

Implement Queue using Stacks

// best solution is use temp in push instead of pop
class MyQueue {
    private Stack<Integer> stack;
    
    public MyQueue() {
        stack = new Stack<>();
    }
    
    /** Push element x to the back of queue. */
    public void push(int x) {
        Stack<Integer> temp = new Stack<>();
        while (!stack.isEmpty()) {
            temp.push(stack.pop());
        }
        stack.push(x);
        while (!temp.isEmpty()) {
            stack.push(temp.pop());
        }
    }
    
    /** Removes the element from in front of queue and returns that element. */
    public int pop() {
        return stack.pop();
    }
    
    /** Get the front element. */
    public int peek() {
        return stack.peek();
    }
    
    /** Returns whether the queue is empty. */
    public boolean empty() {
        return stack.isEmpty();
    }
}

Top K Frequent Words

class Solution {
    
    public List<String> topKFrequent(String[] words, int k) {
        if (words == null || words.length == 0) {
            return Collections.emptyList();
        }
        
        Map<String, Integer> freq = new HashMap<>();
        for (String word : words) {
            freq.put(word, freq.getOrDefault(word, 0) + 1);
        }
        
        
        // min-heap
        PriorityQueue<Map.Entry<String, Integer>> pq = new PriorityQueue<>((a, b) -> a.getValue() == b.getValue() ?
                                                    b.getKey().compareTo(a.getKey()) : a.getValue() - b.getValue());
        
        for (Map.Entry<String, Integer> entry : freq.entrySet()) {
            pq.offer(entry);
            
            if (pq.size() > k) { // get out of the min element
                pq.poll();    
            }
        }
        
        List<String> res = new LinkedList<>();
        while(!pq.isEmpty())
            res.add(0, pq.poll().getKey());
        
        return res;
    }
    
    // time: nk + nklogk
    private List<String> bucketSort(String[] words, int k) {
        if (words == null || words.length == 0) {
            return Collections.emptyList();
        }
        
        Map<String, Integer> freq = new HashMap<>();
        for (String word : words) {
            freq.put(word, freq.getOrDefault(word, 0) + 1);
        }
        
        int n = words.length;
        List<String>[] buckets = new List[n + 1];
        for (Map.Entry<String, Integer> entry : freq.entrySet()) {
            int f = entry.getValue();
            if (buckets[f] == null) {
                buckets[f] = new ArrayList<>();
            }
            buckets[f].add(entry.getKey());
        }
        for (int i = n; i > 0; i--) {
            if (buckets[i] == null) {
                continue;
            }
            Collections.sort(buckets[i]);
        }
        
        List<String> res = new ArrayList<>(k);
        for (int i = n; i > 0; i--) {
            if (buckets[i] == null) {
                continue;
            }
            
            for (int j = 0; k > 0 && j < buckets[i].size(); j++, k--) {
                res.add(buckets[i].get(j));
            }
        }
        return res;
    }
}

Simplify Path

class Solution {
    public String simplifyPath(String path) {
        if (path == null || path.length() == 0) {
            return "";
        }
        
        Stack<String> stack = new Stack<>();
        String[] parts = path.split("/");
        for (String part : parts) {
            if (part.isEmpty() || part.equals(".")) {
                continue;
            }
            
            if (part.equals("..")) {
                if (!stack.isEmpty()) {
                    stack.pop();    
                }
                continue;
            }
            
            stack.push(part);
        }
        
        StringBuilder sb = new StringBuilder();
        for (String item : stack) {
            sb.append("/").append(item);
        }
        
        String res = sb.toString();
        return res.isEmpty() ? "/" : res;
    }
}

Kth Largest Element in a Stream

class KthLargest {
    private int k;
    private PriorityQueue<Integer> pq;
    
    public KthLargest(int k, int[] nums) {
        this.k = k;
        this.pq = new PriorityQueue<>(); // min-heap
        for (int num : nums) {
            add(num);
        }
    }
    
    public int add(int val) {
        pq.offer(val);
        
        if (pq.size() > k) {
            pq.poll();
        }
        
        return pq.peek();
    }
}


non-increasing stack

class Solution {
    public int[] nextGreaterElement(int[] nums1, int[] nums2) {
        if (nums1 == null || 
            nums1.length == 0 ||
            nums2 == null || 
            nums2.length == 0) {
            return new int[]{}; // ?
        }
        
        int[] res = new int[nums1.length];
        
        // map value to value
        // value in nums2 -> next greater element in nums2 using non-increasing stack
        Map<Integer, Integer> map = new HashMap<>(); 
        Stack<Integer> stack = new Stack<>();
        
        for (int num : nums2) {
            while (!stack.isEmpty() && stack.peek() < num) {
                map.put(stack.pop(), num);
            }
            stack.push(num);
        }
        
        for (int i = 0; i < nums1.length; i++) {
            res[i] = map.getOrDefault(nums1[i], -1);
        }
        
        return res;
    }
}

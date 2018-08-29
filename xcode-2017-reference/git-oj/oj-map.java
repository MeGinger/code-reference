// K-diff Pairs in an Array
/*
Given an array of integers and an integer k, you need to find the number of unique k-diff pairs in the array. Here a k-diff pair is defined as an integer pair (i, j), where i and j are both numbers in the array and their absolute difference is k.

Example 1:
Input: [3, 1, 4, 1, 5], k = 2
Output: 2
Explanation: There are two 2-diff pairs in the array, (1, 3) and (3, 5).
Although we have two 1s in the input, we should only return the number of unique pairs.

Example 2:
Input:[1, 2, 3, 4, 5], k = 1
Output: 4
Explanation: There are four 1-diff pairs in the array, (1, 2), (2, 3), (3, 4) and (4, 5).

Example 3:
Input: [1, 3, 1, 5, 4], k = 0
Output: 1
Explanation: There is one 0-diff pair in the array, (1, 1).
*/

class Solution {
    public int findPairs(int[] nums, int k) {
        if (nums == null || nums.length == 0 || k < 0) {
            return 0;
        }
        
        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            map.put(nums[i], map.getOrDefault(nums[i], 0) + 1);
        }
    
        int count = 0; // for unique pairs
        for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
            if (k == 0) {
                if (entry.getValue() >= 2) {
                    count++;
                }
            } else {
                // either way is fine..
                // both way adopted will make result double
                
                //if (map.containsKey(entry.getKey() + k)) {
                //    count++;
                //} 
                if (map.containsKey(entry.getKey() - k)) {
                    count++;
                }
            }
        }
        
        return count;
    }
}

// Sort Characters By Frequency
// Given a string, sort it in decreasing order based on the frequency of characters.
// 
class Solution {
    // priority queue: O(nlogn)
    public String frequencySort(String s) {
        Map<Character, Integer> map = new TreeMap<>();

        for (char c : s.toCharArray()) {
            int freq = map.getOrDefault(c, 0) + 1;
            map.put(c, freq);
        }

        PriorityQueue<Map.Entry<Character, Integer>> pq = new PriorityQueue<>(
                new Comparator<Map.Entry<Character, Integer>>() {
                    @Override
                    public int compare(Map.Entry<Character, Integer> a, Map.Entry<Character, Integer> b) {
                        return b.getValue() - a.getValue();
                    }
                });

        pq.addAll(map.entrySet());
        StringBuilder sb = new StringBuilder();
        while (!pq.isEmpty()) {
            Map.Entry<Character, Integer> e = pq.poll();
            for (int i = 0; i < e.getValue(); i++) {
                sb.append(e.getKey());
            }
        }

        return sb.toString();
    }

    // bucket sort
    public String frequencySort(String s) {
        Map<Character, Integer> map = new HashMap<>();
        for (char ch : s.toCharArray()) {
            int freq = map.getOrDefault(ch, 0) + 1;
            map.put(ch, freq);
        }
        
        
        // size should be length + 1
        List<Character>[] buckets = new List[s.length() + 1]; 
        for (Map.Entry<Character, Integer> entry : map.entrySet()) {
            int freq = entry.getValue();
            if (buckets[freq] == null) {
                buckets[freq] = new ArrayList<>();
            }
            buckets[freq].add(entry.getKey());
        }
        
        StringBuilder sb = new StringBuilder();
        for (int i = buckets.length - 1; i >= 0; i--) {
            if (buckets[i] == null) {
                continue;
            }
            
            for (int j = 0; j < buckets[i].size(); j++) {
                for (int k = 0; k < i; k++) {
                    sb.append(buckets[i].get(j));
                }
            }
        }
        
        return sb.toString();
    }
}
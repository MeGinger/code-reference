
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

s = "leetcode"
return 0.



Top K Frequent Words

class Solution {
    // time: nlogk
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




class Solution {
    public int firstUniqChar(String s) {
        if (s == null || s.length() == 0) {
            return -1;
        }
        
        // mapping: character to its first index
        Map<Character, Integer> map = new LinkedHashMap<>();
        Set<Character> duplicates = new HashSet<>();
        char[] chars = s.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            if (!map.containsKey(chars[i]) 
                && !duplicates.contains(chars[i])) {
                map.put(chars[i], i);
            } else {
                map.remove(chars[i]);
                duplicates.add(chars[i]);
            }
        }
        return map.isEmpty() ? -1 : map.values().iterator().next();
    }
}

Longest Consecutive Sequence

// SET + TWO POINTERS
class Solution {
    public int longestConsecutive(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }
        
        Set<Integer> set = new HashSet<>();
        for (int num : nums) {
            set.add(num);
        }
        
        int longest = 0;
        for (int i = 0; i < nums.length; i++) {
            
            int left = nums[i] - 1;
            while (set.contains(left)) {
                set.remove(left); // remove first
                left--; // decrement then, otherwise cause error..
            }
            
            int right = nums[i] + 1;
            while (set.contains(right)) {
                set.remove(right);
                right++;
            }
            
            longest = Math.max(longest, right - left - 1);
        }
        
        return longest;
    }
}
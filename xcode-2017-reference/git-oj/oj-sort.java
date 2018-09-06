    // https://leetcode.com/problems/kth-largest-element-in-an-array/description/
    /*

    */ 

    // QUICK SELECT
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
        return nums[start]; // not found

    }

    // left | pivot | right
    // nums in the left  <= nums[pivot]
    // nums in the right >  nums[pivot]

    quick select

    nums[pivot] = 3, pivot  = 0

    3 0 2 8 4 6 9 5
    p   e s    

    nums[s] >  nums[pivot] 
    nums[e] <= nums[pivot]

    pivot = 0, unchanged

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
        
        // end start: adjacent indices
        // nums[end] <= nums[pivot] < nums[start]
        // so swap end and pivot
        swap(nums, end, pivot);
        return end; // return end !!!! 
                    // pivot is the original start...

    }

    private void swap(int[] nums, int a, int b) {
        int temp = nums[a];
        nums[a] = nums[b];
        nums[b] = temp;
    }


// Alien Dictionary 
/*
There is a new alien language which uses the latin alphabet. However, the order among letters are unknown to you. You receive a list of non-empty words from the dictionary, where words are sorted lexicographically by the rules of this new language. Derive the order of letters in this language.

Example 1:

Input:
[
  "wrt",
  "wrf",
  "er",
  "ett",
  "rftt"
]

Output: "wertf"
Example 2:

Input:
[
  "z",
  "x"
]

Output: "zx"
Example 3:

Input:
[
  "z",
  "x",
  "z"
] 

Output: "" 

Explanation: The order is invalid, so return "".
Note:

You may assume all letters are in lowercase.
You may assume that if a is a prefix of b, then a must appear before b in the given dictionary.
If the order is invalid, return an empty string.
There may be multiple valid order of letters, return any one of them is fine.

Step 1, build a degree map for each character in all the words:

w:0
r:0
t:0
f:0
e:0

Step 2, build the hashmap by comparing the adjacent words, the first character that is different between two adjacent words reflect the lexicographical order. For example:

 "wrt",
 "wrf",
    first different character is 3rd letter, so t comes before f

 "wrf",
 "er",
    first different character is 1rd letter, so w comes before e

The characters in set come after the key. x->y means letter x comes before letter y. x -> set: y,z,t,w means x comes before all the letters in the set. The final HashMap "map" looks like.

t -> set: f    
w -> set: e
r -> set: t
e -> set: r

Step 3. HashMap "degree" looks like, the number means "how many letters come before the key":

w:0
r:1
t:1
f:1
e:1

Step 4. use Karn's aglorithm to do topological sort. This is essentially BFS.
https://en.wikipedia.org/wiki/Topological_sorting

*/


// Topological Sorting - BFS using Queue
class Solution {
    public String alienOrder(String[] words) {
        if (words == null || words.length == 0) {
            return "";
        }
        
        Map<Character, Set<Character>> map = new HashMap<>();
        Map<Character, Integer> degree = new HashMap<>(); // in-degree
        
        StringBuilder result = new StringBuilder();
        
        for(String word : words) {
            for (int i = 0; i < word.length(); i++) {
                degree.put(word.charAt(i), 0);
            }
        }
        
        // compare adjacent words and compute indegree map
        for (int i = 0; i < words.length - 1; i++) {
            String cur = words[i];
            String next = words[i + 1];
            
            // "wrtkj", "wrt"
            if (cur.length() > next.length() && cur.startsWith(next)) {
                return "";
            }
            
            int length = Math.min(cur.length(), next.length());
            for (int j = 0; j < length; j++) {
                char c1 = cur.charAt(j);
                char c2 = next.charAt(j);
                if (c1 != c2) {
                    Set<Character> set = map.computeIfAbsent(c1, 
                                            k -> new HashSet<>());
                    if (set.add(c2)) {
                        degree.put(c2, degree.get(c2) + 1);
                    }
                    break;
                }
            }
        }
        
        // topological sort
        Queue<Character> q = new LinkedList<>();
        for (char c : degree.keySet()) {
            if (degree.get(c) == 0) {
                q.add(c);
            }
        }
        while (!q.isEmpty()) {
            char c = q.poll();
            result.append(c);

            if (!map.containsKey(ch)) {
                continue;
            }
            
            Set<Character> chars = map.get(c);
            for (char c2 : chars) {
                int count = degree.get(c2) - 1;
                degree.put(c2, count);
                if (count == 0) {
                    q.add(c2);
                }
            }
        }

        // CANNOT determine order for some characters in the dictionary
        // input is not a acyclic graph !!!
        if (result.length() != degree.size()) { // <
           return "";
        }
        
        return result.toString();
    }
}


// Sequence Reconstruction
/*
Check whether the original sequence org can be uniquely reconstructed from the sequences in seqs. The org sequence is a permutation of the integers from 1 to n, with 1 ≤ n ≤ 104. Reconstruction means building a shortest common supersequence of the sequences in seqs (i.e., a shortest sequence so that all sequences in seqs are subsequences of it). Determine whether there is only one sequence that can be reconstructed from seqs and it is the org sequence.

Example 1:

Input:
org: [1,2,3], seqs: [[1,2],[1,3]]

Output:
false

Explanation:
[1,2,3] is not the only one sequence that can be reconstructed, because [1,3,2] is also a valid sequence that can be reconstructed.
Example 2:

Input:
org: [1,2,3], seqs: [[1,2]]

Output:
false

Explanation:
The reconstructed sequence can only be [1,2].
Example 3:

Input:
org: [1,2,3], seqs: [[1,2],[1,3],[2,3]]

Output:
true

Explanation:
The sequences [1,2], [1,3], and [2,3] can uniquely reconstruct the original sequence [1,2,3].
Example 4:

Input:
org: [4,1,5,2,6,3], seqs: [[5,2,6,3],[4,1,5,2]]

Output:
true
 */

class Solution {
    public boolean sequenceReconstruction(int[] org, List<List<Integer>> seqs) {
        Map<Integer, Set<Integer>> map = new HashMap<>();
        Map<Integer, Integer> degree = new HashMap<>();
        
        // mix initialization and connection handling
        // use computeIfAbsent for map and degree, otherwise (just use put for degree) cause overwrite
        for (List<Integer> seq : seqs) {
            if (seq.size() == 1) {
                // initialization
                map.computeIfAbsent(seq.get(0), 
                                    k -> new HashSet<>()); // update map
                degree.computeIfAbsent(seq.get(0), k -> 0); // update degree
            } else {
                for (int i = 0; i < seq.size() - 1; i++) {
                    // initialization
                    Set<Integer> set = map.computeIfAbsent(seq.get(i), 
                                        k -> new HashSet<>());
                    degree.computeIfAbsent(seq.get(i), k -> 0);
                    
                    map.computeIfAbsent(seq.get(i + 1),
                                        k -> new HashSet<>());
                    degree.computeIfAbsent(seq.get(i + 1), k -> 0);
                    
                    // connection
                    if (set.add(seq.get(i + 1))) {
                        degree.put(seq.get(i + 1), 
                                   degree.get(seq.get(i + 1)) + 1);
                    }
                }
            }
        }
        
        Queue<Integer> queue = new LinkedList<>();
        for (Map.Entry<Integer, Integer> entry : degree.entrySet()) {
            if (entry.getValue() == 0) {
                queue.offer(entry.getKey());
            }
        }
        
        int index = 0; // index of reconstructed sequence
        while (!queue.isEmpty()) {
            int size = queue.size(); 
            if (size > 1) {
                System.out.println(1);
                return false; 
                // in this loop, we have more than one choices    
            } 
            
            int curr = queue.poll();
            if (index == org.length || curr != org[index++]) {
                // generated sequence length more than org
                // OR the characters are different at index
                System.out.println(2);
                return false;
            }
            
            for (int next : map.get(curr)) {
                degree.put(next, degree.get(next) - 1);
                if (degree.get(next) == 0) {
                    queue.offer(next);
                }
            }
            
        }
        
        // in case generated sequence length less than org
        // AND in case more than one connected component
        System.out.println(3);
        return index == org.length && index == map.size();
    }
}


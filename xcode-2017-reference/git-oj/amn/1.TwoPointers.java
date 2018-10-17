// Longest Palindromic Substring

private int lo, maxLen;

public String longestPalindrome(String s) {
    int len = s.length();
    if (len < 2)
        return s;
    
    for (int i = 0; i < len - 1; i++) {
        extendPalindrome(s, i, i);   //assume odd length, try to extend Palindrome as possible
        extendPalindrome(s, i, i+1); //assume even length.
    }
    return s.substring(lo, lo + maxLen);
}

private void extendPalindrome(String s, int j, int k) {
    while (j >= 0 && k < s.length() && s.charAt(j) == s.charAt(k)) {
        j--;
        k++;
    }
    if (maxLen < k - j - 1) {
        lo = j + 1;
        maxLen = k - j - 1;
    }
}

// Trapping Rain Water

public int trap(int[] height) {
    if (height == null || height.length == 0) {
        return 0;
    }
    
    int left = 0;
    int right = height.length - 1;

    int leftHeight = height[left];
    int rightHeight = height[right];
    
    int ans = 0;
    while (left < right) { // left + 1 < right is ok as well
                           // since when left + 1 == right, last loop
                           // leftHight == rightHeight
        if (height[left] < height[right]) {
            left++;
            // update status of left 
            if (leftHeight > height[left]) {
                ans += leftHeight - height[left];
            } else{
                leftHeight = height[left];                     
            }
        } else {
            right--;
            // update status of right
            if (rightHeight > height[right]) {
                ans += rightHeight - height[right];
            } else {
                rightHeight = height[right];
            }
        }
    }
    
    return ans;
}

class Solution {
    public int maxArea(int[] height) {
        if (height == null || height.length == 0) {
            return 0;
        }
        
        int left = 0;
        int right = height.length - 1;
        
        int maxArea = 0;
        while (left < right) {
            int area = 0;
            if (height[left] < height[right]) {
                area = height[left] * (right - left);
                left++;
            } else {
                area = height[right] * (right - left);
                right--;
            }
            max = Math.max(max, area);
        }
        
        return maxArea;
    }
}



Group Anagrams
Time: nklogk
class Solution {
    public List<List<String>> groupAnagrams(String[] strs) {
        if (strs == null || strs.length == 0) {
            return Collections.emptyList();
        }
        
        List<List<String>> res = new ArrayList<>();
        Map<String, List<String>> map = new HashMap<>();
        
        for (String str : strs) {
            char[] chars = str.toCharArray();
            Arrays.sort(chars);
            String key = new String(chars);
            map.computeIfAbsent(key, k -> new ArrayList<>()).add(str);    
        }
        
        return new ArrayList<>(map.values());
    }
}

Find All Anagrams in a String
- sliding window - fixed size 
public List<Integer> findAnagrams(String s, String p) {
    List<Integer> res = new ArrayList<>();
    
    int left = 0, right = 0;
    int matchSize = p.length();
    int[] map = new int[256];
    
    for (char c : p.toCharArray()) {
        map[c]++;
    }
    
    char[] ch = s.toCharArray();
    while (right < s.length()) { // every loop, right move one step
        if (map[ch[right]] > 0) {
            matchSize--;
        }
        map[ch[right]]--;
        
        // sliding window: [left, right] - both are inclusive
        // check if requirements are met
        // left always move one step in this case 
        if (right - left == p.length() - 1) {
            if (matchSize == 0) {
                res.add(left);
            }
            
            if (map[ch[left]] >= 0) {
                matchSize++;
            }
            map[ch[left]]++;
            left++;
        }
        right++;
    }
    
    return res;
}


Valid Anagram
class Solution {
    public boolean isAnagram(String s, String t) {
        if (s == null || t == null) {
            return false;
        }
        
        int[] map = new int[26];
        for (char c : s.toCharArray()) {
            map[c - 'a']++;
        }
        
        for (char c : t.toCharArray()) {
            map[c - 'a']--;
        }
        
        for (int i = 0; i < 26; i++) {
            if (map[i] != 0) {
                return false;
            }
        }
        return true;
    }
}


public List<Integer> findAnagrams(String s, String p) {
    List<Integer> res = new ArrayList<>();
    
    int left = 0, right = 0;
    int counter = p.length();
    int[] map = new int[256];
    
    for (char c : p.toCharArray()) {
        map[c]++;
    }
    
    char[] ch = s.toCharArray();
    while (right < s.length()) {
        if (map[ch[right]] > 0) {
            counter--;
        }
        map[ch[right]]--;
        
        // adjust sliding window
        if (right - left == p.length()) {
           if (map[ch[left]] >= 0) {
                counter++;
            }
            map[ch[left]]++;
            left++;
        }
        
        // result update if requirements are satisfied
        // sliding window: [left, right] - both are inclusive
        if (right - left == p.length() - 1 && counter == 0) {
            res.add(left);
        }    
        right++;
    }
    
    return res;
}

Longest Substring Without Repeating Characters
Given a string, find the length of the longest substring without repeating characters.

class Solution {
    public int lengthOfLongestSubstring(String s) {
        if (s == null || s.length() == 0) {
            return 0;
        }
        
        int left = 0, right = 0;
        int res = 0;
        
        int[] map = new int[256];
        char[] ch = s.toCharArray();
        
        while (right < s.length()) { // every loop, right move one step
            map[ch[right]]++;
            
            // left moves to satifies the requirement
            while (map[ch[right]] > 1) {
                // adjust sliding window
                map[ch[left]]--;
                left++;
            }
            
            // result update
            res = Math.max(res, right - left + 1);
            right++;
        }
        return res;
    }
}

Minimum Window Substring

class Solution {
    public String minWindow(String s, String t) {
        if (s == null || t == null || s.length() < t.length()) {
            return "";
        }
        
        int[] map = new int[256];
        int counter = t.length();
        for (char c : t.toCharArray()) {
            map[c]++;
        }
        
        int left = 0, right = 0;
        String res = "";
        int min = Integer.MAX_VALUE;
        while (right < s.length()) {
            if (map[s.charAt(right)] > 0) {
                counter--;
            }
            map[s.charAt(right)]--;
            
            while (counter == 0) { // SHOULD BE while NOT if
                if (min > right - left + 1) {
                    min = right - left + 1;
                    res = s.substring(left, right + 1);
                }
                if (map[s.charAt(left)] >= 0) {
                    counter++;
                }
                map[s.charAt(left)]++;
                left++;
            }
            
            right++;
        }
        
        return res;
    }
}

Gas Station

class Solution {
    // Return the starting gas station's index 
    // if you can travel around the circuit once in the clockwise direction, 
    // otherwise return -1.
    
    // cost[i]: it costs cost[i] of gas to travel from station i to its next station (i+1).
    // gas[i]: the amount of gas at station i.
    public int canCompleteCircuit(int[] gas, int[] cost) {
        if (gas == null || cost == null) {
            return -1;
        }
        
        int end = 0, start = gas.length - 1;
        int sum = gas[start] - cost[start];
        while (end < start) {
            if (sum >= 0) {
                sum += gas[end] - cost[end];
                end++;
            } else { // we cannot move forward, so start is not our starting index, move start backwards
                start--;
                sum += gas[start] - cost[start];
            }               
        }
        return sum >= 0 ? start : -1;
    }
}









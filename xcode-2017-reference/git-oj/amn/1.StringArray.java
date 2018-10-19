Partition Labels
class Solution {
    public List<Integer> partitionLabels(String S) {
        if (S == null || S.length() == 0) {
            return Collections.emptyList();
        }
        
        int[] last = new int[26];
        for (int i = 0; i < S.length(); i++) {
            last[S.charAt(i) - 'a'] = i;
        }
        
        int right = 0;
        int left = 0;
        List<Integer> res = new ArrayList<>();
        for (int i = 0; i < S.length(); i++) {
            right = Math.max(right, last[S.charAt(i) - 'a']);
            
            if (i == right) {
                res.add(right - left + 1);
                left = i + 1;
            }
        }
        
        return res;
    }
}

Compare Version Numebrs
class Solution {
    public int compareVersion(String version1, String version2) {
        if (version1 == null || version2 == null) {
            return 0; // ?    
        }
        
        String[] parts1 = version1.split("\\.");
        String[] parts2 = version2.split("\\.");
        for (int i = 0; i < parts1.length || i < parts2.length; i++) {
            Integer a = i < parts1.length ? Integer.parseInt(parts1[i]) : 0;
            Integer b = i < parts2.length ? Integer.parseInt(parts2[i]) : 0;
            
            int compare = a.compareTo(b);
            if (compare != 0) {
                return compare;
            }
        }
        
        return 0;
    }
}

Reverse Words in a String

public class Solution {
    public String reverseWords(String s) {
        if (s == null || s.length() == 0) {
            return "";
        }
        
        s = s.trim().replaceAll("\\s+", " ");
        
        char[] ch = s.toCharArray();
        reverse(ch, 0, s.length() - 1);
        
        int start = 0;
        int end = 0;
        for (int i = 0; i < ch.length; i++) {
            if (ch[i] == ' ') { // handle prev case
                reverse(ch, start, end);
                start = i + 1;
            }
            end = i;
        }
        reverse(ch, start, end); // I FORGOT last one
        return new String(ch);
    }
    
    private void reverse(char[] ch, int start, int end) {
        if (start > end) {
            return;
        }
        
        for (int i = start, j = end; i < j; i++, j--) {
            char temp = ch[i];
            ch[i] = ch[j];
            ch[j] = temp;    
        }
    }
}

Reverse Words in a String II

class Solution {
    public void reverseWords(char[] str) {
        if (str == null || str.length == 0) {
            return;
        }
        
        reverse(str, 0, str.length - 1);
        
        int s = 0, e = 0;

        // invariant: i == e !!!
        for (int i = 0; i <= str.length; i++) { // invariant: i == e
            if (i == str.length || str[i] == ' ') { // i == str.length placed at first 
                                                    // to avoid ArrayIndexOutOfBoundException
                reverse(str, s, e - 1);
                s = e + 1;
            }
            e++;
        }

        for (int i = 0; i < str.length; i++) { 
            if (str[i] == ' ') {
                reverse(str, s, e - 1);
                s = i + 1;
            }
            e++;
        }
        reverse(str, s, e - 1);
    }
    
    private void reverse(char[] str, int s, int e) {
        for (int i = s, j = e; i < j; i++, j--) {
            char temp = str[i];
            str[i] = str[j];
            str[j] = temp;
        }
    }
}


Merge Sorted Array

class Solution {
    public void merge(int[] nums1, int m, int[] nums2, int n) {
        int index = m + n - 1;

        int idx1 = m - 1;
        int idx2 = n - 1;
        while (idx1 >= 0 || idx2 >= 0) {
            int num1 = idx1 >= 0 ? nums1[idx1] : Integer.MIN_VALUE;
            int num2 = idx2 >= 0 ? nums2[idx2] : Integer.MIN_VALUE;
            
            if (num1 > num2) {
                nums1[index--] = num1;
                idx1--;
            } else {
                nums1[index--] = num2;
                idx2--;
            }
        }
    }
}

Jump Game

class Solution {
    public boolean canJump(int[] nums) {
        if (nums == null || nums.length == 0) {
            return false; // tech 
        }
        
        int farthest = nums[0]; // the farthest index I can reach from start so far
        for (int i = 1; i < nums.length; i++) {
            if (i <= farthest && nums[i] + i > farthest) {
                farthest = nums[i] + i; // find farthest every time
            }
        }
        
        return farthest >= nums.length - 1;
    }
}

Exclusive Time of Functions
class Solution {
    public int[] exclusiveTime(int n, List<String> logs) {
        if (n <= 0 || logs == null || logs.size() == 0) {
            return new int[0];
        }
        
        Stack<Integer> stack = new Stack<>(); // only push start timestamp
        int[] res = new int[n];
        int prevTime = 0; // start time for the running task
        for (String log : logs) {
            String[] parts = log.split(":");
            int time = Integer.parseInt(parts[2]);
            int id = Integer.parseInt(parts[0]);
            
            // stack.peek() is currently using CPU..
            if (!stack.isEmpty()) {
                res[stack.peek()] += time - prevTime;
            }
            prevTime = time;
            
            if (parts[1].equals("start")) {
                stack.push(id);
            } else {
                // 1. top task is finished 
                // 2. time is increment by 1, since end means the end of this time unit, so plus 1
                res[stack.pop()]++; //
                prevTime++; // start time for the next running task
            }
        }
        return res;
    }
}

Find the Duplicate Number

class Solution {
    public int findDuplicate(int[] nums) {
        if (nums == null || nums.length <= 1) {
            return 0;
        }
        
        int slow = nums[0]; // index as position NOT value
        int fast = nums[nums[0]]; // index as position NOT value
        while (slow != fast) {
            slow = nums[slow];
            fast = nums[nums[fast]];
        }

        // only the entry point has two incoming edges 
        slow = 0;
        int i = 0;
        while (slow != fast) {
            slow = nums[slow];
            fast = nums[fast];
        }
        
        return slow; // index of entry point is duplicate, NOT value
    }
}

License Key Formatting

class Solution {
    public String licenseKeyFormatting(String S, int K) {
        StringBuilder sb = new StringBuilder();
        
        // scan from right to left 
        for (int i = S.length() - 1; i >= 0; i--) {
            if (S.charAt(i) == '-') {
                continue;
            }
            
            // if sb.length() % (K + 1) == K, append "-", so length mod reset to 0
            sb.append(sb.length() % (K + 1) == K ? "-" : "").append(S.charAt(i));
        }
        
        return sb.reverse().toString().toUpperCase();
    }
}

Remove Element
class Solution {
    public int removeElement(int[] nums, int val) {
        if (nums == null || nums.length == 0) {
            return 0;
        }
        
        int left = 0; // left is the next placehold to put into val
        for (int right = 0; right < nums.length; right++) { // right is to find val
            if (nums[right] != val) {
                swap(nums, left, right);
                left++;
            }
        }
        
        return left; 
    }
    
    private void swap(int[] nums, int a, int b) {
        int temp = nums[a];
        nums[a] = nums[b];
        nums[b] = temp;
    }
}

Remove Duplicates from Sorted Array
class Solution {
    public int removeDuplicates(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }
        
        int left = 0;
        for (int right = 0; right < nums.length; right++) {
            if (nums[right] != nums[left]) {
                nums[++left] = nums[right];
            }
        }
        
        return left + 1;
    }
}




Longest Common Prefix

class Solution {
    // sort string array and compare first and last element in sorted array
    public String longestCommonPrefix(String[] strs) {
        if (strs == null || strs.length == 0) {
            return "";
        }
        
        Arrays.sort(strs);
        
        char[] a = strs[0].toCharArray();
        char[] b = strs[strs.length - 1].toCharArray();
        
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < Math.min(a.length, b.length); i++) {
            if (a[i] != b[i]) {
                break;
            } 
            sb.append(a[i]);
        }
        
        return sb.toString();
    }
}


Implement strStr() in C or indexOf() in Java

class Solution {
    public int strStr(String haystack, String needle) {
        for (int i = 0; ; i++) { // possible start index
            for (int j = 0; ; j++) { // index for needle
                if (j == needle.length()) {
                    return i;
                }
                
                if (i + j == haystack.length()) {
                    return -1;
                }
                
                if (needle.charAt(j) != haystack.charAt(i + j)) {
                    break;
                }
            }
        }
    }
}
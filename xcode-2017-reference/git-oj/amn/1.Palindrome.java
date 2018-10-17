Palindromic Substrings

The substrings with different start indexes or end indexes are counted as different substrings even they consist of same characters.

Input: "aaa"
Output: 6
Explanation: Six palindromic strings: "a", "a", "a", "aa", "aa", "aaa".

class Solution {

    // very similar to longest palindrome substring
    public int countSubstrings(String s) {
        if (s == null || s.length() == 0) {
            return 0;
        }
        
        int count = 0;
        for (int i = 0; i < s.length(); i++) {
            count += extendPalindrome(s, i, i);
            count += extendPalindrome(s, i, i + 1);
        }
        
        return count;
    }
    
    private int extendPalindrome(String s, int left, int right) {
        int count = 0;
        while (left >= 0 && right < s.length() && s.charAt(left) == s.charAt(right)) {
            count++;
            left--;
            right++;
        }
        return count;
    }
}

Find the Closest Palindrome

find the closest integer (not including itself), which is a palindrome.
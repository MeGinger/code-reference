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
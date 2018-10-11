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
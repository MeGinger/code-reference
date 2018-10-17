Restore IP Addresses

Input: "25525511135"
Output: ["255.255.11.135", "255.255.111.35"]

class Solution {
    // ranging from 0 to 255
    public List<String> restoreIpAddresses(String s) {
        if (s == null || s.length() == 0) {
            return Collections.emptyList();
        }
        List<String> res = new ArrayList<>();
        int len = s.length(); 
        // i < len - 2: ensure each part we can have one digit at least
        for (int i = 1; i < 4 && i < len - 2; i++) {
            for (int j = i + 1; j < i + 4 && j < len - 1; j++) {
                for (int k = j + 1; k < j + 4 && k < len; k++) {
                    String s1 = s.substring(0, i);
                    String s2 = s.substring(i, j);
                    String s3 = s.substring(j, k);
                    String s4 = s.substring(k, len);
                    if (isValid(s1) && isValid(s2) && isValid(s3) && isValid(s4)) {
                        res.add(s1 + "." + s2 + "." + s3 + "." + s4);
                    }
                }
            }
        }
        return res;
    }
    
    private boolean isValid(String s) {
        // 0 ~ 255
        if (s.length() <= 0 || s.length() > 3 ||  // length out of boundary
            (s.charAt(0) == '0' && s.length() > 1) ||  // starts with 0 except for 0, like 02
            Integer.parseInt(s) > 255) { // value larger than 255
            return false;
        }
        
        return true;
    }
}
Word Break
class Solution {
    public boolean wordBreak(String s, List<String> wordDict) {
        if (s == null || s.length() == 0 ||
            wordDict == null || wordDict.size() == 0) {
            return false; // when wordDict is empty - not breakable
        }
        
        Set<String> dict = new HashSet<>(wordDict);
        List<Integer> breakableIndex = new ArrayList<>();
        
        for (int i = 0; i < s.length(); i++) {
            if (dict.contains(s.substring(0, i + 1))) {
                breakableIndex.add(i); // substring: 0...i is breakable
                continue;
            }
            
            // all index < i - since linear scan from left to right
            for (Integer index : breakableIndex) {
                if (dict.contains(s.substring(index + 1, i + 1))) {
                    breakableIndex.add(i);
                    break;
                }
            }
        }
        
        return !breakableIndex.isEmpty() && 
            breakableIndex.get(breakableIndex.size() - 1) == s.length() - 1;
    }
}
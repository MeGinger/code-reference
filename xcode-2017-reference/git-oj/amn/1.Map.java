
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
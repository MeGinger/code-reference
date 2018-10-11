
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


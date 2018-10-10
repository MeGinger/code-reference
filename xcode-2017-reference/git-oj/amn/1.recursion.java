
// recursion
public List<String> wordBreak2(String s, Set<String> dict) {
    return breakIntoWordList(s, dict, new HashMap<>());
}

// What method breakIntoWordList does?
// return a sentence list where each sentence is constructed by valid dictionary words seperated by space

// memorized (
//   key: string, 
//   value: a list of sentences (can be constructed into key string)
//          where each sentence is constructed by valid dictionary words seperated by space
// )
private breakIntoWordList(String s, Set<String> dict, Map<String, List<String>> memorized) {
    // result: a list of sentences
    // finally result is added to memorized along with s
    List<String> result = new ArrayList<>();
    if (s == null || s.length() == 0) {
        return result;
    }
    
    if (memorized.containsKey(s)) {
        return memorized.get(s);
    }
    
    for (int i = 1; i <= s.length(); i++){
        String prefix = s.substring(0, i);
        if (!dict.contains(prefix) {
            continue;
        }
        
        if (i == s.length()) {
            result.add(s); // the entire string is a valid word
            continue;
        }
        
        String suffix = s.substring(i);
        List<String> suffixWords = breakIntoWordList(suffix, dict, memorized);
        for (String suffixWord : suffixWords) {
            // !!!! if suffixWords.isEmpty(), nothing being added to results
            result.add(prefix + " " + suffixWord);
        }
    }
    
    memorized.put(s, result);
    return result;
}
// runtime: O(n^2) 
// best: O(n)
// example intput - all prefix are not dictionary words - no recursion happens
// s: "aaaaaaaaaaaaaaaaaa"
// dict: ["b"]

// worst: O(n^2)
// example intput - the first prefix in every recursion is a dicionary word 
// s: "aaaaaaaaaaaaaaaaaa"
// dict: ["a"]

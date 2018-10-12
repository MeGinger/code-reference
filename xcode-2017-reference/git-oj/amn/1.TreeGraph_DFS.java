Letter Combinations of a Phone Number

class Solution {
    // 0, 1 - nothing represented
    private static final String[] mappings = {
      "", "", "abc", "def", "ghi", 
      "jkl", "mno", "pqrs", "tuv", "wxyz"  
    };
    
    public List<String> letterCombinations(String digits) {
        if (digits == null || digits.length() == 0) {
            return Collections.emptyList();
        }
        
        List<String> res = new ArrayList<>();
        
        char[] ds = digits.toCharArray();
        dfs(ds, 0, new char[ds.length], res);
        return res;        
    }
    
    private void dfs(char[] digits, int index, char[] letters, List<String> res) {
        if (index == digits.length) {
            res.add(new String(letters));
            return;
        }
        
        String mapping = mappings[digits[index] - '0'];
        char[] choices = mapping.toCharArray();
        for (int i = 0; i < choices.length; i++) {
            letters[index] = choices[i];
            dfs(digits, index + 1, letters, res);
        }
    }
}

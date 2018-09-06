    /**
	Maze: 给个array,其中只有一格是9，其他格子是0或1，0表示此路不通，1表示可以走，判断从（0,0) 点开始上下左右移动能否找到这个是9的格子
     */
    public boolean maze(int[][] matrix) {
        if (matrix == null || matrix.length == 0 || matrix[0].length == 0) {
            return false;
        }
        if (matrix[0][0] == 9) {
            return true;
        }
        int m = matrix.length, n = matrix[0].length;
        Queue<int[]> queue = new LinkedList<int[]>();
        queue.offer(new int[] { 0, 0 });
        matrix[0][0] = 1;
        while (!queue.isEmpty()) {
            int[] cur = queue.poll();
            for (int[] dir : dirs) {
                int x = cur[0] + dir[0];
                int y = cur[1] + dir[1];
                if (x >= 0 && x < m && y >= 0 && y < n) {
                    if (matrix[x][y] == 9) {
                        return true;
                    }
                    if (matrix[x][y] == 0) {
                        queue.offer(new int[] { x, y });
                        matrix[x][y] = 1;
                    }
                }
            }
        }
        return false;
    }



 /**
  * Subsets
    Given a set of distinct integers, nums, return all possible subsets.
    Note: The solution set must not contain duplicate subsets.
    For example,
    If nums = [1,2,3], a solution is:
    [
      [3],
      [1],
      [2],
      [1,2,3],
      [1,3],
      [2,3],
      [1,2],
      []
    ]
    */

    // recursion - dfs
    public List<List<Integer>> subsets(int[] nums) {
        List<List<Integer>> res = new ArrayList<>(1 << nums.length);
        Arrays.sort(nums);
        res.add(new ArrayList<>()); // empty list
        subsets(nums, new int[nums.length], 0, 0, res);
        return res;
    }
    
    // in: {1, 2, 3}
    
    // l:
    
    // {1} 
    // {1, 2}
    // {1, 2, 3}
    // {1, 3}
    
    // {2}
    // {2, 3}
    
    // {3} 
    private void subsets(int[] in, int[] out, int recurlen, int start,
       List<List<Integer>> res) {
       for (int i = start; i < in.length; i++) {
            out[recurlen] = in[i]; // 1
            List<Integer> l = new ArrayList<>(recurlen + 1);
            for (int j = 0; j <= recurlen; j++) {
                l.add(out[j]); // 1 
            }
            res.add(l); // 1
            if (i < in.length - 1) {
                subsets(in, out, recurlen + 1, i + 1, res);
            }
        }
    }

    // iterative
    public List<List<Integer>> subsets2(int[] nums) {
        Arrays.sort(nums);
        List<List<Integer>> ret = new ArrayList<>(1 << nums.length); // 2^num.length
        ret.add(new ArrayList<>()); // empty list []

        for (int i = 0; i < nums.length; i++) {
            int size = ret.size();
            for (int j = 0; j < size; j++) {
                List<Integer> sub = new ArrayList<>(ret.get(j));
                sub.add(nums[i]);
                ret.add(sub);
            }
        }

        // {}

        // LOOP: 1
        // {1}

        // LOOP: 2
        // {2}, {1, 2}

        // LOOP: 3
        // {3}, {1, 3}, {2, 3}, {1, 2, 3}
        return ret;
    }

    /**
      * Subsets II
      * Given a collection of integers that might contain duplicates,
      * S, return all possible subsets. Elements in a subset must be in
      * non-descending order. The solution set must not contain duplicate
      * subsets. If S = [1,2,2], a solution is: [ [2], [1], [1,2,2], [2,2],
      * [1,2], [] ]
      */

     // duplicate elements
     // non-descending order = ascending order
     public List<List<Integer>> subsetsWithDup(int[] nums) {
        Arrays.sort(nums);
        List<List<Integer>> ret = new ArrayList<>(1 << nums.length); // 2^num.length
        ret.add(new ArrayList<>()); // empty list []

        int start = 0;
        for (int i = 0; i < nums.length; i++) {
            int size = ret.size();
            for (int j = start; j < size; j++) {
                List<Integer> sub = new ArrayList<>(ret.get(j));
                sub.add(nums[i]);
                ret.add(sub);
            }

            // 克隆全部：     [], [1], [2], [1, 2] | [2], [1, 2], [2, 2], [1, 2, 2]
            // 只克隆后半部分：[], [1], [2], [1, 2] | [2, 2], [1, 2, 2]
            
            if (i < nums.length - 1 && nums[i + 1] == nums[i]) { // prepare for the next loop, so make sure i != nums.length - 1
                start = size; // 只克隆后半部分
            } else {
                start = 0;
            }
        }

        return ret;
     }


  /**
    * Expression Add Operators
     Given a string that contains only digits 0-9 and a target value,
     return all possibilities to add binary operators (not unary) +, -, or * between the digits so they evaluate to the target value.

     Examples:

     "123", 6 -> ["1+2+3", "1*2*3"] 
     "232", 8 -> ["2*3+2", "2+3*2"]
     "105", 5 -> ["1*0+5","10-5"]
     "00", 0 -> ["0+0", "0-0", "0*0"]
     "3456237490", 9191 -> []
    */

    public List<String> addOperators(String num, int target) {
        return dfs(new ArrayList<>(), num, new StringBuilder(), 0, 0, 0, target);
    }

    private List<String> dfs(List<String> result, String num, StringBuilder path, 
                             int start, long sum, long lastNum, int target) {
        int len = num.length();
        if (start == len && sum == target) {
            result.add(path.toString());
            return result;
        }
        long curNum = 0;
        for (int i = start; i < len; i++) {
            // 011 => "011", "0"
            //  si
            // 1111
            //    i
            // curNum = 1111
            if (i > start && num.charAt(start) == '0') {
                break;
            }
            curNum = curNum * 10 + num.charAt(i) - '0';
            int currentLen = path.length();
            if (start == 0) {
                dfs(result, num, path.append(curNum), i + 1, curNum, curNum, target);
                path.setLength(currentLen); // restore
            } else {
                dfs(result, num, path.append('+').append(curNum), i + 1, sum + curNum, curNum, target);
                path.setLength(currentLen);
                dfs(result, num, path.append('-').append(curNum), i + 1, sum - curNum, -curNum, target);
                path.setLength(currentLen);
                // 1 - 2 * 3
                // sum = -1 + 2 = 1 + (-2 * 3)
                // lastNum = -2
                dfs(result, num, path.append('*').append(curNum), i + 1, sum - lastNum + lastNum * curNum,
                        lastNum * curNum, target);
                path.setLength(currentLen); // restore
            }
        }
        return result;
    }

   /* 
    mock interview: FB problem - Cracking Password

    xcode => Xcode, xc0de, xcod3, xc0d3, Xc0d3

    x => X
    o => O, 0
    e => E, 3, 9

    "xcode space" => "xc0d3 Spac3"
    */

    private static final Map<Character, Set<Character>> map; // load it from configuration, or database

    public List<String> password(String s){
        //check input
        List<String> rst = new ArrayList<>();
        if (s == null || s.length() == 0) {
            return rst;
        }
        //dfs
        char[] tmp = new char[s.length()];
        dfs(s, tmp, 0, rst);
        return rst;
    }

    private void dfs(String s, char[] tmp, int index, List<String> rst){
        if (index == s.length()){
            rst.add(new String(tmp));
            return;
        }
        
        char c = s.charAt(index);
        tmp[index] = c;
        dfs(s, tmp, index + 1, rst); // original letter
        if (map.contains)
            // mapped letter
            for(char ch : map.get(c)){
                tmp[index] = ch;// overriding <=> restore
                dfs(s, tmp, index + 1, rst); 
                tmp[index] = c; // restore
            }
        
    }


    /**
     * Letter Combinations of a Phone Number 
     Given a digit string, return all possible letter combinations that the number could represent.

     A mapping of digit to letters (just like on the telephone buttons) is given below.

     Input:Digit string "23"
     Output: ["ad", "ae", "af", "bd", "be", "bf", "cd", "ce", "cf"].

     Note:
     Although the above answer is in lexicographical order, your answer could be in any order you want. 
     */
    // digits: 0, 1, 2, 3, 4, 5, 6, 7, 8, 9
    private static final char[][] table = { { ' ' }, {}, { 'a', 'b', 'c' }, { 'd', 'e', 'f' },
            { 'g', 'h', 'i' }, { 'j', 'k', 'l' }, { 'm', 'n', 'o' },
            { 'p', 'q', 'r', 's' }, { 't', 'u', 'v' },
            { 'w', 'x', 'y', 'z' } };

    public List<String> letterCombinations(String digits) {
        // DFS
        List<String> ret = new ArrayList<>();
        if (digits == null || digits.isEmpty()) {
            return ret;
        }
        letterCombinations(digits, 0, new char[digits.length()], ret);
        return ret;
    }
    
    // one letter maps to k letters including iteself
    // n is the length of digits
    
    1 2 3  4 ... n - 1, n
    k*k*k *k ...   k * k = k^n
    
    private void letterCombinations(String digits, int index, char[] ans, List<String> ret) {
        if (index == digits.length()) {
            ret.add(new String(ans));
            return;
        }

        int letterIndex = digits.charAt(index) - '0';
        for (int i = 0; i < table[letterIndex].length; i++) {
            ans[index] = table[letterIndex][i];
            letterCombinations(digits, index + 1, ans, ret);
        }
    }

    private static final String[] letterMapping = { "0", "1", "abc", "def", "ghi", "jkl", "mno", "pqrs", "tuv", "wxyz" };
    public List<String> letterCombinations2(String digits) {
        // BFS
        LinkedList<String> ans = new LinkedList<>();
        if (digits == null || digits.isEmpty()) {
            return ans;
        }
        ans.add("");
        for (int i = 0; i < digits.length(); i++) {
            int x = Character.getNumericValue(digits.charAt(i));

            // every loop: append a character
            while (ans.peek().length() == i) { // Retrieves, but does not remove, the head (first element) of this list.
                String s = ans.remove(); // Retrieves and removes the head (first element) of this list.
                for (char c : letterMapping[x].toCharArray()) {
                    ans.add(s + c);
                }
            }
        }
        return ans;
    }

    public List<String> letterCombinations3(String digits) {
        // BFS
        LinkedList<String> cur = new LinkedList<>(), next = new LinkedList<>();
        if (digits == null || digits.isEmpty()) {
            return cur;
        }
        cur.add("");
        for (int i = 0; i < digits.length(); i++) {
            int x = digits.charAt(i) - '0';
            while (!cur.isEmpty()) {
                String s = cur.remove();
                for (char c : letterMapping[x].toCharArray()) {
                    next.add(s + c);// intermediate strings for garbage collection -> StringBuilder
                    // clone the original string O(n)
                    // and the solution 1 is DFS - you might have stack overflow
                    // in interview there is no best solution, they all have advantages & disadvantages, tradeoff
                }
            }
            cur = next;
            next = new LinkedList<>();
        }
        return cur;
    }


    /**
* Word Search
* Given a 2D board and a word, find if the word exists in the grid.
* 
* The word can be constructed from letters of sequentially adjacent cell,
* where "adjacent" cells are those horizontally or vertically neighboring.
* The same letter cell may not be used more than once.
* 
* For example, Given board =
* 
* [ ["ABCE"], ["SFCS"], ["ADEE"] ]
* 
* word = "ABCCED", -> returns true, word = "SEE", -> returns true, word =
* "ABCB", -> returns false.
*/

// thought: scan the matrix from left to right, row by row from up to bottom,
// check whether we can start from point (i, j) can search the word
// how to check? I use depth-first search from the starting point, 
// check if the character of the current point matches with that of the corresponding position of the word
// if not we can just return false; if it is, we continue to iterate the left, right, upper and lower points 
// and do the recursion
// finally if we reach out of the index of the word, i.e., word.length(), means the word is found.
public boolean exists(char[][] board, String word) {
    if (board.length == 0 || word.length() == 0) {
        return false;
    }
    
    int m = board.length;
    int n = board[0].length;
    for (int i = 0; i < m; i++) {
        for (int j = 0; j < n; j++) {
            if (exists(board, word, 0, i, j)) { // start point (i, j)
                return true;
            }
        }
    }
    return false;
}

// this dir two-dimensional array is used to
// simplify the traversal of left, right, upper and lower nodes/points in a matrix
//          (x-1, y) 
// (x, y-1) (x  , y) (x, y+1)
//          (x+1, y)  
private static final int[][] dir = new int[][]{
                                                {0, 1},
                                                {0, -1},
                                                {1, 0},
                                                {-1, 0}
                                              };

// this exists method check
// whether substring of word from index i to the end can be found in the board starting from point (x, y)                                              
private boolean exists(char[][] board, String word, int i, int x, int y) {
    // found
    if (i == word.length()) {
        return true;
    } 
    // out of boundary
    if (x < 0 || y < 0 || x == board.length || y == board[0].length) {
        return false;
    }
    char old = board[x][y];
    // not found
    if (old != word.charAt(i)) {
        return false;
    }
    board[x][y] = '#'; // avoid traversal back to visited position
    for (int[] dir : dirs) {
        if (exists(board, word, i + 1, x + dir[0], y + dir[1])) {
            board[x][y] = old; // restore before method ends
            return true;
        }
    }
    board[x][y] = old // restore before method ends
    return false; 
}

// run time:
// best:
// worst: O(n^2 * 4^(n^2))

/**
*Word Search II
*Given a 2D board and a list of words from the dictionary, find all words in the board.
*Each word must be constructed from letters of sequentially adjacent cell, where "adjacent" cells are those horizontally or vertically neighboring. The same letter cell may not be used more than once in a word.
*
*For example,
*Given words = ["oath","pea","eat","rain"] and board
*=
*[
*['o','a','a','n'],
*['e','t','a','e'],
*['i','h','k','r'],
*['i','f','l','v']
*]
*
*Return ["eat","oath"].
*You may assume that all inputs are consist of lowercase letters a-z.
*/

public List<String> findWords(char[][] board, String[] words) {
    List<String> result = new ArrayList<>();
    
    // build Trie
    Trie trie = new Trie();
    for (String word : words) {
        trie.insert(word);
    }
    
    for (int i = 0; i < board.length; i++) {
        for (int j = 0; j < board.length[0]; j++) {
            dfsFindWords(board, result, trie, null, i, j);
        }
    }
    
    return result;
}

private void dfsFindWords(char[][] board, List<String> result, Trie trie, TrieNode node, int x, int y) {
    char c = board[x][y];
    TrieNode nextNode = trie.searchNextNode(node, c);
    // if not found
    if (nextNode == null) {
        return; // early return if there is no matching dictionary word
    }
    // if found
    if (nextNode.word != null) {
        result.add(nextNode.word);
        nextNode.word = null; // de-duplicate
        // trie.delete(nextNode.word);
    }
    board[x][y] = '#';
    for (Point neighbor : getWordSearchNeighbors(board, x, y)) {
        dfsFindWords(board, result, trie, nextNode, neighbor.x, neighbor.y);
    }
    board[x][y] = c;
}

private List<Point> getWordSearchNeighbors(char[][] board, int x, int y) {
    List<Point> neighbors = new ArrayList<>(4);
    for (Point candidate : new Point[] { new Point(x - 1, y), new Point(x + 1, y), new Point(x, y - 1),
            new Point(x, y + 1) }) {
        if (0 <= candidate.x && candidate.x < board.length && 0 <= candidate.y && candidate.y < board[0].length
                && board[candidate.x][candidate.y] != '#') {
            neighbors.add(candidate);
        }
    }

    return neighbors;
}

// runtime:
// best: 
// worst: 




/**
* Word Ladder
Given two words (beginWord and endWord), and a dictionary's word list, find the length of shortest transformation sequence from beginWord to endWord, such that:
Only one letter can be changed at a time.
Each transformed word must exist in the word list. Note that beginWord is not a transformed word.
For example,
Given:
beginWord = "hit"
endWord = "cog"
wordList = ["hot","dot","dog","lot","log","cog"]
As one shortest transformation is "hit" -> "hot" -> "dot" -> "dog" -> "cog",
return its length 5.
Note:
Return 0 if there is no such transformation sequence.
All words have the same length.
All words contain only lowercase alphabetic characters.
You may assume no duplicates in the word list.
You may assume beginWord and endWord are non-empty and are not the same.

Reference https://discuss.leetcode.com/topic/29303/two-end-bfs-in-java-31ms/27
*/

// thought: Use double-ended breath-first search for traversal.
// Use two sets to maintain transformed words at the initial state where we only have beginWord,
// and at the final state where we have endWord.
// In each time of traversal, I work on the set which is smaller.
// iterate every word in the smaller set, get all possible one-edit-words
// if the other set contains one of the words, indicate we found
// otherwise we will add the valid dictionary word into a new set, which will be next-level set. 
// do this util we cannot find any transformed words

// ladder length: the count of nodes 
public int ladderLength(String beginWord, String endWord, List<String> wordList) {
    Set<String> dict = new HashSet<>(wordList);
    // corner case
    if (!dict.contains(endWord)) {
        return 0;
    }
    
    // Two-end BFS - work on the set which is smaller each time
    Set<String> set1 = new HashSet<>();
    set1.add(beginWord);
    Set<String> set2 = new HashSet<>();
    set2.add(endWord);
    
    int len = 2; // beginWord -> endWord, assume beginWord and endWord are non-empty and are not the same.
    while (true) {
        if (set1.size() > set2.size()) {
            Set<String> temp = set1;
            set1 = set2;
            set2 = temp;
        }
        // set1.size() <= set2.size() - make sure working on the set which is smaller
        
        
        if (set1.isEmpty()) {
            break;
        }
        
        // set for the next level
        Set<String> set = new HashSet<>();
        for (String str : set1) {
            for (String word : getOneEditWords(str)) {
                if (set2.contains(word)) { // found
                    return len;
                }
                if (dict.contains(word)) {
                    set.add(word);
                    dict.remove(word); // de-duplicate
                }
            }
        }
        
        set1 = set;
        len++;
    }
    return 0; // no path connect 'start' and 'end'
}

// return all the possible words of one-edit character of given word
// return iterator with lazy return or just inline this function will save space
private Set<String> getOneEditWords(String str) {
    Set<String> words = new HashSet<>();
    for (int i = 0; i < str.length(); i++) {
        for (char c = 'a'; c <= 'z'; c++) {
            String tmp = str.substring(0, i) + c + str.substring(i + 1, str.length());
            words.add(tmp);
        }
    }
    return words;
}

// runtime: 
// best:
// worst: 


/**
* Word Ladder II
* Graph of example: |--- dot --- dog ---| hit --- hot -- | |
*                   | |--- cog |--- lot --- log ---|
* 
* backward adjacent list: 
*  hit => hot => dot => dog => cog 
*             => lot => log
* Given two words (start and end), and
* a dictionary, find all shortest transformation sequence(s) from start to
* end, such that:
* 
* Only one letter can be changed at a time Each intermediate word must
* exist in the dictionary
* 
* For example,
* 
* Given: start = "hit" end = "cog" dict = ["hot","dot","dog","lot","log"]
* 
* Return
* 
* [ ["hit","hot","dot","dog","cog"], ["hit","hot","lot","log","cog"] ]
* 
* Note:
* 
* All words have the same length. All words contain only lowercase
* alphabetic characters.
*/

// find all shortest transformation sequence(s) from start to end
// two-end BFS
public List<List<String>> findLadders(String beginWord, String endWord, List<String> wordList) {
    Set<String> dict = new HashSet<>(wordList);
    if (!wordList.contains(endWord)) {
        return Collections.emptyList(); // !!!!!!
    }
    
    // hash set for both ends
    Set<String> set1 = new HashSet<>();
    Set<String> set2 = new HashSet<>();
    
    // initial words in both ends
    set1.add(beginWord);
    set2.add(endWord);
    
    // we use a map to help construct the final result
    // mapping: word -> ?
    Map<String, List<String>> backtrace = new HashMap<>();
    if (!findLadders(dict, set1, set2, backtrace, false)) {
        return Collections.emptyList();
    }
    
    return generateList(beingWord, endWord, backtrace, new ArrayList<>(), new ArrayList<>());
}

// flip helps construct string in a correct direction
private boolean findLadders(Set<String> dict, Set<String> set1, Set<String> set2, Map<String, List<String>> backtrace, boolean flip) {
    if (set1.size() > set2.size()) {
        return findLadders(dict, set2, set1, backtrace, !flip);
    }

    // set1.size() <= set2.size()
    if (set1.isEmpty()) {
        return false;
    }

    // remove words on current both ends from the dict
    dict.removeAll(set1);
    dict.removeAll(set2);

    // as we only need the shortest paths
    // we use a boolean value help early termination
    boolean done = false;
    // set for the next level
    Set<String> set = new HashSet<>();
    for (String str : set1) {
        for (String word : getOneEditWords(str)) {
            // make sure we construct the tree in the correct direction
            String key = flip ? word : str;
            String val = flip ? str : word;

            if (set2.contains(word)) {
                done = true;
                backtrace.computeIfAbsent(key, k -> new ArrayList<>()).add(val);
                // not early terminate here - find all possible shortest paths
            }

            if (!done && dict.contains(word)) {
                set.add(word);
                backtrace.computeIfAbsent(key, k -> new ArrayList<>()).add(val);
            }
        }
    }

    // early terminate if done is true
    return done || findLadders(dict, set2, set, backtrace, !flip);
}

// backtrace/dfs
// find all the paths from startWord to endWord, given by map (represent the connection between 
// words - key is a word and value is a list of dicionary words that key word is transformed into)
// and those paths are considered to be shortest, since we use early termination once done.
private List<List<String>> generateList(String start, String end, Map<String, List<String>> map,
        List<String> path, 
        List<List<String>> res) {
    if (start.equals(end)) { // one path is found - early return
        path.add(start); // add
        res.add(new ArrayList<>(path));
        path.remove(path.size() - 1); // restore
        return res;
    }

    if (!map.containsKey(start)) {
        return res;
    }

    List<String> words = map.get(start);
 
    path.add(start); // add
    for (String word : words) {
        generateList(word, end, map, path, res);
    }
    path.remove(path.size() - 1); // restore
    return res;
}
    
private static final char[] charSet = new char[] {'A', 'C', 'G', 'T'};

// mutation distance: the count of edges = ladder length - 1
public int findMutationDistance(String start, String end, String[] bank) {
    Set<String> dict = new HashSet<>(Arrays.asList(bank);
    if (!dict.contains(end)) {
        return -1;
    }
    
    Set<String> set1 = new HashSet<>();
    set1.add(start);
    Set<String> set2 = new HashSet<>();
    set2.add(end);
    
    int len = 1;
    while (true) {
        if (set1.size() > set2.size()) {
            Set<String> temp = set1;
            set1 = set2;
            set2 = temp;
        }
        
        if (set1.isEmpty()) {
            break;
        }
            
        HashSet<String> set = new HashSet<>();
        for (String str : set1) {
            for (String mutation : getOneEditWord(str)) {
                if (set2.contains(mutation)) {
                    return len;
                }
                
                if (bank.contains(mutation)) {
                    set.add(mutation);
                    bank.remove(mutation);
                }
            }
        }
        
        set1 = set;
        len++;
    }
    
    return -1;
}

// return all the possible words of one-edit character of given word
// return iterator with lazy return or just inline this function will save space
public static Set<String> getOneEditWord(String str) {
    Set<String> result = new ArrayList<>();
    for (int i = 0; i < str.length(); i++) {
        for (char c : charSet) {
            String tmp = str.substring(0, i) + c + str.substring(i + 1, str.length());
            words.add(tmp);
        }
    }
            
    return result;
}


// maze
// 0: walkable
// 1: blocked or visited
// 9: destination
public boolean maze(int[][] matrix) {
    if(matrix == null || matrix.length == 0 || matrix[0].length ==0) {
        return false;
    }
    if (matrix[0][0] == 9) {
        return true;
    }
    int m = matrix.length, n = matrix[0].length;
    Queue<int[]> queue = new LinkedList<>();
    queue.offer(new int[]{0,0});
    matrix[0][0] = 1;
    while(!queue.isEmpty()) {
        int[] cur = queue.poll();
        for (int[] dir : dirs ) {
            int x = cur[0] + dir[0];
            int y = cur[1] + dir[1];
            if (x >= 0 && x < m && y >= 0 && y < n) {
                if(matrix[x][y] == 9){
                    return true;
                }
                if (matrix[x][y] ==0) {
                    queue.offer(new int[] {x, y});
                    matrix[x][y] = 1;
                }
            }
        }
    }
    return false;
}




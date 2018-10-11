Word Ladder
public int ladderLength(String beginWord, String endWord, List<String> wordList) {
    if (beginWord == null || endWord == null || wordList == null) {
        return 0;
    }
    
    Set<String> dict = new HashSet<>(wordList);
    if (beginWord.equals(endWord) || !dict.contains(endWord)) {
        return 0;
    }
    
    // Two-end BFS - work on the set which is smaller each time
    Set<String> set1 = new HashSet<>();
    Set<String> set2 = new HashSet<>();

    set1.add(beginWord);    
    set2.add(endWord);
    
    int len = 2; // at least 2: beginWord -> endWord
    while (true) {
        if (set1.size() > set2.size()) { // swap to ensure set1 is smaller
            swap(set1, set2);
        }
        
        // VERY IMPORTANT
        if (set1.isEmpty()) {
            break;
        }
        
        Set<String> next = new HashSet<>();
        for (String str : set1) {
            for (String word : getOneEditWords(str)) {
                if (set2.contains(word)) { // if contains, word must be in dict
                    return len;
                }
                
                if (dict.contains(word)) {
                    next.add(word);
                    dict.remove(word); // de-duplicate
                }
            }
        }
        
        set1 = next;
        len++;
    }
    
    return 0;
}

private Set<String> getOneEditWords(String str) {
    Set<String> words = new HashSet<>();
    for (int i = 0; i < str.length(); i++) {
        for (char c = 'a'; c <= 'z'; c++) {
            String tmp = str.substring(0, i) + c + str.substring(i + 1);
            words.add(tmp);
        }
    }
    return words;
}

private void swap(Set<String> set1, Set<String> set2) {
    Set<String> temp = set1;
    set1 = set2;
    set2 = temp;
}


// find all shortest transformation sequence(s) from start to end
// two-end BFS
public List<List<String>> findLadders(String beginWord, String endWord, List<String> wordList) {
    Set<String> dict = new HashSet<>(wordList);
    if (!wordList.contains(endWord)) {
        return Collections.emptyList(); 
    }
    
    Set<String> set1 = new HashSet<>();
    Set<String> set2 = new HashSet<>();
    
    set1.add(beginWord);
    set2.add(endWord);
    
    // graph: backtrace
    // start point: begin word
    // end point: end word
    // path
    // res: in this graph, starting from beginWord, all path to endWord
    Map<String, List<String>> backtrace = new HashMap<>();
    if (!findLadders(dict, set1, set2, backtrace, false)) {
        return Collections.emptyList();
    }
    
    return generateList(beingWord, endWord, backtrace, new ArrayList<>(), new ArrayList<>());
}

// flip helps construct string in a correct direction
// backtrace stores mappings between and 
private boolean findLadders(Set<String> dict, Set<String> set1, Set<String> set2, Map<String, List<String>> backtrace, boolean flip) {
    if (set1.size() > set2.size()) {
        return findLadders(dict, set2, set1, backtrace, !flip);
    }

    // smaller
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
                // same word might enter not only once
                set.add(word); // set might contains the word, BUT the path different!!
                backtrace.computeIfAbsent(key, k -> new ArrayList<>()).add(val);
            }
        }
    }
    // set is supposed to assign to set1

    // early terminate if done is true
    return done || findLadders(dict, set, set2, backtrace, flip);
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

// Cut Off Trees for Golf Event

Since we have to cut trees in order of their height, 
we first put trees (int[] {row, col, height}) into a priority queue 
and sort by height.
Poll each tree from the queue and use BFS to find out steps needed.
The worst case time complexity could be O(m^2 * n^2) 
(m = number of rows, n = number of columns) 
since there are m * n trees and 
for each BFS worst case time complexity is O(m * n) too.


class Solution {
    
    private static final int[][] dirs = new int[][] {
        {0, 1}, {0, -1}, {1, 0}, {-1, 0}
    };
    
    public int cutOffTree(List<List<Integer>> forest) {
        if (forest == null || forest.size() == 0) {
            return 0; // 0?
        }
        
        int r = forest.size();
        int c = forest.get(0).size();
        
        // row, col, height
        PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) -> a[2] - b[2]);
        for (int i = 0; i < r; i++) {
            for (int j = 0; j < c; j++) {
                int height = forest.get(i).get(j);
                if (height > 1) {
                    pq.offer(new int[]{i, j, height});
                }
            }
        }
        
        int[] start = new int[2];
        int sum = 0;
        while (!pq.isEmpty()) {
            int[] tree = pq.poll();
            int step = minSteps(forest, start, tree, r, c);
            
            if (step < 0) {
                return -1;
            }
            
            sum += step;
            
            start[0] = tree[0];
            start[1] = tree[1];
        }
        
        return sum;
    }
    
    // greatest pattern for shortest path calculation
    // visited follows with queue.offer
    private int minSteps(List<List<Integer>> forest, int[] start, int[] tree, int r, int c) {
        boolean[][] visited = new boolean[r][c];
        Queue<int[]> queue = new LinkedList<>();
        
        queue.offer(start);
        visited[start[0]][start[1]] = true; // !!
        
        int step = 0;
        
        while (!queue.isEmpty()) {
            int size = queue.size(); // !! for step calculation
            
            for (int i = 0; i < size; i++) { // !!
                int[] cur = queue.poll();
                if (cur[0] == tree[0] && cur[1] == tree[1]) {
                    return step;
                }

                for (int[] dir : dirs) {
                    int x = dir[0] + cur[0];
                    int y = dir[1] + cur[1];

                    if (0 <= x && x < r && 0 <= y && y < c 
                        && forest.get(x).get(y) != 0 // !!
                        && visited[x][y] == false) { // !!
                        queue.offer(new int[]{x, y});
                        visited[x][y] = true;
                    }
                }
            }


            step++; // !!
        }
        
        return -1;
    }
}




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
    set1.add(beginWord);
    
    Set<String> set2 = new HashSet<>();
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




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

Minimum Genetic Mutation

class Solution {
    public int minMutation(String start, String end, String[] bank) {
        if (start == null || end == null || bank == null || bank.length == 0) {
            return -1;
        }
        Set<String> dict = new HashSet<>(Arrays.asList(bank));
        if (!dict.contains(end)) {
            return -1;
        }
        
        Set<String> set1 = new HashSet<>();
        Set<String> set2 = new HashSet<>();
        
        set1.add(start);
        set2.add(end);
        
        int res = 1;
        while (true) {
            if (set1.size() > set2.size()) {
                Set<String> temp = set1;
                set1 = set2;
                set2 = temp;
            }
            
            if (set1.isEmpty()) {
                break;
            }
            
            Set<String> set = new HashSet<>();
            for (String str : set1) {
                for (String next : getOneEditWord(str)) {
                    if (set2.contains(next)) {
                        return res;
                    }
                    
                    if (dict.contains(next)) {
                        set.add(next);
                        dict.remove(next);
                    }
                }
            }
            res++;
            set1 = set;
        }
        
        return -1;
    }
    
    private static final char[] chars = new char[]{'A', 'C', 'G', 'T'};
    
    private List<String> getOneEditWord(String str) {
        List<String> res = new ArrayList<>();
        for (int i = 0; i < str.length(); i++) {
            for (char c : chars) {
                res.add(str.substring(0, i) + c + str.substring(i + 1));
            }
        }
        return res;
    }
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


Populating Next Right Pointers in Each Node

/**
 * Definition for binary tree with next pointer.
 * public class TreeLinkNode {
 *     int val;
 *     TreeLinkNode left, right, next;
 *     TreeLinkNode(int x) { val = x; }
 * }
 */
public class Solution {
    // it is a perfect binary tree 
    // (ie, all leaves are at the same level, and every parent has two children).
    public void connect(TreeLinkNode root) {
        if (root == null) {
            return;
        }
        
        while (root != null) {
            TreeLinkNode cur = root;
            while (cur != null) { // linked list manipulation
                if (cur.left != null) { // cur might be a leaf
                    cur.left.next = cur.right;
                }
                if (cur.right != null && cur.next != null) {
                    cur.right.next = cur.next.left;
                }
                
                cur = cur.next;
            }
            
            root = root.left;
        }
    }
}

Binary Tree Vertical Order Traversal

class Solution {
    public List<List<Integer>> verticalOrder(TreeNode root) {
        List<List<Integer>> res = new ArrayList<>();
        
        if (root == null) {
            return res;
        }
        
        Queue<TreeNode> nodes = new LinkedList<>();
        Queue<Integer> cols = new LinkedList<>();
        
        Map<Integer, List<Integer>> map = new HashMap<>();
        
        nodes.offer(root);
        cols.offer(0);
        
        int min = 0, max = 0;
        
        while (!nodes.isEmpty()) {
            TreeNode node = nodes.poll();
            int col = cols.poll();
            
            List<Integer> list = map.computeIfAbsent(col, c -> new ArrayList<>());
            list.add(node.val);
            
            if (node.left != null) {
                nodes.offer(node.left);
                cols.offer(col - 1);
                min = Math.min(min, col - 1);
            }
            
            if (node.right != null) {
                nodes.offer(node.right);
                cols.offer(col + 1);
                max = Math.max(max, col + 1);
            }
            
        }
        
        for (int i = min; i <= max; i++) {
            res.add(map.get(i));
        }
        
        return res;
    }
}

Binary Tree Level Order Traversal

class Solution {
    public List<List<Integer>> levelOrder(TreeNode root) {
        List<List<Integer>> res = new ArrayList<>();
        
        if (root == null) {
            return res;
        }
        
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);
        
        while (!queue.isEmpty()) {
            int size = queue.size();
            
            List<Integer> list = new ArrayList<>();
            for (int i = 0; i < size; i++) {
                TreeNode node = queue.poll();
                list.add(node.val);
                if (node.left != null) {
                    queue.offer(node.left);
                }

                if (node.right != null) {
                    queue.offer(node.right);
                }
            }
            res.add(list);
        }
        return res;
        
    }
}

Pacific Atlantic Water Flow
Find the list of grid coordinates where water can flow to both the Pacific and Atlantic ocean.
class Solution {
    public List<int[]> pacificAtlantic(int[][] matrix) {
        if(matrix == null || matrix.length == 0 || matrix[0].length == 0){
            return Collections.emptyList();
        }
        
        List<int[]> res = new LinkedList<>();
        
        int r = matrix.length;
        int c = matrix[0].length;
        
        boolean[][] pacific = new boolean[r][c];
        boolean[][] atlantic = new boolean[r][c];
        Queue<int[]> pQueue = new LinkedList<>();
        Queue<int[]> aQueue = new LinkedList<>();
        for (int i = 0; i < r; i++) {
            pacific[i][0] = true;
            pQueue.offer(new int[]{i, 0});
            
            atlantic[i][c - 1] = true;
            aQueue.offer(new int[]{i, c - 1});
        }
        
        for (int i = 0; i < c; i++) {
            pacific[0][i] = true;
            pQueue.offer(new int[]{0, i});
            
            atlantic[r - 1][i] = true;
            aQueue.offer(new int[]{r - 1, i});
        }
        
        bfs(matrix, pQueue, pacific);
        bfs(matrix, aQueue, atlantic);
        
        for (int i = 0; i < r; i++) {
            for (int j = 0; j < c; j++) {
                if (pacific[i][j] && atlantic[i][j]) {
                    res.add(new int[]{i, j});
                }
            }
        }
        return res;
    }
    
    private static final int[][] dirs = new int[][] {
        {1, 0}, {-1, 0}, {0, 1}, {0, -1}  
    };
    
    private void bfs(int[][] matrix, Queue<int[]> queue, boolean[][] visited) {
        int r = matrix.length;
        int c = matrix[0].length;
        
        while (!queue.isEmpty()) {
            int[] cur  = queue.poll();
            // visited[cur[0]][cur[1]] = true; this one works as well
            for (int[] dir : dirs) {
                int x = cur[0] + dir[0];
                int y = cur[1] + dir[1];
                
                if (0 <= x && x < r && 0 <= y && y < c &&
                    visited[x][y] == false && 
                    matrix[x][y] >= matrix[cur[0]][cur[1]]) {
                    queue.offer(new int[]{x, y});
                    visited[x][y] = true;
                }
            }
        }
    }
}

Flood Fill

class Solution {
    private static final int[][] dirs = new int[][] {
        {0, 1}, {0, -1}, {1, 0}, {-1, 0}
    };
    
    public int[][] floodFill(int[][] image, int sr, int sc, int newColor) {
        if (image == null || image.length == 0 || image[0].length == 0) {
            return new int[0][0];
        }
        
        int r = image.length;
        int c = image[0].length;
        int color = image[sr][sc];
        boolean[][] visited = new boolean[r][c];
        
        Queue<int[]> queue = new LinkedList<>();
        queue.offer(new int[]{sr, sc});
        image[sr][sc] = newColor; // works like visited
        visited[sr][sc] = true;
        
        while (!queue.isEmpty()) {
            int[] cur = queue.poll();
            
            for (int[] dir : dirs) {
                int x = dir[0] + cur[0];
                int y = dir[1] + cur[1];
                
                if (0 <= x && x < r && 0 <= y && y < c && 
                    visited[x][y] == false && 
                    image[x][y] == color) {
                    queue.offer(new int[]{x, y});
                    image[x][y] = newColor; // works like visited
                    visited[x][y] = true;
                }
            }
        }
        return image;
    }
}


class Solution {
    // Graph (undirected graph) + BFS
    public List<Integer> distanceK(TreeNode root, TreeNode target, int K) {
        if (root == null) {
            return Collections.emptyList();
        }
        
        Map<TreeNode, Set<TreeNode>> adj = new HashMap<>();
        buildMap(root, null, adj);
        
        if (!adj.containsKey(target)) {
            return Collections.emptyList();
        }
        
        Queue<TreeNode> queue = new LinkedList<>();
        Set<TreeNode> visited = new HashSet<>();
        queue.offer(target);
        visited.add(target);
        
        List<Integer> res = new ArrayList<>();
        while (!queue.isEmpty()) {
            int size = queue.size();
            
            if (K == 0) {
                for (int i = 0; i < size; i++) {
                    res.add(queue.poll().val);    
                }
                return res;
            }
            
            for (int i = 0; i < size; i++) {
                TreeNode node = queue.poll();
                for (TreeNode next : adj.get(node)) {
                    if (visited.contains(next)) {
                        continue;
                    }
                    
                    queue.offer(next);
                    visited.add(next);
                }
            }
            K--;
        }
        
        return res;
    }
    
    // preorder
    private void buildMap(TreeNode node, TreeNode parent, Map<TreeNode, Set<TreeNode>> adj) {
        if (node == null) {
            return;
        }
        
        adj.computeIfAbsent(node, k -> new HashSet<>());
        if (parent != null) {
            adj.get(node).add(parent); // undirected graph
            adj.get(parent).add(node); // undirected graph
        }
        
        buildMap(node.left, node, adj);
        buildMap(node.right, node, adj);
    }
}

class Solution {
    public int snakesAndLadders(int[][] board) {
        int n = board.length;
        int[] arr = new int[n * n];
        
        int i = n - 1, j = 0, index = 0, inc = 1;
        while (index < n * n) {
            arr[index++] = board[i][j];
            if (inc == 1 && j == n - 1) {
                inc = -1;
                i--;
            } else if (inc == -1 && j == 0) {
                inc = 1;
                i--;
            } else {
                j += inc;
            }
        }
        
        boolean[] visited = new boolean[n * n];
        Queue<Integer> q = new LinkedList<>();
        int start = arr[0] > -1 ? arr[0] - 1 : 0;
        q.offer(start);
        int step = 0;
        while (!q.isEmpty()) {
            int size = q.size();
            while (size-- > 0) {
                int cur = q.poll();
                if (cur == n * n - 1) {
                    return step;
                }
                
                for (int next = cur + 1; next <= Math.min(cur + 6, n * n -1); next++) {
                    int dest = arr[next] > -1 ? arr[next] - 1 : next;
                    if (!visited[dest]) { // avoid going back by snake
                        visited[dest] = true;
                        q.offer(dest);
                    }
                }
            }
            step++;
        }
        return -1;
    }
}
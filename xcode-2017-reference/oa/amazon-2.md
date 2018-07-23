* Day change (cell growth): int[] dayChange(int[] cells, int days), cells 数组，有8个元素，每天的变化情况是 当前位置 cell = (cell[i - 1] == cell[i + 1]) ? 0 : 1, 左右相等，当前位置为0， 不等为1， 默认 cells[0]左边 和cells[cells.length - 1]右边的位置元素都是0， 求days天后的变化.
* Number of Connected Components in an Undirected Graph 
* Number of Islands I
* Number of Islands II
* Minimum Spanning Tree
* Find the Weak Connected Component in the Directed Graph
* Five Scores
* Maximum Subtree of Average
* Sliding Window Maximum
* Maze
* Maximum Minimum Path


```java
  XOR
    { 0, 1, 1, 0, 1, 1, 1 }
  [0, 0, 1, 1, 0, 1, 1, 1, 0]  day 1
  [0, 1, 1, 1, 0, 1, 0, 1, 0]  day 2
  [0, 1, 0, 1, 0, 0, 0, 0, 0]  day 3
  [0, 0, 0, 0, 1, 0, 0, 0, 0]
  [0, 0, 0, 1, 0, 1, 0, 0, 0]
  public static int[] daysChange(int[] days, int n) {
      if (days == null || n <= 0) {
          return days;
      }
      int length = days.length;
      int[] rvalue = new int[length + 2];
      rvalue[0] = rvalue[length + 1] = 0;
      int pre = rvalue[0];
      for (int i = 1; i <= length; i++) {
          rvalue[i] = days[i - 1];
      }
      for (int i = 0; i < n; i++) {
          for (int j = 1; j <= length; j++) {
              int temp = rvalue[j];
              rvalue[j] = pre ^ rvalue[j + 1];
              pre = temp;
          }
      }
      return Arrays.copyOfRange(rvalue, 1, length + 1);
  }

  /*
  System.arraycopy(src, srcPos, dest, destPos, length);
  src − This is the source array.
  srcPos − This is the starting position in the source array.
  dest − This is the destination array.
  destPos − This is the starting position in the destination data.
  length − This is the number of array elements to be copied.
  */

  // !!! Arrays.toString(arr)
  // Collection (list, set) 可以直接打 .toString()

  /*
  Arrays.copyOfRange(original, from, to);
  original − This is the array from which a range is to to be copied.
  from − This is the initial index of the range to be copied, inclusive.
  to − This is the final index of the range to be copied, exclusive.
  */
  public static int[] daysChange2(int[] days, int n) {
      if (days == null || n <= 0) {
          return days;
      }
      int[] arr = new int[days.length + 2];
      System.arraycopy(days, 0, arr, 1, days.length);
      while (n-- > 0) {
          int pre = arr[0]; // 0
          System.out.println(Arrays.toString(arr));
          for (int j = 1; j <= days.length; j++) {
              int temp = arr[j];
              arr[j] = pre ^ arr[j + 1];
              pre = temp;
          }
      }
      return Arrays.copyOfRange(arr, 1, days.length + 1);
  }    
```

```java
  class UnionFind {
      int[] id, size;
      int count;

      public UnionFind(int len) {
          this.id = new int[len];
          this.size = new int[len];
          this.count = len;
      }

      public boolean find(int p, int q) {
          return root(p) == root(q);
      }

      public void union(int p, int q) {
          int pi = root(p), qi = root(q);
          if (this.size[pi] < this.size[qi]) {
              this.id[pi] = qi;
              this.size[qi] += this.size[pi];
          } else {
              this.id[qi] = pi;
              this.size[pi] += this.size[qi];
          }
          this.count--;
      }

      private int root(int i) {
          while (i != id[i]) {
              id[i] = id[id[i]]; // path compression
              i = id[i];
          }
          return i;
      }
  }
``` 

```java
    /**
     * Number of Connected Components in an Undirected Graph 
       Given n nodes labeled from 0 to n - 1 and a list of undirected edges (each edge is a pair of nodes), write a function to find the number of connected components in an undirected graph.
       Example 1:
           0          3
           |          |
           1 --- 2    4
       Given n = 5 and edges = [[0, 1], [1, 2], [3, 4]], return 2.
       Example 2:
           0           4
           |           |
           1 --- 2 --- 3
       Given n = 5 and edges = [[0, 1], [1, 2], [2, 3], [3, 4]], return 1.
       Note:
       You can assume that no duplicate edges will appear in edges. Since all edges are undirected, [0, 1] is the same as [1, 0] and thus will not appear together in edges. 
       his is 1D version of Number of Islands II. For more explanations, check out this 2D Solution.
       n points = n islands = n trees = n roots.
       With each edge added, check which island is e[0] or e[1] belonging to.
       If e[0] and e[1] are in same islands, do nothing.
       Otherwise, union two islands, and reduce islands count by 1.
       Bonus: path compression can reduce time by 50%.
     */
    public int countComponents(int n, int[][] edges) {
        UnionFind u = new UnionFind(n);
        for (int i = 0; i < n; i++) {
            u.id[i] = i;
        }

        for (int[] e : edges) {
            if (!u.find(e[0], e[1])) {
                u.union(e[0], e[1]);
            }
        }
        return u.count;
    }
```

```java
    /**
     * Number of Islands
     * Given a 2d grid map of '1's (land) and '0's (water), count the number of islands.
     * An island is surrounded by water and is formed by connecting adjacent lands horizontally or vertically.
     * You may assume all four edges of the grid are all surrounded by water.
       Example 1:
       11110
       11010
       11000
       00000
       Answer: 1
       Example 2:
       11000
       11000
       00100
       00011
       Answer: 3
     */
    public int numIslands(char[][] grid) {
        if (grid == null || grid.length == 0 || grid[0].length == 0) {
            return 0;
        }

        int count = 0;
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                if (grid[i][j] != '1') {
                    continue;
                }

                count++;
                dfsIsland(grid, i, j);
            }
        }

        return count;
    }

    private void dfsIsland(char[][] grid, int x, int y) {
        if (x < 0 || x >= grid.length || y < 0 || y >= grid[0].length || grid[x][y] != '1') {
            return;
        }

        grid[x][y] = '2';
        for (int[] dir : dirs) {
            dfsIsland(grid, x + dir[0], y + dir[1]);
        }
    }
```

```java
    /**
     *  Number of Islands II
        A 2d grid map of m rows and n columns is initially filled with water. We may perform an addLand operation which turns the water at position (row, col) into a land. Given a list of positions to operate, count the number of islands after each addLand operation. An island is surrounded by water and is formed by connecting adjacent lands horizontally or vertically. You may assume all four edges of the grid are all surrounded by water.
        Example:
        Given m = 3, n = 3, positions = [[0,0], [0,1], [1,2], [2,1]].
        Initially, the 2d grid grid is filled with water. (Assume 0 represents water and 1 >represents land).
        0 0 0
        0 0 0
        0 0 0
        Operation #1: addLand(0, 0) turns the water at grid0 into a land.
        1 0 0
        0 0 0   Number of islands = 1
        0 0 0
        Operation #2: addLand(0, 1) turns the water at grid0 into a land.
        1 1 0
        0 0 0   Number of islands = 1
        0 0 0
        Operation #3: addLand(1, 2) turns the water at grid1 into a land.
        1 1 0
        0 0 1   Number of islands = 2
        0 0 0
        Operation #4: addLand(2, 1) turns the water at grid2 into a land.
        1 1 0
        0 0 1   Number of islands = 3
        0 1 0
        We return the result as an array: [1, 1, 2, 3]
        http://segmentfault.com/a/1190000004197552
     */
    public List<Integer> numIslands2(int m, int n, int[][] positions) {
        UnionFind set = new UnionFind(m * n);
        Arrays.fill(set.id, -1);
        List<Integer> res = new ArrayList<>();
        int count = 0;
        for (int[] position : positions) {
            int x = position[0], y = position[1];
            int index = x * n + y;
            count++;
            set.id[index] = index;
            for (int i = 0; i < dirs.length; i++) {
                int newX = x + dirs[i][0];
                int newY = y + dirs[i][1];
                if (newX >= 0 && newX < m && newY >= 0 && newY < n) {
                    int newIndex = newX * n + newY;
                    if (set.id[newIndex] == -1) {
                        continue; // not visited
                    }
                    if (!set.find(index, newIndex)) {
                        set.union(index, newIndex);
                        count--;
                    }
                }
            }
            res.add(count);
        }
        return res;
    }
```

```java
/**
 * Minimum Spanning Tree
 * 题目内容是这样的，给十几个城市供电，连接不同城市的花费不同，让花费最小同时连到所有的边。给出一系列connection类，里面是edge两端的城市名和它们之间的一个cost，找出要你挑一些边，把所有城市连接起来并且总花费最小。不能有环，最后所以城市要连成一个连通块。
 * 不能的话输出空表，最后还要按城市名字排序输出，按照node1来排序,如果一样的话再排node2。 输入: {“Acity”,”Bcity”,1}
 * (“Acity”,”Ccity”,2} (“Bcity”,”Ccity”,3} 输出： (“Acity”,”Bcity”,1}
 * (“Acity”,”Ccity”,2} 补充一句，test case一共有6个。
 */
class Connection {
    String node1;
    String node2;
    int cost;

    public Connection(String a, String b, int c) {
        node1 = a;
        node2 = b;
        cost = c;
    }
}

public class MST {
    static class DisjointSet {
        Set<String> set;
        Map<String, String> map;
        int count;

        public DisjointSet() {
            this.count = 0;
            this.set = new HashSet<>();
            this.map = new HashMap<>();
        }

        public void makeSet(String s) {
            if (this.set.contains(s)) {
                return;
            }
            this.count++;
            this.set.add(s);
            this.map.put(s, s);
        }

        public String root(String s) {
            if (!this.set.contains(s)) {
                return null;
            }
            String v = this.map.get(s);
            if (s.equals(v)) {
                return s;
            }
            String root = this.root(v);
            // path compression: 不是只压一层，直接压至root
            this.map.put(s, root); 
            return root;
        }

        public void union(String s, String t) {
            if (!this.set.contains(s) || !this.set.contains(t)) {
                return;
            }
            if (s.equals(t)) {
                return;
            }
            this.count--;
            this.map.put(s, t);
        }
    }

    public static List<Connection> getMST(List<Connection> connections) {
        Collections.sort(connections, (a, b) -> Integer.compare(a.cost, b.cost));
        DisjointSet set = new DisjointSet();
        List<Connection> res = new ArrayList<>();
        for (Connection connection : connections) {
            set.makeSet(connection.node1);
            set.makeSet(connection.node2);
        }
        for (Connection connection : connections) {
            String s = set.root(connection.node1);
            String t = set.root(connection.node2);
            if (!s.equals(t)) {
                set.union(s, t);
                res.add(connection);
                if (set.count == 1) {
                    break;
                }
            }
        }
        if (set.count == 1) {
            Collections.sort(res, (a, b) -> {
                if (a.node1.equals(b.node1)) {
                    return a.node2.compareTo(b.node2);
                }
                return a.node1.compareTo(b.node1);
            });
            return res;
        }

        return Collections.emptyList();
    }
```

```java
    /**
     *  Find the Weak Connected Component in the Directed Graph
        Find the number Weak Connected Component in the directed graph. Each node in the graph contains a label and a list of its neighbors. (a connected set of a directed graph is a subgraph in which any two vertices are connected by direct edge path.)
        Example
        Given graph:
        A----->B  C
         \     |  | 
          \    |  |
           \   |  |
            \  v  v
             ->D  E <- F
        Return {A,B,D}, {C,E,F}. Since there are two connected component which are {A,B,D} and {C,E,F}
        Note
        Sort the element in the set in increasing order
     */
    public List<List<Integer>> connectedSet2(ArrayList<DirectedGraphNode> nodes) {
        List<List<Integer>> res = new ArrayList<>();
        HashMap<DirectedGraphNode, DirectedGraphNode> map = new HashMap<>();
        for (DirectedGraphNode node : nodes) {
            for (DirectedGraphNode n : node.neighbors) {
                DirectedGraphNode fa = find(map, node);
                DirectedGraphNode ch = find(map, n);
                map.put(fa, ch);
            }
        }
        
        Map<DirectedGraphNode, List<Integer>> record = new HashMap<>();
        for (DirectedGraphNode node : nodes) {
            DirectedGraphNode val = find(map, node);
            List<Integer> group = record.get(val);
            if (group == null) {
                group = new ArrayList<>();
                record.put(val, group);
                res.add(group);
            }
            group.add(node.label);
        }

        for (List<Integer> group : res) {
            Collections.sort(group);
        }
        return res;
    }
   
    private DirectedGraphNode find(
            HashMap<DirectedGraphNode, DirectedGraphNode> map,
            DirectedGraphNode k) {
        DirectedGraphNode v = map.get(k);
        if (v == null) {
            map.put(k, k);
            return k;
        }
        while (v != k) {
            k = v;
            v = map.get(k);
        }
        return k;
    }
    public static void main(String[] args) {
        ArrayList<Connection> connections = new ArrayList<>();
        // connections.add(new Connection("Acity","Bcity",1));
        // connections.add(new Connection("Acity","Ccity",2));
        // connections.add(new Connection("Bcity","Ccity",3));
        connections.add(new Connection("A", "B", 6));
        connections.add(new Connection("B", "C", 4));
        connections.add(new Connection("C", "D", 5));
        connections.add(new Connection("D", "E", 8));
        connections.add(new Connection("E", "F", 1));
        connections.add(new Connection("B", "F", 10));
        connections.add(new Connection("E", "C", 9));
        connections.add(new Connection("F", "C", 7));
        connections.add(new Connection("B", "E", 3));
        connections.add(new Connection("A", "F", 1));

        List<Connection> res = getMST(connections);
        for (Connection c : res) {
            System.out.println(c.node1 + " -> " + c.node2 + " cost : " + c.cost);
        }
    }

}
```

```java
   /**
    Five Scores
    写好了一个叫Result的类，中文翻译成节点，题目是输入是一堆节点，节点里面有两个属性学生id和分数
    保证每个学生至少会有5个分数，求每个人最高的5个分数的平均分。返回的是Map, key是id，value是每个人最高5个分数的平均分double是平均分。
     */
    public static Map<Integer, Double> getHighFive(Result[] results) {
        Map<Integer, PriorityQueue<Integer>> map = new HashMap<>();
        for (Result r : results) {
            PriorityQueue<Integer> q = map.computeIfAbsent(r.id, k -> new PriorityQueue<Integer>(5));
            if (q.size() < 5) {
                q.offer(r.value);
                continue;
            }
            
            if (q.peek() >= r.value) {
                continue;
            }

            q.poll();
            q.offer(r.value);
        }

        Map<Integer, Double> res = new HashMap<>();
        for (Entry<Integer, PriorityQueue<Integer>> entry : map.entrySet()) {
            int id = entry.getKey();
            int sum = 0;
            PriorityQueue<Integer> q = entry.getValue();
            // 比每次调用poll() 时间性能得到优化，以上的课有note
            // for : O(k)
            // poll : O(klog(k)), 但preserve order
            for (int n : q) { 
                sum += n;
            }
            res.put(id, ((double) sum) / 5);
        }
        return res;
    }
```

```java
    /**
     * Maximum Subtree of Average
     * 就是给一棵多叉树，表示公司内部的上下级关系。每个节点表示一个员工，节点包含的成员是他工作了几个月(int)，以及一个下属数组(ArrayList)
     * 目标就是找到一棵子树，满足：这棵子树所有节点的工作月数的平均数是所有子树中最大的。最后返回这棵子树的根节点
     * 这题补充一点，返回的不能是叶子节点(因为叶子节点没有下属)，一定要是一个有子节点的节点
     */
     class Node {
        int val;
        List<Node> children;

        public Node(int val) {
            this.val = val;
            children = new ArrayList<Node>();
        }
    }

    static class SumCount {
        int sum;
        int count;

        public SumCount(int sum, int count) {
            this.sum = sum;
            this.count = count;
        }
    }

    static Node ans;
    static double max = 0;
    public static Node find(Node root) {
        // Initialize static variables is very important for AMZAON OA!
        ans = null;
        max = 0;
        DFS(root);
        return ans;
    }

    private static SumCount DFS(Node root) {
        if (root == null) {
            return new SumCount(0, 0);
        }
        
        //////////// 这一块不写也可以的 ////////////
        if (root.children == null || root.children.isEmpty()) {
            return new SumCount(root.val, 1);
        }
        //////////////////////////////////////////

        int sum = root.val;
        int count = 1;
        for (Node itr : root.children) {
            SumCount sc = DFS(itr);
            sum += sc.sum;
            count += sc.count;
        }
        if (count > 1 && ((double) sum) / count > max) {
            max = ((double) sum) / count;
            ans = root;
        }
        return new SumCount(sum, count);
    }
```

```java
    /**
     *  Sliding Window Maximum
        Given an array nums, there is a sliding window of size k which is moving from the very left of the array to the very right.
        You can only see the k numbers in the window. Each time the sliding window moves right by one position.
        For example,
        Given nums = [1,3,-1,-3,5,3,6,7], and k = 3.
        Window position                Max
        ---------------               -----
        [1  3  -1] -3  5  3  6  7       3
         1 [3  -1  -3] 5  3  6  7       3
         1  3 [-1  -3  5] 3  6  7       5
         1  3  -1 [-3  5  3] 6  7       5
         1  3  -1  -3 [5  3  6] 7       6
         1  3  -1  -3  5 [3  6  7]      7
        sliding windoe: (i-k, i) i++
        decreasing:
        LinkedList = 3, -1
        请用例子讲思路，每个loop, LinkedList的变化，另外给出有duplicate的例子更好，因为相等的需要留下
        Therefore, return the max sliding window as [3,3,5,5,6,7].
        You may assume k is always valid, 1 鈮� k 鈮� input array's size
     */
    public int[] maxSlidingWindow(int[] nums, int k) {
        LinkedList<Integer> l = new LinkedList<>();
        int n = nums.length;
        if (n == 0 || k == 0) {
            return new int[0];
        }
        int[] result = new int[n - k + 1];

        for (int i = 0; i < k; i++) { // First fill in result[0] ~ result[k-1]
            addToSlidingWindow(nums[i], l);
        }

        for (int i = k; i < n; i++) {
            result[i - k] = l.getFirst(); // correspond to nums[i-k] ~ nums[i-1]
            if (nums[i - k] == l.getFirst()) {
                l.removeFirst();
            }
            addToSlidingWindow(nums[i], l);
        }
        result[n - k] = l.getFirst();
        return result;
    }
    
    private void addToSlidingWindow(int a, LinkedList<Integer> l) {
        while (!l.isEmpty() && l.getLast() < a) {
            l.removeLast();
        }
        l.addLast(a);
    }
```

```java
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
```

```java
   /**
    Maximum Minimum Path
    给一个int[][]的matirx，对于一条从左上到右下的path pi，pi中的最小值是mi，求所有mi中的最大值。只能往下或右.
    比如：
    [8, 4, 7]
    [6, 5, 9]
    有3条path：
    8-4-7-9 min: 4
    8-4-5-9 min: 4
    8-6-5-9 min: 5
    return: 5
     */
    public int maxMinPath(int[][] matrix) {
        int m = matrix.length, n = matrix[0].length;
        int[] result = new int[n];
        result[0] = matrix[0][0];
        for (int i = 1; i < n; i++) {
            // populate the first row, only one row from (0, 0) to (0, i), use Math.min
            result[i] = Math.min(result[i - 1], matrix[0][i]);
        }
        for (int i = 1; i < m; i++) {
            // populate the frist columne, only one column from (0, 0) to (i, 0), use Math.min
            result[0] = Math.min(matrix[i][0], result[0]);

            for (int j = 1; j < n; j++) {

                // result[j]: (i-1, j)
                // result[j-1]: (i, j-1)

                //          (i-1, j)
                // (j-1, i) (i,   j)

                result[j] = (result[j] < matrix[i][j] && result[j - 1] < matrix[i][j])
                        ? Math.max(result[j - 1], result[j]) : matrix[i][j];
            }
        }
        return result[n - 1];
    }
```




   class UnionFind {
        int[] id, size; // size: the size of subtree with root not of index
        int count;

        public UnionFind(int len) {
            this.id = new int[len];
            this.size = new int[len];
            this.count = len;
        }

        // boolean: if p and q belong to the same component return true; otherwise return false
        public boolean find(int p, int q) {
            return root(p) == root(q);
        }

        public void union(int p, int q) {
            int pi = root(p), qi = root(q);
            // decide which component to be a child of another component
            // the difference for the child component is 
            // there is one more step for all the nodes under that component to find out the root.
            // so we pick the component with smaller size to be the child.
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
            u.size[i] = 1; // initialize size please
        }

        for (int[] e : edges) {
            if (!u.find(e[0], e[1])) {
                u.union(e[0], e[1]);
            }
        }
        return u.count;
    }

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
	private static final int[][] dir = new int[][]{ 
										  {0, -1},
										  {0, 1},
										  {1, 0},
										  {-1, 0}
									   };

    public int numIslands(char[][] grid) {
        if (grid == null || grid.length == 0 || grid[0].length == 0) {
            return 0;
        }

        int count = 0;
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                if (grid[i][j] != '1') { // append && visited[i][j] == true
                    continue; 
                }

                count++;
                dfsIsland(grid, i, j);
            }
        }

        return count;
    }

    private void dfsIsland(char[][] grid, int x, int y) {
        if (x < 0 || x >= grid.length || y < 0 || y >= grid[0].length || grid[x][y] != '1') { // append || visited[x][y] == true
            return;
        }

        grid[x][y] = '2'; // input being modified... changed to visited[x][y] = true;
        for (int[] dir : dirs) {
            dfsIsland(grid, x + dir[0], y + dir[1]);
        }
    }
n
    private static final int[][] dir = new int[][] {
        {0, 1}, {0, -1}, {1, 0}, {-1, 0}
    };
    
    // char[][] grid !!!
    public int numIslands(char[][] grid) {
        if (grid == null || grid.length == 0 || grid[0].length == 0) {
            return 0;
        }
        
        int r = grid.length;
        int c = grid[0].length;

        UnionFind uf = new UnionFind(r * c);
        // initialization?
        for (int i = 0; i < uf.count; i++) {
            uf.id[i] = i;
            uf.size[i] = 1;
        }
        
        int count = 0;
        for (int i = 0; i < r; i++) {
            for (int j = 0; j < c; j++) {
                if (grid[i][j] == '0') {
                    continue;                    
                }
                count++;
                int id = i * c + j;
                for (int[] d : dir) {
                    int x = i + d[0];
                    int y = j + d[1];
                    
                    if (0 <= x && x < r && 0 <= y && y < c 
                        && grid[x][y] == '1') {
                        
                        int id2 = x * c + y;
                        if (!uf.find(id, id2)) {
                            uf.union(id, id2);     
                            count--;
                        }
                    }
                }
            }
        }
        return count;
    }

    /**
     * Number of Islands II
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
        // m: row count
        // n: col count
        List<Integer> res = new ArrayList<>();

        UnionFind uf = new UnionFind(m * n);
        Arrays.fill(uf.id, -1);
        
        int count = 0;
        for (int[] position : positions) {
            int x = position[0], y = position[1];
            int index = x * n + y;
            count++;

            uf.id[index] = index;  // island. Initially, island itself
            for (int[] dir : dirs) {
                int newX = x + dir[0];
                int newY = y + dir[1];
                if (newX >= 0 && newX < m && newY >= 0 && newY < n) {
                    int newIndex = newX * n + newY;
                    if (uf.id[newIndex] == -1) {
                        continue; // not visited
                    }
                    if (!uf.find(index, newIndex)) {
                        uf.union(index, newIndex);
                        count--;
                    }
                }
            }
            res.add(count);
        }
        return res;
    }

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

    /**
     * Find the Weak Connected Component in the Directed Graph
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

/**
* Accounts Merge
*Given a list accounts, each element accounts[i] is a list of strings, where the first element accounts[i][0] is a name, and the rest of the elements are emails representing emails of the account.
*
*Now, we would like to merge these accounts. Two accounts definitely belong to the same person if there is some email that is common to both accounts. Note that even if two accounts have the same name, they may belong to different people as people could have the same name. A person can have any number of accounts initially, but all of their accounts definitely have the same name.
*
*After merging the accounts, return the accounts in the following format: the first element of each account is the name, and the rest of the elements are emails in sorted order. The accounts themselves can be returned in any order.
*
*Example 1:
*Input: 
*accounts = [["John", "johnsmith@mail.com", "john00@mail.com"], ["John", "johnnybravo@mail.com"], ["John", "johnsmith@mail.com", "john_newyork@mail.com"], ["Mary", "mary@mail.com"]]
*Output: [["John", 'john00@mail.com', 'john_newyork@mail.com', 'johnsmith@mail.com'],  ["John", "johnnybravo@mail.com"], ["Mary", "mary@mail.com"]]
*Explanation: 
*The first and third John's are the same person as they have the common email "johnsmith@mail.com".
*The second John and Mary are different people as none of their email addresses are used by other accounts.
*We could return these lists in any order, for example the answer [['Mary', 'mary@mail.com'], ['John', 'johnnybravo@mail.com'], 
*['John', 'john00@mail.com', 'john_newyork@mail.com', 'johnsmith@mail.com']] would still be accepted.
*Note:
*
*The length of accounts will be in the range [1, 1000].
*The length of accounts[i] will be in the range [1, 10].
*The length of accounts[i][j] will be in the range [1, 30].
*/

// Input: accounts = [
//	["John", "johnsmith@mail.com", "john00@mail.com"], -> email to name one-to-one mapping, email to id one-to-one mapping (each email has a unique id)
//  ["John", "johnnybravo@mail.com"], 
//  ["John", "johnsmith@mail.com", "john_newyork@mail.com"], 
//  ["Mary", "mary@mail.com"]]
// Output: [
//  ["John", 'john00@mail.com', 'john_newyork@mail.com', 'johnsmith@mail.com'],  
//  ["John", "johnnybravo@mail.com"], 
//  ["Mary", "mary@mail.com"]
//]
	public List<List<String>> accountsMerge(List<List<String>> accounts) {
		Map<String, String> emailToName = new HashMap<>();
		Map<String, Integer> emailToID = new HashMap<>();
		int id = 0;

		// initialization
		for (List<String> account : accounts) {
			String name = "";
			for (String email : account) {
				if (name == "") {
					name = email;
					continue;
				}
				emailToName.put(email, name);
				if (!emailToID.containsKey(email)) { 
					emailToID.put(email, id++); // each email has a unique id
				}
			}
		}

		// initialize UnionFind
		UnionFind u = new UnionFind(emailToID.size());
		for (int i = 0; i < emailToID.size(); i++) {
			u.id[i] = i; // id
			u.size[i] = 1; // size
		}

		// union id of each email in the same account
		// a unique email corresponds to a unique id
		for (List<String> account : accounts) {
			for (int i = 2; i < account.size(); i++) {
				u.union(emailToID.get(account.get(i)), emailToID.get(account.get(1)));
			}
		}

		// 
		Map<Integer, List<String>> ans = new HashMap<>();
		for (String email : emailToName.keySet()) {
			int index = u.root(emailToID.get(email));
			ans.computeIfAbsent(index, x -> new ArrayList<>()).add(email);
		}
		for (List<String> component : ans.values()) {
			Collections.sort(component);
			component.add(0, emailToName.get(component.get(0)));
		}
		return new ArrayList<>(ans.values());
	}


public class GraphValidTree {
    /**
     * Graph Valid Tree
     Given n nodes labeled from 0 to n - 1 and a list of undirected edges (each edge is a pair of nodes),
     write a function to check whether these edges make up a valid tree.
     For example:
     Given n = 5 and edges = [[0, 1], [0, 2], [0, 3], [1, 4]], return true.
     Given n = 5 and edges = [[0, 1], [1, 2], [2, 3], [1, 3], [1, 4]], return false.

     Hint:
     Given n = 5 and edges = [[0, 1], [1, 2], [3, 4]], what should your return? Is this case a valid tree?
     According to the definition of tree on Wikipedia: “a tree is an undirected graph in which any two vertices are connected by exactly one path.
     In other words, any connected graph without simple cycles is a tree.”
     
     Note: you can assume that no duplicate edges will appear in edges.
     Since all edges are undirected, [0, 1] is the same as [1, 0] and thus will not appear together in edges.

     AC Java Union-Find solution
     */
    public boolean validTree(int n, int[][] edges) {
        if (edges.length != n - 1) {
            return false;
        }
        UnionFind set = new UnionFind(n);
        for (int i = 0; i < n; i++) {
            set.id[i] = i;
            set.size[i] = 1;
        }
        for (int[] edge : edges) {
            if (set.find(edge[0], edge[1])) {
                return false; // cycle check
            }
            set.union(edge[0], edge[1]);
        }
        return set.count == 1; // single tree check
    }

    // inner class
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

        public int root(int i) {
            while (i != id[i]) {
                id[i] = id[id[i]]; // path compression
                i = id[i];
            }
            return i;
        }
    }
}

import java.util.HashSet;
import java.util.Set;

public class SentenceSimilarity {
    /**
     * Sentence Similarity
     Given two sentences words1, words2 (each represented as an array of strings), and a list of similar word pairs pairs, determine if two sentences are similar.

     For example, "great acting skills" and "fine drama talent" are similar, if the similar word pairs are pairs = [["great", "fine"], ["acting","drama"], ["skills","talent"]].

     Note that the similarity relation is not transitive. For example, if "great" and "fine" are similar, and "fine" and "good" are similar, "great" and "good" are not necessarily similar.

     However, similarity is symmetric. For example, "great" and "fine" being similar is the same as "fine" and "great" being similar.

     Also, a word is always similar with itself. For example, the sentences words1 = ["great"], words2 = ["great"], pairs = [] are similar, even though there are no specified similar word pairs.

     Finally, sentences can only be similar if they have the same number of words. So a sentence like words1 = ["great"] can never be similar to words2 = ["doubleplus","good"].

     Note:

     The length of words1 and words2 will not exceed 1000.
     The length of pairs will not exceed 2000.
     The length of each pairs[i] will be 2.
     The length of each words[i] and pairs[i][j] will be in the range [1, 20].
     */
    public boolean areSentencesSimilar(String[] words1, String[] words2, String[][] pairs) {
        if (words1.length != words2.length) {
            return false;
        }

        Set<String> pairSet = new HashSet<>();
        for (String[] pair : pairs) {
            pairSet.add(pair[0] + "#" + pair[1]);
        }

        for (int i = 0; i < words1.length; ++i) {
            if (!words1[i].equals(words2[i]) && !pairSet.contains(words1[i] + "#" + words2[i])
                    && !pairSet.contains(words2[i] + "#" + words1[i])) {
                return false;
            }
        }

        return true;
    }
}

import java.util.HashMap;
import java.util.Map;

public class SentenceSimilarityII {
    /**
     * Sentence Similarity II
     Given two sentences words1, words2 (each represented as an array of strings), and a list of similar word pairs pairs, determine if two sentences are similar.

     For example, words1 = ["great", "acting", "skills"] and words2 = ["fine", "drama", "talent"] are similar, if the similar word pairs are pairs = [["great", "good"], ["fine", "good"], ["acting","drama"], ["skills","talent"]].

     Note that the similarity relation is transitive. For example, if "great" and "good" are similar, and "fine" and "good" are similar, then "great" and "fine" are similar.

     Similarity is also symmetric. For example, "great" and "fine" being similar is the same as "fine" and "great" being similar.

     Also, a word is always similar with itself. For example, the sentences words1 = ["great"], words2 = ["great"], pairs = [] are similar, even though there are no specified similar word pairs.

     Finally, sentences can only be similar if they have the same number of words. So a sentence like words1 = ["great"] can never be similar to words2 = ["doubleplus","good"].

     Note:

     The length of words1 and words2 will not exceed 1000.
     The length of pairs will not exceed 2000.
     The length of each pairs[i] will be 2.
     The length of each words[i] and pairs[i][j] will be in the range [1, 20].
     */
    public boolean areSentencesSimilarTwo(String[] words1, String[] words2, String[][] pairs) {
        if (words1.length != words2.length) {
            return false;
        }

        Map<String, Integer> index = new HashMap<>();
        int count = 0; 
        for (String[] pair : pairs) {
            for (String p : pair) {
                if (!index.containsKey(p)) {
                    index.put(p, count++); // count : represented as id
                }
            }
        }

        UnionFind u = new UnionFind(index.size());
        for (int i = 0; i < index.size(); i++) {
            u.id[i] = i;
            u.size[i] = 1;
        }
        for (String[] pair : pairs) {
            u.union(index.get(pair[0]), index.get(pair[1]));
        }

        for (int i = 0; i < words1.length; ++i) {
            String w1 = words1[i], w2 = words2[i];
            if (w1.equals(w2)) {
                continue;
            }
            if (!index.containsKey(w1) || 
                !index.containsKey(w2) ||
                u.root(index.get(w1)) != u.root(index.get(w2))) {
                return false;
            }
        }

        return true;
    }
}





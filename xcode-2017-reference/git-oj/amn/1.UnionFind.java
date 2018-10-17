Graph Valid Tree

Given n nodes labeled from 0 to n-1 and a list of undirected edges (each edge is a pair of nodes), write a function to check whether these edges make up a valid tree.


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
        if (size[pi] > size[qi]) {
            id[qi] = pi;
            size[pi] += size[qi];
        } else {
            id[pi] = qi;
            size[qi] += size[pi];
        }
        this.count--;
    }
    
    private int root(int i) {
        while (i != id[i]) {
            id[i] = id[id[i]];
            i = id[i];
        }
        return i;
    }
}



public class Solution {
    public boolean validTree(int n, int[][] edges) {
        UnionFind uf = new UnionFind(n);
        // initialization
        for (int i = 0; i < n; i++) {
            uf.id[i] = i;
            uf.size[i] = 1;
        }
        
        for (int[] edge : edges) {
            int e1 = edge[0];
            int e2 = edge[1]; 
            if (uf.find(e1, e2)) {
                return false;
            }
            
            uf.union(e1, e2);
        }
        
        return uf.count == 1;
    }
}

Number of Connected Components in an Undirected Graph
class UnionFind {
    int[] id, size;
    int count;
    
    public UnionFind(int n) {
        id = new int[n];
        size = new int[n];
        count = n; // DONT FORGET
    }
    
    public boolean find(int p, int q) {
        return root(p) == root(q);
    }
    
    // have to perform find check before union
    public void union(int p, int q) {
        int pi = root(p), qi = root(q);
        if (size[pi] > size[qi]) {
            id[qi] = pi;
            size[pi] += size[qi];
        } else {
            id[pi] = qi;
            size[qi] += size[pi];
        }
        this.count--;
    }
    
    private int root(int i) {
        while (i != id[i]) {
            id[i] = id[id[i]];
            i = id[i];
        }
        return i;
    }
}
public class Solution {
    public int countComponents(int n, int[][] edges) {
        if (n < 0) {
            return 0;
        }
        
        if (edges == null || edges.length == 0 || edges[0].length == 0) {
            return n;
        }
        
        UnionFind uf = new UnionFind(n);
        for (int i = 0; i < n; i++) {
            uf.id[i] = i;
            uf.size[i] = 1;
        }
        
        for (int[] e : edges) {
            if (!uf.find(e[0], e[1])) { // have to perform find check before union
                uf.union(e[0], e[1]);
            }
        }
        
        return uf.count;
    }
}




public class Solution {
    private static final int[][] dirs = new int[][]{
        {1, 0}, {-1, 0}, {0, 1}, {0, -1}    
    };
    
    public void solve(char[][] board) {
        if (board == null || board.length == 0 || board[0].length == 0) {
            return;
        }
        
        int r = board.length;
        int c = board[0].length;
        
        int len = r * c + 1;
        UnionFind uf = new UnionFind(len);
        for (int i = 0; i < len; i++) {
            uf.id[i] = i;
            uf.size[i] = 1;
        }
        int dummy = len - 1;
        for (int i = 0; i < r; i++) {
            for (int j = 0; j < c; j++) {
                if (board[i][j] == 'X') {
                    continue;
                }
                
                int k = i * c + j; 
                if (i == 0 || i == r - 1 || j == 0 || j == c - 1) {
                    if (!uf.find(k, dummy)) {
                        uf.union(k, dummy);    
                    }
                    continue;
                }
                
                for (int[] dir : dirs) {
                    int x = dir[0] + i;
                    int y = dir[1] + j;
                    // no out of boundary issue
                    if (board[x][y] == 'X') {
                        continue;
                    }
                    
                    int t = x * c + y;
                    if (!uf.find(t, k)) {
                        uf.union(t, k);
                    }
                }
            }
        }
        
        System.out.println(uf.count);
        
        for (int i = 0; i < r; i++) {
            for (int j = 0; j < c; j++) {
                if (board[i][j] == 'X') {
                    continue;
                }
                
                int id = i * c + j;
                if (!uf.find(id, dummy)) {
                    board[i][j] = 'X';
                }
            }
        }
    }
}
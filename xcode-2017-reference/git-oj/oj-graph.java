// Network Delay Time
/*
There are N network nodes, labelled 1 to N.

Given times, a list of travel times as directed edges times[i] = (u, v, w), where u is the source node, v is the target node, and w is the time it takes for a signal to travel from source to target.

Now, we send a signal from a certain node K. How long will it take for all nodes to receive the signal? If it is impossible, return -1.

Note:
N will be in the range [1, 100].
K will be in the range [1, N].
The length of times will be in the range [1, 6000].
All edges times[i] = (u, v, w) will have 1 <= u, v <= N and 1 <= w <= 100.
*/ 

/*
We use Dijkstra's algorithm to find the shortest path from our source to all targets.
*/
class Solution {
    public int networkDelayTime(int[][] times, int N, int K) {
        /* graph mapping: source -> {{target, dist}, 
                                      ...
                                     {target, dist}}
         */
        Map<Integer, List<int[]>> graph = new HashMap();
        for (int[] edge: times) { // edges
            List<int[]> targets = graph.computeIfAbsent(edge[0], //
                                      e -> new ArrayList<>());
            targets.add(new int[]{edge[1], edge[2]});
        }
        
        // minheap
        // info1, info2: int[]
        // int[] - size 2
        // * index 0: distance
        // * index 1: target node
        PriorityQueue<int[]> heap = new PriorityQueue<int[]>(
                (info1, info2) -> info1[0] - info2[0]);
        heap.offer(new int[]{0, K});
        
        // mapping: target node - shortest distance from source to target
        Map<Integer, Integer> dist = new HashMap();

        // BFS traversal to find all shortest paths from source to every target
        
        // d in PriorityQueue is defined as distance from source K
        while (!heap.isEmpty()) {
            // every loop, handle the node with min dist to the target
            // source only one - K node
            int[] info = heap.poll(); // dist min
            
            int d = info[0], node = info[1];
            if (dist.containsKey(node)) { 
                continue;
            }
            
            dist.put(node, d); // distinct edges with minimum distance
                               // added to dist map
            
            if (graph.containsKey(node)) {
                for (int[] edge : graph.get(node)) {
                    int nei = edge[0], d2 = edge[1];
                    if (!dist.containsKey(nei)) {
                        // source only one - K node
                        heap.offer(new int[]{d+d2, nei});
                    }
                }
            }
        }
        
        if (dist.size() != N) {
            // some nodes are unreachable from source
            // failed
            return -1;
        }
        
        // answer is the longest distance from source to a target
        // considering all shortest paths
        int longestDistance = 0;
        for (int distance: dist.values()) {
            longestDistance = Math.max(longestDistance, distance);
        }
        return longestDistance;
    }
}
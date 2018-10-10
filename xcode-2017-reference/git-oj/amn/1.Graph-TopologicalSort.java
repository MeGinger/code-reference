Alien Dictionary

class Solution {
    public String alienOrder(String[] words) {
        if (words == null || words.length == 0) {
            return "";
        }
        
        Map<Character, Set<Character>> adj = new HashMap<>();
        Map<Character, Integer> indegree = new HashMap<>();
        
        for (String word : words) {
            for (int i = 0; i < word.length(); i++) {
                indegree.put(word.charAt(i), 0);
            }
        }
        
        for (int i = 0; i < words.length - 1; i++) {
            String cur = words[i];
            String nex = words[i + 1];
            
            // invalid scenario
            // cur: wrtfabc, next: wrtf - IMPOSSIBLE order
            if (cur.length() > nex.length() && cur.startsWith(nex)) { // I FORGOT
                return "";
            }
            
            int min = Math.min(cur.length(), nex.length());
            
            // find the first different character by doing a linear scan from left to right
            for (int j = 0; j < min; j++) {
                char c1 = cur.charAt(j);
                char c2 = nex.charAt(j);
                
                // the first different character
                if (c1 != c2) {
                    Set<Character> set = adj.computeIfAbsent(c1, k -> new HashSet<>());
                    
                    if (!set.contains(c2)) {
                        set.add(c2);
                        indegree.put(c2, indegree.get(c2) + 1);
                    }
                    break; // I FORGOT.....
                }
            }
        }
        
        Queue<Character> queue = new LinkedList<>();
        for (Map.Entry<Character, Integer> entry : indegree.entrySet()) {
            if (entry.getValue() == 0) {
                queue.offer(entry.getKey());
            }
        }
        
        StringBuilder sb = new StringBuilder();
        while (!queue.isEmpty()) {
            char ch = queue.poll(); 
            sb.append(ch);
            
            // this node does not have any out coming edge
            if (!adj.containsKey(ch)) {
                continue;
            }
            
            Set<Character> adjacentNodes = adj.get(ch);
            for (char adjacentNode : adjacentNodes) {
                int count = indegree.get(adjacentNode) - 1;
                indegree.put(adjacentNode, count);
                if (count == 0) {
                    queue.offer(adjacentNode);
                }
            }
        }
        
        // invalid scenario
        // the graph is NOT an acyclic graph and there is some cycle in there.
        // x, y, x
        if (sb.length() < indegree.size()) {
            return "";
        }
        
        return sb.toString();
    }
}
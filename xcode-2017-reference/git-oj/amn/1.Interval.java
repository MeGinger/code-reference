Merge Intervals

class Solution {
    public List<Interval> merge(List<Interval> intervals) {
        if (intervals == null || intervals.size() == 0) {
            return Collections.emptyList();
        }
        
        Collections.sort(intervals, (a, b) -> a.start - b.start);
        
        List<Interval> res = new ArrayList<>();
        int start = intervals.get(0).start;
        int end = intervals.get(0).end;
        for (Interval interval : intervals) {
            if (end < interval.start) {
                res.add(new Interval(start, end));
                start = interval.start;
                end = interval.end;
                continue;
            }
            
            end = Math.max(end, interval.end);
        }
        res.add(new Interval(start, end)); // I FORGOT THE LAST ONE
        return res;
    }
}

class Solution {
    public List<Interval> merge(List<Interval> intervals) {
        if (intervals == null || intervals.size() <= 1) {
            return intervals;
        }
        
        Collections.sort(intervals, new IntervalComparator()); // !!!
        
        List<Interval> results = new ArrayList<Interval>();
        Interval last = intervals.get(0);
        
        for (int i = 1; i < intervals.size(); i++) {
            Interval cur = intervals.get(i);
            
            if (cur.start <= last.end) {
                last.end = Math.max(cur.end, last.end);
            } else {
                results.add(last);
                last = cur;
            }
        }
        
        results.add(last);
        return results;
    }
    
    // !!!
    private class IntervalComparator implements Comparator<Interval> {
        public int compare(Interval a, Interval b) {
            return a.start - b.start;
        }
    }
}


class Solution {
    public List<int[]> getSkyline(int[][] buildings) {
        List<int[]> res = new ArrayList<>();
        if (buildings == null || buildings.length == 0 || buildings[0].length == 0) {
            return res;
        }
        
        List<int[]> heights = new ArrayList<>();
        for (int[] b : buildings) {
            heights.add(new int[]{b[0], -b[2]}); 
            heights.add(new int[]{b[1], b[2]}); // for right points, sorted in increasing order 
        }
        
        Collections.sort(heights, (a, b) -> a[0] != b[0] ? a[0] - b[0] : a[1] - b[1]);
        // location different: non-decreasing order
        // location same: 
        // * both are left, sorted in decreasing order, higher one comes first and should be considered first
        // * both are right, sorted in increasing order, lower one comes first and should be removed first
        // * left and right, left one comes first to avoid error
        //   * examples: right one > left one, if right one remove first, the remaining top might be different from the left one.
        //   * so before removing any one, should put other heights with same location into pq first
        
        // max-heap works as a sweeping line to store all available heights at this point
        Queue<Integer> queue = new PriorityQueue<Integer>((a, b) -> (b - a));
        queue.offer(0); // avoid edge case, pq is empty
        int prev = 0;
        for (int[] h : heights) {
            if (h[1] < 0) {
                queue.offer(-h[1]); // log(n)
            } else {
                queue.remove(h[1]); // log(n)
            }
            
            int cur = queue.peek(); // we are not doing any poll() !
            if (prev != cur) { // cur could be higher or lower than prev
                res.add(new int[]{h[0], cur});
                prev = cur;
            }
        }
        
        return res;
    }
}
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
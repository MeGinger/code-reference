Meeting Room II
class Solution {
    public int minMeetingRooms(Interval[] intervals) {
        if (intervals == null || intervals.length == 0) {
            return 0;
        }
        
        int len = intervals.length;
        int[] starts = new int[len];
        int[] ends = new int[len];
        for (int i = 0; i < len; i++) {
            starts[i] = intervals[i].start;
            ends[i] = intervals[i].end;
        }
        
        Arrays.sort(starts);
        Arrays.sort(ends);
        
        int rooms = 0;
        int endIndex = 0;
        for (int startIndex = 0; startIndex < starts.length; startIndex++) {
            // one loop handles an interval
            if (starts[startIndex] < ends[endIndex]) {
                // counting the number of overlapping intervals/meetings
                // throughout the timeline
                // and recording the maximum
                rooms++;
            } else {
                // one meeting finishes
                // the interval can be put into the rooms
                endIndex++;
            }
        }
        return rooms;
    }

    // follow up: print out all meeting rooms with their assigned intervals
    // ?
    public int minMeetingRooms2(Interval[] intervals) {
        if (intervals == null || intervals.length == 0) {
            return 0;
        }
        Element[] starts = new Element[intervals.length];
        Element[] ends = new Element[intervals.length];
        for (int i = 0; i < intervals.length; i++) {
            starts[i] = new Element(intervals[i].start, i);
            ends[i] = new Element(intervals[i].end, i);
        }
        Arrays.sort(starts);
        Arrays.sort(ends);
        int rooms = 0;
        int endIndex = 0;
        // roomNums[i] is intervals[i]'s room number
        int[] roomNums = new int[intervals.length];
        for (int startIndex = 0; startIndex < starts.length; startIndex++) {
            int currentIndex = starts[startIndex].index;
            if (starts[startIndex].compareTo(ends[endIndex]) < 0) {
                roomNums[currentIndex] = rooms++;
            } else {
                roomNums[currentIndex] = roomNums[ends[endIndex++].index]; 
                // room number of the current original index = room number of the original index of this end index
            }
        }
        Map<Integer, List<Interval>> m = new HashMap<>(rooms);
        for (int i = 0; i < intervals.length; i++) {
            System.out.println(intervals[i] + " room " + roomNums[i]);
            m.computeIfAbsent(roomNums[i], k -> new ArrayList<>()).add(intervals[i]);
        }
        for (int i = 0; i < rooms; i++) {
            System.out.println("Room " + i + " " + m.get(i));
        }
        return rooms;
    }
    
    class Element implements Comparable<Element> {
        int val;
        int index; // wrapper class保留它原有的index，常用套路之一，因为sort会让index丢失
        
        public Element(int v, int i) {
            this.val = v;
            this.index = i;
        }
        
        // used in sorting!!!!! with Comparable<Element>
        public int compareTo(Element other) {
            return Integer.compare(this.val, other.val);
        }
        
        public int getIndex() {
            return this.index;
        }
        
        public int getValue() {
            return this.val;
        }
    }

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


Insert Interval

Given a set of non-overlapping intervals, insert a new interval into the intervals (merge if necessary).
You may assume that the intervals were initially sorted according to their start times.

class Solution {
    public List<Interval> insert(List<Interval> intervals, Interval newInterval) {
        if (intervals == null || newInterval == null) { // intervals can be empty!!!
            return intervals;
        }
        
        List<Interval> results = new ArrayList<Interval>();
        
        int insertPos = 0;
        
        for (Interval interval : intervals) {
            if (interval.end < newInterval.start) {
                results.add(interval);
                insertPos++;
            } else if (newInterval.end < interval.start) { 
                results.add(interval);
            } else { // overlap
                newInterval.start = Math.min(newInterval.start, interval.start);
                newInterval.end = Math.max(newInterval.end, interval.end);
            }
        }
            
        // add newInterval (merged)
        results.add(insertPos, newInterval);
        
        return results;
    }
}
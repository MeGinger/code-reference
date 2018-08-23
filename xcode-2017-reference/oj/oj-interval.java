	// tasks: a bunch of tasks to be executed
	// n: the minimal intervals between two same tasks to be executed in order
	public void leastIntervals(char[] tasks, int n) {
	  int[] c = new int[26]; // assume that tasks are only represented as A to Z
	  for (char task : tasks) {
	      c[task - 'A']++;
	  }
	  Arrays.sort(c); // !
	  int index = c.length - 1;
	  while (index >= 0 && c[index] == c[c.length - 1]) {
	    index--;
	  }
	  
	  return Math.max(tasks.length, (c[c.length - 1] - 1) * (n + 1) + c.length - 1 - index);
	}

	    public int leastInterval(char[] tasks, int n) {
        // thought
           // 
        // diagram
           // tasks = ["A","A","A","B","B","B"], n = 2
           // A -> B -> x -> A -> B -> x -> A -> B - shorter - solution
           // A -> x -> B -> A -> x -> B -> A -> x -> B 
        // code 
        int[] c = new int[26]; 
        for (char t : tasks) {
            c[t - 'A']++; 
        }
        
        Arrays.sort(c);
        int i = 25; // the end index of array c
        while (i >= 0 && c[i] == c[25]) {
            i--;
        }
        // index: 0 1 2 ... 23 24 25
        // value: 0 0 0 ... 2  3  3 
        //                  |
        //                  i    
        
        // why? 
        // situation 1:
        // ABxABxAB
        // |  |  |
        // b1 b2 b3
        // b1: ABx - full blk
        // b2: ABx - full blk
        // b3: AB  - partial blk
        // n+1: the number of intervals in each block 
        // c[25] - 1: the total number of blocks - 1
        // (c[25] - 1) * (n + 1): the total number of intervals of all blocks except the last one (could be full or not full), e.g., the first 2 blocks
        // 25 - i: the number of unique characters that occur most frequently, i.e., the size of last block, e.g., AB
        
        // situation 2:
        // tasks.length
        // ABxABxAB + CDEFGHI
        // in this case, the characters that occurs only once cannot fir into the idle intervals in all the blocks except for the last one
        // go with the tasks length
        
        return Math.max(tasks.length, (c[25] - 1) * (n + 1) + 25 - i);
        
        
        // test case
        
        // run time
    }

    
	/**
	 * Rearrange String k Distance Apart
	 Given a non-empty string s and an integer k, rearrange the string such that the same characters are at least distance k from each other.
	 All input strings are given in lowercase letters. If it is not possible to rearrange the string, return an empty string "".
	 
	 Example 1:
	 s = "aabbcc", k = 3
	 Result: "abcabc"
	 The same letters are at least distance 3 from each other.
	 
	 Example 2:
	 s = "aaabc", k = 3
	 Answer: ""
	 It is not possible to rearrange the string.
	 
	 Example 3:
	 s = "aaadbbcc", k = 2
	 Answer: "abacabcd"
	 
	 Another possible answer is: "abcabcda"
	 The same letters are at least distance 2 from each other.
	 
	 Reference: task scheduler with cool down time https://discuss.leetcode.com/topic/112/minimal-run-time-scheduler/7
	 
	 This is a greedy problem.
	 Every time we want to find the best candidate: which is the character with the largest remaining count. Thus we will be having two arrays.
	 One count array to store the remaining count of every character. Another array to keep track of the most left position that one character can appear.
	 We will iterated through these two array to find the best candidate for every position. Since the array is fixed size, it will take constant time to do this.
	 After we find the candidate, we update two arrays.
	 */

	// thought: first count the frequency of each character in input string (using data structure map)
	// then iterate from index 0 to length - 1, in each loop find the next valid character in this current position which ...
	// "valid" means the distance requirement should be satisfied
	// if not found, means cannot rearrange, return ""
	// otherwise collect and append the valid character to the result.

	// distance is different from interval: distance = interval + 1 
	// space is not allowed, in that case return ""
	public String rearrangeString(String str, int k) {
		int length = str.length();
		// count frequency of each char
		// different from int[26], more general!
		Map<Character, Integer> count = new HashMap<>();
		for (char c : str.toCharArray()) {
			count.put(c, count.getOrDefault(c, 0) + 1);
		}

		// no sorting involved - findValidMax(..) will do the job

		// what valid map does?
		// store character as key and its next valid index as value
		// e.g., "AABBCC", distance = 3
		// "AxxA"
		//  |  |
		//  0123  
		// If A is placed at the index of 0, the next valid index for A will be 3
		// means the next A shoud be placed at index 3 or more.
		// getByDefault(x, 0) - default index is 0
		Map<Character, Integer> valid = new HashMap<>();
		StringBuilder sb = new StringBuilder();
		for (int index = 0; index < length; index++) {
			char candidate = findValidMax(count, valid, index);
			if (candidate == ' ') {
				return "";
			}

			// update count map
			int frequency = count.get(candidate) - 1;
			if (frequency == 0) {
				count.remove(candidate);
			} else {
				count.put(candidate, frequency);
			}


			valid.put(candidate, index + k);
			sb.append(candidate);
		}
		return sb.toString();
	}
	
	// find the next character candidate which is valid to placed at index index
	// and also has max count - handle the max first!
	private char findValidMax(Map<Character, Integer> count, Map<Character, Integer> valid, int index) {
		int max = Integer.MIN_VALUE;
		char candidate = ' ';
		for (Map.Entry<Character, Integer> entry : count.entrySet()) {
			if (entry.getValue() > max && index >= valid.getOrDefault(entry.getKey(), 0)) {
				max = entry.getValue();
				candidate = entry.getKey();
			}
		}
		return candidate;
	}


		/**
	 * Rearrange String k Distance Apart
	 Given a non-empty string s and an integer k, rearrange the string such that the same characters are at least distance k from each other.
	 All input strings are given in lowercase letters. If it is not possible to rearrange the string, return an empty string "".
	 
	 Example 1:
	 s = "aabbcc", k = 3
	 Result: "abcabc"
	 The same letters are at least distance 3 from each other.
	 
	 Example 2:
	 s = "aaabc", k = 3
	 Answer: ""
	 It is not possible to rearrange the string.
	 
	 Example 3:
	 s = "aaadbbcc", k = 2
	 Answer: "abacabcd"
	 
	 Another possible answer is: "abcabcda"
	 The same letters are at least distance 2 from each other.
	 
	 Reference: task scheduler with cool down time https://discuss.leetcode.com/topic/112/minimal-run-time-scheduler/7
	 
	 This is a greedy problem.
	 Every time we want to find the best candidate: which is the character with the largest remaining count. Thus we will be having two arrays.
	 One count array to store the remaining count of every character. Another array to keep track of the most left position that one character can appear.
	 We will iterated through these two array to find the best candidate for every position. Since the array is fixed size, it will take constant time to do this.
	 After we find the candidate, we update two arrays.
	 */

	// thought: first count the frequency of each character in input string (using data structure map)
	// then iterate from index 0 to length - 1, in each loop find the next valid character in this current position which ...
	// "valid" means the distance requirement should be satisfied
	// if not found, means cannot rearrange, return ""
	// otherwise collect and append the valid character to the result.

	// distance is different from interval: distance = interval + 1 
	// space is not allowed, in that case return ""
	public String rearrangeString(String str, int k) {
		int length = str.length();
		// count frequency of each char
		// different from int[26], more general!
		Map<Character, Integer> count = new HashMap<>();
		for (char c : str.toCharArray()) {
			count.put(c, count.getOrDefault(c, 0) + 1);
		}

		// no sorting involved - findValidMax(..) will do the job

		// what valid map does?
		// store character as key and its next valid index as value
		// e.g., "AABBCC", distance = 3
		// "AxxA"
		//  |  |
		//  0123  
		// If A is placed at the index of 0, the next valid index for A will be 3
		// means the next A shoud be placed at index 3 or more.
		// getByDefault(x, 0) - default index is 0
		Map<Character, Integer> valid = new HashMap<>();
		StringBuilder sb = new StringBuilder();
		for (int index = 0; index < length; index++) {
			char candidate = findValidMax(count, valid, index);
			if (candidate == ' ') {
				return "";
			}

			// update count map
			int frequency = count.get(candidate) - 1;
			if (frequency == 0) {
				count.remove(candidate);
			} else {
				count.put(candidate, frequency);
			}


			valid.put(candidate, index + k);
			sb.append(candidate);
		}
		return sb.toString();
	}
	
	// find the next character candidate which is valid to placed at index index
	// and also has max count - handle the max first!
	private char findValidMax(Map<Character, Integer> count, Map<Character, Integer> valid, int index) {
		int max = Integer.MIN_VALUE;
		char candidate = ' ';
		for (Map.Entry<Character, Integer> entry : count.entrySet()) {
			if (entry.getValue() > max && index >= valid.getOrDefault(entry.getKey(), 0)) {
				max = entry.getValue();
				candidate = entry.getKey();
			}
		}
		return candidate;
	}


	/** Interval-related Questions
	 * K Empty Slots
	 * Meeting Rooms
	 * Meeting Rooms II
	 * */

	class Interval {
		int start;
		int end;
		Interval() { start = 0; end = 0; }
		Interval(int s, int e) { start = s; end = e; }
	}
	
	/**
	 * Meeting Rooms - determine if a person could attend all meetings

	 Given an array of meeting time intervals consisting of start and end times [[s1,e1],[s2,e2],...] (si < ei),
	 determine if a person could attend all meetings.
	 
	 For example,
	 Given [[0, 30],[5, 10],[15, 20]],
	 return false.
	 */
	public boolean canAttendMeetings(Interval[] intervals) {
		if (intervals == null) {
			return false;
		}
		
		// Sort the intervals by start time - ascending order
		Arrays.sort(intervals, (a, b) -> Integer.compare(a.start, b.start)); // 使用a - b会overflow
		
		for (int i = 1; i < intervals.length; i++) {
			if (intervals[i].start < intervals[i - 1].end) { // if two intervals overlap, early termination and return false
				return false;
			}
		}
		
		return true;
	}
	
	/**
	 * Meeting Rooms II - find the minimum number of conference rooms required

	 Given an array of meeting time intervals consisting of start and end times [[s1,e1],[s2,e2],...] (si < ei),
	 find the minimum number of conference rooms required.
	 For example,
	 Given [[0, 30],[5, 10],[15, 20]],
	 return 2.
	 Reference: Non-overlapping Intervals
	*/

	/** 
	 o understand why it works, first let’s define two events:
	 Meeting Starts
	 Meeting Ends
	 
	 Next, we acknowledge three facts:
	 The numbers of the intervals give chronological orders
	 When an ending event occurs, there must be a starting event has happened before that, where “happen before” is defined by the chronological orders given by the intervals
	 Meetings that started which haven’t ended yet have to be put into different meeting rooms, and the number of rooms needed is the number of such meetings
	 
	 So, what this algorithm works as follows:
	 
	 for example, we have meetings that span along time as follows:
	 
	 |_____|
	 |______|
	 |________|
	 |_______|
	 
	 Then, the start time array and end time array after sorting appear like follows:
	 
	 ||    ||
	 |   |   |  |
	 
	 Initially, endsItr points to the first end event, and we move i which is the start event pointer.
	 As we examine the start events, we’ll find the first two start events happen before the end event that endsItr points to,
	 so we need two rooms (we magically created two rooms), as shown by the variable rooms.
	 Then, as i points to the third start event, we’ll find that this event happens after the end event pointed by endsItr, then we increment endsItr so that it points to the next end event.
	 What happens here can be thought of as one of the two previous meetings ended, and we moved the newly started meeting into that vacant room, thus we don’t need to increment rooms at this time and move both of the pointers forward.
	 Next, because endsItr moves to the next end event, we’ll find that the start event pointed by i happens before the end event pointed by endsItr.
	 Thus, now we have 4 meetings started but only one ended, so we need one more room. And it goes on as this.
	 */

	// 
	public int minMeetingRooms(Interval[] intervals) {
		if (intervals == null || intervals.length == 0) {
			return 0;
		}
		int[] starts = new int[intervals.length];
		int[] ends = new int[intervals.length];
		for (int i = 0; i < intervals.length; i++) {
			starts[i] = intervals[i].start;
			ends[i] = intervals[i].end;
		}
		Arrays.sort(starts);
		Arrays.sort(ends);
		int rooms = 0;
		int endIndex = 0;
		for (int startIndex = 0; startIndex < starts.length; startIndex++) {
			if (starts[startIndex] < ends[endIndex]) {
				rooms++;
			} else {
				endIndex++;
			}
		}
		return rooms;
	}
	
	// follow up: print out all meeting rooms with their assigned intervals
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
	
	public int minMeetingRooms3(Interval[] intervals) {
		if (intervals == null || intervals.length == 0) {
			return 0;
		}
		
		// Sort the intervals by start time
		Arrays.sort(intervals, new Comparator<Interval>() {
			public int compare(Interval a, Interval b) {
				return Integer.compare(a.start, b.start);
			}
		});
		
		// Use a min heap to track the minimum end time of merged intervals
		PriorityQueue<Interval> heap = new PriorityQueue<>(intervals.length, new Comparator<Interval>() {
			public int compare(Interval a, Interval b) {
				return Integer.compare(a.end, b.end);
			}
		});
		
		// start with the first meeting, put it to a meeting room
		heap.offer(intervals[0]);
		
		for (int i = 1; i < intervals.length; i++) {
			// get the meeting room that finishes earliest
			Interval interval = heap.poll();
			
			if (intervals[i].start >= interval.end) {
				// if the current meeting starts right after
				// there's no need for a new room, merge the interval
				interval.end = intervals[i].end;
			} else {
				// otherwise, this meeting needs a new room
				heap.offer(intervals[i]);
			}
			
			// don't forget to put the meeting room back
			heap.offer(interval);
		}
		
		return heap.size();
	}
	
	
	public static void main (String[] args) {}
}

/* FB onstie 高频 */
/**
 * Given two lists of intervals, find their overlapping intervals
   l1: [1,5], [7,10], [12,18], [22,24]
   l2: [3,8], [13,15], [16,17], [18,21], [22,23]
   returns [3,5],[7,8],[13,15],[16,17],[18,18],[22,23]
 */
public List<Interval> getOverlappingIntervals(List<Interval> l1, List<Interval> l2) {
	if (l1 == null || l2 == null || l1.isEmpty() || l2.isEmpty()) {
		return Collections.emptyList();
	}
	int[] begs = this.merge2Lists(l1, l2, INTERVAL_START_COMPARE);
	int[] ends = this.merge2Lists(l1, l2, INTERVAL_END_COMPARE);
	System.out.println(Arrays.toString(begs));
	System.out.println(Arrays.toString(ends));
	List<Interval> res = new ArrayList<>();
	int count = 0, start = 0;
	for (int i = 0, j = 0; j < ends.length;) {
		if (i < begs.length && begs[i] < ends[j]) {
			if (++count == 2) {
					// enter overlap
				start = begs[i];
			}
			i++;
		} else if (i == begs.length || begs[i] > ends[j]) {
			if (--count == 1) {
					// exit overlap
				res.add(new Interval(start, ends[j]));
			}
			j++;
		} else {
			// begs[i] == ends[j]
			// 要理解一下啊
			// [        ]
			// [    ][     ] 得跟面试官确认下这个情况算不算overlap

			// [        ] if-stmt
			// 	   [       ] if-stmt
			//          [     ] 
			if (count > 1) {
					// already in overlap
				continue;
			}
            //      (ends[j])
            //          |
			// {        } if-stmt
			//          {     }
			//          |
			//      (begs[i])
			res.add(new Interval(begs[i++], ends[j++]));
		}

		// System.out.println(res);
	}
	return res;
}

interface IntervalComparator extends Comparator<Interval> {
	int get(Interval interval);
}

class IntervalStartComparator implements IntervalComparator {
	@Override
	public int compare(Interval o1, Interval o2) {
		return Integer.compare(o1.start, o2.start);
	}

	@Override
	public int get(Interval interval) {
		return interval.start;
	}
}

class IntervalEndComparator implements IntervalComparator {
	@Override
	public int compare(Interval o1, Interval o2) {
		return Integer.compare(o1.end, o2.end);
	}

	@Override
	public int get(Interval interval) {
		return interval.end;
	}
}

private static final IntervalComparator INTERVAL_START_COMPARE = new IntervalStartComparator();
private static final IntervalComparator INTERVAL_END_COMPARE = new IntervalEndComparator();

private int[] merge2Lists(List<Interval> l1, List<Interval> l2, IntervalComparator c) {
	int[] res = new int[l1.size() + l2.size()];
	for (int index = 0, i = 0, j = 0; i < l1.size() || j < l2.size(); index++) {
		if (i == l1.size()) {
			res[index] = c.get(l2.get(j++));
			continue;
		} else if (j == l2.size()) {
			res[index] = c.get(l1.get(i++));
			continue;
		}
		if (c.compare(l1.get(i), l2.get(j)) < 0) {
			res[index] = c.get(l1.get(i++));
		} else {
			res[index] = c.get(l2.get(j++));
		}
	}
	return res;
}


/**
 * Given two lists of intervals, find their overlapping intervals
 // l1: [1,5], [7,10], [12,18], [22,24]
 // l2: [3,8], [13,15], [16,17], [18,21], [22,23]
 // returns [3,5],[7,8],[13,15],[16,17],[18,18],[22,23]
 */
public List<Interval> getOverlappingIntervals(List<Interval> l1, List<Interval> l2) {
	// 套路解法
	if (l1 == null || l2 == null || l1.isEmpty() || l2.isEmpty()) {
		return Collections.emptyList();
	}
	int[] begs = this.merge2Lists(l1, l2, INTERVAL_START_COMPARE);
	int[] ends = this.merge2Lists(l1, l2, INTERVAL_END_COMPARE);
	System.out.println(Arrays.toString(begs));
	System.out.println(Arrays.toString(ends));
	List<Interval> res = new ArrayList<>();
	int count = 0, start = 0;
	for (int i = 0, j = 0; j < ends.length;) {
		if (i < begs.length && begs[i] < ends[j]) {
			if (++count == 2) {
					// enter overlap
				start = begs[i];
			}
			i++;
		} else if (i == begs.length || begs[i] > ends[j]) {
			if (--count == 1) {
					// exit overlap
				res.add(new Interval(start, ends[j]));
			}
			j++;
		} else {
				// begs[i] == ends[j]
			if (count > 1) {
					// already in overlap
				continue;
			}
			res.add(new Interval(begs[i++], ends[j++]));
		}

			// System.out.println(res);
	}
	return res;
}

private static final IntervalComparator INTERVAL_START_COMPARE = new IntervalStartComparator();
private static final IntervalComparator INTERVAL_END_COMPARE = new IntervalEndComparator();

private int[] merge2Lists(List<Interval> l1, List<Interval> l2, IntervalComparator c) {
	int[] res = new int[l1.size() + l2.size()];
	for (int index = 0, i = 0, j = 0; i < l1.size() || j < l2.size(); index++) {
		if (i == l1.size()) {
			res[index] = c.get(l2.get(j++));
			continue;
		} else if (j == l2.size()) {
			res[index] = c.get(l1.get(i++));
			continue;
		}
		if (c.compare(l1.get(i), l2.get(j)) < 0) {
			res[index] = c.get(l1.get(i++));
		} else {
			res[index] = c.get(l2.get(j++));
		}
	}
	return res;
}


	

// airbnb 经典高频

/**
N个员工，每个员工有若干个interval表示在这段时间是忙碌的。求所有员工都不忙的intervals
每个subarray都已经sort好
举例： [
   [[1, 3], [6, 7]],
   [[2, 4]],
   [[2, 3], [9, 12]].
]
返回 [[4, 6], [7, 9]]
	 * Refer: Merge Intervals
	 */
public List<Interval> getFreeIntervals2(List<List<Interval>> schedule) {
	// 套路解法
	int[] begs = this.mergeKLists(schedule, INTERVAL_START_COMPARE);
	int[] ends = this.mergeKLists(schedule, INTERVAL_END_COMPARE);
	System.out.println(Arrays.toString(begs));
	System.out.println(Arrays.toString(ends));
	List<Interval> res = new ArrayList<>();
	int count = 0, start = 0;
	for (int i = 0, j = 0; i < begs.length;) {
		if (begs[i] < ends[j]) {
			if (++count == 1) {
					// exit free time
				if (start > 0) {
					res.add(new Interval(start, begs[i]));
				}
			}
			i++;
		} else if (begs[i] > ends[j]) {
			if (--count == 0) {
					// start free zone
				start = ends[j];
			}
			j++;
		} else {
				// begs[i] == ends[j]
			i++;
			j++;
		}
		System.out.println(res);
	}
	return res;
}

private int[] mergeKLists(List<List<Interval>> lists, IntervalComparator c) {
	Interval[][] res = new Interval[lists.size()][];
	for (int i = 0; i < res.length; i++) {
		List<Interval> l = lists.get(i);
		res[i] = l.stream().toArray(Interval[]::new);
	}
	int end = lists.size() - 1;
	while (end > 0) {
		int beg = 0;
		while (beg < end) {
			res[beg] = merge2Lists(res[beg], res[end], c);
			beg++;
			end--;
		}
	}
	return Arrays.stream(res[0]).mapToInt(interval -> c.get(interval)).toArray();
}

private Interval[] merge2Lists(Interval[] l1, Interval[] l2, IntervalComparator c) {
	Interval[] res = new Interval[l1.length + l2.length];
	for (int index = 0, i = 0, j = 0; i < l1.length || j < l2.length; index++) {
		if (i == l1.length) {
			res[index] = l2[j++];
			continue;
		} else if (j == l2.length) {
			res[index] = l1[i++];
			continue;
		}
		if (c.compare(l1[i], l2[j]) < 0) {
			res[index] = l1[i++];
		} else {
			res[index] = l2[j++];
		}
	}
	return res;
}

import java.util.*;

/**
 * My Calendar I
Implement a MyCalendar class to store your events. A new event can be added if adding the event will not cause a double booking.

Your class will have the method, book(int start, int end). Formally, this represents a booking on the half open interval [start, end), the range of real numbers x such that start <= x < end.

A double booking happens when two events have some non-empty intersection (ie., there is some time that is common to both events.)

For each call to the method MyCalendar.book, return true if the event can be added to the calendar successfully without causing a double booking. Otherwise, return false and do not add the event to the calendar.

Your class will be called like this: MyCalendar cal = new MyCalendar(); MyCalendar.book(start, end)
Example 1:
MyCalendar();
MyCalendar.book(10, 20); // returns true
MyCalendar.book(15, 25); // returns false
MyCalendar.book(20, 30); // returns true
Explanation: 
The first event can be booked.  The second can't because time 15 is already booked by another event.
The third event can be booked, as the first event takes every time less than 20, but not including 20.
Note:

The number of calls to MyCalendar.book per test case will be at most 1000.
In calls to MyCalendar.book(start, end), start and end are integers in the range [0, 10^9].
 */
public class MyCalendar {
	private TreeMap<Integer, Integer> calendar;

	public MyCalendar() {
		this.calendar = new TreeMap<>();
	}

	public boolean book(int start, int end) {
		// .floorKey(key) - returns the greatest key less than or equal to the given key, or null if there is no such key.
		// .ceilingKey(key) - Returns the least key greater than or equal to the given key, or null if there is no such key.
		Integer prev = calendar.floorKey(start), next = calendar.ceilingKey(start);
		if ((prev == null || calendar.get(prev) <= start) && 
			(next == null || end <= next)) {
			calendar.put(start, end);
			return true;
		}
		return false;
	}
}

import java.util.*;

/**
 * My Calendar II - not cause a triple booking
Implement a MyCalendarTwo class to store your events. A new event can be added if adding the event will not cause a triple booking.

Your class will have one method, book(int start, int end). Formally, this represents a booking on the half open interval [start, end), the range of real numbers x such that start <= x < end.

A triple booking happens when three events have some non-empty intersection (ie., there is some time that is common to all 3 events.)

For each call to the method MyCalendar.book, return true if the event can be added to the calendar successfully without causing a triple booking. Otherwise, return false and do not add the event to the calendar.

Your class will be called like this: MyCalendar cal = new MyCalendar(); MyCalendar.book(start, end)

Example 1:
MyCalendar();
MyCalendar.book(10, 20); // returns true
MyCalendar.book(50, 60); // returns true
MyCalendar.book(10, 40); // returns true
MyCalendar.book(5, 15);  // returns false
MyCalendar.book(5, 10);  // returns true
MyCalendar.book(25, 55); // returns true

Explanation: 
The first two events can be booked.  The third event can be double booked.
The fourth event (5, 15) can't be booked, because it would result in a triple booking.
The fifth event (5, 10) can be booked, as it does not use time 10 which is already double booked.
The sixth event (25, 55) can be booked, as the time in [25, 40) will be double booked with the third event;
the time [40, 50) will be single booked, and the time [50, 55) will be double booked with the second event.
Note:

The number of calls to MyCalendar.book per test case will be at most 1000.
In calls to MyCalendar.book(start, end), start and end are integers in the range [0, 10^9].
*/
public class MyCalendarTwo {
	private TreeMap<Integer, Integer> delta;
	private int maxStart;

	public MyCalendarTwo() {
		this.delta = new TreeMap<>();
	}

	// MyCalendar.book(10, 20); // returns true
	// MyCalendar.book(50, 60); // returns true
	// MyCalendar.book(10, 40); // returns true
	// MyCalendar.book(5, 15);  // returns false
	// MyCalendar.book(5, 10);  // returns true
	// MyCalendar.book(25, 55); // returns true
	public boolean book(int start, int end) {
		this.delta.put(start, this.delta.getOrDefault(start, 0) + 1);
		this.delta.put(end, this.delta.getOrDefault(end, 0) - 1);
		this.maxStart = Math.max(this.maxStart, start);

		int active = 0;
		for (Map.Entry<Integer, Integer> entry : this.delta.entrySet()) {
			int k = entry.getKey();
			int d = entry.getValue();

			active += d;
			if (active >= 3) { // active booking more than 2
				// revert
				this.delta.put(start, delta.get(start) - 1);
				this.delta.put(end, delta.get(end) + 1);
				if (this.delta.get(start) == 0) {
					delta.remove(start); // this line can be commented out

					// Do we have to remove(end)? No
					// there may be multiple ends
					// this.delta.get(start) may be larger than 0
				}
				return false;
			}

			// early terminate on the last start
			// don't have to deal with the rest of end parts
			if (d > 0 && k == this.maxStart) { 
				break;
			}
		}

		return true;
	}
}

import java.util.*;

/**
 * My Calendar III
Implement a MyCalendarThree class to store your events. A new event can always be added.

Your class will have one method, book(int start, int end). Formally, this represents a booking on the half open interval [start, end), the range of real numbers x such that start <= x < end.

A K-booking happens when K events have some non-empty intersection (ie., there is some time that is common to all K events.)

For each call to the method MyCalendar.book, return an integer K representing the largest integer such that there exists a K-booking in the calendar.

Your class will be called like this: MyCalendarThree cal = new MyCalendarThree(); MyCalendarThree.book(start, end)
Example 1:
MyCalendarThree();
MyCalendarThree.book(10, 20); // returns 1
MyCalendarThree.book(50, 60); // returns 1
MyCalendarThree.book(10, 40); // returns 2
MyCalendarThree.book(5, 15); // returns 3
MyCalendarThree.book(5, 10); // returns 3
MyCalendarThree.book(25, 55); // returns 3
Explanation: 
The first two events can be booked and are disjoint, so the maximum K-booking is a 1-booking.
The third event [10, 40) intersects the first event, and the maximum K-booking is a 2-booking.
The remaining events cause the maximum K-booking to be only a 3-booking.
Note that the last event locally causes a 2-booking, but the answer is still 3 because
eg. [10, 20), [10, 40), and [5, 15) are still triple booked.
Note:

The number of calls to MyCalendarThree.book per test case will be at most 400.
In calls to MyCalendarThree.book(start, end), start and end are integers in the range [0, 10^9]
 */
public class MyCalendarThree {
	private TreeMap<Integer, Integer> delta;
	private int maxStart;

	public MyCalendarThree() {
		this.delta = new TreeMap<>();
	}

	public int book(int start, int end) {
		this.delta.put(start, delta.getOrDefault(start, 0) + 1);
		this.delta.put(end, delta.getOrDefault(end, 0) - 1);
		this.maxStart = Math.max(this.maxStart, start);

		int active = 0, ans = 0;
		for (Map.Entry<Integer, Integer> entry : this.delta.entrySet()) {
			int k = entry.getKey();
			int d = entry.getValue();

			active += d;
			ans = Math.max(ans, active);

			// early terminate
			if (d > 0 && k == this.maxStart) {
				break;
			}
		}

		return ans;
	}

	public static void main(String[] args) {
		MyCalendarThree cal = new MyCalendarThree();
		cal.book(10, 20);
		cal.book(50, 60);
		cal.book(10, 40);
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





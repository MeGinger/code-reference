// Copy List with Random Pointer

/**
 * Definition for singly-linked list with a random pointer.
 * class RandomListNode {
 *     int label;
 *     RandomListNode next, random;
 *     RandomListNode(int x) { this.label = x; }
 * };
 */

Solution 1 - map to store a mapping between old and new nodes based on the next property, as a linear list
public RandomListNode copyRandomList(RandomListNode head) {
    // NEW/VAL: dummy/pre -> O -> O -> O -> O -> O -> O
    //                       |    |    |    |    |    |
    // OLD/KEY:              O -> O -> O -> O -> O -> O
    
    // invalid input
    if (head == null) {
        return null; 
    }
    
    Map<RandomListNode, RandomListNode> map = new HashMap<>();
    RandomListNode dummy = new RandomListNode(0);
    RandomListNode pre = dummy, newNode;        
    
    while (head != null) {
        // if (map.containsKey(head)) {
        //     newNode = map.get(head);
        // } else {
        //     newNode = new RandomListNode(head.label);
        //     map.put(head, newNode);
        // }
        
        newNode = map.computeIfAbsent(head, k -> new RandomListNode(k.label));
        
        if (head.random != null) {
            // if (map.containsKey(head.random)) {
            //     newNode.random = map.get(head.random);
            // } else {
            //     newNode.random = new RandomListNode(head.random.label);
            //     map.put(head.random, newNode.random);
            // }
            newNode.random = map.computeIfAbsent(head.random, k -> new RandomListNode(k.label));
        }
        
        // Actually we do this only for connecting dummy with the first node....
        pre.next = newNode;
        pre = newNode;
        
        head = head.next;
    }
    
    return dummy.next;
}
// time complexity: O(n)
// space complexity: O(n) ? or O(1)

Solution 2 - without map
    public RandomListNode copyRandomList(RandomListNode head) {
        if (head == null) {
            return null;
        }
        
        copyNext(head);
        copyRandom(head);
        return splitList(head);
    }

	// 0 -> 1  -> 2 -> 3  -> 4
	// 0 -> 0' -> 1 -> 1' -> 2 -> 2' -> 3 -> 3' -> 4 -> 4'
	// random are shared
    private void copyNext(RandomListNode head) {
        while (head != null)  {
           RandomListNode newNode = new RandomListNode(head.label);
           newNode.random = head.random;
           newNode.next = head.next;
           head.next = newNode;
           head = head.next.next;
       }
    }
    
    // random are ready and copied
    private void copyRandom(RandomListNode head) {
        while (head != null) {
            if (head.next.random != null) {
                head.next.random = head.random.next;
            }
            head = head.next.next;
        }
    }
    
    // split list into 2 list
    private RandomListNode splitList(RandomListNode head) {
        RandomListNode newHead = head.next;
        while (head != null) {
            RandomListNode temp = head.next;

            // restore old list
            head.next = temp.next;
            head = head.next;
            
            // reconstruct new list
            if (temp.next != null) { // temp might be the last node
                temp.next = temp.next.next;
            }
        }
        return newHead;
    }  

    // time complexity: O(NlogK)
    // N: the count of nodes
    // K: the count of lists
    public ListNode mergeKLists(ListNode[] lists) {
        if (lists == null || lists.length == 0) {
            return null;
        }
        
        // first argument: size of priorityqueue
        PriorityQueue<ListNode> queue = new PriorityQueue<ListNode>(lists.length, 
            (o1, o2) -> o1.val - o2.val); // min-heap
        
        ListNode dummy = new ListNode(0);
        ListNode tail = dummy;
        for (ListNode node : lists) {
            if (node != null) {
                queue.offer(node);
            }
        }
        
        while (!queue.isEmpty()) {
            ListNode node = queue.poll();
            tail.next = node;
            tail = tail.next;
            
            if (node.next != null) {
                queue.offer(node.next);
            }
        }
        
        return dummy.next;
    }

// Find Median from Data Stream 
// Design a data structure that supports the following two operations:
// void addNum(int num) - Add a integer number from the data stream to the data structure.
// double findMedian() - Return the median of all elements so far.

    PriorityQueue<Integer> minHeap = null; // right
    PriorityQueue<Integer> maxHeap = null; // left
    
    /** initialize your data structure here. */
    public MedianFinder() {
        this.minHeap = new PriorityQueue<>();
        this.maxHeap = new PriorityQueue<>((a, b) -> b - a);
    }
    
    public void addNum(int num) {
        // add number
        if (maxHeap.isEmpty() || num <= maxHeap.peek()) { // dont miss maxHeap.isEmpty()
            maxHeap.offer(num);
        } else {
            minHeap.offer(num);
        }
        
        // keep two heaps balanced
        if (maxHeap.size() - minHeap.size() > 1) { // diff == 2
            minHeap.offer(maxHeap.poll()); // diff == 0
        } else if (minHeap.size() - maxHeap.size() > 1) { // diff  == 2
            maxHeap.offer(minHeap.poll()); // diff == 0
        }
    }
    
    public double findMedian() {
        if (minHeap.isEmpty() && maxHeap.isEmpty()) {
            return 0;
        }
        
        if (maxHeap.size() > minHeap.size()) { // diff == 1
            return maxHeap.peek();
        } else if (minHeap.size() > maxHeap.size()) { // diff == 1
            return minHeap.peek();
        } else { // diff == 0
            return (maxHeap.peek() + minHeap.peek()) / 2.0;
        }
    }


Add Two Numbers 
Input: (2 -> 4 -> 3) + (5 -> 6 -> 4)
Output: 7 -> 0 -> 8
Explanation: 342 + 465 = 807.
class Solution {
    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        if (l1 == null || l2 == null) {
            return null;
        }
        
        ListNode dummy = new ListNode(0);
        ListNode tail = dummy;
        int carry = 0;
        while (l1 != null || l2 != null) { // || 
            int v1 = l1 == null ? 0 : l1.val;
            int v2 = l2 == null ? 0 : l2.val;
            int sum = v1 + v2 + carry;
            
            carry = sum / 10;
            tail.next = new ListNode(sum % 10);
            tail = tail.next;
            
            l1 = l1 == null ? null : l1.next;
            l2 = l2 == null ? null : l2.next;
        }
        
        tail.next = carry == 0 ? null : new ListNode(carry);
        
        return dummy.next;
    }
}

Sliding Window Maximum
/* 
 LinkedList
 - getLast(), addLast(a), removeLast()
 - getFirst(), addFirst(a), removeFirst()
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
        // take care of the previous window
        result[i - k] = l.getFirst(); 
        if (nums[i - k] == l.getFirst()) {
            l.removeFirst();
        }

        // build new window
        addToSlidingWindow(nums[i], l);
    }
    // takes care of last one
    result[n - k] = l.getFirst();

    return result;
}

// l is a non-increasing list
// head is the maximum!!
private void addToSlidingWindow(int a, LinkedList<Integer> l) {
    while (!l.isEmpty() && l.getLast() < a) {
        l.removeLast();
    }
    l.addLast(a);
}
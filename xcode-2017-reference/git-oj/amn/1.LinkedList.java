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

Add Two Numbers II
Input: (7 -> 2 -> 4 -> 3) + (5 -> 6 -> 4)
Output: 7 -> 8 -> 0 -> 7
class Solution {
    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        if (l1 == null || l2 == null) {
            return null;
        }
        
        ListNode dummy = new ListNode(0);
        ListNode cur = dummy;
        
        l1 = reverse(l1);
        l2 = reverse(l2);
        
        int carry = 0;
        while (l1 != null || l2 != null) {
            int v1 = l1 != null ? l1.val : 0;
            int v2 = l2 != null ? l2.val : 0;
            
            int sum = v1 + v2 + carry;
            carry = sum / 10;
            cur.next = new ListNode(sum % 10);
            cur = cur.next;
            
            l1 = l1 == null ? l1 : l1.next;
            l2 = l2 == null ? l2 : l2.next;
        }
        cur.next = carry == 0 ? null : new ListNode(carry);
        
        return reverse(dummy.next);
    }
    
    private ListNode reverse(ListNode head) {
        ListNode pre = null;
        while (head != null) {
            ListNode nex = head.next;
            head.next = pre;
            pre = head;
            head = nex;
        }
        return pre;
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


Reverse Linked List

class Solution {
    public ListNode reverseList(ListNode head) {
        
        if (head == null) {
            return head;
        }
        
        // pre    head
        // null   1 -> 2 -> 3 -> 4
        
        ListNode pre = null
        while (head != null) {
            ListNode nex = head.next;
            head.next = pre;
            pre = head;
            head = nex;
        }    
            
        return pre;
    }
}

Merge Two Sorted Lists

class Solution {
    public ListNode mergeTwoLists(ListNode l1, ListNode l2) {
        // below can cover all edge cases like l1 or l2 is/are null
        ListNode dummy = new ListNode(0);
        ListNode tail = dummy;
        
        while (l1 != null && l2 != null) {
            if (l1.val < l2.val) {
                tail.next = l1;
                l1 = l1.next;
            } else {
                tail.next = l2;
                l2 = l2.next;
            }
            tail = tail.next;
        }
        
        if (l1 != null) {
            tail.next = l1;   
        } else { // l2 == null or l2 != null
            tail.next = l2; 
        }
        
        return dummy.next;
    }
}


// Palindrome Linked List
class Solution {
    public boolean isPalindrome(ListNode head) {
        if (head == null) {
            return true;
        }
        
        // 1->2->3     => 2 // odd
        // 1->2->3->4  => 2 // even
        ListNode middle = middle(head);
        middle.next = reverse(middle.next);
        
        ListNode p = head, q = middle.next;
        while (p != null && q != null && p.val == q.val) {
            p = p.next;
            q = q.next;
        }
        
        return q == null; // means both two halves are evaluated and done
    }
    
    private ListNode middle(ListNode head) {
        ListNode slow = head;
        ListNode fast = head.next;
        
        while (fast != null) {
            if (fast.next == null) {
                return slow; // even
            }
            slow = slow.next;
            fast = fast.next.next;
        }
        return slow; // odd
    }
    
    private ListNode reverse(ListNode head) {
        ListNode pre = null; // I FORGET initialization !
        while (head != null) {
            ListNode next = head.next;
            head.next = pre;
            pre = head;
            head = next;
        }
        
        return pre;
    }
}

// Linked List Cycle
Given a linked list, determine if it has a cycle in it.

public boolean hasCycle(ListNode head) {
    if (head == null) {
        return false;
    }
    // head.next == null can be eliminated since it will be checked in the 1st loop
    
    ListNode slow = head;
    ListNode fast = head.next;
    // slow cannot be null always!!
    while (fast != slow) {
        if (fast == null || fast.next == null) {
            return false;
        }

        slow = slow.next;
        fast = fast.next.next;
    }
    
    return true;
}

// Intersection of Two Linked Lists
public class Solution {
    public ListNode getIntersectionNode(ListNode headA, ListNode headB) {
        if(headA == null || headB == null) return null;

        ListNode a = headA;
        ListNode b = headB;

        // if a & b have different len, then we will stop the loop after second iteration
        while(a != b){
            a = a == null? headB : a.next;
            b = b == null? headA : b.next;    
        }
        // a == b, either null or not null
        return a;
    }
}


Reverse Nodes in k-Group
Given this linked list: 1->2->3->4->5
For k = 2, you should return: 2->1->4->3->5
For k = 3, you should return: 3->2->1->4->5


Swap Nodes in Pairs

Given a linked list, swap every two adjacent nodes and return its head.
Given 1->2->3->4, you should return the list as 2->1->4->3.

class Solution {
    public ListNode swapPairs(ListNode head) {
        ListNode dummy = new ListNode(0);
        dummy.next = head;
        ListNode cur = dummy;
        
        while (cur.next != null && cur.next.next != null) {
            ListNode first = cur.next;
            ListNode second = cur.next.next;
            first.next = second.next;
            cur.next = second;
            second.next = first;
            cur = first;
        }
        
        return dummy.next;
    }
}


class Solution {
    public ListNode[] splitListToParts(ListNode root, int k) {
        ListNode[] parts = new ListNode[k];
        int len = 0;
        for (ListNode node = root; node != null; node = node.next) {
            len++;
        }
        int n = len / k, r = len % k;
        // n : minimum guaranteed part size; 
        // r : extra nodes spread to the first r parts;
        
        ListNode node = root, cur = null; // every reference variable should be initialized
        // node != null: some bucket might be empty - edge case
        for (int i = 0; node != null && i < k; i++, r--) {
            parts[i] = node;
            for (int j = 0; j < n + (r > 0 ? 1 : 0); j++) {
                cur = node;
                node = node.next; // node is next node 
            }
            cur.next = null; 
        }
        
        return parts;
    }
}


Reorder List

Given a singly linked list L: L0→L1→…→Ln-1→Ln,
reorder it to: L0→Ln→L1→Ln-1→L2→Ln-2→…

/**
 * Definition for singly-linked list.
 * public class ListNode {
 *     int val;
 *     ListNode next;
 *     ListNode(int x) { val = x; }
 * }
 */
class Solution {
    public void reorderList(ListNode head) {
        if (head == null) {
            return;
        }
        
        ListNode middle = findMiddle(head);
        ListNode second = reverse(middle.next);
        middle.next = null;
        
        merge(head, second);
    }
    
    private ListNode merge(ListNode a, ListNode b) {
        ListNode dummy = new ListNode(0);
        ListNode cur = dummy;
        
        while (a != null && b != null) {
            cur.next = a;
            a = a.next;
            cur.next.next = b;
            b = b.next;
            cur = cur.next.next;
        }
        
        if (a != null) {
            cur.next = a;
        }
        if (b != null) {
            cur.next = b;
        }
        return dummy.next;
    }
    
    private ListNode findMiddle(ListNode head) {
        
        ListNode slow = head;
        ListNode fast = head.next;
        while (fast != null) {
            if (fast.next == null) {
                return slow; // even
            }
            
            fast = fast.next.next;
            slow = slow.next;
        }
        return slow;
    }
    
    private ListNode reverse(ListNode head) {
        ListNode pre = null;
        while (head != null) {
            ListNode nex = head.next;
            head.next = pre;
            pre = head;
            head = nex;
        }
        return pre;
    }
}





Remove Nth Node From End of List


// Copy List with Random Pointer

/*
A linked list is given such that each node contains an additional random pointer which could point to any node in the list or null.
Return a deep copy of the list.
*/

// solution 1 - map to store a mapping between old and new nodes based on the next property, as a linear list

/**
 * Definition for singly-linked list with a random pointer.
 * class RandomListNode {
 *     int label;
 *     RandomListNode next, random;
 *     RandomListNode(int x) { this.label = x; }
 * };
 */
public class Solution {
    public RandomListNode copyRandomList(RandomListNode head) {
        
        if (head == null) {
            return null;
        }
        
        Map<RandomListNode, RandomListNode> map = new HashMap<>();
        RandomListNode dummy = new RandomListNode(0);
        RandomListNode pre = dummy, newNode;
        // pre: prev node of new list
        // newNode : cur node of new list
        
        while (head != null) {
            // head & newNode
            if (map.containsKey(head)) {
                newNode = map.get(head);
            } else {
                newNode = new RandomListNode(head.label);
                map.put(head, newNode);
            }
            
            if (head.random != null) {
                if (map.containsKey(head.random)) {
                    newNode.random = map.get(head.random);
                } else {
                    newNode.random
                        = new RandomListNode(head.random.label);
                    map.put(head.random, newNode.random);
                }
            }
            
            // pre & head move on the next step
            pre.next = newNode;
            pre = newNode;
            head = head.next;
        }
        
        return dummy.next;
    }
}
// time complexity: O(n)
// space complexity: O(n)



// solution 2: without map
/**
 * Definition for singly-linked list with a random pointer.
 * class RandomListNode {
 *     int label;
 *     RandomListNode next, random;
 *     RandomListNode(int x) { this.label = x; }
 * };
 */
public class Solution {
    public RandomListNode copyRandomList(RandomListNode head) {
        if (head == null) {
            return null;
        }
        
        copyNext(head);
        copyRandom(head);
        return splitList(head);
    }
    
    private void copyNext(RandomListNode head) {
        while (head != null)  {
           RandomListNode newNode = new RandomListNode(head.label);
           newNode.random = head.random;
           newNode.next = head.next;
           head.next = newNode;
           head = head.next.next;
       }
    }
    
    private void copyRandom(RandomListNode head) {
        while (head != null) {
            if (head.next.random != null) {
                head.next.random = head.random.next;
            }
            head = head.next.next;
        }
    }
    
    private RandomListNode splitList(RandomListNode head) {
        RandomListNode newHead = head.next;
        while (head != null) {
            RandomListNode temp = head.next;
            head.next = temp.next;
            head = head.next;
            
            if (temp.next != null) { // temp might be the last node
                temp.next = temp.next.next;
            }
        }
        return newHead;
    }   
}

time complexity: O(3n)
space complexity: O(1)

ian40

LeetCode
 217 50

725. Split Linked List in Parts
Description
Hints
Submissions
Discuss
Solution
Pick One
Given a (singly) linked list with head node root, write a function to split the linked list into k consecutive linked list "parts".

The length of each part should be as equal as possible: no two parts should have a size differing by more than 1. This may lead to some parts being null.

The parts should be in order of occurrence in the input list, and parts occurring earlier should always have a size greater than or equal parts occurring later.

Return a List of ListNode's representing the linked list parts that are formed.

Examples 1->2->3->4, k = 5 // 5 equal parts [ [1], [2], [3], [4], null ]
Example 1:
Input: 
root = [1, 2, 3], k = 5
Output: [[1],[2],[3],[],[]]
Explanation:
The input and each element of the output are ListNodes, not arrays.
For example, the input root has root.val = 1, root.next.val = 2, \root.next.next.val = 3, and root.next.next.next = null.
The first element output[0] has output[0].val = 1, output[0].next = null.
The last element output[4] is null, but it's string representation as a ListNode is [].
Example 2:
Input: 
root = [1, 2, 3, 4, 5, 6, 7, 8, 9, 10], k = 3
Output: [[1, 2, 3, 4], [5, 6, 7], [8, 9, 10]]
Explanation:
The input has been split into consecutive parts with size difference at most 1, and earlier parts are a larger size than the later parts.
Note:

The length of root will be in the range [0, 1000].
Each value of a node in the input will be an integer in the range [0, 999].
k will be an integer in the range [1, 50].
Seen this question in a real interview before?  YesNo
Companies   
Google 2Amazon 2
Related Topics 
Linked List
Similar Questions 
Rotate ListOdd Even Linked List
Java    




/**
 * Definition for singly-linked list.
 * public class ListNode {
 *     int val;
 *     ListNode next;
 *     ListNode(int x) { val = x; }
 * }
 */
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
        
        ListNode node = root, cur = null; // dont forget to initialize 
        // outer loop: array
        for (int i = 0; node != null && i < k; i++, r--) {
            parts[i] = node;

            // inner loop: sub list

            // j starts from 0!, though the head is assigned to res
            // also no need for condition (node != null), 
            // since it is fixed
            for (int j = 0; j < n + (r > 0 ? 1 : 0); j++) { 
                cur = node;
                node = node.next;
            }
            cur.next = null; // remove connections to original next 
        }
        return parts;
    }
}

// Palindrome Linked List
/* Given a singly linked list, determine if it is a palindrome.
  Could you do it in O(n) time and O(1) space?
 */
/**
 * Definition for singly-linked list.
 * public class ListNode {
 *     int val;
 *     ListNode next;
 *     ListNode(int x) { val = x; }
 * }
 */

Time: O(n) 
Space: O(1)
class Solution {
    // 经典台词 关于input被修改的
    // this code would destroy the original structure of the linked list
    // if you do not want to destroy the structure, you can reverse the second part back
    
    public boolean isPalindrome(ListNode head) {
        if (head == null) {
            return true;
        }
        
        ListNode middle = findMiddle(head);
        middle.next = reverse(middle.next);

        ListNode p1 = head, p2 = middle.next;
        while (p1 != null && p2 != null && p1.val == p2.val) {
            p1 = p1.next;
            p2 = p2.next;
        }

        // reverse the second part back
        middle.next = reverse(middle.next);
        
        return p2 == null; // which means
    }
    
    // odd  3->5->7 return 5
    // even 3->5->7->9 return 5
    private ListNode findMiddle(ListNode head) {
        ListNode slow = head;
        ListNode fast = head.next;
        
        while (fast != null) { // if terminated here, len of list is odd
            if (fast.next == null) { 
                return slow; // if terminated here, len of list is even    
            }
            
            slow = slow.next;
            fast = fast.next.next;
        }
        
        return slow;
    }

    // Middle of the Linked List
    // odd  3->5->7 return 5
    // even 3->5->7->9 return 7
    public ListNode middleNode(ListNode head) {
        if (head == null) {
            return null;
        }
        
        ListNode slow = head, fast = head.next;
        while (fast != null) { // odd
            if (fast.next == null) { // even
                return slow.next; // next
            }
            
            slow = slow.next;
            fast = fast.next.next;
        }
        
        return slow;
    }
    
    private ListNode reverse(ListNode node) {
        ListNode pre = null;
        /*
          pre      node
           |        |
          null      3 -> 4 -> 5 -> null
          
                 pre   node
                  |     |
          null <- 3     4 -> 5 -> null

                            pre    node
                             |      |
          null <- 3 <-  4 <- 5     null
         */
        while (node != null) {
            ListNode nex = node.next;
            node.next = pre;
            pre = node;
            node = nex;
        }
        
        return pre;
    }
}

// Merge k Sorted Lists
// Merge k sorted linked lists and return it as one sorted list. Analyze and describe its complexity.
class Solution {
    // timw complexity: lists.length * count of all nodes
    // using priority queue is O(nlgk), 
    public ListNode mergeKLists(ListNode[] lists) {
        if (lists == null || lists.length == 0) {
            return null;
        }
        
        PriorityQueue<ListNode> queue = new PriorityQueue<ListNode>(lists.length, 
            (o1, o2) -> {return o1.val - o2.val;}); // min-heap
        
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

    // Just find min of array heads using O(nk). 
    public ListNode mergeKLists(ListNode[] lists) {
        if (lists == null || lists.length == 0) {
            return null;
        }
        
        ListNode dummy = new ListNode(0);
        ListNode head = dummy;
        while (true) {
            ListNode node = smallest(lists);
            
            if (node == null) {
                break;
            }
            
            head.next = node;
            head = node;
        }
        
        return dummy.next;
    }
    
    private ListNode smallest(ListNode[] lists) {
        int min = Integer.MAX_VALUE;
        ListNode minNode = null;
        int minIndex = -1;
        for (int i = 0; i < lists.length; i++) {
            if (lists[i] != null && lists[i].val < min) {
                minNode = lists[i];
                min = lists[i].val;
                minIndex = i;
            }
        }
        if (minNode == null) {
            return null;
        }
        
        lists[minIndex] = lists[minIndex].next;
        return minNode;
    }

    // using mergesort is O(nklgk)
    // sorting is the worst solution...
}

// Add Two Numbers

/*
You are given two non-empty linked lists representing two non-negative integers. The digits are stored in reverse order and each of their nodes contain a single digit. Add the two numbers and return it as a linked list.

You may assume the two numbers do not contain any leading zero, except the number 0 itself.

Example:

Input: (2 -> 4 -> 3) + (5 -> 6 -> 4)
Output: 7 -> 0 -> 8
Explanation: 342 + 465 = 807.
 */
    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        if (l1 == null || l2 == null) {
            return null;
        }
        
        ListNode dummy = new ListNode(0);
        ListNode tail = dummy;
        int carry = 0;
        while (l1 != null || l2 != null) {
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

// Intersection of Two Linked Lists
/*
Write a program to find the node at which the intersection of two singly linked lists begins.
For example, the following two linked lists:

A:          a1 → a2
                   ↘
                     c1 → c2 → c3
                   ↗            
B:     b1 → b2 → b3
begin to intersect at node c1.
*/

/*
We can use two iterations to do that. 

In the first iteration, we will reset the pointer of one linkedlist to the head of another linkedlist after it reaches the tail node. 

In the second iteration, we will move two pointers until they points to the same node. 

Our operations in first iteration will help us counteract the difference. 

So if two linkedlist intersects, the meeting point in second iteration must be the intersection point. 
If the two linked lists have no intersection at all, 
then the meeting pointer in second iteration must be the tail node of both lists, which is null
 */
public class Solution {
    public ListNode getIntersectionNode(ListNode headA, ListNode headB) {
        // boundary check
        if(headA == null || headB == null) return null;

        ListNode a = headA;
        ListNode b = headB;

        // if a & b have different len, then we will stop the loop after second iteration
        while(a != b){
            // for the end of first iteration, 
            // we just reset the pointer to the head of another linkedlist
            a = a == null? headB : a.next;
            b = b == null? headA : b.next;    
        }

        return a;
    }
}

Plus One Linked List
/*
Given a non-negative integer represented as non-empty a singly linked list of digits, plus one to the integer.
You may assume the integer do not contain any leading zero, except the number 0 itself.
The digits are stored such that the most significant digit is at the head of the list.

Example :
Input: [1,2,3]
Output: [1,2,4]
*/
    public ListNode plusOne(ListNode head) {
        if (head == null) {
            return null;
        }
        
        head = reverse(head);
        
        ListNode node = head, pre = null;
        int carry = 1;
        while (node != null) {
            int sum = node.val + carry;
            node.val = sum % 10;
            carry = sum / 10;
            pre = node;
            node = node.next;
        }
        
        if (carry != 0) {
            pre.next = new ListNode(carry);
        }
        
        return reverse(head);
    }
    
    private ListNode reverse(ListNode head) {
        ListNode pre = null;
        while (head != null) {
            ListNode next = head.next;
            head.next = pre;
            pre = head;
            head = next;
        }
        
        return pre;
    }


Linked List Cycle
/*
Given a linked list, determine if it has a cycle in it.
Follow up:
Can you solve it without using extra space?
*/
public class Solution {
    /**
     * Returns boolean value indicating if list has cycle
     * @param head a head node of a list
     */
    public boolean hasCycle(ListNode head) {
        // edge case: list has 0 or 1 node
        // Note that head.next == null, so it cant be a circular list with 1 node
        if (head == null || head.next == null) {
            return false;
        }
        // head.next == null can be eliminated since it will be checked in the 1st loop
        
        ListNode fast = head.next;
        ListNode slow = head;
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
}



Populating Next Right Pointers in Each Node
/*
Given a binary tree
struct TreeLinkNode {
  TreeLinkNode *left;
  TreeLinkNode *right;
  TreeLinkNode *next;
}
Populate each next pointer to point to its next right node. If there is no next right node, the next pointer should be set to NULL.
Initially, all next pointers are set to NULL.

Note:
You may only use constant extra space. -- using queue is not right
Recursive approach is fine, implicit stack space does not count as extra space for this problem.
You may assume that it is a perfect binary tree (ie, all leaves are at the same level, and every parent has two children).
*/


    // PERFECT BINARY TREE
    public void connect(TreeLinkNode root) {
        while (root != null) {
            TreeLinkNode cur = root;
            // level order traversal
            while (cur != null) { 
                // 往后面怼
                if (cur.left != null) {
                    cur.left.next = cur.right;    
                }
                if (cur.right != null && cur.next != null) {
                    cur.right.next = cur.next.left;    
                } 
                cur = cur.next;
            }
            
            root = root.left; // perfect binary tree
        }
        
    }


Populating Next Right Pointers in Each Node II
/*
Given a binary tree

struct TreeLinkNode {
  TreeLinkNode *left;
  TreeLinkNode *right;
  TreeLinkNode *next;
}
Populate each next pointer to point to its next right node. If there is no next right node, the next pointer should be set to NULL.

Initially, all next pointers are set to NULL.

Note:

You may only use constant extra space.
Recursive approach is fine, implicit stack space does not count as extra space for this problem.
Example:

Given the following binary tree,

     1
   /  \
  2    3
 / \    \
4   5    7
After calling your function, the tree should look like:

     1 -> NULL
   /  \
  2 -> 3 -> NULL
 / \    \
4-> 5 -> 7 -> NULL
*/


    // general binary tree
    public void connect(TreeLinkNode root) {
        while (root != null){
            TreeLinkNode dummy = new TreeLinkNode(0);
            TreeLinkNode currentChild = dummy;
            while (root != null){
                // 对当前/以前怼
                if (root.left != null) { 
                    currentChild.next = root.left; 
                    currentChild = currentChild.next;
                }
                
                if (root.right != null) { 
                    currentChild.next = root.right; 
                    currentChild = currentChild.next;
                }
                root = root.next;
            }
            root = dummy.next; // be the first children
        }
    }
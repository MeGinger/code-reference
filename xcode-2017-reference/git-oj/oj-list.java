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
time complexity: O(n)
space complexity: O(n)

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



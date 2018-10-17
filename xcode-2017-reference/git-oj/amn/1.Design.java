Design Tic-Tac-Toe

class TicTacToe {
    private int[] rows;
    private int[] cols;
    private int diagonal;
    private int antiDiagonal;

    /** Initialize your data structure here. */
    public TicTacToe(int n) {
        rows = new int[n];
        cols = new int[n];
    }

    /** Player {player} makes a move at ({row}, {col}).
        @param row The row of the board.
        @param col The column of the board.
        @param player The player, can be either 1 or 2.
        @return The current winning condition, can be either:
                0: No one wins.
                1: Player 1 wins.
                2: Player 2 wins. */
    // player: 1 or 2
    // assume the move is valid
    public int move(int row, int col, int player) { // O(1)
        // input validity check 
        // - set of {0 ... row * col - 1}
        // - player value


        int toAdd = player == 1 ? 1 : -1;
        int size = rows.length;

        rows[row] += toAdd;
        cols[col] += toAdd;
        
        if (row == col) {
            diagonal += toAdd;
        }

        // n = 3
        //               (0, 2)
        //        (1, 1)
        // (2, 0)
        if (col + row + 1 == size) {
            antiDiagonal += toAdd;
        }

        // I forgot Math.abs()...
        if (Math.abs(rows[row]) == size ||
            Math.abs(cols[col]) == size ||
            Math.abs(diagonal) == size  ||
            Math.abs(antiDiagonal) == size) {
            return player;
        }

        return 0;
    }
}

Design Hit Counter

Design a hit counter which counts the number of hits received in the past 5 minutes.

class HitCounter {
    private Queue<Integer> queue;
    
    /** Initialize your data structure here. */
    public HitCounter() {
        this.queue = new LinkedList<>();;
    }
    
    /** Record a hit.
        @param timestamp - The current timestamp (in seconds granularity). */
    public void hit(int timestamp) {
        queue.offer(timestamp);
    }
    
    /** Return the number of hits in the past 5 minutes.
        @param timestamp - The current timestamp (in seconds granularity). */
    public int getHits(int timestamp) {
        while (!queue.isEmpty() && timestamp - queue.peek() >= 300) {
            queue.poll();
        }      
        return queue.size();
    }
}


Peeking Iterator

Given an Iterator class interface with methods: next() and hasNext(), design and implement a PeekingIterator that support the peek() operation -- it essentially peek() at the element that will be returned by the next call to next().


// Java Iterator interface reference:
// https://docs.oracle.com/javase/8/docs/api/java/util/Iterator.html
class PeekingIterator implements Iterator<Integer> {
    private Iterator<Integer> iterator;
    private Integer next;
    
    public PeekingIterator(Iterator<Integer> iterator) {
        // initialize any member here.
        this.iterator = iterator;
        next = iterator.hasNext() ? iterator.next() : null;
    }

    // Returns the next element in the iteration without advancing the iterator.
    public Integer peek() {
        return next;
    }

    // hasNext() and next() should behave the same as in the Iterator interface.
    // Override them if needed.
    @Override
    public Integer next() {
        Integer res = next;
        next = iterator.hasNext() ? iterator.next() : null;
        return res;
    }
    
    @Override
    public boolean hasNext() {
        return next != null;
    }
}


Flatten Nested List Iterator
/**
 * // This is the interface that allows for creating nested lists.
 * // You should not implement it, or speculate about its implementation
 * public interface NestedInteger {
 *
 *     // @return true if this NestedInteger holds a single integer, rather than a nested list.
 *     public boolean isInteger();
 *
 *     // @return the single integer that this NestedInteger holds, if it holds a single integer
 *     // Return null if this NestedInteger holds a nested list
 *     public Integer getInteger();
 *
 *     // @return the nested list that this NestedInteger holds, if it holds a nested list
 *     // Return null if this NestedInteger holds a single integer
 *     public List<NestedInteger> getList();
 * }
 */

// By calling next repeatedly until hasNext returns false
public class NestedIterator implements Iterator<Integer> {
    private Stack<NestedInteger> stack;
    
    public NestedIterator(List<NestedInteger> nestedList) {
        this.stack = new Stack<>();
        for (int i = nestedList.size() - 1; i >= 0; i--) {
            stack.push(nestedList.get(i));
        }
    }

    // the top element is always integer? - Yes. 
    // should call hasNext() before calling next() !!!
    // otherwise it might return null and that is incorrect
    @Override
    public Integer next() {
        return stack.pop().getInteger();
    }

    // responsible to make sure the top element is integer
    // if it is a list, flatten it
    @Override
    public boolean hasNext() {
        // flatten the top element if it is a list
        while (!stack.isEmpty() && !stack.peek().isInteger()) {
            List<NestedInteger> list = stack.pop().getList();
            for (int i = list.size() - 1; i >= 0; i--) {
                stack.push(list.get(i));
            }
        }

        return !stack.isEmpty();
    }
}

Flatten 2D Vector
Implement an iterator to flatten a 2d vector.
Input: 2d vector =
[
  [1,2],
  [3],
  [4,5,6]
]
Output: [1,2,3,4,5,6]

public class Vector2D implements Iterator<Integer> {
    private Iterator<List<Integer>> i;
    private Iterator<Integer> j; // used as current List

    public Vector2D(List<List<Integer>> vec2d) {
        this.i = vec2d.iterator();
    }

    // assumption: before calling next(), you should call hasNext() first
    @Override
    public Integer next() {
        return j.next(); 
    }

    @Override
    public boolean hasNext() {
        // (j == null || !j.hasNext())
        while ((j == null || !j.hasNext()) && i.hasNext()) {
            j = i.next().iterator(); // might be empty
        }
        
        return j != null && j.hasNext();
    }
}

Binary Search Tree Iterator
public class BSTIterator {
    Stack<TreeNode> stack = new Stack<>();
    
    public BSTIterator(TreeNode root) {
        pushAll(root);
    }

    /** @return whether we have a next smallest number */
    public boolean hasNext() {
        return !stack.isEmpty();
    }

    /** @return the next smallest number */
    public int next() {
        TreeNode tmpNode = stack.pop();
        pushAll(tmpNode.right);
        return tmpNode.val;
    }
    
    private void pushAll(TreeNode root) {
        while (root != null) {
            this.stack.push(root);
            root = root.left;
        }
    }
}

Moving Average from Data Stream
class MovingAverage {
    private Queue<Integer> queue;
    private int sum;
    private int size;
        
    /** Initialize your data structure here. */
    public MovingAverage(int size) {
        this.size = size;
        this.sum = 0;
        queue = new LinkedList<>();
    }
    
    public double next(int val) {
        if (queue.size() == this.size) {
            sum -= queue.poll();
        }
        
        queue.offer(val);
        sum += val;
        return (double) sum / queue.size();
    }
}






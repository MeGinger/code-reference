// WRONG VERSION
class RandomizedSet {
    private Set<Integer> set;
    private Random rnd;
    
    /** Initialize your data structure here. */
    public RandomizedSet() {
        this.set = new HashSet<>();
        this.rnd = new Random();
    }
    
    /** Inserts a value to the set. Returns true if the set did not already contain the specified element. */
    public boolean insert(int val) {
        if (this.set.contains(val)) {
            return false;
        }
        
        this.set.add(val);
        return true;
    }
    
    /** Removes a value from the set. Returns true if the set contained the specified element. */
    public boolean remove(int val) {
        if (!this.set.contains(val)) {
            return false;
        }
        
        this.set.remove(val);
        return true;
    }
    
    /** Get a random element from the set. */
    
    // public int nextInt(int n)
    // return a random number between 0 (inclusive) and n (exclusive)
    public int getRandom() {
        // reservoir sampling - O(n) 
        // 不适合找一个random number
        // 适合找k个random numbers

        int count = 0, result = 0;
        for (int elem : set) {
            if (this.rnd.nextInt(++count) == 0) {
                result = elem;
            }
        }
        return result;
    }
}

/**
 * Your RandomizedSet object will be instantiated and called as such:
 * RandomizedSet obj = new RandomizedSet();
 * boolean param_1 = obj.insert(val);
 * boolean param_2 = obj.remove(val);
 * int param_3 = obj.getRandom();
 */


class RandomizedSet {
    private Map<Integer, Integer> map; // mapping: value to index in the below list
    private List<Integer> nums; // value
    private Random rnd;
    
    /** Initialize your data structure here. */
    public RandomizedSet() {
        this.map = new HashMap<>();
        this.nums = new ArrayList<>();
        this.rnd = new Random();
    }
    
    /** Inserts a value to the set. Returns true if the set did not already contain the specified element. */
    public boolean insert(int val) {
        if (map.containsKey(val)) {
            return false;
        }
        
        this.map.put(val, nums.size());
        this.nums.add(val);
        return true;
    }
    
    /** Removes a value from the set. Returns true if the set contained the specified element. */
    public boolean remove(int val) {
        if (!map.containsKey(val)) {
            return false;
        }
        
        if (map.get(val) != this.nums.size() - 1) {
            // switch the target with the last val
            // so index to last val changes
            // so map has to be updated with the new loc
            int newLoc = map.get(val); // new location for the last value in the list
            int lastVal = this.nums.get(this.nums.size() - 1);
            nums.set(newLoc, lastVal);
            map.put(lastVal, newLoc);
        }
        
        this.map.remove(val);
        this.nums.remove(nums.size() - 1); // remove index
        return true;
    }
    
    /** Get a random element from the set. */
    public int getRandom() {
        return this.nums.get(this.rnd.nextInt(this.nums.size()));
    }
}

/**
 * Your RandomizedSet object will be instantiated and called as such:
 * RandomizedSet obj = new RandomizedSet();
 * boolean param_1 = obj.insert(val);
 * boolean param_2 = obj.remove(val);
 * int param_3 = obj.getRandom();
 */


// Moving Average from Data Stream
Given a stream of integers and a window size, calculate the moving average of all integers in the sliding window.
Example:
MovingAverage m = new MovingAverage(3);
m.next(1) = 1
m.next(10) = (1 + 10) / 2
m.next(3) = (1 + 10 + 3) / 3
m.next(5) = (10 + 3 + 5) / 3

class MovingAverage {
    private Queue<Integer> queue;
    private int size;
    private int sum;
    
    
    /** Initialize your data structure here. */
    public MovingAverage(int size) {
        this.queue = new LinkedList<>();
        this.size = size;
    }
    
    public double next(int val) {
        if (queue.size() == this.size) {
            sum -= queue.poll();
        }
        
        sum += val;
        queue.offer(val);
        return (double) sum / queue.size();
    }
}

/**
 * Your MovingAverage object will be instantiated and called as such:
 * MovingAverage obj = new MovingAverage(size);
 * double param_1 = obj.next(val);
 */



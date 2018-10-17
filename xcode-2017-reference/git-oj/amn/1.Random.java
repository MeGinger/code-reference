Shuffle an Array

class Solution {
    private int[] nums;
    private Random random = new Random();
    
    public Solution(int[] nums) {
        this.nums = nums;    
    }
    
    /** Resets the array to its original configuration and return it. */
    public int[] reset() {
        return nums;
    }
    
    /** Returns a random shuffling of the array. */
    public int[] shuffle() {
        int len = nums.length;
        int[] clone = nums.clone();
        
        // Fisher-Yates algorithm
        // 1. left from right or right from left is okay
        // 2. random index range should include i
        for (int i = len - 1; i > 0; i--) { // last one is not needed
            int j = random.nextInt(i + 1); // [0...i]
            swap(clone, i, j);
        }
        
        return clone;
    }
    
    private void swap(int[] clone, int i, int j) {
        int temp = clone[i];
        clone[i] = clone[j];
        clone[j] = temp;
    }
}

Jump Game - Determine if you are able to reach the last index.

class Solution {
    public boolean canJump(int[] nums) {
        if (nums == null || nums.length == 0) {
            return true; // ?
        }
        
        int farthest = nums[0]; 
        for (int i = 1; i < nums.length; i++) {
            // i <= farthest: current i is reachable
            if (i <= farthest && nums[i] + i > farthest) {
                farthest = nums[i] + i;
            }
        }
        
        return farthest >= nums.length - 1;
    }
}

Jump Game II - Your goal is to reach the last index in the minimum number of jumps.

class Solution {
    public int jump(int[] nums) {
        if (nums == null || nums.length == 0) {
            return -1; // invalid input - tech commu
        }
        
        int start = 0, end = 0, jumps = 0;
        while (end < nums.length - 1) {
            jumps++;
            
            // each step find out farthest reachable index from every start
            for (int i = start; i <= end; i++) {
                if (nums[i] + i > farthest) {
                    farthest = nums[i] + i;
                }
            }
            
            start = end + 1;
            end = farthest;
        }
        
        return jumps;
    }
}
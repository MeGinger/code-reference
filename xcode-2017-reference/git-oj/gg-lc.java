class Solution {
    // assumption: The range of elements are in the inclusive range [lower, upper]
    // add to the head of nums - (lower - 1)
    // add to the tail of nums - (upper + 1)
    public List<String> findMissingRanges(int[] nums, int lower, int upper) {
        List<String> res = new ArrayList<>();
        long prev = (long)lower - 1; // int -> long 
                                     // indicate the last number in the nums
        long curr = 0;
        for(int i = 0; i <= nums.length; i++) {
            curr = (i == nums.length) ? (long)upper + 1: (long)nums[i]; // int -> long
            if(prev + 2 == curr) {
                res.add((prev + 1) + "");
            } else if(prev + 2 < curr) {
                res.add((prev + 1) + "->" + (curr - 1));
            }
            prev = curr;
        }
        return res;
    }
}

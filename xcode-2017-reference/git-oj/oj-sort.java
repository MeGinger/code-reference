    // https://leetcode.com/problems/kth-largest-element-in-an-array/description/
    /*

    */ 

    // QUICK SELECT
    public int findKthLargest(int[] nums, int k) {
        int start = 0, end = nums.length - 1, index = nums.length - k;
        while (start < end) {
            int pivot = partition(nums, start, end);
            if (pivot < index) {
                start = pivot + 1;
            } else if (pivot > index) {
                end = pivot - 1;
            } else {
                return nums[pivot];
            }
        }
        // 
        return nums[start];

    }

    // left | pivot | right
    // nums in the left  <= nums[pivot]
    // nums in the right >  nums[pivot]

    quick select

    nums[pivot] = 3, pivot  = 0

    3 0 2 8 4 6 9 5
    p   e s    

    nums[s] >  nums[pivot] 
    nums[e] <= nums[pivot]

    pivot = 0, unchanged

    private int partition(int[] nums, int start, int end) {
        int pivot = start;
        while (start <= end) {
            while (start <= end && nums[start] <= nums[pivot]) {
                start++;
            }
            while (start <= end && nums[end] > nums[pivot]) {
                end--;
            }
            if (start > end) {
                break;
            }
            swap(nums, start, end);
        }
        
        // end start: adjacent indices
        // nums[end] <= nums[pivot] < nums[start]
        // so swap end and pivot
        swap(nums, end, pivot);
        return end;

    }

    private void swap(int[] nums, int a, int b) {
        int temp = nums[a];
        nums[a] = nums[b];
        nums[b] = temp;
    }






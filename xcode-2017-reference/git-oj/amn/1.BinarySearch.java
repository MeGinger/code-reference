Binary Search implementation in Java
private static int binarySearch0(int[] a, int fromIndex, int toIndex, int key) {
    int low = fromIndex;
    int high = toIndex - 1;

    while (low <= high) {
        int mid = (low + high) >>> 1; // because low and high are always >=
                                        // 0.... faster
        int midVal = a[mid];

        if (midVal < key)
            low = mid + 1;
        else if (midVal > key)
            high = mid - 1;
        else
            return mid; // key found
    }

    // low is insertion index

    // array: 1 2 3 4 5 6
    // index: 0 1 2 3 4 5 6 (all possible values for low, since low = mid +
    // 1)
    /*
     * insertion index: 0, 1, 2 ..., 5, 6
     * 
     * returned value: -1, -2, -3 ..., -6, -7
     */
    return -(low + 1); // key not found.
}

Search in Rotated Sorted Array - duplicate is not allowed
4,5,6,7,0,1,2
class Solution {
    public int search(int[] nums, int target) {
        if (nums == null || nums.length == 0) {
            return -1;
        }
        
        int low = 0, high = nums.length - 1;
        while (low <= high) {
            int mid = (high + low) >>> 1;
            if (nums[mid] == target) {
                return mid;
            } else if (nums[low] <= nums[mid]) {
                //single element is sorted
                
                if (nums[low] <= target && target < nums[mid]) {
                    high = mid - 1;
                } else {
                    low = mid + 1;
                }
            } else {
                if (nums[mid] < target && target <= nums[high]) {
                    low = mid + 1;
                } else {
                    high = mid - 1;
                }
            }
        }
        
        return -1;
    }
}

Search a 2D Matrix II
class Solution {
    public boolean searchMatrix(int[][] matrix, int target) {
        if (matrix == null || matrix.length == 0 || matrix[0].length == 0) {
            return false; // ?
        }
        
        int r = matrix.length;
        int c = matrix[0].length;
        
        int x = r - 1, y = 0; // start from LEFT BOTTOM corner
        while (x >= 0 && y < c) { // within the boundary !!!
            if (matrix[x][y] == target) {
                return true;
            } else if (matrix[x][y] > target) {
                x--; 
            } else {
                y++;
            }
        }
        
        return false;
    }
}










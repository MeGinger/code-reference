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

Find Minimum in Rotated Sorted Array
class Solution {
    public int findMin(int[] nums) {
        if (nums == null || nums.length == 0) {
            return -1; // ?
        }
        
        int low = 0, high = nums.length - 1;
        int min = Integer.MAX_VALUE;
        while (low <= high) {
            int mid = (low + high) >>> 1;
            
            // following is trying to get to the gap point with 
            if (nums[low] <= nums[mid]) { // left part is sorted 
                min = Math.min(min, nums[low]);
                low = mid + 1; 
            } else { // right part is sorted
                min = Math.min(min, nums[mid]);
                high = mid - 1;
            }
        }
        
        return min;
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


Search a 2D Matrix
class Solution {
    public boolean searchMatrix(int[][] matrix, int target) {
        if (matrix == null || 
            matrix.length == 0 || 
            matrix[0].length == 0) {
            return false; // target not found in matrix
        }
        
        int r = matrix.length;
        int c = matrix[0].length;
        
        int low = 0, high = r * c - 1;
        while (low <= high) {
            int mid = (low + high) >>> 1;
            
            int x = mid / c;
            int y = mid % c;
            
            if (matrix[x][y] == target) {
                return true;
            } else if (matrix[x][y] < target) {
                low = mid + 1;
            } else {
                high = mid - 1;
            }
        }
        
        return false;        
    }
}

Missing Number
class Solution {
    public int missingNumber(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }
        
        Arrays.sort(nums);
        
        int low = 0, high = nums.length - 1;
        // 0 1 2 3 5 6
        // 0 1 2 3 4 5
        // nums[mid] == mid OR
        // nums[mid] > mid
        while (low <= high) {
            int mid = (low + high) >>> 1;
            if (mid < nums[mid]) {
                high = mid - 1;
            } else { // nums[mid] == mid
                low = mid + 1;
            }
        }
        
        return low; // insertion key ~~~~~~~~
    }
}

class Solution {
    public int missingNumber(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }
        
        // 0 1 2 3 5 6
        // 0 1 2 3 4 5 6
        // all above numbers ^ -> missing number 4
        
        int res = nums.length;
        for (int i = 0; i < nums.length; i++) {
            res ^= i;
            res ^= nums[i];
        }
        
        return res;
    }
}

Find K Closest Elements

class Solution {
    // a sorted array
    public List<Integer> findClosestElements(int[] arr1, int k, int x) {
        if (arr1 == null || arr1.length == 0 || k > arr1.length) { // arr too short
            return Collections.emptyList();
        }
        
        List<Integer> arr = new ArrayList<>();
        for (int a : arr1) {
            arr.add(a);
        }
        
        int n = arr.size();
        if (x <= arr.get(0)) {
            return arr.subList(0, k);
        } else if (arr.get(n - 1) <= x) {
            return arr.subList(n - k, n);
        } else {
            int index = Collections.binarySearch(arr, x);
            if (index < 0)
                index = - index - 1;
            int low = Math.max(0, index - k), high = Math.min(arr.size() - 1, index + k);

            // if high - low == k - 1  ->  result with size of k
            while (high - low > k - 1) {
                // If there is a tie, the smaller elements are always preferred.
                // so == then high-- and low one is picked
                if ((x - arr.get(low)) <= (arr.get(high) - x)) 
                    high--;
                else if ((x - arr.get(low)) > (arr.get(high) - x))
                    low++;
                else
                    System.out.println("unhandled case: " + low + " " + high);
            }
            return arr.subList(low, high + 1); // arr[low...high]
        }
    }
}





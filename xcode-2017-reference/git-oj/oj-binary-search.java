	// Split Array Largest Sum (leetcode 410) 
	// problem: Given an array which consists of non-negative integers and an integer m, you can split the array into m non-empty continuous subarrays. Write an algorithm to minimize the largest sum among these m subarrays.
	// Note: If n is the length of array, assume the following constraints are satisfied:
	// 1 ≤ n ≤ 1000
	// 1 ≤ m ≤ min(50, n)
	// Examples: Input: nums = [7,2,5,10,8] m = 2
	// Output: 18
	// Explanation: There are four ways to split nums into two subarrays. The best way is to split it into [7,2,5] and [10,8], where the largest sum among the two subarrays is only 18.

	// binary seasrch
    public int splitArray(int[] nums, int m) {
        long l = 0; // max - subarrays with size 1
        long r = 0; // sum - only one array 
        int n = nums.length;
        for (int i = 0; i < n; i++) {
            r += nums[i];
            if (l < nums[i]) {
                l = nums[i]; // find max
            }
        }
        long ans = r;
        while (l <= r) {
            long mid = (l + r) >> 1;
            long sum = 0;
            int cnt = 1;
            for (int i = 0; i < n; i++) {
                if (sum + nums[i] > mid) {
                    cnt++;
                    sum = nums[i];
                } else {
                    sum += nums[i];
                }
            }
            if (cnt <= m) {
            	// assume that when cnt < m, the cnt subarrays could be redistributed into m subarrays.
                ans = Math.min(ans, mid); 
                r = mid - 1;
            } else {
                l = mid + 1;
            }
        }
        return (int)l; // ans;      
    }


	// Copy Books (lint code 437)
	// problem : Given an array A of integer with size of n( means n books and number of pages of each book) and k people to copy the book. You must distribute the continuous id books to one people to copy. (You can give book A[1],A[2] to one people, but you cannot give book A[1], A[3] to one people, because book A[1] and A[3] is not continuous.) Each person have can copy one page per minute. 
	// Return the number of smallest minutes need to copy all the books. - minimize the largest sum

    // Example: Given array A = [3,2,4], k = 2. Return 5 (First person spends 5 minutes to copy book 1 and book 2 and second person spends 4 minutes to copy book 3.)
	// Challenge: Could you do this in O(n*k) time ?

    public int copyBooks(int[] pages, int k) {
        // write your code here
        // if(pages == null || pages.length == 0){
        //     return 0;
        // }

        // if(k < 1){
        //     return -1;
        // }

        // int n = pages.length;
        // int[][] T = new int[n + 1][k + 1];

        // for(int j = 1; j <= k; j++){
        //     T[1][j] = pages[0];
        // }

        // int sum = 0;
        // for(int i = 1; i <= n; i++){
        //     sum += pages[i - 1];
        //     T[i][1] = sum;
        // }

        // for(int i = 2; i <= n; i++){
        //     for(int j = 2; j <= k; j++){
        //         if(j > i){
        //             T[i][j] = T[i][j - 1];
        //             continue;
        //         }
        //         int min = Integer.MAX_VALUE;
        //         for(int h = j - 1; h <= i; h++){
        //             int temp = Math.max(T[h][j - 1], T[i][1] - T[h][1]);
        //             min = Math.min(min, temp);
        //         }
        //         T[i][j] = min;
        //     }
        // }

        // return T[n][k];
    }

    public int copyBooks(int[] pages, int k) {
        // write your code here
        //O(n*logM)? O(n*k)
        int l = 0;
        int r = 9999999;
        while( l <= r){
            int mid = l + (r - l) / 2;
            if(search(mid, pages, k))
                r = mid-1;
            else
                l = mid+1;
        }
        return l;
    }

    private boolean search(int total, int[] page, int k){
    //至少有一个人copy，所以count从1开始
        int count = 1;
        int sum = 0;
        for(int i = 0; i < page.length;) {
            if(sum + page[i] <= total){
                sum += page[i++];
            }else if(page[i] <= total){
                sum = 0;
                count++;
            }else{
                return false;
            }
        }

        return count <= k;
    }

	// Wood cuts(lint code 183)
	// problem: Given n pieces of wood with length L[i] (integer array). 
	// Cut them into small pieces to guarantee you could have equal or more than k pieces with the same length. 
	// What is the longest length you can get from the n pieces of wood? Given L & k, 
	// return the maximum length of the small pieces.

 	// Notice
 	// You couldn't cut wood into float length.
 	// If you couldn't get >= k pieces, return 0.
 	// Example: For L=[232, 124, 456], k=7, return 114.

    public int woodCut(int[] L, int k) {
        int max = 0;
        for (int i = 0; i < L.length; i++) {
            max = Math.max(max, L[i]); // max should be sum of L[i] i think? 
            						   // wood cut! wood should be cut - max is max of L[i]
        }
        
        // find the largest length that can cut more than k pieces of wood.
        int start = 1, end = max;
        // start = 1: smallest length of wood
        // end = max: largest length of wood
        while (start + 1 < end) {
            int mid = start + (end - start) / 2;
            if (count(L, mid) >= k) {
                start = mid;
            } else {
                end = mid;
            }
        }
        
        if (count(L, end) >= k) { // return maximum length (end > start)
            return end;
        }
        if (count(L, start) >= k) {
            return start;
        }
        return 0;
    }
    
    private int count(int[] L, int length) {
        int sum = 0;
        for (int i = 0; i < L.length; i++) {
            sum += L[i] / length;
        }
        return sum;
    }


  /**
    * Kth Smallest Number in Multiplication Table 
    Nearly every one have used the Multiplication Table. But could you find out the k-th smallest number quickly from the multiplication table?

    Given the height m and the length n of a m * n Multiplication Table, and a positive integer k, you need to return the k-th smallest number in this table.

    Example 1:

    Input: m = 3, n = 3, k = 5
    Output: 
    Explanation: 
    The Multiplication Table:
    1 2 3
    2 4 6
    3 6 9

    The 5-th smallest number is 3 (1, 2, 2, 3, 3).

    Example 2:

    Input: m = 2, n = 3, k = 6
    Output: 
    Explanation: 
    The Multiplication Table:
    1 2 3
    2 4 6

    The 6-th smallest number is 6 (1, 2, 2, 3, 4, 6).
    */
  public int findKthNumber(int m, int n, int k) {
    int low = 1, high = m * n + 1;

    while (low < high) {
      int mid = low + (high - low) / 2;
      int c = count(mid, m, n);
      if (c >= k) {
        high = mid;
      } else {
        low = mid + 1;
      }
    }

    return high;
  }

  private int count(int v, int m, int n) {
    int count = 0;
    for (int i = 1; i <= m; i++) {
      count += Math.min(v / i, n);
    }
    return count;
  }
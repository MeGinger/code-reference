// Product of Array Except Self
/*
Given an array nums of n integers where n > 1,  return an array output such that output[i] is equal to the product of all the elements of nums except nums[i].

Example:

Input:  [1,2,3,4]
Output: [24,12,8,6]
Note: Please solve it without division and in O(n).

Follow up:
Could you solve it with constant space complexity? (The output array does not count as extra space for the purpose of space complexity analysis.)
*/

// solve it without division and in O(n)
public int[] productExceptSelf(int[] nums) {
    int n = nums.length;
    int[] res = new int[n];
    res[0] = 1; 
    for (int i = 1; i < n; i++) {
        res[i] = res[i - 1] * nums[i - 1];
    }
    /*
        res[0] = 1
        res[1] = nums[0]
        res[2] = nums[0] * nums[1]
        res[3] = nums[0] * nums[1] * nums[2] 
        ..
        res[n-2] = nums[0] * nums[1] * nums[2] * ... * nums[n-3]
        res[n-1] = nums[0] * nums[1] * nums[2] * ... * nums[n-3] * nums[n-2]
     */
    /*
        n-1 right = 1
        n-2 right = nums[i]
        ... right = nums[i] * nums[i - 1]
        ... ...
        0   right = nums[i] * nums[i - 1] * ... * nums[1]
     */
    int right = 1;
    for (int i = n - 1; i >= 0; i--) {
        res[i] *= right;
        right *= nums[i];
    }
    return res;
}


class Solution {
    // input is guaranteed to be non-negative and less than 2^31 - 1.
    // at most billion 
    // int -2,147,483,648 to 2,147,483,647 
    //                        b   m   t
    
    // Easily misspelled words:
    // Ninety
    
    
    /*
     index:    1, 2, ..., 9
     numner:   1, 2, ..., 9
     */
    private final String[] belowTen = {
      "", "One", "Two", "Three", "Four", 
      "Five", "Six", "Seven", "Eight", "Nine"
    };
    /* 
     index:    0,  1,  ..., 9
     number:   10, 11, ..., 19
     */
    private final String[] belowTwenty = {
      "Ten", "Eleven", "Twelve", "Thirteen", "Fourteen",
      "Fifteen", "Sixteen", "Seventeen", "Eighteen", "Nineteen"
    };
    /*
     index:    0, 1,  ..., 9
     number:      10, .... 90
     */
    private final String[] belowHundred = {
      "", "Ten", "Twenty", "Thirty", "Forty", 
      "Fifty", "Sixty", "Seventy", "Eighty", "Ninety"
    };
    
    public String numberToWords(int num) {
        if (num == 0) {
            return "Zero";
        }
        return helper(num);
    }
    
    private String helper(int num) {
        StringBuilder sb = new StringBuilder();
        if (num < 10) {
            sb.append(belowTen[num]);
        } else if (num < 20) { // num: 10 - 19
            sb.append(belowTwenty[num - 10]);
        } else if (num < 100) { // num: 21 - 99
            sb.append(belowHundred[num / 10]).append(" ").append(helper(num % 10));
        } else if (num < 1000) { // num: 100 - 999
            // the 1st term can only be used as 1- 9
            sb.append(helper(num / 100)).append(" Hundred ").append(helper(num % 100));
        } else if (num < 1000000) { // num: 1000 - 0 999 999
            // the 1st term can be 1 - 999
            sb.append(helper(num / 1000)).append(" Thousand ").append(helper(num % 1000));
        } else if (num < 1000000000) { // num: 1 000 000 - 0 999 999 999
            // the 1st term can be 1 - 999
            sb.append(helper(num / 1000000)).append(" Million ").append(helper(num % 1000000));
        } else {
            sb.append(helper(num / 1000000000)).append(" Billion ").append(helper(num % 1000000000));
        }
        
        return sb.toString().trim(); // dont forget trim
    }
}

Similar RGB Color

class Solution {
    public String similarRGB(String color) {
        // highest similarity    
        // -(AB - UV)^2 - (CD - WX)^2 - (EF - YZ)^2    - highest
        // (AB - UV)^2 + (CD - WX)^2 + (EF - YZ)^2     - lowest
        
        StringBuilder sb = new StringBuilder();
        sb.append("#");
        // input: #09f166
        for (int i = 1; i < color.length(); i += 2) {
            sb.append(getHexColor(color.charAt(i), color.charAt(i + 1)));
        }
        return sb.toString();
    }
    
    // AF
    // c1 = A, c2 = F
    // c1 * 16 + c2
    private String getHexColor(char c1, char c2) {
        int v1 = '0' <= c1 && c1 <= '9' ? c1 - '0' : 10 + c1 - 'a';
        int v2 = '0' <= c2 && c2 <= '9' ? c2 - '0' : 10 + c2 - 'a';
        
        int sum = v1 * 16 + v2;
        int index = sum / 17;
        // index: 1...8 go to 0
        // index: 9...16 go to 17
        if (sum % 17 > 17 / 2) {
            index++;
        }
        
        char c = (0 <= index && index <= 9) ? (char) (index + '0') : (char) (index - 10 + 'a');
        return String.valueOf(c) + String.valueOf(c);
    }
}


Rectangle
    /*
     -----------------
     rec1:
              (x2, y2)
     (x1, y1)
     -----------------
     rec2:
              (x2, y2)
     (x1, y1)
     -----------------
     */
    public boolean isRectangleOverlap(int[] rec1, int[] rec2) {
        return rec2[0] < rec1[2] && 
               rec1[0] < rec2[2] && 
            
               rec2[1] < rec1[3] && 
               rec1[1] < rec2[3];
        
        // or 
        // rec1[0] < rec2[2] && 
        // rec2[0] < rec1[2] && 
        // rec1[1] < rec2[3] && 
        // rec2[1] < rec1[3];
    }


    public boolean isInsideRectangle(int[] point, int[] rec) {
        int x1 = rec[0], y1 = rec[1];
        int x2 = rec[2], y2 = rec[3];

        if (x1 <= point[0] && point[0] <= x2 &&
            y1 <= point[1] && point[1] <= y2) {
            return true;
        } 

        return false;
    }

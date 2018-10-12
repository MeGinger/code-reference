Integer to Roman

class Solution {
    // test case: 1954
    public String intToRoman(int num) {
        if (num <= 0) {
            return "";
        }

        int[] values  = {1000, 900, 500, 400, 100, 90, 50, 40, 10, 9, 5, 4, 1};
        String[] strs = {"M", "CM", "D", "CD", "C", "XC", "L", "XL", "X", "IX", "V", "IV", "I"};
    
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < values.length; i++) {
            while (values[i] <= num) {
                num -= values[i];
                sb.append(strs[i]);
            }
        }
        return sb.toString();
    }
}

Roman to Integer

class Solution {
    
    // if special cases occur, only once!
    public int romanToInt(String s) {
        int sum = 0;
        if (s.contains("IV")) {
            sum -= 2;
        }
        if (s.contains("IX")) {
            sum -= 2;
        }
        if (s.contains("XL")) {
            sum -= 20;
        }
        if (s.contains("XC")) {
            sum -= 20;
        }
        if (s.contains("CD")) {
            sum -= 200;
        }
        if (s.contains("CM")) {
            sum -= 200;
        }
        
        char[] c = s.toCharArray();
        
        for (int i = 0; i < s.length(); i++) {
            switch (c[i]) {
                case 'M': sum += 1000; break;
                case 'D': sum += 500; break;
                case 'C': sum += 100; break;
                case 'L': sum += 50; break;
                case 'X': sum += 10; break;
                case 'V': sum += 5; break;
                case 'I': sum += 1; break;
            }
        }
        
        return sum;
    }
}

Product of Array Except Self

class Solution {
    public int[] productExceptSelf(int[] nums) {
        if (nums == null || nums.length <= 1) {
            return new int[0];
        }
    
        // solve it without division & O(n)
        
        int[] res = new int[nums.length];
        res[0] = 1;
        for (int i = 1; i < nums.length; i++) {
            res[i] = res[i - 1] * nums[i -1];
        }

        // 0   1
        // 1   nums[0]
        // 2   nums[0] * nums[1]
        // ... ...
        // n-1 nums[0] * nums[1] * nums[2] * ... * nums[n-2]

        int right = 1;
        for (int i = nums.length - 2; i >= 0; i--) {
            right *= nums[i + 1];
            res[i] *= right;
        }
        
        return res;
    }
}

Pow(x, n)
x is double, n can be any integer, negative, 0, positive

Time: O(logn) 
Space: O(1) - iteration!

-3 % 2 -> -1
-2 % 2 -> 0
-1 % 2 -> -1
-1 / 2 -> 0

3 % 2 -> 1
2 % 2 -> 0
1 % 2 -> 1
1 / 2 -> 0

int n = Integer.MIN_VALUE;
n = -n; // itself still -2147483648
x = 2
res = 1, it should be 0, since it cannot go to if (n % 2 == 1) {...}
public class Solution {
    public double pow(double x, int n) {
        if (n < 0) {
            n = -n;
        }
        
        double res = 1;
        while (n != 0) {
            if (n % 2 == 1 || n % 2 == -1) {
                res *= x;
            }
            x *= x;
            n /= 2;
        }
        return res;
    }
}


Find the Celebrity
/* The knows API is defined in the parent class Relation.
      boolean knows(int a, int b); */

public class Solution extends Relation {
    public int findCelebrity(int n) {
        // find that one candidate
        int candidate = 0;
        
        for (int i = 1; i < n; i++) {
            if (knows(candidate, i)) {
                candidate = i;
            }
            
            // others i: candidate don't know i => i cannot be candidate
        }
        
        // verify the candidate
        // The definition of a celebrity is that all the other n - 1 people know him/her but he/she does not know any of them.
        for (int i = 0; i < n; i++) {
            if (candidate != i && (!knows(i, candidate) || knows(candidate, i))) {
                return -1;
            }
        }
        
        return candidate;
    }
}







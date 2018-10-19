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

String to Integer (atoi)
class Solution {
    /*
        I think we only need to handle four cases:
        1. discards all leading whitespaces
        2. sign of the number
        3. overflow
        4. invalid input
     */
    public int myAtoi(String str) {
        if (str == null || str.length() == 0) {
            return 0;
        }

        // ignore ceiling whitespace
        int i = 0;
        while (i < str.length() && str.charAt(i) == ' ') {
            i++;
        }
        
        // handle operator
        int sign = 1;
        if (i < str.length() && (str.charAt(i) == '-' || str.charAt(i) == '+')) {
            sign = (str.charAt(i++) == '-') ? -1 : 1;
        }
        
        // |  +345  |  -> 345
        // |  -7    |  -> -7
        // |  -  7  |  -> 0

        // | good 34 |  -> 0
        // | gd 34 d |  -> 0
        // | 34 good |  -> 34 !!!

        int base = 0;
        while (i < str.length() && 
               '0' <= str.charAt(i) && str.charAt(i) <= '9') {
            // max: +2147483647
            // min: -2147483648

            // base > 2147483647, go into below
            if (base >  Integer.MAX_VALUE / 10 || 
                (base == Integer.MAX_VALUE / 10 && str.charAt(i) - '0' > 7)) { // 8, 9
                if (sign == 1) {
                    return Integer.MAX_VALUE;    
                }
                else {
                    return Integer.MIN_VALUE;    
                }
            }

            // max: +2147483647
            base  = 10 * base + (str.charAt(i++) - '0');
        }
        return base * sign;
    }
}

Majority Element - appears more than ⌊ n/2 ⌋ times.
class Solution {
    public int majorityElement(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }
        
        // assumption: must exist a major element
        // test case fails: 1, 1, 1, 2, 3, 2, 2 -> major = 2
        int major = nums[0], count = 1;
        for (int i = 1; i < nums.length; i++) {
            if (major == nums[i]) {
                count++; 
            } else if (count == 0) {
                count++;
                major = nums[i];
            } else {
                count--;
            }
        }
        return major;
    }
}

Single Number - every element appears twice except for one. Find that single one.
class Solution {
    public int singleNumber(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }
        
        int res = 0; 
        for (int num : nums) {
            res ^= num;
        }
        
        // bit operation
        // 0 ^ a = a, a ^ 0 = a
        // 0 ^ 1 = 1, 1 ^ 0 = 1
        // 0 ^ 0 = 0, 1 ^ 1 = 0
        return res;
    }
}

Largest Number
class Solution {
    public String largestNumber(int[] nums) {
        if (nums == null || nums.length == 0) {
            return "";
        }
        
        int len = nums.length;
        String[] snums = new String[len];
        for (int i = 0; i < len; i++) {
            snums[i] = String.valueOf(nums[i]);
        }
        
        Arrays.sort(snums, (a, b) -> (b + a).compareTo(a + b));
        // input: "31", "9" 
        // sort: "9", "31" since 931 > 319
        
        if (snums[0].charAt(0) == '0') {
            return "0";
        }
        
        StringBuilder sb = new StringBuilder();
        for (String snum : snums) {
            sb.append(snum);
        }
        
        return sb.toString();
    }
}


Missing Number
Given an array containing n distinct numbers taken from 0, 1, 2, ..., n, find the one that is missing from the array.

* if ordered, binary search
* if not, XOR




class Solution {
    // The first thing to mention is that I use Java
    // input: an array of Integer - numbers
    // output: the fisrt missing positive number
    // 0 1 2 3 4 5 -> 6 
    // 0 2 3 4 5 6 -> 1
    // -3 -2 -1 0  -> 1
    public int missingNumber(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 1; // ? which number you expect me to return?
                      // 0 for invalid input. Or 1
        }
        
        // do a partition to separate numbers into two ports 
        // put non-negeative numbers in left part, negative numbers in right part
        int index = partition(nums) + 1;
        // nums[0 ... index - 1] > 0
        
        // 0 1 2 3 4 ... index
        // 3 5 2 6 7 ...
        //     -   -
        for (int i = 0; i < index; i++) {
            int val = Math.abs(nums[i]);
            
            if (val > index) {
                continue;
            }
            
            if (nums[val - 1] > 0) {
                nums[val - 1] *= -1;
            }
        }
        
        for (int i = 0; i < index; i++) {
            if (nums[i] > 0) {
                return i + 1;
            }
        }
        
        return index + 1;
    }
    
    private int partition(int[] nums) {
        int p = -1;
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] > 0) {
                p++;
                swap(nums, p, i);
            }
        }
        return p;
    }
    
    private void swap(int[] nums, int a, int b) {
        int temp = nums[a];
        nums[a] = nums[b];
        nums[b] = temp;
    }
}


Power of Two - is n is power of two - only 1 bit in number
operator &
public boolean isPowerOfTwo(int n) {
    return n > 0 && (n & (n - 1)) == 0;
}


Max Points on a Line

class Solution {
    public int maxPoints(Point[] points) {
        if (points == null || points.length == 0) {
            return 0;
        }
        
        int res = 0;
        for (int i = 0; i < points.length; i++) {
            int maxPoints = 0;
            int vertical = 0, overlap = 0; // vertical: count of points vertical with points[i]
            
            Map<String, Integer> slope = new HashMap<>();
            for (int j = i + 1; j < points.length; j++) {
                if (points[i].x == points[j].x) {
                    if (points[i].y == points[j].y) {
                        overlap++;
                    } else {
                        vertical++;
                    }
                    continue; // easy to be missed
                }
                
                int dx = points[i].x - points[j].x;
                int dy = points[i].y - points[j].y;
                
                int gcd = gcd(dx, dy);
                dx /= gcd;
                dy /= gcd;
                
                String key = dx + "/" + dy;
                slope.put(key, slope.getOrDefault(key, 0) + 1);
                maxPoints = Math.max(maxPoints, slope.get(key));
            }
            // first term: count of points on the same line sharing same slope except for points[i]
            // overlap: count of overlapping points except for points[i]
            // 1: points[i] itself
            res = Math.max(res, Math.max(maxPoints, vertical) + overlap + 1);
        }
        
        return res;
    }
    
    private int gcd(int a, int b) {
        if (b == 0) {
            return a;
        } else {
            return gcd(b, a % b);
        }
    }
}

Count Primes
Count the number of prime numbers less than a non-negative number, n.

class Solution {
    // less than a non-negative number, n.
    public int countPrimes(int n) {
        boolean[] notPrime = new boolean[n];
        int count = 0;
        for (int i = 2; i < n; i++) {
            if (notPrime[i] == false) {
                count++;
                for (int j = 2; i * j < n; j++) { // i is a prime number
                    notPrime[i * j] = true;
                }
            }
        }
        
        return count;
    }
}

Reverse Bits

Reverse bits of a given 32 bits unsigned integer.

public class Solution {
    // you need treat n as an unsigned value
    public int reverseBits(int n) {
        int res = 0;
        for (int i = 0; i < 32; i++) {
            res += (n & 1);
            
            n >>>= 1;
            
            if (i < 31) {
                res <<= 1; // leave space for next loop
            }
        }
        return res;
    }
}

Pascal's Triangle

class Solution {
    public List<List<Integer>> generate(int numRows) {
        List<List<Integer>> res = new ArrayList<>();
        List<Integer> row = new ArrayList<>();
        for (int i = 0; i < numRows; i++) {
            row.add(0, 1); // move the rest afterwards (one step) 
                           // so we have row.get(j), row.get(j + 1)
            for (int j = 1; j <= i - 1; j++) {
                row.set(j, row.get(j) + row.get(j + 1));
            }
            res.add(new ArrayList<>(row));
        }
        
        return res;
    }
}


Happy Number
Input: 19
Output: true
Explanation: 
1^2 + 9^2 = 82
8^2 + 2^2 = 68
6^2 + 8^2 = 100
1^2 + 0^2 + 0^2 = 1

class Solution {
    public boolean isHappy(int n) {
        Set<Integer> seen = new HashSet<>();
        
        while (n != 1) {
            n = getDigitSquare(n);
            
            if (!seen.add(n)) {
                return false;
            }
        }
        
        return true;
    }
    
    private int getDigitSquare(int n) {
        int res = 0;
        while (n != 0) {
            int digit = n % 10;
            res += digit * digit;
            n /= 10;
        }
        return res;
    }
}


slow-fast 
int digitSquareSum(int n) {
    int sum = 0, tmp;
    while (n) {
        tmp = n % 10;
        sum += tmp * tmp;
        n /= 10;
    }
    return sum;
}

bool isHappy(int n) {
    int slow, fast;
    slow = fast = n;
    do {
        slow = digitSquareSum(slow);
        fast = digitSquareSum(fast);
        fast = digitSquareSum(fast);
    } while(slow != fast);
    if (slow == 1) return 1;
    else return 0;
}


Add Binary
class Solution {
    public String addBinary(String a, String b) {
        if (a == null || b == null) {
            return "";
        }
        
        char[] charA = a.toCharArray();
        char[] charB = b.toCharArray();
        
        int indexA = charA.length - 1;
        int indexB = charB.length - 1;
        int carry = 0;
        StringBuilder sb = new StringBuilder();
        while (indexA >= 0 || indexB >= 0 || carry != 0) {
            int c1 = indexA >= 0 ? charA[indexA--] - '0' : 0;
            int c2 = indexB >= 0 ? charB[indexB--] - '0' : 0;
            
            int sum = c1 + c2 + carry;
            
            sb.append(sum % 2);
            carry = sum / 2;
        }
        return sb.reverse().toString();
    }
}

Multiple String



Valid Sudoku
class Solution {
    public boolean isValidSudoku(char[][] board) {
        if (board == null || board.length != 9 || board[0].length != 9) {
            return false;
        }
        
        int r = board.length;
        int c = board[0].length;
        
        Set<String> rows = new HashSet<>();
        Set<String> cols = new HashSet<>();
        Set<String> blocks = new HashSet<>();
        
        for (int i = 0; i < r; i++) {
            for (int j = 0; j < c; j++) {
                if (board[i][j] == '.') {
                    continue;
                }
                
                int block = i / 3 * 3 + j / 3;
                char val = board[i][j];
                
                if (!rows.add("r" + i + val) || 
                    !cols.add("c" + j + val) ||
                    !blocks.add("b" + block + val)) {
                    return false;
                } 
            }
        }
        return true;
    }
}
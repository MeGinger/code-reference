
    // Day change(cell growth): int[] dayChange(int[] cells, int days), 
    // cells 数组，有8个元素，每天的变化情况是 当前位置 cell = (cell[i - 1] == cell[i + 1]) ? 0 : 1， 
    // 默认cells[0]左边和cells[cells.length - 1]右边的位置元素都是0， 求days天后的变化.

    // XOR
	//   { 0, 1, 1, 0, 1, 1, 1 }    day 1
	// [0, 0, 1, 1, 0, 1, 1, 1, 0]  day 1 extended
	// [0, 1, 1, 1, 0, 1, 0, 1, 0]  day 2
	// [0, 1, 0, 1, 0, 0, 0, 0, 0]  day 3
	// [0, 0, 0, 0, 1, 0, 0, 0, 0]
	// [0, 0, 0, 1, 0, 1, 0, 0, 0]
	/*  System.arraycopy(src, srcPos, dest, destPos, length);
		src − This is the source array.
		srcPos − This is the starting position in the source array.
		dest − This is the destination array.
		destPos − This is the starting position in the destination data.
		length − This is the number of array elements to be copied.
	*/

	// Arrays.toString(arr)
	// Collection (list, set) 可以直接打 .toString()

	/*  Arrays.copyOfRange(original, from, to);
		original − This is the array from which a range is to to be copied.
		from − This is the initial index of the range to be copied, inclusive.
		to − This is the final index of the range to be copied, exclusive.
	*/

    public static int[] daysChange(int[] days, int n) {
        if (days == null || n <= 0) {
            return days;
        }
        int length = days.length;
        int[] arr = new int[length + 2];
        
		// can be replaced with System.arraycopy(...)
        arr[0] = arr[length + 1] = 0;
        for (int i = 1; i <= length; i++) {
            arr[i] = days[i - 1];
        }

        for (int i = 0; i < n; i++) {
        	int pre = arr[0];
            for (int j = 1; j <= length; j++) {
                int temp = arr[j];
                arr[j] = pre ^ arr[j + 1];
                pre = temp;
            }
        }
        return Arrays.copyOfRange(arr, 1, length + 1);
    }

   	public static int[] daysChange2(int[] days, int n) {
        if (days == null || n <= 0) {
            return days;
        }
        int[] arr = new int[days.length + 2];
        System.arraycopy(days, 0, arr, 1, days.length);
        while (n-- > 0) { // == for (int i = 0; i < n; i++) {...}
            int pre = arr[0]; // 0
            System.out.println(Arrays.toString(arr));
            for (int j = 1; j <= days.length; j++) {
                int temp = arr[j];
                arr[j] = pre ^ arr[j + 1];
                pre = temp;
            }
        }
        return Arrays.copyOfRange(arr, 1, days.length + 1);
    }

    // The gray code is a binary numeral system where two successive values differ in only one bit.
    // ^ -> int
    // byte: 8 bits
    // int: 32 bits 
    //          24 | 8
    // a^b  = 1..1 | result
    //          24 | 8
    // 0xFF = 0..0 | 1...1
    // F = 1111
    public static boolean grayCode(byte a, byte b) {
        int c = (a ^ b) & 0xFF;
        System.out.println(Integer.toBinaryString(c));
        return c != 0 && (c & (c - 1)) == 0;
    }
    // c & (c - 1) 每次把最低位的1删掉
    // 1010101010 => 1010101000 => 1010100000 => 1010000000
    // 可以用于计算一个数的1bit的数目







	
	// Remove Duplicates from Sorted Array (leetcode 26)
	// problem: Given a sorted array, remove the duplicates in-place such that each element appear only once and return the new length.
	// Do not allocate extra space for another array, you must do this by modifying the input array in-place with O(1) extra memory.
	// Example: Given nums = [1,1,2], Your function should return length = 2, with the first two elements of nums being 1 and 2 respectively.
    // It doesn't matter what you leave beyond the new length.

	// two-pointer
	public int removeDuplicates(int[] nums) {
	    if (nums.length == 0) return 0;
	    int i = 0;
	    for (int j = 1; j < nums.length; j++) {
	        if (nums[j] != nums[i]) {
	            i++;
	            nums[i] = nums[j];
	        }
	    }
	    return i + 1;
	}

	// Remove Duplicates from Sorted Array II (leetcode 80) 
	// problem: Follow up for "Remove Duplicates":
	// What if duplicates are allowed at most twice?
	// For example, Given sorted array nums = [1,1,1,2,2,3],
	// Your function should return length = 5, with the first five elements of nums being 1, 1, 2, 2 and 3. It doesn't matter what you leave beyond the new length.

	// two-pointer
	public int removeDuplicates(int[] nums) {
	   int i = 0;
	   for (int n : nums)
	      if (i < 2 || n > nums[i - 2])
	         nums[i++] = n;
	   return i;
	}



	/*

	22336677
	2336677 (3>2)
	0123456

	to get a largest number

	* replacement digit > original digit
	* the more significant bit where the change happens, the better

	这个思路在remove k digits也有所反映
	
	从前往后扫，cur next, cur在group里，cur < next, cur remove
	
	corner case:

	777777654321 - 这种情况要删最后一个
	     |
	   index
	77777654321

	7777776666655
	            |
	          index
	777777666665

	*/
public class DuplicateNumver {
	public static int solution(int X) {
		int index = -1;
		String s = String.valueOf(X);
		for (int i = 0; i < s.length() - 1; i++) {
			int cur = s.charAt(i) - '0';
			int next = s.charAt(i + 1) - '0';
			if (cur != next) {
				continue;
			}
			// found group
			while (i < s.length() - 1 && s.charAt(i) == s.charAt(i + 1)) {
				i++;
			}
			// i is the last index of a group

			// i | i + 1
			index = i;
			if (i == s.length() - 1) {
				break;
			}
			cur = s.charAt(i) - '0';
			next = s.charAt(i + 1) - '0';
			if (next > cur) {
				return Integer.valueOf(s.substring(0, i) + s.substring(i + 1)); // not including char at i
			}
		}
		if (index == -1) {
			return Integer.MIN_VALUE; // invalid input like single-digit integer
		}
		return Integer.valueOf(s.substring(0, index) + s.substring(index + 1)); // not including char at index
		// corner case:
		// 777777654321 - 这种情况要删最后一个
	    //      |
	    //    index
		// 77777654321
	}

	public static int solution3(int X) {
		int max = Integer.MIN_VALUE;
		StringBuilder builder = new StringBuilder(String.valueOf(X));
		for (int i = 1; i < builder.length(); i++) {
			if (builder.charAt(i - 1) == builder.charAt(i)) {
				StringBuilder b = new StringBuilder(builder.toString());
				b.deleteCharAt(i);
				int a = Integer.valueOf(b.toString()); // try every possibility
				if (a > max) {
					max = a;
				}
			}
		}
		return max;
	}

	public static int solution2(int X) {
		int max = Integer.MIN_VALUE;
		StringBuilder builder = new StringBuilder(String.valueOf(X));
		for (int i = 1; i < builder.length(); i++) {
			if (builder.charAt(i - 1) != builder.charAt(i)) {
				continue;
			}
			while (i < builder.length() - 1 && builder.charAt(i + 1) == builder.charAt(i)) {
				i++;
			}
			StringBuilder b = new StringBuilder(builder.toString());
			b.deleteCharAt(i);
			int a = Integer.valueOf(b.toString());
			if (a > max) {
				max = a;
			}
		}
		return max;
	}

	public static void main(String[] args) {
		int X = 7766554;
		System.out.println(solution(X));
		System.out.println(solution2(X));
		System.out.println(solution3(X));
	}
}




  /**
   * Isomorphic Strings
   * 
   * Given two strings s and t, determine if they are isomorphic.

	Two strings are isomorphic if the characters in s can be replaced to get t.

	All occurrences of a character must be replaced with another character while preserving the order of characters. No two characters may map to the same character but a character may map to itself.

	For example,
	Given "egg", "add", return true.

	Given "foo", "bar", return false.

	Given "paper", "title", return true.

	Note:
	You may assume both s and t have the same length.
   */
  public boolean isIsomorphic(String s, String t) {
    if (s == null) {
      return t == null;
    }

    if (s.length() != t.length()) {
      return false;
    }

    // O(2n) space
    // key is the char, value is the "last appeared" index of the key of the char
    Map<Character, Integer> sMapping = new HashMap<>();
    Map<Character, Integer> tMapping = new HashMap<>();
    for (int i = 0; i < s.length(); i++) {
      char sc = s.charAt(i);
      char tc = t.charAt(i);
      // so first time =>
      // Map.put(k, v) 
      // return null if key k not exists in map
      // return existing value if key k exists in map

      // s: egg, t: add
      // sMapping: <e:0, g:2>
      // tMapping: <a:0, d:2>
      // sc: g
      // tc: d
      // i: 2
      // 1 == 1 (previous/existing value)
      if (!Objects.equals(sMapping.put(sc, i), tMapping.put(tc, i))) {
        return false;
      }
    }

    return true;
  }


  // follow up
  private String[] strings;

  // solution 1
  // key is the string length
  Map<Integer, List<String>> map = ...

  public boolean isIsomorphic(String[] strings, String t) { 
  	// preprocess strings to map (key: string length, value: a list of string with key length)

    List<String> l = map.get(t.length());
    if (l == null) {
      return false;
    }
    for (String s : l) {
      if (isIsomorphic(s, t)) {
      	return true; // find if exist - so we can early return true
      }
    }
    return false;
  }

  
  // solution 2 - better
  // transitive
  // a is similar to b, and b is similar to c
  // so a and b and c are similar
  // union find
  // preprocess - remove unnecessary words
  // ?

  

  /**
   * Longest Substring with At Most K Distinct Characters
   * Given a string, find the length of the longest substring T that contains at most k distinct characters.
   * For example, Given s = “eceba” and k = 2,
   * T is "ece" which its length is 3.
   */
  // test cases: ASCII, I18N
  // For test cases, you can mention I can probably test Chinese charaters 
  // (to demonstrate you have sense of internalization)
  // int[256] - not generic

  public int lengthOfLongestSubstringKDistinct(String s, int k) {
    int[] count = new int[256];
    int num = 0, i = 0, res = 0;
    for (int j = 0; j < s.length(); j++) {
    	if (count[s.charAt(j)]++ == 0) {
        	num++;
      	}
      	if (num > k) {
        	while (--count[s.charAt(i++)] > 0) {
          	;
        	}
        num--;
    	}
      	// num == k
      	res = Math.max(res, j - i + 1); // dont put it into the above if-stmt, 
      								  // input string s may not contain k distinct characters...
    }
    return res;
  }
  
  // Use map - more generic
  // space counts: worst case: k = 10, s = "abcdefgh", k > n, n is length of s, O(n)

  // follow up: input string s is a very big stream of characters
  // stream.next() -> a char, so you can only have right pointer

  public int lengthOfLongestSubstringKDistinct2(String s, int k) {
    Map<Character, Integer> counts = new HashMap<>();
    int total = 0, left = 0, res = 0, count;
    char c;
    // best case: left == 0, right moved to n - 1, O(n) that assume the following
    // do-while loop takes constant time
    // worst case: left == (close) n - 1, right to n - 1, O(2n)
    // do not simply say runtime is O(n); should reason about it by analysis
    for (int right = 0; right < s.length(); right++) {
        c = s.charAt(right); // stream.next(); you only have right pointer
      	count = counts.getOrDefault(c, 0) + 1;
      	counts.put(c, count);
      	if (count == 1) {
        	total++;
      	}
      	if (total > k) {
        	do {
          		c = s.charAt(left++); // but you cannot have this character
                                	  // since string is so big and you can't save such string in memory
          		// what should we do?
          		// we only record rightmost index of each character in the sliding window
          		// find the minimum of indices of those characters in the sliding window -> minIndex
          		// res = Math.max(res, right - (minIndex + 1) + 1)
          		count = counts.get(c) - 1;
          		counts.put(c, count);
        	} while (count > 0); // count 1 -> 0, remove one distinct character, total == k
        total--;
    	}
    	res = Math.max(res, right - left + 1);
    }

    return res;
  }
 
  public int lengthOfLongestSubstringKDistinct3(String s, int k) {
    if (s.isEmpty()) {
      return 0;
    }
    Map<Character, Integer> indexes = new HashMap<>();
    int left = 0, right = 0, res = 0;
    char c;
    while (right < s.length()) {
      c = s.charAt(right);
      indexes.put(c, right++); // keep right most index of each character
      if (indexes.size() > k) {
        int leftMost = s.length();
        // how to optimize this?
        // use priority queue - min heap - but not best solution
        // use LRU - FIFO, head, tail, LinkedHashMap 
        // - the least frequently used item is target
        
        //  L           R
        // \|/         \|/
        //  A B A A C C D   (left most) 
        //  D -> C -> A -> B 
        //  H              T
        for (int i : indexes.values()) {
          leftMost = Math.min(leftMost, i);
        }
        c = s.charAt(leftMost);
        indexes.remove(c);
        left = leftMost + 1;
      }
      // indexes.size() <= k (at most k characters)
      res = Math.max(res, right - left);
    }
    return res;
  }

  /**
   * Longest Substring with At Most Two Distinct Characters
   * Given a string, find the length of the longest substring T that contains at most 2 distinct characters.
   * For example, Given s = “eceba”,
   * T is "ece" which its length is 3. 
   */
  public int lengthOfLongestSubstringTwoDistinct(String s) {
    int i = 0, j = -1, maxLen = 0;

    // "ece"
    // k:      1, 2
    // i (starting point): 0, 0
    // j (right most point) : 0, 1
    // maxLen: 0, 0, 3

    for (int k = 1; k < s.length(); k++) {
      if (s.charAt(k) == s.charAt(k - 1)) {
        continue;
      }
      // found 2 distinct characters

      if (j >= 0 && s.charAt(j) != s.charAt(k)) { // found 3 distinct characters
        maxLen = Math.max(k - i, maxLen); // s.charAt(k) is not included in the substring 
        i = j + 1;
      }
      j = k - 1;
    }
    return Math.max(s.length() - i, maxLen);
  }


  /**
   * The main idea is to maintain a sliding window with 2 unique characters.
   * The key is to store the last occurrence of each character as the value in the hashmap.
   * This way, whenever the size of the hashmap exceeds 2, 
   * we can traverse through the map to find the character with the left most index,
   * and remove 1 character from our map. Since the range of characters is constrained, 
   * we should be able to find the left most index in constant time.
   */
  public int lengthOfLongestSubstringTwoDistinct2(String s) {
    if (s.isEmpty()) {
      return 0;
    }
    Map<Character, Integer> indexes = new HashMap<>();
    int left = 0, right = 0, res = 0;
    char c;
    while (right < s.length()) {
      c = s.charAt(right);
      indexes.put(c, right++);
      while (indexes.size() > 2) { // similar to "K Distinct Characters"
        int leftMost = s.length();
        for (int i : indexes.values()) {
          leftMost = Math.min(leftMost, i);
        }
        c = s.charAt(leftMost);
        indexes.remove(c);
        left = leftMost + 1;
      }
      res = Math.max(res, right - left);
    }
    return res;
  }


   
	// 1,1,1,1,1,1,1,1,1
   /**
	*  Longest Consecutive Sequence 
	Given an unsorted array of integers, find the length of the longest consecutive elements sequence.

	For example,
	Given [100, 4, 200, 1, 3, 2],
	The longest consecutive elements sequence is [1, 2, 3, 4]. Return its length: 4.

	Your algorithm should run in O(n) complexity. 
    */
    public int longestConsecutive(int[] nums) {
        int res = 0;
        // key is the number, value is the length of the sequence the number is
        // in
        Map<Integer, Integer> map = new HashMap<>();
        for (int n : nums) {
            if (map.containsKey(n)) {
                // duplicates
                continue;
            }
            int left = map.getOrDefault(n - 1, 0);
            int right = map.getOrDefault(n + 1, 0);
            // len: length of the sequence n is in
            int len = left + right + 1;
            map.put(n, len);

            // keep track of the max length
            res = Math.max(res, len);

            // extend the length to the boundary(s)
            // of the sequence
            // will do nothing if n has no neighbors
            map.put(n - left, len);
            map.put(n + right, len);
        }
        return res;
    }

	/**
     * Remove Duplicate Letters
 	Given a string which contains only lowercase letters, remove duplicate letters so that every letter appear once and only once.
	You must make sure your result is the smallest in lexicographical order among all possible results.
	Example:

	Given "bcabc"
	Return "abc"

	Given "cbacdcbc"
	Return "acdb" 
    */
    public String removeDuplicateLetters(String s) {
        if (s == null || s.isEmpty()) {
            return s;
        }
        int[] count = new int[26];
        for (int i = 0; i < s.length(); i++) {
            count[s.charAt(i) - 'a']++; // frequency count
        }
        boolean[] in = new boolean[26]; 
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            int pos = c - 'a';
            count[pos]--;
            if (sb.length() == 0) {
                sb.append(c);
                in[pos] = true;
                continue;
            }

            if (in[pos]) { // duplicate
                continue;
            }

            int lastIndex = sb.length() - 1;
            while (sb.length() > 0 && c < sb.charAt(lastIndex) && count[sb.charAt(lastIndex) - 'a'] > 0) { 
            	// "c < sb.charAt(lastIndex)" -> smallest lexicographical order
            	// "count[sb.charAt(lastIndex) - 'a'] > 0" -> we still have this character 

            	// remove the character at the last index
                in[sb.charAt(lastIndex) - 'a'] = false;
                sb.deleteCharAt(lastIndex);
                lastIndex--;
            }

            sb.append(c);
            in[pos] = true;
        }

        return sb.toString();
    }
 
	/**
     * Remove K Digits
	 Given a non-negative integer num represented as a string, remove k digits from the number so that the new number is the smallest possible.

	 Note:

     The length of num is less than 10002 and will be ≥ k.
     The given num does not contain any leading zero.

	 Example 1:

	 Input: num = "1432219", k = 3
	 Output: "1219"
	 Explanation: Remove the three digits 4, 3, and 2 to form the new number 1219 which is the smallest.

	 Example 2:

	 Input: num = "10200", k = 1
	 Output: "200"
	 Explanation: Remove the leading 1 and the number is 200. Note that the output must not contain leading zeroes.

	 Example 3:

	 Input: num = "10", k = 2
	 Output: "0"
	 Explanation: Remove all the digits from the number and it is left with nothing which is 0.
	 Similar to Remove Duplicate Letters
     */
    public String removeKdigits(String num, int k) {
        if (k >= num.length()) {
            // corner case
            return "0";
        }

        StringBuilder result = new StringBuilder();
        for (int i = 0; i < num.length(); i++) {
            char c = num.charAt(i);
            // whenever meet a digit which is less than the previous digit,
            // discard the previous one
            // result.length() - 1 is last char's index
            // result.charAt(result.length() - 1) is result's last char
            // c < last char
            // c would make it smaller
            // result.length() - 1 if result.length() = 0, => -1
            // 12345 + 1 => 11
            // second digit: 1 < 2
            while (k > 0 && result.length() > 0 && result.charAt(result.length() - 1) > c) { // more significant bit, more matters
                result.deleteCharAt(result.length() - 1);
                k--;
            }
            result.append(c);
        }

        while (k > 0) {
            // corner case like "1111" 
            // k = 2, "11"
            result.deleteCharAt(result.length() - 1);
            k--;
        }

        // remove all the 0 at the head
        while (result.length() > 1 && result.charAt(0) == '0') {
            result.deleteCharAt(0);
        }
        return result.toString();
    }



import java.util.*;

public class AddBoldTagInString {
    /**
     * Add Bold Tag in String
     Given a string s and a list of strings dict, you need to add a closed pair of bold tag <b> and </b> to wrap the substrings in s that exist in dict. If two such substrings overlap, you need to wrap them together by only one pair of closed bold tag. Also, if two substrings wrapped by bold tags are consecutive, you need to combine them.

     Example 1:
     Input:
     s = "abcxyz123"
     dict = ["abc","123"]
     Output:
     "<b>abc</b>xyz<b>123</b>"

     Example 2:
     Input:
     s = "aaabbcc"
     dict = ["aaa","aab","bc"]
     Output:
     "<b>aaabbc</b>c" overlap or adjacent combine 

     Note:
     The given dict won't contain duplicates, and its length won't exceed 100.
     All the strings in input have length in range [1, 1000].
     */
    public String addBoldTag(String s, String[] dict) {
        boolean[] bold = new boolean[s.length()];
        // indexOf(string) -> -1, if not exist -> strstr, KMP algorithm?
        for (String d : dict) {
            for (int i = 0; i <= s.length() - d.length(); i++) {
                if (s.substring(i, i + d.length()).equals(d)) {
                    for (int j = i; j < i + d.length(); j++)
                        // for follow up, just demon your idea ~
                        // FFFFTTFTTFFFF
                        //   |        |
                        //   TT     TTT
                        // input is very large => using boolean array is a waste of space 
                        // => use interval [beg, end]
                        bold[j] = true;
                }
            }
        }
        StringBuilder res = new StringBuilder();
        for (int i = 0; i < s.length();) {
            if (bold[i]) {
                res.append("<b>");
                while (i < s.length() && bold[i]) {
                    res.append(s.charAt(i++));
                }
                res.append("</b>");
            } else {
                res.append(s.charAt(i++));
            }
        }
        return res.toString();
    }

    public String addBoldTag2(String s, String[] dict) {
        List<int[]> list = new ArrayList<>();
        Set<String> set = new HashSet<>(Arrays.asList(dict));
        for (int i = 0; i < s.length(); i++) {
            for (int j = i; j < s.length(); j++) {
                if (set.contains(s.substring(i, j + 1))) {
                    list.add(new int[] { i, j });
                }
            }
        }
        if (list.isEmpty()) {
            return s;
        }
        Collections.sort(list, (a, b) -> a[0] == b[0] ? a[1] - b[1] : a[0] - b[0]);
        int start, prev = 0, end = 0;
        StringBuilder res = new StringBuilder();
        for (int i = 0; i < list.size(); i++) {
            res.append(s.substring(prev, list.get(i)[0]));
            start = i;
            end = list.get(i)[1];
            while (i < list.size() - 1 && list.get(i + 1)[0] <= end + 1) {
                end = Math.max(end, list.get(i + 1)[1]);
                i++;
            }
            res.append("<b>" + s.substring(list.get(start)[0], end + 1) + "</b>");
            prev = end + 1;
        }
        res.append(s.substring(end + 1, s.length()));
        return res.toString();
    }
}




public class ReadNCharactersGivenRead4 {
    /*
     * Read N Characters Given Read4
     *
     * Question: The API: int read4(char *buf) reads 4 characters at a time from a
     * file. The return value is the actual number of characters read. For example,
     * it returns 3 if there is only 3 characters left in the file. By using the
     * read4 API, implement the function int read(char *buf, int n) that reads n
     * characters from the file. Note: The read function will only be called
     * multiplmultiple for each test case.
     *
     * The read4 API is defined in the parent class Reader4.
     */
    private int read4(char[] buf) {
        return 0;
    }

    private String read4String() {
        return null;
    }

    private char[] buffer = new char[4];
    private int offset = 0, bufsize = 0;

    /**
     * @param buf Destination buffer
     * @param n Maximum number of characters to read
     * @return The number of characters read
     */
    public int read(char[] buf, int n) {
        int readBytes = 0;
        boolean eof = false;
        while (!eof && readBytes < n) {

        	// read4(this.buffer) returns
        	// 4
        	// 0-3

        	// bufsize   0 0 0  ... 0 or 0
        	// sz        4 4 4  ... 3    3 (eof = true)
        	// bytes     4 4 4  ... 2    3 (actual count of character to read based on requirement n)
        	// offset    0 0 0  ... 2    3
        	// readBytes 4 8 12 ... n    n
        	// bufsize   0 0 0  ... 1    0 (used in later API calls)

            int sz = (this.bufsize > 0) ? this.bufsize : read4(this.buffer);
            if (this.bufsize == 0 && sz < 4) {
                eof = true;
            }
            int bytes = Math.min(n - readBytes, sz);
            System.arraycopy(this.buffer /* src - 4 character long */, 
            	 		     this.offset /* srcPos - 0 to 3 */, 
            	 		     buf /* dest */, 
            	 		     readBytes /* destPos */,
                    		 bytes /* length */);
            this.offset = (this.offset + bytes) % 4;
            this.bufsize = sz - bytes;
            readBytes += bytes;
        }

        return readBytes;
    }

    private String s;

    public String read2(int n) {
        int readBytes = 0;
        boolean eof = false;
        StringBuilder sb = new StringBuilder();
        while (!eof && readBytes < n) {
            int sz;
            if (this.bufsize > 0) {
                sz = this.bufsize;
            } else {
                this.s = read4String();
                sz = this.s.length();
            }

            if (this.bufsize == 0 && sz < 4) {
                eof = true;
            }
            int bytes = Math.min(n - readBytes, sz);
            // pseudo function
            // copyString(this.s /* src */, this.offset /* srcPos */, sb /* dest */, readBytes /* destPos */,
            //		bytes /* length */);
            this.offset = (this.offset + bytes) % 4;
            this.bufsize = sz - bytes;
            readBytes += bytes;
        }

        return sb.toString();
    }
}


	/**MOCK QUESTION
	 * Wildcard Matching
	Implement wildcard pattern matching with support for '?' and '*'.

	'?' Matches any single character.
	'*' Matches any sequence of characters (including the empty sequence).

	The matching should cover the entire input string (not partial).

	The function prototype should be:
	bool isMatch(const char *s, const char *p)

	Some examples:
	isMatch("aa","a") → false
	isMatch("aa","aa") → true
	isMatch("aaa","aa") → false
	isMatch("aa", "*") → true
	isMatch("aa", "a*") → true
	isMatch("ab", "?*") → true
	isMatch("aab", "c*a*b") → false
	 */
	public boolean isMatch(String s, String p) {
		if (p.equals("")) {
			return s.equals("");
		}

		LinkedList<String> splits = splitPatternByStar(p);
		if (splits.isEmpty()) {
			return true;
		}
		if (!p.startsWith("*")) {
			String first = splits.getFirst();
			if (!isMatchWithoutStar(s, first, 0)) {
				return false;
			}
			s = s.substring(first.length());
			splits.removeFirst();
		}
		if (!p.endsWith("*")) {
			if (splits.isEmpty()) {
				return s.equals("");
			}
			String last = splits.getLast();
			int sBeg = s.length() - last.length();
			if (!isMatchWithoutStar(s, last, sBeg)) {
				return false;
			}
			s = s.substring(0, sBeg);
			splits.removeLast();
		}
		int sBeg = 0;
		for (String part : splits) {
			while (sBeg <= s.length() - part.length()) {
				if (isMatchWithoutStar(s, part, sBeg)) {
					break;// continue to next part
				}
				sBeg++; // Use * to eat current sBeg and try next
			}

			sBeg += part.length();
			if (sBeg > s.length()) {
				return false;
			}
		}
		return true;
	}

	// e.g. "**ab*c**d*" => [ab, c, d]
	private LinkedList<String> splitPatternByStar(String p) {
		LinkedList<String> ret = new LinkedList<>();
		int left = -1, right = -1;
		while (right < p.length()) {
			while (++left < p.length() && '*' == p.charAt(left)) {
				;
			}
			right = left;
			while (++right < p.length() && '*' != p.charAt(right)) {
				;
			}
			if (left < p.length() && right <= p.length() && right > left) {
				ret.add(p.substring(left, right));
			}
			left = right;
		}
		return ret;
	}

	private boolean isMatchWithoutStar(String s, String p, int sBeg) {
		if (s.length() < p.length()) {
			return false;
		}

		for (int i = 0; i < p.length(); i++) {
			if (p.charAt(i) != s.charAt(sBeg + i) && '?' != p.charAt(i)) {
				return false;
			}
		}
		return true;
	}

	private LinkedList<String> splitPatternByStar2(String p) {
		LinkedList<String> ret = new LinkedList<>();
		for (String s : p.split("\\*")) {
			if (!s.isEmpty()) {
				ret.add(s);
			}
		}

		return ret;
	}

	public boolean isMatch4(String s, String p) {
		int sIndex = 0, pIndex = 0, match = 0, starIndex = -1;
		while (sIndex < s.length()) {
			// advancing both pointers
			if (pIndex < p.length() && (p.charAt(pIndex) == '?' || s.charAt(sIndex) == p.charAt(pIndex))) {
				sIndex++;
				pIndex++;
			}
			// * found, only advancing pattern pointer
			else if (pIndex < p.length() && p.charAt(pIndex) == '*') {
				starIndex = pIndex;
				match = sIndex;
				pIndex++;
			}
			// last pattern pointer was *, advancing string pointer
			else if (starIndex != -1) {
				pIndex = starIndex + 1;
				sIndex = ++match;
			}
			// current pattern pointer is not star, last pattern pointer was not
			// *
			// characters do not match
			else {
				return false;
			}
		}

		// check for remaining characters in pattern
		while (pIndex < p.length() && p.charAt(pIndex) == '*') {
			pIndex++;
		}

		return pIndex == p.length();
	}


// Information Masking
// thought: separate email string into two parts:
// - the first part is from index 0 to the index before the index of character '@' 
//   - get the 1st and last character of the first part and reconstruct the first part by 
//     1st charcter, 5 star signs and last character in order
// - the second part is the rest of the string - no change
// - combine these two parts
public String maskEmail(String email) {
	// clean code
	int index = email.indexOf('@');
	return email.charAt(0) + "*****" + email.charAt(index - 1) + email.substring(index);
}

// thought: scan from right to left of phone string. 
// ignore any white space character, parenthesis and minus sign.
// append to result the first 4 digits, the rest of digits are represented as star sign.
// when the 5tb, 8th and 11th digit come in, append minus sign before appending the star sign
public String maskPhone(String phone) {
	StringBuilder res = new StringBuilder();
	int count = 0;
	// scan from right to left ... and return reversed result string
	for (int i = phone.length() - 1; i >= 0; i--) {
		char c = phone.charAt(i);
		// character: white-space, '(', ')', '-'
		if (Character.isWhitespace(c) || c == '(' || c == ')' || c == '-') {
			continue;
		}
		// character: '0' ~ '9'
		if (Character.isDigit(c)) {
			count++;
			if (count < 5) {
				res.append(c);
			} else {
				if (count == 5 || count == 8 || count == 11) {
					res.append('-');
				}
				res.append('*');
			}
		// other character: '+'
		} else {
			res.append(c);
		}
	}
	
	return res.reverse().toString();
}

public static void main(String[] args) {
	InformationMasking i = new InformationMasking();
	System.out.println(i.maskEmail("jackAndJill@gmail.com"));
	System.out.println(i.maskEmail("jackAndJill@twitter.com"));
	System.out.println(i.maskPhone("+1 (333) 444-5678"));
	System.out.println(i.maskPhone("+91 (333) 444-5678"));
	System.out.println(i.maskPhone("+111 (333) 444-5678"));
	System.out.println(i.maskPhone("333 444 5678"));
	System.out.println(i.maskPhone("(333) 444-5678"));
	System.out.println(i.maskPhone("3334445678"));
	System.out.println(i.maskPhone("+13334445678"));
	System.out.println(i.maskPhone("+1(333) 444-5678"));
}

	// Exclusive Time of Functions(leetcode 636)
	// problem: Given the running logs of n functions that are executed in a nonpreemptive single threaded CPU, find the exclusive time of these functions.
	// Each function has a unique id, start from 0 to n-1. A function may be called recursively or by another function.
	
	// A log is a string has this format : function_id:start_or_end:timestamp. For example, "0:start:0" means function 0 starts from the very beginning of time 0. "0:end:0" means function 0 ends to the very end of time 0.

	// Exclusive time of a function is defined as the time spent within this function, the time spent by calling other functions should not be considered as this function's exclusive time. You should return the exclusive time of each function sorted by their function id.
	
	// Example 1:
	// Input:
	// n = 2
	// logs = 
	/* 
	["0:start:0",
	 "1:start:2",
	 "1:end:5",
	 "0:end:6"]
	 
	 0 0 1 1 1 1 0
	 |_|_|_|_|_|_|_|
	 0 1 2 3 4 5 6
	 
	 Output:[3, 4]
	*/
	/*
	Explanation:
	Function 0 starts at time 0, then it executes 2 units of time and reaches the end of time 1. 
	Now function 0 calls function 1, function 1 starts at time 2, executes 4 units of time and end at time 5.
	Function 0 is running again at time 6, and also end at the time 6, thus executes 1 unit of time. 
	So function 0 totally execute 2 + 1 = 3 units of time, and function 1 totally execute 4 units of time.
	*/

    public int[] exclusiveTime(int n, List < String > logs) {
        Stack<Integer> stack = new Stack<>();
        int[] res = new int[n];
        String[] s = logs.get(0).split(":");
        stack.push(Integer.parseInt(s[0]));
        int i = 1, prev = Integer.parseInt(s[2]); // start as prev
        while (i < logs.size()) {
            s = logs.get(i).split(":");
            if (s[1].equals("start")) {
                if (!stack.isEmpty())
                    res[stack.peek()] += Integer.parseInt(s[2]) - prev; // start as cur
                stack.push(Integer.parseInt(s[0]));
                prev = Integer.parseInt(s[2]); // start as prev
            } else {
                res[stack.peek()] += Integer.parseInt(s[2]) - prev + 1; // end as cur
                stack.pop();
                prev = Integer.parseInt(s[2]) + 1; // end as prev -> 
            }
            i++;
        }
        return res;
    }



	// Next Closest Time(leetcode 681)
	// Problem: 
	// Given a time represented in the format "HH:MM", form the next closest time by reusing the current digits. There is no limit on how many times a digit can be reused.
	// You may assume the given input string is always valid. For example, "01:34", "12:09" are all valid. "1:34", "12:9" are all invalid.
	// Example 1:
	// Input: "19:34"
	// Output: "19:39"
	// Explanation: The next closest time choosing from digits 1, 9, 3, 4, is 19:39, which occurs 5 minutes later.  It is not 19:33, because this occurs 23 hours and 59 minutes later.
	// Example 2:
	// Input: "23:59"
	// Output: "22:22"
	// Explanation: The next closest time choosing from digits 2, 3, 5, 9, is 22:22. It may be assumed that the returned time is next day's time since it is smaller than the input time numerically.

	public String nextClosestTime(String time) {
		int start = 60 * Integer.parseInt(time.substring(0, 2));
        start += Integer.parseInt(time.substring(3));
        int ans = start;
        int elapsed = 24 * 60;
        Set<Integer> allowed = new HashSet();
        for (char c : time.toCharArray()) if (c != ':') {
            allowed.add(c - '0');
        }

        for (int h1: allowed) for (int h2: allowed) if (h1 * 10 + h2 < 24) {
            for (int m1: allowed) for (int m2: allowed) if (m1 * 10 + m2 < 60) {
                int cur = 60 * (h1 * 10 + h2) + (m1 * 10 + m2);
				// cur - start <, >, = 0
				// Math.floorMod(3, 5)  = 3
				// Math.floorMod(-3, 5) = 5 - 3 = 2 -> complement number
				
				// cur - start is in the range of [-24*60+1, 24*60-1].
                int candElapsed = Math.floorMod(cur - start, 24 * 60);
                if (0 < candElapsed && candElapsed < elapsed) {
                    ans = cur;
                    elapsed = candElapsed;
                }
            }
        }
	
        return String.format("%02d:%02d", ans / 60, ans % 60);
    }
	
	

class File {
	boolean isFile = false;
	Map<String, File> children = new HashMap<>();
	String content = "";
}

/**
 * Design In-Memory File System
 Design an in-memory file system to simulate the following functions:
 
 ls: Given a path in string format. If it is a file path, return a list that only contains this file's name. If it is a directory path, return the list of file and directory names in this directory. Your output (file and directory names together) should in lexicographic order.
 
 mkdir: Given a directory path that does not exist, you should make a new directory according to the path. If the middle directories in the path don't exist either, you should create them as well. This function has void return type.
 
 addContentToFile: Given a file path and file content in string format. If the file doesn't exist, you need to create that file containing given content. If the file already exists, you need to append given content to original content. This function has void return type.
 
 readContentFromFile: Given a file path, return its content in string format.
 
 Example:
 
 Input:
 ["FileSystem","ls","mkdir","addContentToFile","ls","readContentFromFile"]
 [[],["/"],["/a/b/c"],["/a/b/c/d","hello"],["/"],["/a/b/c/d"]]
 Output:
 [null,[],null,null,["a"],"hello"]
 Explanation:
 filesystem
 
 Note:
 
 You can assume all file or directory paths are absolute paths which begin with / and do not end with / except that the path is just "/".
 You can assume that all operations will be passed valid parameters and users will not attempt to retrieve file content or list a directory or file that does not exist.
 You can assume that all directory names and file names only contain lower-case letters, and same names won't exist in the same directory.
 */
class FileSystem {
	private static final String SEPARATOR = "/";
	private final File root;
	
	public FileSystem() {
		this.root = new File();
	}
	
	/**
	 * Given a path in string format. If it is a file path, return a list that only contains this file's name.
	 * If it is a directory path, return the list of file and directory names in this directory.
	 * Your output (file and directory names together) should in lexicographic order.
	 */
	public List<String> ls(String path) {
		String[] dirs = path.split(SEPARATOR);
		File current = this.root;
		String name = "";
		for (String dir : dirs) {
			if (dir.isEmpty()) { // corner case: "/a/b/c///d/e"
				continue;
			}
			File child = current.children.get(dir);
			if (child == null) {
				return Collections.emptyList();
			}
			current = child;
			name = dir;
		}
		
		if (current.isFile) { // is file
			List<String> result = new ArrayList<>(1);
			result.add(name);
			return result;
		}
		
		// is directory path
		return current.children.keySet()
							.stream()
							.sorted()
							.collect(Collectors.toList());
	}
	
	/**
	 * Given a directory path that does not exist, you should make a new directory according to the path.
	 * If the middle directories in the path don't exist either, you should create them as well.
	 * This function has void return type.
	 */
	public void mkdir(String path) {
		upsertDir(path);
	}
	
	/**
	 * Given a file path and file content in string format.
	 * If the file doesn't exist, you need to create that file containing given content.
	 * If the file already exists, you need to append given content to original content.
	 * This function has void return type.
	 */
	public void addContentToFile(String filePath, String content) {
		File current = upsertDir(filePath);
		current.isFile = true;
		current.content += content;
	}
	
	/**
	 * Given a file path, return its content in string format.
	 */
	public String readContentFromFile(String filePath) {
		return upsertDir(filePath).content;
	}
	
	private File upsertDir(String path) {
		String[] dirs = path.split(SEPARATOR);
		File current = this.root;
		for (String dir : dirs) {
			if (dir.isEmpty()) {
				continue;
			}
			current = current.children.computeIfAbsent(dir, k -> new File());
		}
		return current;
	}
	
	public static void main(String[] args) {
		System.out.println("/a/b".split("/")[0].isEmpty());
	}
}

class ImageDirectory {
	int sum;
	int numberOfimg;
	
	private static final String[] suffixes = new String[] { ".jpeg", ".gif", ".png" };
	
	// https://leetcode.com/problems/longest-absolute-file-path/description/
	// use stack to keep track of its parent directories
	// every time we go into a further directory, we push the parent directory into the stack,
	// and the stack is keeping track of length - "absolute path length of parent directory"
	
	// string input "dir\n\tsubdir1\n\tsubdir2\n\t\tfile.ext" is represented as 
	// dir
	// 	  subdir1
	//    subdir2
	//       file.ext	  
	public int solution2(String input) {
		// iterative approach
		
		// STACK: this stack stores path length of every directory
		Stack<Integer> stack = new Stack<>();
		stack.push(1); // "dummy" length => "/", lev is 0 now
		int res = 0;
		for (String s : input.split("\n")) {
			int lev = s.lastIndexOf("\t") + 1; // number of "\t" or " "
			while (lev + 1 < stack.size()) {
				// lev + 1 = stack.size() iterate over sibling element under the same parent directory
				// lev + 1 > stack.size() go over child element under the same parent directory
				stack.pop(); // find parent
			}
			
			// remove: "/t" -> - lev
			// add:    "/"  -> + 1
			int l = stack.peek();
			int len = l + s.length() - lev + 1; 
			
			stack.push(len);
			// check if it is file
			for (String suffix : suffixes) {
				if (s.endsWith(suffix)) {
					res += l - 1; // find all path length
							      // -1 removes dummy length "/" ?
					break;
				}
			}
		}
		return res;
	}
	
	public int solution(String S) {
		sum = 0;
		numberOfimg = 0;
		String[] ss = S.split("\n");
		helper(ss);
		return sum == 0 ? numberOfimg : sum;
	}
	
	public void helper(String[] ss) {
		Set<String> set = new HashSet<>();
		StringBuilder b = new StringBuilder();
		Stack<Integer> st = new Stack<>();
		int index = 0;
		boolean[] visit = new boolean[ss.length];
		int num = 0;
		
		while (index < ss.length) {
			st.push(index++);
			while (!st.empty()) {
				int s = st.peek();
				if (!visit[s]) {
					visit[s] = true;
					if (!ss[s].contains(".")) {
						num = num + 1 + ss[s].trim().length();
						b.append('/');
						b.append(ss[s].trim());
					}
				}
				
				if (check(ss[s]) && set.add(b.toString())) {
					numberOfimg++;
					sum = sum + num;
				}
				
				if (index < ss.length && isNextLev(ss[index], ss[s])) {
					st.push(index++);
				} else {
					int s1 = st.pop();
					if (!ss[s1].contains(".")) {
						b.delete(b.length() - ss[s1].trim().length() - 1, b.length());
						num = num - 1 - ss[s1].trim().length();
					}
				}
			}
		}
	}
	
	public boolean isNextLev(String s1, String s) {
		int a = 0;
		int b = 0;
		for (int i = 0; i < s.length(); i++) {
			if (s.charAt(i) != ' ') {
				a = i;
				break;
			}
		}
		for (int j = 0; j < s1.length(); j++) {
			if (s1.charAt(j) != ' ') {
				b = j;
				break;
			}
		}
		return b - a == 1;
	}
	
	public boolean check(String s) {
		String s2 = s.trim();
		int a = 0;
		for (int i = 0; i < s2.length(); i++) {
			if (s2.charAt(i) == '.') {
				a = i;
				break;
			}
		}
		String s1 = s2.substring(a);
		return s1.equals(".jpeg") || s1.equals(".gif") || s1.equals(".png");
	}
	
	public static void main(String[] args) {
		ImageDirectory s = new ImageDirectory();
		String lines = "dir1\n" +
				" dir11\n" + " dir12\n" + "  picture.jpeg\n" + "  dir121\n" + "  file1.txt\n" +
				"dir2\n" + " file2.gif\n";
		System.out.println(s.solution(lines));
		System.out.println(s.solution2(lines));
	}
}

class nextCloestTime {
	/**
	 * Next Closest Time
	 Given a time represented in the format "HH:MM", form the next closest time by reusing the current digits. There is no limit on how many times a digit can be reused.
	 
	 You may assume the given input string is always valid. For example, "01:34", "12:09" are all valid. "1:34", "12:9" are all invalid.
	 
	 Example 1:
	 
	 Input: "19:34"
	 Output: "19:39"
	 Explanation: The next closest time choosing from digits 1, 9, 3, 4, is 19:39, which occurs 5 minutes later.  It is not 19:33, because this occurs 23 hours and 59 minutes later.
	 
	 Example 2:
	 
	 Input: "23:59"
	 Output: "22:22"
	 Explanation: The next closest time choosing from digits 2, 3, 5, 9, is 22:22. It may be assumed that the returned time is next day's time since it is smaller than the input time numerically
	 */
	public String nextClosestTime(String time) {
		Set<Integer> s = new TreeSet<>();
		for (int i : new int[] { 0, 1, 3, 4 }) {
			s.add(time.charAt(i) - '0');
		}
		List<Integer> l = getCombo(s);
		Collections.sort(l); // unnecessary?
		int curH = Integer.parseInt(time.substring(0, 2));
		int curM = Integer.parseInt(time.substring(3));
		// First try m > curM
		int index = Collections.binarySearch(l, curM); // curM must in the list l, index >= 0
		if (index < l.size() - 1) {
			return String.format("%02d:%02d", curH, l.get(index + 1));
		}
		
		index = Collections.binarySearch(l, curH); // curH must in the list l, index >= 0
		if (index == l.size() - 1 || l.get(index + 1) > 23) {
			index = -1; // to get the smallest hour like 22 in the example2
		}
		return String.format("%02d:%02d", l.get(index + 1), l.get(0));
	}
	
	private List<Integer> getCombo(Set<Integer> s) {
		List<Integer> l = new ArrayList<>(s);
		List<Integer> res = new ArrayList<>();
		for (int i = 0; i < l.size(); i++) {
			int h = l.get(i);
			if (h >= 6) {
				return res;
			}
			for (int j = 0; j < l.size(); j++) {
				int c = h * 10 + l.get(j);
				res.add(c);
			}
		}
		return res;
	}
}

public class Code13 {
	public static void main (String[] args) {
		//@1 In-Memory File System
		FileSystem testFileSystem = new FileSystem();
		testFileSystem.main(args);
		//@2 Image Directory
		ImageDirectory testImageDirectory = new ImageDirectory();
		testImageDirectory.main(args);
		//@3 Find kth largest num from remote machine -> See the video
		//@4 Next Cloest Time
		
	}
}


// Set Matrix Zeroes
// Given a m x n matrix, if an element is 0, set its entire row and column to 0. Do it in-place.
/*
Follow up:

A straight forward solution using O(mn) space is probably a bad idea.
A simple improvement uses O(m + n) space, but still not the best solution.
Could you devise a constant space solution?
*/

class Solution {
    public void setZeroes(int[][] matrix) {
        // invalid input
        if (matrix == null || matrix.length == 0 || matrix[0].length == 0) {
            return;
            // // throw new IllegalArgumentException();
        }
        
        // constant space 
        int col0 = 1, row = matrix.length, col = matrix[0].length;
        
        for (int i = 0; i < row; i++) {
            if (matrix[i][0] == 0) {
                col0 = 0;
            }// The reason we treat this the first col separately is that...
              /* we use first col [0...length - 1] to indicate the whole row should be populated as 0
                 matrix[2][0] == 0 -> the 3rd row ...
                 
                 use first row [1...length - 1] to indicate the whole col should be populated as 0
                 matrix[0][2] == 0 -> the 3rd col ...
                 
                 in this case, the 1st col indicator is missing, 
                 so I use a single variable as the indicator, only constant space is needed
                 
               */
            for (int j = 1; j < col; j++) {
                if (matrix[i][j] == 0) {
                    matrix[i][0] = matrix[0][j] = 0;
                }
            }
        }
        
        for (int i = row - 1; i >= 0; i--) {            
            for (int j = col - 1; j >= 1; j--) {
                if (matrix[i][0] == 0 || matrix[0][j] == 0) {
                    matrix[i][j] = 0;
                }                                   
            }
            if (col0 == 0) { // put it after loop, NOT before loop
                matrix[i][0] = 0;
            }
        }
    }
}

// Global and Local Inversions
// The number of (global) inversions is the number of i < j with 0 <= i < j < N and A[i] > A[j].
// The number of local inversions is the number of i with 0 <= i < N and A[i] > A[i+1].
// Return true if and only if the number of global inversions is equal to the number of local inversions.
class Solution {
    public boolean isIdealPermutation(int[] A) {
        // If it is a local inversion, it is also a (global) inversion.
        // If the number of local inversions = the number of (global) inversions,
        // all inversions are local
        
        if (A == null || A.length == 0) {
            return true; // ?
        }
        
        int max = A[0];
        for (int i = 0; i < A.length - 2; i++) {
            max = Math.max(max, A[i]);
            if (max > A[i + 2]) {
                return false;
            }
        }
        
        return true;
    }
}

// Partition Labels
/*
A string S of lowercase letters is given. We want to partition this string into as many parts as possible so that each letter appears in at most one part, and return a list of integers representing the size of these parts.

Example 1:
Input: S = "ababcbacadefegdehijhklij"
Output: [9,7,8]
Explanation:
The partition is "ababcbaca", "defegde", "hijhklij".
This is a partition so that each letter appears in at most one part.
A partition like "ababcbacadefegde", "hijhklij" is incorrect, because it splits S into less parts.
*/
class Solution {
    public List<Integer> partitionLabels(String S) {
        List<Integer> res = new ArrayList<>();
        if (S == null || S.length() == 0) {
            return res;
        }    
        
        // use map to reduce time - remember
        // use map to record info - remember
        int[] map = new int[26];
        for (int i = 0; i < S.length(); i++) {
            map[S.charAt(i) - 'a'] = i;
        }
        
        int s = 0, e = 0;
        for (int i = 0; i < S.length(); i++) {
            e = Math.max(e, map[S.charAt(i) - 'a']);
            
            if (i == e) {
                res.add(e - s + 1);
                e = s = i + 1;
            }
        }
        
        return res;
    }
}

// Non-decreasing Array
/*
Given an array with n integers, your task is to check if it could become non-decreasing by modifying at most 1 element.

We define an array is non-decreasing if array[i] <= array[i + 1] holds for every i (1 <= i < n).

Example 1:
Input: [4,2,3]
Output: True
Explanation: You could modify the first 4 to 1 to get a non-decreasing array.
Example 2:
Input: [4,2,1]
Output: False
Explanation: You can't get a non-decreasing array by modify at most one element.
 */

// greedy
/*
This problem is like a greedy problem. 
When you find nums[i-1] > nums[i] for some i, you will prefer to 
change nums[i-1]'s value, since a larger nums[i] will give you more 
risks that you get inversion errors after position i. 

But, if you also find nums[i-2] > nums[i], then you have to change nums[i]'s value instead, or else you need to change both of nums[i-2]'s and nums [i-1]'s values.
*/
class Solution {
    public boolean checkPossibility(int[] nums) {
        if (nums == null || nums.length == 0) {
            return true;
        }
        
        int chg = 0;
        for (int i = 1; i < nums.length; i++) {
            if (nums[i - 1] > nums[i]) {
                chg++;
                if (i - 2 < 0 || nums[i - 2] <= nums[i]) {
                    /*
                        2   3  2   ->    2   2  2
                       i-2 i-1 i        i-2 i-1 i
                       
                       or
                       
                       3 2         ->    2 2
                       0 1               0 1
                     */
                    nums[i - 1] = nums[i];
                } else {
                    /*
                        3   4  2   ->    2   4  4     
                       i-2 i-1 i        i-2 i-1 i
                     */
                    nums[i] = nums[i - 1];                        
                }   
            }
        }
        
        return chg <= 1;
    }
}

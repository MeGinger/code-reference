import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Class1 {
	/**
	 * Task Scheduler
	 Given a char array representing tasks CPU need to do. It contains capital letters A to Z where different letters represent different tasks.Tasks could be done without original order. Each task could be done in one interval. For each interval, CPU could finish one task or just be idle.
	 However, there is a non-negative cooling interval n that means between two same tasks, there must be at least n intervals that CPU are doing different tasks or just be idle.
	 You need to return the least number of intervals the CPU will take to finish all the given tasks.
	 Example 1:
	 Input: tasks = ['A','A','A','B','B','B'], n = 2
	 Output: 8
	 Explanation: A -> B -> idle -> A -> B -> idle -> A -> B.
	 
	 Note:
	 The number of tasks is in the range [1, 10000].
	 The integer n is in the range [0, 100].
	 */
	public int leastInterval(char[] tasks, int n) {
		int[] c = new int[26];
		for (char t : tasks) {
			c[t - 'A']++;
		}
		Arrays.sort(c);
		int i = 25;
		while (i >= 0 && c[i] == c[25]) {
			i--;
		}
		
		// c[25]-1: we have totally "c[25]" frames,
		// *(n+1): the length of each frame, each of the first c[25]-1 frames must have a length of "n+1"
		// +25-i: count for the most frequent letters, it is the length of the last frame
		return Math.max(tasks.length, (c[25] - 1) * (n + 1) + 25 - i);
	}
	
	/**
	 * Rearrange String k Distance Apart
	 Given a non-empty string s and an integer k, rearrange the string such that the same characters are at least distance k from each other.
	 All input strings are given in lowercase letters. If it is not possible to rearrange the string, return an empty string "".
	 
	 Example 1:
	 s = "aabbcc", k = 3
	 Result: "abcabc"
	 The same letters are at least distance 3 from each other.
	 
	 Example 2:
	 s = "aaabc", k = 3
	 Answer: ""
	 It is not possible to rearrange the string.
	 
	 Example 3:
	 s = "aaadbbcc", k = 2
	 Answer: "abacabcd"
	 
	 Another possible answer is: "abcabcda"
	 The same letters are at least distance 2 from each other.
	 
	 Reference: task scheduler with cool down time https://discuss.leetcode.com/topic/112/minimal-run-time-scheduler/7
	 
	 This is a greedy problem.
	 Every time we want to find the best candidate: which is the character with the largest remaining count. Thus we will be having two arrays.
	 One count array to store the remaining count of every character. Another array to keep track of the most left position that one character can appear.
	 We will iterated through these two array to find the best candidate for every position. Since the array is fixed size, it will take constant time to do this.
	 After we find the candidate, we update two arrays.
	 */
	public String rearrangeString(String str, int k) {
		int length = str.length();
		// count frequency of each char
		Map<Character, Integer> count = new HashMap<>();
		for (char c : str.toCharArray()) {
			count.put(c, count.getOrDefault(c, 0) + 1);
		}
		Map<Character, Integer> valid = new HashMap<>();
		StringBuilder sb = new StringBuilder();
		for (int index = 0; index < length; index++) {
			char candidate = findValidMax(count, valid, index);
			if (candidate == ' ') {
				return "";
			}
			int frequency = count.get(candidate) - 1;
			if (frequency == 0) {
				count.remove(candidate);
			} else {
				count.put(candidate, frequency);
			}
			valid.put(candidate, index + k);
			sb.append(candidate);
		}
		return sb.toString();
	}
	
	private char findValidMax(Map<Character, Integer> count, Map<Character, Integer> valid, int index) {
		int max = Integer.MIN_VALUE;
		char candidate = ' ';
		for (Map.Entry<Character, Integer> entry : count.entrySet()) {
			if (entry.getValue() > max && index >= valid.getOrDefault(entry.getKey(), 0)) {
				max = entry.getValue();
				candidate = entry.getKey();
			}
		}
		return candidate;
	}
}

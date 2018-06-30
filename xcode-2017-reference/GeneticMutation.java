package twitter;

import java.util.*;

public class GeneticMutation {
	private static final char[] charSet = new char[] { 'A', 'C', 'G', 'T' };

	public int findMutationDistance(String start, String end, String[] bank) {
		Set<String> dict = new HashSet<>(Arrays.asList(bank));
		if (!dict.contains(end)) {
			return -1;
		}
		// Two-end BFS is much faster than one-end BFS
		Set<String> set1 = new HashSet<>();
		set1.add(start);
		Set<String> set2 = new HashSet<>();
		set2.add(end);

		int len = 1;
		while (true) {
			if (set1.size() > set2.size()) {
				// swap set1 and set2
				Set<String> set = set1;
				set1 = set2;
				set2 = set;
			}

			// set1.size() <= set2.size()
			if (set1.isEmpty()) {
				break;
			}
			// set for the next level
			Set<String> set = new HashSet<>();
			for (String str : set1) {
				for (String word : getOneEditWords(str)) {
					if (set2.contains(word)) {
						return len;
					}
					if (dict.contains(word)) {
						set.add(word);
						dict.remove(word);
					}
				}
			}

			set1 = set;
			len++;
		}

		return -1;
	}

	// return all the possible words of one-edit character of given word
	// return iterator with lazy return or just inline this function will save
	// space
	private static Set<String> getOneEditWords(String str) {
		Set<String> words = new HashSet<>();
		for (int i = 0; i < str.length(); i++) {
			for (char c : charSet) {
				String tmp = str.substring(0, i) + c + str.substring(i + 1, str.length());
				words.add(tmp);
			}
		}
		return words;
	}
}

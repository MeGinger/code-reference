 /* Lesson 13
 * In-Memory File System
 * Image Directory
 * Find kth largest num from remote machine
 * */
import java.util.*;
import java.util.stream.Collectors;


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
			if (dir.isEmpty()) {
				continue;
			}
			File child = current.children.get(dir);
			if (child == null) {
				return Collections.emptyList();
			}
			current = child;
			name = dir;
		}
		
		if (current.isFile) {
			List<String> result = new ArrayList<>(1);
			result.add(name);
			return result;
		}
		return current.children.keySet().stream().sorted().collect(Collectors.toList());
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
	// use stack to keep track of its parent
	// every time we go into a further directory, we push the parent directory into the stack,
	// and the stack is keeping track of length - "absolute path length of parent directory"
	public int solution2(String input) {
		Stack<Integer> stack = new Stack<>();
		stack.push(1); // "dummy" length => "/"
		int res = 0;
		for (String s : input.split("\n")) {
			int lev = s.lastIndexOf(" ") + 1; // number of "\t" or " "
			while (lev + 1 < stack.size()) {
				stack.pop(); // find parent
			}
			// remove "/t", add "/"
			int l = stack.peek();
			int len = l + s.length() - lev + 1;
			stack.push(len);
			// check if it is file
			for (String suffix : suffixes) {
				if (s.endsWith(suffix)) {
					res += l - 1; // find all path length
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

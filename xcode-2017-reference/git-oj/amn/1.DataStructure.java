Min Stack

push(x) -- Push element x onto stack.
pop() -- Removes the element on top of the stack.
top() -- Get the top element.
getMin() -- Retrieve the minimum element in the stack.


class MinStack {
    private Stack<Integer> stack;
    private Stack<Integer> minStack;

    public MinStack() {
        stack = new Stack<>();
        minStack = new Stack<>();
    }
    
    public void push(int x) {
        stack.push(x);
        
        // I FORGET - minStack.isEmpty()
        if (minStack.isEmpty() || minStack.peek() > x) {
            minStack.push(x);
        } else {
            minStack.push(minStack.peek());
        }
    }
    
    public void pop() {
        stack.pop();
        minStack.pop();
    }
    
    public int top() {
        return stack.peek();
    }
    
    public int getMin() {
        return minStack.peek();
    }
}


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
    // file path is alwasy valid? Yes, 上面说明有写
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

class File {
    Map<String, File> children; // dir name <-> File
    boolean isFile; // dir or file
    String content; // content if file
    
    public File() {
        this.children = new HashMap<>();
        this.content = ""; // not NULL
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

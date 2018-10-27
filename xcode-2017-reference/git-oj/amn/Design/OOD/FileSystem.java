package amazon.ood;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

class File {
	Map<String, File> children; // dir name <-> File
	boolean isFile; // dir or file
	String content; // content if file

	public File() {
		this.children = new HashMap<>();
		this.content = ""; // not NULL
	}
}

public class FileSystem {
	private static final String SEPARATOR = "/";
	private final File root;

	public FileSystem() {
		this.root = new File();
	}

	// find under path a list of files whose filename contains key
	public List<String> find(String path, String key) {
		if (path == null || key == null) {
			return Collections.emptyList();
		}
		
		// what is separator
		
		// if path is invalid
		
		// if path is a file path?
		// if path if a directory path?

		// what is search criteria - equals, startsWith, endsWith, contains, !equals
		
		List<String> res = new ArrayList<>();
		
		String[] dirs = path.split(SEPARATOR);
		File current = this.root;
		String name = "";
		for (String dir : dirs) {
			if (dir.isEmpty()) {
				continue;
			}
			
			File child = current.children.get(dir);
			if (child == null) {
				throw new IllegalArgumentException();
			}
			
			current = child;
			name = dir;
		}
		
		if (current.isFile) {
			if (name.contains(key)) {
				res.add(name);
				return res;
			}
		}
		
		dfs(current, key, res);
		return res;
	}

	private void dfs(File file, String key, List<String> res) {
		
		for (Map.Entry<String, File> child : file.children.entrySet()) {
			File childFile = child.getValue();
			String childName = child.getKey();
			
			if (childFile.isFile && childName.contains(key)) {
				res.add(childName);
			}
			
			dfs(childFile, key, res);
		}
	}
	
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

		// file
		if (current.isFile) {
			List<String> res = new ArrayList<>(1);
			res.add(name);
			return res;
		}

		// directory
		// Collector: interface
		// collect(Collector Implementation);
		return current.children.keySet().stream().sorted().collect(Collectors.toList());
	}

	public void mkdir(String path) {
		upsertDir(path);
	}

	public void addContentToFile(String filePath, String content) {
		File current = upsertDir(filePath);
		current.isFile = true;
		current.content += content;
	}

	// file path is always valid? If not, this implementation will create
	// directories
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
}

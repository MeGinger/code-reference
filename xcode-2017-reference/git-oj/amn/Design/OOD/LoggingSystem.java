package amazon.ood;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

abstract class Application {
}

class FooApp extends Application {
}

class BarApp extends Application {
}

class BazApp extends Application {
}

abstract class Category {
}

class ExceptionType extends Category {
}

enum Position {
	START, MIDDLE, END;
}

class Log {
	Application app;
	Category category;
	Position position;
}

public class LoggingSystem {
	List<Log> logs;

	// considering it is a log stream
	Map<String, Integer> topK = new HashMap<>();
	
	public LoggingSystem() {
		this.logs = new ArrayList<>();
	}

	// chronological order
	public void addLog(Log log) {
		this.logs.add(log);
	}


	public List<String> topK(Application app, Category category, int k) {
		List<Log> candidates = new ArrayList<>();
		for (Log log : logs) {
			if (!log.app.equals(app) || !log.category.equals(category)) {
				continue;
			}
			candidates.add(log);
		}

		List<List<Log>> logGroup = new ArrayList<>();
		List<Log> group = new ArrayList<>();
		for (Log candidate : candidates) {
			if (candidate.position.equals(Position.START)) {
				logGroup.add(new ArrayList<>(group));
				group.clear();
			}
			group.add(candidate);
		}
		logGroup.add(new ArrayList<>(group));

		Map<String, Integer> count = new HashMap<>();
		for (List<Log> g : logGroup) {
			String key = getKey(g);
			count.put(key, count.getOrDefault(key, 0) + 1);
		}

		List<String> res = new ArrayList<>();
		// priority queue, if using queue, data is static, since element in
		// queue is considered as immutable
		
		// bucket sort

		return res;
	}

	private String getKey(List<Log> group) {
		return "";
	}
}

package amazon.ood;

import java.util.HashMap;
import java.util.Map;

class Lane {
	int id;
	String name;
}

class Airplane {
	int id;
	String name;
}

enum ADirection {
	TAKEOFF, LAND
}

class Schedule {
	EDirection direction;
	int dayOfWeek;
	long startTime;
	long duration;// how much time to take off or land
}

public class AirplaneTrackingSystem {
	Map<Airplane, Schedule> schedules = new HashMap<>();
	Map<Map.Entry<Airplane, Schedule>, Lane> positions = new HashMap<>();

	// meeting room 2
	public void schedule() {
		// 1. categorize by day of week
		// 2. do a meeting room 2 follow up 
		// - assign meetings (pair of airplane and schedule) to rooms (lanes)
	}

	public void display(Airplane airplane) {
		schedules.get(airplane);
		positions.get(airplane);
	}
}

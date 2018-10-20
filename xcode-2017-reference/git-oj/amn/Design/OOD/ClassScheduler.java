package amazon.ood;

import java.util.ArrayList;
import java.util.List;

class Classroom {
	public String building;
	public int roomNumber;
	public int capacity;
}

class Course {
	public int courseId; // unique identifier
	public int enrolledStudent;
	public String title; // NOT a unique identifier
	public List<Time> times;
}

class Time {
	public String dayOfWeek;
	public String startTime;
	public String endTime;
}

class Student {
	public int stuId;
	public String name;
}

public class ClassScheduler {
	private List<Course> courses;
	private List<Classroom> classrooms;
	private List<Student> students;

	public ClassScheduler() {
		this.courses = new ArrayList<>();
		this.classrooms = new ArrayList<>();
		this.students = new ArrayList<>();
	}

	public void viewShedule() {
		// display mapping between courses and classrooms
	}

	// meeting room 2 - variation
	// categorize by dayOfWeek
	// for each day of week, do a meeting room 2 scheduling
	// interval [startTime, endTime]
	public void runScheduler() {
		// mapping between courses and classrooms
	}

	public void addCourse(Course course) {
		this.courses.add(course);
	}

	public void addClassroom(Classroom classroom) {
		this.classrooms.add(classroom);
	}

	public void addStudent(Student student) {
		this.students.add(student);
	}

	// etc.
	// view...
	// get...
	// remove...
}

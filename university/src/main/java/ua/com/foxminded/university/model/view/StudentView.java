package ua.com.foxminded.university.model.view;

import java.util.List;

import ua.com.foxminded.university.model.Group;
import ua.com.foxminded.university.model.Lecture;
import ua.com.foxminded.university.model.Student;

public class StudentView {

	private final Student student;
	private final Group group;
	private final List<Lecture> lectures;

	public StudentView(Student student, Group group, List<Lecture> lectures) {
		super();
		this.student = student;
		this.group = group;
		this.lectures = lectures;
	}

	public Student getStudent() {
		return student;
	}

	public Group getGroup() {
		return group;
	}

	public List<Lecture> getLectures() {
		return lectures;
	}

}

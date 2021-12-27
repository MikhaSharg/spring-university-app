package ua.com.foxminded.university.model.view;

import java.util.List;

import ua.com.foxminded.university.model.Group;
import ua.com.foxminded.university.model.Lecture;
import ua.com.foxminded.university.model.Student;

public class StudentView {

	private final Student student;
	private final Group group;

	public StudentView(Student student, Group group) {
		super();
		this.student = student;
		this.group = group;
	}

	public Student getStudent() {
		return student;
	}

	public Group getGroup() {
		return group;
	}

}

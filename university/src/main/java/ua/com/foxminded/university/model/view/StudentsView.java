package ua.com.foxminded.university.model.view;

import java.util.List;
import java.util.Map;

import ua.com.foxminded.university.model.Group;
import ua.com.foxminded.university.model.Student;

public class StudentsView {
	
	private final List<Student> students;
	private final Map<Long, Group> groups;

	public StudentsView(List<Student> students, Map<Long, Group> groups) {
		super();
		this.students = students;
		this.groups = groups;
	}

	public List<Student> getStudents() {
		return students;
	}

	public Map<Long, Group> getGroups() {
		return groups;
	}

}

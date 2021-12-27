package ua.com.foxminded.university.model.view;

import java.util.List;

import ua.com.foxminded.university.model.Teacher;

public class SubjectView {

	private final Long id;
	private final String name;
	private final List<Teacher> teachers;

	public SubjectView(Long id, String name, List<Teacher> teachers) {
		this.id = id;
		this.name = name;
		this.teachers = teachers;
	}

	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public List<Teacher> getTeachers() {
		return teachers;
	}

}

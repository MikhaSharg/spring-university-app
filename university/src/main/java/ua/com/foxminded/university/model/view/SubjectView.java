package ua.com.foxminded.university.model.view;

import java.util.List;

import ua.com.foxminded.university.model.Teacher;

public class SubjectView {

	private Long id;
	private String name;
	private List<Teacher> teachers;

	public SubjectView(Long id, String name, List<Teacher> teachers) {
		this.id = id;
		this.name = name;
		this.teachers = teachers;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Teacher> getTeachers() {
		return teachers;
	}

	public void setTeachers(List<Teacher> teachers) {
		this.teachers = teachers;
	}

}

package ua.com.foxminded.university.wrappers;

import java.util.ArrayList;
import java.util.List;

import ua.com.foxminded.university.model.Subject;
import ua.com.foxminded.university.model.Teacher;

public class SubjectWrapper {

	private Long subjectId;
	private String name;

	private Teacher teacher;
	private Subject subject;
	private Subject newSubject;

	private List<Teacher> teachers = new ArrayList<>();

	public SubjectWrapper() {
		super();
	}

	public SubjectWrapper(Teacher teacher) {
		this.teacher = teacher;
		teachers.add(teacher);
	}

	public Long getSubjectId() {
		return subjectId;
	}

	public void setSubjectId(Long subjectId) {
		this.subjectId = subjectId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Teacher getTeacher() {
		return teacher;
	}

	public void setTeacher(Teacher teacher) {
		this.teacher = teacher;
	}

	public Subject getSubject() {
		return subject;
	}

	public void setSubject(Subject subject) {
		this.subject = subject;
	}

	public List<Teacher> getTeachers() {
		return teachers;
	}

	public void setTeachers(List<Teacher> teachers) {
		this.teachers = teachers;
	}

	public Subject getNewSubject() {
		return new Subject(name);
	}

	public void setNewSubject(Subject newSubject) {
		this.newSubject = newSubject;
	}

}

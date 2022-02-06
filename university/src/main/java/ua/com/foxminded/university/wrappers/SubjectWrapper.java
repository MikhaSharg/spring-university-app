package ua.com.foxminded.university.wrappers;

import java.util.ArrayList;
import java.util.List;

import ua.com.foxminded.university.model.Subject;
import ua.com.foxminded.university.model.Teacher;

public class SubjectWrapper {

	private Long id;
	private String name;

	private Long teacherId;
	private Teacher teacher;
	private Subject subject;
	private Subject newSubject;

	private List<Teacher> teachers = new ArrayList<>();

	public SubjectWrapper() {
		super();
	}

	public SubjectWrapper(Subject subject, Teacher teacher, List<Teacher> teachers) {
		super();
		this.teachers = teachers;
		this.teacher = teacher;
		this.subject = subject;
		this.id = subject.getId();
		this.name = subject.getName();
	}

	public SubjectWrapper(Subject subject, List<Teacher> teachers) {
		super();
		this.teachers = teachers;
		this.subject = subject;
		this.id = subject.getId();
		this.name = subject.getName();
	}

	public SubjectWrapper(Teacher teacher) {
		this.teacher = teacher;
		teachers.add(teacher);
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

		if (id == null) {
			return new Subject(name);
		} else {

			System.out.println("created");
			return new Subject(id, name);
		}
	}

	public Long getTeacherId() {
		return teacherId;
	}

	public void setTeacherId(Long teacherId) {
		this.teacherId = teacherId;
	}

	public void setNewSubject(Subject newSubject) {
		this.newSubject = newSubject;
	}

	@Override
	public String toString() {
		return "SubjectWrapper [id=" + id + ", name=" + name + ", teacherId=" + teacherId + ", teacher=" + teacher
				+ ", subject=" + subject + ", newSubject=" + newSubject + ", teachers=" + teachers + "]";
	}

}

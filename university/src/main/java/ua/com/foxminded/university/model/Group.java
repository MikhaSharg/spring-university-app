package ua.com.foxminded.university.model;

import java.util.ArrayList;
import java.util.List;

public class Group extends IdEntity {

	private String name;
	private List<Student> students;

	public Group() {
		super();
	}

	public Group(Long id, String name, List<Student> students) {
		super(id);
		this.name = name;
		this.students = students;
	}

	public Group(String name, List<Student> srudents) {
		super(null);
		this.name = name;
		this.students = srudents;
	}

	public Group(String name) {
		super(null);
		this.name = name;
		this.students = new ArrayList<>();
	}

	public Group(Long id, String name) {
		super(id);
		this.name = name;
		this.students = new ArrayList<>();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Student> getStudents() {
		return students;
	}

	public void setStudents(List<Student> students) {
		this.students = students;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((students == null) ? 0 : students.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		Group other = (Group) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (students == null) {
			if (other.students != null)
				return false;
		} else if (!students.equals(other.students))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return name;
	}

}

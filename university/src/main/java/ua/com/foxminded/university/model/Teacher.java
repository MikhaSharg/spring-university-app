package ua.com.foxminded.university.model;

import java.util.ArrayList;
import java.util.List;

public class Teacher extends AbstractPerson {

	private String profile;
	private List<Subject> subjects = new ArrayList<>();
	private Boolean isFired = false;
	
	public Teacher() {
	}

	public Teacher(Long id, String firstName, String lastName, String gender, String email, String address, Integer age,
			Long phoneNumber, String role, String profile, List<Subject> subjects) {
		super(id, firstName, lastName, gender, email, address, age, phoneNumber, role);
		this.profile = profile;
		this.subjects = subjects;
	}

	public Teacher(Long id, String firstName, String lastName, String gender, String email, String address, Integer age,
			Long phoneNumber, String role, String profile) {
		super(id, firstName, lastName, gender, email, address, age, phoneNumber, role);
		this.profile = profile;
	}

	public Teacher(String firstName, String lastName, String gender, String email, String address, Integer age,
			Long phoneNumber, String role, String profile, List<Subject> subjects) {
		super(null, firstName, lastName, gender, email, address, age, phoneNumber, role);
		this.profile = profile;
		this.subjects = subjects;
	}

	public Teacher(String firstName, String lastName, String gender, String email, String address, Integer age,
			Long phoneNumber, String role, String profile) {
		super(null, firstName, lastName, gender, email, address, age, phoneNumber, role);
		this.profile = profile;
	}

	public String getProfile() {
		return profile;
	}

	public void setProfile(String profile) {
		this.profile = profile;
	}

	public List<Subject> getSubjects() {
		return subjects;
	}

	public void setSubjects(List<Subject> subjects) {
		this.subjects = subjects;
	}

	public void addSubject(Subject subject) {
		this.subjects.add(subject);
	}
	
	public Boolean getIsFired() {
		return isFired;
	}

	public void setIsFired(Boolean isFired) {
		this.isFired = isFired;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((profile == null) ? 0 : profile.hashCode());
		result = prime * result + ((subjects == null) ? 0 : subjects.hashCode());
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
		Teacher other = (Teacher) obj;
		if (profile == null) {
			if (other.profile != null)
				return false;
		} else if (!profile.equals(other.profile))
			return false;
		if (subjects == null) {
			if (other.subjects != null)
				return false;
		} else if (!subjects.equals(other.subjects))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return getFirstName() + " " + getLastName() + ", " + profile;
	}
	
}

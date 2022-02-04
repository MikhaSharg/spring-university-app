package ua.com.foxminded.university.wrappers;

import java.util.Arrays;
import java.util.List;

import ua.com.foxminded.university.model.Subject;
import ua.com.foxminded.university.model.Teacher;

public class TeacherWrapper {

	private Long id;
	private String firstName;
	private String lastName;
	private String gender;
	private String email;
	private String address;
	private Integer age;
	private Long phoneNumber;
	private String profile;
	private List<Subject> subjects;
	
	private final List<String> genders = Arrays.asList(GENDER_FEMALE, GENDER_MALE);
	
	private Teacher newTeacher;
	private Teacher oldTeacher;
	
	private static final String GENDER_MALE = "male";
	private static final String GENDER_FEMALE = "female";
	private static final String ROLE_TEACHER = "teacher";
	
	public TeacherWrapper() {
		super();
	}
	
	public TeacherWrapper(Teacher oldTeacher) {
		super();
		this.oldTeacher = oldTeacher;
		this.id=oldTeacher.getId();
		this.firstName=oldTeacher.getFirstName();
		this.lastName=oldTeacher.getLastName();
		this.gender=oldTeacher.getGender();
		this.email=oldTeacher.getEmail();
		this.address=oldTeacher.getAddress();
		this.age=oldTeacher.getAge();
		this.phoneNumber=oldTeacher.getPhoneNumber();
		this.profile=oldTeacher.getProfile();
		this.subjects=oldTeacher.getSubjects();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public Long getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(Long phoneNumber) {
		this.phoneNumber = phoneNumber;
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
	
	public Teacher getNewTeacher() {
		if (this.id == null) {
			return new Teacher(firstName, lastName, gender, email, address, age, phoneNumber, ROLE_TEACHER,
					profile);
		} else {
			return new Teacher(id, firstName, lastName, gender, email, address, age, phoneNumber, oldTeacher.getRole(),
					profile, oldTeacher.getSubjects());
		}
	}
	public void setNewTeacher(Teacher newTeacher) {
		this.newTeacher = newTeacher;
	}

	public Teacher getOldTeacher() {
		return oldTeacher;
	}

	public void setOldTeacher(Teacher oldTeacher) {
		this.oldTeacher = oldTeacher;
	}

	public List<String> getGenders() {
		return genders;
	}

	@Override
	public String toString() {
		return "TeacherWrapper [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", gender=" + gender
				+ ", email=" + email + ", address=" + address + ", age=" + age + ", phoneNumber=" + phoneNumber
				+ ", profile=" + profile + ", subjects=" + subjects + ", genders=" + genders + ", newTeacher="
				+ newTeacher + ", oldTeacher=" + oldTeacher + "]";
	}
	
}

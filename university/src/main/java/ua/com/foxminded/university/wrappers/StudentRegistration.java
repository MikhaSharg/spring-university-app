package ua.com.foxminded.university.wrappers;

import java.util.Arrays;
import java.util.List;

import ua.com.foxminded.university.model.Group;
import ua.com.foxminded.university.model.Student;

public class StudentRegistration {
	
	private String firstName;
	private String lastName;
	private String gender;
	private String email;
	private String address;
	private Integer age;
	private Long phoneNumber;
	private String groupName;
	
	private List<Group> avaliableGroups;
	private final List<String> genders = Arrays.asList(GENDER_FEMALE, GENDER_MALE);
	private Student student;
	
	private static final String GENDER_MALE = "male";
	private static final String GENDER_FEMALE = "female";
	
	public StudentRegistration(List<Group> avaliableGroups) {
		super();
		this.avaliableGroups = avaliableGroups;
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

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public List<Group> getAvaliableGroups() {
		return avaliableGroups;
	}

	public void setAvaliableGroups(List<Group> avaliableGroups) {
		this.avaliableGroups = avaliableGroups;
	}

	public Student getStudent() {
		
		Long groupId = avaliableGroups.stream().filter(gr->gr.getName().equals(groupName)).findAny().orElse(null).getId();
		this.student = new Student(firstName, lastName, gender, email, address, age, groupId, address, groupId);
		return student;
	}

	public void setStudent(Student student) {
		this.student = student;
	}

	public List<String> getGenders() {
		return genders;
	}

	@Override
	public String toString() {
		return "StudentRegistration [firstName=" + firstName + ", lastName=" + lastName + ", gender=" + gender
				+ ", email=" + email + ", address=" + address + ", age=" + age + ", phoneNumber=" + phoneNumber
				+ ", groupName=" + groupName + ", avaliableGroups=" + avaliableGroups + ", genders=" + genders
				+ ", student=" + student + "]";
	}
	
	
	

}

package ua.com.foxminded.university.wrappers;

import java.util.Arrays;
import java.util.List;

import ua.com.foxminded.university.model.Group;
import ua.com.foxminded.university.model.Student;

public class StudentWrapper {
	
	private Long id;
	private String firstName;
	private String lastName;
	private String gender;
	private String email;
	private String address;
	private Integer age;
	private Long phoneNumber;
	private Long groupId;
	private String groupName;
	
	private List<Group> avaliableGroups;
	private final List<String> genders = Arrays.asList(GENDER_FEMALE, GENDER_MALE);
	private Student student;
	private Student beforeUpdateStudent;
	
	private static final String GENDER_MALE = "male";
	private static final String GENDER_FEMALE = "female";
	
	public StudentWrapper() {
		super();
	}

	public StudentWrapper(List<Group> avaliableGroups) {
		super();
		this.avaliableGroups = avaliableGroups;
	}
	
	public StudentWrapper(Student beforeUpdateStudent, List<Group> avaliableGroups, String groupName) {
		super();
		this.avaliableGroups = avaliableGroups;
		this.beforeUpdateStudent = beforeUpdateStudent;
		
		this.firstName=beforeUpdateStudent.getFirstName();
		this.lastName=beforeUpdateStudent.getLastName();
		this.gender=beforeUpdateStudent.getGender();
		this.email=beforeUpdateStudent.getEmail();
		this.address=beforeUpdateStudent.getAddress();
		this.age=beforeUpdateStudent.getAge();
		this.phoneNumber=beforeUpdateStudent.getPhoneNumber();
		this.groupName=groupName;
		this.id=beforeUpdateStudent.getId();
	}

	public StudentWrapper(Student beforeUpdateStudent, List<Group> avaliableGroups) {
		super();
		this.avaliableGroups = avaliableGroups;
		this.beforeUpdateStudent = beforeUpdateStudent;
		this.id=beforeUpdateStudent.getId();
		this.firstName=beforeUpdateStudent.getFirstName();
		this.lastName=beforeUpdateStudent.getLastName();
		this.gender=beforeUpdateStudent.getGender();
		this.email=beforeUpdateStudent.getEmail();
		this.address=beforeUpdateStudent.getAddress();
		this.age=beforeUpdateStudent.getAge();
		this.phoneNumber=beforeUpdateStudent.getPhoneNumber();
		this.id=beforeUpdateStudent.getId();
		this.groupId=beforeUpdateStudent.getGroupId();
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
		this.student = new Student(firstName, lastName, gender, email, address, age, phoneNumber, address, groupId);
		return student;
	}

	public void setStudent(Student student) {
		this.student = student;
	}

	public List<String> getGenders() {
		return genders;
	}
	

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getGroupId() {
		return groupId;
	}

	public void setGroupId(Long groupId) {
		this.groupId = groupId;
	}

	public Student getBeforeUpdateStudent() {
		return beforeUpdateStudent;
	}

	@Override
	public String toString() {
		return "StudentWrapper [id=" + id + ",\n firstName=" + firstName + ",\n lastName=" + lastName + ",\n gender=" + gender
				+ ",\n email=" + email + ",\n address=" + address + ",\n age=" + age + ",\n phoneNumber=" + phoneNumber
				+ ",\n groupId=" + groupId + ",\n groupName=" + groupName + ",\n avaliableGroups=" + avaliableGroups
				+ ",\n genders=" + genders + ",\n student=" + student + ",\n beforeUpdateStudent=" + beforeUpdateStudent
				+ "]";
	}

	
	
	

}

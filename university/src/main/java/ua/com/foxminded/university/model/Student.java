package ua.com.foxminded.university.model;

public class Student extends AbstractPerson {

	private Long groupId;

	public Student(Long id, String firstName, String lastName, String gender, String email, String address, Integer age,
			Long phoneNumber, String role, Long gropId) {
		super(id, firstName, lastName, gender, email, address, age, phoneNumber, role);
		this.groupId = gropId;
	}

	public Student(String firstName, String lastName, String gender, String email, String address, Integer age,
			Long phoneNumber, String role, Long gropId) {
		super(firstName, lastName, gender, email, address, age, phoneNumber, role);
		this.groupId = gropId;
	}

	public Student(String firstName, String lastName, String gender, String email, String address, Integer age,
			Long phoneNumber) {
		super(firstName, lastName, gender, email, address, age, phoneNumber);
	}
	
	public Student(String firstName, String lastName, String gender, String email, String address, Integer age,
			Long phoneNumber, String role) {
		super(firstName, lastName, gender, email, address, age, phoneNumber, role);
	}

	public Student() {
	}

	public Long getGroupId() {
		return groupId;
	}

	public void setGroupId(Long groupId) {
		this.groupId = groupId;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((groupId == null) ? 0 : groupId.hashCode());
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
		Student other = (Student) obj;
		if (groupId == null) {
			if (other.groupId != null)
				return false;
		} else if (!groupId.equals(other.groupId))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return " Student\n [Id()=" + getId() + ",\n FirstName()=" + getFirstName() + ",\n LastName()=" + getLastName()
				+ ",\n Gender()=" + getGender() + ",\n Email()=" + getEmail() + ",\n Address()=" + getAddress()
				+ ",\n Age()=" + getAge() + ",\n PhoneNumber()=" + getPhoneNumber() + ",\n Role()=" + getRole()
				+ ",\n groupId=" + groupId + "]";
	}


}

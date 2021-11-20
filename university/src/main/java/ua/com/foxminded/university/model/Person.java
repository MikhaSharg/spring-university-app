package ua.com.foxminded.university.model;

public class Person extends AbstractPerson {

	public Person(Long id, String firstName, String lastName, String gender, String email, String address, Integer age,
			Long phoneNumber, String role) {
		super(id, firstName, lastName, gender, email, address, age, phoneNumber, role);
	}

	public Person(String firstName, String lastName, String gender, String email, String address, Integer age,
			Long phoneNumber, String role) {
		super(null, firstName, lastName, gender, email, address, age, phoneNumber, role);
	}

	@Override
	public int hashCode() {
		return super.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Person [getFirstName()=" + getFirstName() + "\n, getLastName()=" + getLastName() + "\n, getGender()="
				+ getGender() + "\n, getEmail()=" + getEmail() + "\n, getAddress()=" + getAddress() + "\n, getAge()="
				+ getAge() + "\n, getPhoneNumber()=" + getPhoneNumber() + "\n, getRole()=" + getRole() + "\n, toString()="
				+ super.toString() + "\n, getId()=" + getId() + "\n, getClass()=" + getClass() + "]";
	}

}

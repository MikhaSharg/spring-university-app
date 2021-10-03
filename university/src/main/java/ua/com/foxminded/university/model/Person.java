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

}

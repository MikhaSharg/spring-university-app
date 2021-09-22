package ua.com.foxminded.university.model;

public class Person extends AbstractPerson {

    public Person(Long id, String firstName, String lastName, String gender, String email, String address, Integer age,
            Integer phoneNumber) {
        super(id, firstName, lastName, gender, email, address, age, phoneNumber);
    }

    public Person(String firstName, String lastName, String gender, String email, String address, Integer age,
            Integer phoneNumber) {
        super(null, firstName, lastName, gender, email, address, age, phoneNumber);
    }

}

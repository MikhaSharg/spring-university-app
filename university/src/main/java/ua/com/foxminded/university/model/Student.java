package ua.com.foxminded.university.model;

public class Student extends AbstractPerson {

    private Group group;

    public Student(Long id, String firstName, String lastName, String gender, String email, String address, Integer age,
            Integer phoneNumber) {
        super(id, firstName, lastName, gender, email, address, age, phoneNumber);
    }

    public Student(String firstName, String lastName, String gender, String email, String address, Integer age,
            Integer phoneNumber, Group group) {
        super(null, firstName, lastName, gender, email, address, age, phoneNumber);
        this.group = group;
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + ((group == null) ? 0 : group.hashCode());
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
        if (group == null) {
            if (other.group != null)
                return false;
        } else if (!group.equals(other.group))
            return false;
        return true;
    }

}

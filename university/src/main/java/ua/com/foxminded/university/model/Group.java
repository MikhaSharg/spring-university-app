package ua.com.foxminded.university.model;

import java.util.List;

public class Group extends IdEntity {

    private String name;
    private List<Student> srudents;

    public Group(Long id, String name, List<Student> srudents) {
        super(id);
        this.name = name;
        this.srudents = srudents;
    }

    public Group(String name, List<Student> srudents) {
        super(null);
        this.name = name;
        this.srudents = srudents;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Student> getSrudents() {
        return srudents;
    }

    public void setSrudents(List<Student> srudents) {
        this.srudents = srudents;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        result = prime * result + ((srudents == null) ? 0 : srudents.hashCode());
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
        if (srudents == null) {
            if (other.srudents != null)
                return false;
        } else if (!srudents.equals(other.srudents))
            return false;
        return true;
    }

}

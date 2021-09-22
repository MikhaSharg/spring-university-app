package ua.com.foxminded.university.model;

import java.util.Date;

public class Lecture extends IdEntity {

    private String name;
    private Date date;
    private Audience audience;
    private Teacher teacher;
    private Group group;
    private Subject subject;

    public Lecture(Long id, String name, Date date, Audience audience, Teacher teacher, Group group, Subject subject) {
        super(id);
        this.name = name;
        this.date = date;
        this.audience = audience;
        this.teacher = teacher;
        this.group = group;
        this.subject = subject;
    }

    public Lecture(String name, Date date, Audience audience, Teacher teacher, Group group, Subject subject) {
        super(null);
        this.name = name;
        this.date = date;
        this.audience = audience;
        this.teacher = teacher;
        this.group = group;
        this.subject = subject;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Audience getAudience() {
        return audience;
    }

    public void setAudience(Audience audience) {
        this.audience = audience;
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + ((audience == null) ? 0 : audience.hashCode());
        result = prime * result + ((date == null) ? 0 : date.hashCode());
        result = prime * result + ((group == null) ? 0 : group.hashCode());
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        result = prime * result + ((subject == null) ? 0 : subject.hashCode());
        result = prime * result + ((teacher == null) ? 0 : teacher.hashCode());
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
        Lecture other = (Lecture) obj;
        if (audience == null) {
            if (other.audience != null)
                return false;
        } else if (!audience.equals(other.audience))
            return false;
        if (date == null) {
            if (other.date != null)
                return false;
        } else if (!date.equals(other.date))
            return false;
        if (group == null) {
            if (other.group != null)
                return false;
        } else if (!group.equals(other.group))
            return false;
        if (name == null) {
            if (other.name != null)
                return false;
        } else if (!name.equals(other.name))
            return false;
        if (subject == null) {
            if (other.subject != null)
                return false;
        } else if (!subject.equals(other.subject))
            return false;
        if (teacher == null) {
            if (other.teacher != null)
                return false;
        } else if (!teacher.equals(other.teacher))
            return false;
        return true;
    }

}

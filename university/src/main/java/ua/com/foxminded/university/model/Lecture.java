package ua.com.foxminded.university.model;

import java.sql.Date;
import java.time.LocalDate;
import java.util.Objects;

public class Lecture extends IdEntity implements Comparable<Lecture> {

	private LocalDate date;
	private LectureSessions session;
	private Audience audience;
	private Subject subject;
	private Teacher teacher;
	private Group group;

	public Lecture(LocalDate date) {
		super(null);
		this.date = date;

	}

	public Lecture(Long id, LocalDate date) {
		super(id);
		this.date = date;

	}

	public Lecture(LocalDate date, LectureSessions session, Audience audience, Subject subject, Teacher teacher,
			Group group) {
		super(null);
		this.date = date;
		this.session = session;
		this.audience = audience;
		this.subject = subject;
		this.teacher = teacher;
		this.group = group;
	}
	
	public Lecture(Long id, LocalDate date, LectureSessions session, Audience audience, Subject subject, Teacher teacher,
			Group group) {
		super(id);
		this.date = date;
		this.session = session;
		this.audience = audience;
		this.subject = subject;
		this.teacher = teacher;
		this.group = group;
	}
  

	public LocalDate getDate() {			
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public LectureSessions getSession() {
		return session;
	}

	public void setSession(LectureSessions session) {
		this.session = session;
	}

	public Audience getAudience() {
		return audience;
	}

	public void setAudience(Audience audience) {
		this.audience = audience;
	}

	public Subject getSubject() {
		return subject;
	}

	public void setSubject(Subject subject) {
		this.subject = subject;
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Objects.hash(audience, date, group, session, subject, teacher);
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
		return Objects.equals(audience, other.audience) && Objects.equals(date, other.date)
				&& Objects.equals(group, other.group) && Objects.equals(session, other.session)
				&& Objects.equals(subject, other.subject) && Objects.equals(teacher, other.teacher);
	}

	@Override
	public String toString() {
		return "Lecture [date=" + date + "\n, session=" + session + "\n, audience=" + audience + "\n, subject=" + subject
				+ "\n, teacher=" + teacher + "\n, group=" + group + "\n, getId()=" + getId() + "]";
	}

	@Override
	public int compareTo(Lecture o) {
		return this.session.getPeriod().compareTo(o.getSession().getPeriod());
	}

}

package ua.com.foxminded.university.model;

import java.time.LocalDate;
import java.util.Map;
import java.util.Objects;

public class Lecture extends IdEntity implements Comparable<Lecture> {

	private LocalDate date;
	private LectureSessions session;
	private Audience audience;
	private Subject subject;
	private Teacher teacher;
	private Group group;

	private boolean modified; // if lecture was edited (but wasn't cancelled)
	private boolean archived; // if lecture was cancelled or rescheduled and position became free
	private boolean update; //
	private String status;
	private Map<String, Boolean> modifiedField;
	private Long newLectureId;
	private Long oldLectureId;

	public Lecture() {
	}

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

	public Lecture(Long id, LocalDate date, LectureSessions session, Audience audience, Subject subject,
			Teacher teacher, Group group) {
		super(id);
		this.date = date;
		this.session = session;
		this.audience = audience;
		this.subject = subject;
		this.teacher = teacher;
		this.group = group;
	}

	public Lecture(Long id, LocalDate date, LectureSessions session, Audience audience, Subject subject,
			Teacher teacher, Group group, boolean archived, String status, Long newLectureId) {
		super(id);
		this.date = date;
		this.session = session;
		this.audience = audience;
		this.subject = subject;
		this.teacher = teacher;
		this.group = group;
		this.archived = archived;
		this.status = status;
		this.newLectureId = newLectureId;
	}

	public Lecture(LocalDate date, LectureSessions session, Subject subject, Teacher teacher, Group group,
			boolean modified, String status, Long oldLectureId) {
		super(null);
		this.date = date;
		this.session = session;
		this.subject = subject;
		this.teacher = teacher;
		this.group = group;
		this.modified = modified;
		this.status = status;
		this.oldLectureId = oldLectureId;
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

	public boolean isModified() {
		return modified;
	}

	public void setModified(boolean modified) {
		this.modified = modified;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public boolean isArchived() {
		return archived;
	}

	public void setArchived(boolean archived) {
		this.archived = archived;
	}

	public Map<String, Boolean> getModifiedField() {
		return modifiedField;
	}

	public void setModifiedField(Map<String, Boolean> modifiedField) {
		this.modifiedField = modifiedField;
	}

	public Long getNewLectureId() {
		return newLectureId;
	}

	public void setNewLectureId(Long newLectureId) {
		this.newLectureId = newLectureId;
	}

	public Long getOldLectureId() {
		return oldLectureId;
	}

	public void setOldLectureId(Long oldLectureId) {
		this.oldLectureId = oldLectureId;
	}

	public boolean isUpdate() {
		return update;
	}

	public void setUpdate(boolean update) {
		this.update = update;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Objects.hash(archived, audience, date, group, modified, modifiedField, newLectureId,
				oldLectureId, session, status, subject, teacher, update);
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
		return archived == other.archived && Objects.equals(audience, other.audience)
				&& Objects.equals(date, other.date) && Objects.equals(group, other.group) && modified == other.modified
				&& Objects.equals(modifiedField, other.modifiedField)
				&& Objects.equals(newLectureId, other.newLectureId) && Objects.equals(oldLectureId, other.oldLectureId)
				&& Objects.equals(session, other.session) && Objects.equals(status, other.status)
				&& Objects.equals(subject, other.subject) && Objects.equals(teacher, other.teacher)
				&& update == other.update;
	}

	@Override
	public String toString() {
		return "\n Lecture [\n date=" + date + "\n, session=" + session + "\n, audience=" + audience + "\n, subject="
				+ subject + "\n, teacher=" + teacher + "\n, group=" + group + "\n, getId()=" + getId()
				+ "\n, isModified()=" + isModified() + "\n, isArchived()=" + isArchived() + "\n, getStatus()="
				+ getStatus() + "\n, oldLecureId=" + getOldLectureId() + "\n, newLecureId=" + getNewLectureId() + "] \n"
				+ "\n";
	}

	@Override
	public int compareTo(Lecture o) {
		return this.session.getPeriod().compareTo(o.getSession().getPeriod());
	}

}

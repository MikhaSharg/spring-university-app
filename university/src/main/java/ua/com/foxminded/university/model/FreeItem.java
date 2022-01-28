package ua.com.foxminded.university.model;

import java.time.LocalDate;
import java.util.Objects;

public class FreeItem {

	private LocalDate date;
	private Long sessionId;
	private Long lectureId; // set LectureId if item gets from archive.

	public FreeItem(LocalDate date, Long sessionId, Long lectureId) {
		super();
		this.date = date;
		this.sessionId = sessionId;
		this.lectureId = lectureId;
	}

	public FreeItem(LocalDate date, Long sessionId) {
		super();
		this.date = date;
		this.sessionId = sessionId;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public Long getSessionId() {
		return sessionId;
	}

	public void setSessionId(Long sessionId) {
		this.sessionId = sessionId;
	}

	public Long getLectureId() {
		return lectureId;
	}

	public void setLectureId(Long lectureId) {
		this.lectureId = lectureId;
	}

	@Override
	public int hashCode() {
		return Objects.hash(date, lectureId, sessionId);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		FreeItem other = (FreeItem) obj;
		return Objects.equals(date, other.date) && Objects.equals(lectureId, other.lectureId)
				&& Objects.equals(sessionId, other.sessionId);
	}

	@Override
	public String toString() {
		return "FreeItem [date=" + date + ", sessionId=" + sessionId + ", lectureId=" + lectureId + "]";
	}

}

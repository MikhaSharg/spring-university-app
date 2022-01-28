package ua.com.foxminded.university.model;

import java.time.LocalDate;

public class ArchivedLecture {

	private final Long lectureId;
	private final LocalDate date;
	private final Long sessionId;
	private final Long audienceId;
	private final Long subjectId;
	private final Long teacherId;
	private final Long groupId;
	private final String status;
	private final Long newLectureId;
	
	public ArchivedLecture(Long lectureId, LocalDate date, Long sessionId, Long audienceId, Long subjectId,
			Long teacherId, Long groupId, String status, Long newLectureId) {
		this.lectureId = lectureId;
		this.date = date;
		this.sessionId = sessionId;
		this.audienceId = audienceId;
		this.subjectId = subjectId;
		this.teacherId = teacherId;
		this.groupId = groupId;
		this.status = status;
		this.newLectureId = newLectureId;
	}

	public Long getLectureId() {
		return lectureId;
	}

	public LocalDate getDate() {
		return date;
	}

	public Long getSessionId() {
		return sessionId;
	}

	public Long getAudienceId() {
		return audienceId;
	}

	public Long getSubjectId() {
		return subjectId;
	}

	public Long getTeacherId() {
		return teacherId;
	}

	public Long getGroupId() {
		return groupId;
	}

	public String getStatus() {
		return status;
	}

	public Long getNewLectureId() {
		return newLectureId;
	}
	
	
	
}

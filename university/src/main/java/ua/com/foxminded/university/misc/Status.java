package ua.com.foxminded.university.misc;

import java.time.LocalDate;

import ua.com.foxminded.university.model.Lecture;

public class Status {

	public static final String CANCELED = "canceled";
	public static final String RESCHEDULED = "rescheduled";
	private static final String MS_RESCHEDULED_TO = "Lecture was rescheduled to ";
	private static final String MS_RESCHEDULED_FROM = "Lecture was rescheduled from ";
	private static final String MS_CANCELED = "Lecture was canceled ";

	public static void generateStatusAsMessage(Lecture lecture, LocalDate newLectureDate, String session) {
		switch (lecture.getStatus()) {
		case CANCELED:
			lecture.setStatus(MS_CANCELED);
			break;
		case RESCHEDULED:
			if (lecture.getNewLectureId() != null) {
				lecture.setStatus(MS_RESCHEDULED_TO + session + "/" + newLectureDate);
			}
		}
	}

	public static String generateStatusAsMessage(String session, LocalDate date, String status) {
		String generatedStatus = null;
		switch (status) {
		case RESCHEDULED:
			generatedStatus = MS_RESCHEDULED_FROM + session + "/" + date;
			break;
		}
		return generatedStatus;
	}
}

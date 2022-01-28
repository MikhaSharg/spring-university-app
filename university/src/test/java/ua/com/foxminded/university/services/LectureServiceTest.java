package ua.com.foxminded.university.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import ua.com.foxminded.university.dao.LectureDao;
import ua.com.foxminded.university.dao.LectureSessionsDao;
import ua.com.foxminded.university.misc.GeneratorConfig;
import ua.com.foxminded.university.model.Audience;
import ua.com.foxminded.university.model.FreeItem;
import ua.com.foxminded.university.model.Group;
import ua.com.foxminded.university.model.Lecture;
import ua.com.foxminded.university.model.LectureSessions;
import ua.com.foxminded.university.model.Subject;
import ua.com.foxminded.university.model.Teacher;

import ua.com.foxminded.university.misc.Status;

@SpringBootTest(classes = LectureService.class)
class LectureServiceTest {

	@MockBean
	LectureDao lectureDao;

	@MockBean
	LectureSessionsDao lectureSessionsDao;

	@MockBean
	GeneratorConfig generatorConfig;

	@Autowired
	LectureService lectureService;

	LectureSessions lectureSessions1 = new LectureSessions(1L, "1th", "8:00", "9:20");
	LectureSessions lectureSessions2 = new LectureSessions(2L, "2th", "9:30", "10:50");
	LectureSessions lectureSessions3 = new LectureSessions(3L, "3th", "11:00", "12:20");
	LectureSessions lectureSessions4 = new LectureSessions(4L, "4th", "13:00", "14:20");
	LectureSessions lectureSessions5 = new LectureSessions(5L, "5th", "14:30", "15:50");
	LectureSessions lectureSessions6 = new LectureSessions(6L, "6th", "16:00", "17:20");

	Audience audience1 = new Audience(1L, 100);
	Audience audience2 = new Audience(2L, 101);
	Audience audience3 = new Audience(3L, 102);
	Audience audience4 = new Audience(4L, 103);
	Audience audience5 = new Audience(5L, 104);

	Subject subject1 = new Subject(1L, "Theory of probability and mathematical statistics");
	Subject subject2 = new Subject(2L, "Theoretical mechanics");
	Subject subject3 = new Subject(3L, "Architecture");
	Subject subject4 = new Subject(4L, "Strength of materials");
	Subject subject5 = new Subject(5L, "SAPR");

	Teacher teacher1 = new Teacher(1L, "Alex", "Petrov", "male", "AlexPetrov@gmail.com", "Saint Petersburg", 68,
			89313262896L, "teacher", "Professor");
	Teacher teacher2 = new Teacher(2L, "Anna", "Ermakova", "female", "AnnaErmakova@gmail.com", "Kaliningrad", 48,
			89215895789L, "teacher", "Assistant Lecturer");
	Teacher teacher3 = new Teacher(3L, "Roman", "Sidorov", "male", "RomanSidorov@gmail.com", "Moscow", 53, 89112568975L,
			"teacher", "Doctor of Technical Science");
	Teacher teacher4 = new Teacher(4L, "Diana", "Gukova", "female", "DianaGukova@gmail.com", "Rostov", 52, 89225896325L,
			"teacher", "Senior Lecturer");
	Teacher teacher5 = new Teacher(5L, "Dmitry", "Solodin", "male", "MikhailSolodin@gmail.com", "Andora", 56,
			89052655985L, "teacher", "Candidate of Technical Science");

	Group group1 = new Group(1L, "AB-12");
	Group group2 = new Group(2L, "CD-34");
	Group group3 = new Group(3L, "EF-56");
	Group group4 = new Group(4L, "GH-78");
	Group group5 = new Group(5L, "IJ-90");

	@Test
	void shouldFindAllFreeItemsInSheduleForTeacherAndGroup() {
		Long teacherId = 1L;
		Long groupId = 1L;
		LocalDate date1 = LocalDate.now();
		LocalDate date2 = LocalDate.now().plusDays(1);
		LocalDate date3 = LocalDate.now().plusDays(2);
		LocalDate date4 = LocalDate.now().plusDays(3);

		List<Lecture> lectures = Arrays.asList(
				new Lecture(1L, date1, lectureSessions1, audience1, subject1, teacher1, group1),
				new Lecture(2L, date1, lectureSessions2, audience2, subject2, teacher2, group1),
				new Lecture(3L, date1, lectureSessions3, audience3, subject3, teacher3, group1),
				new Lecture(4L, date1, lectureSessions4, audience4, subject4, teacher4, group1),
				new Lecture(5L, date1, lectureSessions5, audience5, subject5, teacher5, group1),
				new Lecture(6L, date1, lectureSessions6, audience3, subject2, teacher4, group1),

				new Lecture(7L, date2, lectureSessions1, audience1, subject1, teacher1, group1),
				new Lecture(8L, date2, lectureSessions2, audience2, subject2, teacher2, group1),
				new Lecture(9L, date2, lectureSessions3, audience3, subject3, teacher3, group1),
				new Lecture(10L, date2, lectureSessions4, audience4, subject4, teacher4, group1),
				new Lecture(11L, date2, lectureSessions5, audience5, subject5, teacher5, group1),
				new Lecture(12L, date2, lectureSessions6, audience3, subject2, teacher4, group1),

				new Lecture(13L, date3, lectureSessions1, audience1, subject1, teacher4, group3),
				new Lecture(14L, date3, lectureSessions2, audience2, subject2, teacher2, group1),
				new Lecture(15L, date3, lectureSessions3, audience3, subject3, teacher3, group1),
				new Lecture(16L, date3, lectureSessions4, audience4, subject4, teacher4, group1),
				new Lecture(17L, date3, lectureSessions5, audience5, subject5, teacher5, group1),
				new Lecture(18L, date3, lectureSessions6, audience3, subject2, teacher4, group1),

				new Lecture(19L, date4, lectureSessions1, audience1, subject1, teacher1, group1),
				new Lecture(20L, date4, lectureSessions2, audience2, subject2, teacher2, group1),
				new Lecture(21L, date4, lectureSessions3, audience3, subject3, teacher3, group1),
				new Lecture(22L, date4, lectureSessions4, audience4, subject4, teacher4, group1),
				new Lecture(23L, date4, lectureSessions5, audience5, subject5, teacher5, group1),
				new Lecture(24L, date4, lectureSessions6, audience3, subject2, teacher4, group1));

		List<LectureSessions> lectureSessions = Arrays.asList(lectureSessions1, lectureSessions2, lectureSessions3,
				lectureSessions4, lectureSessions5, lectureSessions6);
		List<Lecture> archivedLectures = Arrays.asList(new Lecture(1L, date1, lectureSessions1, audience1, subject1,
				teacher1, group1, false, Status.CANCELED, null));

		String firstPartOfHolidays = String.valueOf(LocalDate.now().getYear()) + "-01-01/"
				+ LocalDate.now().minusDays(6).toString(); // Algorithm has to skip all days before today
		String secondPartOfHolidays = LocalDate.now().plusDays(3).toString() + "/"
				+ String.valueOf(LocalDate.now().getYear()) + "-12-31";
		String holidays = firstPartOfHolidays + ", " + secondPartOfHolidays;
		String startDate = LocalDate.now().getYear() + "-01-01";
		String endDate = LocalDate.now().getYear() + "-12-31";
		List<LocalDate> studyDays = new ArrayList<>();
		for (int i = 0; i < 4; i++) {
			studyDays.add(LocalDate.now().plusDays(i));
		}

		when(lectureDao.findLecturesByTeacherAndGroupId(teacherId, groupId)).thenReturn(lectures);
		when(lectureSessionsDao.findAll()).thenReturn(lectureSessions);
		when(lectureDao.findArchivedLectures(teacherId, groupId)).thenReturn(archivedLectures);
		when(generatorConfig.getHolidays()).thenReturn(holidays);
		when(generatorConfig.getStartDate()).thenReturn(startDate);
		when(generatorConfig.getEndDate()).thenReturn(endDate);

		List<FreeItem> expected = Arrays.asList(new FreeItem(date1, 1l, 1l));
		if (date3.getDayOfWeek() != DayOfWeek.SUNDAY) {
			expected.add(new FreeItem(date3, 1l));
		}
		List<FreeItem> actual = lectureService.findAllFreeItemsInSchedule(teacherId, groupId);
		assertThat(actual).isEqualTo(expected);
	}

}

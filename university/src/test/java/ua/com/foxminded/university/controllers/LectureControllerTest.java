package ua.com.foxminded.university.controllers;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.MultiValueMap;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import ua.com.foxminded.university.facade.ControllersFacade;
import ua.com.foxminded.university.facade.ControllersFacadeImpl;
import ua.com.foxminded.university.model.*;
import ua.com.foxminded.university.model.Group;
import ua.com.foxminded.university.model.Lecture;
import ua.com.foxminded.university.model.LectureSessions;
import ua.com.foxminded.university.model.Subject;
import ua.com.foxminded.university.model.Teacher;
import ua.com.foxminded.university.model.view.LecturesAudience;
import ua.com.foxminded.university.model.view.LecturesGroup;
import ua.com.foxminded.university.model.view.LecturesSubject;
import ua.com.foxminded.university.model.view.LecturesTeacher;
import ua.com.foxminded.university.model.view.LecturesView;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(LectureController.class)
class LectureControllerTest {

	@MockBean
	ControllersFacade controllersFacade;

	@Autowired
	MockMvc mockMvc;

	@Autowired
	LectureController lectureController;

	LocalDate currentDate = LocalDate.now();

	LectureSessions lectureSessions1 = new LectureSessions(1L, "1th", "8:00", "9:20");
	LectureSessions lectureSessions2 = new LectureSessions(2L, "2th", "9:30", "10:50");
	LectureSessions lectureSessions3 = new LectureSessions(3L, "3th", "11:00", "12:20");
	LectureSessions lectureSessions4 = new LectureSessions(4L, "4th", "13:00", "14:20");
	LectureSessions lectureSessions5 = new LectureSessions(5L, "5th", "14:30", "15:50");
	LectureSessions lectureSessions6 = new LectureSessions(6L, "6th", "16:00", "17:20");

	Audience audience1 = new Audience(1L, 101);
	Audience audience2 = new Audience(2L, 102);
	Audience audience3 = new Audience(3L, 103);
	Audience audience4 = new Audience(4L, 104);
	Audience audience5 = new Audience(5L, 105);
	Audience audience6 = new Audience(6L, 106);

	Subject subject1 = new Subject(1L, "Theory of probability and mathematical statistics");
	Subject subject2 = new Subject(2L, "Theoretical mechanics");
	Subject subject3 = new Subject(3L, "Architecture");
	Subject subject4 = new Subject(4L, "Strength of materials");
	Subject subject5 = new Subject(5L, "SAPR");
	Subject subject6 = new Subject(6L, "Chemistry");

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
	Teacher teacher6 = new Teacher(6L, "Andrey", "Vnukov", "male", "AndreyVnukov@gmail.com", "Novgorod", 61,
			89052655985L, "teacher", "Professor");

	Group group1 = new Group(1L, "AB-12");
	Group group2 = new Group(2L, "CD-34");
	Group group3 = new Group(3L, "EF-56");
	Group group4 = new Group(4L, "GH-78");
	Group group5 = new Group(5L, "IJ-90");
	Group group6 = new Group(6L, "RH-28");

	@Test
	void shouldShowListOfLecturesForCurrentDay() throws Exception {

		List<Lecture> lectures = Arrays.asList(
				new Lecture(1L, currentDate, lectureSessions1, audience1, subject1, teacher1, group1),
				new Lecture(2L, currentDate, lectureSessions2, audience2, subject2, teacher2, group2),
				new Lecture(3L, currentDate, lectureSessions3, audience3, subject3, teacher3, group3),
				new Lecture(4L, currentDate, lectureSessions4, audience4, subject4, teacher4, group4),
				new Lecture(5L, currentDate, lectureSessions5, audience5, subject5, teacher5, group5));

		when(controllersFacade.collectLecturesByDateRange(currentDate, currentDate))
				.thenReturn(Arrays.asList(new LecturesView(lectures)));
		mockMvc.perform(get("/lectures"))

				.andExpect(status().isOk())

				.andExpect(content().string(containsString(currentDate.toString())))

				.andExpect(content().string(containsString("1th")))
				.andExpect(content().string(containsString("Theory of probability and mathematical statistics")))
				.andExpect(content().string(containsString("Alex Petrov, Professor")))
				.andExpect(content().string(containsString("AB-12"))).andExpect(content().string(containsString("101")))

				.andExpect(content().string(containsString("2th")))
				.andExpect(content().string(containsString("Theoretical mechanics")))
				.andExpect(content().string(containsString("Anna Ermakova, Assistant Lecturer")))
				.andExpect(content().string(containsString("CD-34")))

				.andExpect(content().string(containsString("102"))).andExpect(content().string(containsString("3th")))
				.andExpect(content().string(containsString("Architecture")))
				.andExpect(content().string(containsString("Roman Sidorov, Doctor of Technical Science")))
				.andExpect(content().string(containsString("EF-56"))).andExpect(content().string(containsString("103")))

				.andExpect(content().string(containsString("4th")))
				.andExpect(content().string(containsString("Strength of materials")))
				.andExpect(content().string(containsString("Diana Gukova, Senior Lecturer")))
				.andExpect(content().string(containsString("GH-78"))).andExpect(content().string(containsString("104")))

				.andExpect(content().string(containsString("5th"))).andExpect(content().string(containsString("SAPR")))
				.andExpect(content().string(containsString("Dmitry Solodin, Candidate of Technical Science")))
				.andExpect(content().string(containsString("IJ-90")))
				.andExpect(content().string(containsString("105")));
	}

	@Test
	void shouldShowListOfLecturesForGroupForToday() throws Exception {

		List<Lecture> lectures1 = Arrays.asList(
				new Lecture(1L, currentDate, lectureSessions1, audience1, subject1, teacher1, group1),
				new Lecture(2L, currentDate, lectureSessions2, audience2, subject2, teacher2, group1),
				new Lecture(3L, currentDate, lectureSessions3, audience3, subject3, teacher3, group1),
				new Lecture(4L, currentDate, lectureSessions4, audience4, subject4, teacher4, group1),
				new Lecture(5L, currentDate, lectureSessions5, audience5, subject5, teacher5, group1),
				new Lecture(6L, currentDate, lectureSessions6, audience6, subject6, teacher6, group1));

		Map<LocalDate, List<Lecture>> lectures = Map.of(currentDate, lectures1);
		
		when(controllersFacade.collectLecturesForGroupByDateRange(currentDate, currentDate, 1L))
				.thenReturn(new LecturesGroup(group1.getId(), group1.getName(), lectures));
		mockMvc.perform(get("/lectures/groups/1"))

				.andExpect(status().isOk())

				.andExpect(content().string(containsString("Lectures/group AB-12/date " + currentDate.toString())))
				.andExpect(content().string(containsString(currentDate.toString())))
				.andExpect(content().string(containsString("AB-12")))

				.andExpect(content().string(containsString("1th")))
				.andExpect(content().string(containsString("Theory of probability and mathematical statistics")))
				.andExpect(content().string(containsString("Alex Petrov, Professor")))
				.andExpect(content().string(containsString("101")))

				.andExpect(content().string(containsString("2th")))
				.andExpect(content().string(containsString("Theoretical mechanics")))
				.andExpect(content().string(containsString("Anna Ermakova, Assistant Lecturer")))
				.andExpect(content().string(containsString("102")))

				.andExpect(content().string(containsString("3th")))
				.andExpect(content().string(containsString("Architecture")))
				.andExpect(content().string(containsString("Roman Sidorov, Doctor of Technical Science")))
				.andExpect(content().string(containsString("103")))

				.andExpect(content().string(containsString("4th")))
				.andExpect(content().string(containsString("Strength of materials")))
				.andExpect(content().string(containsString("Diana Gukova, Senior Lecturer")))
				.andExpect(content().string(containsString("104")))

				.andExpect(content().string(containsString("5th"))).andExpect(content().string(containsString("SAPR")))
				.andExpect(content().string(containsString("Dmitry Solodin, Candidate of Technical Science")))
				.andExpect(content().string(containsString("105")))

				.andExpect(content().string(containsString("6th")))
				.andExpect(content().string(containsString("Chemistry")))
				.andExpect(content().string(containsString("Andrey Vnukov, Professor")))
				.andExpect(content().string(containsString("106")));
	}

	@Test
	void shouldShowListOfLecturesForTeacherForToday() throws Exception {

		List<Lecture> lectures1 = Arrays.asList(
				new Lecture(1L, currentDate, lectureSessions1, audience1, subject1, teacher1, group1),
				new Lecture(2L, currentDate, lectureSessions2, audience2, subject2, teacher1, group2),
				new Lecture(3L, currentDate, lectureSessions3, audience3, subject3, teacher1, group3),
				new Lecture(4L, currentDate, lectureSessions4, audience4, subject1, teacher1, group4),
				new Lecture(5L, currentDate, lectureSessions5, audience5, subject2, teacher1, group5),
				new Lecture(6L, currentDate, lectureSessions6, audience6, subject3, teacher1, group6));

		Map<LocalDate, List<Lecture>> lectures = Map.of(currentDate, lectures1);

		String teacherName = String.format("%s %s", teacher1.getFirstName(), teacher1.getLastName());

		when(controllersFacade.collectLecturesForTeacherByDateRange(currentDate, currentDate, 1L))
				.thenReturn(new LecturesTeacher(teacher1.getId(), teacherName, teacher1.getProfile(), lectures));
		mockMvc.perform(get("/lectures/teacher/1"))

				.andExpect(status().isOk())

				.andExpect(content().string(
						containsString("Lectures/teacher Alex Petrov, Professor/date " + currentDate.toString())))
				.andExpect(content().string(containsString(currentDate.toString())))

				.andExpect(content().string(containsString("1th")))
				.andExpect(content().string(containsString("Theory of probability and mathematical statistics")))
				.andExpect(content().string(containsString("AB-12"))).andExpect(content().string(containsString("101")))

				.andExpect(content().string(containsString("2th")))
				.andExpect(content().string(containsString("Theoretical mechanics")))
				.andExpect(content().string(containsString("CD-34"))).andExpect(content().string(containsString("102")))

				.andExpect(content().string(containsString("3th")))
				.andExpect(content().string(containsString("Architecture")))
				.andExpect(content().string(containsString("EF-56"))).andExpect(content().string(containsString("103")))

				.andExpect(content().string(containsString("4th")))
				.andExpect(content().string(containsString("Theory of probability and mathematical statistics")))
				.andExpect(content().string(containsString("GH-78"))).andExpect(content().string(containsString("104")))

				.andExpect(content().string(containsString("5th")))
				.andExpect(content().string(containsString("Theoretical mechanics")))
				.andExpect(content().string(containsString("IJ-90"))).andExpect(content().string(containsString("105")))

				.andExpect(content().string(containsString("6th")))
				.andExpect(content().string(containsString("Architecture")))
				.andExpect(content().string(containsString("RH-28")))
				.andExpect(content().string(containsString("106")));
	}

	@Test
	void shouldShowListOfLecturesForSubjectForToday() throws Exception {

		List<Lecture> lectures1 = Arrays.asList(
				new Lecture(1L, currentDate, lectureSessions1, audience1, subject1, teacher1, group1),
				new Lecture(2L, currentDate, lectureSessions2, audience2, subject1, teacher2, group2),
				new Lecture(3L, currentDate, lectureSessions3, audience3, subject1, teacher3, group3),
				new Lecture(4L, currentDate, lectureSessions4, audience4, subject1, teacher1, group4),
				new Lecture(5L, currentDate, lectureSessions5, audience5, subject1, teacher2, group5),
				new Lecture(6L, currentDate, lectureSessions6, audience6, subject1, teacher3, group6));

		Map<LocalDate, List<Lecture>> lectures = Map.of(currentDate, lectures1);

		when(controllersFacade.collectLecturesForSubjectByDateRange(currentDate, currentDate, 1L))
				.thenReturn(new LecturesSubject(subject1.getId(), subject1.getName(), lectures));
		mockMvc.perform(get("/lectures/subject/1"))

				.andExpect(status().isOk())
				.andExpect(content().string(
						containsString("Lectures/subject Theory of probability and mathematical statistics/date "
								+ currentDate.toString())))
				.andExpect(
						content().string(containsString("Subject: Theory of probability and mathematical statistics")))
				.andExpect(content().string(containsString(currentDate.toString())))

				.andExpect(content().string(containsString("1th")))
				.andExpect(content().string(containsString("Alex Petrov, Professor")))
				.andExpect(content().string(containsString("AB-12"))).andExpect(content().string(containsString("101")))

				.andExpect(content().string(containsString("2th")))
				.andExpect(content().string(containsString("Anna Ermakova, Assistant Lecturer")))
				.andExpect(content().string(containsString("CD-34"))).andExpect(content().string(containsString("102")))

				.andExpect(content().string(containsString("3th")))
				.andExpect(content().string(containsString("Roman Sidorov, Doctor of Technical Science")))
				.andExpect(content().string(containsString("EF-56"))).andExpect(content().string(containsString("103")))

				.andExpect(content().string(containsString("4th")))
				.andExpect(content().string(containsString("Alex Petrov, Professor")))
				.andExpect(content().string(containsString("GH-78"))).andExpect(content().string(containsString("104")))

				.andExpect(content().string(containsString("5th")))
				.andExpect(content().string(containsString("Anna Ermakova, Assistant Lecturer")))
				.andExpect(content().string(containsString("IJ-90"))).andExpect(content().string(containsString("105")))

				.andExpect(content().string(containsString("6th")))
				.andExpect(content().string(containsString("Roman Sidorov, Doctor of Technical Science")))
				.andExpect(content().string(containsString("RH-28")))
				.andExpect(content().string(containsString("106")));
	}

	@Test
	void shouldShowListOfLecturesForAudienceForToday() throws Exception {

		List<Lecture> lectures1 = Arrays.asList(
				new Lecture(1L, currentDate, lectureSessions1, audience1, subject1, teacher1, group1),
				new Lecture(2L, currentDate, lectureSessions2, audience1, subject2, teacher2, group2),
				new Lecture(3L, currentDate, lectureSessions3, audience1, subject3, teacher3, group3),
				new Lecture(4L, currentDate, lectureSessions4, audience1, subject4, teacher4, group4),
				new Lecture(5L, currentDate, lectureSessions5, audience1, subject5, teacher5, group5),
				new Lecture(6L, currentDate, lectureSessions6, audience1, subject6, teacher6, group6));

		Map<LocalDate, List<Lecture>> lectures = Map.of(currentDate, lectures1);

		when(controllersFacade.collectLecturesForAudienceByDateRange(currentDate, currentDate, 1L))
				.thenReturn(new LecturesAudience(audience1.getId(), audience1.getRoomNumber(), lectures));
		mockMvc.perform(get("/lectures/audience/1"))

				.andExpect(status().isOk())
				.andExpect(content().string(containsString("Lectures/audience 101/date " + currentDate.toString())))
				.andExpect(content().string(containsString("Audience: 101")))
				.andExpect(content().string(containsString(currentDate.toString())))

				.andExpect(content().string(containsString("1th")))
				.andExpect(content().string(containsString("Theory of probability and mathematical statistics")))
				.andExpect(content().string(containsString("AB-12")))
				.andExpect(content().string(containsString("Alex Petrov, Professor")))

				.andExpect(content().string(containsString("2th")))
				.andExpect(content().string(containsString("Theoretical mechanics")))
				.andExpect(content().string(containsString("CD-34")))
				.andExpect(content().string(containsString("Anna Ermakova, Assistant Lecturer")))

				.andExpect(content().string(containsString("3th")))
				.andExpect(content().string(containsString("Architecture")))
				.andExpect(content().string(containsString("EF-56")))
				.andExpect(content().string(containsString("Roman Sidorov, Doctor of Technical Science")))

				.andExpect(content().string(containsString("4th")))
				.andExpect(content().string(containsString("Strength of materials")))
				.andExpect(content().string(containsString("GH-78")))
				.andExpect(content().string(containsString("Alex Petrov, Professor")))

				.andExpect(content().string(containsString("5th"))).andExpect(content().string(containsString("SAPR")))
				.andExpect(content().string(containsString("IJ-90")))
				.andExpect(content().string(containsString("Anna Ermakova, Assistant Lecturer")))

				.andExpect(content().string(containsString("6th")))
				.andExpect(content().string(containsString("Chemistry")))
				.andExpect(content().string(containsString("RH-28")))
				.andExpect(content().string(containsString("Roman Sidorov, Doctor of Technical Science")));
	}

	@Test
	void shouldShowViewOfLecture() throws Exception {

		LocalDate date = LocalDate.of(2021, 10, 10);

		when(controllersFacade.findLectureById(1L))
				.thenReturn(new Lecture(1L, date, lectureSessions1, audience1, subject1, teacher1, group1));
		mockMvc.perform(get("/lectures/1"))

				.andExpect(status().isOk())
				.andExpect(content().string(containsString(
						"Lectures/lecture/1th/2021-10-10/Theory of probability and mathematical statistics/AB-12")))
				.andExpect(content().string(containsString("Lecture: 1th/2021-10-10")))

				.andExpect(content().string(containsString("10/10/21")))
				.andExpect(content().string(containsString("1th: 8:00 - 9:20")))
				.andExpect(content().string(containsString("101")))
				.andExpect(content().string(containsString("Theory of probability and mathematical statistics")))
				.andExpect(content().string(containsString("Alex Petrov, Professor")))
				.andExpect(content().string(containsString("AB-12")));
	}
	
}

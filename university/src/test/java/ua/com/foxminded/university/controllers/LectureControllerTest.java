package ua.com.foxminded.university.controllers;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import ua.com.foxminded.university.facade.ControllersFacade;
import ua.com.foxminded.university.model.*;
import ua.com.foxminded.university.model.Group;
import ua.com.foxminded.university.model.Lecture;
import ua.com.foxminded.university.model.LectureSessions;
import ua.com.foxminded.university.model.Subject;
import ua.com.foxminded.university.model.Teacher;
import ua.com.foxminded.university.model.view.LecturesView;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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
	
	@Test
	void shouldShowListOfLectures() throws Exception {
		LocalDate start = LocalDate.of(2021, 10, 10);
		LocalDate end = LocalDate.of(2021, 10, 11);
		
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
		Teacher teacher3 = new Teacher(3L, "Roman", "Sidorov", "male", "RomanSidorov@gmail.com", "Moscow", 53,
				89112568975L, "teacher", "Doctor of Technical Science");
		Teacher teacher4 = new Teacher(4L, "Diana", "Gukova", "female", "DianaGukova@gmail.com", "Rostov", 52,
				89225896325L, "teacher", "Senior Lecturer");
		Teacher teacher5 = new Teacher(5L, "Dmitry", "Solodin", "male", "MikhailSolodin@gmail.com", "Andora", 56,
				89052655985L, "teacher", "Candidate of Technical Science");

		Group group1 = new Group(1L, "AB-12");
		Group group2 = new Group(2L, "CD-34");
		Group group3 = new Group(3L, "EF-56");
		Group group4 = new Group(4L, "GH-78");
		Group group5 = new Group(5L, "IJ-90");

		LocalDate date = LocalDate.of(2021, 11, 11);
		LocalDate date2 = LocalDate.of(2021, 11, 12);
		LocalDate date3 = LocalDate.of(2021, 11, 13);

		List<Lecture> lectures1 = Arrays.asList(
				new Lecture(1L, date, lectureSessions1, audience1, subject1, teacher1, group1),
				new Lecture(2L, date, lectureSessions2, audience2, subject2, teacher2, group2));
		
		List<Lecture> lectures2 = Arrays.asList(
				new Lecture(3L, date2, lectureSessions3, audience3, subject3, teacher3, group3),
				new Lecture(4L, date2, lectureSessions4, audience4, subject4, teacher4, group4));
		
		List<Lecture> lectures3 = Arrays.asList(
				new Lecture(5L, date3, lectureSessions5, audience5, subject5, teacher5, group5),
				new Lecture(6L, date3, lectureSessions6, audience3, subject2, teacher4, group3));
		
		when(controllersFacade.collectLecturesByDateRange(start, end))
		.thenReturn(Arrays.asList(new LecturesView(lectures1), new LecturesView(lectures2), new LecturesView(lectures3)));
		
		mockMvc.perform(get("/lectures"))
		.andExpect(status().isOk())
		.andExpect(content().string(containsString("1th")))
		.andExpect(content().string(containsString("Theory of probability and mathematical statistics")))
		.andExpect(content().string(containsString("Alex Petrov, Professor")))
		.andExpect(content().string(containsString("AB-12")))
		.andExpect(content().string(containsString("100")));
	}

}

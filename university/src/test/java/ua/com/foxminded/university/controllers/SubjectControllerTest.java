package ua.com.foxminded.university.controllers;

import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Arrays;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import ua.com.foxminded.university.facade.ControllersFacade;
import ua.com.foxminded.university.model.Subject;
import ua.com.foxminded.university.model.Teacher;
import ua.com.foxminded.university.model.view.SubjectView;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.hamcrest.Matchers.containsString;

@WebMvcTest(SubjectController.class)
class SubjectControllerTest {

	@MockBean
	ControllersFacade controllersFacade;

	@Autowired
	MockMvc mockMvc;

	@Autowired
	SubjectController subjectController;

	

	@Test
	void shoulsShowListOfSubjects() throws Exception {
		
		Subject subject1 = new Subject(1L, "Theory of probability and mathematical statistics");
		Subject subject2 = new Subject(2L, "Theoretical mechanics");
		Subject subject3 = new Subject(3L, "Architecture");
		Subject subject4 = new Subject(4L, "Strength of materials");
		Subject subject5 = new Subject(5L, "SAPR");
		Subject subject6 = new Subject(6L, "Chemistry");

		List<Subject> subjects = Arrays.asList(subject1, subject2, subject3, subject4, subject5, subject6);

		when(controllersFacade.collectAllSubjectsForView()).thenReturn(subjects);
		mockMvc.perform(get("/subjects"))

				.andExpect(status().isOk())
				.andExpect(content().string(containsString("Subjects")))
				
				.andExpect(content().string(containsString("Theory of probability and mathematical statistics")))
				.andExpect(content().string(containsString("Theoretical mechanics")))
				.andExpect(content().string(containsString("Architecture")))
				.andExpect(content().string(containsString("Strength of materials")))
				.andExpect(content().string(containsString("SAPR")))
				.andExpect(content().string(containsString("Chemistry")));
	}

	@Test
	void shoulsShowSubjectView() throws Exception {

		Subject subject = new Subject(1L, "Theory of probability and mathematical statistics");
		
		Teacher teacher1 = new Teacher(1L, "Alex", "Petrov", "male", "AlexPetrov@gmail.com", "Saint Petersburg", 68,
				89313262896L, "teacher", "Professor");
		Teacher teacher2 = new Teacher(2L, "Anna", "Ermakova", "female", "AnnaErmakova@gmail.com", "Kaliningrad", 48,
				89215895789L, "teacher", "Assistant Lecturer");

		List<Teacher> teachers = Arrays.asList(teacher1, teacher2);
		
		when(controllersFacade.collectSubjectForView(1L)).thenReturn(new SubjectView(1L, subject.getName(), teachers));
		mockMvc.perform(get("/subjects/1"))
		
		.andExpect(status().isOk())
		.andExpect(content().string(containsString("Subjects/Theory of probability and mathematical statistics")))
		
		.andExpect(content().string(containsString("Theory of probability and mathematical statistics")))
		
		.andExpect(content().string(containsString("Alex")))
		.andExpect(content().string(containsString("Petrov")))
		.andExpect(content().string(containsString("Professor")))
		
		.andExpect(content().string(containsString("Anna")))
		.andExpect(content().string(containsString("Ermakova")))
		.andExpect(content().string(containsString("Assistant Lecturer")));
	}
}

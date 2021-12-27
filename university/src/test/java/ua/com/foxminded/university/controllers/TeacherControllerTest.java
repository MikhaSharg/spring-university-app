package ua.com.foxminded.university.controllers;

import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import ua.com.foxminded.university.facade.ControllersFacade;
import ua.com.foxminded.university.model.Subject;
import ua.com.foxminded.university.model.Teacher;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest (TeacherController.class)
class TeacherControllerTest {
	
	@MockBean
	ControllersFacade controllersFacade;
	
	@Autowired
	MockMvc mockMvc;

	@Autowired
	TeacherController teacherController;
	
	
	@Test
	void shouldShowListOfTeachers() throws Exception {
		
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
		
		List<Teacher> teachers = Arrays.asList(teacher1, teacher2, teacher3, teacher4, teacher5, teacher6);
		
		Subject subject1 = new Subject(1L, "Theory of probability and mathematical statistics");
		Subject subject2 = new Subject(2L, "Theoretical mechanics");
		Subject subject3 = new Subject(3L, "Architecture");
		Subject subject4 = new Subject(4L, "Strength of materials");
		Subject subject5 = new Subject(5L, "SAPR");
		Subject subject6 = new Subject(6L, "Chemistry");

		List<Subject> subjects = Arrays.asList(subject1, subject2, subject3, subject4, subject5, subject6);
		
		for (int i=0; i<teachers.size(); i++) {
			teachers.get(i).setSubjects(Arrays.asList(subjects.get(i)));
		}
		
		when(controllersFacade.collectAllTeachersForList()).thenReturn(teachers);
		mockMvc.perform(get("/teachers"))
		
		.andExpect(status().isOk())
		.andExpect(content().string(containsString("Teachers")))
		
		.andExpect(content().string(containsString("Alex")))
		.andExpect(content().string(containsString("Petrov")))
		.andExpect(content().string(containsString("Professor")))
		.andExpect(content().string(containsString("1")))
		
		.andExpect(content().string(containsString("Anna")))
		.andExpect(content().string(containsString("Ermakova")))
		.andExpect(content().string(containsString("Assistant Lecturer")))
		.andExpect(content().string(containsString("1")))
		
		.andExpect(content().string(containsString("Roman")))
		.andExpect(content().string(containsString("Sidorov")))
		.andExpect(content().string(containsString("Doctor of Technical Science")))
		.andExpect(content().string(containsString("1")))
		
		.andExpect(content().string(containsString("Diana")))
		.andExpect(content().string(containsString("Gukova")))
		.andExpect(content().string(containsString("Senior Lecturer")))
		.andExpect(content().string(containsString("1")))
		
		.andExpect(content().string(containsString("Dmitry")))
		.andExpect(content().string(containsString("Solodin")))
		.andExpect(content().string(containsString("Candidate of Technical Science")))
		.andExpect(content().string(containsString("1")))
		
		.andExpect(content().string(containsString("Andrey")))
		.andExpect(content().string(containsString("Vnukov")))
		.andExpect(content().string(containsString("Professor")))
		.andExpect(content().string(containsString("1")));
	}

	@Test
	void shouldShowTeacherView() throws Exception {
		

		Teacher teacher = new Teacher(1L, "Alex", "Petrov", "male", "AlexPetrov@gmail.com", "Saint Petersburg", 68,
				89313262896L, "teacher", "Professor");
		
		Subject subject1 = new Subject(1L, "Theory of probability and mathematical statistics");
		Subject subject2 = new Subject(2L, "Theoretical mechanics");

		List<Subject> subjects = Arrays.asList(subject1, subject2);
		
		teacher.setSubjects(subjects);
		
		when(controllersFacade.collectTeacherForView(1L)).thenReturn(teacher);
		mockMvc.perform(get("/teachers/1"))
		
		.andExpect(status().isOk())
		.andExpect(content().string(containsString("Teacher: Alex Petrov, Professor")))
		
		.andExpect(content().string(containsString(teacher.getFirstName())))
		.andExpect(content().string(containsString(teacher.getLastName())))
		.andExpect(content().string(containsString(teacher.getAddress())))
		.andExpect(content().string(containsString(teacher.getEmail())))
		.andExpect(content().string(containsString(teacher.getGender())))
		.andExpect(content().string(containsString(teacher.getAge().toString())))
		.andExpect(content().string(containsString(teacher.getPhoneNumber().toString())))

		.andExpect(content().string(containsString(subject1.getName())))
		.andExpect(content().string(containsString(subject2.getName())));
	}
}

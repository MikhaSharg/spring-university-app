package ua.com.foxminded.university.controllers;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
import ua.com.foxminded.university.model.view.SubjectView;
import ua.com.foxminded.university.wrappers.SubjectWrapper;

@WebMvcTest(SubjectController.class)
class SubjectControllerTest {

	@MockBean
	ControllersFacade controllersFacade;

	@Autowired
	MockMvc mockMvc;

	@Autowired
	SubjectController subjectController;

	Subject subject1 = new Subject(1L, "Theory of probability and mathematical statistics");
	Subject subject2 = new Subject(2L, "Theoretical mechanics");
	Subject subject3 = new Subject(3L, "Architecture");
	Subject subject4 = new Subject(4L, "Strength of materials");
	Subject subject5 = new Subject(5L, "SAPR");
	Subject subject6 = new Subject(6L, "Chemistry");

	List<Subject> subjects = Arrays.asList(subject1, subject2, subject3, subject4, subject5, subject6);

	Teacher teacher1 = new Teacher(1L, "Alex", "Petrov", "male", "AlexPetrov@gmail.com", "Saint Petersburg", 68,
			89313262896L, "teacher", "Professor");
	Teacher teacher2 = new Teacher(2L, "Anna", "Ermakova", "female", "AnnaErmakova@gmail.com", "Kaliningrad", 48,
			89215895789L, "teacher", "Assistant Lecturer");

	List<Teacher> teachers = Arrays.asList(teacher1, teacher2);

	@Test
	void shoulsShowListOfSubjects() throws Exception {

		when(controllersFacade.collectAllSubjectsForView()).thenReturn(subjects);
		mockMvc.perform(get("/subjects"))

				.andExpect(status().isOk()).andExpect(content().string(containsString("Subjects")))

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

		when(controllersFacade.collectSubjectForView(1L)).thenReturn(new SubjectView(1L, subject.getName(), teachers));
		mockMvc.perform(get("/subjects/1"))

				.andExpect(status().isOk())
				.andExpect(
						content().string(containsString("Subjects/Theory of probability and mathematical statistics")))

				.andExpect(content().string(containsString("Theory of probability and mathematical statistics")))

				.andExpect(content().string(containsString("Alex")))
				.andExpect(content().string(containsString("Petrov")))
				.andExpect(content().string(containsString("Professor")))

				.andExpect(content().string(containsString("Anna")))
				.andExpect(content().string(containsString("Ermakova")))
				.andExpect(content().string(containsString("Assistant Lecturer")));
	}

	@Test
	void shoulShowNewSubjectForm() throws Exception {

		SubjectWrapper subject = new SubjectWrapper(teacher1);
		when(controllersFacade.collectTeacherForView(1L)).thenReturn(teacher1);

		mockMvc.perform(get("/subjects/1/new"))

				.andExpect(status().isOk()).andExpect(content().string(containsString("Add new subject to teacher")))
				.andExpect(content().string(containsString("Alex")))
				.andExpect(content().string(containsString("Petrov")))
				.andExpect(content().string(containsString("Professor")));
	}

	@Test
	void shoulShowSubjectEditFormTeacherMode() throws Exception {

		SubjectWrapper subject = new SubjectWrapper(subject1, teacher1, teachers);

		when(controllersFacade.findSubjectById(1L)).thenReturn(subject1);
		when(controllersFacade.collectTeacherForView(1L)).thenReturn(teacher1);
		when(controllersFacade.collectTeachersForSubject(1L)).thenReturn(teachers);
		mockMvc.perform(get("/subjects/edit/subject1/for/teacher1"))

				.andExpect(status().isOk()).andExpect(content().string(containsString("Edit Subject to Teacher")))
				.andExpect(content().string(containsString(subject1.getName())))
				.andExpect(content().string(containsString(teachers.get(0).getFirstName())))
				.andExpect(content().string(containsString(teachers.get(0).getLastName())))
				.andExpect(content().string(containsString(teachers.get(0).getProfile())))
				.andExpect(content().string(containsString(teachers.get(1).getFirstName())))
				.andExpect(content().string(containsString(teachers.get(1).getLastName())))
				.andExpect(content().string(containsString(teachers.get(1).getProfile())));
	}

	@Test
	void shoulShowSubjectEditFormBaseMode() throws Exception {
		SubjectWrapper subject = new SubjectWrapper(subject1, teachers);
		when(controllersFacade.findSubjectById(1L)).thenReturn(subject1);
		when(controllersFacade.collectTeachersForSubject(1L)).thenReturn(teachers);

		mockMvc.perform(get("/subjects/1/edit"))

				.andExpect(status().isOk()).andExpect(content().string(containsString("Edit Subject")))

				.andExpect(content().string(containsString(subject1.getName())))
				.andExpect(content().string(containsString(teachers.get(0).getFirstName())))
				.andExpect(content().string(containsString(teachers.get(0).getLastName())))
				.andExpect(content().string(containsString(teachers.get(0).getProfile())))
				.andExpect(content().string(containsString(teachers.get(1).getFirstName())))
				.andExpect(content().string(containsString(teachers.get(1).getLastName())))
				.andExpect(content().string(containsString(teachers.get(1).getProfile())));

	}

}

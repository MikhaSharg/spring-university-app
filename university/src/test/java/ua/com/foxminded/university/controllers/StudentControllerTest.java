package ua.com.foxminded.university.controllers;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import ua.com.foxminded.university.facade.ControllersFacade;
import ua.com.foxminded.university.model.Group;
import ua.com.foxminded.university.model.Student;
import ua.com.foxminded.university.model.view.StudentView;
import ua.com.foxminded.university.model.view.StudentsView;
import ua.com.foxminded.university.wrappers.StudentWrapper;

@WebMvcTest(StudentController.class)
class StudentControllerTest {

	@MockBean
	ControllersFacade controllersFacade;

	@Autowired
	MockMvc mockMvc;

	@Autowired
	StudentController studentController;

	List<Student> students = Arrays.asList(
			new Student(1L, "Alex", "Petrov", "male", "AlexPetrov@gmail.com", "Saint Petersburg", 25, 89523268951L,
					"student", 1L),
			new Student(2L, "Anna", "Ermakova", "female", "AnnaErmakova@gmail.com", "Kaliningrad", 26, 8952328575L,
					"student", 2L),
			new Student(3L, "Roman", "Sidorov", "male", "RomanSidorov@gmail.com", "Moscow", 23, 89583658547L, "student",
					3L),
			new Student(4L, "Diana", "Gukova", "female", "DianaGukova@gmail.com", "Rostov", 21, 89538792563L, "student",
					4L),
			new Student(5L, "Dmitry", "Solodin", "male", "MikhailSolodin@gmail.com", "Andora", 35, 89528769523L,
					"student", 5L));

	Map<Long, Group> groups = Map.of(1L, new Group(1L, "AB-12"), 2L, new Group(2L, "CD-34"), 3L, new Group(3L, "EF-56"),
			4L, new Group(4L, "GH-78"), 5L, new Group(5L, "IJ-90"));
	List<Group> avaliableGroups = Arrays.asList(new Group(1L, "AB-12"), new Group(2L, "CD-34"), new Group(3L, "EF-56"),
			new Group(4L, "GH-78"), new Group(5L, "IJ-90"));

	@Test
	void shouldShowListOfStudents() throws Exception {
		when(controllersFacade.collectAllStudentsForView()).thenReturn(new StudentsView(students, groups));
		mockMvc.perform(get("/students"))

				.andExpect(status().isOk()).andExpect(content().string(containsString("Students")))

				.andExpect(content().string(containsString("Alex")))
				.andExpect(content().string(containsString("Petrov")))
				.andExpect(content().string(containsString("AB-12")))

				.andExpect(content().string(containsString("Anna")))
				.andExpect(content().string(containsString("Ermakova")))
				.andExpect(content().string(containsString("CD-34")))

				.andExpect(content().string(containsString("Roman")))
				.andExpect(content().string(containsString("Sidorov")))
				.andExpect(content().string(containsString("EF-56")))

				.andExpect(content().string(containsString("Diana")))
				.andExpect(content().string(containsString("Gukova")))
				.andExpect(content().string(containsString("GH-78")))

				.andExpect(content().string(containsString("Dmitry")))
				.andExpect(content().string(containsString("Solodin")))
				.andExpect(content().string(containsString("IJ-90")));
	}

	@Test
	void shouldShowViewStudent() throws Exception {
		Student student = students.get(0);
		Group group = groups.get(1L);

		when(controllersFacade.collectStudentForView(1L)).thenReturn(new StudentView(student, group));
		mockMvc.perform(get("/students/1"))

				.andExpect(status().isOk()).andExpect(content().string(containsString("Student/Alex Petrov")))

				.andExpect(content().string(containsString(student.getFirstName())))
				.andExpect(content().string(containsString(student.getLastName())))
				.andExpect(content().string(containsString(student.getAddress())))
				.andExpect(content().string(containsString(student.getEmail())))
				.andExpect(content().string(containsString(student.getGender())))
				.andExpect(content().string(containsString(student.getAge().toString())))
				.andExpect(content().string(containsString(student.getPhoneNumber().toString())))
				.andExpect(content().string(containsString(group.getName())));
	}

	@Test
	void shouldShowNewStudentRegistrationForm() throws Exception {
		Group group = groups.get(1L);

		when(controllersFacade.collectAllNotFullGroups()).thenReturn(Arrays.asList(group));
		mockMvc.perform(get("/students/registerNewStudent")).andExpect(status().isOk())
				.andExpect(content().string(containsString("Students/new student registration")));

	}

	@Test
	void shouldShowStudentEditForm() throws Exception {
		Student student = students.get(0);
		Group group = groups.get(1L);

		when(controllersFacade.collectStudentForView(1L)).thenReturn(new StudentView(student, group));
		when(controllersFacade.collectAllNotFullGroups()).thenReturn(avaliableGroups);
		when(controllersFacade.findGroupById(group.getId())).thenReturn(group);
		mockMvc.perform(get("/students/1/edit"))

				.andExpect(status().isOk()).andExpect(content().string(containsString("Edit student/Alex Petrov")))

				.andExpect(content().string(containsString(student.getFirstName())))
				.andExpect(content().string(containsString(student.getLastName())))
				.andExpect(content().string(containsString(student.getAddress())))
				.andExpect(content().string(containsString(student.getEmail())))
				.andExpect(content().string(containsString(student.getGender())))
				.andExpect(content().string(containsString(student.getAge().toString())))
				.andExpect(content().string(containsString(student.getPhoneNumber().toString())))
				.andExpect(content().string(containsString(group.getName())))
				// options for Group
				.andExpect(content().string(containsString(avaliableGroups.get(0).getName())))
				.andExpect(content().string(containsString(avaliableGroups.get(1).getName())))
				.andExpect(content().string(containsString(avaliableGroups.get(2).getName())))
				.andExpect(content().string(containsString(avaliableGroups.get(3).getName())))
				.andExpect(content().string(containsString(avaliableGroups.get(4).getName())));
	}

	@Test
	void shouldshowMoveStudentToAnotherGroupForm() throws Exception {
		Student student = students.get(0);
		Group group = groups.get(1L);
		StudentWrapper studentWrapper = new StudentWrapper(student, avaliableGroups);

		when(controllersFacade.prepareDataForMoveStudentForm(student.getId())).thenReturn(studentWrapper);
		mockMvc.perform(get("/students/1/moveToAnotherGroup"))

				.andExpect(status().isOk())
				.andExpect(content().string(containsString("Move student to another Group/Alex Petrov")))

				.andExpect(content().string(containsString(student.getFirstName())))
				.andExpect(content().string(containsString(student.getLastName())))
				.andExpect(content().string(containsString(student.getAddress())))
				.andExpect(content().string(containsString(student.getEmail())))
				.andExpect(content().string(containsString(student.getGender())))
				.andExpect(content().string(containsString(student.getAge().toString())))
				.andExpect(content().string(containsString(student.getPhoneNumber().toString())))
				.andExpect(content().string(containsString(group.getName())))
				// options for Group
				.andExpect(content().string(containsString(avaliableGroups.get(0).getName())))
				.andExpect(content().string(containsString(avaliableGroups.get(1).getName())))
				.andExpect(content().string(containsString(avaliableGroups.get(2).getName())))
				.andExpect(content().string(containsString(avaliableGroups.get(3).getName())))
				.andExpect(content().string(containsString(avaliableGroups.get(4).getName())));
	}

}

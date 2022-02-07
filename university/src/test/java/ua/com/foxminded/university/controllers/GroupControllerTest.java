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
import ua.com.foxminded.university.model.Group;
import ua.com.foxminded.university.model.Student;

@WebMvcTest
class GroupControllerTest {

	@MockBean
	ControllersFacade controllersFacade;

	@Autowired
	MockMvc mockMvc;

	@Autowired
	GroupController groupController;

	Student students1 = new Student(1L, "Alex", "Petrov", "male", "AlexPetrov@gmail.com", "Saint Petersburg", 25,
			89523268951L, "student", 1L);
	Student students2 = new Student(2L, "Anna", "Ermakova", "female", "AnnaErmakova@gmail.com", "Kaliningrad", 26,
			8952328575L, "student", 2L);
	Student students3 = new Student(3L, "Roman", "Sidorov", "male", "RomanSidorov@gmail.com", "Moscow", 23,
			89583658547L, "student", 3L);
	Student students4 = new Student(4L, "Diana", "Gukova", "female", "DianaGukova@gmail.com", "Rostov", 21,
			89538792563L, "student", 4L);
	Student students5 = new Student(5L, "Dmitry", "Solodin", "male", "MikhailSolodin@gmail.com", "Andora", 35,
			89528769523L, "student", 5L);

	@Test
	void shouldReturnListOfGroups() throws Exception {

		Group group1 = new Group(1L, "AB-12");
		Group group2 = new Group(2L, "CD-34");
		Group group3 = new Group(3L, "EF-56");

		group1.setStudents(Arrays.asList(students1, students2));
		group2.setStudents(Arrays.asList(students3, students4));
		group3.setStudents(Arrays.asList(students5));

		List<Group> groups = Arrays.asList(group1, group2, group3);

		when(controllersFacade.collectAllGroupsForList()).thenReturn(groups);
		mockMvc.perform(get("/groups"))

				.andExpect(status().isOk()).andExpect(content().string(containsString("Groups")))

				.andExpect(content().string(containsString("AB-12"))).andExpect(content().string(containsString("2")))

				.andExpect(content().string(containsString("CD-34"))).andExpect(content().string(containsString("2")))

				.andExpect(content().string(containsString("EF-56"))).andExpect(content().string(containsString("1")));
	}

	@Test
	void shouldReturnGroupView() throws Exception {

		Group group = new Group(1L, "AB-12");
		List<Student> students = Arrays.asList(students1, students2, students3, students4, students5);
		group.setStudents(students);

		when(controllersFacade.findGroupById(1L)).thenReturn(group);
		mockMvc.perform(get("/groups/1"))

				.andExpect(status().isOk()).andExpect(content().string(containsString("Group/AB-12")))

				.andExpect(content().string(containsString("AB-12")))

				.andExpect(content().string(containsString("Alex")))
				.andExpect(content().string(containsString("Petrov")))

				.andExpect(content().string(containsString("Roman")))
				.andExpect(content().string(containsString("Sidorov")))

				.andExpect(content().string(containsString("Diana")))
				.andExpect(content().string(containsString("Gukova")))

				.andExpect(content().string(containsString("Dmitry")))
				.andExpect(content().string(containsString("Solodin")));
	}

	@Test
	void shouldShowGroupEditForm() throws Exception {
		Group group = new Group(1L, "AB-12");

		when(controllersFacade.findGroupById(1L)).thenReturn(group);
		mockMvc.perform(get("/groups/1/edit"))

				.andExpect(status().isOk()).andExpect(content().string(containsString("Edit Group/" + group.getName())))

				.andExpect(content().string(containsString("AB-12")));

	}

}

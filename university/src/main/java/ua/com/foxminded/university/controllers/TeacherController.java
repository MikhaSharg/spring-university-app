package ua.com.foxminded.university.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import ua.com.foxminded.university.facade.ControllersFacade;
import ua.com.foxminded.university.model.Teacher;
import ua.com.foxminded.university.wrappers.TeacherWrapper;

import static ua.com.foxminded.university.controllers.ControllerUtils.*;

@Controller
@RequestMapping("/teachers")
public class TeacherController {

	private final ControllersFacade facade;

	public TeacherController(ControllersFacade facade) {
		this.facade = facade;
	}

	private Teacher teacherBuffer = new Teacher();

	@GetMapping
	String showTeachersList(Model model) {
		model.addAttribute("teachers", facade.collectAllTeachersForList());
		setTitle(model, "Teachers");
		return "teachers/list";
	}

	@GetMapping(path = "/{id}")
	String showTeachersView(@PathVariable(name = "id", required = true) Long id, Model model) {
		Teacher teacher = facade.collectTeacherForView(id);
		model.addAttribute("teacher", teacher);
		setTitle(model, String.format("Teacher: %s %s, %s", teacher.getFirstName(), teacher.getLastName(),
				teacher.getProfile()));
		return "teachers/view";
	}

	@GetMapping(path = "/{id}/edit")
	String showTeacherEditForm(@PathVariable(name = "id") Long id, Model model) {
		TeacherWrapper teacher = new TeacherWrapper(facade.collectTeacherForView(id));
		teacherBuffer = teacher.getOldTeacher();
		model.addAttribute("teacher", teacher);
		setTitle(model, String.format("Edit teacher: %s %s, %s", teacher.getFirstName(), teacher.getLastName(),
				teacher.getProfile()));
		return "teachers/edit";
	}

	@PostMapping(path = "/{id}/edit")
	String updateTeacher(@PathVariable(name = "id") Long id, TeacherWrapper teacher, Model model) {
		TeacherWrapper updatingTeacher = teacher;
		teacher.setOldTeacher(teacherBuffer);
		Teacher updatedTeacher = facade.updateTeacher(teacher.getNewTeacher());
		updatedTeacher.setSubjects(teacherBuffer.getSubjects());
		model.addAttribute("teacher", updatedTeacher);
		setTitle(model, String.format("Update teacher: %s %s, %s", teacher.getFirstName(), teacher.getLastName(),
				teacher.getProfile()));
		return "/teachers/view";
	}
	
	@PostMapping(path="/{id}/delete")
	String deleteTeacherWithSubjects(@PathVariable(name="id") Long id) {
		facade.deleteTeacher(id);
		return "redirect:/teachers/";
	}
} 

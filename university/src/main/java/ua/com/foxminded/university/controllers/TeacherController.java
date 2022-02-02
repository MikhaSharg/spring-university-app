package ua.com.foxminded.university.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import ua.com.foxminded.university.facade.ControllersFacade;
import ua.com.foxminded.university.model.Teacher;

import static ua.com.foxminded.university.controllers.ControllerUtils.*;

@Controller
@RequestMapping ("/teachers")
public class TeacherController {
	
	private final ControllersFacade facade;

	public TeacherController(ControllersFacade facade) {
		this.facade = facade;
	}
	
	@GetMapping
	String showTeachersList (Model model) {
		model.addAttribute("teachers", facade.collectAllTeachersForList());
		setTitle(model, "Teachers");
		return "teachers/list";
	}
	
	@GetMapping (path ="/{id}")
	String teachersView (@PathVariable(name = "id", required = true) Long id, Model model) {
		Teacher teacher = facade.collectTeacherForView(id);
		model.addAttribute("teacher", teacher);
		setTitle(model, String.format("Teacher: %s %s, %s", teacher.getFirstName(),
				teacher.getLastName(), teacher.getProfile()));
		return "teachers/view";
	}

}

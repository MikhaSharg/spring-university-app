package ua.com.foxminded.university.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import ua.com.foxminded.university.facade.ControllersFacade;
import ua.com.foxminded.university.facade.ControllersFacadeImpl;
import ua.com.foxminded.university.model.Group;
import ua.com.foxminded.university.model.Student;
import ua.com.foxminded.university.model.view.StudentView;
import ua.com.foxminded.university.model.view.StudentsView;
import ua.com.foxminded.university.wrappers.StudentRegistration;

import static ua.com.foxminded.university.controllers.ControllerUtils.*;

import java.util.List;

@Controller
@RequestMapping("/students")
public class StudentController {

	private final ControllersFacade facade;

	private  List<Group> avaliableGroups = null;
	
	public StudentController(ControllersFacade facade) {
		this.facade = facade;
	}

	@GetMapping
	String showStudentsList(Model model) {
		StudentsView studentsView = facade.collectAllStudentsForView();
		model.addAttribute("studentsView", studentsView);
		setTitle(model, "Students");
		return "students/list";
	}

	@GetMapping(path = "/{id}")
	String showViewStudent(@PathVariable(name = "id", required = true) Long id, Model model) {
		StudentView studentView = facade.collectStudentForView(id);
		model.addAttribute("student", studentView.getStudent());
		model.addAttribute("group", studentView.getGroup());
		setTitle(model, "Student", String.format("%s %s", studentView.getStudent().getFirstName(),
				studentView.getStudent().getLastName()));
		return "students/view";
	}
	
	@GetMapping(path="/registerNewStudent")
	String showNewStudentRegistrationForm (Model model) {
		avaliableGroups = facade.collectAllNotFullGroups();
		StudentRegistration studentRegistration = new StudentRegistration(avaliableGroups);
		model.addAttribute("student", studentRegistration);
		setTitle(model, "Students", "new student registration");
		return "students/registration";
	}
	
	@PostMapping(path="/newStudentRegistration")
	String registrateNewStudent (StudentRegistration studentRegistration, Model model) {
		studentRegistration.setAvaliableGroups(avaliableGroups);
		Long newStudentId = facade.saveNewStudent(studentRegistration.getStudent());
		StudentView studentView = facade.collectStudentForView(newStudentId);
		model.addAttribute("student", studentView.getStudent());
		model.addAttribute("group", studentView.getGroup());
		setTitle(model, "New student", String.format("%s %s", studentView.getStudent().getFirstName(),
				studentView.getStudent().getLastName()));
		return "students/view";
	}
}

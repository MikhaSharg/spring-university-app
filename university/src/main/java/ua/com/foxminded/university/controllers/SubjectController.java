package ua.com.foxminded.university.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import ua.com.foxminded.university.facade.ControllersFacade;
import ua.com.foxminded.university.model.view.SubjectView;

import static ua.com.foxminded.university.controllers.ControllerUtils.*;


@Controller
@RequestMapping("/subjects")
public class SubjectController {

	private final ControllersFacade facade;

	public SubjectController(ControllersFacade facade) {
		this.facade = facade;
	}

	@GetMapping
	String subjectsList(Model model) {
		model.addAttribute("subjects", facade.collectAllSubjectsForView());
		setTitle(model, "Subjects");
		return "subjects/list";
	}

	@GetMapping (path = "/{id}")
	String subjectView (@PathVariable (name = "id", required = true) Long id, Model model) {
	SubjectView subjectView = facade.collectSubjectForView(id);	
	model.addAttribute("subject", subjectView);
	setTitle(model, "Subjects/", String.format("%s", subjectView.getName()));
	return "subjects/view";	
	}
}

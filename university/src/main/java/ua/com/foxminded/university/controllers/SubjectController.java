package ua.com.foxminded.university.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import ua.com.foxminded.university.facade.ControllersFacade;
import ua.com.foxminded.university.model.Subject;
import ua.com.foxminded.university.model.view.SubjectView;
import ua.com.foxminded.university.wrappers.SubjectWrapper;

import static ua.com.foxminded.university.controllers.ControllerUtils.*;

@Controller
@RequestMapping("/subjects")
public class SubjectController {

	private final ControllersFacade facade;

	public SubjectController(ControllersFacade facade) {
		this.facade = facade;
	}

	@GetMapping
	String showSubjectsList(Model model) {
		model.addAttribute("subjects", facade.collectAllSubjectsForView());
		setTitle(model, "Subjects");
		return "subjects/list";
	}

	@GetMapping(path = "/{id}")
	String subjectView(@PathVariable(name = "id", required = true) Long id, Model model) {
		SubjectView subjectView = facade.collectSubjectForView(id);
		model.addAttribute("subject", subjectView);
		setTitle(model, "Subjects", subjectView.getName());
		return "subjects/view";
	}
	
	@GetMapping(path="/{teacherId}/new")
	String showNewSubjectForm (@PathVariable(name="teacherId") Long teacherId, Model model) {
		SubjectWrapper subject = new SubjectWrapper(facade.collectTeacherForView(teacherId));
		model.addAttribute("subject", subject);
		setTitle(model, "Add new subject to teacher", subject.getName());
		return "subjects/new";
	}
	
	@PostMapping(path="/{teacherId}/addNewSubject")
	String addNewSubjectToTeacher (@PathVariable(name="teacherId") Long teacherId, SubjectWrapper subject, Model model) {
		SubjectView subjectView = facade.addNewSubjectToTeacher (teacherId, subject);
		String response = "redirect:/teachers/"+teacherId;
		model.addAttribute("subject", subjectView);
		return response;
	}
	
	@GetMapping(path="/delete/subject{subjectId}/from/teacher{teacherId}")
	String deleteSubjectFromTeacher (@PathVariable(name="teacherId") Long teacherId, @PathVariable(name="subjectId") Long subjectId) {
		facade.deleteSubject(teacherId, subjectId);
		String response = "redirect:/teachers/"+teacherId;
		return response;
	}
	
	@GetMapping(path="/edit/subject{subjectId}/for/teacher{teacherId}")
	String showSubjectEditFormTeacherMode (@PathVariable(name="subjectId") Long subjectId, @PathVariable(name="teacherId") Long teacherId, Model model) {
		SubjectWrapper subject = new SubjectWrapper(
				facade.findSubjectById(subjectId), 
				facade.collectTeacherForView(teacherId),
				facade.collectTeachersForSubject(subjectId));
		model.addAttribute("subject", subject);
		return "subjects/edit";
	}
	
	@PostMapping(path="/edit/subject{subjectId}/for/teacher{teacherId}")
	String updateSubjectTeacherMode (@PathVariable(name="subjectId") Long subjectId, @PathVariable(name="teacherId") Long teacherId, Model model, SubjectWrapper subjectWrapper) {
		subjectWrapper.setId(subjectId);
		subjectWrapper.setTeacher(facade.collectTeacherForView(teacherId));
		facade.saveSubject(subjectWrapper.getNewSubject());
		return "redirect:/teachers/"+teacherId;
	}
	
	@GetMapping(path="/{id}/edit")
	String showSubjectEditFormBaseMode (@PathVariable(name="id") Long id, Model model) {
		SubjectWrapper subject = new SubjectWrapper(
				facade.findSubjectById(id), 
				facade.collectTeachersForSubject(id));
		model.addAttribute("subject", subject);
		return "subjects/editBase";
	}
	
	@PostMapping(path="/{id}/edit")
	String updateSubjectBaseMode (@PathVariable(name="id") Long id, Model model, SubjectWrapper subjectWrapper) {
		subjectWrapper.setId(id);
		subjectWrapper.setTeachers(facade.collectTeachersForSubject(id));
		facade.saveSubject(subjectWrapper.getNewSubject());
		model.addAttribute("subject", subjectWrapper);
		return "subjects/view";
	}
	
	@GetMapping(path="/{id}/delete")
	String showSubjectDeleteForm (@PathVariable(name="id") Long id, Model model) {
		SubjectWrapper subject = new SubjectWrapper(
				facade.findSubjectById(id), 
				facade.collectTeachersForSubject(id));
		model.addAttribute("subject", subject);
		return "subjects/delete";
	}
	
	@PostMapping(path="/{id}/delete")
	String deleteSubject (@PathVariable(name="id") Long id, Model model, SubjectWrapper subjectWrapper) {
		System.out.println(subjectWrapper);
		facade.deleteSubject(subjectWrapper.getTeacherId(), id);
		model.addAttribute("subjects", facade.collectAllSubjectsForView());
		return "subjects/list";
	}
}

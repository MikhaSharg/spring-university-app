package ua.com.foxminded.university.controllers;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import ua.com.foxminded.university.facade.ControllersFacade;
import ua.com.foxminded.university.facade.ControllersFacadeImpl;
import ua.com.foxminded.university.model.Lecture;
import ua.com.foxminded.university.model.view.DateRange;
import ua.com.foxminded.university.model.view.LecturesAudience;
import ua.com.foxminded.university.model.view.LecturesSubject;
import ua.com.foxminded.university.model.view.LecturesTeacher;
import ua.com.foxminded.university.model.view.LecturesView;
import ua.com.foxminded.university.services.LectureService;

@Controller
@RequestMapping("/lectures")
public class LectureController {

	private final LectureService lectureService;
	private final ControllersFacade controllersFacadeImpl;

	private LocalDate currentDate = LocalDate.now();

	public LectureController(LectureService lectureService, ControllersFacadeImpl controllersFacadeImpl) {
		this.lectureService = lectureService;
		this.controllersFacadeImpl = controllersFacadeImpl;
	}

	@GetMapping
	String lectureList(Model model) {
		List<LecturesView> lecturesViews = controllersFacadeImpl.collectLecturesForDateRange(currentDate, currentDate);
		model.addAttribute("lectures", lecturesViews);
		model.addAttribute("dateRange", new DateRange());
		return "lectures/list";
	}

	@PostMapping(path = "/dateRange")
	String findLecturesByDateRange(Model model, DateRange dateRange) {
		List<LecturesView> lecturesViews = controllersFacadeImpl.collectLecturesForDateRange(
				LocalDate.parse(dateRange.getStart()), LocalDate.parse(dateRange.getEnd()));
		model.addAttribute("lectures", lecturesViews);
		return "lectures/list";
	}

	@GetMapping(path = "/groups/{id}")
	String lectureListForGroup(@PathVariable(name = "id", required = true) Long id, Model model) {

		List<LecturesView> lecturesViews = controllersFacadeImpl.collectLecturesForGroupByDateRange(currentDate,
				currentDate, id);
		model.addAttribute("lectures", lecturesViews);
		model.addAttribute("dateRange", new DateRange());
		return "lectures/list-group";
	}

	@PostMapping(path = "/dateRangeGroup/{id}")
	String findLecturesForGroupByDateRange(@PathVariable(name = "id", required = true) Long id, Model model,
			DateRange dateRange) {
		List<LecturesView> lecturesViews = controllersFacadeImpl.collectLecturesForGroupByDateRange(
				LocalDate.parse(dateRange.getStart()), LocalDate.parse(dateRange.getEnd()), id);
		model.addAttribute("lectures", lecturesViews);
		return "lectures/list-group";
	}

	@GetMapping(path = "/teacher/{id}")
	String showLecturesForTeacher(@PathVariable(name = "id", required = true) Long id, Model model) {
		LecturesTeacher lecturesTeacher = controllersFacadeImpl.collectLecturesForTeacherByDateRange(currentDate,
				currentDate, id);
		model.addAttribute("lectures", lecturesTeacher);
		model.addAttribute("dateRange", new DateRange());
		return "lectures/list-teacher";
	}

	@PostMapping(path = "/dateRangeTeacher/{id}")
	String findLecturesForTeacherByDateRange(@PathVariable(name = "id", required = true) Long id, Model model,
			DateRange dateRange) {
		LecturesTeacher lecturesTeacher = controllersFacadeImpl.collectLecturesForTeacherByDateRange(
				LocalDate.parse(dateRange.getStart()), LocalDate.parse(dateRange.getEnd()), id);
		model.addAttribute("lectures", lecturesTeacher);
		return "lectures/list-teacher";
	}

	@GetMapping(path = "/subject/{id}")
	String showLecturesForSubject(@PathVariable(name = "id", required = true) Long id, Model model) {
		LecturesSubject lecturesSubject = controllersFacadeImpl.collectLecturesForSubjectByDateRange(currentDate,
				currentDate, id);
		model.addAttribute("lectures", lecturesSubject);
		model.addAttribute("dateRange", new DateRange());
		return "lectures/list-subject";

	}

	@PostMapping(path = "/dateRangeSubject/{id}")
	String findLecturesForSubjectByDateRange(@PathVariable(name = "id", required = true) Long id, Model model,
			DateRange dateRange) {
		LecturesSubject lecturesSubject = controllersFacadeImpl.collectLecturesForSubjectByDateRange(
				LocalDate.parse(dateRange.getStart()), LocalDate.parse(dateRange.getEnd()), id);
		model.addAttribute("lectures", lecturesSubject);
		return "lectures/list-subject";
	}
	
	@GetMapping(path = "/audience/{id}")
	String showLecturesForAudience(@PathVariable(name = "id", required = true) Long id, Model model) {
		LecturesAudience lecturesAudience = controllersFacadeImpl.collectLecturesForAudienceByDateRange(currentDate,
				currentDate, id);
		model.addAttribute("lectures", lecturesAudience);
		model.addAttribute("dateRange", new DateRange());
		return "lectures/list-audience";

	}

	@PostMapping(path = "/dateRangeAudience/{id}")
	String findLecturesForAudienceByDateRange(@PathVariable(name = "id", required = true) Long id, Model model,
			DateRange dateRange) {
		LecturesAudience lecturesAudience = controllersFacadeImpl.collectLecturesForAudienceByDateRange(
				LocalDate.parse(dateRange.getStart()), LocalDate.parse(dateRange.getEnd()), id);
		model.addAttribute("lectures", lecturesAudience);
		return "lectures/list-audience";
	}
	
	@GetMapping(path = "/{id}")
	String showViewLecture(@PathVariable(name = "id", required = true) Long id, Model model) {
		Lecture lecture = controllersFacadeImpl.findStudentById(id);
		model.addAttribute("lecture", lecture);
		return "lectures/view";

	}
}

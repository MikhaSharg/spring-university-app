package ua.com.foxminded.university.controllers;



import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import ua.com.foxminded.university.facade.ControllersFacade;
import ua.com.foxminded.university.model.Lecture;
import ua.com.foxminded.university.model.view.DateRange;
import ua.com.foxminded.university.model.view.LecturesAudience;
import ua.com.foxminded.university.model.view.LecturesSubject;
import ua.com.foxminded.university.model.view.LecturesTeacher;
import ua.com.foxminded.university.model.view.LecturesView;

import static ua.com.foxminded.university.controllers.ControllerUtils.setTitle;

@Controller
@RequestMapping("/lectures")
public class LectureController {

	private final ControllersFacade facade;

	private LocalDate currentDate = LocalDate.now();

	public LectureController(ControllersFacade facade) {
		this.facade = facade;
	}

	@GetMapping
	String showLecturesForToday(Model model) {
		List<LecturesView> lecturesViews = facade.collectLecturesByDateRange(currentDate, currentDate);
		model.addAttribute("lectures", lecturesViews);
		model.addAttribute("dateRange", new DateRange());
		setTitle(model, "Lectures", currentDate.toString());
		return "lectures/list";
	}

	@PostMapping(path = "/dateRange")
	String showLecturesForDateRange(Model model, DateRange dateRange) {
		List<LecturesView> lecturesViews = facade.collectLecturesByDateRange(
				LocalDate.parse(dateRange.getStart()), LocalDate.parse(dateRange.getEnd()));
		model.addAttribute("lectures", lecturesViews);
		setTitle(model, "Lectures", String.format("%s - %s",dateRange.getStart(), dateRange.getEnd()));
		return "lectures/list";
	}

	@GetMapping(path = "/groups/{id}")
	String showLecturesForGroupForToday(@PathVariable(name = "id", required = true) Long id, Model model) {
		List<LecturesView> lecturesViews = facade.collectLecturesForGroupByDateRange(currentDate,
				currentDate, id);
		model.addAttribute("lectures", lecturesViews);
		model.addAttribute("dateRange", new DateRange());
		setTitle(model, "Lectures", 
				String.format("group %s", lecturesViews.get(0).getLectures().get(0).getGroup().getName()),
				String.format("date %s", currentDate.toString()));
		return "lectures/list-group";
	}

	@PostMapping(path = "/dateRangeGroup/{id}")
	String showLecturesForGroupByDateRange(@PathVariable(name = "id", required = true) Long id, Model model,
			DateRange dateRange) {
		List<LecturesView> lecturesViews = facade.collectLecturesForGroupByDateRange(
				LocalDate.parse(dateRange.getStart()), LocalDate.parse(dateRange.getEnd()), id);
		model.addAttribute("lectures", lecturesViews);
		setTitle(model, "Lectures", 
				String.format("group %s", lecturesViews.get(0).getLectures().get(0).getGroup().getName()),
				String.format("dates %s_%s", dateRange.getStart(), dateRange.getEnd()));
		return "lectures/list-group";
	}

	@GetMapping(path = "/teacher/{id}")
	String showLecturesForTeacherForToday(@PathVariable(name = "id", required = true) Long id, Model model) {
		LecturesTeacher lecturesTeacher = facade.collectLecturesForTeacherByDateRange(currentDate,
				currentDate, id);
		model.addAttribute("lectures", lecturesTeacher);
		model.addAttribute("dateRange", new DateRange());
		setTitle(model, "Lectures", 
				String.format("teacher %s, %s", lecturesTeacher.getName(), lecturesTeacher.getProfile()),
				String.format("date %s", currentDate.toString()));
		return "lectures/list-teacher";
	}

	@PostMapping(path = "/dateRangeTeacher/{id}")
	String showLecturesForTeacherByDateRange(@PathVariable(name = "id", required = true) Long id, Model model,
			DateRange dateRange) {
		LecturesTeacher lecturesTeacher = facade.collectLecturesForTeacherByDateRange(
				LocalDate.parse(dateRange.getStart()), LocalDate.parse(dateRange.getEnd()), id);
		model.addAttribute("lectures", lecturesTeacher);
		setTitle(model, "Lectures", 
				String.format("teacher %s, %s", lecturesTeacher.getName(), lecturesTeacher.getProfile()),
				String.format("dates %s_%s", dateRange.getStart(), dateRange.getEnd()));
		return "lectures/list-teacher";
	}

	@GetMapping(path = "/subject/{id}")
	String showLecturesForSubject(@PathVariable(name = "id", required = true) Long id, Model model) {
		LecturesSubject lecturesSubject = facade.collectLecturesForSubjectByDateRange(currentDate,
				currentDate, id);
		model.addAttribute("lectures", lecturesSubject);
		model.addAttribute("dateRange", new DateRange());
		setTitle(model, "Lectures", 
				String.format("subject %s", lecturesSubject.getName()),
				String.format("date %s", currentDate.toString()));
		return "lectures/list-subject";
	}

	@PostMapping(path = "/dateRangeSubject/{id}")
	String showLecturesForSubjectByDateRange(@PathVariable(name = "id", required = true) Long id, Model model,
			DateRange dateRange) {
		LecturesSubject lecturesSubject = facade.collectLecturesForSubjectByDateRange(
				LocalDate.parse(dateRange.getStart()), LocalDate.parse(dateRange.getEnd()), id);
		model.addAttribute("lectures", lecturesSubject);
		setTitle(model, "Lectures", 
				String.format("subject %s", lecturesSubject.getName()),
				String.format("dates %s_%s", dateRange.getStart(), dateRange.getEnd()));
		return "lectures/list-subject";
	}
	
	@GetMapping(path = "/audience/{id}")
	String showLecturesForAudience(@PathVariable(name = "id", required = true) Long id, Model model) {
		LecturesAudience lecturesAudience = facade.collectLecturesForAudienceByDateRange(currentDate,
				currentDate, id);
		model.addAttribute("lectures", lecturesAudience);
		model.addAttribute("dateRange", new DateRange());
		setTitle(model, "Lectures", 
				String.format("audience %s", lecturesAudience.getRoom()),
				String.format("date %s", currentDate.toString()));
		return "lectures/list-audience";
	}

	@PostMapping(path = "/dateRangeAudience/{id}")
	String showLecturesForAudienceByDateRange(@PathVariable(name = "id", required = true) Long id, Model model,
			DateRange dateRange) {
		LecturesAudience lecturesAudience = facade.collectLecturesForAudienceByDateRange(
				LocalDate.parse(dateRange.getStart()), LocalDate.parse(dateRange.getEnd()), id);
		model.addAttribute("lectures", lecturesAudience);
		setTitle(model, "Lectures", 
				String.format("audience %s", lecturesAudience.getRoom()),
				String.format("dates %s_%s", dateRange.getStart(), dateRange.getEnd()));
		return "lectures/list-audience";
	}
	
	@GetMapping(path = "/{id}")
	String showViewLecture(@PathVariable(name = "id", required = true) Long id, Model model) {
		Lecture lecture = facade.findLectureById(id);
		model.addAttribute("lecture", lecture);
		setTitle(model, "Lectures",
				"lecture",
				lecture.getSession().getPeriod(),
				lecture.getDate().toString(),
				lecture.getSubject().getName(),
				lecture.getGroup().getName());
		return "lectures/view";
	}
}

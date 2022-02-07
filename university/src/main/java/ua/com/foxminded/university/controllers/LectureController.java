package ua.com.foxminded.university.controllers;

import static ua.com.foxminded.university.controllers.ControllerUtils.setTitle;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import ua.com.foxminded.university.facade.ControllersFacade;
import ua.com.foxminded.university.misc.Status;
import ua.com.foxminded.university.model.FreeItem;
import ua.com.foxminded.university.model.Lecture;
import ua.com.foxminded.university.model.view.AudienceForm;
import ua.com.foxminded.university.model.view.DateRange;
import ua.com.foxminded.university.model.view.FreeItemsView;
import ua.com.foxminded.university.model.view.LecturesAudience;
import ua.com.foxminded.university.model.view.LecturesGroup;
import ua.com.foxminded.university.model.view.LecturesSubject;
import ua.com.foxminded.university.model.view.LecturesTeacher;
import ua.com.foxminded.university.model.view.LecturesView;
import ua.com.foxminded.university.model.view.RescheduleLecture;

@Controller
@RequestMapping("/lectures")
public class LectureController {

	private final ControllersFacade facade;
	private LocalDate currentDate = LocalDate.now();

	private RescheduleLecture rescheduleLecture = new RescheduleLecture();

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
		List<LecturesView> lecturesViews = facade.collectLecturesByDateRange(LocalDate.parse(dateRange.getStart()),
				LocalDate.parse(dateRange.getEnd()));
		model.addAttribute("lectures", lecturesViews);
		setTitle(model, "Lectures", String.format("%s - %s", dateRange.getStart(), dateRange.getEnd()));
		return "lectures/list";
	}

	@GetMapping(path = "/groups/{id}")
	String showLecturesForGroupForToday(@PathVariable(name = "id", required = true) Long id, Model model) {
		LecturesGroup lecturesGroup = facade.collectLecturesForGroupByDateRange(currentDate, currentDate, id);
		model.addAttribute("lectures", lecturesGroup);
		model.addAttribute("dateRange", new DateRange());
		setTitle(model, "Lectures", String.format("group %s", lecturesGroup.getName()),
				String.format("date %s", currentDate.toString()));
		return "lectures/list-group";
	}

	@PostMapping(path = "/dateRangeGroup/{id}")
	String showLecturesForGroupByDateRange(@PathVariable(name = "id", required = true) Long id, Model model,
			DateRange dateRange) {
		LecturesGroup lecturesGroup = facade.collectLecturesForGroupByDateRange(LocalDate.parse(dateRange.getStart()),
				LocalDate.parse(dateRange.getEnd()), id);
		model.addAttribute("lectures", lecturesGroup);
		setTitle(model, "Lectures", String.format("group %s", lecturesGroup.getName()),
				String.format("dates %s_%s", dateRange.getStart(), dateRange.getEnd()));
		return "lectures/list-group";
	}

	@GetMapping(path = "/teacher/{id}")
	String showLecturesForTeacherForToday(@PathVariable(name = "id", required = true) Long id, Model model) {
		LecturesTeacher lecturesTeacher = facade.collectLecturesForTeacherByDateRange(currentDate, currentDate, id);
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
		LecturesSubject lecturesSubject = facade.collectLecturesForSubjectByDateRange(currentDate, currentDate, id);
		model.addAttribute("lectures", lecturesSubject);
		model.addAttribute("dateRange", new DateRange());
		setTitle(model, "Lectures", String.format("subject %s", lecturesSubject.getName()),
				String.format("date %s", currentDate.toString()));
		return "lectures/list-subject";
	}

	@PostMapping(path = "/dateRangeSubject/{id}")
	String showLecturesForSubjectByDateRange(@PathVariable(name = "id", required = true) Long id, Model model,
			DateRange dateRange) {
		LecturesSubject lecturesSubject = facade.collectLecturesForSubjectByDateRange(
				LocalDate.parse(dateRange.getStart()), LocalDate.parse(dateRange.getEnd()), id);
		model.addAttribute("lectures", lecturesSubject);
		setTitle(model, "Lectures", String.format("subject %s", lecturesSubject.getName()),
				String.format("dates %s_%s", dateRange.getStart(), dateRange.getEnd()));
		return "lectures/list-subject";
	}

	@GetMapping(path = "/audience/{id}")
	String showLecturesForAudience(@PathVariable(name = "id", required = true) Long id, Model model) {
		LecturesAudience lecturesAudience = facade.collectLecturesForAudienceByDateRange(currentDate, currentDate, id);
		model.addAttribute("lectures", lecturesAudience);
		model.addAttribute("dateRange", new DateRange());
		setTitle(model, "Lectures", String.format("audience %s", lecturesAudience.getRoom()),
				String.format("date %s", currentDate.toString()));
		return "lectures/list-audience";
	}

	@PostMapping(path = "/dateRangeAudience/{id}")
	String showLecturesForAudienceByDateRange(@PathVariable(name = "id", required = true) Long id, Model model,
			DateRange dateRange) {
		LecturesAudience lecturesAudience = facade.collectLecturesForAudienceByDateRange(
				LocalDate.parse(dateRange.getStart()), LocalDate.parse(dateRange.getEnd()), id);
		model.addAttribute("lectures", lecturesAudience);
		setTitle(model, "Lectures", String.format("audience %s", lecturesAudience.getRoom()),
				String.format("dates %s_%s", dateRange.getStart(), dateRange.getEnd()));
		return "lectures/list-audience";
	}

	@GetMapping(path = "/{id}")
	String showViewLecture(@PathVariable(name = "id", required = true) Long id, Model model) {
		Lecture lecture = facade.findLectureById(id);
		model.addAttribute("lecture", lecture);
		setTitle(model, "Lectures", "lecture", lecture.getSession().getPeriod(), lecture.getDate().toString(),
				lecture.getSubject().getName(), lecture.getGroup().getName());
		return "lectures/view";
	}

	@PostMapping(path = "/{id}/cancel")
	String cancelLecture(@PathVariable(name = "id", required = true) Long id) {
		facade.cancelLecture(id);
		return "redirect:/lectures";
	}

	@GetMapping(path = "/{id}/reschedule")
	String resheduleLecture(@PathVariable(name = "id", required = true) Long id, Model model) {
		FreeItemsView freeItemsView = facade.collectFreeItemsInSchedule(id);
		model.addAttribute("freeItems", freeItemsView);
		return "lectures/reschedule-lecture";
	}

	@GetMapping(path = "/reschedule/lecture{lectureId}/item{hashCode}")
	String showRescheduleLectureView(@PathVariable(name = "lectureId", required = true) Long lectureId,
			@PathVariable(name = "hashCode") Integer hashCode, Model model) {
		FreeItemsView freeItemsView = facade.collectFreeItemsInSchedule(lectureId);
		FreeItem freeItem = freeItemsView.getFreeItems().stream().filter(item -> item.hashCode() == hashCode).findAny()
				.get();

		Lecture lecture = new Lecture(freeItem.getDate(), facade.findSessionById(freeItem.getSessionId()),
				freeItemsView.getLecture().getSubject(), freeItemsView.getLecture().getTeacher(),
				freeItemsView.getLecture().getGroup(), true, Status.RESCHEDULED, freeItemsView.getLecture().getId());

		AudienceForm audienceForm = new AudienceForm(
				facade.collectAvailableAudiences(freeItem.getDate(), freeItem.getSessionId()));
		System.out.println(freeItemsView);
		rescheduleLecture.setNewLecture(lecture);
		rescheduleLecture.setAudiences(audienceForm.getAudiences());

		if (freeItem.getLectureId() != null) {
			rescheduleLecture.getNewLecture().setId(freeItem.getLectureId());
			rescheduleLecture.getNewLecture().setUpdate(true);
		}
		model.addAttribute("lecture", lecture);
		model.addAttribute("audiences", audienceForm);
		return "lectures/view-reschedule-lecture";
	}

	@PostMapping(path = "/saveResheduledLecture")
	String saveResheduledLecture(AudienceForm audienceForm, Model model) {
		Lecture lecture = rescheduleLecture.getNewLecture();
		lecture.setAudience(rescheduleLecture.getAudiences().stream()
				.filter(e -> e.getRoomNumber().equals(audienceForm.getRoomNumber())).findAny().orElseThrow());
		Lecture savedLecture = facade.saveRescheduleLecture(lecture);
		model.addAttribute("lecture", savedLecture);
		return "lectures/view";
	}

}

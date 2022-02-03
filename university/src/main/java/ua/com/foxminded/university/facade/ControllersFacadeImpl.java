package ua.com.foxminded.university.facade;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import org.springframework.stereotype.Controller;

import ua.com.foxminded.university.misc.GeneratorConfig;
import ua.com.foxminded.university.model.Audience;
import ua.com.foxminded.university.model.FreeItem;
import ua.com.foxminded.university.model.Group;
import ua.com.foxminded.university.model.Lecture;
import ua.com.foxminded.university.model.LectureSessions;
import ua.com.foxminded.university.model.Student;
import ua.com.foxminded.university.model.Subject;
import ua.com.foxminded.university.model.Teacher;
import ua.com.foxminded.university.model.view.FreeItemsView;
import ua.com.foxminded.university.model.view.LecturesAudience;
import ua.com.foxminded.university.model.view.LecturesGroup;
import ua.com.foxminded.university.model.view.LecturesSubject;
import ua.com.foxminded.university.model.view.LecturesTeacher;
import ua.com.foxminded.university.model.view.LecturesView;
import ua.com.foxminded.university.model.view.StudentView;
import ua.com.foxminded.university.model.view.StudentsView;
import ua.com.foxminded.university.model.view.SubjectView;
import ua.com.foxminded.university.services.AudienceService;
import ua.com.foxminded.university.services.GroupService;
import ua.com.foxminded.university.services.LectureService;
import ua.com.foxminded.university.services.SessionService;
import ua.com.foxminded.university.services.StudentService;
import ua.com.foxminded.university.services.SubjectService;
import ua.com.foxminded.university.services.TeacherService;

@Controller
public class ControllersFacadeImpl implements ControllersFacade {

	private final StudentService studentService;
	private final GroupService groupService;
	private final LectureService lectureService;
	private final TeacherService teacherService;
	private final SubjectService subjectService;
	private final AudienceService audienceService;
	private final SessionService sessionService;
	private final GeneratorConfig generatorConfig;

	public ControllersFacadeImpl(StudentService studentService, GroupService groupService,
			LectureService lectureService, TeacherService teacherService, SubjectService subjectService,
			AudienceService audieenceService, SessionService sessionService, GeneratorConfig generatorConfig) {
		this.studentService = studentService;
		this.groupService = groupService;
		this.lectureService = lectureService;
		this.teacherService = teacherService;
		this.subjectService = subjectService;
		this.audienceService = audieenceService;
		this.sessionService = sessionService;
		this.generatorConfig = generatorConfig;
	}

	private Map<Long, Group> collectGroupsFromStudents(Collection<Student> students) {
		Set<Long> groupIds = students.stream().map(Student::getId).collect(Collectors.toSet());
		return collectGroupsToMap(group -> groupIds.contains(group.getId()));
	}

	private Map<Long, Group> collectGroupsToMap(Predicate<Group> filter) {
		return groupService.findAllExistGroups().stream().filter(filter)
				.collect(Collectors.toMap(Group::getId, it -> it));

	}

	private StudentsView collectStudentsAndGroups(Supplier<List<Student>> studentSupplier) {
		List<Student> students = studentSupplier.get();
		return new StudentsView(students, collectGroupsFromStudents(students));
	}

	@Override
	public StudentsView collectAllStudentsForView() {
		return collectStudentsAndGroups(() -> studentService.findAllExistStudents());
	}

	@Override
	public StudentView collectStudentForView(Long id) {
		Student student = studentService.findStudentById(id);
		Group group = groupService.findGroupById(student.getGroupId());
		return new StudentView(student, group);
	}

	@Override
	public List<LecturesView> collectLecturesByDateRange(LocalDate start, LocalDate end) {
		List<Lecture> lectures = lectureService.findAllLecturesForDateRange(start, end);
		List<LecturesView> lecturesViews = new ArrayList<>();
		List<LocalDate> dates = lectures.stream().map(e -> e.getDate()).distinct().collect(Collectors.toList());
		dates.stream().forEach(e -> {
			List<Lecture> lecturesForOneDate = new ArrayList<>();
			for (Lecture lecture : lectures) {
				if (lecture.getDate().equals(e)) {
					lecturesForOneDate.add(lecture);
				}
			}
			lecturesForOneDate.sort(((Lecture lecture1, Lecture lecture2) -> lecture1.getSession().getId()
					.compareTo(lecture2.getSession().getId())));
			lecturesViews.add(new LecturesView(lecturesForOneDate));
		});
		return lecturesViews;
	}

	@Override
	public LecturesTeacher collectLecturesForTeacherByDateRange(LocalDate start, LocalDate end, Long teacherId) {
		Map<LocalDate, List<Lecture>> lectures = lectureService.findAllLecturesForDateRange(start, end).stream()
				.filter(e -> e.getTeacher().getId() == teacherId).collect(Collectors.groupingBy(Lecture::getDate));
		Teacher teacher = teacherService.findTeacherById(teacherId);
		return new LecturesTeacher(teacherId, teacher.getFirstName() + " " + teacher.getLastName(),
				teacher.getProfile(), sorteMap(lectures));
	}

	@Override
	public LecturesSubject collectLecturesForSubjectByDateRange(LocalDate start, LocalDate end, Long subjectId) {
		Map<LocalDate, List<Lecture>> lectures = lectureService.findAllLecturesForDateRange(start, end).stream()
				.filter(e -> e.getSubject().getId() == subjectId).collect(Collectors.groupingBy(Lecture::getDate));
		Subject subject = subjectService.findSubjectById(subjectId);
		return new LecturesSubject(subjectId, subject.getName(), sorteMap(lectures));
	}

	@Override
	public LecturesAudience collectLecturesForAudienceByDateRange(LocalDate start, LocalDate end, Long audienceId) {
		Map<LocalDate, List<Lecture>> lectures = lectureService.findAllLecturesForDateRange(start, end).stream()
				.filter(e -> e.getAudience().getId() == audienceId).collect(Collectors.groupingBy(Lecture::getDate));
		Audience audience = audienceService.findAudienceById(audienceId);
		return new LecturesAudience(audienceId, audience.getRoomNumber(), sorteMap(lectures));
	}

	@Override
	public LecturesGroup collectLecturesForGroupByDateRange(LocalDate start, LocalDate end, Long groupId) {
		List<Lecture> listLectures = lectureService.findAllLecturesForDateRange(start, end);
		Map<LocalDate, List<Lecture>> lectures = listLectures.stream().filter(e -> e.getGroup().getId().equals(groupId))
				.collect(Collectors.groupingBy(Lecture::getDate));
		Group group = groupService.findGroupById(groupId);
		return new LecturesGroup(groupId, group.getName(), sorteMap(lectures));
	}

	@Override
	public Lecture findLectureById(Long studentId) {
		return lectureService.findLectureById(studentId);
	}

	@Override
	public Group findGroupById(Long groupId) {
		Group group = groupService.findGroupById(groupId);
		group.setStudents(studentService.findAllStudentsByGroupId(groupId));
		return group;
	}

	@Override
	public List<Teacher> collectAllTeachersForList() {
		return teacherService.findAllExistTeachers();
	}

	@Override
	public Teacher collectTeacherForView(Long teacherId) {
		return teacherService.findTeacherById(teacherId);
	}

	@Override
	public List<Subject> collectAllSubjectsForView() {
		return subjectService.findAllExistSubjects();
	}

	@Override
	public SubjectView collectSubjectForView(Long subjectId) {
		List<Teacher> teachers = teacherService.findAllTeachersBySubjectId(subjectId);
		return new SubjectView(subjectId, subjectService.findSubjectById(subjectId).getName(), teachers);
	}

	@Override
	public List<Group> collectAllGroupsForList() {
		return groupService.findAllExistGroups();
	}

	@Override
	public void cancelLecture(Long lectureId) {
		lectureService.cancelLecture(lectureId);
	}

	@Override
	public FreeItemsView collectFreeItemsInSchedule(Long lectureId) {
		Lecture lecture = lectureService.findLectureById(lectureId);
		List<FreeItem> freeItems = lectureService.findAllFreeItemsInSchedule(lecture.getTeacher().getId(),
				lecture.getGroup().getId());
		return new FreeItemsView(lecture, freeItems);
	}

	@Override
	public LectureSessions findSessionById(Long sessionId) {
		return sessionService.findSessiontById(sessionId);
	}

	@Override
	public List<Audience> collectAvailableAudiences(LocalDate date, Long sessionId) {
		return audienceService.findAllFreeAudiencesByDateAndSession(date, sessionId);
	}

	@Override
	public Lecture saveRescheduleLecture(Lecture lecture) {
		return lectureService.saveRescheduleLecture(lecture);

	}

	private Map<LocalDate, List<Lecture>> sorteMap(Map<LocalDate, List<Lecture>> unsortedMap) {
		Map<LocalDate, List<Lecture>> sorteMap = new TreeMap<>(unsortedMap);
		for (Map.Entry<LocalDate, List<Lecture>> entry : sorteMap.entrySet()) {
			entry.getValue().sort((Lecture lecture1, Lecture lecture2) -> lecture1.getSession().getId()
					.compareTo(lecture2.getSession().getId()));
		}
		return new TreeMap<>(unsortedMap);
	}

	@Override
	public List<Group> collectAllNotFullGroups() {
		return groupService.findAllNotFullGroups();
	}

	@Override
	public Long saveNewStudent(Student student) {
		return studentService.saveStudent(student).getId();
	}

	@Override
	public Student updateStudent(Student student) {
		return studentService.saveStudent(student);
	}

	@Override
	public void deleteStudent(Long id) {
		studentService.deleteStudentById(id);
	}

	@Override
	public Teacher updateTeacher(Teacher newTeacher) {
		return teacherService.saveTeacher(newTeacher);
	}

}

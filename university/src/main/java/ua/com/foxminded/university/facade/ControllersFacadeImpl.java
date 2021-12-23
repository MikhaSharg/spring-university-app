package ua.com.foxminded.university.facade;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import org.springframework.stereotype.Controller;

import ua.com.foxminded.university.model.*;
import ua.com.foxminded.university.model.view.*;
import ua.com.foxminded.university.services.AudienceService;
import ua.com.foxminded.university.services.GroupService;
import ua.com.foxminded.university.services.LectureService;
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

	public ControllersFacadeImpl(StudentService studentService, GroupService groupService,
			LectureService lectureService, TeacherService teacherService, SubjectService subjectService,
			AudienceService audieenceService) {
		this.studentService = studentService;
		this.groupService = groupService;
		this.lectureService = lectureService;
		this.teacherService = teacherService;
		this.subjectService = subjectService;
		this.audienceService = audieenceService;
	}

	private Map<Long, Group> collectGroupsFromStudents(Collection<Student> students) {
		Set<Long> groupIds = students.stream().map(Student::getId).collect(Collectors.toSet());
		return collectGroupsToMap (group -> groupIds.contains(group.getId()));
	}
	
	private Map<Long, Group> collectGroupsToMap (Predicate<Group> filter) {
		return groupService.findAllExistGroups().stream().filter(filter).collect(Collectors.toMap(Group::getId, it->it));
		
	}

	private StudentsView collectStudentsAndGroups (Supplier<List<Student>> studentSupplier) {
		List<Student> students = studentSupplier.get();
		return new StudentsView(students, collectGroupsFromStudents(students));
	}

	@Override
	public StudentsView collectAllStudentsForView() {
		return collectStudentsAndGroups(()->studentService.findAllExistStudents());
	}

	@Override
	public StudentView collectStudentForView(Long id) {
		Student student = studentService.findStudentById(id);
		Group group = groupService.findGroupsById(student.getGroupId());
		List<Lecture> lectures = lectureService.findLectureForGroupForCurrentDay(group.getId());
		return new StudentView(student, group, lectures);
	}

	@Override
	public LecturesView collectLecturesForCurrentDate() {
		
		return null;
	}

	@Override
	public List<LecturesView> collectLecturesByDateRange(LocalDate start, LocalDate end) {
		List<Lecture> lectures = lectureService.findLectureForDateRange(start, end);
		List<LecturesView> lecturesViews = new ArrayList<>();
		List<LocalDate> dates = lectures.stream().map(e->e.getDate()).distinct().collect(Collectors.toList());
		dates.stream().forEach(e->{
			List <Lecture> lecturesForOneDate = new ArrayList<>();
			for(Lecture lecture : lectures) {
				if (lecture.getDate().equals(e)) {
					lecturesForOneDate.add(lecture);
				}
			}
			lecturesViews.add(new LecturesView (lecturesForOneDate));
		}); 
		return lecturesViews;
	}

	@Override
	public List<LecturesView> collectLecturesForGroupByDateRange(LocalDate start, LocalDate end, Long groupId) {
		List<Lecture> lectures = lectureService.findLectureForDateRange(start, end);
		List<LecturesView> lecturesViews = new ArrayList<>();
		List<LocalDate> dates = lectures.stream().map(e->e.getDate()).distinct().collect(Collectors.toList());
		dates.stream().forEach(e->{
			List <Lecture> lecturesForOneDate = new ArrayList<>();
			for(Lecture lecture : lectures) {
				if (lecture.getDate().equals(e)&&lecture.getGroup().getId()==groupId) {
					lecturesForOneDate.add(lecture);
				}
			}
			lecturesViews.add(new LecturesView (lecturesForOneDate));
		}); 
		return lecturesViews;
	}

	@Override
	public LecturesTeacher collectLecturesForTeacherByDateRange(LocalDate start, LocalDate end, Long teacherId) {
		Map<LocalDate, List<Lecture>> lectures = lectureService.findLectureForDateRange(start, end)
				.stream()
				.filter(e->e.getTeacher().getId()==teacherId)
				.collect(Collectors.groupingBy(Lecture::getDate));
		Teacher teacher = teacherService.findTeacherById(teacherId);
		return new LecturesTeacher(teacherId, teacher.getFirstName()+" "+teacher.getLastName(), teacher.getProfile(), lectures);
	}

	@Override
	public LecturesSubject collectLecturesForSubjectByDateRange(LocalDate start, LocalDate end, Long subjectId) {
		Map<LocalDate, List<Lecture>> lectures = lectureService.findLectureForDateRange(start, end)
				.stream()
				.filter(e->e.getSubject().getId()==subjectId)
				.collect(Collectors.groupingBy(Lecture::getDate));
		Subject subject = subjectService.findSubjectById(subjectId);
		return new LecturesSubject(subjectId, subject.getName(), lectures);
	}

	@Override
	public LecturesAudience collectLecturesForAudienceByDateRange(LocalDate start, LocalDate end, Long audienceId) {
		Map<LocalDate, List<Lecture>> lectures = lectureService.findLectureForDateRange(start, end)
				.stream()
				.filter(e->e.getAudience().getId()==audienceId)
				.collect(Collectors.groupingBy(Lecture::getDate));
		Audience audience = audienceService.findAudienceById(audienceId);
		return new LecturesAudience(audienceId, audience.getRoomNumber(), lectures);
	}

	@Override
	public Lecture findStudentById(Long studentId) {
		return lectureService.findLectureById(studentId);
	}

	@Override
	public Group findGroupById(Long groupId) {
		Group group = groupService.findGroupsById(groupId); 
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
	public List <Subject> collectAllSubjectsForView() {
		return subjectService.findAllExistSubjects();
	}

	@Override
	public SubjectView collectSubjectForView(Long subjectId) {
		List<Teacher> teachers = teacherService.findAllTeachersBySubjectId(subjectId);
		return new SubjectView(subjectId, subjectService.findSubjectById(subjectId).getName(), teachers);
	}
	
}

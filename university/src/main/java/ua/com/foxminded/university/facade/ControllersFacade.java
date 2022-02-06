package ua.com.foxminded.university.facade;

import java.time.LocalDate;
import java.util.List;

import ua.com.foxminded.university.model.Audience;
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
import ua.com.foxminded.university.wrappers.StudentWrapper;
import ua.com.foxminded.university.wrappers.SubjectWrapper;

public interface ControllersFacade {
	
	//students
    StudentsView collectAllStudentsForView();
    StudentView collectStudentForView(Long id);
    Long saveNewStudent(Student student);
    Student updateStudent (Student student);
    void deleteStudent(Long id);
    StudentWrapper prepareDataForMoveStudentForm(Long studentId);
    
    //lectures
    List<LecturesView> collectLecturesByDateRange(LocalDate start, LocalDate end);
    LecturesGroup collectLecturesForGroupByDateRange(LocalDate start, LocalDate end, Long groupId);
    LecturesTeacher collectLecturesForTeacherByDateRange (LocalDate start, LocalDate end, Long teacherId);
    LecturesSubject collectLecturesForSubjectByDateRange (LocalDate start, LocalDate end, Long subjectId);
    LecturesAudience collectLecturesForAudienceByDateRange (LocalDate start, LocalDate end, Long audienceId);
    Lecture findLectureById (Long lectureId);
    Lecture saveRescheduleLecture(Lecture lecture);
    
    //groups
    Group findGroupById (Long groupId);
    List <Group> collectAllGroupsForList();
    List<Group> collectAllNotFullGroups();
    void saveGroup(Group group);
    
    //teachers
    List<Teacher> collectAllTeachersForList();
    Teacher collectTeacherForView(Long teacherId);
    Teacher updateTeacher(Teacher newTeacher);
	void deleteTeacher(Long id);
	List<Teacher> collectTeachersForSubject (Long subjectId);

    
    //subjects
    List <Subject> collectAllSubjectsForView();
	SubjectView collectSubjectForView(Long subjectId);
	Subject saveSubject(Subject newSubject);
	Subject findSubjectById(Long subjectId);
	SubjectView addNewSubjectToTeacher(Long teacherId, SubjectWrapper subject);
	void deleteSubject(Long teacherId, Long subjectId);
	
	//audiences
	List<Audience> collectAvailableAudiences(LocalDate date, Long sessionId);
	
	//sessions
	LectureSessions findSessionById(Long sessionId);
	
	// others
	void cancelLecture (Long lectureId);
	FreeItemsView collectFreeItemsInSchedule (Long lectureId);
	
	
	
	
	
	
	
	
	
	
	
	

	

	

	

	
	
	
	
	
}

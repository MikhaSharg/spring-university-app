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
import ua.com.foxminded.university.model.view.*;

public interface ControllersFacade {
	
	//students
    StudentsView collectAllStudentsForView();
    StudentView collectStudentForView(Long id);
    Long saveNewStudent(Student student);
    
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
    
    //teachers
    List<Teacher> collectAllTeachersForList();
    Teacher collectTeacherForView(Long teacherId);
    
    //subjects
    List <Subject> collectAllSubjectsForView();
	SubjectView collectSubjectForView(Long subjectId);
	
	//audiences
	List<Audience> collectAvailableAudiences(LocalDate date, Long sessionId);
	
	//sessions
	LectureSessions findSessionById(Long sessionId);
	
	// others
	void cancelLecture (Long lectureId);
	FreeItemsView collectFreeItemsInSchedule (Long lectureId);
	
	

	

	

	

	
	
	
	
	
}

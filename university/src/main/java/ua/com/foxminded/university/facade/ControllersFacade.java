package ua.com.foxminded.university.facade;

import java.time.LocalDate;
import java.util.List;

import ua.com.foxminded.university.model.Audience;
import ua.com.foxminded.university.model.Group;
import ua.com.foxminded.university.model.Lecture;
import ua.com.foxminded.university.model.LectureSessions;
import ua.com.foxminded.university.model.Subject;
import ua.com.foxminded.university.model.Teacher;
import ua.com.foxminded.university.model.view.*;

public interface ControllersFacade {

    StudentsView collectAllStudentsForView();
    
    StudentView collectStudentForView(Long id);
    
    List<LecturesView> collectLecturesByDateRange(LocalDate start, LocalDate end);
    
    LecturesGroup collectLecturesForGroupByDateRange(LocalDate start, LocalDate end, Long groupId);

    LecturesTeacher collectLecturesForTeacherByDateRange (LocalDate start, LocalDate end, Long teacherId);
    
    LecturesSubject collectLecturesForSubjectByDateRange (LocalDate start, LocalDate end, Long subjectId);
    
    LecturesAudience collectLecturesForAudienceByDateRange (LocalDate start, LocalDate end, Long audienceId);
    
    Lecture findLectureById (Long lectureId);
    
    Group findGroupById (Long groupId);
    
    List<Teacher> collectAllTeachersForList();
    
    Teacher collectTeacherForView(Long teacherId);
    
    List <Subject> collectAllSubjectsForView();

	SubjectView collectSubjectForView(Long subjectId);
	
	List <Group> collectAllGroupsForList();
	
	void cancelLecture (Long lectureId);
	
	FreeItemsView collectFreeItemsInSchedule (Long lectureId);
	
	LectureSessions findSessionById(Long sessionId);

	List<Audience> collectAvailableAudiences(LocalDate date, Long sessionId);

	Lecture saveRescheduleLecture(Lecture lecture);

	
	
	
	
	
}

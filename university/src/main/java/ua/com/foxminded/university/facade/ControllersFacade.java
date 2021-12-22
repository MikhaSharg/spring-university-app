package ua.com.foxminded.university.facade;

import java.time.LocalDate;
import java.util.List;

import ua.com.foxminded.university.model.Group;
import ua.com.foxminded.university.model.Lecture;
import ua.com.foxminded.university.model.Subject;
import ua.com.foxminded.university.model.Teacher;
import ua.com.foxminded.university.model.view.*;

public interface ControllersFacade {

    StudentsView collectAllStudentsForView();
    
    StudentView collectStudentForView(Long id);
    
    LecturesView collectLecturesForCurrentDate();
    
    List<LecturesView> collectLecturesForDateRange(LocalDate start, LocalDate end);
    
    List<LecturesView> collectLecturesForGroupByDateRange(LocalDate start, LocalDate end, Long groupId);

    LecturesTeacher collectLecturesForTeacherByDateRange (LocalDate start, LocalDate end, Long teacherId);
    
    LecturesSubject collectLecturesForSubjectByDateRange (LocalDate start, LocalDate end, Long subjectId);
    
    LecturesAudience collectLecturesForAudienceByDateRange (LocalDate start, LocalDate end, Long audienceId);
    
    Lecture findStudentById (Long studentId);
    
    Group findGroupById (Long groupId);
    
    List<Teacher> collectAllTeachersForList();
    
    Teacher collectTeacherForView(Long teacherId);
    
    List <Subject> collectAllSubjectsForView();

	SubjectView collectSubjectForView(Long subjectId);
	
}

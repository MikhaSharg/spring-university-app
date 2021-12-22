package ua.com.foxminded.university.dao;

import java.time.LocalDate;
import java.util.List;

import ua.com.foxminded.university.model.Group;
import ua.com.foxminded.university.model.Lecture;

public interface LectureDao extends CrudDao<Lecture, Long> {

	List<Lecture> findLecturesOneDate(LocalDate date);

	List<Lecture> findLectureForDateRange(LocalDate startDate, LocalDate endDate);
	
	List<Lecture> findLecturesForGroupByDate (Long id, LocalDate date);
	
	List<Lecture> findLecturesForTeacherByDate (Long teacherId, LocalDate date);
	
}

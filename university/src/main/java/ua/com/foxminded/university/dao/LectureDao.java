package ua.com.foxminded.university.dao;

import java.time.LocalDate;
import java.util.List;

import ua.com.foxminded.university.model.Lecture;

public interface LectureDao extends CrudDao<Lecture, Long> {

	List<Lecture> findLectureForOneDate(LocalDate date);

	List<Lecture> findLectureForDateRange(LocalDate startDate, LocalDate endDate);

}

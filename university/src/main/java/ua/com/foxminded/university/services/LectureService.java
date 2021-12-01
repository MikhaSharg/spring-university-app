package ua.com.foxminded.university.services;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;

import ua.com.foxminded.university.dao.LectureDao;
import ua.com.foxminded.university.model.Lecture;

@Service
public class LectureService {

	private final LectureDao lectureDao;

	public LectureService(LectureDao lectureDao) {
		super();
		this.lectureDao = lectureDao;
	}

	public List<Lecture> findLecturesForCurrentDay() {
		LocalDate currentDay = LocalDate.now();
		return lectureDao.findLecturesOneDate(currentDay);
	}

	public List<Lecture> findLectureForDateRange(LocalDate startDate, LocalDate endDate) {
		return lectureDao.findLectureForDateRange(startDate, endDate);
	}
	
	public List<Lecture> findLectureForGroupByDate (Long groupId, LocalDate date) {
		return lectureDao.findLecturesForGroupByDate(groupId, date);
		
	}
	
	public List<Lecture> findLectureForTeacherByDate (Long teacherId, LocalDate date) {
		return lectureDao.findLecturesForTeacherByDate(teacherId, date);
		
	}

	public Lecture saveLecture(Lecture lecture) {
		return lectureDao.save(lecture);
	}

	public void deleteLectureById(Long id) {
		lectureDao.deleteById(id);
	}

}

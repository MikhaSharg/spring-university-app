package ua.com.foxminded.university.services;

import java.time.LocalDate;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ua.com.foxminded.university.dao.LectureDao;
import ua.com.foxminded.university.misc.DataGenerator;
import ua.com.foxminded.university.model.Lecture;

@Service
@Transactional(readOnly = true)
public class LectureService {

	private final LectureDao lectureDao;

	private static final Logger log = LoggerFactory.getLogger(LectureService.class);

	public LectureService(LectureDao lectureDao) {
		this.lectureDao = lectureDao;
	}

	public List<Lecture> findLecturesForCurrentDay() {
		LocalDate currentDay = LocalDate.now();
		List<Lecture> lectures = lectureDao.findLecturesOneDate(currentDay);
		if (!lectures.isEmpty()) {
			log.info("Finded {} lectures on current day {}", lectures.size(), currentDay);
		} else {
			log.warn("Could not find any lectures on current day {}", currentDay);
		}
		return lectures;
	}

	public List<Lecture> findLectureForDateRange(LocalDate startDate, LocalDate endDate) {

		List<Lecture> lectures = lectureDao.findLectureForDateRange(startDate, endDate);
		if (!lectures.isEmpty()) {
			log.info("Finded {} lectures for range from {} to {}", lectures.size(), startDate, endDate);
		} else {
			log.warn("Could not find lectures for range from {} to {}", startDate, endDate);
		}
		return lectures;
	}

	public List<Lecture> findLectureForGroupByDate(Long groupId, LocalDate date) {
		List<Lecture> lectures = lectureDao.findLecturesForGroupByDate(groupId, date);
		if (!lectures.isEmpty()) {
			log.info("Finded {} lectures for group {} on date {}", lectures.size(), groupId, date);
		} else {
			log.warn("Could not find any lectures for group {} on date {}", groupId, date);
		}

		return lectures;

	}

	public List<Lecture> findLectureForTeacherByDate(Long teacherId, LocalDate date) {
		List<Lecture> lectures = lectureDao.findLecturesForTeacherByDate(teacherId, date);
		if (!lectures.isEmpty()) {
			log.info("Finded {} lectures for teacher ID {} on date {}", lectures.size(), teacherId, date);
		} else {
			log.warn("Could not find any lectures for teacher ID {} on date {}", teacherId, date);
		}
		return lectures;

	}

	public Lecture saveLecture(Lecture lecture) {
		Lecture savedLecture = lectureDao.save(lecture);
		if (lecture.getId() != null) {
			log.info("Saved lecture with ID{}", lecture.getId());
		} else {
			log.info("Updated lecture with ID {}", lecture.getId());
		}
		return savedLecture;
	}

	public void deleteLectureById(Long id) {

		lectureDao.deleteById(id);
		log.info("Deleted lecture with ID {}", id);
	}

}

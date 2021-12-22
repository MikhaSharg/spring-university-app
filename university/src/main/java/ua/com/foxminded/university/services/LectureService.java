package ua.com.foxminded.university.services;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ua.com.foxminded.university.dao.LectureDao;
import ua.com.foxminded.university.misc.DataGenerator;
import ua.com.foxminded.university.model.Lecture;
import ua.com.foxminded.university.model.Student;

@Service
@Transactional(readOnly = true)
public class LectureService {

	private final LectureDao lectureDao;

	private static final Logger log = LoggerFactory.getLogger(LectureService.class);

//	LocalDate currentDay = LocalDate.now(); to do
	LocalDate currentDay = LocalDate.of(2021, 12, 10);
	
	public LectureService(LectureDao lectureDao) {
		this.lectureDao = lectureDao;
	}

	public List<Lecture> findLecturesForCurrentDay() {
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
	
	public List<Lecture> findLectureForGroupForCurrentDay(Long groupId) {
		List<Lecture> lectures = lectureDao.findLecturesForGroupByDate(groupId, currentDay);
		if (!lectures.isEmpty()) {
			log.info("Finded {} lectures for group {} on date {}", lectures.size(), groupId, currentDay);
		} else {
			log.warn("Could not find any lectures for group {} on date {}", groupId, currentDay);
		}
		return lectures;
}
	
	public List<Lecture> findAllLecturesForCurrentDay() {
		
		List<Lecture> lectures = lectureDao.findLecturesOneDate(currentDay);
		if (!lectures.isEmpty()) {
			log.info("Finded {} lectures for on date {}", lectures.size(), currentDay);
		} else {
			log.warn("Could not find any lectures on date {}", currentDay);
		}
		return lectures;
}
	
	public Lecture findLectureById (Long lectureId) {
		Optional<Lecture> lecture = lectureDao.findById(lectureId);
		if (lecture.isPresent()) {
			log.info("Finded lecture id: {}, date: {}, subject: {}", lecture.get().getId(), lecture.get().getDate(),
					lecture.get().getSubject().getName());
		} else {
			log.warn("Could not find subject with ID {}", lectureId);
		}
		return lecture.get();
	}
	
}

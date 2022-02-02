package ua.com.foxminded.university.services;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ua.com.foxminded.university.dao.LectureDao;
import ua.com.foxminded.university.dao.LectureSessionsDao;
import ua.com.foxminded.university.misc.DateGenerationUtils;
import ua.com.foxminded.university.misc.GeneratorConfig;
import ua.com.foxminded.university.misc.Status;
import ua.com.foxminded.university.model.Lecture;
import ua.com.foxminded.university.model.LectureSessions;
import ua.com.foxminded.university.model.FreeItem;

@Service
@Transactional
public class LectureService {

	private final LectureDao lectureDao;
	private final LectureSessionsDao lectureSessionsDao;
	private final GeneratorConfig config;

	private static final Logger log = LoggerFactory.getLogger(LectureService.class);
	public static final String TEACHER = "teacher";
	public static final String GROUP = "group";

	LocalDate currentDay = LocalDate.now();

	public LectureService(LectureDao lectureDao, LectureSessionsDao lectureSessionsDao, GeneratorConfig config) {
		super();
		this.lectureDao = lectureDao;
		this.lectureSessionsDao = lectureSessionsDao;
		this.config = config;
	}

	public List<Lecture> findAllLecturesForDateRange(LocalDate startDate, LocalDate endDate) {
		List<Lecture> lectures = lectureDao.findLectureForDateRange(startDate, endDate);
		pointModifiedLectures(lectures);
		addArchivedLecturesToListForDateRange(lectures, startDate, endDate);
		if (!lectures.isEmpty()) {
			log.info("Found {} lectures for range from {} to {}", lectures.size(), startDate, endDate);
		} else {
			log.warn("Could not find lectures for range from {} to {}", startDate, endDate);
		}
		return lectures;
	}

	private void pointModifiedLectures(List<Lecture> lectures) {
		Map<Long, String> modifiedLectures = lectureDao.findModifiedLectures();
		if (!modifiedLectures.isEmpty()) {
			lectures.stream().forEach(lecture -> {
				if (modifiedLectures.containsKey(lecture.getId())) {
					lecture.setModified(true);
					lecture.setStatus(modifiedLectures.get(lecture.getId()));
				}
			});
		}
	}

	public List<Lecture> findLectureForGroupByDate(Long groupId, LocalDate date) {
		List<Lecture> lectures = lectureDao.findLecturesForGroupByDate(groupId, date);
		addArchivedLecturesToListForDateRange(lectures, date, date);
		if (!lectures.isEmpty()) {
			log.info("Found {} lectures for group {} on date {}", lectures.size(), groupId, date);
		} else {
			log.warn("Could not find any lectures for group {} on date {}", groupId, date);
		}
		return lectures;
	}

	public List<Lecture> findLectureForTeacherByDate(Long teacherId, LocalDate date) {
		List<Lecture> lectures = lectureDao.findLecturesForTeacherByDate(teacherId, date);
		addArchivedLecturesToListForDateRange(lectures, date, date);
		if (!lectures.isEmpty()) {
			log.info("Found {} lectures for teacher ID {} on date {}", lectures.size(), teacherId, date);
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

	public Lecture findLectureById(Long lectureId) {
		Optional<Lecture> lecture = lectureDao.findById(lectureId);
		if (lecture.isPresent()) {
			log.info("Finded lecture id: {}, date: {}, subject: {}", lecture.get().getId(), lecture.get().getDate(),
					lecture.get().getSubject().getName());
		} else {
			log.warn("Could not find subject with ID {}", lectureId);
		}
		return lecture.get();
	}

	public void cancelLecture(Long lectureId) {
		Lecture lecture = this.findLectureById(lectureId);
		lecture.setStatus(Status.CANCELED);
		lectureDao.archiveLecture(lecture);
		log.info("Lecture ID {} was archived with status {}", lectureId, lecture.getStatus());
		lectureDao.deleteById(lectureId);
		log.info("Lecture ID {} canceled (deleted)", lectureId);
	}

	public List<FreeItem> findAllFreeItemsInSchedule(Long teacherId, Long groupId) {
		List<Lecture> lectures = lectureDao.findLecturesByTeacherAndGroupId(teacherId, groupId);
		log.info("Found {} lectures for Teacher ID {} and Group ID {}", lectures.size(), teacherId, groupId);
		List<LocalDate> allStudyDays = DateGenerationUtils.generateStudyDates(config.getHolidays(), config.getStartDate(), config.getEndDate());
		List<LocalDate> studyDays = allStudyDays.stream().filter(e->e.getDayOfYear() >= LocalDate.now().getDayOfYear()).collect(Collectors.toList());
		log.info("Found {} days in current year", studyDays.size());
		List<FreeItem> freeItems = new ArrayList<>();
		List<LectureSessions> sessions = lectureSessionsDao.findAll();
		List<Lecture> archivedLectures = lectureDao.findArchivedLectures(teacherId, groupId);
		List<Lecture> actualArchivedLectures = archivedLectures.stream().filter(e->e.getDate().getDayOfYear() >= LocalDate.now().getDayOfYear()).collect(Collectors.toList());
		if (!actualArchivedLectures.isEmpty()) {
			actualArchivedLectures.stream().forEach(lecture -> {
				freeItems.add(new FreeItem(lecture.getDate(), lecture.getSession().getId(), lecture.getId()));
			});
		}
		Map<LocalDate, List<Lecture>> groupingLecturesByDate = lectures.stream()
				.collect(Collectors.groupingBy(e -> e.getDate()));
		int dateCount = 0;
		while (dateCount < studyDays.size()) {
			LocalDate date = studyDays.get(dateCount);
			if (groupingLecturesByDate.containsKey(date)) {

				sessions.stream().forEach(session -> {
					Map<String, Boolean> isFree = new HashMap<>();
					Optional<Lecture> teacherLecture = groupingLecturesByDate.get(date).stream()
							.filter(e -> e.getSession().equals(session) && e.getTeacher().getId().equals(teacherId))
							.findAny();
					Optional<Lecture> groupLecture = groupingLecturesByDate.get(date).stream()
							.filter(e -> e.getSession().equals(session) && e.getGroup().getId().equals(groupId))
							.findAny();
					if (teacherLecture.isEmpty()) {
						isFree.put(TEACHER, true);
					}
					if (groupLecture.isEmpty()) {
						isFree.put(GROUP, true);
					}
					if (isFree.get(GROUP) != null && isFree.get(GROUP) == true && isFree.get(TEACHER) != null
							&& isFree.get(TEACHER) == true) {
						if (freeItems.stream()
								.filter(e -> e.getDate().equals(date) && e.getSessionId().equals(session.getId()))
								.findAny().isEmpty()) {
							freeItems.add(new FreeItem(date, session.getId()));
						}
					}
				});
				dateCount++;
			} else {
				sessions.stream().forEach(session -> {
					freeItems.add(new FreeItem(date, session.getId()));
				});
				dateCount++;
			}
		}
		Collections.sort(freeItems, (i1, i2) -> i1.getDate().compareTo(i2.getDate()));
		log.info("Found {} free dates for reschedule lecture", freeItems.size());
		return freeItems;
	}

	public Lecture saveRescheduleLecture(Lecture lecture) {
		if (lecture.getId() != null) {
			lectureDao.deleteArchivedLecture(lecture.getId());
			lecture.setId(null);
		}
		Lecture savedLecture = lectureDao.save(lecture);
		log.info("Reschedule lecture ID {} was saved", savedLecture.getId());

		Lecture lectureForArchive = lectureDao.findById(lecture.getOldLectureId()).orElseThrow();
		lectureForArchive.setStatus(lecture.getStatus());
		lectureForArchive.setNewLectureId(savedLecture.getId());
		lectureDao.archiveLecture(lectureForArchive);
		log.info("Old position ID {} of rescheduled lecture {}  was archived", lecture.getOldLectureId(),
				savedLecture.getId());

		lectureDao.deleteById(lecture.getOldLectureId());
		log.info("Old position ID {} of rescheduled lecture {}  was deleted", lecture.getOldLectureId(),
				savedLecture.getId());
		return savedLecture;
	}

	private void addArchivedLecturesToListForDateRange(List<Lecture> lectures, LocalDate startDate, LocalDate endDate) {
		List<Lecture> archivedLectures = lectureDao.findArchivedLecturesForDateRange(startDate, endDate);
		if (!archivedLectures.isEmpty()) {
			lectures.addAll(archivedLectures);
			lectures.sort((Lecture lecture1, Lecture lecture2) -> lecture1.getId().compareTo(lecture2.getId()));
		}
	}
}

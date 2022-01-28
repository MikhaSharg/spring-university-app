package ua.com.foxminded.university.dao;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import ua.com.foxminded.university.model.Group;
import ua.com.foxminded.university.model.Lecture;
import ua.com.foxminded.university.model.view.LecturesGroup;
import ua.com.foxminded.university.model.ArchivedLecture;

public interface LectureDao extends CrudDao<Lecture, Long> {

	List<Lecture> findLecturesOneDate(LocalDate date);

	List<Lecture> findLectureForDateRange(LocalDate startDate, LocalDate endDate);

	List<Lecture> findLecturesForGroupByDate(Long id, LocalDate date);

	List<Lecture> findLecturesForTeacherByDate(Long teacherId, LocalDate date);

	List<Lecture> findLecturesByTeacherAndGroupId(Long teacherId, Long groupId);

	void archiveLecture(Lecture lecture);

	List<Lecture> findArchivedLectures();

	void updateStatusLectureInArchive(Lecture lecture);

	List<Lecture> findArchivedLecturesForDateRange(LocalDate startDate, LocalDate endDate);

	List<Lecture> findArchivedLectures(Long teacherId, Long groupId);

	void deleteArchivedLecture(Long id);

	Map<Long, String> findModifiedLectures();

}

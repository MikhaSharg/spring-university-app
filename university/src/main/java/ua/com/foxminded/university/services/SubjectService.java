package ua.com.foxminded.university.services;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ua.com.foxminded.university.dao.SubjectDao;
import ua.com.foxminded.university.model.Subject;

@Service
@Transactional
public class SubjectService {

	private final SubjectDao subjectDao;

	private static final Logger log = LoggerFactory.getLogger(LectureService.class);

	public SubjectService(SubjectDao subjectDao) {
		super();
		this.subjectDao = subjectDao;
	}

	public Subject findSubjectById(Long id) {
		Optional<Subject> subject = subjectDao.findById(id);
		if (subject.isPresent()) {
			log.info("Found subject {}, {}", subject.get().getId(), subject.get().getName());
		} else {
			log.warn("Could not find subject with ID {}", id);
		}
		return subject.get();
	}

	public List<Subject> findAllExistSubjects() {
		List<Subject> subjects = subjectDao.findAll();
		List<Long> subjectIdsWithTeachers = subjectDao.findSubjectsWithTeachers();
		subjects.stream().forEach(sub -> {
			if (subjectIdsWithTeachers.contains(sub.getId()) == false) {
				sub.setIsWithoutTeacher(true);
			}
		});
		if (!subjects.isEmpty()) {
			log.info("Found {} subjects", subjects.size());
		} else {
			log.warn("Could not find any subjects");
		}
		return subjects;
	}

	public Subject saveSubject(Subject subject) {
		Subject savedSubject = subjectDao.save(subject);
		if (subject != null) {
			log.info("Subject ID = {} name = {} was updated", subject.getId(), savedSubject.getName());
		} else {
			log.info("New Subject ID = {} name = {} was saved", savedSubject.getId(), savedSubject.getName());
		}
		return savedSubject;
	}

	public void enrolleSubjectToTeacher(Long teacherId, Long subjectId) {
		subjectDao.addSubjectToTeacher(teacherId, subjectId);
		log.info("Subject ID {} was enrolled to Teacher ID {}", subjectId, teacherId);
	}

	public void unenrollSubjectFromTeacher(Long teacherId, Long subjectId) {
		subjectDao.deleteSubjectFromTeacher(teacherId, subjectId);
		log.info("Subject ID {} was unenrolled from Teacher ID {}", subjectId, teacherId);
	}

	public void deleteSubject(Long subjectId) {
		subjectDao.deleteById(subjectId);
		log.info("Subject ID {} was deleted from DB", subjectId);
	}
}

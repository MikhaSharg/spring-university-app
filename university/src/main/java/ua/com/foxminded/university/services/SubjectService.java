package ua.com.foxminded.university.services;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ua.com.foxminded.university.dao.SubjectDao;
import ua.com.foxminded.university.model.Student;
import ua.com.foxminded.university.model.Subject;

@Service
@Transactional(readOnly = true)
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
			log.info("Finded subject {}, {}", subject.get().getId(), subject.get().getName());
		} else {
			log.warn("Could not find subject with ID {}", id);
		}
		return subject.get();
	}

	public List<Subject> findAllExistSubjects() {
		List<Subject> subjects = subjectDao.findAll();
		if (!subjects.isEmpty()) {
			log.info("Finded {} subjects", subjects.size());
		} else {
			log.warn("Could not find any subjects");
		}
		return subjects;
	}

}

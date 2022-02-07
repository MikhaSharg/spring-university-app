package ua.com.foxminded.university.services;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ua.com.foxminded.university.dao.TeacherDao;
import ua.com.foxminded.university.model.Teacher;

@Service
@Transactional
public class TeacherService {

	private final TeacherDao teachertDao;
	private static final Logger log = LoggerFactory.getLogger(TeacherService.class);
	private static final String FIRED = "fired_teacher";

	public TeacherService(TeacherDao teachertDao) {
		this.teachertDao = teachertDao;
	}

	public Teacher saveTeacher(Teacher newTeacher) {
		Teacher teacher = teachertDao.save(newTeacher);
		if (newTeacher.getId() == null) {
			log.info("Saved teacher {}, {}, {}", teacher.getId(), teacher.getFirstName(), teacher.getLastName());
		} else {
			log.info("Updated teacher {}, {}, {}", teacher.getId(), teacher.getFirstName(), teacher.getLastName());
		}
		return teacher;
	}

	public void deleteTeacherById(Long id) {
		teachertDao.deleteById(id);
		log.info("Deleted teacher with ID {}", id);
	}

	public List<Teacher> findAllExistTeachers() {
		List<Teacher> teachers = teachertDao.findAll();
		if (!teachers.isEmpty()) {
			log.info("Found {} teachers", teachers.size());
		} else {
			log.warn("Could not find any teachers");
		}
		return teachers;
	}

	public Teacher findTeacherById(Long teacherId) {
		Optional<Teacher> teacher = teachertDao.findById(teacherId);
		if (teacher.isPresent()) {
			log.info("Found teacher {}, {}, {}", teacher.get().getId(), teacher.get().getFirstName(),
					teacher.get().getLastName());
		} else {
			log.warn("Could not find teacher with ID {}", teacherId);
		}
		return teacher.get();
	}

	public List<Teacher> findAllTeachersBySubjectId(Long subjectId) {
		List<Teacher> teachers = teachertDao.findAllTeachersBySubjectId(subjectId);
		if (!teachers.isEmpty()) {
			log.info("Found {} teachers for subject_id {}", teachers.size(), subjectId);
		} else {
			log.warn("Could not find any teachers for subject_id {}", subjectId);
		}

		return teachers;

	}

	public void fireTeacher(Long id) {
		Teacher teacher = this.findTeacherById(id);
		teacher.setRole(FIRED);
		this.saveTeacher(teacher);
		log.info("Teacher {} {} was fired", teacher.getId(), teacher.toString());

	}

}

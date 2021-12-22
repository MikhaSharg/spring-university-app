package ua.com.foxminded.university.services;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ua.com.foxminded.university.dao.StudentDao;
import ua.com.foxminded.university.model.Student;

@Service
@Transactional(readOnly = true)
public class StudentService {

	private final StudentDao studentDao;

	private static final Logger log = LoggerFactory.getLogger(LectureService.class);

	public StudentService(StudentDao studentDao) {
		this.studentDao = studentDao;
	}

	Student saveStudent(Student newStudent) {
		Student student = studentDao.save(newStudent);
		if (newStudent.getId() != null) {
			log.info("Updated student {}, {}, {}", student.getId(), student.getFirstName(), student.getLastName());
		} else {
			log.info("Saved student {}, {}, {}", student.getId(), student.getFirstName(), student.getLastName());
		}
		return student;
	}

	public void deleteStudentById(Long id) {
		studentDao.deleteById(id);
		log.info("Deleted student with ID {}", id);
	}

	public List<Student> findAllStudentsByGroupId(Long groupId) {

		List<Student> students = studentDao.findAllStudentsByGroupId(groupId);
		if (!students.isEmpty()) {
			log.info("Finded {} students for group ID {}", students.size(), groupId);
		} else {
			log.warn("Could not find any students for group ID {}", groupId);
		}
		return students;
	}

	public List<Student> findAllExistStudents() {

		List<Student> students = studentDao.findAll();
		if (!students.isEmpty()) {
			log.info("Finded {} students", students.size());
		} else {
			log.warn("Could not find any students");
		}
		return students;
	}

	public Student findStudentById(Long studentId) {
		Optional<Student> student = studentDao.findById(studentId);
		if (student.isPresent()) {
			log.info("Finded student {}, {}, {}", student.get().getId(), student.get().getFirstName(),
					student.get().getLastName());
		} else {
			log.warn("Could not find student with ID {}", student);
		}
		return student.get();
	}

	public void setGroupToStudent(Long groupId, Long studentId) {
		studentDao.setGroupToStudent(groupId, studentId);
		log.info("Set group with ID {} for Student ID {}", groupId, studentId);
	}
}

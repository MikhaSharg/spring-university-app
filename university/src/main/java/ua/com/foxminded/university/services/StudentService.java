package ua.com.foxminded.university.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import ua.com.foxminded.university.dao.StudentDao;
import ua.com.foxminded.university.model.Student;

@Service
public class StudentService {

	private final StudentDao studentDao;

	public StudentService(StudentDao studentDao) {
		this.studentDao = studentDao;
	}

	Student saveStudent(Student newStudent) {
			return studentDao.save(newStudent);
		}

	public void deleteStudentById(Long id) {
		studentDao.deleteById(id);
	}

	public List<Student> findAllStudentsByGroupId (Long groupId) {
		return studentDao.findAllStudentsByGroupId(groupId);
	}
	
	public List<Student> findAllExistStudents () {
		return studentDao.findAll();
	}
	
	public Optional <Student> findStudentById (Long studentId) {
		return studentDao.findById(studentId);
	}
	
	public void setGroupToStudent (Long groupId, Long studentId) {
		studentDao.setGroupToStudent(groupId, studentId);
	}
}

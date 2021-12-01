package ua.com.foxminded.university.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import ua.com.foxminded.university.dao.TeacherDao;
import ua.com.foxminded.university.model.Teacher;

@Service
public class TeacherService {

	private final TeacherDao teachertDao;

	public TeacherService(TeacherDao teachertDao) {
		this.teachertDao = teachertDao;
	}

	Teacher saveStudent(Teacher newTeacher) {
		return teachertDao.save(newTeacher);
	}

	public void deleteTeacherById(Long id) {
		teachertDao.deleteById(id);
	}

	public List<Teacher> findAllExistTeachers() {
		return teachertDao.findAll();
	}

	public Optional<Teacher> findTeacherById(Long teacherId) {
		return teachertDao.findById(teacherId);
	}

	public List<Teacher> saveAllTeachers(List<Teacher> teachers) {
		return teachertDao.saveAll(teachers);
	}

}

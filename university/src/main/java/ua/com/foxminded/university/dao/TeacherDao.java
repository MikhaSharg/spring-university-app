package ua.com.foxminded.university.dao;

import java.util.List;

import ua.com.foxminded.university.model.Teacher;

public interface TeacherDao extends CrudDao<Teacher, Long> {
	
	List<Teacher> findAllTeachersBySubjectId (Long subjectId);
}

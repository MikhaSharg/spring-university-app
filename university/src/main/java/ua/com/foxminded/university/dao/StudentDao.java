package ua.com.foxminded.university.dao;

import java.util.List;

import ua.com.foxminded.university.model.Student;

public interface StudentDao extends CrudDao<Student, Long> {
    
    public List<Student> findAllStudentsByGroupId(Long groupId);

}

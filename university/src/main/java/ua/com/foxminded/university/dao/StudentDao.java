package ua.com.foxminded.university.dao;

import java.util.List;

import ua.com.foxminded.university.model.Student;

public interface StudentDao extends CrudDao<Student, Long> {
    
    public List<Student> findAllStudentsByGroupId(Long groupId);
    
    public List<Student> findExistingStudents (Student student);
    
    public void setGroupToStudent (Long groupId, Long studentId);

}

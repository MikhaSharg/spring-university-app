package ua.com.foxminded.university.dao;

import java.util.List;

import ua.com.foxminded.university.model.Subject;

public interface SubjectDao extends CrudDao<Subject, Long> {
	
	List<Subject> findAllSubjectsByTeacherId (Long teacherId);
	
	public void addSubjectToTeacher (Long teacherId, Long subjectId);
	
	public void deleteSubjectFromTeacher (Long teacherId, Long subjectId);
	
	public List<Long> findArchivedSubjectIds();

	List<Long> findSubjectsWithTeachers(); 

}

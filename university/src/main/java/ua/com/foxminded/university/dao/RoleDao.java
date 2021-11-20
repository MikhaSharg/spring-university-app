package ua.com.foxminded.university.dao;

import java.util.List;

import ua.com.foxminded.university.model.Role;

public interface RoleDao extends CrudDao<Role, Long> {

	public void addRoleToStudent(Long student_id, Long role_id);

	public void deleteRoleFromStudent(Long student_id, Long role_id);

	public List<Role> findRolesForStudent(Long student_id);

	public void addRoleToTeacher(Long teacher_id, Long role_id);

	public void deleteRoleFromTeacher(Long teacher_id, Long role_id);

	public List<Role> findRolesForTeacher(Long teacher_id);

}

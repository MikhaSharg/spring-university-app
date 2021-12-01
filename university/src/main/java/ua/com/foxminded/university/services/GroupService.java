package ua.com.foxminded.university.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ua.com.foxminded.university.dao.GroupDao;
import ua.com.foxminded.university.model.Group;

@Service
@Transactional (readOnly=true)
public class GroupService {

	private final GroupDao groupDao;

	public GroupService(GroupDao groupDao) {
		this.groupDao = groupDao;
	}

	Group saveGroup(Group newGroup) {
		return groupDao.save(newGroup);
	}

	public void deleteStudentById(Long id) {
		groupDao.deleteById(id);
	}

	public List<Group> findAllExistGroups() {
		return groupDao.findAll();
	}

	public Optional<Group> findGroupsById(Long studentId) {
		return groupDao.findById(studentId);
	}

}

package ua.com.foxminded.university.services;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ua.com.foxminded.university.dao.GroupDao;
import ua.com.foxminded.university.misc.DataGenerator;
import ua.com.foxminded.university.model.Group;

@Service
@Transactional(readOnly = true)
public class GroupService {

	private static final Logger log = LoggerFactory.getLogger(GroupService.class);
	private final GroupDao groupDao;

	public GroupService(GroupDao groupDao) {
		this.groupDao = groupDao;
	}

	Group saveGroup(Group newGroup) {
		Group group = groupDao.save(newGroup);
		if (newGroup.getId() == null) {
			log.info("Saved group {}", newGroup.getName());
		} else {
			log.info("Updated group {}", newGroup.getName());
		}
		return group;
	}

	public void deleteGroupById(Long id) {
		groupDao.deleteById(id);
		log.info("Deleted group with ID {} ", id);
	}

	public List<Group> findAllExistGroups() {
		List<Group> groups = groupDao.findAll();
		log.info("Finded {} groups", findAllExistGroups().size());
		return groups;
	}

	public Optional<Group> findGroupsById(Long studentId) {
		Optional<Group> group = groupDao.findById(studentId);
		if (!group.isEmpty()) {
			log.info("Finded group {}", findAllExistGroups().size());
		} else {
			log.warn("Could not find group {}", findAllExistGroups().size());
		}
		return groupDao.findById(studentId);
	}

}

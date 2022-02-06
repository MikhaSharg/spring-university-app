package ua.com.foxminded.university.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ua.com.foxminded.university.dao.GroupDao;
import ua.com.foxminded.university.misc.GeneratorConfig;
import ua.com.foxminded.university.model.Group;
import ua.com.foxminded.university.model.Student;

@Service
@Transactional
public class GroupService {

	private static final Logger log = LoggerFactory.getLogger(GroupService.class);
	private final GroupDao groupDao;
	private final StudentService studentService;
	private final GeneratorConfig config;

	public GroupService(GroupDao groupDao, StudentService studentService, GeneratorConfig config) {
		this.groupDao = groupDao;
		this.studentService = studentService;
		this.config = config;
	}

	public Group saveGroup(Group newGroup) {
		Group group = groupDao.save(newGroup);
		if (newGroup.getId() == null) {
			log.info("Saved group {}", group.getName());
		} else {
			log.info("Updated group {}", group.getName());
		}
		return group;
	}

	public void deleteGroupById(Long id) {
		groupDao.deleteById(id);
		log.info("Deleted group with ID {} ", id);
	}

	public List<Group> findAllExistGroups() {
		List<Group> groups = groupDao.findAll();
		groups.stream().forEach(group -> {
			List<Student> students = studentService.findAllStudentsByGroupId(group.getId());
			group.getStudents().addAll(students);
		});

		if (!groups.isEmpty()) {
			log.info("Found {} groups", groups.size());
		} else {
			log.warn("Could not find any groups");
		}

		return groups;
	}

	public Group findGroupById(Long groupId) {
		Optional<Group> group = groupDao.findById(groupId);
		if (!group.isEmpty()) {
			log.info("Found group with Id {}", groupId);
		} else {
			log.warn("Could not find group {}", group);
		}
		return group.get();
	}

	public List<Group> findAllNotFullGroups() {
		List<Group> allGroups = this.findAllExistGroups();
		int maxSizeStudentInOneGroup = config.getGroupsMaxStudenets();
		List<Group> notFullGroups = allGroups.stream().filter(gr -> gr.getStudents().size() < maxSizeStudentInOneGroup)
				.collect(Collectors.toList());
		if (!notFullGroups.isEmpty()) {
			log.info("Found {} not full groups. Max count of students in one group is {}", notFullGroups.size(),
					config.getGroupsMaxStudenets());
		} else {
			log.warn("All groups are full");
		}
		return notFullGroups;
	}
}

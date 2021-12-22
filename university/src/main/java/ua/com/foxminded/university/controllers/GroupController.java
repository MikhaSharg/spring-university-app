package ua.com.foxminded.university.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import ua.com.foxminded.university.facade.ControllersFacadeImpl;
import ua.com.foxminded.university.model.Group;
import ua.com.foxminded.university.services.GroupService;

import static ua.com.foxminded.university.controllers.ControllerUtils.*;

import java.util.List;

@Controller
@RequestMapping("/groups")
public class GroupController {
	
	private final ControllersFacadeImpl facade;
	
	private final GroupService groupService;

	public GroupController(ControllersFacadeImpl facade, GroupService groupService) {
		this.facade = facade;
		this.groupService=groupService;
	}

	@GetMapping
	String groupList(Model model) {
		
		List<Group> groups =  groupService.findAllExistGroups();
		model.addAttribute(GROUPS, groups);
		setTitle(model, "groups");
		return "groups/list";
	}

	@GetMapping (path = "/{id}")
	String showGroupView(@PathVariable(name = "id", required = true) Long id, Model model) {
		Group group = facade.findGroupById(id);
		model.addAttribute("group", group);
		return "groups/view";
	}

}

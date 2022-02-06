package ua.com.foxminded.university.controllers;

import static ua.com.foxminded.university.controllers.ControllerUtils.setTitle;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import ua.com.foxminded.university.facade.ControllersFacade;
import ua.com.foxminded.university.model.Group;


@Controller
@RequestMapping("/groups")
public class GroupController {
	
	private final ControllersFacade facade;

	public GroupController(ControllersFacade facade) {
		this.facade = facade;
	}

	@GetMapping
	String showGroupList(Model model) {
		model.addAttribute("groups", facade.collectAllGroupsForList());
		setTitle(model, "Groups");
		return "groups/list";
	}

	@GetMapping (path = "/{id}")
	String showGroupView(@PathVariable(name = "id", required = true) Long id, Model model) {
		Group group = facade.findGroupById(id);
		model.addAttribute("group", group);
		setTitle(model, "Group", group.getName());
		return "groups/view";
	}
	
	@GetMapping(path="/{id}/edit")
	String showGroupEditForm (@PathVariable(name="id") Long id, Model model) {
		model.addAttribute("group", facade.findGroupById(id));
		return "groups/edit";
	}
	
	@PostMapping(path="/{id}/edit")
	String updateGroup (@PathVariable(name="id") Long id, Group group) {
		group.setId(id);
		facade.saveGroup(group);
		return "redirect:/groups/" + id;
	}
	
}

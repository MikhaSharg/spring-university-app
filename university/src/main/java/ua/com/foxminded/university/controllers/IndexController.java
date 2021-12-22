package ua.com.foxminded.university.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import static ua.com.foxminded.university.controllers.ControllerUtils.setTitle;

@Controller
@RequestMapping({ "/", "/index" })
public class IndexController {

	@GetMapping
	public String main(Model model) {
		setTitle(model, "University App");
		return "index";

	}

}

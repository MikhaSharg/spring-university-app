package ua.com.foxminded.university.controllers;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.ui.Model;

public class ControllerUtils {
	
	public static final String PAGE_TITLE = "pageTitle";
	public static final String STUDENTS_VIEW = "studentsView";
	public static final String GROUP = "group";
	public static final String GROUPS = "groups";
	
	private ControllerUtils() {}

	static void setTitle(Model model, String... title) {
		model.addAttribute(PAGE_TITLE, Stream.of(title).collect(Collectors.joining("/")));
	}
	
	
}

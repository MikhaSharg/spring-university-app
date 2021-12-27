package ua.com.foxminded.university.model.view;

import java.util.List;
import java.util.Map;
import java.time.LocalDate;
import ua.com.foxminded.university.model.Lecture;

public class LecturesTeacher {

	private final Long id;
	private final String name;
	private final String profile;
	private final Map<LocalDate, List<Lecture>> lectures;

	public LecturesTeacher(Long id, String name, String profile, Map<LocalDate, List<Lecture>> lectures) {
		super();
		this.id = id;
		this.name = name;
		this.profile = profile;
		this.lectures = lectures;
	}

	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getProfile() {
		return profile;
	}

	public Map<LocalDate, List<Lecture>> getLectures() {
		return lectures;
	}

	@Override
	public String toString() {
		return "LecturesTeacher [id=" + id + ", name=" + name + ", profile=" + profile + ", lectures=" + lectures + "]";
	}

}

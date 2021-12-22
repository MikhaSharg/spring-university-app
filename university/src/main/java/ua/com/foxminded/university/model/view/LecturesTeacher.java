package ua.com.foxminded.university.model.view;

import java.util.List;
import java.util.Map;
import java.time.LocalDate;
import ua.com.foxminded.university.model.Lecture;

public class LecturesTeacher {
	
	private Long id;
	private String name;
	private String profile;
	private Map<LocalDate, List<Lecture>> lectures;
	
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

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getProfile() {
		return profile;
	}

	public void setProfile(String profile) {
		this.profile = profile;
	}

	public Map<LocalDate, List<Lecture>> getLectures() {
		return lectures;
	}

	public void setLectures(Map<LocalDate, List<Lecture>> lectures) {
		this.lectures = lectures;
	}

	@Override
	public String toString() {
		return "LecturesTeacher [id=" + id + ", name=" + name + ", profile=" + profile + ", lectures=" + lectures + "]";
	}
	
	

}

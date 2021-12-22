package ua.com.foxminded.university.model.view;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import ua.com.foxminded.university.model.Lecture;

public class LecturesSubject {

	private Long id;
	private String name;
	private Map<LocalDate, List<Lecture>> lectures;

	public LecturesSubject(Long id, String name, Map<LocalDate, List<Lecture>> lectures) {
		super();
		this.id = id;
		this.name = name;
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

	public Map<LocalDate, List<Lecture>> getLectures() {
		return lectures;
	}

	public void setLectures(Map<LocalDate, List<Lecture>> lectures) {
		this.lectures = lectures;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, lectures, name);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		LecturesSubject other = (LecturesSubject) obj;
		return Objects.equals(id, other.id) && Objects.equals(lectures, other.lectures)
				&& Objects.equals(name, other.name);
	}

	@Override
	public String toString() {
		return "LecturesSubject [id=" + id + ", name=" + name + ", lectures=" + lectures + "]";
	}

}

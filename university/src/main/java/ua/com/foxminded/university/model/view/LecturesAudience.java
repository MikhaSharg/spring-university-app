package ua.com.foxminded.university.model.view;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import ua.com.foxminded.university.model.Lecture;

public class LecturesAudience {

	private Long id;
	private Integer room;
	private Map<LocalDate, List<Lecture>> lectures;

	public LecturesAudience(Long id, Integer room, Map<LocalDate, List<Lecture>> lectures) {
		super();
		this.id = id;
		this.room = room;
		this.lectures = lectures;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getRoom() {
		return room;
	}

	public void setRoom(Integer room) {
		this.room = room;
	}

	public Map<LocalDate, List<Lecture>> getLectures() {
		return lectures;
	}

	public void setLectures(Map<LocalDate, List<Lecture>> lectures) {
		this.lectures = lectures;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, lectures, room);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		LecturesAudience other = (LecturesAudience) obj;
		return Objects.equals(id, other.id) && Objects.equals(lectures, other.lectures)
				&& Objects.equals(room, other.room);
	}

	@Override
	public String toString() {
		return "LecturesAudience [id=" + id + ", room=" + room + ", lectures=" + lectures + "]";
	}

}

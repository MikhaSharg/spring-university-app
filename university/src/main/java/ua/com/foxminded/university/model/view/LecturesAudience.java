package ua.com.foxminded.university.model.view;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import ua.com.foxminded.university.model.Lecture;

public class LecturesAudience {

	private final Long id;
	private final Integer room;
	private final Map<LocalDate, List<Lecture>> lectures;

	public LecturesAudience(Long id, Integer room, Map<LocalDate, List<Lecture>> lectures) {
		super();
		this.id = id;
		this.room = room;
		this.lectures = lectures;
	}

	public Long getId() {
		return id;
	}

	public Integer getRoom() {
		return room;
	}

	public Map<LocalDate, List<Lecture>> getLectures() {
		return lectures;
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

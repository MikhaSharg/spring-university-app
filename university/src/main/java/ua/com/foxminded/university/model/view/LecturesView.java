package ua.com.foxminded.university.model.view;

import java.util.List;
import java.util.Objects;

import ua.com.foxminded.university.model.Lecture;

public class LecturesView {

	private final List<Lecture> lectures;

	public LecturesView(List<Lecture> lectures) {
		this.lectures = lectures;
	}

	public List<Lecture> getLectures() {
		return lectures;
	}

	@Override
	public String toString() {
		return "LecturesView [lectures=" + lectures + ", getLectures()=" + getLectures() + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(lectures);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		LecturesView other = (LecturesView) obj;
		return Objects.equals(lectures, other.lectures);
	}

	
	
}

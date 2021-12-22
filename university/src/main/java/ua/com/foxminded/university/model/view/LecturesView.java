package ua.com.foxminded.university.model.view;

import java.util.List;

import ua.com.foxminded.university.model.Lecture;

public class LecturesView {

	private final List<Lecture> lectures;

	public LecturesView(List<Lecture> lectures) {
		this.lectures = lectures;
	}

	public List<Lecture> getLectures() {
		return lectures;
	}

}

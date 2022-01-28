package ua.com.foxminded.university.model.view;

import java.util.List;

import ua.com.foxminded.university.model.Audience;
import ua.com.foxminded.university.model.Lecture;

public class RescheduleLecture {

	private Lecture newLecture;
	private List<Audience> audiences;

	public RescheduleLecture(Lecture newLecture, List<Audience> audiences) {
		super();
		this.newLecture = newLecture;
		this.audiences = audiences;
	}

	public RescheduleLecture() {
		super();
	}

	public Lecture getNewLecture() {
		return newLecture;
	}

	public void setNewLecture(Lecture newLecture) {
		this.newLecture = newLecture;
	}

	public List<Audience> getAudiences() {
		return audiences;
	}

	public void setAudiences(List<Audience> audiences) {
		this.audiences = audiences;
	}

	@Override
	public String toString() {
		return "RescheduleLecture [newLecture=" + newLecture + ", audiences=" + audiences + "]";
	}

}

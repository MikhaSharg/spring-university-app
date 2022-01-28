package ua.com.foxminded.university.model.view;

import java.util.List;

import ua.com.foxminded.university.model.Audience;

public class AudienceForm {

	private Integer roomNumber;
	private final List<Audience> audiences;

	public AudienceForm(List<Audience> audiences) {
		super();
		this.audiences = audiences;
	}

	public Integer getRoomNumber() {
		return roomNumber;
	}

	public void setRoomNumber(Integer roomNumber) {
		this.roomNumber = roomNumber;
	}

	public List<Audience> getAudiences() {
		return audiences;
	}

	@Override
	public String toString() {
		return "AudienceForm [roomNumber=" + roomNumber + "]";
	}

}

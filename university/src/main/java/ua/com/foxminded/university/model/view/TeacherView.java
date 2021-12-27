package ua.com.foxminded.university.model.view;

public class TeacherView {

	private final String firstName;
	private final String lastName;
	private final String profile;
	private final Integer SubjectsCount;
	private final Integer GroupsCount;

	public TeacherView(String firstName, String lastName, String profile, Integer subjectsCount, Integer groupsCount) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.profile = profile;
		SubjectsCount = subjectsCount;
		GroupsCount = groupsCount;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public String getProfile() {
		return profile;
	}

	public Integer getSubjectsCount() {
		return SubjectsCount;
	}

	public Integer getGroupsCount() {
		return GroupsCount;
	}

}

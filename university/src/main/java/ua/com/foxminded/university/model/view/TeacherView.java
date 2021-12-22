package ua.com.foxminded.university.model.view;

public class TeacherView {

	private String firstName;
	private String lastName;
	private String profile;
	private Integer SubjectsCount;
	private Integer GroupsCount;

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

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getProfile() {
		return profile;
	}

	public void setProfile(String profile) {
		this.profile = profile;
	}

	public Integer getSubjectsCount() {
		return SubjectsCount;
	}

	public void setSubjectsCount(Integer subjectsCount) {
		SubjectsCount = subjectsCount;
	}

	public Integer getGroupsCount() {
		return GroupsCount;
	}

	public void setGroupsCount(Integer groupsCount) {
		GroupsCount = groupsCount;
	}
}

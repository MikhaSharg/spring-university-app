package ua.com.foxminded.university.misc;

import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Value;

@Component
@PropertySource("classpath:generator.properties")
public class GeneratorConfig {

	@Value("${studentsCount}")
	private Integer studentCount;
	
	@Value("${groupsCount}")
	private Integer groupsCount;
	
	@Value("${groupsMinStudents}")
	private Integer groupsMinStudents;
	
	@Value("${groupsMaxStudents}")
	private Integer groupsMaxStudenets;
	
	@Value("${teacherCount}")
	private Integer teacherCount;
	
	@Value("${maxSubjectCountForOneTeacher}")
	private Integer maxSubjectCountForOneTeacher;
	
	@Value("${maxTeacherCountForOneSubject}")
	private Integer maxTeacherCountForOneSubject;
	
	@Value("${audienceCount}")
	private Integer audienceCount;
	
	@Value("${holidays}")
	private String holidays;
	
	@Value("${startDate}")
	private String startDate;
	
	@Value ("${endDate}")
	private String endDate;
	
	public GeneratorConfig() {
	}

	public GeneratorConfig(Integer studentCount, Integer groupsCount, Integer groupsMinStudents,
			Integer groupsMaxStudenets, Integer teacherCount, Integer maxSubjectCountForOneTeacher,
			Integer maxTeacherCountForOneSubject, Integer audienceCount, String holidays, String startDate,
			String endDate) {
		super();
		this.studentCount = studentCount;
		this.groupsCount = groupsCount;
		this.groupsMinStudents = groupsMinStudents;
		this.groupsMaxStudenets = groupsMaxStudenets;
		this.teacherCount = teacherCount;
		this.maxSubjectCountForOneTeacher = maxSubjectCountForOneTeacher;
		this.maxTeacherCountForOneSubject = maxTeacherCountForOneSubject;
		this.audienceCount = audienceCount;
		this.holidays = holidays;
		this.startDate = startDate;
		this.endDate = endDate;
	}

	public Integer getStudentCount() {
		return studentCount;
	}

	public void setStudentCount(Integer studentCount) {
		this.studentCount = studentCount;
	}

	public Integer getGroupsCount() {
		return groupsCount;
	}

	public void setGroupsCount(Integer groupsCount) {
		this.groupsCount = groupsCount;
	}

	public Integer getGroupsMinStudents() {
		return groupsMinStudents;
	}

	public void setGroupsMinStudents(Integer groupsMinStudents) {
		this.groupsMinStudents = groupsMinStudents;
	}

	public Integer getGroupsMaxStudenets() {
		return groupsMaxStudenets;
	}

	public void setGroupsMaxStudenets(Integer groupsMaxStudenets) {
		this.groupsMaxStudenets = groupsMaxStudenets;
	}

	public Integer getTeacherCount() {
		return teacherCount;
	}

	public void setTeacherCount(Integer teacherCount) {
		this.teacherCount = teacherCount;
	}

	public Integer getMaxSubjectCountForOneTeacher() {
		return maxSubjectCountForOneTeacher;
	}

	public void setMaxSubjectCountForOneTeacher(Integer maxSubjectCountForOneTeacher) {
		this.maxSubjectCountForOneTeacher = maxSubjectCountForOneTeacher;
	}

	public Integer getMaxTeacherCountForOneSubject() {
		return maxTeacherCountForOneSubject;
	}

	public void setMaxTeacherCountForOneSubject(Integer maxTeacherCountForOneSubject) {
		this.maxTeacherCountForOneSubject = maxTeacherCountForOneSubject;
	}

	public Integer getAudienceCount() {
		return audienceCount;
	}

	public void setAudienceCount(Integer audienceCount) {
		this.audienceCount = audienceCount;
	}

	public String getHolidays() {
		return holidays;
	}

	public void setHolidays(String holidays) {
		this.holidays = holidays;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	
}

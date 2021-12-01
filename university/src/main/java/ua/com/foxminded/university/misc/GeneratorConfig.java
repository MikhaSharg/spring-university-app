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
	
	public GeneratorConfig() {
	}

	public GeneratorConfig(Integer studentCount, Integer groupsCount, Integer groupsMinStudents,
			Integer groupsMaxStudenets, Integer teacherCount, Integer maxSubjectCountForOneTeacher,
			Integer maxTeacherCountForOneSubject, Integer audienceCount, String holidays) {
		super();
		this.studentCount = studentCount;
		this.groupsCount = groupsCount;
		this.groupsMinStudents = groupsMinStudents;
		this.groupsMaxStudenets = groupsMaxStudenets;
		this.maxSubjectCountForOneTeacher=maxSubjectCountForOneTeacher;
		this.maxTeacherCountForOneSubject=maxTeacherCountForOneSubject;
		this.teacherCount=teacherCount;
		this.audienceCount=audienceCount;
		this.holidays=holidays;
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

	public Integer getGroupsMaxStudnets() {
		return groupsMaxStudenets;
	}

	public void setGroupsMaxStudnets(Integer groupsMaxStudnets) {
		this.groupsMaxStudenets = groupsMaxStudnets;
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

	public void setMaxSubjectCountForOneTeacher(Integer maxSubjectCountForOneTeacher) {
		this.maxSubjectCountForOneTeacher = maxSubjectCountForOneTeacher;
	}

	public String getHolidays() {
		return holidays;
	}

	public void setHolidays(String holidays) {
		this.holidays = holidays;
	}
	
}

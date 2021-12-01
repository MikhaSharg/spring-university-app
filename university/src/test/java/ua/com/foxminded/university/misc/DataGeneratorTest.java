package ua.com.foxminded.university.misc;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.contains;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.apache.coyote.http11.upgrade.UpgradeServletOutputStream;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;

import ua.com.foxminded.university.dao.*;
import ua.com.foxminded.university.dao.jdbc.*;
import ua.com.foxminded.university.dao.mappers.*;
import ua.com.foxminded.university.model.*;


import static ua.com.foxminded.university.misc.DateGenerationUtils.*;
import static org.assertj.core.api.Assertions.assertThat;

@JdbcTest
class DataGeneratorTest {

	@Autowired
	private JdbcTemplate jdbcTemplate;
	private DataGenerator dataGenerator;
	private StudentDao studentDao;
	private GroupDao groupDao;
	private TeacherDao teacherDao;
	private SubjectDao subjectDao;
	private AudienceDao audienceDao;
	private LectureSessionsDao lectureSessionsDao;
	private LectureDao lectureDao;
	private GeneratorConfig config;
	
	@PostConstruct
	void setup() {
		this.studentDao = new JdbcStudentDao(jdbcTemplate, new StudentMapper());
		this.groupDao = new JdbcGroupDao(jdbcTemplate, new GroupMapper());
		this.teacherDao = new JdbcTeacherDao(jdbcTemplate, new TeacherMapper());
		this.subjectDao = new JdbcSubjectDao(jdbcTemplate, new SubjectMapper());
		this.audienceDao = new JdbcAudienceDao(jdbcTemplate, new AudienceMapper());
		this.lectureSessionsDao = new JdbcLectureSessionsDao(jdbcTemplate, new LectureSessionsMapper());
		this.lectureDao = new JdbcLectureDao(jdbcTemplate, new LectureMapper());
		this.config = new GeneratorConfig();
		this.dataGenerator = new DataGenerator(studentDao, groupDao, teacherDao, subjectDao, audienceDao, lectureSessionsDao, lectureDao, null);
		
	}
	
	@Test
	void shouldGenerateData() {
		int expectedStudentCount = 200;
		int expectedGroupCount = 10;
		int expectedTeacherCount = 10;
		int expectedMaxSubjectCountForOneTeacher = 3;
		int expectedMaxTeacherCountForOneSubject = 2;
		int expectedGroupsMaxStudnets = 30;
		int expectedGroupsMinStudnets = 10;
		int expectedAudienceCount = 20;
		String expectedHolidays = "2021-06-01/2021-08-30, 2021-01-01/2021-01-10"; 
		
	config = new GeneratorConfig(expectedStudentCount, 
			expectedGroupCount, 
			expectedGroupsMinStudnets, 
			expectedGroupsMaxStudnets, 
			expectedGroupsMinStudnets,
			expectedMaxSubjectCountForOneTeacher,
			expectedMaxTeacherCountForOneSubject,
			expectedAudienceCount,
			expectedHolidays);	
	
	dataGenerator.generate(config);
	List<Group> groups = groupDao.findAll();
	assertThat(groups.size()).isEqualTo(expectedGroupCount);
	
	List<Student> students = studentDao.findAll();
	assertThat(students.size()).isEqualTo(expectedStudentCount);
	assertThat(students.stream().filter(e->e.getId()==null).collect(Collectors.toList()).size()).isZero();
	int uniqueGroupsCount = students.stream().map(e->e.getGroupId()).distinct().collect(Collectors.toList()).size();
	assertThat(uniqueGroupsCount).isEqualTo(expectedGroupCount);
	
	
	List<Teacher> teachers = teacherDao.findAll();
	assertThat(teachers.size()).isEqualTo(expectedTeacherCount);
	assertTrue(teachers.stream().filter(e->e.getSubjects().isEmpty()).collect(Collectors.toList()).isEmpty());
	
	List<Subject> subjects = subjectDao.findAll();
	int expectedSubjectCount = expectedTeacherCount*expectedMaxSubjectCountForOneTeacher/expectedMaxTeacherCountForOneSubject;
	assertThat(subjects.size()).isEqualTo(expectedSubjectCount);
	for (Teacher teacher : teachers) {
		assertFalse(subjectDao.findAllSubjectsByTeacherId(teacher.getId()).isEmpty());
	}
	
	List<Audience> audiences = audienceDao.findAll();
	assertThat(audiences.size()).isEqualTo(expectedAudienceCount);
	
	List<LectureSessions> sessions = lectureSessionsDao.findAll();
	assertThat(sessions.size()).isEqualTo(6);
	
	List<LocalDate> dates = DateGenerationUtils.generateStudyDates(expectedHolidays);
	assertThat(dates.size()).isNotEqualTo(0);

	List<Lecture> lectures = lectureDao.findAll();
	assertThat(lectureDao.findLecturesOneDate(LocalDate.of(2021, 04, 06)).size()).isEqualTo(60);
	assertTrue(lectures.stream().filter(e->e.getId()==null).collect(Collectors.toList()).isEmpty());
	assertThat(lectureDao.findLecturesForGroupByDate(1L, LocalDate.of(2021, 04, 06)).size()).isEqualTo(6);
	
	}
	
}

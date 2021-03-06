package ua.com.foxminded.university.dao.jdbc;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDate;
import java.util.List;

import javax.annotation.PostConstruct;

import java.util.Arrays;
import java.util.Collections;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.jdbc.Sql;

import ua.com.foxminded.university.dao.LectureDao;
import ua.com.foxminded.university.dao.mappers.LectureMapper;
import ua.com.foxminded.university.model.Audience;
import ua.com.foxminded.university.model.Group;
import ua.com.foxminded.university.model.Lecture;
import ua.com.foxminded.university.model.LectureSessions;
import ua.com.foxminded.university.model.Subject;
import ua.com.foxminded.university.model.Teacher;

import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;

@JdbcTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
class JdbcLectureDaoTest {

	@Autowired
	JdbcTemplate jdbcTemplate;
	private LectureDao dao;

	@PostConstruct
	void setUp() {
		dao = new JdbcLectureDao(jdbcTemplate, new LectureMapper());
	}

	@Test
	@Sql(scripts = { "/sql/clean_db.sql", "/sql/lectures_test_values.sql" })
	void shouldFindLectureById() {

		LectureSessions lectureSessions = new LectureSessions(1L, "1th", "8:00", "9:20");
		Audience audience = new Audience(1L, 100);
		Subject subject = new Subject(1L, "Theory of probability and mathematical statistics");
		Teacher teacher = new Teacher(1L, "Alex", "Petrov", "male", "AlexPetrov@gmail.com", "Saint Petersburg", 68,
				89313262896L, "teacher", "Professor");
		Group group = new Group(1L, "AB-12");
		LocalDate date = LocalDate.of(2021, 11, 11);

		Lecture expected = new Lecture(1L, date, lectureSessions, audience, subject, teacher, group);
		Lecture actual = dao.findById(1L).get();
		assertThat(actual).isEqualTo(expected);
	}

	@Test
	@Sql(scripts = { "/sql/clean_db.sql", "/sql/lectures_test_values.sql" })
	void shouldNotFindLecture() {
		assertThat(dao.findById(50L)).isEmpty();
	}

	@Test
	@Sql(scripts = { "/sql/clean_db.sql", "/sql/lectures_test_values.sql" })
	void shouldFindAllLecture() {

		LectureSessions lectureSessions1 = new LectureSessions(1L, "1th", "8:00", "9:20");
		LectureSessions lectureSessions2 = new LectureSessions(2L, "2th", "9:30", "10:50");
		LectureSessions lectureSessions3 = new LectureSessions(3L, "3th", "11:00", "12:20");
		LectureSessions lectureSessions4 = new LectureSessions(4L, "4th", "13:00", "14:20");
		LectureSessions lectureSessions5 = new LectureSessions(5L, "5th", "14:30", "15:50");
		LectureSessions lectureSessions6 = new LectureSessions(6L, "6th", "16:00", "17:20");

		Audience audience1 = new Audience(1L, 100);
		Audience audience2 = new Audience(2L, 101);
		Audience audience3 = new Audience(3L, 102);
		Audience audience4 = new Audience(4L, 103);
		Audience audience5 = new Audience(5L, 104);

		Subject subject1 = new Subject(1L, "Theory of probability and mathematical statistics");
		Subject subject2 = new Subject(2L, "Theoretical mechanics");
		Subject subject3 = new Subject(3L, "Architecture");
		Subject subject4 = new Subject(4L, "Strength of materials");
		Subject subject5 = new Subject(5L, "SAPR");

		Teacher teacher1 = new Teacher(1L, "Alex", "Petrov", "male", "AlexPetrov@gmail.com", "Saint Petersburg", 68,
				89313262896L, "teacher", "Professor");

		Teacher teacher2 = new Teacher(2L, "Anna", "Ermakova", "female", "AnnaErmakova@gmail.com", "Kaliningrad", 48,
				89215895789L, "teacher", "Assistant Lecturer");
		Teacher teacher3 = new Teacher(3L, "Roman", "Sidorov", "male", "RomanSidorov@gmail.com", "Moscow", 53,
				89112568975L, "teacher", "Doctor of Technical Science");
		Teacher teacher4 = new Teacher(4L, "Diana", "Gukova", "female", "DianaGukova@gmail.com", "Rostov", 52,
				89225896325L, "teacher", "Senior Lecturer");
		Teacher teacher5 = new Teacher(5L, "Dmitry", "Solodin", "male", "MikhailSolodin@gmail.com", "Andora", 56,
				89052655985L, "teacher", "Candidate of Technical Science");

		Group group1 = new Group(1L, "AB-12");
		Group group2 = new Group(2L, "CD-34");
		Group group3 = new Group(3L, "EF-56");
		Group group4 = new Group(4L, "GH-78");
		Group group5 = new Group(5L, "IJ-90");

		LocalDate date = LocalDate.of(2021, 11, 11);

		List<Lecture> expected = Arrays.asList(
				new Lecture(1L, date, lectureSessions1, audience1, subject1, teacher1, group1),
				new Lecture(2L, date, lectureSessions2, audience2, subject2, teacher2, group2),
				new Lecture(3L, date, lectureSessions3, audience3, subject3, teacher3, group3),
				new Lecture(4L, date, lectureSessions4, audience4, subject4, teacher4, group4),
				new Lecture(5L, date, lectureSessions5, audience5, subject5, teacher5, group5),
				new Lecture(6L, date, lectureSessions6, audience3, subject2, teacher4, group3));

		List<Lecture> actual = dao.findAll();
		actual.sort((l1, l2) -> l1.getId().compareTo(l2.getId()));
		assertThat(actual).isEqualTo(expected);
	}

	@Test
	@Sql(scripts = "/sql/clean_db.sql")
	void shouldNotFindAll() {
		assertThat(dao.findAll().size()).isZero();
	}

	@Test
	@Sql(scripts = { "/sql/clean_db.sql", "/sql/lectures_test_values.sql" })
	void shouldInsertNew() {

		LectureSessions lectureSessions = new LectureSessions(1L, "1th", "8:00", "9:20");
		Audience audience = new Audience(1L, 100);
		Subject subject = new Subject(1L, "Theory of probability and mathematical statistics");
		Teacher teacher = new Teacher(1L, "Alex", "Petrov", "male", "AlexPetrov@gmail.com", "Saint Petersburg", 68,
				89313262896L, "teacher", "Professor");
		Group group = new Group(1L, "AB-12");
		LocalDate date = LocalDate.of(2021, 11, 12);
		Lecture expected = new Lecture(date, lectureSessions, audience, subject, teacher, group);
		Lecture created = dao.save(expected);
		expected.setId(created.getId());
		assertThat(created).isEqualTo(expected);
	}

	@Test
	@Sql(scripts = { "/sql/clean_db.sql", "/sql/lectures_test_values.sql" })
	void shouldUpdateLecture() {

		LectureSessions lectureSessions = new LectureSessions(1L, "1th", "8:00", "9:20");
		Audience audience = new Audience(1L, 100);
		Subject subject = new Subject(1L, "Theory of probability and mathematical statistics");
		Teacher teacher = new Teacher(3L, "Roman", "Sidorov", "male", "RomanSidorov@gmail.com", "Moscow", 53,
				89112568975L, "teacher", "Doctor of Technical Science");
		Group group = new Group(1L, "AB-12");
		LocalDate date = LocalDate.of(2021, 11, 11);
		Lecture expected = new Lecture(1L, date, lectureSessions, audience, subject, teacher, group);

		assertThat(dao.findById(1L).get()).isNotEqualTo(expected);
		Lecture updated = dao.save(expected);
		assertThat(dao.findById(1L).get()).isEqualTo(expected);
		assertThat(updated).isEqualTo(expected);
	}

	@Test
	@Sql(scripts = { "/sql/clean_db.sql", "/sql/lectures_test_values.sql" })
	void shouldDeleteLectureById() {
		LectureSessions lectureSessions = new LectureSessions(1L, "1th", "8:00", "9:20");
		Audience audience = new Audience(1L, 100);
		Subject subject = new Subject(1L, "Theory of probability and mathematical statistics");
		Teacher teacher = new Teacher(1L, "Alex", "Petrov", "male", "AlexPetrov@gmail.com", "Saint Petersburg", 68,
				89313262896L, "teacher", "Professor");
		Group group = new Group(1L, "AB-12");
		LocalDate date = LocalDate.of(2021, 11, 11);
		Lecture expected = new Lecture(1L, date, lectureSessions, audience, subject, teacher, group);

		assertThat(dao.findById(1L)).get().isEqualTo(expected);
		assertThat(dao.findById(1L)).isPresent();
		dao.deleteById(1L);
		assertThat(dao.findById(1L)).isEmpty();
	}

	@Test
	@Sql(scripts = "/sql/clean_db.sql")
	void shouldNotDeleteLectureByIdIfNOtExist() {
		assertThat(dao.findById(30L)).isNotPresent();
		assertThat(dao.findById(30L)).isEmpty();
		assertThat(assertThrows(IllegalArgumentException.class, () -> dao.deleteById(30L)).getMessage())
				.contains("Unable to delete item with id ", "30");
	}

	@Test
	@Sql(scripts = { "/sql/clean_db.sql", "/sql/lectures_test_values.sql" })
	void shouldBatchSave() {

		LectureSessions lectureSessions1 = new LectureSessions(1L, "1th", "8:00", "9:20");
		LectureSessions lectureSessions2 = new LectureSessions(2L, "2th", "9:30", "10:50");
		LectureSessions lectureSessions3 = new LectureSessions(3L, "3th", "11:00", "12:20");
		LectureSessions lectureSessions4 = new LectureSessions(4L, "4th", "13:00", "14:20");
		LectureSessions lectureSessions5 = new LectureSessions(5L, "5th", "14:30", "15:50");
		LectureSessions lectureSessions6 = new LectureSessions(6L, "6th", "16:00", "17:20");

		Audience audience1 = new Audience(1L, 100);
		Audience audience2 = new Audience(2L, 101);
		Audience audience3 = new Audience(3L, 102);
		Audience audience4 = new Audience(4L, 103);
		Audience audience5 = new Audience(5L, 104);

		Subject subject1 = new Subject(1L, "Theory of probability and mathematical statistics");
		Subject subject2 = new Subject(2L, "Theoretical mechanics");
		Subject subject3 = new Subject(3L, "Architecture");
		Subject subject4 = new Subject(4L, "Strength of materials");
		Subject subject5 = new Subject(5L, "SAPR");

		Teacher teacher1 = new Teacher(1L, "Alex", "Petrov", "male", "AlexPetrov@gmail.com", "Saint Petersburg", 68,
				89313262896L, "teacher", "Professor");

		Teacher teacher2 = new Teacher(2L, "Anna", "Ermakova", "female", "AnnaErmakova@gmail.com", "Kaliningrad", 48,
				89215895789L, "teacher", "Assistant Lecturer");
		Teacher teacher3 = new Teacher(3L, "Roman", "Sidorov", "male", "RomanSidorov@gmail.com", "Moscow", 53,
				89112568975L, "teacher", "Doctor of Technical Science");
		Teacher teacher4 = new Teacher(4L, "Diana", "Gukova", "female", "DianaGukova@gmail.com", "Rostov", 52,
				89225896325L, "teacher", "Senior Lecturer");
		Teacher teacher5 = new Teacher(5L, "Dmitry", "Solodin", "male", "MikhailSolodin@gmail.com", "Andora", 56,
				89052655985L, "teacher", "Candidate of Technical Science");

		Group group1 = new Group(1L, "AB-12");
		Group group2 = new Group(2L, "CD-34");
		Group group3 = new Group(3L, "EF-56");
		Group group4 = new Group(4L, "GH-78");
		Group group5 = new Group(5L, "IJ-90");

		List<Lecture> toSave = Arrays.asList(
				new Lecture(1L, LocalDate.of(2021, 11, 11), lectureSessions1, audience3, subject1, teacher1, group1),
				new Lecture(2L, LocalDate.of(2021, 11, 11), lectureSessions2, audience5, subject2, teacher2, group2),
				new Lecture(4L, LocalDate.of(2021, 11, 11), lectureSessions4, audience4, subject4, teacher4, group4),
				new Lecture(5L, LocalDate.of(2021, 11, 11), lectureSessions5, audience2, subject5, teacher5, group5),
				new Lecture(LocalDate.of(2021, 11, 12), lectureSessions1, audience1, subject1, teacher1, group1),
				new Lecture(LocalDate.of(2021, 11, 12), lectureSessions2, audience2, subject2, teacher2, group2),
				new Lecture(LocalDate.of(2021, 11, 12), lectureSessions3, audience3, subject3, teacher3, group3),
				new Lecture(LocalDate.of(2021, 11, 12), lectureSessions4, audience4, subject4, teacher4, group4),
				new Lecture(LocalDate.of(2021, 11, 12), lectureSessions5, audience5, subject5, teacher5, group5),
				new Lecture(LocalDate.of(2021, 11, 12), lectureSessions6, audience3, subject2, teacher4, group3));

		List<Lecture> actual = dao.saveAll(toSave);
		assertThat(actual.size()).isEqualTo(10);
		assertThat(dao.findAll().size()).isEqualTo(12);
		assertThat(actual.stream().filter(item -> item.getId() == null).count()).isZero();
	}

	@Test
	@Sql(scripts = { "/sql/clean_db.sql", "/sql/lectures_test_values2.sql" })
	void shouldFindLecturesForOneDate() {
		assertThat(dao.findLecturesOneDate(LocalDate.of(2021, 11, 11)).size()).isEqualTo(6);

	}

	@Test
	@Sql(scripts = { "/sql/clean_db.sql", "/sql/lectures_test_values2.sql" })
	void shouldFindLecturesForDateRange() {
		assertThat(dao.findLectureForDateRange(LocalDate.of(2021, 11, 11), LocalDate.of(2021, 11, 16)).size())
				.isEqualTo(36);

	}

	@Test
	@Sql(scripts = { "/sql/clean_db.sql", "/sql/lectures_test_values2.sql" })
	void shouldThrowExceptionIfSaveLectureWithDublicateFields() {

		LectureSessions lectureSessions = new LectureSessions(1L, "1th", "8:00", "9:20");
		Subject subject = new Subject(5L, "SAPR");

		// Duplicate data-session-teacher

		Audience audience = new Audience(3L, 102);
		Teacher teacher = new Teacher(1L, "Alex", "Petrov", "male", "AlexPetrov@gmail.com", "Saint Petersburg", 68,
				89313262896L, "teacher", "Professor");
		Group group = new Group(4L, "GH-78");
		Lecture newLection = new Lecture(LocalDate.of(2021, 11, 13), lectureSessions, audience, subject, teacher,
				group);

		assertThrows(Exception.class, () -> dao.save(newLection));

		// Duplicate data-session-group

		Audience audience2 = new Audience(3L, 102);
		Teacher teacher2 = new Teacher(3L, "Roman", "Sidorov", "male", "RomanSidorov@gmail.com", "Moscow", 53,
				89112568975L, "teacher", "Doctor of Technical Science");
		Group group2 = new Group(1L, "AB-12");
		Lecture newLection2 = new Lecture(LocalDate.of(2021, 11, 13), lectureSessions, audience2, subject, teacher2,
				group2);

		assertThrows(Exception.class, () -> dao.save(newLection2));

		// Duplicate data-session-audience

		Audience audience3 = new Audience(1L, 100);
		Teacher teacher3 = new Teacher(3L, "Roman", "Sidorov", "male", "RomanSidorov@gmail.com", "Moscow", 53,
				89112568975L, "teacher", "Doctor of Technical Science");
		Group group3 = new Group(4L, "GH-78");
		Lecture newLection3 = new Lecture(LocalDate.of(2021, 11, 13), lectureSessions, audience3, subject, teacher3,
				group3);

		assertThrows(Exception.class, () -> dao.save(newLection3));

	}

	@Test
	@Sql(scripts = { "/sql/clean_db.sql", "/sql/lectures_test_values2.sql" })
	void shouldFindLecturesForTeacherByDate() {
		LectureSessions lectureSessions = new LectureSessions(1L, "1th", "8:00", "9:20");
		Audience audience = new Audience(1L, 100);
		Subject subject = new Subject(1L, "Theory of probability and mathematical statistics");
		Teacher teacher = new Teacher(1L, "Alex", "Petrov", "male", "AlexPetrov@gmail.com", "Saint Petersburg", 68,
				89313262896L, "teacher", "Professor");
		Group group = new Group(1L, "AB-12");
		LocalDate date = LocalDate.of(2021, 11, 12);
		Lecture expected = new Lecture(7L, date, lectureSessions, audience, subject, teacher, group);
		List<Lecture> expectedList = Arrays.asList(expected);

		List<Lecture> actual = dao.findLecturesForTeacherByDate(1L, LocalDate.of(2021, 11, 12));
		assertThat(actual.size()).isEqualTo(1);
		assertThat(actual).isEqualTo(expectedList);

	}

	@Test
	@Sql(scripts = { "/sql/clean_db.sql", "/sql/lectures_test_values2.sql" })
	void shouldFindLecturesForGroupByDate() {
		LectureSessions lectureSessions = new LectureSessions(1L, "1th", "8:00", "9:20");
		Audience audience = new Audience(1L, 100);
		Subject subject = new Subject(1L, "Theory of probability and mathematical statistics");
		Teacher teacher = new Teacher(1L, "Alex", "Petrov", "male", "AlexPetrov@gmail.com", "Saint Petersburg", 68,
				89313262896L, "teacher", "Professor");
		Group group = new Group(1L, "AB-12");
		LocalDate date = LocalDate.of(2021, 11, 12);
		Lecture expected = new Lecture(7L, date, lectureSessions, audience, subject, teacher, group);
		List<Lecture> expectedList = Arrays.asList(expected);

		List<Lecture> actual = dao.findLecturesForTeacherByDate(1L, LocalDate.of(2021, 11, 12));
		assertThat(actual.size()).isEqualTo(1);
		assertThat(actual).isEqualTo(expectedList);

	}

	@Test
	@Sql(scripts = { "/sql/clean_db.sql", "/sql/lectures_test_values2.sql" })
	void shouldFindLecturesBySessionAndGroupId() {

		LectureSessions lectureSessions1 = new LectureSessions(1L, "1th", "8:00", "9:20");
		LectureSessions lectureSessions2 = new LectureSessions(2L, "2th", "9:30", "10:50");
		LectureSessions lectureSessions3 = new LectureSessions(3L, "3th", "11:00", "12:20");
		LectureSessions lectureSessions4 = new LectureSessions(4L, "4th", "13:00", "14:20");
		LectureSessions lectureSessions5 = new LectureSessions(5L, "5th", "14:30", "15:50");
		LectureSessions lectureSessions6 = new LectureSessions(6L, "6th", "16:00", "17:20");

		Audience audience1 = new Audience(1L, 100);
		Audience audience2 = new Audience(2L, 101);
		Audience audience3 = new Audience(3L, 102);
		Audience audience4 = new Audience(4L, 103);
		Audience audience5 = new Audience(5L, 104);

		Subject subject1 = new Subject(1L, "Theory of probability and mathematical statistics");
		Subject subject2 = new Subject(2L, "Theoretical mechanics");
		Subject subject3 = new Subject(3L, "Architecture");
		Subject subject4 = new Subject(4L, "Strength of materials");
		Subject subject5 = new Subject(5L, "SAPR");

		Teacher teacher1 = new Teacher(1L, "Alex", "Petrov", "male", "AlexPetrov@gmail.com", "Saint Petersburg", 68,
				89313262896L, "teacher", "Professor");

		Group group1 = new Group(1L, "AB-12");

		LocalDate date1 = LocalDate.of(2021, 11, 11);
		LocalDate date2 = LocalDate.of(2021, 11, 12);
		LocalDate date3 = LocalDate.of(2021, 11, 13);
		LocalDate date4 = LocalDate.of(2021, 11, 14);
		LocalDate date5 = LocalDate.of(2021, 11, 15);
		LocalDate date6 = LocalDate.of(2021, 11, 16);

		List<Lecture> expected = Arrays.asList(
				new Lecture(1L, date1, lectureSessions1, audience1, subject1, teacher1, group1),
				new Lecture(7L, date2, lectureSessions1, audience1, subject1, teacher1, group1),
				new Lecture(13L, date3, lectureSessions1, audience1, subject1, teacher1, group1),
				new Lecture(19L, date4, lectureSessions1, audience1, subject1, teacher1, group1),
				new Lecture(25L, date5, lectureSessions1, audience1, subject1, teacher1, group1),
				new Lecture(31L, date6, lectureSessions1, audience1, subject1, teacher1, group1));

		List<Lecture> actual = dao.findLecturesByTeacherAndGroupId(1L, 1L);
		assertThat(expected).isEqualTo(actual);
	}

	@Test
	@Sql(scripts = { "/sql/clean_db.sql", "/sql/archive_test_values.sql" })
	void shouldFindAllLecturesFromArchiveByDateRange() {

		LectureSessions lectureSessions1 = new LectureSessions(1L, "1th", "8:00", "9:20");
		LectureSessions lectureSessions2 = new LectureSessions(2L, "2th", "9:30", "10:50");
		LectureSessions lectureSessions3 = new LectureSessions(3L, "3th", "11:00", "12:20");
		LectureSessions lectureSessions4 = new LectureSessions(4L, "4th", "13:00", "14:20");
		LectureSessions lectureSessions5 = new LectureSessions(5L, "5th", "14:30", "15:50");
		LectureSessions lectureSessions6 = new LectureSessions(6L, "6th", "16:00", "17:20");

		Audience audience1 = new Audience(1L, 100);
		Audience audience2 = new Audience(2L, 101);
		Audience audience3 = new Audience(3L, 102);
		Audience audience4 = new Audience(4L, 103);
		Audience audience5 = new Audience(5L, 104);

		Subject subject1 = new Subject(1L, "Theory of probability and mathematical statistics");
		Subject subject2 = new Subject(2L, "Theoretical mechanics");
		Subject subject3 = new Subject(3L, "Architecture");
		Subject subject4 = new Subject(4L, "Strength of materials");
		Subject subject5 = new Subject(5L, "SAPR");

		Teacher teacher1 = new Teacher(1L, "Alex", "Petrov", "male", "AlexPetrov@gmail.com", "Saint Petersburg", 68,
				89313262896L, "teacher", "Professor");

		Teacher teacher2 = new Teacher(2L, "Anna", "Ermakova", "female", "AnnaErmakova@gmail.com", "Kaliningrad", 48,
				89215895789L, "teacher", "Assistant Lecturer");
		Teacher teacher3 = new Teacher(3L, "Roman", "Sidorov", "male", "RomanSidorov@gmail.com", "Moscow", 53,
				89112568975L, "teacher", "Doctor of Technical Science");
		Teacher teacher4 = new Teacher(4L, "Diana", "Gukova", "female", "DianaGukova@gmail.com", "Rostov", 52,
				89225896325L, "teacher", "Senior Lecturer");
		Teacher teacher5 = new Teacher(5L, "Dmitry", "Solodin", "male", "MikhailSolodin@gmail.com", "Andora", 56,
				89052655985L, "teacher", "Candidate of Technical Science");

		Group group1 = new Group(1L, "AB-12");
		Group group2 = new Group(2L, "CD-34");
		Group group3 = new Group(3L, "EF-56");
		Group group4 = new Group(4L, "GH-78");
		Group group5 = new Group(5L, "IJ-90");

		LocalDate date1 = LocalDate.of(2021, 11, 11);
		LocalDate date2 = LocalDate.of(2021, 11, 12);
		LocalDate date3 = LocalDate.of(2021, 11, 13);
		LocalDate date4 = LocalDate.of(2021, 11, 14);
		LocalDate date5 = LocalDate.of(2021, 11, 15);
		LocalDate date6 = LocalDate.of(2021, 11, 16);

		List<Lecture> expected = Arrays.asList(
				new Lecture(2L, date2, lectureSessions2, audience2, subject2, teacher2, group2, true,
						"rescheduled to 20.11.2021", 6L),
				new Lecture(1L, date1, lectureSessions1, audience1, subject1, teacher1, group1, true, "Lecture was canceled ",
						null),
				new Lecture(3L, date3, lectureSessions3, audience3, subject3, teacher3, group3, true,
						"edited audience to", null));

		List<Lecture> archivedLectures = dao.findArchivedLecturesForDateRange(LocalDate.of(2021, 11, 11),
				LocalDate.of(2021, 11, 13));
		assertThat(archivedLectures).isEqualTo(expected);
	}

	@Test
	@Sql(scripts = { "/sql/clean_db.sql", "/sql/archive_test_values.sql" })
	void shouldArchiveLecture() {
		LectureSessions lectureSessions1 = new LectureSessions(1L, "1th", "8:00", "9:20");
		LectureSessions lectureSessions2 = new LectureSessions(2L, "2th", "9:30", "10:50");
		LectureSessions lectureSessions3 = new LectureSessions(3L, "3th", "11:00", "12:20");
		LectureSessions lectureSessions4 = new LectureSessions(4L, "4th", "13:00", "14:20");
		LectureSessions lectureSessions5 = new LectureSessions(5L, "5th", "14:30", "15:50");
		LectureSessions lectureSessions6 = new LectureSessions(6L, "6th", "16:00", "17:20");

		Audience audience1 = new Audience(1L, 100);
		Audience audience2 = new Audience(2L, 101);
		Audience audience3 = new Audience(3L, 102);
		Audience audience4 = new Audience(4L, 103);
		Audience audience5 = new Audience(5L, 104);

		Subject subject1 = new Subject(1L, "Theory of probability and mathematical statistics");
		Subject subject2 = new Subject(2L, "Theoretical mechanics");
		Subject subject3 = new Subject(3L, "Architecture");
		Subject subject4 = new Subject(4L, "Strength of materials");
		Subject subject5 = new Subject(5L, "SAPR");

		Teacher teacher1 = new Teacher(1L, "Alex", "Petrov", "male", "AlexPetrov@gmail.com", "Saint Petersburg", 68,
				89313262896L, "teacher", "Professor");

		Teacher teacher2 = new Teacher(2L, "Anna", "Ermakova", "female", "AnnaErmakova@gmail.com", "Kaliningrad", 48,
				89215895789L, "teacher", "Assistant Lecturer");
		Teacher teacher3 = new Teacher(3L, "Roman", "Sidorov", "male", "RomanSidorov@gmail.com", "Moscow", 53,
				89112568975L, "teacher", "Doctor of Technical Science");
		Teacher teacher4 = new Teacher(4L, "Diana", "Gukova", "female", "DianaGukova@gmail.com", "Rostov", 52,
				89225896325L, "teacher", "Senior Lecturer");
		Teacher teacher5 = new Teacher(5L, "Dmitry", "Solodin", "male", "MikhailSolodin@gmail.com", "Andora", 56,
				89052655985L, "teacher", "Candidate of Technical Science");

		Group group1 = new Group(1L, "AB-12");
		Group group2 = new Group(2L, "CD-34");
		Group group3 = new Group(3L, "EF-56");
		Group group4 = new Group(4L, "GH-78");
		Group group5 = new Group(5L, "IJ-90");

		LocalDate date1 = LocalDate.of(2021, 11, 11);
		LocalDate date2 = LocalDate.of(2021, 11, 12);
		LocalDate date3 = LocalDate.of(2021, 11, 13);
		LocalDate date4 = LocalDate.of(2021, 11, 14);
		LocalDate date5 = LocalDate.of(2021, 11, 15);
		LocalDate date6 = LocalDate.of(2021, 11, 16);

		List<Lecture> expectedBeforeArchiveLecture = Arrays.asList(
				new Lecture(2L, date2, lectureSessions2, audience2, subject2, teacher2, group2, false,
						"rescheduled to 20.11.2021", 6L),
				new Lecture(1L, date1, lectureSessions1, audience1, subject1, teacher1, group1, false, "Lecture was canceled ",
						null),
				new Lecture(3L, date3, lectureSessions3, audience3, subject3, teacher3, group3, false,
						"edited audience to", null));

		List<Lecture> expectedAfterArchiveLecture = Arrays.asList(
				new Lecture(2L, date2, lectureSessions2, audience2, subject2, teacher2, group2, false,
						"rescheduled to 20.11.2021", 6L),
				new Lecture(1L, date1, lectureSessions1, audience1, subject1, teacher1, group1, false, "Lecture was canceled ",
						null),
				new Lecture(3L, date3, lectureSessions3, audience3, subject3, teacher3, group3, false,
						"edited audience to", null),
				new Lecture(4L, date4, lectureSessions4, audience4, subject4, teacher4, group4, false, "Lecture was canceled ",
						null));
		List<Lecture> expectedAfterArchiveLecture2 = Arrays.asList(
				new Lecture(2L, date2, lectureSessions2, audience2, subject2, teacher2, group2, false,
						"rescheduled to 20.11.2021", 6L),
				new Lecture(1L, date1, lectureSessions1, audience1, subject1, teacher1, group1, false, "Lecture was canceled ",
						null),
				new Lecture(3L, date3, lectureSessions3, audience3, subject3, teacher3, group3, false,
						"edited audience to", null),
				new Lecture(4L, date4, lectureSessions4, audience4, subject4, teacher4, group4, false, "Lecture was canceled ",
						null),
				new Lecture(5L, date1, lectureSessions5, audience5, subject5, teacher5, group5, false,
						"rescheduled to 15.11.2021", 7L));

		List<Lecture> expectedAfterArchiveLecture3 = Arrays.asList(
				new Lecture(2L, date2, lectureSessions2, audience2, subject2, teacher2, group2, false,
						"rescheduled to 20.11.2021", 6L),
				new Lecture(1L, date1, lectureSessions1, audience1, subject1, teacher1, group1, false, "Lecture was canceled ",
						null),
				new Lecture(3L, date3, lectureSessions3, audience3, subject3, teacher3, group3, false,
						"edited audience to", null),
				new Lecture(4L, date4, lectureSessions4, audience4, subject4, teacher4, group4, false, "Lecture was canceled ",
						null),
				new Lecture(5L, date1, lectureSessions5, audience5, subject5, teacher5, group5, false,
						"rescheduled to 15.11.2021", 7L),
				new Lecture(8L, date3, lectureSessions3, audience3, subject3, teacher3, group3, false,
						"edited audience to 104", null));

		Lecture canceledLecture = new Lecture(4L, date4, lectureSessions4, audience4, subject4, teacher4, group4, false,
				"canceled", null);
		Lecture rescheduledLecture = new Lecture(5L, date1, lectureSessions5, audience5, subject5, teacher5, group5,
				false, "rescheduled to 15.11.2021", 7L);
		Lecture editedLecture = new Lecture(8L, date3, lectureSessions3, audience3, subject3, teacher3, group3, false,
				"edited audience to 104", null);

		List<Lecture> actualBeforeArchive = dao.findArchivedLectures();
		assertThat(actualBeforeArchive).isEqualTo(expectedBeforeArchiveLecture);

		dao.archiveLecture(canceledLecture);
		List<Lecture> actualAfterArchive = dao.findArchivedLectures();
		assertThat(actualAfterArchive).isEqualTo(expectedAfterArchiveLecture);

		dao.archiveLecture(rescheduledLecture);
		List<Lecture> actualAfterArchive2 = dao.findArchivedLectures();
		assertThat(actualAfterArchive2).isEqualTo(expectedAfterArchiveLecture2);

		dao.archiveLecture(editedLecture);
		List<Lecture> actualAfterArchive3 = dao.findArchivedLectures();
		assertThat(actualAfterArchive3).isEqualTo(expectedAfterArchiveLecture3);
	}

}

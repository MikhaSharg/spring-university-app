package ua.com.foxminded.university.dao.jdbc;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDate;
import java.util.List;

import javax.annotation.PostConstruct;

import java.util.Arrays;
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
				89313262896L, "Dean", "Professor");
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
				89313262896L, "Dean", "Professor");

		Teacher teacher2 = new Teacher(2L, "Anna", "Ermakova", "female", "AnnaErmakova@gmail.com", "Kaliningrad", 48,
				89215895789L, "no", "Assistant Lecturer");
		Teacher teacher3 = new Teacher(3L, "Roman", "Sidorov", "male", "RomanSidorov@gmail.com", "Moscow", 53,
				89112568975L, "Deputy Dean", "Doctor of Technical Science");
		Teacher teacher4 = new Teacher(4L, "Diana", "Gukova", "female", "DianaGukova@gmail.com", "Rostov", 52,
				89225896325L, "no", "Senior Lecturer");
		Teacher teacher5 = new Teacher(5L, "Dmitry", "Solodin", "male", "MikhailSolodin@gmail.com", "Andora", 56,
				89052655985L, "no", "Candidate of Technical Science");

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
				89313262896L, "Dean", "Professor");
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
				89112568975L, "Deputy Dean", "Doctor of Technical Science");
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
				89313262896L, "Dean", "Professor");
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
				89313262896L, "Dean", "Professor");

		Teacher teacher2 = new Teacher(2L, "Anna", "Ermakova", "female", "AnnaErmakova@gmail.com", "Kaliningrad", 48,
				89215895789L, "no", "Assistant Lecturer");
		Teacher teacher3 = new Teacher(3L, "Roman", "Sidorov", "male", "RomanSidorov@gmail.com", "Moscow", 53,
				89112568975L, "Deputy Dean", "Doctor of Technical Science");
		Teacher teacher4 = new Teacher(4L, "Diana", "Gukova", "female", "DianaGukova@gmail.com", "Rostov", 52,
				89225896325L, "no", "Senior Lecturer");
		Teacher teacher5 = new Teacher(5L, "Dmitry", "Solodin", "male", "MikhailSolodin@gmail.com", "Andora", 56,
				89052655985L, "no", "Candidate of Technical Science");

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

}

package ua.com.foxminded.university.dao.jdbc;

import javax.annotation.PostConstruct;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.jdbc.Sql;

import ua.com.foxminded.university.dao.SubjectDao;
import ua.com.foxminded.university.dao.mappers.SubjectMapper;
import ua.com.foxminded.university.model.Student;
import ua.com.foxminded.university.model.Subject;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@JdbcTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
class JdbcSubjectDaoTest {

	@Autowired
	JdbcTemplate jdbcTemplate;
	private SubjectDao dao;

	@PostConstruct
	void setUp() {
		dao = new JdbcSubjectDao(jdbcTemplate, new SubjectMapper());
	}

	@Test
	@Sql(scripts = { "/sql/clean_db.sql", "/sql/subjects_init_test_values.sql" })
	void shouldFindOneSubject() {

		Subject expected = new Subject(1L, "Theory of probability and mathematical statistics");
		Subject expected2 = new Subject(4L, "Strength of materials");
		assertThat(dao.findById(1L).get()).isEqualTo(expected);
		assertThat(dao.findById(4L).get()).isEqualTo(expected2);
	}

	@Test
	@Sql(scripts = { "/sql/clean_db.sql", "/sql/subjects_init_test_values.sql" })
	void shouldNotFindSubject() {
		assertThat(dao.findById(50L)).isEmpty();
	}

	@Test
	@Sql(scripts = { "/sql/clean_db.sql", "/sql/subjects_init_test_values.sql" })
	void shouldFindAllSubjects() {

		List<Subject> expected = Arrays.asList(new Subject(1L, "Theory of probability and mathematical statistics"),
				new Subject(2L, "Theoretical mechanics"), new Subject(3L, "Architecture"),
				new Subject(4L, "Strength of materials"), new Subject(5L, "SAPR"));
		List<Subject> actual = dao.findAll();
		assertThat(actual).isEqualTo(expected);
	}

	@Test
	@Sql(scripts = "/sql/clean_db.sql")
	void shouldNotFindAllSubject() {
		assertThat(dao.findAll().size()).isZero();
	}

	@Test
	@Sql(scripts = "/sql/clean_db.sql")
	void shouldInsertNewSubject() {
		Subject expected = new Subject("Theory of probability and mathematical statistics");
		Subject created = dao.save(expected);
		expected.setId(created.getId());
		assertThat(created).isEqualTo(expected);
	}

	@Test
	@Sql(scripts = { "/sql/clean_db.sql", "/sql/subjects_init_test_values.sql" })
	void shouldUpdateOneSubject() {

		Subject current = new Subject(2L, "Theoretical mechanics");
		Subject expected = new Subject(2L, "Structural mechanics");
		assertThat(dao.findById(2L).get()).isEqualTo(current);
		dao.save(expected);
		assertThat(dao.findById(2L).get()).isNotEqualTo(current);
		assertThat(dao.findById(2L).get()).isEqualTo(expected);
	}

	@Test
	@Sql(scripts = { "/sql/clean_db.sql", "/sql/subjects_init_test_values.sql" })
	void shouldDeleteSubjectById() {

		Subject expected = new Subject(4L, "Strength of materials");
		assertThat(dao.findById(4L)).get().isEqualTo(expected);
		assertThat(dao.findById(4L)).isPresent();
		dao.deleteById(4L);
		assertThat(dao.findById(4L)).isEmpty();
	}

	@Test
	@Sql(scripts = "/sql/clean_db.sql")
	void shouldNotDeleteSubjectByIdIfNOtExist() {
		assertThat(dao.findById(30L)).isNotPresent();
		assertThat(dao.findById(30L)).isEmpty();
		assertThat(assertThrows(IllegalArgumentException.class, () -> dao.deleteById(30L)).getMessage())
				.contains("Unable to delete item with id ", "30");
	}

	@Test
	@Sql(scripts = "/sql/clean_db.sql")
	void shouldBatchSaveNewSubjects() {

		List<Subject> expected = Arrays.asList(new Subject("Theory of probability and mathematical statistics"),
				new Subject("Theoretical mechanics"), new Subject("Architecture"), new Subject("Strength of materials"),
				new Subject("SAPR"));
		List<Subject> actual = dao.saveAll(expected);
		assertThat(actual.size()).isEqualTo(5);
		assertThat(actual.stream().filter(subject -> subject.getName() == expected.get(0).getName()).findAny())
				.isPresent();
		assertThat(actual.stream().filter(subject -> subject.getName() == expected.get(0).getName()).count())
				.isEqualTo(1);
		assertThat(actual.containsAll(expected));
		assertThat(actual.stream().filter(subject -> subject.getId() == null).collect(Collectors.toList()).size())
				.isEqualTo(0);
		assertThat(actual).isEqualTo(dao.findAll());
	}

	@Test
	@Sql(scripts = { "/sql/clean_db.sql", "/sql/subjects_init_test_values.sql" })
	void shouldBatchSaveExistAndNotExistSubjects() {

		List<Subject> toSave = Arrays.asList(new Subject("Geodesy"), new Subject("Geology"), new Subject("Economy"),
				new Subject(2L, "Math statistics"), new Subject("Informatics"));

		assertThat(dao.findAll().size()).isEqualTo(5);
		List<Subject> actual = dao.saveAll(toSave);
		assertThat(dao.findAll().size()).isEqualTo(9);
		assertThat(actual.stream().filter(subject -> subject.getId() == null).findAny()).isNotPresent();
	}

	@Test
	@Sql(scripts = { "/sql/clean_db.sql", "/sql/teachers_subjects_test_values.sql" })
	void shouldFindAllByTeacherId() {

		List<Subject> expected = Arrays.asList(new Subject(1L, "Theory of probability and mathematical statistics"),
				new Subject(2L, "Theoretical mechanics"), new Subject(3L, "Architecture"),
				new Subject(4L, "Strength of materials"));

		List<Subject> actual = dao.findAllSubjectsByTeacherId(1L);
		assertThat(actual).isEqualTo(expected);

	}

	@Test
	@Sql(scripts = { "/sql/clean_db.sql", "/sql/teachers_subjects_test_values.sql" })
	void shouldAddSubjectToTeacher() {

		List<Subject> expected = Arrays.asList(new Subject(1L, "Theory of probability and mathematical statistics"),
				new Subject(2L, "Theoretical mechanics"), new Subject(3L, "Architecture"),
				new Subject(4L, "Strength of materials"), new Subject(5L, "SAPR"));

		assertThat(dao.findAllSubjectsByTeacherId(1L)).isNotEqualTo(expected);
		dao.addSubjectToTeacher(1L, 5L);
		assertThat(dao.findAllSubjectsByTeacherId(1L)).isEqualTo(expected);
	}

	@Test
	@Sql(scripts = { "/sql/clean_db.sql", "/sql/teachers_subjects_test_values.sql" })
	void shouldDeleteSubjectFromTeacher() {

		List<Subject> expected = Arrays.asList(new Subject(1L, "Theory of probability and mathematical statistics"),
				new Subject(2L, "Theoretical mechanics"), new Subject(3L, "Architecture"));

		assertThat(dao.findAllSubjectsByTeacherId(1L)).isNotEqualTo(expected);
		dao.deleteSubjectFromTeacher(1L, 4L);
		assertThat(dao.findAllSubjectsByTeacherId(1L)).isEqualTo(expected);
	}

}

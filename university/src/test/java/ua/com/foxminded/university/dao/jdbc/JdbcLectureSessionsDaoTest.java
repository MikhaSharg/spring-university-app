package ua.com.foxminded.university.dao.jdbc;

import javax.annotation.PostConstruct;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.jdbc.Sql;

import ua.com.foxminded.university.dao.LectureSessionsDao;
import ua.com.foxminded.university.dao.mappers.LectureSessionsMapper;
import ua.com.foxminded.university.model.LectureSessions;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Arrays;
import java.util.List;

@JdbcTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
class JdbcLectureSessionsDaoTest {

	@Autowired
	JdbcTemplate jdbcTemplate;
	private LectureSessionsDao dao;

	@PostConstruct
	void setUp() {
		dao = new JdbcLectureSessionsDao(jdbcTemplate, new LectureSessionsMapper());
	}

	@Test
	@Sql(scripts = ("/sql/clean_db.sql"))
	void shouldInsertNewOne() {
		LectureSessions expected = new LectureSessions("1th", "8:00", "9:20");
		LectureSessions actual = dao.save(expected);
		expected.setId(actual.getId());
		assertThat(actual).isEqualTo(expected);
	}

	@Test
	@Sql(scripts = { "/sql/clean_db.sql", "/sql/sessions_test_values.sql" })
	void shouldFindOne() {

		LectureSessions expected = new LectureSessions(1L, "1th", "8:00", "9:20");
		assertThat(dao.findById(1L).get()).isEqualTo(expected);
	}

	@Test
	@Sql(scripts = { "/sql/clean_db.sql", "/sql/sessions_test_values.sql" })
	void shouldNotFindOne() {
		assertThat(dao.findById(50L)).isEmpty();
	}

	@Test
	@Sql(scripts = { "/sql/clean_db.sql", "/sql/sessions_test_values.sql" })
	void shouldFindAll() {

		List<LectureSessions> expected = Arrays.asList(new LectureSessions(1L, "1th", "8:00", "9:20"),
				new LectureSessions(2L, "2th", "9:30", "10:50"), new LectureSessions(3L, "3th", "11:00", "12:20"),
				new LectureSessions(4L, "4th", "13:00", "14:20"), new LectureSessions(5L, "5th", "14:30", "15:50"),
				new LectureSessions(6L, "6th", "16:00", "17:20"));
		List<LectureSessions> actual = dao.findAll();
		assertThat(actual).isEqualTo(expected);
	}

	@Test
	@Sql(scripts = "/sql/clean_db.sql")
	void shouldNotFindAll() {
		assertThat(dao.findAll().size()).isZero();
	}

	@Test
	@Sql(scripts = { "/sql/clean_db.sql", "/sql/sessions_test_values.sql" })
	void shouldUpdateOne() {

		LectureSessions current = new LectureSessions(1L, "1th", "8:00", "9:20");
		LectureSessions expected = new LectureSessions(1L, "1th+", "10:00", "12:00");
		assertThat(dao.findById(1L).get()).isEqualTo(current);
		dao.save(expected);
		assertThat(dao.findById(1L).get()).isNotEqualTo(current);
		assertThat(dao.findById(1L).get()).isEqualTo(expected);
	}

	@Test
	@Sql(scripts = { "/sql/clean_db.sql", "/sql/sessions_test_values.sql" })
	void shouldDeleteOneById() {

		LectureSessions expected = new LectureSessions(1L, "1th", "8:00", "9:20");
		assertThat(dao.findById(1L)).get().isEqualTo(expected);
		assertThat(dao.findById(1L)).isPresent();
		dao.deleteById(1L);
		assertThat(dao.findById(1L)).isEmpty();
	}

	@Test
	@Sql(scripts = "/sql/clean_db.sql")
	void shouldNotDeleteOneByIdIfNOtExist() {
		assertThat(dao.findById(30L)).isNotPresent();
		assertThat(dao.findById(30L)).isEmpty();
		assertThat(assertThrows(IllegalArgumentException.class, () -> dao.deleteById(30L)).getMessage())
				.contains("Unable to delete item with id ", "30");
	}

	@Test
	@Sql(scripts = { "/sql/clean_db.sql", "/sql/sessions_test_values.sql" })
	void shouldBatchSave() {

		List<LectureSessions> toSave = Arrays.asList(new LectureSessions(1L, "1th+", "7:00", "8:20"),
				new LectureSessions(2L, "2th+", "8:30", "9:50"), new LectureSessions(3L, "3th+", "10:00", "11:20"),
				new LectureSessions("7th", "17:30", "18:50"), new LectureSessions("8th", "19:00", "20:20"));

		List<LectureSessions> actual = dao.saveAll(toSave);
		assertThat(actual.size()).isEqualTo(5);
		List<LectureSessions> fromDB = dao.findAll();
		assertThat(fromDB.size()).isEqualTo(8);
		assertThat(fromDB.stream().filter(subject -> subject.getPeriod() == toSave.get(0).getPeriod()).findAny())
				.isPresent();
	}

}

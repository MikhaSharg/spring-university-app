package ua.com.foxminded.university.dao.jdbc;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.List;

import javax.annotation.PostConstruct;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.jdbc.Sql;

import ua.com.foxminded.university.dao.AudienceDao;
import ua.com.foxminded.university.dao.mappers.AudienceMapper;
import ua.com.foxminded.university.model.Audience;

import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;

@JdbcTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
class JdbcAudienceDaoTest {

	@Autowired
	JdbcTemplate jdbcTemplate;
	private AudienceDao dao;

	@PostConstruct
	void setUp() {
		dao = new JdbcAudienceDao(jdbcTemplate, new AudienceMapper());
	}

	@Test
	@Sql(scripts = { "/sql/clean_db.sql", "/sql/audiences_test_values.sql" })
	void shouldFindOneAudience() {
		Audience expected = new Audience(1L, 100);
		Audience actual = dao.findById(1L).get();
		assertThat(actual).isEqualTo(expected);
	}

	@Test
	@Sql(scripts = { "/sql/clean_db.sql", "/sql/audiences_test_values.sql" })
	void shouldNotFindOneAudience() {
		assertThat(dao.findById(50L)).isEmpty();
	}

	@Test
	@Sql(scripts = { "/sql/clean_db.sql", "/sql/audiences_test_values.sql" })
	void shouldFindAllAudiences() {

		List<Audience> expected = Arrays.asList(new Audience(1L, 100), new Audience(2L, 101), new Audience(3L, 102),
				new Audience(4L, 103), new Audience(5L, 104));

		List<Audience> actual = dao.findAll();
		assertThat(actual).isEqualTo(expected);
	}

	@Test
	@Sql(scripts = "/sql/clean_db.sql")
	void shouldNotFindAllAudiences() {
		assertThat(dao.findAll().size()).isZero();
	}

	@Test
	@Sql(scripts = { "/sql/clean_db.sql", "/sql/audiences_test_values.sql" })
	void shouldInsertNew() {
		Audience expected = new Audience(110);
		Audience created = dao.save(expected);
		expected.setId(created.getId());
		assertThat(created).isEqualTo(expected);
	}

	@Test
	@Sql(scripts = { "/sql/clean_db.sql", "/sql/audiences_test_values.sql" })
	void shouldUpdateOneAudience() {

		Audience expected = new Audience(1L, 110);
		assertThat(dao.findById(1L).get()).isNotEqualTo(expected);
		Audience actual = dao.save(expected);
		assertThat(dao.findById(1L).get()).isEqualTo(expected);
		assertThat(actual).isEqualTo(expected);
	}

	@Test
	@Sql(scripts = { "/sql/clean_db.sql", "/sql/audiences_test_values.sql" })
	void shouldDeleteOneAudienceById() {

		Audience audience = new Audience(1L, 100);

		assertThat(dao.findById(1L)).get().isEqualTo(audience);
		assertThat(dao.findById(1L)).isPresent();
		dao.deleteById(1L);
		assertThat(dao.findById(1L)).isEmpty();
	}

	@Test
	@Sql(scripts = "/sql/clean_db.sql")
	void shouldNotDeleteAudienceByIdIfNOtExist() {
		assertThat(dao.findById(30L)).isNotPresent();
		assertThat(dao.findById(30L)).isEmpty();
		assertThat(assertThrows(IllegalArgumentException.class, () -> dao.deleteById(30L)).getMessage())
				.contains("Unable to delete item with id ", "30");
	}

	@Test
	@Sql(scripts = { "/sql/clean_db.sql", "/sql/audiences_test_values.sql" })
	void shouldBatchSave() {

		List<Audience> toSave = Arrays.asList(new Audience(1L, 110), new Audience(111), new Audience(3L, 112),
				new Audience(114), new Audience(115));

		assertThat(dao.findAll().size()).isEqualTo(5);
		List<Audience> actual = dao.saveAll(toSave);
		List<Audience> audiencesFromDB = dao.findAll();

		assertThat(actual.size()).isEqualTo(5);
		assertThat(audiencesFromDB.size()).isEqualTo(8);
		assertThat(actual.stream().filter(subject -> subject.getId() == null).findAny()).isNotPresent();
		assertThat(audiencesFromDB.stream().filter(it -> it.getRoomNumber() == 111).findAny()).isPresent();
		assertThat(audiencesFromDB.stream().filter(it -> it.getRoomNumber() == 114).findAny()).isPresent();

	}

}

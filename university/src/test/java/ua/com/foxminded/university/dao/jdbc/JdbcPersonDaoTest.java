package ua.com.foxminded.university.dao.jdbc;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.jdbc.Sql;

import ua.com.foxminded.university.dao.PersonDao;
import ua.com.foxminded.university.dao.mappers.PersonMapper;
import ua.com.foxminded.university.model.Person;

@JdbcTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
class JdbcPersonDaoTest {

	@Autowired
	JdbcTemplate jdbcTemplate;
	private PersonDao dao;

	@PostConstruct
	void setUp() {
		dao = new JdbcPersonDao(jdbcTemplate, new PersonMapper());
	}

	@Test
	@Sql(scripts = { "/sql/clean_db.sql" })
	void shouldInsertNew() {
		Person person = new Person("Alex", "Petrov", "male", "AlexPetrov@gmail.com", "Saint Petersburg", 60,
				89523268951L, "dean");
		Person created = dao.save(person);
		person.setId(created.getId());
		assertThat(created).isEqualTo(person);
	}

	@Test
	@Sql(scripts = { "/sql/clean_db.sql", "/sql/persons_test_values.sql" })
	void shouldFindPerson() {
		Person expected = new Person(1L, "Alex", "Petrov", "male", "AlexPetrov@gmail.com", "Saint Petersburg", 60,
				89523268951L, "dean");
		Person actual = dao.findById(1L).get();
		assertThat(actual).isEqualTo(expected);
	}

	@Test
	@Sql(scripts = { "/sql/clean_db.sql", "/sql/persons_test_values.sql" })
	void shouldNotFindPerson() {
		assertThat(dao.findById(50L)).isEmpty();
	}

	@Test
	@Sql(scripts = { "/sql/clean_db.sql", "/sql/persons_test_values.sql" })
	void shouldFindAllPerson() {

		List<Person> expected = Arrays.asList(
				new Person(1L, "Alex", "Petrov", "male", "AlexPetrov@gmail.com", "Saint Petersburg", 60, 89523268951L,
						"dean"),
				new Person(2L, "Anna", "Ermakova", "female", "AnnaErmakova@gmail.com", "Kaliningrad", 50, 8952328575L,
						"administrator"),
				new Person(3L, "Roman", "Sidorov", "male", "RomanSidorov@gmail.com", "Moscow", 52, 89583658547L,
						"repairer"),
				new Person(4L, "Diana", "Gukova", "female", "DianaGukova@gmail.com", "Rostov", 40, 89538792563L,
						"cleaner"));

		List<Person> actual = dao.findAll();
		assertThat(actual).isEqualTo(expected);
	}

	@Test
	@Sql(scripts = "/sql/clean_db.sql")
	void shouldNotFindAllPersons() {
		assertThat(dao.findAll().size()).isZero();
	}

	@Test
	@Sql(scripts = { "/sql/clean_db.sql", "/sql/persons_test_values.sql" })
	void shouldUpdatePerson() {

		Person expected = new Person(4L, "Alina", "Bolkina", "female", "AlinaBolkina@gmail.com", "Samara", 39,
				89532566528L, "cleaner");
		assertThat(dao.findById(4L).get()).isNotEqualTo(expected);
		dao.save(expected);
		assertThat(dao.findById(4L).get()).isEqualTo(expected);
	}

	@Test
	@Sql(scripts = { "/sql/clean_db.sql", "/sql/persons_test_values.sql" })
	void shouldDeletePersonById() {
		Person expected = new Person(1L, "Alex", "Petrov", "male", "AlexPetrov@gmail.com", "Saint Petersburg", 60,
				89523268951L, "dean");
		assertThat(dao.findById(1L)).get().isEqualTo(expected);
		assertThat(dao.findById(1L)).isPresent();
		dao.deleteById(1L);
		assertThat(dao.findById(1L)).isEmpty();
	}

	@Test
	@Sql(scripts = "/sql/clean_db.sql")
	void shouldNotDeletePersonByIdIfNOtExist() {
		assertThat(dao.findById(30L)).isNotPresent();
		assertThat(dao.findById(30L)).isEmpty();
		assertThat(assertThrows(IllegalArgumentException.class, () -> dao.deleteById(30L)).getMessage())
				.contains("Unable to delete item with id ", "30");
	}

	@Test
	@Sql(scripts = { "/sql/clean_db.sql", "/sql/persons_test_values.sql" })
	void shouldBatchSave() {

		List<Person> toSave = Arrays.asList(
				new Person(2L, "Maksim", "Progor", "male", "MaksimProgor@gmail.com", "Novgorod", 50, 8952328575L,
						"administrator"),
				new Person("Aleksey", "Grudov", "male", "AlekseyGrudov@gmail.com", "Samara", 52, 89856895235L,
						"electrician"),
				new Person("Dmitry", "Sommer", "male", "DmitrySommer@gmail.com", "Kursk", 38, 89895325845L, "lector"));

		List<Person> actual = dao.saveAll(toSave);
		assertThat(dao.findAll().size()).isEqualTo(6);
		assertThat(actual.stream().filter(p -> p.getId() == null).collect(Collectors.toList()).isEmpty());
	}

}
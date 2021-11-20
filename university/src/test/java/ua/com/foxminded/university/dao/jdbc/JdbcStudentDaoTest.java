package ua.com.foxminded.university.dao.jdbc;

import javax.annotation.PostConstruct;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.jdbc.Sql;

import ua.com.foxminded.university.dao.StudentDao;
import ua.com.foxminded.university.dao.mappers.StudentMapper;
import ua.com.foxminded.university.model.Student;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;
import java.util.stream.Collectors;
import java.util.Arrays;

@JdbcTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
class JdbcStudentDaoTest {

	@Autowired
	JdbcTemplate jdbcTemplate;
	private StudentDao dao;

	@PostConstruct
	void setUp() {
		dao = new JdbcStudentDao(jdbcTemplate, new StudentMapper());
	}

	@Test
	@Sql(scripts = { "/sql/clean_db.sql", "/sql/students_init_test_values.sql" })
	void shouldInsertNew() {
		Student student = new Student("Alex", "Sidorov", "Male", "AlexSidorov@gmail.com", "Kaliningrad", 25,
				89313256895L, "student", 1L);
		Student created = dao.save(student);
		student.setId(created.getId());
		assertThat(created).isEqualTo(student);
	}

	@Test
	@Sql(scripts = { "/sql/clean_db.sql", "/sql/students_init_test_values.sql" })
	void shouldFindStudent() {
		Student expected = new Student(1L, "Alex", "Petrov", "male", "AlexPetrov@gmail.com", "Saint Petersburg", 25,
				89523268951L, "student", 1L);
		Student actual = dao.findById(1L).get();
		assertThat(actual).isEqualTo(expected);
	}

	@Test
	@Sql(scripts = { "/sql/clean_db.sql", "/sql/students_init_test_values.sql" })
	void shouldNotFindStudent() {
		assertThat(dao.findById(50L)).isEmpty();
	}

	@Test
	@Sql(scripts = { "/sql/clean_db.sql", "/sql/students_init_test_values.sql" })
	void shouldFindAllStudent() {

		List<Student> expected = Arrays.asList(
				new Student(1L, "Alex", "Petrov", "male", "AlexPetrov@gmail.com", "Saint Petersburg", 25, 89523268951L,
						"student", 1L),
				new Student(2L, "Anna", "Ermakova", "female", "AnnaErmakova@gmail.com", "Kaliningrad", 26, 8952328575L,
						"student", 2L),
				new Student(3L, "Roman", "Sidorov", "male", "RomanSidorov@gmail.com", "Moscow", 23, 89583658547L,
						"student", 1L),
				new Student(4L, "Diana", "Gukova", "female", "DianaGukova@gmail.com", "Rostov", 21, 89538792563L,
						"student", 2L),
				new Student(5L, "Dmitry", "Solodin", "male", "MikhailSolodin@gmail.com", "Andora", 35, 89528769523L,
						"student", 1L));

		List<Student> actual = dao.findAll();
		assertThat(actual).isEqualTo(expected);
	}

	@Test
	@Sql(scripts = "/sql/clean_db.sql")
	void shouldNotFindAllStudent() {
		assertThat(dao.findAll().size()).isZero();
	}

	@Test
	@Sql(scripts = { "/sql/clean_db.sql", "/sql/students_init_test_values.sql" })
	void shouldUpdateStudent() {

		Student expected = new Student(3L, "Alex", "Sidorov", "Male", "AlexSidorov@gmail.com", "Kaliningrad", 25,
				89313256895L, "student", 1L);
		assertThat(dao.findById(3L).get()).isNotEqualTo(expected);
		dao.save(expected);
		assertThat(dao.findById(3L).get()).isEqualTo(expected);
	}

	@Test
	@Sql(scripts = { "/sql/clean_db.sql", "/sql/students_init_test_values.sql" })
	void shouldDeleteStudentById() {
		Student expected = new Student(1L, "Alex", "Petrov", "male", "AlexPetrov@gmail.com", "Saint Petersburg", 25,
				89523268951L, "student", 1L);
		assertThat(dao.findById(1L)).get().isEqualTo(expected);
		assertThat(dao.findById(1L)).isPresent();
		dao.deleteById(1L);
		assertThat(dao.findById(1L)).isEmpty();
	}

	@Test
	@Sql(scripts = "/sql/clean_db.sql")
	void shouldNotDeleteStudentByIdIfNOtExist() {
		assertThat(dao.findById(30L)).isNotPresent();
		assertThat(dao.findById(30L)).isEmpty();
		assertThat(assertThrows(IllegalArgumentException.class, () -> dao.deleteById(30L)).getMessage())
				.contains("Unable to delete item with id ", "30");
	}

	@Test
	@Sql(scripts = { "/sql/clean_db.sql", "/sql/students_init_test_values.sql" })
	void shouldBatchSave() {
		List<Student> expected = Arrays.asList(
				new Student(1L, "Viktor", "Kim", "male", "ViktorKim@gmail.com", "Svetlogorsk", 21, 89316589853L,
						"student", 1L),

				new Student(2L, "Andrey", "Kunec", "male", "AndreyKunec@gmail.com", "Novgorod", 22, 89528975236L,
						"student", 2L),

				new Student(3L, "Olga", "Pestrecova", "female", "OlgaPestrecova@gmail.com", "Kaliningrad", 25,
						89625879654L, "student", 1L),

				new Student(4L, "Maksim", "Progor", "male", "MaksimProgor@gmail.com", "Greboedov", 27, 8906258943L,
						"student", 2L),

				new Student(5L, "Ganna", "Golikiova", "female", "GannaGolikova@gmail.com", "Yalta", 29, 892265889122L,
						"student", 1L),

				new Student(6L, "Alexander", "Osipov", "male", "AlexanderOsipov@gmail.com", "Omsk", 25, 89526895624L,
						"student", 1L),

				new Student(7L, "Pavel", "Petrov", "male", "PavelPetrov@gmail.com", "Cuba", 35, 898565248566L,
						"student", 1L),

				new Student(8L, "Aleksey", "Grudov", "male", "AlekseyGrudov@gmail.com", "Samara", 28, 89856895235L,
						"student", 2L),

				new Student(9L, "Dmitry", "Sommer", "male", "DmitrySommer@gmail.com", "Kursk", 21, 89895325845L,
						"student", 1L),

				new Student(10L, "Alisa", "Egorova", "female", "AlisaEgorova@gmail.com", "Tomsk", 31, 89225683268L,
						"student", 2L));

		List<Student> toSave = Arrays.asList(
				new Student(1L, "Viktor", "Kim", "male", "ViktorKim@gmail.com", "Svetlogorsk", 21, 89316589853L,
						"student", 1L),

				new Student(2L, "Andrey", "Kunec", "male", "AndreyKunec@gmail.com", "Novgorod", 22, 89528975236L,
						"student", 2L),

				new Student(3L, "Olga", "Pestrecova", "female", "OlgaPestrecova@gmail.com", "Kaliningrad", 25,
						89625879654L, "student", 1L),

				new Student(4L, "Maksim", "Progor", "male", "MaksimProgor@gmail.com", "Greboedov", 27, 8906258943L,
						"student", 2L),

				new Student(5L, "Ganna", "Golikiova", "female", "GannaGolikova@gmail.com", "Yalta", 29, 892265889122L,
						"student", 1L),

				new Student("Alexander", "Osipov", "male", "AlexanderOsipov@gmail.com", "Omsk", 25, 89526895624L,
						"student", 1L),

				new Student("Pavel", "Petrov", "male", "PavelPetrov@gmail.com", "Cuba", 35, 898565248566L, "student",
						1L),

				new Student("Aleksey", "Grudov", "male", "AlekseyGrudov@gmail.com", "Samara", 28, 89856895235L,
						"student", 2L),

				new Student("Dmitry", "Sommer", "male", "DmitrySommer@gmail.com", "Kursk", 21, 89895325845L, "student",
						1L),

				new Student("Alisa", "Egorova", "female", "AlisaEgorova@gmail.com", "Tomsk", 31, 89225683268L,
						"student", 2L));

		List<Student> actual = dao.saveAll(toSave);
		assertThat(actual.size()).isEqualTo(toSave.size());
		assertThat(actual.stream().filter(item -> item.getId() == null).count()).isZero();
		assertThat(actual.stream().filter(item -> item.getFirstName() == "Viktor").count() == 1);
		assertThat(actual.stream().filter(item -> item.getFirstName() == "Andrey" && item.getLastName() == "Kunec")
				.collect(Collectors.toList()).get(0)).isEqualTo(expected.get(1));
	}

	@Test
	@Sql(scripts = { "/sql/clean_db.sql", "/sql/students_init_test_values.sql" })
	void shouldFindStudentsBuGroupId() {

		List<Student> expected = Arrays.asList(
				new Student(1L, "Alex", "Petrov", "male", "AlexPetrov@gmail.com", "Saint Petersburg", 25, 89523268951L,
						"student", 1L),
				new Student(3L, "Roman", "Sidorov", "male", "RomanSidorov@gmail.com", "Moscow", 23, 89583658547L,
						"student", 1L),
				new Student(5L, "Dmitry", "Solodin", "male", "MikhailSolodin@gmail.com", "Andora", 35, 89528769523L,
						"student", 1L));

		List<Student> actual = dao.findAllStudentsByGroupId(1L);
		assertThat(actual).isEqualTo(expected);

	}

}

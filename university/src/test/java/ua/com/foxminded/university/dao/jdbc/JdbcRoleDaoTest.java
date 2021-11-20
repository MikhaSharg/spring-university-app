package ua.com.foxminded.university.dao.jdbc;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.List;

import javax.annotation.PostConstruct;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.jdbc.Sql;

import ua.com.foxminded.university.dao.RoleDao;
import ua.com.foxminded.university.dao.mappers.RoleMapper;
import ua.com.foxminded.university.model.Role;

@JdbcTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
class JdbcRoleDaoTest {

	@Autowired
	JdbcTemplate jdbcTemplate;
	private RoleDao dao;

	@PostConstruct
	void setUp() {
		dao = new JdbcRoleDao(jdbcTemplate, new RoleMapper());
	}

	@Test
	@Sql(scripts = ("/sql/clean_db.sql"))
	void shouldInsertNewOne() {
		Role expected = new Role("dean");
		Role actual = dao.save(expected);
		expected.setId(actual.getId());
		assertThat(actual).isEqualTo(expected);
	}

	@Test
	@Sql(scripts = { "/sql/clean_db.sql", "/sql/roles_test_values.sql" })
	void shouldFindOne() {

		Role expected = new Role(2L, "electrician");
		assertThat(dao.findById(2L).get()).isEqualTo(expected);
	}

	@Test
	@Sql(scripts = { "/sql/clean_db.sql", "/sql/roles_test_values.sql" })
	void shouldNotFindOne() {
		assertThat(dao.findById(50L)).isEmpty();
	}

	@Test
	@Sql(scripts = { "/sql/clean_db.sql", "/sql/roles_test_values.sql" })
	void shouldFindAll() {

		List<Role> expected = Arrays.asList(new Role(1L, "dean"), new Role(2L, "electrician"), new Role(3L, "repairer"),
				new Role(4L, "cleaner"));

		List<Role> actual = dao.findAll();
		assertThat(actual).isEqualTo(expected);
	}

	@Test
	@Sql(scripts = "/sql/clean_db.sql")
	void shouldNotFindAll() {
		assertThat(dao.findAll().size()).isZero();
	}

	@Test
	@Sql(scripts = { "/sql/clean_db.sql", "/sql/roles_test_values.sql" })
	void shouldUpdateOne() {

		Role current = new Role(4L, "cleaner");
		Role expected = new Role(4L, "washer");
		assertThat(dao.findById(4L).get()).isEqualTo(current);
		dao.save(expected);
		assertThat(dao.findById(4L).get()).isNotEqualTo(current);
		assertThat(dao.findById(4L).get()).isEqualTo(expected);
	}

	@Test
	@Sql(scripts = { "/sql/clean_db.sql", "/sql/roles_test_values.sql" })
	void shouldDeleteOneById() {

		Role expected = new Role(1L, "dean");
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
	@Sql(scripts = { "/sql/clean_db.sql", "/sql/roles_test_values.sql" })
	void shouldBatchSave() {

		List<Role> toSave = Arrays.asList(new Role("rector"), new Role("scientist"), new Role(3L, "woodworker"),
				new Role(4L, "washer"));

		List<Role> actual = dao.saveAll(toSave);
		assertThat(actual.size()).isEqualTo(4);
		assertThat(dao.findAll().size()).isEqualTo(6);
	}

	@Test
	@Sql(scripts = { "/sql/clean_db.sql", "/sql/students_roles_test_values.sql" })
	void shouldFindAllRolesForOneStudentByStudentId() {

		List<Role> expected = Arrays.asList(new Role(3L, "repairer"), new Role(5L, "administrator"));
		assertThat(dao.findRolesForStudent(1L)).isEqualTo(expected);

	}

	@Test
	@Sql(scripts = { "/sql/clean_db.sql", "/sql/students_roles_test_values.sql" })
	void shouldAddRoleToStudent() {

		List<Role> expected = Arrays.asList(new Role(3L, "repairer"), new Role(5L, "administrator"),
				new Role(4L, "methodist"));
		List<Role> actual = dao.findRolesForStudent(1L);
		assertThat(actual).isNotEqualTo(expected);
		assertThat(actual.size()).isEqualTo(2);
		dao.addRoleToStudent(1L, 4L);
		assertThat(dao.findRolesForStudent(1L).size()).isEqualTo(3);

		assertTrue(expected.containsAll(dao.findRolesForStudent(1L)));
	}

	@Test
	@Sql(scripts = { "/sql/clean_db.sql", "/sql/students_roles_test_values.sql" })
	void shouldDeleteRoleFromStudent() {

		Role expected = new Role(5L, "administrator");
		assertTrue(dao.findRolesForStudent(1L).contains(expected));
		dao.deleteRoleFromStudent(1L, 5L);
		assertFalse(dao.findRolesForStudent(1L).contains(expected));
	}

	@Test
	@Sql(scripts = { "/sql/clean_db.sql", "/sql/teachers_roles_test_values.sql" })
	void shouldFindAllRolesForOneTeacherByTeacherId() {

		List<Role> expected = Arrays.asList(new Role(3L, "repairer"), new Role(5L, "administrator"));
		assertThat(dao.findRolesForTeacher(1L)).isEqualTo(expected);

	}

	@Test
	@Sql(scripts = { "/sql/clean_db.sql", "/sql/teachers_roles_test_values.sql" })
	void shouldAddRoleToTeacher() {

		List<Role> expected = Arrays.asList(new Role(3L, "repairer"), new Role(5L, "administrator"),
				new Role(4L, "methodist"));
		List<Role> actual = dao.findRolesForTeacher(1L);
		assertThat(actual).isNotEqualTo(expected);
		assertThat(actual.size()).isEqualTo(2);
		dao.addRoleToTeacher(1L, 4L);
		assertThat(dao.findRolesForTeacher(1L).size()).isEqualTo(3);

		assertTrue(expected.containsAll(dao.findRolesForTeacher(1L)));
	}

	@Test
	@Sql(scripts = { "/sql/clean_db.sql", "/sql/teachers_roles_test_values.sql" })
	void shouldDeleteRoleFromTeacher() {

		Role expected = new Role(5L, "administrator");
		assertTrue(dao.findRolesForTeacher(1L).contains(expected));
		dao.deleteRoleFromTeacher(1L, 5L);
		assertFalse(dao.findRolesForTeacher(1L).contains(expected));
	}
}

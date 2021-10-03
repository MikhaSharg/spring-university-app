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
    @Sql(scripts = { "/clean_db.sql", "/students_init_test_values.sql" })
    void shouldInsertNew() {
        Student student = new Student("Alex", "Sidorov", "Male", "AlexSidorov@gmail.com", "Kaliningrad",
                Integer.valueOf(25), Long.valueOf(89313256895L), "no", Long.valueOf(1));
        Student created = dao.save(student);
        student.setId(created.getId());
        System.out.println("Student ID is " + student.getId());
        assertThat(created).isEqualTo(student);
    }
}

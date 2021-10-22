package ua.com.foxminded.university.dao.jdbc;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;

import ua.com.foxminded.university.dao.TeacherDao;
import ua.com.foxminded.university.dao.mappers.TeacherMapper;
import ua.com.foxminded.university.model.Student;
import ua.com.foxminded.university.model.Subject;
import ua.com.foxminded.university.model.Teacher;

import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.jdbc.Sql;

@JdbcTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
class JdbcTeacherDaoTest {

    @Autowired
    JdbcTemplate jdbcTemplate;
    private TeacherDao dao;

    @PostConstruct
    void setUp() {
        dao = new JdbcTeacherDao(jdbcTemplate, new TeacherMapper());
    }

    @Test
    @Sql(scripts = { "/sql/clean_db.sql", "/sql/teachers_test_values.sql" })
    void shouldFindOneTeacherWithListOfSubjects() {

        List<Subject> subjects = Arrays.asList(new Subject(1L, "math"), new Subject(2L, "astronomy"),
                new Subject(3L, "probability theory"), new Subject(4L, "history"));
        Teacher expected = new Teacher(1L, "Alex", "Petrov", "male", "AlexPetrov@gmail.com", "Saint Petersburg", 25,
                89313262896L, "no", "one", subjects);
        Teacher actual = dao.findById(1L).get();
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    @Sql(scripts = { "/sql/clean_db.sql", "/students_init_test_values.sql" })
    void shouldNotFindOneTeacher() {
        assertThat(dao.findById(50L)).isEmpty();
    }

    @Test
    @Sql(scripts = { "/sql/clean_db.sql", "/sql/teachers_test_values.sql" })
    void shouldfindAllTeachersIfExistOnlyOne() {

        List<Subject> subjects = Arrays.asList(new Subject(1L, "math"), new Subject(2L, "astronomy"),
                new Subject(3L, "probability theory"), new Subject(4L, "history"));
        Teacher teacher = new Teacher(1L, "Alex", "Petrov", "male", "AlexPetrov@gmail.com", "Saint Petersburg", 25,
                89313262896L, "no", "one", subjects);
        List<Teacher> expected = Arrays.asList(teacher);
        List<Teacher> actual = dao.findAll();
        assertThat(actual).isEqualTo(expected);

    }

    @Test
    @Sql(scripts = { "/sql/clean_db.sql", "/sql/teachers_test_values_1.sql" })
    void shouldfindAllTeachersIfLastTeacherDoesntHaveSubjects() {

        List<Subject> subjects1 = Arrays.asList(new Subject(1L, "math"), new Subject(2L, "astronomy"),
                new Subject(3L, "probability theory"), new Subject(4L, "history"));
        Teacher teacher1 = new Teacher(1L, "Alex", "Petrov", "male", "AlexPetrov@gmail.com", "Saint Petersburg", 25,
                89313262896L, "no", "one", subjects1);

        List<Subject> subjects2 = Arrays.asList(new Subject(2L, "astronomy"), new Subject(3L, "probability theory"));
        Teacher teacher2 = new Teacher(2L, "Anna", "Ermakova", "female", "AnnaErmakova@gmail.com", "Kaliningrad", 26,
                89215895789L, "no", "two", subjects2);

        List<Subject> subjects3 = Arrays.asList(new Subject(4L, "history"));
        Teacher teacher3 = new Teacher(3L, "Roman", "Sidorov", "male", "RomanSidorov@gmail.com", "Moscow", 23,
                89112568975L, "no", "three", subjects3);

        List<Subject> subjects4 = Arrays.asList(new Subject(1L, "math"), new Subject(4L, "history"));
        Teacher teacher4 = new Teacher(4L, "Diana", "Gukova", "female", "DianaGukova@gmail.com", "Rostov", 21,
                89225896325L, "no", "four", subjects4);

        List<Subject> subjects5 = new ArrayList<>();
        Teacher teacher5 = new Teacher(5L, "Dmitry", "Solodin", "male", "MikhailSolodin@gmail.com", "Andora", 35,
                89052655985L, "student", "five", subjects5);

        List<Teacher> expected = Arrays.asList(teacher1, teacher2, teacher3, teacher4, teacher5);
        List<Teacher> actual = dao.findAll();
        assertThat(actual).isEqualTo(expected);

    }

    @Test
    @Sql(scripts = { "/sql/clean_db.sql", "/sql/teachers_test_values_2.sql" })
    void shouldfindAllTeachersIfSomeTeachersHaveSubjects() {

        List<Subject> subjects1 = new ArrayList<>();
        Teacher teacher1 = new Teacher(1L, "Alex", "Petrov", "male", "AlexPetrov@gmail.com", "Saint Petersburg", 25,
                89313262896L, "no", "one", subjects1);

        List<Subject> subjects2 = Arrays.asList(new Subject(2L, "astronomy"), new Subject(3L, "probability theory"));
        Teacher teacher2 = new Teacher(2L, "Anna", "Ermakova", "female", "AnnaErmakova@gmail.com", "Kaliningrad", 26,
                89215895789L, "no", "two", subjects2);

        List<Subject> subjects3 = new ArrayList<>();
        Teacher teacher3 = new Teacher(3L, "Roman", "Sidorov", "male", "RomanSidorov@gmail.com", "Moscow", 23,
                89112568975L, "no", "three", subjects3);

        List<Subject> subjects4 = Arrays.asList(new Subject(1L, "math"), new Subject(4L, "history"));
        Teacher teacher4 = new Teacher(4L, "Diana", "Gukova", "female", "DianaGukova@gmail.com", "Rostov", 21,
                89225896325L, "no", "four", subjects4);

        List<Subject> subjects5 = new ArrayList<>();
        Teacher teacher5 = new Teacher(5L, "Dmitry", "Solodin", "male", "MikhailSolodin@gmail.com", "Andora", 35,
                89052655985L, "student", "five", subjects5);

        List<Teacher> expected = Arrays.asList(teacher1, teacher2, teacher3, teacher4, teacher5);
        List<Teacher> actual = dao.findAll();
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    @Sql(scripts = { "/sql/clean_db.sql", "/sql/teachers_test_values_3.sql" })
    void shouldfindAllTeachersIfAllTeachersDontHaveSubjects() {

        List<Subject> subjects1 = new ArrayList<>();
        Teacher teacher1 = new Teacher(1L, "Alex", "Petrov", "male", "AlexPetrov@gmail.com", "Saint Petersburg", 25,
                89313262896L, "no", "one", subjects1);

        List<Subject> subjects2 = new ArrayList<>();
        Teacher teacher2 = new Teacher(2L, "Anna", "Ermakova", "female", "AnnaErmakova@gmail.com", "Kaliningrad", 26,
                89215895789L, "no", "two", subjects2);

        List<Subject> subjects3 = new ArrayList<>();
        Teacher teacher3 = new Teacher(3L, "Roman", "Sidorov", "male", "RomanSidorov@gmail.com", "Moscow", 23,
                89112568975L, "no", "three", subjects3);

        List<Subject> subjects4 = new ArrayList<>();
        Teacher teacher4 = new Teacher(4L, "Diana", "Gukova", "female", "DianaGukova@gmail.com", "Rostov", 21,
                89225896325L, "no", "four", subjects4);

        List<Subject> subjects5 = new ArrayList<>();
        Teacher teacher5 = new Teacher(5L, "Dmitry", "Solodin", "male", "MikhailSolodin@gmail.com", "Andora", 35,
                89052655985L, "student", "five", subjects5);

        List<Teacher> expected = Arrays.asList(teacher1, teacher2, teacher3, teacher4, teacher5);
        List<Teacher> actual = dao.findAll();
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    @Sql(scripts = { "/sql/clean_db.sql", "/sql/teachers_test_values_4.sql" })
    void shouldfindAllTeachersIfAllTeachersHaveSubjects() {

        List<Subject> subjects1 = Arrays.asList(new Subject(1L, "math"), new Subject(2L, "astronomy"),
                new Subject(3L, "probability theory"), new Subject(4L, "history"));
        Teacher teacher1 = new Teacher(1L, "Alex", "Petrov", "male", "AlexPetrov@gmail.com", "Saint Petersburg", 25,
                89313262896L, "no", "one", subjects1);

        List<Subject> subjects2 = Arrays.asList(new Subject(2L, "astronomy"), new Subject(3L, "probability theory"));
        Teacher teacher2 = new Teacher(2L, "Anna", "Ermakova", "female", "AnnaErmakova@gmail.com", "Kaliningrad", 26,
                89215895789L, "no", "two", subjects2);

        List<Subject> subjects3 = Arrays.asList(new Subject(4L, "history"));
        Teacher teacher3 = new Teacher(3L, "Roman", "Sidorov", "male", "RomanSidorov@gmail.com", "Moscow", 23,
                89112568975L, "no", "three", subjects3);

        List<Subject> subjects4 = Arrays.asList(new Subject(1L, "math"), new Subject(4L, "history"));
        Teacher teacher4 = new Teacher(4L, "Diana", "Gukova", "female", "DianaGukova@gmail.com", "Rostov", 21,
                89225896325L, "no", "four", subjects4);

        List<Subject> subjects5 = Arrays.asList(new Subject(1L, "math"), new Subject(3L, "probability theory"),
                new Subject(4L, "history"));
        Teacher teacher5 = new Teacher(5L, "Dmitry", "Solodin", "male", "MikhailSolodin@gmail.com", "Andora", 35,
                89052655985L, "student", "five", subjects5);

        List<Teacher> expected = Arrays.asList(teacher1, teacher2, teacher3, teacher4, teacher5);
        List<Teacher> actual = dao.findAll();
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    @Sql(scripts = "/sql/clean_db.sql")
    void shouldNotFindAllTeachers() {
        assertThat(dao.findAll().size()).isZero();
    }

    @Test
    @Sql(scripts = "/sql/clean_db.sql")
    void shouldInsertNewTeacher() {
        Teacher expected = new Teacher("Alex", "Petrov", "male", "AlexPetrov@gmail.com", "Saint Petersburg", 25,
                89313262896L, "no", "one");
        Teacher created = dao.save(expected);
        expected.setId(created.getId());
        assertThat(created).isEqualTo(expected);
    }

    @Test
    @Sql(scripts = { "/sql/clean_db.sql", "/sql/teachers_test_values_4.sql" })
    void shouldUpdateOneTeacher() {

        Teacher expected = new Teacher(3L, "Elena", "Pestrecova", "female", "ElenaPestrecova@gmail.com", "Omsk", 28,
                89658953265L, "no", "technology");

        assertThat(dao.findById(3L).get()).isNotEqualTo(expected);
        Teacher actual = dao.save(expected);
        assertThat(actual).isEqualTo(expected);
        assertThat(dao.findById(3L).get()).isEqualTo(expected);
    }

    @Test
    @Sql(scripts = { "/sql/clean_db.sql", "/sql/teachers_test_values_4.sql" })
    void shouldDeleteOneTeacherById() {

        List<Subject> subjects = Arrays.asList(new Subject(1L, "math"), new Subject(2L, "astronomy"),
                new Subject(3L, "probability theory"), new Subject(4L, "history"));
        Teacher teacher = new Teacher(1L, "Alex", "Petrov", "male", "AlexPetrov@gmail.com", "Saint Petersburg", 25,
                89313262896L, "no", "one", subjects);

        assertThat(dao.findById(1L)).get().isEqualTo(teacher);
        assertThat(dao.findById(1L)).isPresent();
        dao.deleteById(1L);
        assertThat(dao.findById(1L)).isEmpty();
    }

    @Test
    @Sql(scripts = "/sql/clean_db.sql")
    void shouldNotDeleteTeacherByIdIfNOtExist() {
        assertThat(dao.findById(30L)).isNotPresent();
        assertThat(dao.findById(30L)).isEmpty();
        assertThat(assertThrows(IllegalArgumentException.class, () -> dao.deleteById(30L)).getMessage())
                .contains("Unable to delete item with id ", "30");
    }

    @Test
    @Sql(scripts = "/sql/clean_db.sql")
    void shouldBatchSave() {

        List<Teacher> expected = Arrays.asList(
                new Teacher(1L, "Alex", "Petrov", "male", "AlexPetrov@gmail.com", "Saint Petersburg", 25, 89313262896L,
                        "no", "one"),

                new Teacher(2L, "Anna", "Ermakova", "female", "AnnaErmakova@gmail.com", "Kaliningrad", 26, 89215895789L,
                        "no", "two"),

                new Teacher(3L, "Roman", "Sidorov", "male", "RomanSidorov@gmail.com", "Moscow", 23, 89112568975L, "no",
                        "three"),

                new Teacher(4L, "Diana", "Gukova", "female", "DianaGukova@gmail.com", "Rostov", 21, 89225896325L, "no",
                        "four"),

                new Teacher(5L, "Dmitry", "Solodin", "male", "MikhailSolodin@gmail.com", "Andora", 35, 89052655985L,
                        "student", "five"));

        List<Teacher> toSave = Arrays.asList(
                new Teacher(1L, "Alex", "Petrov", "male", "AlexPetrov@gmail.com", "Saint Petersburg", 25, 89313262896L,
                        "no", "one"),

                new Teacher("Anna", "Ermakova", "female", "AnnaErmakova@gmail.com", "Kaliningrad", 26, 89215895789L,
                        "no", "two"),

                new Teacher(3L, "Roman", "Sidorov", "male", "RomanSidorov@gmail.com", "Moscow", 23, 89112568975L, "no",
                        "three"),

                new Teacher(4L, "Diana", "Gukova", "female", "DianaGukova@gmail.com", "Rostov", 21, 89225896325L, "no",
                        "four"),

                new Teacher("Dmitry", "Solodin", "male", "MikhailSolodin@gmail.com", "Andora", 35, 89052655985L,
                        "student", "five"));

        List<Teacher> actual = dao.saveAll(toSave);
        assertThat(actual.size()).isEqualTo(toSave.size());
        assertThat(actual.stream().filter(item -> item.getId() == null).count()).isZero();
        assertThat(actual.stream().filter(item -> item.getFirstName() == "Alex" && item.getLastName() == "Petrov")
                .collect(Collectors.toList()).get(0)).isEqualTo(expected.get(0));
        assertThat(actual.stream().filter(item -> item.getFirstName() == "Diana" && item.getLastName() == "Gukova")
                .collect(Collectors.toList()).get(0)).isEqualTo(expected.get(3));

    }

}

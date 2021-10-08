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
import static org.junit.jupiter.api.Assertions.assertEquals;
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
    @Sql(scripts = { "/clean_db.sql", "/students_init_test_values.sql" })
    void shouldInsertNew() {
        Student student = new Student("Alex", "Sidorov", "Male", "AlexSidorov@gmail.com", "Kaliningrad",
                Integer.valueOf(25), Long.valueOf(89313256895L), "no", Long.valueOf(1));
        Student created = dao.save(student);
        student.setId(created.getId());
        assertThat(created).isEqualTo(student);
    }

    @Test
    @Sql(scripts = { "/clean_db.sql", "/students_init_test_values.sql" })
    void shouldFindStudent() {
        Student expected = new Student(Long.valueOf(1L), "Alex", "Petrov", "male", "AlexPetrov@gmail.com",
                "Saint Petersburg", Integer.valueOf(25), Long.valueOf(89523268951L), "no", Long.valueOf(1L));
        Student actual = dao.findById(1L).get();
        assertThat(actual).isEqualTo(expected);

    }

    @Test
    @Sql(scripts = { "/clean_db.sql", "/students_init_test_values.sql" })
    void shouldNotFindStudent() {
        assertThat(dao.findById(50L)).isEmpty();
    }

    @Test
    @Sql(scripts = { "/clean_db.sql", "/students_init_test_values.sql" })
    void shouldFindAllStudent() {

        List<Student> expected = Arrays.asList(
                new Student(Long.valueOf(1L), "Alex", "Petrov", "male", "AlexPetrov@gmail.com", "Saint Petersburg",
                        Integer.valueOf(25), Long.valueOf(89523268951L), "no", Long.valueOf(1L)),
                new Student(Long.valueOf(2L), "Anna", "Ermakova", "female", "AnnaErmakova@gmail.com", "Kaliningrad",
                        Integer.valueOf(26), Long.valueOf(8952328575L), "teacher", Long.valueOf(2L)),
                new Student(Long.valueOf(3L), "Roman", "Sidorov", "male", "RomanSidorov@gmail.com", "Moscow",
                        Integer.valueOf(23), Long.valueOf(89583658547L), "no", Long.valueOf(1L)),
                new Student(Long.valueOf(4L), "Diana", "Gukova", "female", "DianaGukova@gmail.com", "Rostov",
                        Integer.valueOf(21), Long.valueOf(89538792563L), "no", Long.valueOf(2L)),
                new Student(Long.valueOf(5L), "Dmitry", "Solodin", "male", "MikhailSolodin@gmail.com", "Andora",
                        Integer.valueOf(35), Long.valueOf(89528769523L), "teacher", Long.valueOf(1L)));

        List<Student> actual = dao.findAll();
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    @Sql(scripts = "/clean_db.sql")
    void shouldNotFindAllStudent() {
        assertThat(dao.findAll().size()).isZero();
    }

    @Test
    @Sql(scripts = { "/clean_db.sql", "/students_init_test_values.sql" })
    void shouldUpdateStudent() {

        Student expected = new Student(Long.valueOf(3L), "Alex", "Sidorov", "Male", "AlexSidorov@gmail.com",
                "Kaliningrad", Integer.valueOf(25), Long.valueOf(89313256895L), "no", Long.valueOf(1));
        assertThat(dao.findById(3L).get()).isNotEqualTo(expected);
        dao.save(expected);
        assertThat(dao.findById(3L).get()).isEqualTo(expected);
    }

    @Test
    @Sql(scripts = { "/clean_db.sql", "/students_init_test_values.sql" })
    void shouldDeleteStudentById() {
        Student expected = new Student(Long.valueOf(1L), "Alex", "Petrov", "male", "AlexPetrov@gmail.com",
                "Saint Petersburg", Integer.valueOf(25), Long.valueOf(89523268951L), "no", Long.valueOf(1L));
        assertThat(dao.findById(1L)).get().isEqualTo(expected);
        assertThat(dao.findById(1L)).isPresent();
        dao.deleteById(1L);
        assertThat(dao.findById(1L)).isEmpty();
    }

    @Test
    @Sql(scripts = "/clean_db.sql")
    void shouldNotDeleteStudentByIdIfNOtExist() {
        assertThat(dao.findById(30L)).isNotPresent();
        assertThat(dao.findById(30L)).isEmpty();
        assertThat(assertThrows(IllegalArgumentException.class, () -> dao.deleteById(30L)).getMessage())
                .contains("Unable to delete item with id ", "30");
    }

    @Test
    @Sql(scripts = { "/clean_db.sql", "/students_init_test_values.sql" })
    void shouldBatchSave() {
        List<Student> expected = Arrays.asList(
                new Student(Long.valueOf(1L), "Viktor", "Kim", "male", "ViktorKim@gmail.com", "Svetlogorsk",
                        Integer.valueOf(21), Long.valueOf(89316589853L), "no", Long.valueOf(1L)),

                new Student(Long.valueOf(2L), "Andrey", "Kunec", "male", "AndreyKunec@gmail.com", "Novgorod",
                        Integer.valueOf(22), Long.valueOf(89528975236L), "teacher", Long.valueOf(2L)),

                new Student(Long.valueOf(3L), "Olga", "Pestrecova", "female", "OlgaPestrecova@gmail.com", "Kaliningrad",
                        Integer.valueOf(25), Long.valueOf(89625879654L), "no", Long.valueOf(1L)),

                new Student(Long.valueOf(4L), "Maksim", "Progor", "male", "MaksimProgor@gmail.com", "Greboedov",
                        Integer.valueOf(27), Long.valueOf(8906258943L), "technician", Long.valueOf(2L)),

                new Student(Long.valueOf(5L), "Ganna", "Golikiova", "female", "GannaGolikova@gmail.com", "Yalta",
                        Integer.valueOf(29), Long.valueOf(892265889122L), "teacher", Long.valueOf(1L)),

                new Student(Long.valueOf(6L), "Alexander", "Osipov", "male", "AlexanderOsipov@gmail.com", "Omsk",
                        Integer.valueOf(25), Long.valueOf(89526895624L), "no", Long.valueOf(1L)),

                new Student(Long.valueOf(7L), "Pavel", "Petrov", "male", "PavelPetrov@gmail.com", "Cuba",
                        Integer.valueOf(35), Long.valueOf(898565248566L), "teacher", Long.valueOf(1L)),

                new Student(Long.valueOf(8L), "Aleksey", "Grudov", "male", "AlekseyGrudov@gmail.com", "Samara",
                        Integer.valueOf(28), Long.valueOf(89856895235L), "no", Long.valueOf(2L)),

                new Student(Long.valueOf(9L), "Dmitry", "Sommer", "male", "DmitrySommer@gmail.com", "Kursk",
                        Integer.valueOf(21), Long.valueOf(89895325845L), "teacher", Long.valueOf(1L)),

                new Student(Long.valueOf(10L), "Alisa", "Egorova", "female", "AlisaEgorova@gmail.com", "Tomsk",
                        Integer.valueOf(31), Long.valueOf(89225683268L), "metodist", Long.valueOf(2L)));

        List<Student> toSave = Arrays.asList(
                new Student(Long.valueOf(1L), "Viktor", "Kim", "male", "ViktorKim@gmail.com", "Svetlogorsk",
                        Integer.valueOf(21), Long.valueOf(89316589853L), "no", Long.valueOf(1L)),

                new Student(Long.valueOf(2L), "Andrey", "Kunec", "male", "AndreyKunec@gmail.com", "Novgorod",
                        Integer.valueOf(22), Long.valueOf(89528975236L), "teacher", Long.valueOf(2L)),

                new Student(Long.valueOf(3L), "Olga", "Pestrecova", "female", "OlgaPestrecova@gmail.com", "Kaliningrad",
                        Integer.valueOf(25), Long.valueOf(89625879654L), "no", Long.valueOf(1L)),

                new Student(Long.valueOf(4L), "Maksim", "Progor", "male", "MaksimProgor@gmail.com", "Greboedov",
                        Integer.valueOf(27), Long.valueOf(8906258943L), "technician", Long.valueOf(2L)),

                new Student(Long.valueOf(5L), "Ganna", "Golikiova", "female", "GannaGolikova@gmail.com", "Yalta",
                        Integer.valueOf(29), Long.valueOf(892265889122L), "teacher", Long.valueOf(1L)),

                new Student("Alexander", "Osipov", "male", "AlexanderOsipov@gmail.com", "Omsk", Integer.valueOf(25),
                        Long.valueOf(89526895624L), "no", Long.valueOf(1L)),

                new Student("Pavel", "Petrov", "male", "PavelPetrov@gmail.com", "Cuba", Integer.valueOf(35),
                        Long.valueOf(898565248566L), "teacher", Long.valueOf(1L)),

                new Student("Aleksey", "Grudov", "male", "AlekseyGrudov@gmail.com", "Samara", Integer.valueOf(28),
                        Long.valueOf(89856895235L), "no", Long.valueOf(2L)),

                new Student("Dmitry", "Sommer", "male", "DmitrySommer@gmail.com", "Kursk", Integer.valueOf(21),
                        Long.valueOf(89895325845L), "teacher", Long.valueOf(1L)),

                new Student("Alisa", "Egorova", "female", "AlisaEgorova@gmail.com", "Tomsk", Integer.valueOf(31),
                        Long.valueOf(89225683268L), "metodist", Long.valueOf(2L)));

        List<Student> actual = dao.saveAll(toSave);
        assertThat(actual.size()).isEqualTo(toSave.size());
        assertThat(actual.stream().filter(item -> item.getId() == null).count()).isZero();
        assertThat(actual.stream().filter(item -> item.getFirstName() == "Viktor").count() == 1);
        assertThat(actual.stream().filter(item -> item.getFirstName() == "Andrey" && item.getLastName() == "Kunec")
                .collect(Collectors.toList()).get(0)).isEqualTo(expected.get(1));

    }

}

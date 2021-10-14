package ua.com.foxminded.university.dao.jdbc;

import javax.annotation.PostConstruct;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.jdbc.Sql;

import ua.com.foxminded.university.dao.GroupDao;
import ua.com.foxminded.university.dao.mappers.GroupMapper;
import ua.com.foxminded.university.model.Group;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import java.util.Arrays;
import java.util.List;

@JdbcTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
class JdbcGroupDaoTest {

    @Autowired
    JdbcTemplate jdbcTemplate;

    private GroupDao dao;

    @PostConstruct
    void setUp() {
        dao = new JdbcGroupDao(jdbcTemplate, new GroupMapper());
    }

    @Test
    @Sql({ "/clean_db.sql", "/sql/groups_init_test_values.sql" })
    void shouldInsertNewGroup() {
        Group group = new Group("GH-78");
        Group created = dao.save(group);
        group.setId(created.getId());
        assertThat(created).isEqualTo(group);
    }

    @Test
    @Sql({ "/clean_db.sql", "/sql/groups_init_test_values.sql" })
    void shouldFindGroupById() {
        Group expected = new Group(1L, "AB-12");
        Group actual = dao.findById(expected.getId()).get();
        assertThat(expected).isEqualTo(actual);
    }

    @Test
    @Sql({ "/clean_db.sql", "/sql/groups_init_test_values.sql" })
    void shouldNotFindGroupById() {
        Group group = new Group(1L, "CD-34");
        Group actual = dao.findById(group.getId()).get();
        assertThat(actual).isNotEqualTo(group);
    }

    @Test
    @Sql({ "/clean_db.sql", "/sql/groups_init_test_values.sql" })
    void shouldNotFindGroupIfNotExist() {
        assertThat(dao.findById(10L)).isEmpty();
    }

    @Test
    @Sql({ "/clean_db.sql", "/sql/groups_init_test_values.sql" })
    void shouldFindAllGroups() {

        List<Group> expected = Arrays.asList(new Group(1L, "AB-12"), new Group(2L, "CD-34"), new Group(3L, "EF-56"));
        assertThat(dao.findAll()).isEqualTo(expected);
    }

    @Test
    @Sql("/clean_db.sql")
    void shouldNotFindAllGroups() {

        assertThat(dao.findAll().size()).isZero();
    }

    @Test
    @Sql({ "/clean_db.sql", "/sql/groups_init_test_values.sql" })
    void shouldUpdateGroup() {
        Group expected = new Group(1L, "XZ-89");
        assertThat(dao.findById(1L)).isNotEqualTo(expected);
        dao.save(expected);
        assertThat(dao.findById(1L).get()).isEqualTo(expected);
    }

    @Test
    @Sql("/clean_db.sql")
    void shouldBatchSave() {
        List<Group> expected = Arrays.asList(new Group(1L, "AB-01"), new Group(2L, "CD-02"), new Group(3L, "EF-03"),
                new Group(4L, "GH-04"), new Group(1L, "IJ-05"), new Group(1L, "KL-06"));

        List<Group> toSave = Arrays.asList(new Group(1L, "AB-01"), new Group(2L, "CD-02"), new Group(3L, "EF-03"),
                new Group("GH-04"), new Group("IJ-05"), new Group("KL-06"));
        assertThat(dao.findAll().size()).isZero();
        List<Group> actual = dao.saveAll(toSave);
        assertThat(actual.size()).isEqualTo(6);
        assertThat(actual.stream().filter(it -> it.getName() == "AB-01" && it.getId() == 1L).count() == 1);
        assertThat(actual.stream().filter(it -> it.getId() == null).count()).isZero();

    }
}

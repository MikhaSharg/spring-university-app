package ua.com.foxminded.university;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;

@SpringBootTest
class UniversityApplicationTest {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Test
    void migrationTest() {
        List<String> tables = jdbcTemplate.queryForList(
                "SELECT table_name FROM information_schema.tables WHERE table_schema='PUBLIC'", String.class);
        assertThat(tables.size()).isEqualTo(14);
        assertThat(tables).containsAll(Arrays.asList("groups", "students", "persons", "teachers", "subjects",
                "teachers_subjects", "audiences", "flyway_schema_history", "lecture_sessions", "lectures", "roles", "teachers_roles", "students_roles", "persons_roles"));
    }

    @Test
    void contextLoads() {
        assertThat(jdbcTemplate).isNotNull();
    }

}

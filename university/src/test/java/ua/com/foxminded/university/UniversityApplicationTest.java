package ua.com.foxminded.university;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;

@SpringBootTest
class UniversityApplicationTest {

    @Autowired
    JdbcTemplate template;

    @Test
    void migrationDone() {

        List<String> tables = template.queryForList(
                "SELECT table_name FROM information_schema.tables WHERE table_schema='public'", String.class);
        assertEquals(8, tables.size());

        assertEquals("flyway_schema_history", tables.get(0));
        assertEquals("groups", tables.get(1));
        assertEquals("students", tables.get(2));
        assertEquals("persons", tables.get(3));
        assertEquals("teachers", tables.get(4));
        assertEquals("subjects", tables.get(5));
        assertEquals("audiences", tables.get(6));
        assertEquals("lectures_shedule", tables.get(7));
    }
}

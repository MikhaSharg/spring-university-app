package ua.com.foxminded.university.dao.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.concurrent.ThreadLocalRandom;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import ua.com.foxminded.university.model.Student;

import static org.mockito.Mockito.when;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class StudentMapperTest {

    @Mock
    ResultSet resultSet;

    @InjectMocks
    StudentMapper studentMapper;

    @Test
    void shouldMapRow() throws SQLException {

        when(resultSet.getLong("student_id")).thenReturn(1L, 2L);
        when(resultSet.getString("first_name")).thenReturn("Alex", "Olga");
        when(resultSet.getString("last_name")).thenReturn("Petrov", "Osipova");
        when(resultSet.getString("gender")).thenReturn("male", "female");
        when(resultSet.getString("email")).thenReturn("AlexPetrov@gmail.com", "OlgaOsipova@gmail.com");
        when(resultSet.getString("address")).thenReturn("Kaliningrad", "Rostov");
        when(resultSet.getInt("age")).thenReturn(24, 21);
        when(resultSet.getLong("phone_number")).thenReturn(89313265895L, 89226524589L);
        when(resultSet.getString("role")).thenReturn("teacher", "no");
        when(resultSet.getLong("group_id")).thenReturn(1L, 2L);

        Student expected = new Student(1L, "Alex", "Petrov", "male", "AlexPetrov@gmail.com", "Kaliningrad", 24,
                89313265895L, "teacher", 1L);
        Student expected2 = new Student(2L, "Olga", "Osipova", "female", "OlgaOsipova@gmail.com", "Rostov", 21,
                89226524589L, "no", 2L);

        assertThat(studentMapper.mapRow(resultSet, ThreadLocalRandom.current().nextInt())).isEqualTo(expected);
        assertThat(studentMapper.mapRow(resultSet, ThreadLocalRandom.current().nextInt())).isEqualTo(expected2);

    }

}

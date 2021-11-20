package ua.com.foxminded.university.dao.mappers;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.concurrent.ThreadLocalRandom;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import ua.com.foxminded.university.model.Teacher;

@ExtendWith(MockitoExtension.class)
class TeacherMapperTest {

	@Mock
	ResultSet resultSet;

	@InjectMocks
	TeacherMapper teacherMapper;

	@Test
	void shouldMapRow() throws SQLException {
		when(resultSet.getLong("teacher_id")).thenReturn(1L, 2L);
		when(resultSet.getString("first_name")).thenReturn("Alex", "Olga");
		when(resultSet.getString("last_name")).thenReturn("Petrov", "Osipova");
		when(resultSet.getString("gender")).thenReturn("male", "female");
		when(resultSet.getString("email")).thenReturn("AlexPetrov@gmail.com", "OlgaOsipova@gmail.com");
		when(resultSet.getString("address")).thenReturn("Kaliningrad", "Rostov");
		when(resultSet.getInt("age")).thenReturn(50, 60);
		when(resultSet.getLong("phone_number")).thenReturn(89313265895L, 89226524589L);
		when(resultSet.getString("role")).thenReturn("teacher", "teacher");
		when(resultSet.getString("profile")).thenReturn("Candidate of Technical Science",
				"Doctor of Technical Science");

		Teacher expected1 = new Teacher(1L, "Alex", "Petrov", "male", "AlexPetrov@gmail.com", "Kaliningrad", 50,
				89313265895L, "teacher", "Candidate of Technical Science");
		Teacher expected2 = new Teacher(2L, "Olga", "Osipova", "female", "OlgaOsipova@gmail.com", "Rostov", 60,
				89226524589L, "teacher", "Doctor of Technical Science");

		assertThat(teacherMapper.mapRow(resultSet, ThreadLocalRandom.current().nextInt())).isEqualTo(expected1);
		assertThat(teacherMapper.mapRow(resultSet, ThreadLocalRandom.current().nextInt())).isEqualTo(expected2);

	}

}

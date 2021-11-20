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

import ua.com.foxminded.university.model.Person;

@ExtendWith(MockitoExtension.class)
class PersonMapperTest {

	@Mock
	ResultSet resultSet;

	@InjectMocks
	PersonMapper personMapper;

	@Test
	void shouldMapRow() throws SQLException {
		when(resultSet.getLong("person_id")).thenReturn(1L, 2L);
		when(resultSet.getString("first_name")).thenReturn("Alex", "Olga");
		when(resultSet.getString("last_name")).thenReturn("Petrov", "Osipova");
		when(resultSet.getString("gender")).thenReturn("male", "female");
		when(resultSet.getString("email")).thenReturn("AlexPetrov@gmail.com", "OlgaOsipova@gmail.com");
		when(resultSet.getString("address")).thenReturn("Kaliningrad", "Rostov");
		when(resultSet.getInt("age")).thenReturn(24, 21);
		when(resultSet.getLong("phone_number")).thenReturn(89313265895L, 89226524589L);
		when(resultSet.getString("role")).thenReturn("repairer", "administrator");

		Person expected1 = new Person(1L, "Alex", "Petrov", "male", "AlexPetrov@gmail.com", "Kaliningrad", 24,
				89313265895L, "repairer");
		Person expected2 = new Person(2L, "Olga", "Osipova", "female", "OlgaOsipova@gmail.com", "Rostov", 21,
				89226524589L, "administrator");

		assertThat(personMapper.mapRow(resultSet, ThreadLocalRandom.current().nextInt())).isEqualTo(expected1);
		assertThat(personMapper.mapRow(resultSet, ThreadLocalRandom.current().nextInt())).isEqualTo(expected2);

	}

}

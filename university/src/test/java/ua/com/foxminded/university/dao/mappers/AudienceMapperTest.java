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

import ua.com.foxminded.university.model.Audience;

@ExtendWith(MockitoExtension.class)
class AudienceMapperTest {

	@Mock
	ResultSet resultSet;

	@InjectMocks
	AudienceMapper audienceMapper;

	@Test
	void shouldMapRow() throws SQLException {

		when(resultSet.getLong("audience_id")).thenReturn(1L, 2L);
		when(resultSet.getInt("room_number")).thenReturn(100, 101);

		Audience expected1 = new Audience(1L, 100);
		Audience expected2 = new Audience(2L, 101);

		assertThat(audienceMapper.mapRow(resultSet, ThreadLocalRandom.current().nextInt())).isEqualTo(expected1);
		assertThat(audienceMapper.mapRow(resultSet, ThreadLocalRandom.current().nextInt())).isEqualTo(expected2);

	}

}

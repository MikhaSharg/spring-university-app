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

import ua.com.foxminded.university.model.LectureSessions;

@ExtendWith(MockitoExtension.class)
class LectureSessionsMapperTest {

	@Mock
	ResultSet resultSet;

	@InjectMocks
	LectureSessionsMapper lectureSessionsMapper;

	@Test
	void shouldMapRow() throws SQLException {

		when(resultSet.getLong("session_id")).thenReturn(1L, 2L);
		when(resultSet.getString("period")).thenReturn("1th", "2th");
		when(resultSet.getString("start_time")).thenReturn("8:00", "9:30");
		when(resultSet.getString("end_time")).thenReturn("9:20", "10:50");

		LectureSessions expected1 = new LectureSessions(1L, "1th", "8:00", "9:20");
		LectureSessions expected2 = new LectureSessions(2L, "2th", "9:30", "10:50");

		assertThat(lectureSessionsMapper.mapRow(resultSet, ThreadLocalRandom.current().nextInt())).isEqualTo(expected1);
		assertThat(lectureSessionsMapper.mapRow(resultSet, ThreadLocalRandom.current().nextInt())).isEqualTo(expected2);
	}

}

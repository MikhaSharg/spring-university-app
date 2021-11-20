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

import ua.com.foxminded.university.model.Subject;

@ExtendWith(MockitoExtension.class)
class SubjectMapperTest {

	@Mock
	ResultSet resultSet;

	@InjectMocks
	SubjectMapper subjectMapper;

	@Test
	void shouldMapRow() throws SQLException {

		when(resultSet.getLong("subject_id")).thenReturn(1L, 2L);
		when(resultSet.getString("subject_name")).thenReturn("Architecture", "SAPR");

		Subject expected1 = new Subject(1L, "Architecture");
		Subject expected2 = new Subject(2L, "SAPR");

		assertThat(subjectMapper.mapRow(resultSet, ThreadLocalRandom.current().nextInt())).isEqualTo(expected1);
		assertThat(subjectMapper.mapRow(resultSet, ThreadLocalRandom.current().nextInt())).isEqualTo(expected2);

	}

}

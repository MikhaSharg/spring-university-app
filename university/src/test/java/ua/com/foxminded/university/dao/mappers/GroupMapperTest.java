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

import ua.com.foxminded.university.model.Group;

@ExtendWith(MockitoExtension.class)
class GroupMapperTest {

	@Mock
	ResultSet resultSet;

	@InjectMocks
	GroupMapper groupMapper;

	@Test
	void shouldMapRow() throws SQLException {

		when(resultSet.getLong("group_id")).thenReturn(1L, 2L);
		when(resultSet.getString("group_name")).thenReturn("AB-12", "CD-34");

		Group expected1 = new Group(1L, "AB-12");
		Group expected2 = new Group(2L, "CD-34");

		assertThat(groupMapper.mapRow(resultSet, ThreadLocalRandom.current().nextInt())).isEqualTo(expected1);
		assertThat(groupMapper.mapRow(resultSet, ThreadLocalRandom.current().nextInt())).isEqualTo(expected2);
	}

}

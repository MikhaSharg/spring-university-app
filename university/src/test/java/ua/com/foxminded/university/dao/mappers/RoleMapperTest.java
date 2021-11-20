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

import ua.com.foxminded.university.model.Role;

@ExtendWith(MockitoExtension.class)
class RoleMapperTest {

	@Mock
	ResultSet resultSet;

	@InjectMocks
	RoleMapper roleMapper;

	@Test
	void shouldMapRow() throws SQLException {

		when(resultSet.getLong("role_id")).thenReturn(1L, 2L);
		when(resultSet.getString("name")).thenReturn("dean", "electrician");

		Role expected1 = new Role(1L, "dean");
		Role expected2 = new Role(2L, "electrician");

		assertThat(roleMapper.mapRow(resultSet, ThreadLocalRandom.current().nextInt())).isEqualTo(expected1);
		assertThat(roleMapper.mapRow(resultSet, ThreadLocalRandom.current().nextInt())).isEqualTo(expected2);

	}

}

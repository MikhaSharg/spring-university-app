package ua.com.foxminded.university.dao.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import ua.com.foxminded.university.model.Audience;

public class AudienceMapper implements RowMapper<Audience> {

	@Override
	public Audience mapRow(ResultSet rs, int rowNum) throws SQLException {
		return new Audience(rs.getLong("audience_id"), rs.getInt("room_number"));
	}

}

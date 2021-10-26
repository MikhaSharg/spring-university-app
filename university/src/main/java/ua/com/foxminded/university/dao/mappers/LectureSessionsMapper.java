package ua.com.foxminded.university.dao.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import ua.com.foxminded.university.model.LectureSessions;

@Component
public class LectureSessionsMapper implements RowMapper<LectureSessions> {

	@Override
	public LectureSessions mapRow(ResultSet rs, int rowNum) throws SQLException {
		return new LectureSessions(rs.getLong("session_id"), rs.getString("period"), rs.getString("start_time"), rs.getString("end_time"));
	}

}

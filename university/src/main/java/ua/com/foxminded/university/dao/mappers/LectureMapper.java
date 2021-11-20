package ua.com.foxminded.university.dao.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import ua.com.foxminded.university.model.Lecture;

@Component
public class LectureMapper implements RowMapper<Lecture> {

	@Override
	public Lecture mapRow(ResultSet rs, int rowNum) throws SQLException {
		return new Lecture(rs.getLong("lecture_id"), rs.getDate("lecture_date").toLocalDate());
	}

}

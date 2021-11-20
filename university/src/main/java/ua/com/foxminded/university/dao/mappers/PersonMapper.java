package ua.com.foxminded.university.dao.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import ua.com.foxminded.university.model.Person;

@Component
public class PersonMapper implements RowMapper<Person> {

	@Override
	public Person mapRow(ResultSet rs, int rowNum) throws SQLException {
		return new Person(rs.getLong("person_id"), rs.getString("first_name"), rs.getString("last_name"),
				rs.getString("gender"), rs.getString("email"), rs.getString("address"), rs.getInt("age"),
				rs.getLong("phone_number"), rs.getString("role"));
	}
}

package ua.com.foxminded.university.dao.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import ua.com.foxminded.university.model.Subject;
import ua.com.foxminded.university.model.Teacher;

public class TeacherMapper implements RowMapper<Teacher> {

    @Override
    public Teacher mapRow(ResultSet rs, int rowNum) throws SQLException {

        Subject subject = new Subject (rs.getLong("subject_id"),
        rs.getString("subject_name"));
        
        
        return new Teacher(rs.getLong("teacher_id"), 
                rs.getString("first_name"), 
                rs.getString("last_name"),
                rs.getString("gender"), 
                rs.getString("email"), 
                rs.getString("address"), 
                rs.getInt("age"),
                rs.getLong("phone_number"), 
                rs.getString("role"), 
                rs.getString("profile"));
    }
}

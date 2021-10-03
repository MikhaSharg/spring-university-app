package ua.com.foxminded.university.dao.jdbc;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.object.BatchSqlUpdate;
import org.springframework.stereotype.Repository;

import ua.com.foxminded.university.dao.AbstractCrudDao;
import ua.com.foxminded.university.dao.StudentDao;
import ua.com.foxminded.university.model.Student;

import java.sql.Types;

@Repository
public class JdbcStudentDao extends AbstractCrudDao<Student> implements StudentDao {

    public static final String S_ID = "s_id";
    public static final String S_FIRST_NAME = "s_first_name";
    public static final String S_LAST_NAME = "s_last_name";
    public static final String S_GENDER = "s_gender";
    public static final String S_EMAIL = "s_email";
    public static final String S_ADDRESS = "s_address";
    public static final String S_AGE = "s_age";
    public static final String S_PHONE_NUMBER = "s_phone_number";
    public static final String S_ROLE = "s_role";
    public static final String S_GROUP_ID = "s_group_id";

    private static final String INSERT_ONE = "INSERT INTO students (first_name, last_name, gender, email, address, age, phone_number, role, group_id) VALUES (?,?,?,?,?,?,?,?,?)";
    private static final String UPDATE_ONE = "UPDATE students SET student_id=?, first_name=?, last_name=?, gender=?, email=?, address=?, age=?, phone_number=?, role=?, group_id=?  WHERE student_id=?";
    private static final String SELECT_BY_ID = "SELECT * FROM students WHERE student_id=?";
    private static final String DELETE_BY_ID = "DELETE FROM students WHERE student_id=?";
    private static final String INSERT_ONE_NAMED = "INSERT INTO students (:s_student_id, :s_first_name, :s_last_name, :s_gender, :s_email, :s_address, :s_age, :s_phone_number, :s_role, :s_group_id)";
    private static final String UPDATE_ONE_NAMED = "UPDATE students SET first_name=:S_FIRST_NAME, last_name=:S_LAST_NAME, gender=:S_GENDER, email=:S_EMAIL, address=:S_ADDRESS, age=:S_AGE, phone_number=:S_PHONE_NUMBER, role=:S_ROLE, group_id=:S_GROUP_ID  WHERE student_id=:S_ID";
    private static final String SELECT_ALL = "SELECT * FROM students";

    public JdbcStudentDao(JdbcTemplate jdbsTemplate, RowMapper<Student> rowMapper) {
        super(jdbsTemplate, rowMapper);
    }

    @Override
    protected void setInsertParams(PreparedStatement ps, Student entity) throws SQLException {
        ps.setString(1, entity.getFirstName());
        ps.setString(2, entity.getLastName());
        ps.setString(3, entity.getGender());
        ps.setString(4, entity.getEmail());
        ps.setString(5, entity.getAddress());
        ps.setInt(6, entity.getAge());
        ps.setLong(7, entity.getPhoneNumber());
        ps.setString(8, entity.getRole());
        ps.setLong(9, entity.getGroupId());
    }

    @Override
    protected void setUpdateParams(PreparedStatement ps, Student entity) throws SQLException {
        ps.setLong(1, entity.getId());
        ps.setString(2, entity.getFirstName());
        ps.setString(3, entity.getLastName());
        ps.setString(4, entity.getGender());
        ps.setString(5, entity.getEmail());
        ps.setString(6, entity.getAddress());
        ps.setInt(7, entity.getAge());
        ps.setLong(8, entity.getPhoneNumber());
        ps.setString(9, entity.getRole());
        ps.setLong(10, entity.getGroupId());
    }

    @Override
    protected Student createNewWithId(long id, Student entity) {
        return new Student(id, entity.getFirstName(), entity.getLastName(), entity.getGender(), entity.getEmail(),
                entity.getAddress(), entity.getAge(), entity.getPhoneNumber(), entity.getRole(), entity.getGroupId());
    }

    @Override
    protected Student createNew(Student entity) {
        return new Student(entity.getId(), entity.getFirstName(), entity.getLastName(), entity.getGender(),
                entity.getEmail(), entity.getAddress(), entity.getAge(), entity.getPhoneNumber(), entity.getRole(),
                entity.getGroupId());
    }

    @Override
    protected Map<String, Object> mapUpdateParam(Student entity) {
        Map<String, Object> updatedParam = new HashMap<>();
        updatedParam.put(S_ID, entity.getId());
        updatedParam.put(S_FIRST_NAME, entity.getFirstName());
        updatedParam.put(S_LAST_NAME, entity.getLastName());
        updatedParam.put(S_GENDER, entity.getGender());
        updatedParam.put(S_EMAIL, entity.getEmail());
        updatedParam.put(S_ADDRESS, entity.getAddress());
        updatedParam.put(S_AGE, entity.getAge());
        updatedParam.put(S_PHONE_NUMBER, entity.getPhoneNumber());
        updatedParam.put(S_ROLE, entity.getRole());
        updatedParam.put(S_GROUP_ID, entity.getGroupId());
        return updatedParam;
    }

    @Override
    protected void declareInsertParams(BatchSqlUpdate batchSqlUpdate) {
        batchSqlUpdate.declareParameter(new SqlParameter(Types.INTEGER, S_ID));
        batchSqlUpdate.declareParameter(new SqlParameter(Types.VARCHAR, S_FIRST_NAME));
        batchSqlUpdate.declareParameter(new SqlParameter(Types.VARCHAR, S_LAST_NAME));
        batchSqlUpdate.declareParameter(new SqlParameter(Types.VARCHAR, S_GENDER));
        batchSqlUpdate.declareParameter(new SqlParameter(Types.VARCHAR, S_EMAIL));
        batchSqlUpdate.declareParameter(new SqlParameter(Types.VARCHAR, S_ADDRESS));
        batchSqlUpdate.declareParameter(new SqlParameter(Types.INTEGER, S_AGE));
        batchSqlUpdate.declareParameter(new SqlParameter(Types.INTEGER, S_PHONE_NUMBER));
        batchSqlUpdate.declareParameter(new SqlParameter(Types.VARCHAR, S_ROLE));
        batchSqlUpdate.declareParameter(new SqlParameter(Types.INTEGER, S_GROUP_ID));
    }

    @Override
    protected String getInsertOneNamedQuery() {
        return INSERT_ONE_NAMED;
    }

    @Override
    protected String getInsertQuery() {
        return INSERT_ONE;
    }

    @Override
    protected String getUpdateOneNamedQuery() {
        return UPDATE_ONE_NAMED;
    }

    @Override
    protected String getUpdateQuery() {
        return UPDATE_ONE;
    }

    @Override
    protected String getSelectByIdQuery() {
        return SELECT_BY_ID;
    }

    @Override
    protected String getSelectAllQuery() {
        return SELECT_ALL;
    }

    @Override
    protected String getDeleteByIdQuery() {
        return DELETE_BY_ID;
    }
}

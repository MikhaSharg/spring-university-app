package ua.com.foxminded.university.dao.jdbc;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.object.BatchSqlUpdate;
import org.springframework.stereotype.Repository;

import ua.com.foxminded.university.dao.AbstractCrudDao;
import ua.com.foxminded.university.dao.StudentDao;
import ua.com.foxminded.university.model.Student;

import java.sql.Types;

@Repository
public class JdbcStudentDao extends AbstractCrudDao<Student> implements StudentDao {

    public static final String ID = "ID";
    public static final String FIRST_NAME = "FIRST_NAME";
    public static final String LAST_NAME = "LAST_NAME";
    public static final String GENDER = "GENDER";
    public static final String EMAIL = "EMAIL";
    public static final String ADDRESS = "ADDRESS";
    public static final String AGE = "AGE";
    public static final String PHONE_NUMBER = "PHONE_NUMBER";
    public static final String ROLE = "ROLE";
    public static final String GROUP_ID = "GROUP_ID";

    private static final String UPDATE_ONE = "UPDATE students SET first_name=?, last_name=?, gender=?, email=?, address=?, age=?, phone_number=?, role=?, group_id=?  WHERE student_id=?";
    private static final String SELECT_BY_ID = "SELECT * FROM students WHERE student_id=?";
    private static final String DELETE_BY_ID = "DELETE FROM students WHERE student_id=?";
    private static final String INSERT_ONE_NAMED = "INSERT INTO students (first_name, last_name, gender, email, address, age, phone_number, role, group_id) VALUES (:FIRST_NAME, :LAST_NAME, :GENDER, :EMAIL, :ADDRESS, :AGE, :PHONE_NUMBER, :ROLE, :GROUP_ID)";
    private static final String UPDATE_ONE_NAMED = "UPDATE students SET first_name=:FIRST_NAME, last_name=:LAST_NAME, gender=:GENDER, email=:EMAIL, address=:ADDRESS, age=:AGE, phone_number=:PHONE_NUMBER, role=:ROLE, group_id=:GROUP_ID  WHERE student_id=:ID";
    private static final String SELECT_ALL = "SELECT * FROM students";
    private static final String SELECT_BY_GROUPID = "SELECT * FROM students WHERE group_id =?";

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
        ps.setString(1, entity.getFirstName());
        ps.setString(2, entity.getLastName());
        ps.setString(3, entity.getGender());
        ps.setString(4, entity.getEmail());
        ps.setString(5, entity.getAddress());
        ps.setInt(6, entity.getAge());
        ps.setLong(7, entity.getPhoneNumber());
        ps.setString(8, entity.getRole());
        ps.setLong(9, entity.getGroupId());
        ps.setLong(10, entity.getId());
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
    protected Map<String, Object> mapInsertParam(Student entity) {
        Map<String, Object> insertParam = new HashMap<>();
        insertParam.put(FIRST_NAME, entity.getFirstName());
        insertParam.put(LAST_NAME, entity.getLastName());
        insertParam.put(GENDER, entity.getGender());
        insertParam.put(EMAIL, entity.getEmail());
        insertParam.put(ADDRESS, entity.getAddress());
        insertParam.put(AGE, entity.getAge());
        insertParam.put(PHONE_NUMBER, entity.getPhoneNumber());
        insertParam.put(ROLE, entity.getRole());
        insertParam.put(GROUP_ID, entity.getGroupId());
        return insertParam;
    }

    @Override
    protected Map<String, Object> mapUpdateParam(Student entity) {
        Map<String, Object> updatedParam = new HashMap<>();
        updatedParam.put(ID, entity.getId());
        updatedParam.put(FIRST_NAME, entity.getFirstName());
        updatedParam.put(LAST_NAME, entity.getLastName());
        updatedParam.put(GENDER, entity.getGender());
        updatedParam.put(EMAIL, entity.getEmail());
        updatedParam.put(ADDRESS, entity.getAddress());
        updatedParam.put(AGE, entity.getAge());
        updatedParam.put(PHONE_NUMBER, entity.getPhoneNumber());
        updatedParam.put(ROLE, entity.getRole());
        updatedParam.put(GROUP_ID, entity.getGroupId());
        return updatedParam;
    }

    @Override
    protected void declareInsertParams(BatchSqlUpdate batchSqlUpdate) {
        batchSqlUpdate.declareParameter(new SqlParameter(Types.VARCHAR, FIRST_NAME));
        batchSqlUpdate.declareParameter(new SqlParameter(Types.VARCHAR, LAST_NAME));
        batchSqlUpdate.declareParameter(new SqlParameter(Types.VARCHAR, GENDER));
        batchSqlUpdate.declareParameter(new SqlParameter(Types.VARCHAR, EMAIL));
        batchSqlUpdate.declareParameter(new SqlParameter(Types.VARCHAR, ADDRESS));
        batchSqlUpdate.declareParameter(new SqlParameter(Types.INTEGER, AGE));
        batchSqlUpdate.declareParameter(new SqlParameter(Types.BIGINT, PHONE_NUMBER));
        batchSqlUpdate.declareParameter(new SqlParameter(Types.VARCHAR, ROLE));
        batchSqlUpdate.declareParameter(new SqlParameter(Types.INTEGER, GROUP_ID));
    }

    @Override
    protected void declareUpdateParams(BatchSqlUpdate batchSqlUpdate) {
        batchSqlUpdate.declareParameter(new SqlParameter(Types.VARCHAR, FIRST_NAME));
        batchSqlUpdate.declareParameter(new SqlParameter(Types.VARCHAR, LAST_NAME));
        batchSqlUpdate.declareParameter(new SqlParameter(Types.VARCHAR, GENDER));
        batchSqlUpdate.declareParameter(new SqlParameter(Types.VARCHAR, EMAIL));
        batchSqlUpdate.declareParameter(new SqlParameter(Types.VARCHAR, ADDRESS));
        batchSqlUpdate.declareParameter(new SqlParameter(Types.INTEGER, AGE));
        batchSqlUpdate.declareParameter(new SqlParameter(Types.BIGINT, PHONE_NUMBER));
        batchSqlUpdate.declareParameter(new SqlParameter(Types.VARCHAR, ROLE));
        batchSqlUpdate.declareParameter(new SqlParameter(Types.INTEGER, GROUP_ID));
        batchSqlUpdate.declareParameter(new SqlParameter(Types.INTEGER, ID));

    }

    @Override
    protected String getInsertOneNamedQuery() {
        return INSERT_ONE_NAMED;
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

    @Override
    public List<Student> findAllStudentsByGroupId(Long groupId) {
        PreparedStatementSetter ps = new PreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps) throws SQLException {
                ps.setLong(1, groupId);
            }
        };
        return jdbcTemplate.query(SELECT_BY_GROUPID, ps, rowMapper);
    }

    @Override
    protected SimpleJdbcInsert getJdbcInsert() {
        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
        jdbcInsert.withTableName("students").setGeneratedKeyNames("student_id");
        return jdbcInsert;
    }

    @Override
    protected String getColumnNameWithId() {
        return "student_id";
    }

    @Override
    protected Optional<Student> findEntityById(Long id) {
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(SELECT_BY_ID, rowMapper, id));
        } catch (Exception e) {
            return Optional.empty();
        }

    }

    @Override
    protected List<Student> findAllEntities() {
        return jdbcTemplate.query(SELECT_ALL, rowMapper);
    }

}

package ua.com.foxminded.university.dao.jdbc;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.object.BatchSqlUpdate;
import org.springframework.stereotype.Repository;

import ua.com.foxminded.university.dao.AbstractCrudDao;
import ua.com.foxminded.university.dao.GroupDao;
import ua.com.foxminded.university.dao.StudentDao;
import ua.com.foxminded.university.dao.mappers.StudentMapper;
import ua.com.foxminded.university.model.Group;
import ua.com.foxminded.university.model.Student;

@Repository
public class JdbcGroupDao extends AbstractCrudDao<Group> implements GroupDao {

    private static final String GROUP_NAME = "GROUP_NAME";
    private static final String ID = "ID";
    private static final String UPDATE_ONE = "UPDATE groups SET group_name=? WHERE group_id=?";
    private static final String FIND_ONE_BY_ID = "SELECT * FROM groups WHERE group_id=?";
    private static final String INSERT_ONE_NAMED = "INSERT INTO groups (group_name) VALUES (:GROUP_NAME)";
    private static final String UPDATE_ONE_NAMED = "UPDATE groups SET group_name=:GROUP_NAME WHERE group_id=:ID";
    private static final String SELECT_ALL = "SELECT * FROM groups";
    private static final String DELETE_BY_ID = "DELETE FROM groups WHERE group_id=?";
    
    
    
    
    public JdbcGroupDao(JdbcTemplate jdbsTemplate, RowMapper<Group> rowMapper) {
        super(jdbsTemplate, rowMapper);
    }

    @Override
    protected void declareUpdateParams(BatchSqlUpdate batchSqlUpdate) {
        batchSqlUpdate.declareParameter(new SqlParameter(Types.VARCHAR, GROUP_NAME));
        batchSqlUpdate.declareParameter(new SqlParameter(Types.INTEGER, ID));
    }

    @Override
    protected void declareInsertParams(BatchSqlUpdate batchSqlUpdate) {
        batchSqlUpdate.declareParameter(new SqlParameter(Types.VARCHAR, GROUP_NAME));
    }

    @Override
    protected Map<String, Object> mapUpdateParam(Group entity) {
        Map<String, Object> param = new HashMap<>();
        param.put(ID, entity.getId());
        param.put(GROUP_NAME, entity.getName());
        return param;
    }

    @Override
    protected Map<String, Object> mapInsertParam(Group entity) {
        Map <String, Object> param = new HashMap<>();
        param.put(GROUP_NAME, entity.getName());
        return param;
    }

    @Override
    protected void setInsertParams(PreparedStatement ps, Group entity) throws SQLException {
        ps.setString(1, entity.getName());
        
    }

    @Override
    protected void setUpdateParams(PreparedStatement ps, Group entity) throws SQLException {
       ps.setString(1, entity.getName());
       ps.setLong(2, entity.getId());
    }

    @Override
    protected Group createNew(Group entity) {
        return new Group(entity.getId(), entity.getName());
    }

    @Override
    protected String getInsertQuery() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected String getUpdateQuery() {
        return UPDATE_ONE;
    }

    @Override
    protected String getUpdateOneNamedQuery() {
        return UPDATE_ONE_NAMED;
    }

    @Override
    protected String getInsertOneNamedQuery() {
        return INSERT_ONE_NAMED;
    }

    @Override
    protected String getSelectByIdQuery() {
        return FIND_ONE_BY_ID;
    }

    @Override
    protected String getDeleteByIdQuery() {
        return DELETE_BY_ID;
    }

    @Override
    protected String getSelectAllQuery() {
        return SELECT_ALL;
    }

    @Override
    protected Group createNewWithId(long id, Group entity) {
        return new Group(id, entity.getName());
    }

    private List<Student> getListOfStudents(Long groupId) {
        StudentDao studentDao = new JdbcStudentDao(jdbcTemplate, new StudentMapper());
        return studentDao.findStudentsByGroupId(groupId);
    }

    @Override
    protected SimpleJdbcInsert getJdbcInsert() {
          SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
          jdbcInsert.withTableName("groups").setGeneratedKeyNames("group_id");
          return jdbcInsert;
      }

    @Override
    protected String getColumnNameWithId() {
        return "group_id";
    }
    }
    
    

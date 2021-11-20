package ua.com.foxminded.university.dao.jdbc;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.object.BatchSqlUpdate;
import org.springframework.stereotype.Repository;

import ua.com.foxminded.university.dao.AbstractCrudDao;
import ua.com.foxminded.university.dao.RoleDao;
import ua.com.foxminded.university.model.Role;

@Repository
public class JdbcRoleDao extends AbstractCrudDao<Role> implements RoleDao {

	public static final String ID = "ID";
	public static final String NAME = "NAME";

	private static final String SELECT_BY_ID = "SELECT * FROM roles WHERE role_id=?";
	private static final String DELETE_BY_ID = "DELETE FROM roles WHERE role_id=?";
	private static final String INSERT_ONE_NAMED = "INSERT INTO roles (name) VALUES (:NAME)";
	private static final String UPDATE_ONE_NAMED = "UPDATE roles SET name=:NAME WHERE role_id=:ID";
	private static final String SELECT_ALL = "SELECT * FROM roles";
	private static final String UPDATE_ONE_BY_ID = "UPDATE roles  SET name=? WHERE role_id=?";
	private static final String INSERT_ROLE_TO_STUDENT = "INSERT INTO students_roles (student_id, role_id) VALUES (?,?)";
	private static final String SELECT_ROLES_FROM_STUDENT = "SELECT * FROM roles  WHERE role_id IN (SELECT role_id FROM students_roles WHERE student_id = ?)";
	private static final String DELETE_ROLE_FROM_STUDENT = "DELETE FROM students_roles WHERE student_id=? AND role_id=?";
	private static final String INSERT_ROLE_TO_TEACHER = "INSERT INTO teachers_roles (teacher_id, role_id) VALUES (?,?)";
	private static final String SELECT_ROLES_FROM_TEACHER = "SELECT * FROM roles  WHERE role_id IN (SELECT role_id FROM teachers_roles WHERE teacher_id = ?)";
	private static final String DELETE_ROLE_FROM_TEACHER = "DELETE FROM teachers_roles WHERE teacher_id=? AND role_id=?";

	protected JdbcRoleDao(JdbcTemplate jdbsTemplate, RowMapper<Role> rowMapper) {
		super(jdbsTemplate, rowMapper);
	}

	@Override
	protected Map<String, Object> mapInsertParam(Role entity) {
		Map<String, Object> param = new HashMap<>();
		param.put(NAME, entity.getName());
		return param;
	}

	@Override
	protected Map<String, Object> mapUpdateParam(Role entity) {
		Map<String, Object> param = new HashMap<>();
		param.put(NAME, entity.getName());
		param.put(ID, entity.getId());
		return param;
	}

	@Override
	protected List<Role> findAllEntities() {
		return jdbcTemplate.query(SELECT_ALL, rowMapper);
	}

	@Override
	protected String getColumnNameWithId() {
		return "role_id";
	}

	@Override
	protected void declareUpdateParams(BatchSqlUpdate batchSqlUpdate) {

		batchSqlUpdate.declareParameter(new SqlParameter(Types.VARCHAR, NAME));
		batchSqlUpdate.declareParameter(new SqlParameter(Types.INTEGER, ID));
	}

	@Override
	protected void declareInsertParams(BatchSqlUpdate batchSqlUpdate) {
		batchSqlUpdate.declareParameter(new SqlParameter(Types.VARCHAR, NAME));
	}

	@Override
	protected void setInsertParams(PreparedStatement ps, Role entity) throws SQLException {
		ps.setString(1, entity.getName());
	}

	@Override
	protected void setUpdateParams(PreparedStatement ps, Role entity) throws SQLException {
		ps.setString(1, entity.getName());
		ps.setLong(2, entity.getId());
	}

	@Override
	protected Role createNewWithId(long id, Role entity) {
		return new Role(id, entity.getName());
	}

	@Override
	protected Role createNew(Role entity) {
		return new Role(entity.getId(), entity.getName());
	}

	@Override
	protected String getUpdateQuery() {
		return UPDATE_ONE_BY_ID;
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
		return SELECT_BY_ID;
	}

	@Override
	protected String getDeleteByIdQuery() {
		return DELETE_BY_ID;
	}

	@Override
	protected SimpleJdbcInsert getJdbcInsert() {
		SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
		jdbcInsert.withTableName("roles").setGeneratedKeyNames("role_id");
		return jdbcInsert;
	}

	@Override
	protected Optional<Role> findEntityById(Long id) {
		try {
			return Optional.ofNullable(jdbcTemplate.queryForObject(SELECT_BY_ID, rowMapper, id));
		} catch (Exception e) {
			return Optional.empty();
		}
	}

	@Override
	public void addRoleToStudent(Long student_id, Long role_id) {
		int updated = jdbcTemplate.update(INSERT_ROLE_TO_STUDENT, ps -> {
			ps.setLong(1, student_id);
			ps.setLong(2, role_id);
		});

		if (updated != 1) {
			throw new IllegalArgumentException("Unable to add role");
		}
	}

	@Override
	public void deleteRoleFromStudent(Long student_id, Long role_id) {

		int updated = jdbcTemplate.update(DELETE_ROLE_FROM_STUDENT, ps -> {
			ps.setLong(1, student_id);
			ps.setLong(2, role_id);
		});

		if (updated != 1) {
			throw new IllegalArgumentException("Unable to delete role");
		}

	}

	@Override
	public List<Role> findRolesForStudent(Long student_id) {
		return jdbcTemplate.query(SELECT_ROLES_FROM_STUDENT, rowMapper, student_id);
	}

	@Override
	public void addRoleToTeacher(Long teacher_id, Long role_id) {
		int updated = jdbcTemplate.update(INSERT_ROLE_TO_TEACHER, ps -> {
			ps.setLong(1, teacher_id);
			ps.setLong(2, role_id);
		});

		if (updated != 1) {
			throw new IllegalArgumentException("Unable to add role");
		}

	}

	@Override
	public void deleteRoleFromTeacher(Long teacher_id, Long role_id) {
		int updated = jdbcTemplate.update(DELETE_ROLE_FROM_TEACHER, ps -> {
			ps.setLong(1, teacher_id);
			ps.setLong(2, role_id);
		});

		if (updated != 1) {
			throw new IllegalArgumentException("Unable to delete role");
		}

	}

	@Override
	public List<Role> findRolesForTeacher(Long teacher_id) {
		return jdbcTemplate.query(SELECT_ROLES_FROM_TEACHER, rowMapper, teacher_id);
	}

}

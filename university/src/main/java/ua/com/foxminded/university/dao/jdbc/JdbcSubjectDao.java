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

import ch.qos.logback.core.subst.Token.Type;
import ua.com.foxminded.university.dao.AbstractCrudDao;
import ua.com.foxminded.university.dao.SubjectDao;
import ua.com.foxminded.university.dao.mappers.TeacherMapper;
import ua.com.foxminded.university.model.Subject;

@Repository
public class JdbcSubjectDao extends AbstractCrudDao<Subject> implements SubjectDao {

	public static final String ID = "ID";
	public static final String SUBJECT_NAME = "SUBJECT_NAME";

	private static final String UPDATE_ONE = "UPDATE subjects SET subject_name=? WHERE subject_id=?";
	private static final String SELECT_BY_ID = "SELECT * FROM subjects WHERE subject_id=?";
	private static final String DELETE_BY_ID = "DELETE FROM subjects WHERE subject_id=?";
	private static final String INSERT_ONE_NAMED = "INSERT INTO subjects (subject_name) VALUES (:SUBJECT_NAME)";
	private static final String UPDATE_ONE_NAMED = "UPDATE subjects SET subject_name=:SUBJECT_NAME WHERE subject_id=:ID";
	private static final String SELECT_ALL = "SELECT * FROM subjects";
	private static final String SELECT_ALL_BY_TEACHER_ID = "SELECT * FROM subjects WHERE subject_id  IN  (SELECT subject_id FROM teachers_subjects WHERE teacher_id = ?)";
	private static final String INSERT_ONE_SUBJECT_TO_TEACHER = "INSERT INTO teachers_subjects (teacher_id, subject_id) VALUES (?, ?)";
	private static final String DELETE_ONE_SUBJECT_FROM_TEACHER = "DELETE FROM teachers_subjects WHERE teacher_id =? AND subject_id =?";

	protected JdbcSubjectDao(JdbcTemplate jdbsTemplate, RowMapper<Subject> rowMapper) {
		super(jdbsTemplate, rowMapper);
	}

	@Override
	public Optional<Subject> findById(Long id) {
		return findEntityById(id);
	}

	@Override
	protected List<Subject> findAllEntities() {
		return jdbcTemplate.query(SELECT_ALL, rowMapper);
	}

	@Override
	protected String getColumnNameWithId() {
		return "subject_id";
	}

	@Override
	protected void declareUpdateParams(BatchSqlUpdate batchSqlUpdate) {
		batchSqlUpdate.declareParameter(new SqlParameter(Types.VARCHAR, SUBJECT_NAME));
		batchSqlUpdate.declareParameter(new SqlParameter(Types.INTEGER, ID));

	}

	@Override
	protected void declareInsertParams(BatchSqlUpdate batchSqlUpdate) {
		batchSqlUpdate.declareParameter(new SqlParameter(Types.VARCHAR, SUBJECT_NAME));

	}

	@Override
	protected Map<String, Object> mapUpdateParam(Subject entity) {
		Map<String, Object> param = new HashMap<>();
		param.put(ID, entity.getId());
		param.put(SUBJECT_NAME, entity.getName());
		return param;
	}

	@Override
	protected Map<String, Object> mapInsertParam(Subject entity) {
		Map<String, Object> param = new HashMap<>();
		param.put(SUBJECT_NAME, entity.getName());
		return param;
	}

	@Override
	protected void setInsertParams(PreparedStatement ps, Subject entity) throws SQLException {
		ps.setString(1, entity.getName());

	}

	@Override
	protected void setUpdateParams(PreparedStatement ps, Subject entity) throws SQLException {
		ps.setString(1, entity.getName());
		ps.setLong(2, entity.getId());

	}

	@Override
	protected Subject createNewWithId(long id, Subject entity) {
		return new Subject(id, entity.getName());
	}

	@Override
	protected Subject createNew(Subject entity) {
		return new Subject(entity.getId(), entity.getName());
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
		return SELECT_BY_ID;
	}

	@Override
	protected String getDeleteByIdQuery() {
		return DELETE_BY_ID;
	}

	@Override
	protected SimpleJdbcInsert getJdbcInsert() {
		SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
		jdbcInsert.withTableName("subjects").setGeneratedKeyNames("subject_id");
		return jdbcInsert;
	}

	@Override
	protected Optional<Subject> findEntityById(Long id) {
		try {
			System.out.println("Start");
			return Optional.ofNullable(jdbcTemplate.queryForObject(SELECT_BY_ID, rowMapper, id));
		} catch (Exception e) {
			return Optional.empty();
		}
	}

	@Override
	public void addSubjectToTeacher(Long teacherId, Long subjectId) {
		int updated = jdbcTemplate.update(INSERT_ONE_SUBJECT_TO_TEACHER, ps -> {
			ps.setLong(1, teacherId);
			ps.setLong(2, subjectId);
		});
		if (updated != 1) {
			throw new IllegalArgumentException("Unable to add subject ");
		}

	}

	@Override
	public void deleteSubjectFromTeacher(Long teacherId, Long subjectId) {
		int updated = jdbcTemplate.update(DELETE_ONE_SUBJECT_FROM_TEACHER, ps -> {
			ps.setLong(1, teacherId);
			ps.setLong(2, subjectId);
		});
		if (updated != 1) {
			throw new IllegalArgumentException("Unable to add subject ");
		}
	}

	@Override
	public List<Subject> findAllSubjectsByTeacherId(Long teacherId) {
		return jdbcTemplate.query(SELECT_ALL_BY_TEACHER_ID, rowMapper, teacherId);
	}

}

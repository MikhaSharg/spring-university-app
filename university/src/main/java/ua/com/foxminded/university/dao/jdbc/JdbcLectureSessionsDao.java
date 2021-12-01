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
import ua.com.foxminded.university.dao.LectureSessionsDao;
import ua.com.foxminded.university.model.LectureSessions;

@Repository
public class JdbcLectureSessionsDao extends AbstractCrudDao<LectureSessions> implements LectureSessionsDao {

	public JdbcLectureSessionsDao(JdbcTemplate jdbsTemplate, RowMapper<LectureSessions> rowMapper) {
		super(jdbsTemplate, rowMapper);
	}

	private static final String ID = "ID";
	private static final String PERIOD = "PERIOD";
	private static final String START_TIME = "START_TIME";
	private static final String END_TIME = "END_TIME";

	private static final String UPDATE_ONE_BY_ID = "UPDATE lecture_sessions  SET period=?, start_time=?, end_time=? WHERE session_id=?";
	private static final String SELECT_ONE_BY_ID = "SELECT * FROM lecture_sessions WHERE  session_id=?";
	private static final String SELECT_ALL = "SELECT * FROM lecture_sessions";
	private static final String DELETE_ONE = "DELETE FROM lecture_sessions WHERE session_id=?";
	private static final String INSERT_ONE_NAMED = "INSERT INTO lecture_sessions (period, start_time, end_time) VALUES (:PERIOD, :START_TIME, :END_TIME)";
	private static final String UPDATE_ONE_NAMED = "UPDATE lecture_sessions SET   period=:PERIOD, start_time=:START_TIME, end_time=:END_TIME WHERE session_id=:ID";

	@Override
	protected Map<String, Object> mapInsertParam(LectureSessions entity) {
		Map<String, Object> param = new HashMap<>();
		param.put(PERIOD, entity.getPeriod());
		param.put(START_TIME, entity.getStartTime());
		param.put(END_TIME, entity.getEndTime());
		return param;
	}

	@Override
	protected Map<String, Object> mapUpdateParam(LectureSessions entity) {
		Map<String, Object> param = new HashMap<>();
		param.put(PERIOD, entity.getPeriod());
		param.put(START_TIME, entity.getStartTime());
		param.put(END_TIME, entity.getEndTime());
		param.put(ID, entity.getId());
		return param;
	}

	@Override
	protected List<LectureSessions> findAllEntities() {
		return jdbcTemplate.query(SELECT_ALL, rowMapper);
	}

	@Override
	protected String getColumnNameWithId() {
		return "session_id";
	}

	@Override
	protected void declareUpdateParams(BatchSqlUpdate batchSqlUpdate) {

		batchSqlUpdate.declareParameter(new SqlParameter(Types.VARCHAR, PERIOD));
		batchSqlUpdate.declareParameter(new SqlParameter(Types.VARCHAR, START_TIME));
		batchSqlUpdate.declareParameter(new SqlParameter(Types.VARCHAR, END_TIME));
		batchSqlUpdate.declareParameter(new SqlParameter(Types.INTEGER, ID));
	}

	@Override
	protected void declareInsertParams(BatchSqlUpdate batchSqlUpdate) {
		batchSqlUpdate.declareParameter(new SqlParameter(Types.VARCHAR, PERIOD));
		batchSqlUpdate.declareParameter(new SqlParameter(Types.VARCHAR, START_TIME));
		batchSqlUpdate.declareParameter(new SqlParameter(Types.VARCHAR, END_TIME));
	}

	@Override
	protected void setInsertParams(PreparedStatement ps, LectureSessions entity) throws SQLException {
		ps.setString(1, entity.getPeriod());
		ps.setString(2, entity.getStartTime());
		ps.setString(3, entity.getEndTime());
	}

	@Override
	protected void setUpdateParams(PreparedStatement ps, LectureSessions entity) throws SQLException {
		ps.setString(1, entity.getPeriod());
		ps.setString(2, entity.getStartTime());
		ps.setString(3, entity.getEndTime());
		ps.setLong(4, entity.getId());
	}

	@Override
	protected LectureSessions createNewWithId(long id, LectureSessions entity) {
		return new LectureSessions(id, entity.getPeriod(), entity.getStartTime(), entity.getEndTime());
	}

	@Override
	protected LectureSessions createNew(LectureSessions entity) {
		return new LectureSessions(entity.getId(), entity.getPeriod(), entity.getStartTime(), entity.getEndTime());
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
		return SELECT_ONE_BY_ID;
	}

	@Override
	protected String getDeleteByIdQuery() {
		return DELETE_ONE;
	}

	@Override
	protected SimpleJdbcInsert getJdbcInsert() {
		SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
		jdbcInsert.withTableName("lecture_sessions").setGeneratedKeyNames("session_id");
		return jdbcInsert;
	}

	@Override
	protected Optional<LectureSessions> findEntityById(Long id) {
		try {
			return Optional.ofNullable(jdbcTemplate.queryForObject(SELECT_ONE_BY_ID, rowMapper, id));
		} catch (Exception e) {
			return Optional.empty();
		}
	}

}

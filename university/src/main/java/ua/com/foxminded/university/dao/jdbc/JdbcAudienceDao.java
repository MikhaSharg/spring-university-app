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
import ua.com.foxminded.university.dao.AudienceDao;
import ua.com.foxminded.university.model.Audience;

@Repository
public class JdbcAudienceDao extends AbstractCrudDao<Audience> implements AudienceDao {

	public static final String ID = "ID";
	public static final String ROOM_NUMBER = "ROOM_NUMBER";

	private static final String UPDATE_ONE = "UPDATE audiences SET room_number=?  WHERE audience_id=?";
	private static final String SELECT_BY_ID = "SELECT * FROM audiences WHERE audience_id=?";
	private static final String DELETE_BY_ID = "DELETE FROM audiences WHERE audience_id=?";
	private static final String INSERT_ONE_NAMED = "INSERT INTO audiences (room_number) VALUES (:ROOM_NUMBER)";
	private static final String UPDATE_ONE_NAMED = "UPDATE audiences SET room_number=:ROOM_NUMBER  WHERE audience_id=:ID";
	private static final String SELECT_ALL = "SELECT * FROM audiences";

	protected JdbcAudienceDao(JdbcTemplate jdbsTemplate, RowMapper<Audience> rowMapper) {
		super(jdbsTemplate, rowMapper);
	}

	@Override
	protected void setInsertParams(PreparedStatement ps, Audience entity) throws SQLException {
		ps.setInt(1, entity.getRoomNumber());
	}

	@Override
	protected void setUpdateParams(PreparedStatement ps, Audience entity) throws SQLException {
		ps.setInt(1, entity.getRoomNumber());
		ps.setLong(2, entity.getId());
	}

	@Override
	protected Audience createNewWithId(long id, Audience entity) {
		return new Audience(id, entity.getRoomNumber());
	}

	@Override
	protected Audience createNew(Audience entity) {
		return new Audience(entity.getId(), entity.getRoomNumber());
	}

	@Override
	protected Map<String, Object> mapInsertParam(Audience entity) {
		Map<String, Object> param = new HashMap<>();
		param.put(ROOM_NUMBER, entity.getRoomNumber());
		return param;
	}

	@Override
	protected Map<String, Object> mapUpdateParam(Audience entity) {
		Map<String, Object> param = new HashMap<>();
		param.put(ID, entity.getId());
		param.put(ROOM_NUMBER, entity.getRoomNumber());
		return param;
	}

	@Override
	protected void declareInsertParams(BatchSqlUpdate batchSqlUpdate) {
		batchSqlUpdate.declareParameter(new SqlParameter(Types.INTEGER, ROOM_NUMBER));
	}

	@Override
	protected void declareUpdateParams(BatchSqlUpdate batchSqlUpdate) {
		batchSqlUpdate.declareParameter(new SqlParameter(Types.INTEGER, ROOM_NUMBER));
		batchSqlUpdate.declareParameter(new SqlParameter(Types.INTEGER, ID));
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
	protected String getColumnNameWithId() {
		return "audience_id";
	}

	@Override
	protected List<Audience> findAllEntities() {
		return jdbcTemplate.query(SELECT_ALL, rowMapper);
	}

	@Override
	protected SimpleJdbcInsert getJdbcInsert() {
		SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
		jdbcInsert.withTableName("audiences").setGeneratedKeyNames("audience_id");
		return jdbcInsert;
	}

	@Override
	protected Optional<Audience> findEntityById(Long id) {
		try {
			return Optional.ofNullable(jdbcTemplate.queryForObject(SELECT_BY_ID, rowMapper, id));
		} catch (Exception e) {
			return Optional.empty();
		}
	}

}

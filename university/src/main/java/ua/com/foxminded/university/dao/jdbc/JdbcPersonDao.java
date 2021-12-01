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
import ua.com.foxminded.university.dao.PersonDao;
import ua.com.foxminded.university.model.Person;

@Repository
public class JdbcPersonDao extends AbstractCrudDao<Person> implements PersonDao {

	public static final String ID = "ID";
	public static final String FIRST_NAME = "FIRST_NAME";
	public static final String LAST_NAME = "LAST_NAME";
	public static final String GENDER = "GENDER";
	public static final String EMAIL = "EMAIL";
	public static final String ADDRESS = "ADDRESS";
	public static final String AGE = "AGE";
	public static final String PHONE_NUMBER = "PHONE_NUMBER";
	public static final String ROLE = "ROLE";

	private static final String UPDATE_ONE = "UPDATE persons SET first_name=?, last_name=?, gender=?, email=?, address=?, age=?, phone_number=?, role=? \n"
			+ "WHERE person_id=?";
	private static final String SELECT_BY_ID = "SELECT * FROM persons WHERE person_id=?";
	private static final String DELETE_BY_ID = "DELETE FROM persons WHERE person_id=?";
	private static final String INSERT_ONE_NAMED = "INSERT INTO persons (first_name, last_name, gender, email, address, age, phone_number, role) \n"
			+ "VALUES (:FIRST_NAME, :LAST_NAME, :GENDER, :EMAIL, :ADDRESS, :AGE, :PHONE_NUMBER, :ROLE)";
	private static final String UPDATE_ONE_NAMED = "UPDATE persons SET first_name=:FIRST_NAME, last_name=:LAST_NAME, gender=:GENDER, email=:EMAIL, \n"
			+ "address=:ADDRESS, age=:AGE, \n" + "phone_number=:PHONE_NUMBER, role=:ROLE WHERE person_id=:ID";
	private static final String SELECT_ALL = "SELECT * FROM persons";

	public JdbcPersonDao(JdbcTemplate jdbsTemplate, RowMapper<Person> rowMapper) {
		super(jdbsTemplate, rowMapper);
	}

	@Override
	protected void setInsertParams(PreparedStatement ps, Person entity) throws SQLException {
		ps.setString(1, entity.getFirstName());
		ps.setString(2, entity.getLastName());
		ps.setString(3, entity.getGender());
		ps.setString(4, entity.getEmail());
		ps.setString(5, entity.getAddress());
		ps.setInt(6, entity.getAge());
		ps.setLong(7, entity.getPhoneNumber());
		ps.setString(8, entity.getRole());
	}

	@Override
	protected void setUpdateParams(PreparedStatement ps, Person entity) throws SQLException {
		ps.setString(1, entity.getFirstName());
		ps.setString(2, entity.getLastName());
		ps.setString(3, entity.getGender());
		ps.setString(4, entity.getEmail());
		ps.setString(5, entity.getAddress());
		ps.setInt(6, entity.getAge());
		ps.setLong(7, entity.getPhoneNumber());
		ps.setString(8, entity.getRole());
		ps.setLong(9, entity.getId());
	}

	@Override
	protected Person createNewWithId(long id, Person entity) {
		return new Person(id, entity.getFirstName(), entity.getLastName(), entity.getGender(), entity.getEmail(),
				entity.getAddress(), entity.getAge(), entity.getPhoneNumber(), entity.getRole());
	}

	@Override
	protected Person createNew(Person entity) {
		return new Person(entity.getId(), entity.getFirstName(), entity.getLastName(), entity.getGender(),
				entity.getEmail(), entity.getAddress(), entity.getAge(), entity.getPhoneNumber(), entity.getRole());
	}

	@Override
	protected Map<String, Object> mapInsertParam(Person entity) {
		Map<String, Object> insertParam = new HashMap<>();
		insertParam.put(FIRST_NAME, entity.getFirstName());
		insertParam.put(LAST_NAME, entity.getLastName());
		insertParam.put(GENDER, entity.getGender());
		insertParam.put(EMAIL, entity.getEmail());
		insertParam.put(ADDRESS, entity.getAddress());
		insertParam.put(AGE, entity.getAge());
		insertParam.put(PHONE_NUMBER, entity.getPhoneNumber());
		insertParam.put(ROLE, entity.getRole());
		return insertParam;
	}

	@Override
	protected Map<String, Object> mapUpdateParam(Person entity) {
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
	protected String getDeleteByIdQuery() {
		return DELETE_BY_ID;
	}

	@Override
	protected SimpleJdbcInsert getJdbcInsert() {
		SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
		jdbcInsert.withTableName("persons").setGeneratedKeyNames("person_id");
		return jdbcInsert;
	}

	@Override
	protected String getColumnNameWithId() {
		return "person_id";
	}

	@Override
	protected Optional<Person> findEntityById(Long id) {
		try {
			return Optional.ofNullable(jdbcTemplate.queryForObject(SELECT_BY_ID, rowMapper, id));
		} catch (Exception e) {
			return Optional.empty();
		}

	}

	@Override
	protected List<Person> findAllEntities() {
		return jdbcTemplate.query(SELECT_ALL, rowMapper);
	}

}

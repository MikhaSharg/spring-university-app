package ua.com.foxminded.university.dao;

import static java.util.stream.Collectors.partitioningBy;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.object.BatchSqlUpdate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import ua.com.foxminded.university.model.IdEntity;

public abstract class AbstractCrudDao<T extends IdEntity> implements CrudDao<T, Long> {

	protected final JdbcTemplate jdbcTemplate;
	protected final RowMapper<T> rowMapper;

	protected AbstractCrudDao(JdbcTemplate jdbsTemplate, RowMapper<T> rowMapper) {
		this.jdbcTemplate = jdbsTemplate;
		this.rowMapper = rowMapper;
	}

	private T create(T entity) {
		Number key = getJdbcInsert().executeAndReturnKey(mapInsertParam(entity));
		return createNewWithId(key.longValue(), entity);
	}

	protected T update(T entity) {
		int updated = jdbcTemplate.update(getUpdateQuery(), ps -> setUpdateParams(ps, entity));
		if (updated != 1) {
			throw new IllegalArgumentException("Unable to update " + entity.toString());
		}
		return createNew(entity);
	}

	@Override
	public T save(T entity) {
		return entity.getId() == null ? create(entity) : update(entity);
	}

	@Override
	public List<T> saveAll(List<T> entities) {
		return entities.stream().collect(partitioningBy(e -> e.getId() != null)).entrySet().stream().flatMap(
				p -> (Boolean.TRUE.equals(p.getKey()) ? updateBatch(p.getValue()) : createBatch(p.getValue())).stream())
				.collect(Collectors.toList());
	}

	@Override
	public Optional<T> findById(Long id) {
		return findEntityById(id);
	}

	@Override
	public List<T> findAll() {
		return findAllEntities();
	}

	protected abstract List<T> findAllEntities();

	@Override
	public void deleteById(Long id) {
		if (jdbcTemplate.update(getDeleteByIdQuery(), id) != 1) {
			throw new IllegalArgumentException("Unable to delete item with id " + id);
		}
	}

	protected List<T> createBatch(List<T> value) {

		BatchSqlUpdate batchSqlUpdate = new BatchSqlUpdate();
		batchSqlUpdate.setJdbcTemplate(jdbcTemplate);
		batchSqlUpdate.setSql(getInsertOneNamedQuery());
		batchSqlUpdate.setReturnGeneratedKeys(true);
		batchSqlUpdate.setGeneratedKeysColumnNames(getColumnNameWithId());
		declareInsertParams(batchSqlUpdate);
		List<T> updated = value.stream().map(item -> {
			KeyHolder keyHolder = new GeneratedKeyHolder();
			Map<String, Object> paramMap = mapInsertParam(item);
			batchSqlUpdate.updateByNamedParam(paramMap, keyHolder);
			assert keyHolder.getKey() != null;
			return createNewWithId(keyHolder.getKey().longValue(), item);
		}).collect(Collectors.toList());
		batchSqlUpdate.flush();
		return updated;
	}

	protected abstract String getColumnNameWithId();

	protected List<T> updateBatch(List<T> value) {
		BatchSqlUpdate batchSqlUpdate = new BatchSqlUpdate();
		batchSqlUpdate.setJdbcTemplate(jdbcTemplate);
		batchSqlUpdate.setSql(getUpdateOneNamedQuery());
		declareUpdateParams(batchSqlUpdate);
		List<T> updated = value.stream().map(item -> {
			Map<String, Object> paramMap = mapUpdateParam(item);
			batchSqlUpdate.updateByNamedParam(paramMap);
			return createNew(item);
		}).collect(Collectors.toList());
		batchSqlUpdate.flush();
		return updated;
	}

	protected abstract void declareUpdateParams(BatchSqlUpdate batchSqlUpdate);

	protected abstract void declareInsertParams(BatchSqlUpdate batchSqlUpdate);

	protected abstract Map<String, Object> mapUpdateParam(T entity);

	protected abstract Map<String, Object> mapInsertParam(T entity);

	protected abstract void setInsertParams(PreparedStatement ps, T entity) throws SQLException;

	protected abstract void setUpdateParams(PreparedStatement ps, T entity) throws SQLException;

	protected abstract T createNewWithId(long id, T entity);

	protected abstract T createNew(T entity);

	protected abstract String getUpdateQuery();

	protected abstract String getUpdateOneNamedQuery();

	protected abstract String getInsertOneNamedQuery();

	protected abstract String getSelectByIdQuery();

	protected abstract String getDeleteByIdQuery();

	protected abstract SimpleJdbcInsert getJdbcInsert();

	protected abstract Optional<T> findEntityById(Long id);

}

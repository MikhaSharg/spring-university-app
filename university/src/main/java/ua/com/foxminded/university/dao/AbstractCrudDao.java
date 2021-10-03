package ua.com.foxminded.university.dao;

import static java.util.stream.Collectors.partitioningBy;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
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
        KeyHolder keyHolder = new GeneratedKeyHolder();
        int updated = jdbcTemplate.update(con -> {
            PreparedStatement ps = con.prepareStatement(getInsertQuery());
            setInsertParams(ps, entity);
            return ps;
        }, keyHolder);
        Number key = keyHolder.getKey();
        if (updated != 1 || key == null) {
            throw new IllegalArgumentException("Unable to create " + entity.toString());
        }
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
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(getSelectByIdQuery(), rowMapper, id));
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    @Override
    public List<T> findAll() {
        return jdbcTemplate.query(getSelectAllQuery(), rowMapper);
    }

    @Override
    public void deleteById(Long id) {
        if (jdbcTemplate.update(getDeleteByIdQuery(), id) != 1) {
            throw new IllegalArgumentException("Unable to deleteitem with id " + id);

        }
    }

    protected List<T> createBatch(List<T> value) {
        BatchSqlUpdate batchSqlUpdate = new BatchSqlUpdate();
        batchSqlUpdate.setJdbcTemplate(jdbcTemplate);
        batchSqlUpdate.setSql(getInsertOneNamedQuery());
        batchSqlUpdate.setReturnGeneratedKeys(true);
        declareInsertParams(batchSqlUpdate);
        List<T> updated = value.stream().map(item -> {
            KeyHolder keyHolder = new GeneratedKeyHolder();
            Map<String, Object> paramMap = mapUpdateParam(item);
            batchSqlUpdate.updateByNamedParam(paramMap, keyHolder);
            assert keyHolder.getKey() != null;
            return createNewWithId(keyHolder.getKey().longValue(), item);
        }).collect(Collectors.toList());
        batchSqlUpdate.flush();
        return updated;
    }

    protected List<T> updateBatch(List<T> value) {
        BatchSqlUpdate batchSqlUpdate = new BatchSqlUpdate();
        batchSqlUpdate.setJdbcTemplate(jdbcTemplate);
        batchSqlUpdate.setSql(getUpdateOneNamedQuery());
        List<T> updated = value.stream().map(item -> {
            Map<String, Object> paramMap = mapUpdateParam(item);
            batchSqlUpdate.updateByNamedParam(paramMap);
            return createNew(item);
        }).collect(Collectors.toList());
        batchSqlUpdate.flush();
        return updated;
    }

    protected abstract String getUpdateOneNamedQuery();

    protected abstract Map<String, Object> mapUpdateParam(T entity);

    protected abstract void declareInsertParams(BatchSqlUpdate batchSqlUpdate);

    protected abstract String getInsertOneNamedQuery();

    protected abstract void setInsertParams(PreparedStatement ps, T entity) throws SQLException;

    protected abstract void setUpdateParams(PreparedStatement ps, T entity) throws SQLException;

    protected abstract T createNewWithId(long id, T entity);

    protected abstract T createNew(T entity);

    protected abstract String getInsertQuery();

    protected abstract String getUpdateQuery();

    protected abstract String getSelectByIdQuery();

    protected abstract String getDeleteByIdQuery();

    protected abstract String getSelectAllQuery();

}

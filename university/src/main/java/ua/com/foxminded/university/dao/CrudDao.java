package ua.com.foxminded.university.dao;

import java.util.List;
import java.util.Optional;

import ua.com.foxminded.university.model.Entity;

public interface CrudDao<T extends Entity<K>, K> {

    T save(T entity);

    List<T> saveAll(List<T> entities);

    Optional<T> findById(K id);

    List<T> findAll();

    void deleteById(K id);

}

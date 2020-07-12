package ru_kpfu_itis_group905_kiyamdinov.repositories;

import java.util.List;

/**
 * 10.07.2020
 * 01. Database
 *
 * @author Sidikov Marsel (First Software Engineering Platform)
 * @version v1.0
 */
public interface CrudRepository<T, E> {
    List<T> findAll();
    T findById(Long id);
    void save(T entity1, E entity2);
    void update(T entity);
}


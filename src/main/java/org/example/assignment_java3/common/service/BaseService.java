package org.example.assignment_java3.common.service;

import java.util.List;

public interface BaseService<T, ID> {
    T create(T entity);

    T getById(ID id);

    T update(T entity);

    int delete(ID id);

    List<T> getAll();
}

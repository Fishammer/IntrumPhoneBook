package com.example.phonebook.services;

import java.util.List;

public abstract class AbstractService<T> {
    public abstract List<T> getAll();

    public abstract T getById(Long id);

    public abstract void save(T entity);

    public abstract void update(T entity, Long id);

    public abstract void delete(Long id);

    public abstract void entityExists(Long id);
}

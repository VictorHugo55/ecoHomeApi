package org.example.repositories;

import org.example.entities._BaseEntity;

import java.util.List;

public interface _BaseRepositorie<T extends _BaseEntity> {
    public void Create(T entity);
    public List<T> ReadAll();
    public boolean DeleteById(int id);
    public boolean UpdateById(T entity, int id);
    public T ReadById(int id);
}

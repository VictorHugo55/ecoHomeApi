package org.example.repositories;

import org.example.entities._BaseEntity;
import org.example.utils.Log4jLogger;

import java.util.List;

public class _BaseRepositoryImpl <T extends _BaseEntity> implements _BaseRepositorie<T>{

    protected Log4jLogger<T> logger;

    public _BaseRepositoryImpl(Class<T> tClass) {
        this.logger = new Log4jLogger(tClass);
    }

    @Override
    public void Create(T entity) {
        logger.logCreate(entity);    }


    @Override
    public List<T> ReadAll() {
        logger.logReadAll(null);
        return null;
    }

    @Override
    public boolean DeleteById(int id) {
        logger.logDeleteById(ReadAll().get(id));
        return false;
    }

    @Override
    public boolean UpdateById(T entity, int id) {
        logger.logUpdateById(entity);
        return false;
    }

    @Override
    public T ReadById(int id) {
        logger.logReadById(ReadAll().get(id));
        return null;
    }
}

package com.increff.pos.dao;

import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.lang.reflect.Type;
import java.util.List;


//Generic Functions for All Dao Classes

@Repository
public class AbstractDao {

    @PersistenceContext
    private EntityManager em;

    protected <T> void insert(T pojo){
        em.persist(pojo);
    }

    protected <T> T selectById(Class<T> clazz,int id){
        return em.find(clazz,id);
    }

    protected <T> List<T> selectAll(Class<T> clazz){
        String selectQuery = "select p from " + clazz.getName()+" p";
        TypedQuery<T> query = em.createQuery(selectQuery,clazz);
        return query.getResultList();
    }
    protected <T> void deleteById(Class<T> clazz, int id){
        T obj = selectById(clazz,id);
        em.remove(obj);
    }

    protected <T> void update(T obj){
        em.merge(obj);
    }

    protected <T> TypedQuery<T> getQuery(String query,Class<T> clazz){
        return em.createQuery(query,clazz);
    }

    protected <T> T getFirstRowFromQuery(TypedQuery<T> query){
        return query.getResultList().stream().findFirst().orElse(null);
    }


}


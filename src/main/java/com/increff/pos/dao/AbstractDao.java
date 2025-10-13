package com.increff.pos.dao;

import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.lang.reflect.ParameterizedType;
import java.util.List;


//Generic Functions for All Dao Classes

@Repository
public abstract class  AbstractDao<T> {

    private final Class<T> clazz;

    @SuppressWarnings("unchecked")
    public AbstractDao() {
        this.clazz = (Class<T>) ((ParameterizedType) getClass()
                .getGenericSuperclass())
                .getActualTypeArguments()[0];
    }

    @PersistenceContext
    protected EntityManager em;

    public void insert(T pojo){
        em.persist(pojo);
    }

    public T selectById(Integer id){
        return em.find(clazz,id);
    }

    public List<T> selectAll(){
        String selectQuery = "select p from " + clazz.getName()+" p";
        TypedQuery<T> query = em.createQuery(selectQuery,clazz);
        return query.getResultList();
    }

    public void deleteById(Integer id){
        T obj = selectById(id);
        em.remove(obj);
    }

    public void update(T obj){
        em.merge(obj);
    }

    protected TypedQuery<T> getQuery(String query){
        return em.createQuery(query,clazz);
    }

    protected T getFirstRowFromQuery(TypedQuery<T> query){
        return query.getResultList().stream().findFirst().orElse(null);
    }
}


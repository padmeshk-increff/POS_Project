package com.increff.pos.dao;

import com.increff.pos.pojo.ClientPojo;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;


@Repository
public class ClientDao extends AbstractDao{
    /**
     * Available protected methods:
     *
     *   insert(Object pojo)                          : void
     *   deleteById(Class<?> clazz, int id)               : void
     *   <T> T selectById(Class<T> clazz, int id)     : T
     *   <T> List<T> selectAll(Class<T> clazz)        : List<T>
     *   update(Object pojo)                          : void
     *   <T> TypedQuery<T> getQuery(String jpql, Class<T> clazz) : TypedQuery<T>
     *   <T> T getFirstRowFromQuery(TypedQuery<T> query)         : T
     */

    private static final String SELECT_BY_NAME = "select p from ClientPojo p where clientName=:clientName";

    public ClientPojo selectByName(String clientName) {
        TypedQuery<ClientPojo> query = getQuery(SELECT_BY_NAME, ClientPojo.class);
        query.setParameter("clientName", clientName);
        return getFirstRowFromQuery(query);
    }
}

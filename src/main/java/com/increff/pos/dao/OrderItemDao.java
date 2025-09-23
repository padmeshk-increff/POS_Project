package com.increff.pos.dao;

import com.increff.pos.pojo.OrderItemPojo;
import java.util.List;
import javax.persistence.TypedQuery;
import org.springframework.stereotype.Repository;

@Repository
public class OrderItemDao extends AbstractDao {
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

    private static final String SELECT_BY_ORDER_ID = "select p from OrderItemPojo p where p.order.id = :orderId";

    public List<OrderItemPojo> selectByOrderId(int orderId) {
        TypedQuery<OrderItemPojo> query = getQuery(SELECT_BY_ORDER_ID, OrderItemPojo.class);
        query.setParameter("orderId", orderId);
        return query.getResultList();
    }
}
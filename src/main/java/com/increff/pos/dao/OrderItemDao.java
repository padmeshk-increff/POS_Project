package com.increff.pos.dao;

import com.increff.pos.pojo.OrderItem;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class OrderItemDao extends AbstractDao<OrderItem> {

    private static final String SELECT_BY_ORDER_ID = "select p from OrderItem p where orderId = :orderId";

    public List<OrderItem> selectByOrderId(int orderId) {
        TypedQuery<OrderItem> query = getQuery(SELECT_BY_ORDER_ID);
        query.setParameter("orderId", orderId);
        return query.getResultList();
    }
}
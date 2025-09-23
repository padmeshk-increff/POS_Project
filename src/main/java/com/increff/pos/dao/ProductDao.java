package com.increff.pos.dao;

import com.increff.pos.pojo.ProductPojo;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;

@Repository
public class ProductDao extends AbstractDao{
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

    private static final String SELECT_BY_BARCODE = "select p from ProductPojo p where barcode = :barcode";

    public ProductPojo selectByBarcode(String barcode) {
        TypedQuery<ProductPojo> query = getQuery(SELECT_BY_BARCODE, ProductPojo.class);
        query.setParameter("barcode", barcode);
        return getFirstRowFromQuery(query);
    }
}

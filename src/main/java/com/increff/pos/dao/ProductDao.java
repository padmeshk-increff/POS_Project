package com.increff.pos.dao;

import com.increff.pos.entity.Product;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;

@Repository
public class ProductDao extends AbstractDao<Product> {

    private static final String SELECT_BY_BARCODE = "select p from Product p where barcode = :barcode";

    public Product selectByBarcode(String barcode) {
        TypedQuery<Product> query = getQuery(SELECT_BY_BARCODE);
        query.setParameter("barcode", barcode);
        return getFirstRowFromQuery(query);
    }
}
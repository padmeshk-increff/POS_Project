package com.increff.pos.dao;

import com.increff.pos.entity.Client;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;

@Repository
public class ClientDao extends AbstractDao<Client>{

    private static final String SELECT_BY_NAME = "select p from Client p where clientName=:clientName";

    public Client selectByName(String clientName) {
        TypedQuery<Client> query = getQuery(SELECT_BY_NAME);
        query.setParameter("clientName", clientName);

        return getFirstRowFromQuery(query);
    }
}

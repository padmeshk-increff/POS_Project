package com.increff.pos.api;

import com.increff.pos.dao.ClientDao;
import com.increff.pos.commons.exception.ApiException;
import com.increff.pos.pojo.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ClientApi {
    
    @Autowired
    private ClientDao clientDao;

    @Transactional(rollbackFor = ApiException.class)
    public Client insert(Client client) throws ApiException {

        if(clientDao.selectByName(client.getClientName()) != null){
            throw new ApiException("Client "+ client +" already exists!");
        }

        clientDao.insert(client);
        return clientDao.selectByName(client.getClientName());

    }

    public Client selectByName(String clientName) throws ApiException{
        if(clientDao.selectByName(clientName)==null)throw new ApiException("Client "+clientName+" doesn't exist!");
        return clientDao.selectByName(clientName);
    }

    public void deleteById(int id) throws ApiException{
        if (clientDao.selectById(id) == null) {
            throw new ApiException("Client is not present!");
        }
        clientDao.deleteById(id);
    }

    public void deleteByName(String clientName) throws ApiException{
        if (clientDao.selectByName(clientName) == null) {
            throw new ApiException("Client is not present!");
        }
        Client client = selectByName(clientName);
        clientDao.deleteById(client.getId());
    }




}
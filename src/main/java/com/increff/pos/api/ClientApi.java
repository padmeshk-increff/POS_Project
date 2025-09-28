package com.increff.pos.api;

import com.increff.pos.dao.ClientDao;
import com.increff.pos.commons.exception.ApiException;
import com.increff.pos.pojo.Client;
import com.increff.pos.utils.ClientUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(rollbackFor = ApiException.class)
public class ClientApi {
    
    @Autowired
    private ClientDao clientDao;

    public Client insert(Client client) throws ApiException {
        Client existingClient = clientDao.selectByName(client.getClientName());
        ClientUtil.ifExists(existingClient);
        clientDao.insert(client);
        return client;
    }

    public Client selectById(Integer id) throws ApiException{
        Client existingClient = clientDao.selectById(id);
        ClientUtil.ifNotExists(existingClient);
        return existingClient;
    }

    public Client selectByName(Client client) throws ApiException{
        Client existingClient = clientDao.selectByName(client.getClientName());
        ClientUtil.ifNotExists(existingClient);
        return existingClient;
    }

    public void deleteById(Integer id) throws ApiException{
        Client existingClient = clientDao.selectById(id);
        ClientUtil.ifNotExists(existingClient);
        clientDao.deleteById(id);
    }

    public List<Client> selectAll(){
        return clientDao.selectAll();
    }

    public Client updateById(Integer id,Client client) throws ApiException{
        Client existingClient = clientDao.selectById(id);
        ClientUtil.ifNotExists(existingClient);
        existingClient.setClientName(client.getClientName());
        clientDao.update(existingClient);
        return existingClient;
    }

}
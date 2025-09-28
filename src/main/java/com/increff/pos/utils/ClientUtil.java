package com.increff.pos.utils;

import com.increff.pos.commons.exception.ApiException;
import com.increff.pos.model.data.ClientData;
import com.increff.pos.model.form.ClientForm;
import com.increff.pos.pojo.Client;

import java.util.ArrayList;
import java.util.List;

public class ClientUtil extends BaseUtil{

    public static Client convert(ClientForm clientForm){
        Client clientPojo = new Client();
        clientPojo.setClientName(clientForm.getClientName());
        return clientPojo;
    }

    public static List<ClientData> convert(List<Client> clientsPojo){
        List<ClientData> clientsData = new ArrayList<>();
        for(Client clientPojo:clientsPojo){
            clientsData.add(convert(clientPojo));
        }
        return clientsData;
    }

    public static ClientData convert(Client clientPojo){
        ClientData clientData = new ClientData();
        clientData.setClientId(clientPojo.getId());
        clientData.setClientName(clientPojo.getClientName());
        clientData.setCreatedAt(clientPojo.getCreatedAt());
        clientData.setUpdatedAt(clientPojo.getUpdatedAt());
        clientData.setVersion(clientPojo.getVersion());
        return clientData;
    }

    public static void ifExists(Object pojo) throws ApiException {
        ifExists(pojo,"Client");
    }

    public static void ifNotExists(Object pojo) throws ApiException{
        ifNotExists(pojo,"Client");
    }
}

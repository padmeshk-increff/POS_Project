package com.increff.pos.dto;

import com.increff.pos.api.ClientApi;
import com.increff.pos.commons.exception.ApiException;
import com.increff.pos.controller.ApiResponse;
import com.increff.pos.model.data.ClientData;
import com.increff.pos.model.form.ClientForm;
import com.increff.pos.pojo.Client;
import com.increff.pos.utils.ClientUtil;
import com.increff.pos.utils.NormalizeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ClientDto {

    @Autowired
    private ClientApi clientApi;

    public ApiResponse<ClientData> add(ClientForm clientForm) throws ApiException {
        NormalizeUtil.normalize(clientForm);
        Client clientPojo = ClientUtil.convert(clientForm);
        clientApi.insert(clientPojo);
        ClientData clientData = ClientUtil.convert(clientPojo);
        return new ApiResponse<>(clientData,"Client " +clientData.getClientName()+"added successfully"); //returns ClientData to the Controller
    }

    public ApiResponse<List<ClientData>> getAll(){
        List<Client> clientsPojo = clientApi.selectAll();
        List<ClientData> clientsData = ClientUtil.convert(clientsPojo);
        return new ApiResponse<>(clientsData,"Clients retrieved successfully");
    }

    public ApiResponse<ClientData> getById(Integer id) throws ApiException{
        Client clientPojo = clientApi.selectById(id);
        ClientData clientData = ClientUtil.convert(clientPojo);
        return new ApiResponse<>(clientData,"Client "+clientData.getClientId()+" retrieved successfully");//returns ClientData to the Controller
    }

    public void deleteById(Integer id) throws ApiException{
        clientApi.deleteById(id);
    }

    public ApiResponse<ClientData> updateById(Integer id,ClientForm clientForm) throws ApiException{
        NormalizeUtil.normalize(clientForm);
        Client clientPojo = ClientUtil.convert(clientForm);
        Client updatedClientPojo = clientApi.updateById(id,clientPojo);
        ClientData updatedClientData = ClientUtil.convert(updatedClientPojo);
        return new ApiResponse<>(updatedClientData,"Client "+id+" has been updated successfully");
    }

}

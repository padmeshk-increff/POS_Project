package com.increff.pos.dto;

import com.increff.pos.commons.exception.ApiException;
import com.increff.pos.model.data.ClientData;
import com.increff.pos.model.form.ClientForm;
import com.increff.pos.pojo.Client;
import lombok.Data;

@Data
public class ClientDto {


    public static Client formToPojo(ClientForm form){
        Client client = new Client();
        client.setClientName(form.getClientName());
        return client;
    }

    public static ClientData PojoToData(Client pojo){
        ClientData data = new ClientData();
        data.setClientId(pojo.getId());
        data.setClientName(pojo.getClientName());
        return data;
    }



//    public foo(form) {
//        pojo = converHelper.convertToPojo(form)
//        pojo = flow.create(pojo)
//                return converJHelper.convetToData(pojo)
//    }
}

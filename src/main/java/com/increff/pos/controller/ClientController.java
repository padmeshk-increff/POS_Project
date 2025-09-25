package com.increff.pos.controller;

import com.increff.pos.api.ClientApi;
import com.increff.pos.commons.exception.ApiException;
import com.increff.pos.dto.ClientDto;
import com.increff.pos.model.data.ClientData;
import com.increff.pos.model.form.ClientForm;
import com.increff.pos.pojo.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ClientController {

    @Autowired
    ClientApi clientApi;

    @PostMapping("/client")
    public ClientData add(@RequestBody ClientForm form) throws ApiException {
        ClientDto.validateName(form);
        Client pojo = ClientDto.formToPojo(form);

        Client updated_pojo=clientApi.insert(pojo);

        return ClientDto.PojoToData(updated_pojo);
    }
}

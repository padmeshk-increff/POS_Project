package com.increff.pos.model.form;

import lombok.Getter;

@Getter
public class ClientForm {
    private String clientName;

    @Override
    public String toString() {
        return "ClientForm{" +
                "clientName='" + clientName + '\'' +
                '}';
    }
}

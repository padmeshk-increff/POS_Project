package com.increff.pos.controller;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
public class ApiResponse <T>{
    private boolean success ;
    private T data;
    private String message;
    private Map<String,String> errors;

    public ApiResponse(T data,String message){
        this.success = true;
        this.data = data;
        this.message = message;
    }

    public ApiResponse(String message){
        this.success=false;
        this.message = message;
    }

    public ApiResponse(String message, Map<String,String> errors){
        this.success = false;
        this.message = message;
        this.errors = errors;
    }
}

package com.increff.pos.utils;

import com.increff.pos.commons.exception.ApiException;

public abstract class BaseUtil {
    public static void ifExists(Object pojo,String fieldName)throws ApiException {
        if(pojo != null)throw new ApiException(fieldName + " already exists!");
    }

    public static void ifNotExists(Object pojo,String fieldName)throws ApiException{
        if(pojo == null)throw new ApiException(fieldName + " doesn't exist!");
    }
}

package com.increff.pos.utils;

import com.increff.pos.model.form.ClientForm;
import com.increff.pos.model.form.ProductForm;

public class NormalizeUtil {

    public static void normalize(ClientForm clientForm) {
        clientForm.setClientName(clientForm.getClientName().trim().toLowerCase());
    }

    public static void normalize(ProductForm productForm){
        productForm.setName(productForm.getName().trim().toLowerCase());
        productForm.setCategory(productForm.getCategory().trim().toLowerCase());
    }

}

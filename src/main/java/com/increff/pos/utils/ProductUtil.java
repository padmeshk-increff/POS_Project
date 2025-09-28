package com.increff.pos.utils;

import com.increff.pos.commons.exception.ApiException;
import com.increff.pos.model.data.ProductData;
import com.increff.pos.model.form.ProductForm;
import com.increff.pos.pojo.Product;

import java.util.ArrayList;
import java.util.List;

public class ProductUtil extends BaseUtil{

    public static Product convert(ProductForm productForm){
        Product productPojo = new Product();
        productPojo.setName(productForm.getName());
        productPojo.setCategory(productForm.getCategory());
        productPojo.setMrp(productForm.getMrp());
        productPojo.setCostPrice(productForm.getCostPrice());
        productPojo.setImageUrl(productForm.getImageUrl());
        productPojo.setBarcode(productForm.getBarcode());
        productPojo.setClientId(productForm.getClientId());
        productPojo.setStatus(productForm.getStatus());
        return productPojo;
    }

    public static List<ProductData> convert(List<Product> productsPojo){
        List<ProductData> productsData = new ArrayList<>();
        for(Product productPojo:productsPojo){
            productsData.add(convert(productPojo));
        }
        return productsData;
    }

    public static ProductData convert(Product productPojo){
        ProductData productData = new ProductData();
        productData.setId(productPojo.getId());
        productData.setBarcode(productPojo.getBarcode());
        productData.setCategory(productPojo.getCategory());
        productData.setMrp(productPojo.getMrp());
        productData.setName(productPojo.getName());
        productData.setStatus(productPojo.getStatus());
        productData.setClientId(productPojo.getClientId());
        productData.setCostPrice(productPojo.getCostPrice());
        productData.setImageUrl(productPojo.getImageUrl());
        productData.setCreatedAt(productPojo.getCreatedAt());
        productData.setUpdatedAt(productPojo.getUpdatedAt());
        productData.setVersion(productPojo.getVersion());
        return productData;
    }

    public static void ifExists(Product productPojo) throws ApiException {
        ifExists(productPojo,"Product");
    }
    public static void ifNotExists(Product productPojo) throws ApiException {
        ifNotExists(productPojo,"Product");
    }
}

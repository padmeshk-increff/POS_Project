package com.increff.pos.utils;

import com.increff.pos.commons.exception.ApiException;
import com.increff.pos.model.data.ProductData;
import com.increff.pos.model.form.ProductForm;
import com.increff.pos.entity.Product;

import java.util.ArrayList;
import java.util.List;

public class ProductUtil extends BaseUtil{

    public static void validate(Product product) throws ApiException {
        if (product.getBarcode() == null || product.getBarcode().trim().isEmpty()) {
            throw new ApiException("Barcode cannot be empty");
        }
        if (product.getName() == null || product.getName().trim().isEmpty()) {
            throw new ApiException("Product name cannot be empty");
        }
        if (product.getCategory() == null || product.getCategory().trim().isEmpty()) {
            throw new ApiException("Category cannot be empty");
        }
        if (product.getClientId() == null) {
            throw new ApiException("Client ID cannot be null");
        }
        if (product.getMrp() == null || product.getMrp() < 0) {
            throw new ApiException("MRP cannot be null or negative");
        }
    }

    public static Product convert(ProductForm productForm){
        Product productPojo = new Product();
        productPojo.setName(productForm.getName());
        productPojo.setCategory(productForm.getCategory());
        productPojo.setMrp(productForm.getMrp());
        productPojo.setImageUrl(productForm.getImageUrl());
        productPojo.setBarcode(productForm.getBarcode());
        productPojo.setClientId(productForm.getClientId());
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
        productData.setClientId(productPojo.getClientId());
        productData.setImageUrl(productPojo.getImageUrl());
        return productData;
    }

}

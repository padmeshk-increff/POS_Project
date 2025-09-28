package com.increff.pos.dto;

import com.increff.pos.api.ProductApi;
import com.increff.pos.commons.exception.ApiException;
import com.increff.pos.controller.ApiResponse;
import com.increff.pos.model.data.ProductData;
import com.increff.pos.model.form.ProductForm;
import com.increff.pos.pojo.Product;
import com.increff.pos.utils.NormalizeUtil;
import com.increff.pos.utils.ProductUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ProductDto {

    @Autowired
    private ProductApi productApi;

    public ApiResponse<ProductData> add(ProductForm productForm) throws ApiException {
        NormalizeUtil.normalize(productForm);
        Product productPojo = ProductUtil.convert(productForm);
        productApi.insert(productPojo);
        ProductData productData = ProductUtil.convert(productPojo);
        return new ApiResponse<>(productData,"Product "+ productData.getName()+" added successfully");
    }

    public ApiResponse<List<ProductData>> getAll() {
        List<Product> productsPojo = productApi.selectAll();
        List<ProductData> productsData = ProductUtil.convert(productsPojo);
        return new ApiResponse<>(productsData,"Products retrieved successfully");
    }

    public ApiResponse<ProductData> getById(Integer id) throws ApiException{
        Product productPojo = productApi.selectById(id);
        ProductData productData = ProductUtil.convert(productPojo);
        return new ApiResponse<>(productData,"Product "+ productData.getId()+" retrieved successfully");
    }

    public ApiResponse<ProductData> updateById(Integer id, ProductForm productForm) throws ApiException{
        NormalizeUtil.normalize(productForm);
        Product productPojo = ProductUtil.convert(productForm);
        Product updatedPojo = productApi.updateById(id,productPojo);
        ProductData productData = ProductUtil.convert(updatedPojo);
        return new ApiResponse<>(productData,"Product "+ productData.getId()+" updated successfully");
    }

    public void deleteById(Integer id) {
        productApi.deleteById(id);
    }
}

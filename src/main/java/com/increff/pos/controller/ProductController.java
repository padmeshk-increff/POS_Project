package com.increff.pos.controller;

import com.increff.pos.commons.exception.ApiException;
import com.increff.pos.dto.ProductDto;
import com.increff.pos.model.data.ProductData;
import com.increff.pos.model.form.ProductForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class ProductController {

    @Autowired
    private ProductDto productDto;

    @RequestMapping(value="/products", method = RequestMethod.GET)
    public ApiResponse<List<ProductData>> getAll(){
        return productDto.getAll();
    }

    @RequestMapping(value="/product",method = RequestMethod.POST)
    public ApiResponse<ProductData> addProduct(@Valid @RequestBody ProductForm form) throws ApiException {
        return productDto.add(form);
    }

    @RequestMapping(value="/products/{id}", method = RequestMethod.GET)
    public ApiResponse<ProductData> getById(@PathVariable(value="id")Integer id) throws ApiException{
        return productDto.getById(id);
    }

    @RequestMapping(value="/products/{id}", method = RequestMethod.PUT)
    public ApiResponse<ProductData> updateById(@PathVariable(value="id")Integer id, @Valid @RequestBody ProductForm form) throws ApiException{
        return productDto.updateById(id,form);
    }

    @RequestMapping(value="/products/{id}", method=RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable(value="id")Integer id){
        productDto.deleteById(id);
    }
}

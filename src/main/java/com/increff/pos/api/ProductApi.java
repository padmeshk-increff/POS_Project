package com.increff.pos.api;

import com.increff.pos.commons.exception.ApiException;
import com.increff.pos.dao.ClientDao;
import com.increff.pos.dao.ProductDao;
import com.increff.pos.pojo.Client;
import com.increff.pos.pojo.Product;
import com.increff.pos.utils.ClientUtil;
import com.increff.pos.utils.ProductUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
@Transactional(rollbackFor = ApiException.class)
public class ProductApi {

    @Autowired
    private ProductDao productDao;

    @Autowired
    private ClientDao clientDao;

    public Product insert(Product product)throws ApiException {
        Product existingProduct = productDao.selectByBarcode(product.getBarcode());
        ProductUtil.ifExists(existingProduct);
        Client existingClient = clientDao.selectById(product.getClientId());
        ClientUtil.ifNotExists(existingClient);
        productDao.insert(product);
        return product;
    }

    public List<Product> selectAll(){
        return productDao.selectAll();
    }

    public Product selectById(Integer id) throws ApiException{
        Product existingProduct = productDao.selectById(id);
        ProductUtil.ifNotExists(existingProduct);
        return productDao.selectById(id);
    }

    public Product updateById(Integer id,Product product) throws ApiException{
        Product existingProduct = productDao.selectById(id);
        ProductUtil.ifNotExists(existingProduct);

        if(!Objects.equals(existingProduct.getClientId(), product.getClientId())){
            throw new ApiException("Client id of a product can't be changed");
        }

        existingProduct.setBarcode(product.getBarcode());
        existingProduct.setName(product.getName());
        existingProduct.setCategory(product.getCategory());
        existingProduct.setMrp(product.getMrp());
        existingProduct.setCostPrice(product.getCostPrice());
        existingProduct.setStatus(product.getStatus());
        existingProduct.setImageUrl(product.getImageUrl());

        productDao.update(existingProduct);
        return existingProduct;
    }

    public void deleteById(Integer id){
        productDao.deleteById(id);
    }
}

package com.increff.pos.api;

import com.increff.pos.commons.exception.ApiException;
import com.increff.pos.dao.ProductDao;
import com.increff.pos.entity.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
@Transactional(rollbackFor = ApiException.class)
public class ProductApi extends AbstractApi {

    @Autowired
    private ProductDao productDao;

    public Product insert(Product product) throws ApiException {
        checkNull(product, "Product object cannot be null");

        Product existingProduct = productDao.selectByBarcode(product.getBarcode());
        checkNotNull(existingProduct, "Product already exists");

        productDao.insert(product);
        return product;
    }

    public List<Product> getAll() {
        return productDao.selectAll();
    }

    public Product getById(Integer id) throws ApiException {
        checkNull(id, "Id cannot be null");

        return productDao.selectById(id);
    }

    public Product getCheckById(Integer id) throws ApiException {
        checkNull(id, "Id cannot be null");

        Product existingProduct = productDao.selectById(id);
        checkNull(existingProduct, "Product " + id + " doesn't exist");

        return existingProduct;
    }

    public Product getCheckByBarcode(String barcode) throws ApiException {
        checkNull(barcode, "Barcode cannot be null");

        Product existingProduct = productDao.selectByBarcode(barcode);
        checkNull(existingProduct, "Product with barcode " + barcode + " doesn't exist");

        return existingProduct;
    }

    public Product getByBarcode(String barcode) throws ApiException {
        checkNull(barcode, "Barcode cannot be null");

        return productDao.selectByBarcode(barcode);
    }

    public Product updateById(Integer id, Product product) throws ApiException {
        checkNull(id, "Id cannot be null");
        checkNull(product, "Product object cannot be null");

        Product existingProduct = productDao.selectById(id);
        checkNull(existingProduct, "Product doesn't exist");

        if (!Objects.equals(existingProduct.getClientId(), product.getClientId())) {
            throw new ApiException("Client id of a product can't be changed");
        }

        if (!existingProduct.getBarcode().equals(product.getBarcode())) {
            Product productWithSameBarcode = productDao.selectByBarcode(product.getBarcode());
            checkNotNull(productWithSameBarcode,"Another product with the barcode '" + product.getBarcode() + "' already exists.");
        }

        existingProduct.setBarcode(product.getBarcode());
        existingProduct.setName(product.getName());
        existingProduct.setCategory(product.getCategory());
        existingProduct.setMrp(product.getMrp());
        existingProduct.setImageUrl(product.getImageUrl());

        productDao.update(existingProduct);
        return existingProduct;
    }

    public void deleteById(Integer id) throws ApiException {
        checkNull(id, "Id cannot be null");

        Product existingProduct = productDao.selectById(id);
        checkNull(existingProduct, "Product " + id + " doesn't exist");
        productDao.deleteById(id);
    }
}
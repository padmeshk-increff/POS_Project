package com.increff.pos.api;

import com.increff.pos.dao.ClientDao;
import com.increff.pos.dao.ProductDao;
import com.increff.pos.pojo.Product;
import com.increff.pos.model.enums.ProductStatus;
import com.increff.pos.commons.exception.ApiException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
public class ProductService {

    @Autowired
    private ProductDao productDao;
    @Autowired
    private ClientDao clientDao;

    @Transactional(rollbackFor = ApiException.class)
    public void add(Product p) throws ApiException {
        normalize(p);
        validate(p);

        // Set initial status
        p.setStatus(ProductStatus.ACTIVE);
        productDao.insert(p);
    }

    public Product get(int id) throws ApiException {
        Product p = productDao.selectById(id);
        if (p == null) {
            throw new ApiException("Product with given ID does not exist, id: " + id);
        }
        return p;
    }

    public List<Product> getAll() {
        return productDao.selectAll();
    }

    @Transactional(rollbackFor = ApiException.class)
    public void update(int id, Product p) throws ApiException {
        normalize(p);
        validateForUpdate(id, p);

        Product existing = get(id); // Throws exception if not found
        existing.setName(p.getName());
        existing.setBarcode(p.getBarcode());
        existing.setMrp(p.getMrp());
        existing.setCostPrice(p.getCostPrice());
        existing.setCategory(p.getCategory());
        // Timestamps are updated automatically by Hibernate
    }

    // Helper method for initial validation
    private void validate(Product p) throws ApiException {
        if (clientDao.selectById(p.getClientId()) == null) {
            throw new ApiException("Client with given ID does not exist, id: " + p.getClientId());
        }
        if (productDao.selectByBarcode(p.getBarcode()) != null) {
            throw new ApiException("Product with the same barcode already exists: " + p.getBarcode());
        }
        if (p.getMrp() < 0 || p.getCostPrice() < 0) {
            throw new ApiException("MRP and Cost Price cannot be negative.");
        }
    }

    // Helper method for update validation
    private void validateForUpdate(int id, Product p) throws ApiException {
        if (clientDao.selectById(p.getClientId()) == null) {
            throw new ApiException("Client with given ID does not exist, id: " + p.getClientId());
        }
        Product existingWithBarcode = productDao.selectByBarcode(p.getBarcode());
        if (existingWithBarcode != null && !Objects.equals(existingWithBarcode.getId(), id)) {
            throw new ApiException("Another product with the same barcode already exists: " + p.getBarcode());
        }
        if (p.getMrp() < 0 || p.getCostPrice() < 0) {
            throw new ApiException("MRP and Cost Price cannot be negative.");
        }
    }

    private void normalize(Product p) {
        p.setName(p.getName().trim().toLowerCase());
        p.setCategory(p.getCategory().trim().toLowerCase());
        p.setBarcode(p.getBarcode().trim().toLowerCase());
    }
}
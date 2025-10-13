package com.increff.pos.dao;

import com.increff.pos.entity.Client;
import com.increff.pos.entity.Product;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.Assert.*;

@Transactional
public class ProductDaoTest extends AbstractDaoTest {

    @Autowired private ProductDao productDao;
    @Autowired private ClientDao clientDao;

    private Integer testClientId;

    // Use @Before to set up common data for all tests
    @Before
    public void setup() {
        Client client = new Client();
        client.setClientName("test-client");
        clientDao.insert(client);
        this.testClientId = client.getId();
    }

    @Test
    public void testInsertAndSelectById() {
        // Arrange
        Product product = createTestProduct("barcode1");

        // Act
        productDao.insert(product);
        Product retrieved = productDao.selectById(product.getId());

        // Assert
        assertNotNull(retrieved);
        assertEquals("barcode1", retrieved.getBarcode());
    }

    @Test
    public void testSelectAll() {
        // Arrange
        productDao.insert(createTestProduct("barcode1"));
        productDao.insert(createTestProduct("barcode2"));

        // Act
        List<Product> productList = productDao.selectAll();

        // Assert
        assertEquals(2, productList.size());
    }

    @Test
    public void testUpdate() {
        // Arrange
        Product product = createTestProduct("barcode1");
        productDao.insert(product);

        // Act
        Product existing = productDao.selectById(product.getId());
        existing.setMrp(150.75);
        productDao.update(existing);

        // Assert
        Product updated = productDao.selectById(product.getId());
        assertEquals(Double.valueOf(150.75), updated.getMrp());
    }

    @Test
    public void testDeleteById() {
        // Arrange
        Product product = createTestProduct("barcode1");
        productDao.insert(product);
        Integer id = product.getId();

        // Act
        productDao.deleteById(id);

        // Assert
        assertNull(productDao.selectById(id));
    }

    @Test
    public void testSelectByBarcode() {
        // Arrange
        productDao.insert(createTestProduct("unique-barcode"));

        // Act
        Product retrieved = productDao.selectByBarcode("unique-barcode");

        // Assert
        assertNotNull(retrieved);
        assertEquals("unique-barcode", retrieved.getBarcode());
    }

    // Helper method to create a product with default values
    private Product createTestProduct(String barcode) {
        Product p = new Product();
        p.setBarcode(barcode);
        p.setClientId(this.testClientId);
        p.setName("test-product");
        p.setMrp(99.99);
        p.setCategory("test-category");
        return p;
    }
}
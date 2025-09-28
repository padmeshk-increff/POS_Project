package com.increff.pos.dao;

import com.increff.pos.pojo.Client;
import com.increff.pos.pojo.Inventory;
import com.increff.pos.pojo.Product;
import com.increff.pos.model.enums.ProductStatus;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.Assert.*;

@Transactional
public class InventoryDaoTest extends AbstractDaoTest {

    @Autowired private InventoryDao inventoryDao;
    @Autowired private ProductDao productDao;
    @Autowired private ClientDao clientDao;

    private Integer testClientId;

    @Before
    public void setup() {
        Client client = new Client();
        // Adding a random number ensures the client name is always unique
        client.setClientName("test-client-" + Math.random());
        clientDao.insert(client);
        this.testClientId = client.getId();
    }

    @Test
    public void testInsertAndSelectById() {
        // Arrange
        Product p = createTestProduct("barcode1");
        Inventory inv = createTestInventory(p.getId(), 100);

        // Act
        inventoryDao.insert(inv);
        Inventory retrieved = inventoryDao.selectById(p.getId());

        // Assert
        assertNotNull(retrieved);
        assertEquals(p.getId(), retrieved.getProductId());
        assertEquals(Integer.valueOf(100), retrieved.getQuantity());
    }

    @Test
    public void testSelectAll() {
        // Arrange
        Product p1 = createTestProduct("barcode1");
        inventoryDao.insert(createTestInventory(p1.getId(), 50));
        Product p2 = createTestProduct("barcode2");
        inventoryDao.insert(createTestInventory(p2.getId(), 75));

        // Act
        List<Inventory> inventoryList = inventoryDao.selectAll();

        // Assert
        assertEquals(2, inventoryList.size());
    }

    @Test
    public void testUpdate() {
        // Arrange
        Product p = createTestProduct("barcode1");
        inventoryDao.insert(createTestInventory(p.getId(), 50));

        // Act
        Inventory existing = inventoryDao.selectById(p.getId());
        existing.setQuantity(200);
        inventoryDao.update(existing);

        // Assert
        Inventory updated = inventoryDao.selectById(p.getId());
        assertEquals(Integer.valueOf(200), updated.getQuantity());
    }

    @Test
    public void testDeleteById() {
        // Arrange
        Product p = createTestProduct("barcode1");
        inventoryDao.insert(createTestInventory(p.getId(), 50));
        Integer id = p.getId();

        // Act
        inventoryDao.deleteById(id);

        // Assert
        assertNull(inventoryDao.selectById(id));
    }

    // Helper method now uses the pre-created client ID
    private Product createTestProduct(String barcode) {
        Product p = new Product();
        p.setBarcode(barcode);
        p.setClientId(this.testClientId); // Reuses the client created in setup()
        p.setName("test-product");
        p.setMrp(99.99);
        p.setCostPrice(80.00);
        p.setCategory("test-category");
        p.setStatus(ProductStatus.ACTIVE);
        productDao.insert(p);
        return p;
    }

    private Inventory createTestInventory(Integer productId, Integer quantity) {
        Inventory inv = new Inventory();
        inv.setProductId(productId);
        inv.setQuantity(quantity);
        return inv;
    }
}
package com.increff.pos.dao;

import com.increff.pos.pojo.ClientPojo;
import com.increff.pos.pojo.InventoryPojo;
import com.increff.pos.pojo.ProductPojo;
import com.increff.pos.pojo.ProductStatus;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import java.time.ZonedDateTime;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@Transactional
public class InventoryDaoTest extends AbstractDaoTest {

    @Autowired private InventoryDao inventoryDao;
    @Autowired private ProductDao productDao;
    @Autowired private ClientDao clientDao;

    @Test
    public void testInsertAndSelect() {
        // Arrange
        ClientPojo client = createClient("test-client");
        ProductPojo product = createProduct("testbarcode", client.getId());

        InventoryPojo inventory = new InventoryPojo();
        inventory.setProductId(product.getId());
        inventory.setQuantity(100);

        // Act
        inventoryDao.insert(inventory);
        InventoryPojo retrievedInventory = inventoryDao.selectById(InventoryPojo.class, product.getId());

        // Assert
        assertNotNull(retrievedInventory);
        assertEquals(product.getId(), retrievedInventory.getProductId());
        assertEquals(Integer.valueOf(100), retrievedInventory.getQuantity());
    }

    // Helper methods to keep tests clean
    private ClientPojo createClient(String name) {
        ClientPojo p = new ClientPojo();
        p.setClientName(name);
        clientDao.insert(p);
        return p;
    }

    private ProductPojo createProduct(String barcode, Integer clientId) {
        ProductPojo p = new ProductPojo();
        p.setBarcode(barcode);
        p.setClientId(clientId);
        p.setName("test-product");
        p.setMrp(10.0);
        p.setCostPrice(5.0);
        p.setCategory("test-cat");
        p.setStatus(ProductStatus.ACTIVE);
        p.setCreatedAt(ZonedDateTime.now());
        p.setUpdatedAt(ZonedDateTime.now());
        productDao.insert(p);
        return p;
    }
}
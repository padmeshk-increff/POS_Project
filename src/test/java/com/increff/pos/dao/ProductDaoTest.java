package com.increff.pos.dao;

import com.increff.pos.pojo.ClientPojo;
import com.increff.pos.pojo.ProductPojo;
import com.increff.pos.pojo.ProductStatus;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;
import java.time.ZonedDateTime;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@Transactional
public class ProductDaoTest extends AbstractDaoTest {

    @Autowired private ProductDao productDao;
    @Autowired private ClientDao clientDao;

    @Test
    public void testInsertAndSelect() {
        // Arrange
        ClientPojo client = new ClientPojo();
        client.setClientName("test-client");
        clientDao.insert(client);

        ProductPojo product = new ProductPojo();
        product.setBarcode("testbarcode123");
        product.setClientId(client.getId());
        product.setName("test-product");
        product.setMrp(99.99);
        product.setCostPrice(80.00);
        product.setCategory("test-category");
        product.setStatus(ProductStatus.ACTIVE);

        // Act
        productDao.insert(product);
        ProductPojo retrievedProduct = productDao.selectById(ProductPojo.class, product.getId());

        // Assert
        assertNotNull(retrievedProduct);
        assertEquals("testbarcode123", retrievedProduct.getBarcode());
        assertEquals(client.getId(), retrievedProduct.getClientId());

        //Act
        ProductPojo expectedProduct = testUpdate(product.getId());

        //Assert
        retrievedProduct = productDao.selectById(ProductPojo.class,product.getId());
        assertNotNull(retrievedProduct);
        assertEquals(expectedProduct,retrievedProduct);

    }

    public ProductPojo testUpdate(int id){
        // Arrange

        ProductPojo product = productDao.selectById(ProductPojo.class,id);
        product.setMrp(9999.00);

        productDao.update(product);

        assertEquals(product,productDao.selectById(ProductPojo.class,id));

        return product;
    }
}
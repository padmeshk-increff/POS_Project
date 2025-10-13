package com.increff.pos.dao;

import com.increff.pos.model.enums.OrderStatus;
import com.increff.pos.entity.*;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.Assert.*;

@Transactional
public class OrderItemDaoTest extends AbstractDaoTest {

    @Autowired private OrderItemDao orderItemDao;
    @Autowired private OrderDao orderDao;
    @Autowired private ProductDao productDao;
    @Autowired private ClientDao clientDao;

    private Integer testOrderId;
    private Integer testProductId;

    @Before
    public void setup() {
        // Arrange: Create prerequisite data
        Client c = new Client();
        c.setClientName("test-client");
        clientDao.insert(c);

        Product p = new Product();
        p.setBarcode("testbarcode");
        p.setClientId(c.getId());
        p.setName("test-product");
        p.setMrp(50.0);
        p.setCategory("test-cat");
        productDao.insert(p);
        this.testProductId = p.getId();

        Order o = new Order();
        o.setOrderStatus(OrderStatus.CREATED);
        o.setTotalAmount(0.0);
        orderDao.insert(o);
        this.testOrderId = o.getId();
    }

    @Test
    public void testInsertAndSelectById() {
        // Arrange
        OrderItem item = createTestOrderItem(testOrderId, testProductId, 5, 45.0);

        // Act
        orderItemDao.insert(item);
        OrderItem retrieved = orderItemDao.selectById(item.getId());

        // Assert
        assertNotNull(retrieved);
        assertEquals(testOrderId, retrieved.getOrderId());
        assertEquals(testProductId, retrieved.getProductId());
    }

    @Test
    public void testSelectAll() {
        // Arrange
        orderItemDao.insert(createTestOrderItem(testOrderId, testProductId, 1, 10.0));
        orderItemDao.insert(createTestOrderItem(testOrderId, testProductId, 2, 20.0));

        // Act
        List<OrderItem> itemList = orderItemDao.selectAll();

        // Assert
        assertEquals(2, itemList.size());
    }

    @Test
    public void testUpdate() {
        // Arrange
        OrderItem item = createTestOrderItem(testOrderId, testProductId, 5, 45.0);
        orderItemDao.insert(item);

        // Act
        OrderItem existing = orderItemDao.selectById(item.getId());
        existing.setQuantity(10);
        orderItemDao.update(existing);

        // Assert
        OrderItem updated = orderItemDao.selectById(item.getId());
        assertEquals(Integer.valueOf(10), updated.getQuantity());
    }

    @Test
    public void testDeleteById() {
        // Arrange
        OrderItem item = createTestOrderItem(testOrderId, testProductId, 5, 45.0);
        orderItemDao.insert(item);
        Integer id = item.getId();

        // Act
        orderItemDao.deleteById(id);

        // Assert
        assertNull(orderItemDao.selectById(id));
    }

    @Test
    public void testSelectByOrderId() {
        // Arrange
        orderItemDao.insert(createTestOrderItem(testOrderId, testProductId, 2, 50.0));
        orderItemDao.insert(createTestOrderItem(testOrderId, testProductId, 3, 48.0));
        Order anotherOrder = new Order();
        anotherOrder.setOrderStatus(OrderStatus.CREATED);
        anotherOrder.setTotalAmount(1.0);
        orderDao.insert(anotherOrder);
        orderItemDao.insert(createTestOrderItem(anotherOrder.getId(), testProductId, 1, 1.0));

        // Act
        List<OrderItem> items = orderItemDao.selectByOrderId(testOrderId);

        // Assert
        assertEquals(2, items.size());
    }

    private OrderItem createTestOrderItem(Integer orderId, Integer productId, Integer quantity, double sellingPrice) {
        OrderItem item = new OrderItem();
        item.setOrderId(orderId);
        item.setProductId(productId);
        item.setQuantity(quantity);
        item.setSellingPrice(sellingPrice);
        return item;
    }
}
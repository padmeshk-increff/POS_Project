package com.increff.pos.dao;

import com.increff.pos.model.enums.OrderStatus;
import com.increff.pos.pojo.Order;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.ZonedDateTime;
import java.util.List;

import static org.junit.Assert.*;

@Transactional
public class OrderDaoTest extends AbstractDaoTest {

    @Autowired
    private OrderDao orderDao;

    @PersistenceContext
    private EntityManager entityManager;

    @Test
    public void testInsertAndSelectById() {
        // Arrange
        Order order = createTestOrder("test-customer");

        // Act
        orderDao.insert(order);
        Order retrieved = orderDao.selectById(order.getId());

        // Assert
        assertNotNull(retrieved);
        assertEquals("test-customer", retrieved.getCustomerName());
        assertEquals(Double.valueOf(199.99), retrieved.getTotalAmount());
        assertNotNull(retrieved.getCreatedAt());
    }

    @Test
    public void testSelectAll() {
        // Arrange
        orderDao.insert(createTestOrder("customer-a"));
        orderDao.insert(createTestOrder("customer-b"));

        // Act
        List<Order> orderList = orderDao.selectAll();

        // Assert
        assertEquals(2, orderList.size());
    }

    @Test
    public void testUpdate() {
        // Arrange
        Order order = createTestOrder("original-customer");
        orderDao.insert(order);
        ZonedDateTime initialUpdateTime = orderDao.selectById(order.getId()).getUpdatedAt();

        // Act
        // Small delay to ensure the new timestamp will be different
        try { Thread.sleep(10); } catch (InterruptedException e) {}

        Order existing = orderDao.selectById(order.getId());
        // Actually change a property to make the entity "dirty"
        existing.setOrderStatus(OrderStatus.INVOICED);
        orderDao.update(existing);

        entityManager.flush();

        // Assert
        Order updated = orderDao.selectById(order.getId());
        assertEquals(OrderStatus.INVOICED, updated.getOrderStatus());
        // Verify that the @UpdateTimestamp was triggered
        assertNotEquals(initialUpdateTime, updated.getUpdatedAt());
    }

    @Test
    public void testDeleteById() {
        // Arrange
        Order order = createTestOrder("customer-to-delete");
        orderDao.insert(order);
        Integer id = order.getId();

        // Act
        orderDao.deleteById(id);

        // Assert
        assertNull(orderDao.selectById(id));
    }

    // Helper method to create a valid OrderPojo for tests
    private Order createTestOrder(String customerName) {
        Order order = new Order();
        order.setOrderStatus(OrderStatus.CREATED);
        order.setCustomerName(customerName);
        order.setTotalAmount(199.99);
        // createdTime and updatedTime are set automatically by Hibernate
        return order;
    }
}
package com.increff.pos.dao;

import com.increff.pos.pojo.OrderPojo;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZonedDateTime;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@Transactional
public class OrderDaoTest extends AbstractDaoTest{

    @Autowired
    private OrderDao orderDao;

    @Test
    public void testSetAndSelect(){
        //Arrange
        OrderPojo orderTest = new OrderPojo();
        orderTest.setCreatedTime(ZonedDateTime.now());
        orderTest.setUpdatedTime(ZonedDateTime.now());

        //Act
        orderDao.insert(orderTest);
        OrderPojo retrievedOrder = orderDao.selectById(OrderPojo.class,orderTest.getId());

        //Assert
        assertNotNull(retrievedOrder);
        assertEquals(orderTest.getId(),retrievedOrder.getId());
    }
}

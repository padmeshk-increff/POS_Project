package com.increff.pos.dao;

import com.increff.pos.pojo.OrderItemPojo;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@Transactional
public class OrderItemDaoTest extends AbstractDaoTest{

    @Autowired
    private OrderItemDao orderItemDao;

    @Test
    public void testSetAndSelect(){
        //Arrange
        OrderItemPojo orderItemTest = new OrderItemPojo();
        orderItemTest.setOrderId(2);
        orderItemTest.setQuantity(5);
        orderItemTest.setSellingPrice(Double.valueOf("23.00"));
        orderItemTest.setProductId(4);

        //Act
        orderItemDao.insert(orderItemTest);
        OrderItemPojo retrievedOrderItem = orderItemDao.selectById(OrderItemPojo.class,orderItemTest.getId());

        //Assert
        assertNotNull(retrievedOrderItem);
        assertEquals(orderItemTest,retrievedOrderItem);
    }
}

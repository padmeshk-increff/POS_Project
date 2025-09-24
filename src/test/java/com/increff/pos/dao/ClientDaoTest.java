package com.increff.pos.dao;

import com.increff.pos.pojo.ClientPojo;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@Transactional
public class ClientDaoTest extends AbstractDaoTest{

    @Autowired
    private ClientDao clientDao;

    @Test
    public void testInsertAndSelect() {
        // Arrange
        ClientPojo client = new ClientPojo();
        client.setClientName("test-client");

        // Act
        clientDao.insert(client);
        ClientPojo retrievedClient = clientDao.selectById(ClientPojo.class, client.getId());

        // Assert
        assertNotNull(retrievedClient);
        assertEquals("test-client", retrievedClient.getClientName());
    }

    @Test
    public void testSelectByName() {
        // Arrange
        ClientPojo client = new ClientPojo();
        client.setClientName("unique-client-name");
        clientDao.insert(client);

        // Act
        ClientPojo retrievedClient = clientDao.selectByName("unique-client-name");

        // Assert
        assertNotNull(retrievedClient);
        assertEquals(client.getId(), retrievedClient.getId());
    }
}
package com.increff.pos.dao;

import com.increff.pos.pojo.Client;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

@Transactional
public class ClientDaoTest extends AbstractDaoTest {

    @Autowired
    private ClientDao clientDao;

    // Test for insert() and selectById()
    @Test
    public void testInsertAndSelectById() {
        // Arrange
        Client client = new Client();
        client.setClientName("test-client");

        // Act
        clientDao.insert(client);
        Client retrievedClient = clientDao.selectById(client.getId());

        // Assert
        assertNotNull(retrievedClient);
        assertEquals("test-client", retrievedClient.getClientName());
    }

    // Test for selectByName()
    @Test
    public void testSelectByName() {
        // Arrange
        Client client = new Client();
        client.setClientName("unique-client-name");
        clientDao.insert(client);

        // Act
        Client retrievedClient = clientDao.selectByName("unique-client-name");

        // Assert
        assertNotNull(retrievedClient);
        assertEquals(client.getId(), retrievedClient.getId());
    }

    // Test for selectAll()
    @Test
    public void testSelectAll() {
        // Arrange
        Client client1 = new Client();
        client1.setClientName("client1");
        clientDao.insert(client1);

        Client client2 = new Client();
        client2.setClientName("client2");
        clientDao.insert(client2);

        // Act
        List<Client> clientList = clientDao.selectAll();

        // Assert
        assertEquals(2, clientList.size());
    }

    // Test for update()
    @Test
    public void testUpdate() {
        // Arrange
        Client client = new Client();
        client.setClientName("original-name");
        clientDao.insert(client);

        // Act
        Client existingClient = clientDao.selectById(client.getId());
        existingClient.setClientName("updated-name");
        clientDao.update(existingClient);

        // Assert
        Client updatedClient = clientDao.selectById(client.getId());
        assertEquals("updated-name", updatedClient.getClientName());
    }

    // Test for deleteById()
    @Test
    public void testDelete() {
        // Arrange
        Client client = new Client();
        client.setClientName("client-to-delete");
        clientDao.insert(client);
        Integer id = client.getId();

        // Act
        clientDao.deleteById(id);

        // Assert
        Client deletedClient = clientDao.selectById(id);
        assertNull(deletedClient);
    }
}
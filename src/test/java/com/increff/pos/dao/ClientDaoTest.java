package com.increff.pos.dao;

import com.increff.pos.config.DbConfig; // Import your DbConfig
import com.increff.pos.pojo.ClientPojo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringJUnit4ClassRunner.class)
// Use @ContextConfiguration with `classes` to load only what you need for the test.
// This is much faster than loading your entire application context.
@ContextConfiguration(classes = { DbConfig.class, ClientDao.class })
public class ClientDaoTest {

    @Autowired
    private ClientDao clientDao;

    @Test
    @Transactional
    public void testInsertAndSelect() {
        // 1. Create a Pojo
        ClientPojo client = new ClientPojo();
        client.setClientName("test-client-java-config");

        // 2. Insert into the database
        clientDao.insert(client);

        // 3. The ID should be generated and not null
        assertNotNull(client.getId());

        // 4. Retrieve the same Pojo from the database
        ClientPojo retrievedClient = clientDao.selectById(ClientPojo.class, client.getId());

        // 5. Assert that the objects are the same
        assertEquals(client.getId(), retrievedClient.getId());
        assertEquals(client.getClientName(), retrievedClient.getClientName());
    }
}
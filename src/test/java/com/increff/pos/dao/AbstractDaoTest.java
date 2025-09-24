package com.increff.pos.dao;

import com.increff.pos.config.TestDbConfig;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestDbConfig.class)
public class AbstractDaoTest {
    //A common template for other dao test files
}

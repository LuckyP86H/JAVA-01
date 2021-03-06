package com.xianyanyang.domain.service;

import com.xianyanyang.domain.service.impl.CustomerServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CustomerServiceITest {

    @Autowired
    private CustomerServiceImpl customerServiceImpl;

    @Test
    public void testAddCustomer() {
        this.customerServiceImpl.addCustomer("xiaoMing");
    }
}

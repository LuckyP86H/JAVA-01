package com.xianyanyang.domain.service.impl;

import com.xianyanyang.domain.entity.Customer;
import com.xianyanyang.domain.repository.CustomerRepository;
import com.xianyanyang.domain.service.CustomerService;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    @Override
    public void addCustomer(String name) {
        Validate.notBlank(name, "顾客姓名不能为空");
        this.customerRepository.createCustomer(new Customer().setName(name));
    }

    @Override
    public Collection<String> listAllCustomerName() {
        return this.customerRepository.listAllCustomerName();
    }

    @Override
    public void updateCustomerName(String id, String name) {
        Validate.notBlank(id, "顾客编号不能为空");
        Validate.notBlank(name, "顾客姓名不能为空");
        this.customerRepository.updateCustomerName(id, name);
    }

    @Override
    public void deleteCustomer(String id) {
        if (StringUtils.isAnyBlank(id)) {
            return;
        }
        customerRepository.deleteCustomer(id);
    }
}

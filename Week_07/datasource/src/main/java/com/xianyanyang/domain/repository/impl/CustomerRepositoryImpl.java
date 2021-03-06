package com.xianyanyang.domain.repository.impl;

import com.xianyanyang.domain.entity.Customer;
import com.xianyanyang.domain.repository.CustomerRepository;
import com.xianyanyang.domain.repository.JpaCustomerRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
public class CustomerRepositoryImpl implements CustomerRepository {

    @Autowired
    private JpaCustomerRepository customerRepository;

    @Override
    public void createCustomer(Customer customer) {
        customerRepository.saveAndFlush(customer);
    }

    @Override
    public Collection<String> listAllCustomerName() {
        List<Customer> list = customerRepository.findAll();
        Collection<String> result = new ArrayList<>();
        list.forEach(item -> {
            result.add(item.getName());
        });
        return result;
    }

    @Override
    public void updateCustomerName(String id, String name) {
        if (StringUtils.isAnyBlank(id, name)) {
            return;
        }
        Optional<Customer> customer = customerRepository.findById(id);
        customer.ifPresent(customer1 -> customer1.setName(name));
        customerRepository.save(customer.get());
    }

    @Override
    public void deleteCustomer(String id) {
        if (StringUtils.isAnyBlank(id)) {
            return;
        }
        customerRepository.deleteById(id);
    }
}

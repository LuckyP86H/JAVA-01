package com.xianyanyang.domain.repository;

import com.xianyanyang.domain.entity.Customer;

import java.util.Collection;

public interface CustomerRepository {

    void createCustomer(Customer customer);

    Collection listAllCustomerName();

    void updateCustomerName(String id, String name);

    void deleteCustomer(String id);

}

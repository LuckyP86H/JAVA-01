package com.xianyanyang.domain.service;

import com.xianyanyang.config.annotation.read.ReadDataBase;
import com.xianyanyang.config.annotation.wrire.WriteDataBase;

import java.util.Collection;

public interface CustomerService {

    @WriteDataBase
    void addCustomer(String name);

    @ReadDataBase
    Collection<String> listAllCustomerName();

    @WriteDataBase
    void updateCustomerName(String id, String name);

    @WriteDataBase
    void deleteCustomer(String id);
}

package com.xianyanyang.domain.repository;

import com.xianyanyang.domain.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaCustomerRepository extends JpaRepository<Customer, String> {
}

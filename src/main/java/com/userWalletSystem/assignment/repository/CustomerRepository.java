package com.userWalletSystem.assignment.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.userWalletSystem.assignment.entity.Customer;

public interface CustomerRepository extends CrudRepository<Customer, Integer> {

    @Query("SELECT c FROM Customer c WHERE c.email=:email")
    Customer findCustomerByEmail(@Param("email") String email);
    }

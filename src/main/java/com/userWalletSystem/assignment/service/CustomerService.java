package com.userWalletSystem.assignment.service;

import com.userWalletSystem.assignment.entity.Customer;
import com.userWalletSystem.assignment.exception.CustomerAlreadyExists;
import com.userWalletSystem.assignment.exception.CustomerDoesNotExists;

public interface CustomerService {
	
	Customer save(Customer customer)throws CustomerAlreadyExists;

    Customer findByCustomeremail(String customeremail) throws CustomerDoesNotExists;

}

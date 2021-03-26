package com.userWalletSystem.assignment.exception;

import com.userWalletSystem.assignment.entity.Customer;

public class CustomerDoesNotExists extends Exception {

public  CustomerDoesNotExists(Customer customer) {
        
    	super("Customer "+customer.getEmail()+" does not exist. Please Sign up to avail the service.");
    }
}

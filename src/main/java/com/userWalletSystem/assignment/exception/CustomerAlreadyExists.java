package com.userWalletSystem.assignment.exception;

import com.userWalletSystem.assignment.entity.Customer;

public class CustomerAlreadyExists extends Exception {
	
    public  CustomerAlreadyExists(Customer customer) {
        
    	super("Customer "+customer.getEmail()+" already exists by the name "+customer.getCustomerName()+". Please Sign in to make transactions.");
    }

}

package com.userWalletSystem.assignment.exception;

import com.userWalletSystem.assignment.entity.Customer;

public class CustomerAlreadyHasWalletException extends Exception {
    public CustomerAlreadyHasWalletException(Customer customer) {
        super("Customer "+customer.getCustomerName()+" already owns a wallet : "+customer.getWallet().getWalletId());
    }
}

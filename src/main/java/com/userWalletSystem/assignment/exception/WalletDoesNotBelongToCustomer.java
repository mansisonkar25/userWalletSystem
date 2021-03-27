package com.userWalletSystem.assignment.exception;

import com.userWalletSystem.assignment.entity.Customer;
import com.userWalletSystem.assignment.entity.Wallet;

public class WalletDoesNotBelongToCustomer extends Exception {
    public WalletDoesNotBelongToCustomer(Customer customer, Wallet wallet) {
        super("Customer with id"+customer.getCustomerId()+" does not have associated walletId : "+wallet.getWalletId());
    }
}

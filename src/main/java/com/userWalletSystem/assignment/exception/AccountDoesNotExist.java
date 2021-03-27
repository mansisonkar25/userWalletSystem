package com.userWalletSystem.assignment.exception;

import com.userWalletSystem.assignment.entity.Account;

public class AccountDoesNotExist extends Exception {

public  AccountDoesNotExist(Account account) {
        
    	super("Account Number"+ account.getAccountNumber()+"does not exist.");
    }
}

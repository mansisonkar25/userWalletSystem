package com.userWalletSystem.assignment.exception;

public class WrongTransactionIdException extends Exception {
	 public WrongTransactionIdException(int transactionId) {
	        super("Transaction with : "+transactionId+" does not exist. Please Enter correct transactionId.");
	    }
}

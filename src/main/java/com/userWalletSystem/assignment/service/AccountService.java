package com.userWalletSystem.assignment.service;

import java.util.List;

import com.userWalletSystem.assignment.entity.Account;
import com.userWalletSystem.assignment.exception.AccountAlreadyExists;
import com.userWalletSystem.assignment.exception.AccountDoesNotExist;
import com.userWalletSystem.assignment.exception.CustomerDoesNotExists;

public interface AccountService {

	Account save(Account account)throws CustomerDoesNotExists;

	List<Account> findAccountByNumber(int accountNumber) throws AccountDoesNotExist;
}

package com.userWalletSystem.assignment.service;

import java.util.List;

import com.userWalletSystem.assignment.entity.Account;
import com.userWalletSystem.assignment.exception.AccountDoesNotExist;

public interface AccountService {

	Account save(Account account);

	List<Account> findAccountByNumber(int accountNumber) throws AccountDoesNotExist;
}

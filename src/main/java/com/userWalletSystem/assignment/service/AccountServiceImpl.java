package com.userWalletSystem.assignment.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.userWalletSystem.assignment.entity.Account;
import com.userWalletSystem.assignment.entity.Customer;
import com.userWalletSystem.assignment.exception.AccountDoesNotExist;
import com.userWalletSystem.assignment.exception.CustomerDoesNotExists;
import com.userWalletSystem.assignment.repository.AccountRepository;

@Service
public class AccountServiceImpl implements AccountService{

	@Autowired
    AccountRepository accountRepository;
	
	@Autowired
    CustomerService customerService;
	
	@Override
	public Account save(Account account)  {
		
		return accountRepository.save(account);
	}

	@Override
	public List<Account> findAccountByNumber(int accountNumber) throws AccountDoesNotExist {
		
		return accountRepository.findAccountByNumber(accountNumber);
	}

}

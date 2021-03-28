package com.userWalletSystem.assignment.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.userWalletSystem.assignment.comparator.BankTransactionSortingComparator;
import com.userWalletSystem.assignment.entity.Account;
import com.userWalletSystem.assignment.entity.Customer;
import com.userWalletSystem.assignment.entity.Transactions;
import com.userWalletSystem.assignment.entity.Wallet;
import com.userWalletSystem.assignment.exception.AccountNotAssociatedWithWalletException;
import com.userWalletSystem.assignment.exception.CustomerAlreadyHasWalletException;
import com.userWalletSystem.assignment.exception.CustomerDoesNotExists;
import com.userWalletSystem.assignment.exception.InsufficientBalanceInWalletException;
import com.userWalletSystem.assignment.exception.WalletIdDoesNotExistException;
import com.userWalletSystem.assignment.exception.WrongTransactionIdException;
import com.userWalletSystem.assignment.repository.AccountRepository;
import com.userWalletSystem.assignment.repository.CustomerRepository;
import com.userWalletSystem.assignment.repository.TansactionsRepository;
import com.userWalletSystem.assignment.repository.WalletRepository;

@Service
public class WalletServiceImpl implements WalletService{


    @Autowired
    WalletRepository walletRepository;
    @Autowired
    AccountRepository accountRepository;
    @Autowired
    CustomerRepository customerRepository;
    @Autowired
    TansactionsRepository tansactionRepository;

    @Override
    public Wallet createWallet(Integer customerId) throws CustomerDoesNotExists, CustomerAlreadyHasWalletException {

        Customer c = customerRepository.findById(customerId).orElse(null);

        if (c == null) {
            throw new CustomerDoesNotExists(c);
        }
        if (c.getWallet() != null) {
            throw new CustomerAlreadyHasWalletException(c);
        }

        Wallet w = new Wallet();
        int accNo=c.getCustomerAccounts().get(0).getAccountNumber();
        Account a=accountRepository.findAccountByNumber(accNo).get(0);
        w.setWalletHolderCustomerId(c);
        if (c.getCustomerAccounts()!=null && !c.getCustomerAccounts().isEmpty()) {
            w.setAccountsInWallet(new ArrayList<>(c.getCustomerAccounts()));
            a.setWalletHolder(w);
            accountRepository.save(a);
        }
        
        return  walletRepository.save(w);
    }

    @Override
    public Float getAccountBalanceForCurrentWallet(Integer walletId, Integer accountId) throws WalletIdDoesNotExistException, AccountNotAssociatedWithWalletException {

        Wallet wallet = walletRepository.findById(walletId).orElse(null);

        // handle walletId does not exist
        if (wallet==null) {
            throw new WalletIdDoesNotExistException(walletId);
        }
        List<Account> a =  wallet.getAccountsInWallet().stream().filter(aa -> aa.getAccountNumber() == accountId).collect(Collectors.toList());
        if (a.isEmpty()) {
            throw new AccountNotAssociatedWithWalletException(walletId, accountId);
        }

            return a.get(0).getBalance();
        }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {Exception.class})
    public Account withdrawFromAccount(Integer walletId, Integer accountId, Float amount, String type) throws WalletIdDoesNotExistException,
            AccountNotAssociatedWithWalletException, InsufficientBalanceInWalletException {

        Wallet wallet = walletRepository.findById(walletId).orElse(null);

        // handle walletId does not exist
        if (wallet==null) {
            throw new WalletIdDoesNotExistException(walletId);
        }
        List<Account> associateAccount =  wallet.getAccountsInWallet().stream().filter(aa -> aa.getAccountNumber() == accountId).collect(Collectors.toList());;
        if (associateAccount.isEmpty()) {
            throw new AccountNotAssociatedWithWalletException(walletId, accountId);
        }
        if (associateAccount.get(0).getBalance() < amount) {
            throw new InsufficientBalanceInWalletException(walletId);
        }

        float currentBalance = associateAccount.get(0).getBalance();
        associateAccount.get(0).setBalance(currentBalance - amount);

        Account ac = accountRepository.save(associateAccount.get(0));
        // bank transactions not required to be shown in this case, hence setting them null
        ac.setBankTransactions(null);

        // Make Entry in Transaction table
        if ("WITHDRAW".equals(type)) {
            makeEntryInTransaction("WITHDRAW", amount, ac.getBalance(), "Withdrawn from account ", "SUCCSESSFUL",ac);
        }
        return ac;
    }

    /**
     * Method is used to make entry into BankTransaction table for the appropriate transaction - deposit, withdrawl or transfer
     * @param amount : Amount to be deposited || withdrawn || transferred
     * @param postBalance : Balance in account after transaction has occurred
     * @param description : Custom String description associated with deposit || withdrawl || transfer
     * @param associatedAccount : Account associated with the transaction
     */
    private void makeEntryInTransaction(String typeOfTransaction, float amount, float postBalance, String description,String status, Account associatedAccount) {
        Transactions bankTransaction = new Transactions(typeOfTransaction, new Date(), amount, postBalance, description, status,associatedAccount);

        tansactionRepository.save(bankTransaction);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {Exception.class})
    public Account depositToAccount(Integer walletId, Integer accountId, Float amount, String type) throws WalletIdDoesNotExistException,
            AccountNotAssociatedWithWalletException {

        Wallet wallet = walletRepository.findById(walletId).orElse(null);

        // handle walletId does not exist
        if (wallet==null) {
            throw new WalletIdDoesNotExistException(walletId);
        }
        List<Account> associateAccount =  wallet.getAccountsInWallet().stream().filter(aa -> aa.getAccountNumber() == accountId).collect(Collectors.toList());;
        if (associateAccount.isEmpty()) {
            throw new AccountNotAssociatedWithWalletException(walletId, accountId);
        }

        float currentBalance = associateAccount.get(0).getBalance();
        associateAccount.get(0).setBalance(currentBalance + amount);

        Account ac = accountRepository.save(associateAccount.get(0));
        // bank transactions not required to be shown in this case, hence setting them null
        ac.setBankTransactions(null);

        // Make Entry in Transaction table

        if ("DEPOSIT".equals(type)) {
            makeEntryInTransaction("DEPOSIT", amount, ac.getBalance(), "Depositted in account ", "SUCCSESSFUL",ac);
        }

        return ac;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {AccountNotAssociatedWithWalletException.class, WalletIdDoesNotExistException.class, Exception.class})
    public void transferToAccount(Integer fromWalletId, Integer fromAccountId, Integer toWalletId, Integer toAccountId, Float amount) throws WalletIdDoesNotExistException,
    AccountNotAssociatedWithWalletException, InsufficientBalanceInWalletException {

        Wallet fromWallet = walletRepository.findById(fromWalletId).orElse(null);
        Wallet toWallet = walletRepository.findById(toWalletId).orElse(null);

        // handle walletId does not exist
        if (fromWallet == null || toWallet == null) {
            throw new WalletIdDoesNotExistException(fromWallet==null? fromWalletId: toWalletId);
        }

        List<Account> fromAssociateAccount =  fromWallet.getAccountsInWallet().stream().filter(aa -> aa.getAccountNumber() == fromAccountId).collect(Collectors.toList());
        List<Account> toAssociateAccount =  toWallet.getAccountsInWallet().stream().filter(aa -> aa.getAccountNumber() == toAccountId).collect(Collectors.toList());

        if (fromAssociateAccount.isEmpty()) {
            throw new AccountNotAssociatedWithWalletException(fromWalletId, fromAccountId);
        }
        if (toAssociateAccount.isEmpty()) {
            throw new AccountNotAssociatedWithWalletException(toWalletId, toAccountId);
        }

        // Withdraw
        Account fromAccount = this.withdrawFromAccount(fromWalletId,fromAccountId,amount,"TRANSFER");
        StringBuilder sb = new StringBuilder();
        sb
                .append("$")
                .append(amount)
                .append(" transferred to accountId : ")
                .append(toAccountId);
        makeEntryInTransaction("TRANSFER", amount, fromAccount.getBalance(), sb.toString(),"SUCCSESSFUL" ,fromAccount);


        // deposit
        Account toAccount = this.depositToAccount(toWalletId, toAccountId, amount,"TRANSFER");
        StringBuilder sb1 = new StringBuilder();
        sb1
                .append("$")
                .append(amount)
                .append(" transferred from accountId : ")
                .append(fromAccountId);
        makeEntryInTransaction("TRANSFER", amount, toAccount.getBalance(), sb1.toString(),"SUCCSESSFUL" ,toAccount);

    }

    @Override
    public List<Transactions> getStatement(Integer walletId, Integer accountId, Integer n) throws WalletIdDoesNotExistException,
            AccountNotAssociatedWithWalletException{

        Wallet wallet = walletRepository.findById(walletId).orElse(null);

        // handle walletId does not exist
        if (wallet==null) {
            throw new WalletIdDoesNotExistException(walletId);
        }
        List<Account> associateAccount =  wallet.getAccountsInWallet().stream().filter(aa -> aa.getAccountNumber() == accountId).collect(Collectors.toList());;
        if (associateAccount.isEmpty()) {
            throw new AccountNotAssociatedWithWalletException(walletId, accountId);
        }


        List<Transactions> bankTransactions = associateAccount.get(0).getBankTransactions();

        Collections.sort(bankTransactions, new BankTransactionSortingComparator());

        // handling length of last N transactions
        //n = bankTransactions.size()>=n?n:bankTransactions.size();
        //return bankTransactions.subList(0, n);
        return bankTransactions;

    }

	@Override
	public Transactions getTransactionStatus(int transactionId) throws WrongTransactionIdException {
		Transactions transaction = tansactionRepository.findById(transactionId).orElse(null);

        // handle walletId does not exist
        if (transaction==null) {
            throw new WrongTransactionIdException(transactionId);
        }
        
        return transaction;
	}
	
	

}

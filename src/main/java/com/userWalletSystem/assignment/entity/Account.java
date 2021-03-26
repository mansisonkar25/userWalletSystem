package com.userWalletSystem.assignment.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
public class Account implements Serializable {

    @Id //to set as primary key
    @GeneratedValue(strategy = GenerationType.IDENTITY) // to set as autoincrement
    private int accountNumber;
    private float balance;

    @ManyToOne
    private Customer accountHolder;

    @ManyToOne
    @JsonIgnore
    private Wallet walletHolder;

    @OneToMany(mappedBy = "transactionFromAccount")
    private List<Transactions> bankTransactions;

    public Account() {
    	
    }
    
	public Account(int accountNumber, float balance, Customer accountHolder, Wallet walletHolder,
			List<Transactions> bankTransactions) {
		super();
		this.accountNumber = accountNumber;
		this.balance = balance;
		this.accountHolder = accountHolder;
		this.walletHolder = walletHolder;
		this.bankTransactions = bankTransactions;
	}



	public int getAccountNumber() {
		return accountNumber;
	}



	public void setAccountNumber(int accountNumber) {
		this.accountNumber = accountNumber;
	}



	public float getBalance() {
		return balance;
	}



	public void setBalance(float balance) {
		this.balance = balance;
	}



	public Customer getAccountHolder() {
		return accountHolder;
	}



	public void setAccountHolder(Customer accountHolder) {
		this.accountHolder = accountHolder;
	}



	public Wallet getWalletHolder() {
		return walletHolder;
	}



	public void setWalletHolder(Wallet walletHolder) {
		this.walletHolder = walletHolder;
	}



	public List<Transactions> getBankTransactions() {
		return bankTransactions;
	}



	public void setBankTransactions(List<Transactions> bankTransactions) {
		this.bankTransactions = bankTransactions;
	}



	@Override
	public String toString() {
		return "Account [accountNumber=" + accountNumber + ", balance=" + balance + ", accountHolder=" + accountHolder
				+ ", walletHolder=" + walletHolder + ", bankTransactions=" + bankTransactions + "]";
	}
    
    

}

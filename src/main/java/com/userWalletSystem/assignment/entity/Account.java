package com.userWalletSystem.assignment.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

@Entity
public class Account implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int accountNumber;
    private float balance;

    @ManyToOne(cascade = CascadeType.ALL)
    @JsonBackReference
    private Customer accountHolder;

    @ManyToOne(cascade = CascadeType.ALL)
    @JsonBackReference
    private Wallet walletHolder;

    @OneToMany(mappedBy = "transactionFromAccount", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@Fetch(value = FetchMode.SUBSELECT)
	@JsonManagedReference
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

package com.userWalletSystem.assignment.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import java.io.Serializable;
import java.util.List;

@Entity
public class Wallet implements Serializable {

    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY) 
    private int walletId;

    @OneToMany(mappedBy = "walletHolder",orphanRemoval = true, cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@Fetch(value = FetchMode.SUBSELECT)
	//@JsonManagedReference
    private List<Account> accountsInWallet;

    @OneToOne(cascade = CascadeType.ALL)
    @JsonBackReference
    private Customer walletHolderCustomerId;

    public Wallet() {
    	
    }
    
	public Wallet(int walletId, List<Account> accountsInWallet, Customer walletHolderCustomerId) {
		super();
		this.walletId = walletId;
		this.accountsInWallet = accountsInWallet;
		this.walletHolderCustomerId = walletHolderCustomerId;
	}



	public int getWalletId() {
		return walletId;
	}



	public void setWalletId(int walletId) {
		this.walletId = walletId;
	}



	public List<Account> getAccountsInWallet() {
		return accountsInWallet;
	}



	public void setAccountsInWallet(List<Account> accountsInWallet) {
		this.accountsInWallet = accountsInWallet;
	}



	public Customer getWalletHolderCustomerId() {
		return walletHolderCustomerId;
	}



	public void setWalletHolderCustomerId(Customer walletHolderCustomerId) {
		this.walletHolderCustomerId = walletHolderCustomerId;
	}



	@Override
	public String toString() {
		return "Wallet [walletId=" + walletId + ", accountsInWallet=" + accountsInWallet + ", walletHolderCustomerId="
				+ walletHolderCustomerId + "]";
	}

    
    
}

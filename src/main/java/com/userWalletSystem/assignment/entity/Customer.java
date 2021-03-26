package com.userWalletSystem.assignment.entity;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
public class Customer implements Serializable {

    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY) 
    private int userId;
    private String customerName;
    private String password;
    @Column(unique=true)
    private String email;

    @OneToOne(mappedBy = "walletHolderCustomerId")
    @JsonIgnore
    private Wallet wallet;

    @OneToMany(mappedBy = "accountHolder")
    @JsonIgnore
    private List<Account> customerAccounts;

    public Customer() {
    	
    }
    
	public Customer(int userId, String customerName, String password, String email, Wallet wallet,
			List<Account> customerAccounts) {
		super();
		this.userId = userId;
		this.customerName = customerName;
		this.password = password;
		this.email = email;
		this.wallet = wallet;
		this.customerAccounts = customerAccounts;
	}



	public int getUserId() {
		return userId;
	}



	public void setUserId(int userId) {
		this.userId = userId;
	}



	public String getCustomerName() {
		return customerName;
	}



	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}



	public String getPassword() {
		return password;
	}



	public void setPassword(String password) {
		this.password = password;
	}



	public String getEmail() {
		return email;
	}



	public void setEmail(String email) {
		this.email = email;
	}



	public Wallet getWallet() {
		return wallet;
	}



	public void setWallet(Wallet wallet) {
		this.wallet = wallet;
	}



	public List<Account> getCustomerAccounts() {
		return customerAccounts;
	}



	public void setCustomerAccounts(List<Account> customerAccounts) {
		this.customerAccounts = customerAccounts;
	}



	@Override
	public String toString() {
		return "Customer [userId=" + userId + ", customerName=" + customerName + ", password=" + password + ", email="
				+ email + ", wallet=" + wallet + ", customerAccounts=" + customerAccounts + "]";
	}
    
    

}

package com.userWalletSystem.assignment.entity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import java.io.Serializable;
import java.util.List;

@Entity
public class Customer implements Serializable {

    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY) 
    private int customerId;
    private String customerName;
    private String password;
    private String confirmPassword;
    @Column(unique=true)
    private String email;

    @OneToOne(mappedBy = "walletHolderCustomerId",orphanRemoval = true, cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JsonManagedReference
    private Wallet wallet;

    @OneToMany(mappedBy = "accountHolder",orphanRemoval = true, cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@Fetch(value = FetchMode.SUBSELECT)
	@JsonManagedReference
    private List<Account> customerAccounts;

    public Customer() {
    	
    }
   
	public Customer(int customerId, String customerName, String password, String confirmPassword, String email,
			Wallet wallet, List<Account> customerAccounts) {
		super();
		this.customerId = customerId;
		this.customerName = customerName;
		this.password = password;
		this.confirmPassword = confirmPassword;
		this.email = email;
		this.wallet = wallet;
		this.customerAccounts = customerAccounts;
	}





	public int getCustomerId() {
		return customerId;
	}



	public void setCustomerId(int customerId) {
		this.customerId = customerId;
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
	
	

	public String getConfirmPassword() {
		return confirmPassword;
	}

	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}

	@Override
	public String toString() {
		return "Customer [customerId=" + customerId + ", customerName=" + customerName + ", password=" + password
				+ ", confirmPassword=" + confirmPassword + ", email=" + email + ", wallet=" + wallet
				+ ", customerAccounts=" + customerAccounts + "]";
	}



	
    
    

}

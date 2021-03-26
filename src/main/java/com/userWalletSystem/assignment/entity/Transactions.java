package com.userWalletSystem.assignment.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
public class Transactions implements Serializable {

    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY) 
    private int transactionId;
    private String type;
    private Date timestamp;
    private float amount;
    private float balanceAfterTransaction;
    private String status;

    @ManyToOne
    @JsonIgnore
    private Account transactionFromAccount;

    public Transactions() {
    	
    }
    
	public Transactions(int transactionId, String type, Date timestamp, float amount, float balanceAfterTransaction,
			String status, Account transactionFromAccount) {
		super();
		this.transactionId = transactionId;
		this.type = type;
		this.timestamp = timestamp;
		this.amount = amount;
		this.balanceAfterTransaction = balanceAfterTransaction;
		this.status = status;
		this.transactionFromAccount = transactionFromAccount;
	}



	public int getTransactionId() {
		return transactionId;
	}



	public void setTransactionId(int transactionId) {
		this.transactionId = transactionId;
	}



	public String getType() {
		return type;
	}



	public void setType(String type) {
		this.type = type;
	}



	public Date getTimestamp() {
		return timestamp;
	}



	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}



	public float getAmount() {
		return amount;
	}



	public void setAmount(float amount) {
		this.amount = amount;
	}



	public float getBalanceAfterTransaction() {
		return balanceAfterTransaction;
	}



	public void setBalanceAfterTransaction(float balanceAfterTransaction) {
		this.balanceAfterTransaction = balanceAfterTransaction;
	}



	public String getStatus() {
		return status;
	}



	public void setStatus(String status) {
		this.status = status;
	}



	public Account getTransactionFromAccount() {
		return transactionFromAccount;
	}



	public void setTransactionFromAccount(Account transactionFromAccount) {
		this.transactionFromAccount = transactionFromAccount;
	}



	@Override
	public String toString() {
		return "Transactions [transactionId=" + transactionId + ", type=" + type + ", timestamp=" + timestamp
				+ ", amount=" + amount + ", balanceAfterTransaction=" + balanceAfterTransaction + ", status=" + status
				+ ", transactionFromAccount=" + transactionFromAccount + "]";
	}
    
    

}

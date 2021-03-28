package com.userWalletSystem.assignment.controller;

import java.util.List;

import org.omg.IOP.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.userWalletSystem.assignment.entity.ResponseAsEntity;
import com.userWalletSystem.assignment.entity.Transactions;
import com.userWalletSystem.assignment.exception.AccountNotAssociatedWithWalletException;
import com.userWalletSystem.assignment.exception.WalletIdDoesNotExistException;
import com.userWalletSystem.assignment.exception.WrongTransactionIdException;
import com.userWalletSystem.assignment.repository.TansactionsRepository;
import com.userWalletSystem.assignment.repository.WalletRepository;
import com.userWalletSystem.assignment.service.WalletService;

@RestController
public class TransactionController {
	
	@Autowired
	TansactionsRepository transactionRespository;
	
	@Autowired
	WalletService walletService;
	
	@GetMapping("/api/wallet/{walletId}/account/{accountId}/viewPassbook/")
    public ResponseEntity<ResponseAsEntity> getStatement(@PathVariable ("walletId") int walletId,
                                        @PathVariable ("accountId") int accountId) {

    	ResponseAsEntity response = new ResponseAsEntity();

        try {
            List<Transactions> lb = walletService.getStatement(walletId, accountId, 10);
            response.setStatus("200");
            response.setDescription("Statement fetched successfully!!");
            response.setData(lb);
            return new ResponseEntity<>(response, HttpStatus.OK);

        }catch(WalletIdDoesNotExistException e) {
            response.setStatus(String.valueOf(HttpStatus.NOT_FOUND));
            response.setDescription(e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
        catch(AccountNotAssociatedWithWalletException e) {
            response.setStatus(String.valueOf(HttpStatus.EXPECTATION_FAILED));
            response.setDescription(e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.EXPECTATION_FAILED);
        }

    }
	
	@GetMapping("/api/transaction/{transactionId}")
    public ResponseEntity<ResponseAsEntity> getStatus(@PathVariable ("transactionId") int transactionId) {

    	ResponseAsEntity response = new ResponseAsEntity();

        try {
            Transactions lb = walletService.getTransactionStatus(transactionId);
            response.setStatus("200");
            response.setDescription("Status fetched successfully!!");
            response.setData("Status of Transaction: "+lb.getStatus());
            return new ResponseEntity<>(response, HttpStatus.OK);

        }catch(WrongTransactionIdException e) {
            response.setStatus(String.valueOf(HttpStatus.NOT_FOUND));
            response.setDescription(e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
        

    }
	
	 @GetMapping("/api/wallet/{walletId}/account/{accountId}/ComputeChargesAndCommisions")
	    public  ResponseEntity<ResponseAsEntity>  getAccountBalance (
	            @PathVariable("walletId") int walletId,
	            @PathVariable("accountId") int accountId){



	    	ResponseAsEntity response = new ResponseAsEntity();

	        try {
	            Float balance = walletService.getAccountBalanceForCurrentWallet(walletId, accountId);
	            response.setStatus("200");
	            response.setDescription("Charges & Commisions on amount "+balance);
	            response.setData(compute(balance));
	            return new ResponseEntity<>(response, HttpStatus.OK);

	        }catch(WalletIdDoesNotExistException e) {
	            response.setStatus(String.valueOf(HttpStatus.NOT_FOUND));
	            response.setDescription(e.getMessage());
	            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
	        }
	        catch(AccountNotAssociatedWithWalletException e) {
	            response.setStatus(String.valueOf(HttpStatus.EXPECTATION_FAILED));
	            response.setDescription(e.getMessage());
	            return new ResponseEntity<>(response, HttpStatus.EXPECTATION_FAILED);
	        }

	    }
	 
	 private StringBuilder compute(float balance) {
		 float charges=(float) (0.2/100*balance);
		 float commission= (float) (0.05/100*balance);
		 StringBuilder sb=new StringBuilder();
		 sb.append("Charge: $").append(charges).append(" & Commission: $").append(commission);
		 return sb;
		 
	 }

}

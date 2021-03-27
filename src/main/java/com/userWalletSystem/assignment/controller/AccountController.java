package com.userWalletSystem.assignment.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.userWalletSystem.assignment.entity.Account;
import com.userWalletSystem.assignment.entity.ResponseAsEntity;
import com.userWalletSystem.assignment.exception.CustomerDoesNotExists;
import com.userWalletSystem.assignment.service.AccountService;

@RestController
public class AccountController {
	
	@Autowired
	private AccountService accountService;
	
	@PostMapping(value="/api/createAccount",consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseAsEntity> createAccount(@RequestBody Account account) {
		ResponseAsEntity response=new ResponseAsEntity();		
        try {
            Account newAcount = accountService.save(account);
            response.setStatus("200");
            response.setDescription("Account created successfully!");
            response.setData(newAcount);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        catch(CustomerDoesNotExists e) {
            response.setStatus(String.valueOf(HttpStatus.EXPECTATION_FAILED));
            response.setDescription(e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.EXPECTATION_FAILED);
        }
    }

}

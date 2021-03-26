package com.userWalletSystem.assignment.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.userWalletSystem.assignment.entity.Customer;
import com.userWalletSystem.assignment.entity.ResponseAsEntity;
import com.userWalletSystem.assignment.exception.CustomerAlreadyExists;
import com.userWalletSystem.assignment.service.CustomerService;

@RestController
public class SignUpController {
	 @Autowired
	 private CustomerService customerService;
	 
	 
	@PostMapping(value = "api/registration")
    public ResponseEntity<ResponseAsEntity> signUp(@RequestBody Customer customer) {
		ResponseAsEntity response=new ResponseAsEntity();		
        try {
            Customer newCustomer = customerService.save(customer);
            response.setStatus("200");
            response.setDescription("Sign Up successful!");
            response.setData(newCustomer);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        catch(CustomerAlreadyExists e) {
            response.setStatus(String.valueOf(HttpStatus.EXPECTATION_FAILED));
            response.setDescription(e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.EXPECTATION_FAILED);
        }
    }
}

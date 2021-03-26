package com.userWalletSystem.assignment.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.userWalletSystem.assignment.entity.Customer;
import com.userWalletSystem.assignment.entity.ResponseAsEntity;
import com.userWalletSystem.assignment.exception.CustomerDoesNotExists;
import com.userWalletSystem.assignment.service.CustomerService;

public class SignInController {

	 @Autowired
	 private CustomerService customerService;
	 
	@GetMapping(value = "api/login/customeremail/{customeremail}/password/{password}/")
	@ResponseBody
    public ResponseEntity<ResponseAsEntity> signIn(@PathVariable("customeremail") String customeremail,
            @PathVariable("password") String password)
	{
		ResponseAsEntity response=new ResponseAsEntity();		
	
        try {
            Customer existingCustomer = customerService.findByCustomeremail(customeremail);
            if((existingCustomer.getPassword().toString()).compareTo(password)==0)
            {
            	response.setStatus("200");
	            response.setDescription(existingCustomer.getCustomerName()+", you are logged in!");
	            response.setData(existingCustomer);
	            return new ResponseEntity<>(response, HttpStatus.OK);
            }
            else {
            	response.setStatus(String.valueOf(HttpStatus.BAD_REQUEST));
	            response.setDescription("Incorrect Password !");
	            response.setData(existingCustomer);
	            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
            }
        }
        catch(CustomerDoesNotExists e) {
            response.setStatus(String.valueOf(HttpStatus.EXPECTATION_FAILED));
            response.setDescription(e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.EXPECTATION_FAILED);
        }
        
    }
}

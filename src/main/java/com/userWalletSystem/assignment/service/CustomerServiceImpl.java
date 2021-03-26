package com.userWalletSystem.assignment.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.userWalletSystem.assignment.entity.Customer;
import com.userWalletSystem.assignment.exception.CustomerAlreadyExists;
import com.userWalletSystem.assignment.repository.CustomerRepository;

@Service
public class CustomerServiceImpl implements CustomerService{

	@Autowired
	private CustomerRepository customerRepository;
	

	@Override
	public Customer save(Customer customer)throws CustomerAlreadyExists {
		
			String email=customer.getEmail();
	        Customer c = customerRepository.findCustomerByEmail(email);

	        if (c!= null) {
	            throw new CustomerAlreadyExists(customer);
	        }
		return customerRepository.save(customer);
	}

	@Override
	public Customer findByCustomeremail(String customeremail) {
		return  customerRepository.findCustomerByEmail(customeremail);
				
	}

	
}

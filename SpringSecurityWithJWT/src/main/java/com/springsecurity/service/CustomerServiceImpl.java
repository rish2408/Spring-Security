package com.springsecurity.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springsecurity.exception.CustomerException;
import com.springsecurity.model.Customer;
import com.springsecurity.repository.CustomerRepository;

@Service
public class CustomerServiceImpl implements CustomerService {

	@Autowired
	private CustomerRepository customerRepository;

	@Override
	public Customer registerCustomer(Customer customer) {

		return customerRepository.save(customer);
	}

	@Override
	public Customer getCustomerDetailsByEmail(String email) throws CustomerException {

		return customerRepository.findByEmail(email)
				.orElseThrow(() -> new CustomerException("Customer Not Found With Email " + email));
	}

	@Override
	public List<Customer> getAllCustomerDetails() throws CustomerException {

		List<Customer> getAllCustomer = customerRepository.findAll();

		if (getAllCustomer.isEmpty())
			throw new CustomerException("No Customer Find....");

		return getAllCustomer;
	}

}

package com.springsecurity.service;

import java.util.List;

import com.springsecurity.exception.CustomerException;
import com.springsecurity.model.Customer;

public interface CustomerService {

	public Customer registerCustomer(Customer customer);

	public Customer getCustomerDetailsByEmail(String email) throws CustomerException;

	public List<Customer> getAllCustomerDetails() throws CustomerException;
}

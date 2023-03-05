package com.springsecurity.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.springsecurity.model.Customer;
import com.springsecurity.service.CustomerService;

@RestController
public class CustomerController {

	@Autowired
	private CustomerService customerService;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@GetMapping("/welcome")
	public String sayWelcome() {
		return "Welcome to Spring Security Without JWT Application";
	}

	@PostMapping("/customers")
	public ResponseEntity<Customer> registerCustomerHandler(@RequestBody Customer customer) {
		customer.setPassword(passwordEncoder.encode(customer.getPassword()));

		Customer registeredCustomer = customerService.registerCustomer(customer);

		return new ResponseEntity<Customer>(registeredCustomer, HttpStatus.ACCEPTED);
	}

	@GetMapping("customers/{email}")
	public ResponseEntity<Customer> getCustomerByEmailHandler(@PathVariable("email") String email) {
		Customer getCustomerByEmail = customerService.getCustomerDetailsByEmail(email);

		return new ResponseEntity<Customer>(getCustomerByEmail, HttpStatus.ACCEPTED);
	}

	@GetMapping("/all-customers")
	public ResponseEntity<List<Customer>> getAllCustomerHandler() {
		List<Customer> allCustomers = customerService.getAllCustomerDetails();

		return new ResponseEntity<List<Customer>>(allCustomers, HttpStatus.ACCEPTED);
	}
}

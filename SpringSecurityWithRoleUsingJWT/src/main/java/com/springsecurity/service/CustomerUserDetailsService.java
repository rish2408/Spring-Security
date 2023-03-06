package com.springsecurity.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.springsecurity.model.Authority;
import com.springsecurity.model.Customer;
import com.springsecurity.repository.CustomerRepository;

@Service
public class CustomerUserDetailsService implements UserDetailsService {

	@Autowired
	private CustomerRepository customerRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		Optional<Customer> customerByEmail = customerRepository.findByEmail(username);

		if (customerByEmail.isPresent()) {
			Customer customer = customerByEmail.get();

			// Empty Authorities List
			List<GrantedAuthority> authorities = new ArrayList<>();
			
			List<Authority> auths = customer.getAuthorities();

			for(Authority auth: auths)
			{
				SimpleGrantedAuthority sga = new SimpleGrantedAuthority(auth.getName());
				System.out.println("Simple Granted Authority "+sga);
				authorities.add(sga);
			}
			
			System.out.println("Granted Authorities "+authorities);
			
			return new User(customer.getEmail(), customer.getPassword(), authorities);
			
//			return new CustomerUserDetails(customer);
		} else
			throw new BadCredentialsException("User Details not found with this username " + username);

	}

}

package com.springsecurity.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.springsecurity.model.Customer;
import com.springsecurity.repository.CustomerRepository;

@Component
public class MyAuthenticationProvider implements AuthenticationProvider {

	@Autowired
	private CustomerRepository customRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {

		String username = authentication.getName();
		String password = authentication.getCredentials().toString();

		System.out.println(username);
		System.out.println(password);

		Optional<Customer> userFindByEmail = customRepository.findByEmail(username);

		if (userFindByEmail.isPresent()) {
			Customer customer = userFindByEmail.get();

			if (passwordEncoder.matches(password, customer.getPassword())) {
				// Empty Authorities List
				List<GrantedAuthority> authorities = new ArrayList<>();

//				If it contain authorities so we can add them in a list
//				authorities.add(new SimpleGrantedAuthority(customer.getRole())); 

				return new UsernamePasswordAuthenticationToken(username, password, authorities);
			}else
				throw new BadCredentialsException("Invalid Password");
		} else
			throw new BadCredentialsException("User Details not found with this username " + username);
	}

	@Override
	public boolean supports(Class<?> authentication) {

		return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
	}

}

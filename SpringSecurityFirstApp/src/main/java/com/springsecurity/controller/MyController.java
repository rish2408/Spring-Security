package com.springsecurity.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MyController {

	@GetMapping("/welcome")
	public String sayWelcome()
	{
		 return "Welcome to Spring Security Application";
	}
}

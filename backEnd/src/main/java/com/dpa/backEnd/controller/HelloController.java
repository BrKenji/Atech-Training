package com.dpa.backEnd.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {
	
	@GetMapping("/helloWorld")
	public String getHello() {
		return "Hello, World !!!";	
		
	}

}

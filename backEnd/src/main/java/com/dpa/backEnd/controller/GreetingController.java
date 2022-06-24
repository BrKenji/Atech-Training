package com.dpa.backEnd.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import com.dpa.backEnd.model.Greeting;
import com.dpa.backEnd.repository.GreetingRepository;

@RestController
@CrossOrigin(
		origins = "http://localhost:4200/greeting",
		methods = {RequestMethod.POST, RequestMethod.GET}
				)
@RequestMapping
public class GreetingController {
	
	@Autowired
	private GreetingRepository greetingRepo;
	
	@PostMapping(value = "/hello/")
	public Greeting createGreeting(@RequestBody String name) {
		Greeting greeting = new Greeting("");
		greeting.setName(name);
		greeting.setDateTime(LocalDateTime.now());
		this.greetingRepo.insert(greeting);
		return new Greeting(name);
	}
	
	@GetMapping(value = "/{name}")
	public List<Greeting> getByName(@PathVariable("name") String name) {
		return this.greetingRepo.findByName(name);
	}
	
	@GetMapping(value = "/history")
	public List<Greeting> history() {
		return (List<Greeting>) this.greetingRepo.findAll();
	}
}

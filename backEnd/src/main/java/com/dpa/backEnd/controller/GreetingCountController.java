package com.dpa.backEnd.controller;

import java.util.List;
import java.util.TreeMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dpa.backEnd.model.Greeting;
import com.dpa.backEnd.repository.GreetingRepository;

@RestController
@RequestMapping(value = "/dataCount")
public class GreetingCountController {
	
	@Autowired
	private GreetingRepository greetingRepo;
	
	@GetMapping(value= "/byName")
	public TreeMap<String, Integer> countByNames() {
		TreeMap<String, Integer> nameCount = new TreeMap<>();
		
		List<Greeting> dataSet = greetingRepo.findAll();
		
		// Iterate through the dataSet and add values and keys to HashMap
		for (Greeting singleData : dataSet) {
			String nameValue = singleData.getName();
			Integer numberOfOcurrances = nameCount.get(nameValue);
			
			nameCount = putDataCountOnTreeMap(nameValue, numberOfOcurrances, nameCount);
						
		}
		
		return nameCount;
			
	}
	
	@GetMapping(value = "/byGreeting")
	public TreeMap<String, Integer> countByGreeting() {
		TreeMap<String, Integer> greetingCount = new TreeMap<>();
		
		List<Greeting> dataSet = greetingRepo.findAll();
		
		// Iterate through the dataSet and add values and keys to HashMap
		for (Greeting singleData : dataSet) {
			String greeting = singleData.getGreeting();			
			Integer numberOfOccurrences = greetingCount.get(greeting);
			greetingCount = putDataCountOnTreeMap(greeting, numberOfOccurrences, greetingCount);
						
		}		
			
		return greetingCount;
		
	}
	
	@GetMapping(value = "/byMonth")
	public TreeMap<String, Integer> countByMonth() {
		TreeMap<String, Integer> monthTreeMap = new TreeMap<>();

		List<Greeting> dataSet = greetingRepo.findAll();
		
		// Iterate through the dataSet and add values and keys to HashMap
		for (Greeting singleData : dataSet) {
			String month = singleData.getDateTime().toString().substring(5,7);
			Integer occurrences = monthTreeMap.get(month);
			
			monthTreeMap = putDataCountOnTreeMap(month, occurrences, monthTreeMap);
			
		}
		
		return monthTreeMap;
		
	}

	@GetMapping(value = "/byYear")
	public TreeMap<String, Integer> countByYear() {
		TreeMap<String, Integer> yearTreeMap = new TreeMap<>();
		
		List<Greeting> dataSet = greetingRepo.findAll();
		
		for (Greeting singleData : dataSet) {
			String year = singleData.getDateTime().toString().substring(0,4);
			System.out.println(singleData.getDateTime());
			Integer occurrences = yearTreeMap.get(year);
			
			yearTreeMap = putDataCountOnTreeMap(year, occurrences, yearTreeMap);
		}
		
		return yearTreeMap;
	}
	
	public TreeMap<String, Integer> putDataCountOnTreeMap(String dataValue,
			Integer occurrences,
			TreeMap<String, Integer> dataCount) {
		
		if (occurrences == null) {
			occurrences = 0;
			dataCount.put(dataValue, occurrences + 1);
		} else {
			dataCount.put(dataValue, occurrences + 1);
		}
		
		return dataCount;
	}
	
}

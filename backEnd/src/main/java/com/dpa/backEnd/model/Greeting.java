package com.dpa.backEnd.model;

import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

@Document(collection="greeting")
public class Greeting{
	
	public Greeting(String _id, String greeting, String name, LocalDateTime dateTime) {
		this._id = _id;
		this.greeting = greeting;
		this.name = name;
		this.dateTime = dateTime;
	}

	public Greeting() {}
	
	@Id
	private String _id;
	private String greeting = "Olá";
	private String name;
	@DateTimeFormat(iso = ISO.DATE)
	private LocalDateTime dateTime;
	
	public Greeting(String name) {
		this.name = name;
	}
	
	public String getGreeting() {
		return greeting;
	}
	
	public void setGreeting(String greeting) {
		this.greeting = greeting;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the dateTime
	 */
	public LocalDateTime getDateTime() {
		return dateTime;
	}

	/**
	 * @param dateTime the dateTime to set
	 */
	public void setDateTime(LocalDateTime dateTime) {
		this.dateTime = dateTime;
	}


	
}

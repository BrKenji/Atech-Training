package com.dpa.backEnd.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.dpa.backEnd.model.Greeting;

@Qualifier("greeting")
@Repository // maybe delete it afterward
public interface GreetingRepository extends MongoRepository<Greeting, String> {
	
	public List<Greeting> findByName(String name);
	

}
package com.revature.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.revature.models.Customer;

public class MockDB {
	public static List<Customer> customers = new ArrayList<Customer>(); 
	
	static {
			
		customers.add(new Customer(IdGeneratorImp.increament.generate(), "John", "Doe", "johndoe", "1234"));
		
		customers.add(new Customer(IdGeneratorImp.increament.generate(), "Vicky", "Ma", "vickyMa", "1234"));
		
		customers.add(new Customer(IdGeneratorImp.increament.generate(), "Elizibeth", "Lee", "elizibethlee", "1234"));
	}
}

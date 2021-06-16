package com.revature.bankingtests;

import static org.junit.Assert.*;

import org.junit.Test;

import com.revature.models.Person;

public class PersonTests {

	@Test
	public void getIdTest() {
		Person p = new Person();
		p.setId(1);
		assertEquals(1, p.getId().intValue());
	}
	@Test
	public void getFirstNameTest() {
		Person p = new Person();
		p.setFirstName("test");
		assertEquals("test", p.getFirstName());
	}
	
	@Test
	public void getLastNameTest() {
		Person p = new Person();
		p.setLastName("test");
		assertEquals("test", p.getLastName());
	}
	@Test
	public void getUserNameTest() {
		Person p = new Person();
		p.setUserName("test");
		assertEquals("test", p.getUserName());
	}
	
	@Test
	public void getPasswordTest() {
		Person p = new Person();
		p.setPassWord("test");
		assertEquals("test", p.getPassWord());
	}
	
	@Test
	public void getPersonTypeTest() {
		Person p = new Person();
		p.setPersonType("person");
		assertEquals("person", p.getPersonType());
	}
	
	@Test
	public void getStatusTest() {
		Person p = new Person();
		p.setStatus("pending");
		assertEquals("pending", p.getStatus());
	}
	

}

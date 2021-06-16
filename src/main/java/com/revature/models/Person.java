package com.revature.models;

import java.util.ArrayList;
import java.util.List;





public class Person {
	private Integer id;
	private String firstName;
	private String lastName;
	private String userName;
	private String passWord;
	private String personType;
	private String status;
	
	
	public Person() {
		
	}
	
	public Person(String personType) {
		this.firstName = "";
		this.lastName = "";
		
		this.userName = "";
		this.passWord = "";
		this.status = "pending";
		this.personType = personType;
	};
	public Person(String firstName, String lastName, String personType) {
		this.firstName = firstName;
		this.lastName = lastName;
		
		this.userName = "";
		this.passWord = "";

		this.status = "pending";
		this.personType = personType;
		
	}
	
	public Person(String firstName, String lastName, String username, String pass) {
		this.firstName = firstName;
		this.lastName = lastName;
		
		this.userName = username;
		this.passWord = pass;

		this.status = "pending";
		this.personType = personType;
		
	}
	
	public Person(String firstName, String lastName, String userName, String passWord, String personType){
		this.firstName = firstName;
		this.lastName = lastName;
		this.userName = userName;
		this.passWord = passWord;

		this.status = "pending";
		this.personType = personType;
	}
	
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getFirstName() {
		return this.firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassWord() {
		return passWord;
	}
	public void setPassWord(String passWord) {
		this.passWord = passWord;
	}

	public String getPersonType() {
		return personType;
	}
	public void setPersonType(String personType) {
		this.personType = personType;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	@Override
	public String toString() {
		return "Person [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", userName=" + userName
				+ ", passWord=" + passWord + ", personType=" + personType + ", status=" + status + "]";
	}
	
	
	
	
}

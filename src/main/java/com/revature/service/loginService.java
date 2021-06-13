package com.revature.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.revature.dao.AccountDao;
import com.revature.dao.PersonDao;
import com.revature.models.Account;
import com.revature.models.Person;

public class loginService {
	
	public static void userRegister(String first, String last, String username, String pass) {
		Person p = new Person(first, last, username, pass, "customer");
		PersonDao pDao = new PersonDao();
		pDao.create(p);
	}
	
	public static void registrationApproval(String username) {
		PersonDao pDao = new PersonDao();
		Person p = pDao.getByUserName(username);
		p.setStatus("active");
		pDao.update(p);
	}
	
	public static Person userLogin(String username, String pass) {
		PersonDao pDao = new PersonDao();
		Person p = pDao.getByUserName(username);
		
		if(p== null) {
			System.out.println("user does not exist");
			return null;
		}
		
		
		if(p.getPassWord().equals(pass)) {
			return p;
		}else {
			System.out.println("password mismatch");
			return null;
		}
			
	}
	
	public static void doOpenAccount(Person p, String accountType, Float amount) {
			Account a = new Account(accountType, amount, p.getId());
			AccountDao aDao = new AccountDao();
			aDao.create(a);
		}
	
//	public static void main(String[] args) {
//		loginService.userRegister("John", "Doe", "johndoe", "password");
		
//		System.out.println(userLogin("john", "password"));
//		loginService.registrationApproval("johndoe");
		
		
//		PersonDao pDao = new PersonDao();
//		Person p = new Person("john","doe", "johndoe","pass", "customer");
//		p.setId(1);
//		System.out.println(p);
//		loginService.doOpenAccount(p,"checking",new Float(1000.00));
//	}
}

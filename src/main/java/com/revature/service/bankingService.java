package com.revature.service;

import java.util.List;
import java.util.Optional;

import com.revature.dao.AccountDao;
import com.revature.models.Account;
import com.revature.models.Person;

public class bankingService {
	
	static boolean doAccountAccess(Person p) {
		if(p.getStatus().equals("active")) {
			return true;
		}
		return false;
	}
	
	public static boolean doDeposit(Account a, Float amount) {
		if(amount < 0) {
			return false;
		}
		AccountDao aDao = new AccountDao();
		a.setBalance(a.getBalance()+amount);
		aDao.update(a);
		return true;
		
	}
	
	public static boolean doWithdrawal(Account a, Float amount) {
		
		
		AccountDao aDao = new AccountDao();
		Float balance = a.getBalance();
		if(amount > balance) {
			return false;
		}
		balance -= amount;
		a.setBalance(balance);
		aDao.update(a);
		return true;
	}
	
	public static boolean doTransfer(Account a, Float amount, Account b) {
		AccountDao aDao = new AccountDao();
		Float balance = a.getBalance();
		if(amount > balance) {
			return false;
		}
			
		System.out.println("check b amount before: " + b);
		
		a.setBalance(balance- amount);
		b.setBalance(b.getBalance() + amount);
		System.out.println("check b amount after: " + b.getBalance());
		aDao.update(a);
		aDao.update(b);
		return true;
	
	}
}

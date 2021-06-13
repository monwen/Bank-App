package com.revature.models;

public class Account {
	private Integer id;
	private String accountType;
	private Float balance;
	private boolean approve;
	private Integer customer_id;
	
	public Account() {
		this.approve = false;
	};
	
	public Account(String accountType, Float balance, Integer customer_id) {
		this.accountType = accountType;
		this.balance = balance;
		this.approve = false;
		this.customer_id = customer_id;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getAccountType() {
		return accountType;
	}

	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}

	public Float getBalance() {
		return balance;
	}

	public void setBalance(Float balance) {
		this.balance = balance;
	}
	
	public Integer getCustomer_id() {
		return customer_id;
	}

	public void setCustomer_id(Integer customer_id) {
		this.customer_id = customer_id;
	}

	public boolean isApprove() {
		return approve;
	}

	public void setApprove(boolean approve) {
		this.approve = approve;
	}

	@Override
	public String toString() {
		return "Account [id=" + id + ", accountType=" + accountType + ", balance=" + balance + "]";
	}
	

	
}

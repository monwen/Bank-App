package com.revature.models;

import java.sql.Timestamp;

public class Transaction {
	private Integer id;
	private Integer from;
	private Integer to;
	private String methodType;
	private Float amount;
	private boolean approve;
	private Timestamp time;
	
	public Transaction() {
		
	}
	
	public Transaction( Integer from, Integer to, String methodType, Float amount,  Timestamp time) {
	
		this.from = from;
		this.to = to;
		this.methodType = methodType;
		this.amount = amount;
		this.approve = false;
		this.time = time;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getFrom() {
		return from;
	}

	public void setFrom(Integer from) {
		this.from = from;
	}

	public Integer getTo() {
		return to;
	}

	public void setTo(Integer to) {
		this.to = to;
	}

	public String getMethodType() {
		return methodType;
	}

	public void setMethodType(String methodType) {
		this.methodType = methodType;
	}
	
	public Float getAmount() {
		return amount;
	}

	public void setAmount(Float amount) {
		this.amount = amount;
	}
	
	
	public Timestamp getTime() {
		return time;
	}

	public void setTime(Timestamp time) {
		this.time = time;
	}
	
	public boolean isApprove() {
		return approve;
	}

	public void setApprove(boolean approve) {
		this.approve = approve;
	}

	@Override
	public String toString() {
		return "Transaction [id=" + id + ", from=" + from + ", to=" + to + ", amount=" + amount + ", time=" + time
				+ "]";
	}
	
}

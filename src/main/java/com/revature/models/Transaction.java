package com.revature.models;

import java.sql.Timestamp;

public class Transaction {
	private long id;
	private String type;
	private Double amount;
	private Account from;
	private Account to;
	private Timestamp time;
}

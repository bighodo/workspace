package com.example.application.domain;

import java.math.BigDecimal;

public class Account {
	private long id;
	private BigDecimal balance;
	private String log;
	
	public Account() {
		this.balance = BigDecimal.ZERO;
	}
	
	public boolean withdraw(BigDecimal amount) {
		if (balance.compareTo(amount) < 0) {
			return false;
		}
		balance = balance.subtract(amount);
		return true;
	}
	
	public void deposit(BigDecimal amount) {
		balance = balance.add(amount);
	}
	
	public String toString() {
		return "Account : " + this.getId() + "  Balance : " + this.getBalance();
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public BigDecimal getBalance() {
		return balance;
	}

	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}

	public String getLog() {
		return log;
	}

	public void setLog(String log) {
		this.log = log;
	}
	
	
}

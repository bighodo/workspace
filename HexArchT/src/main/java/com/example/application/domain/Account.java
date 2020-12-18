package com.example.application.domain;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Account {
	@Id @GeneratedValue
	private Long id;
	private BigDecimal balance;
	
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

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public BigDecimal getBalance() {
		return balance;
	}

	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}
}

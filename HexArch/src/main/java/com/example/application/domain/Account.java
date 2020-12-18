package com.example.application.domain;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Account {
	@Id @Getter
	private long id;
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
}

package com.example.application.usecase;

import java.math.BigDecimal;

public interface DepositUseCase {
	public void deposit(Long id, BigDecimal amount);
}

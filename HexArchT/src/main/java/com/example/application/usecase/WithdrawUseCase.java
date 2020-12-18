package com.example.application.usecase;

import java.math.BigDecimal;

public interface WithdrawUseCase {
	public boolean withdraw(Long id, BigDecimal amount);
}

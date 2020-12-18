package com.example.application.usecase;

import java.math.BigDecimal;

public interface WithdrawUseCase {
	public String withdraw(Long id, BigDecimal amount);
}

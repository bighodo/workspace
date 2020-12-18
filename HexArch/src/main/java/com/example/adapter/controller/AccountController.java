package com.example.adapter.controller;

import java.math.BigDecimal;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.application.usecase.DepositUseCase;
import com.example.application.usecase.WithdrawUseCase;

@RestController
@RequestMapping("/account")
public class AccountController {
	private DepositUseCase depositUseCase;
	private WithdrawUseCase withdrawUseCase;
	
    public AccountController(DepositUseCase depositUseCase, WithdrawUseCase withdrawUseCase) {
        this.depositUseCase = depositUseCase;
        this.withdrawUseCase = withdrawUseCase;
    }
	
	@PostMapping(value = "/{id}/deposit/{amount}")
	void deposit(@PathVariable final Long id, @PathVariable final BigDecimal amount) {
		depositUseCase.deposit(id, amount);
	}
	
	@PostMapping(value = "/{id}/withdraw/{amount}")
	void withdraw(@PathVariable final Long id, @PathVariable final BigDecimal amount) {
		withdrawUseCase.withdraw(id, amount);
	}
	
	@PostMapping(value = "/{id}")
	Long test(@PathVariable final Long id) {
		return id;
	}
	
	@PostMapping("/2")
	String test2() {
		return "beta";
	}
}

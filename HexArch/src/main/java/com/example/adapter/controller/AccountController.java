package com.example.adapter.controller;

import java.math.BigDecimal;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.adapter.exception.cannotFindAccountException;
import com.example.application.usecase.CheckBalanceUseCase;
import com.example.application.usecase.DepositUseCase;
import com.example.application.usecase.MakeAccountUseCase;
import com.example.application.usecase.WithdrawUseCase;

@RestController
@RequestMapping("/account")
public class AccountController{
	private DepositUseCase depositUseCase;
	private WithdrawUseCase withdrawUseCase;
	private MakeAccountUseCase makeAccountUseCase;
	private CheckBalanceUseCase checkBalanceUseCase;
	
    public AccountController(DepositUseCase depositUseCase, WithdrawUseCase withdrawUseCase, MakeAccountUseCase makeAccountUseCase, CheckBalanceUseCase checkBalanceUseCase) {
        this.depositUseCase = depositUseCase;
        this.withdrawUseCase = withdrawUseCase;
        this.makeAccountUseCase = makeAccountUseCase;
        this.checkBalanceUseCase = checkBalanceUseCase;
    }
	
	@PostMapping(value = "/{id}/deposit/{amount}")
	String deposit(@PathVariable final Long id, @PathVariable final BigDecimal amount) {
		return depositUseCase.deposit(id, amount);
	}
	
	@PostMapping(value = "/{id}/withdraw/{amount}")
	String withdraw(@PathVariable final Long id, @PathVariable final BigDecimal amount) {
		return withdrawUseCase.withdraw(id, amount);
	}
	
	@PostMapping(value ="/{id}/check")
	String checkBalance(@PathVariable final Long id) {
		return checkBalanceUseCase.checkBalance(id);
	}
	
	@PostMapping(value = "/makeAccount")
	String createAccount() {
		return makeAccountUseCase.makeAccount();
	}
	
	@ExceptionHandler(cannotFindAccountException.class)
	public String sampleHandler() {
		return "그딴거 안보임";
	}
	
}

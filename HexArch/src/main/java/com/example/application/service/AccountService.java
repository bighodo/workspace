package com.example.application.service;

import java.math.BigDecimal;
import java.util.NoSuchElementException;
import java.util.Optional;

import com.example.adapter.repository.AccountRepository;
import com.example.application.domain.Account;
import com.example.application.usecase.CheckBalanceUseCase;
import com.example.application.usecase.CreateAccountPort;
import com.example.application.usecase.DepositUseCase;
import com.example.application.usecase.LoadAccountPort;
import com.example.application.usecase.MakeAccountUseCase;
import com.example.application.usecase.SaveAccountPort;
import com.example.application.usecase.WithdrawUseCase;

public class AccountService implements DepositUseCase, WithdrawUseCase, MakeAccountUseCase, CheckBalanceUseCase{

	private LoadAccountPort loadAccountPort;
	private SaveAccountPort saveAccountPort;
	private CreateAccountPort createAccountPort;
	
	public AccountService(LoadAccountPort loadAccountPort, SaveAccountPort saveAccountPort, CreateAccountPort createAccountPort) {
		this.loadAccountPort = loadAccountPort;
		this.saveAccountPort = saveAccountPort;
		this.createAccountPort = createAccountPort;
	}

	@Override
	public String withdraw(Long id, BigDecimal amount) {
		Account account = loadAccountPort.load(id);
		if (account.withdraw(amount)) {
			saveAccountPort.save(account);
			return account.toString();
		}
		return "Amount exceed balance";
	}

	@Override
	public String deposit(Long id, BigDecimal amount) {
		Account account = loadAccountPort.load(id);
		account.deposit(amount);
		saveAccountPort.save(account);
		
		return account.toString();
	}

	@Override
	public String makeAccount() {
		Account account = createAccountPort.createAccount();
		return account.toString();
	}

	@Override
	public String checkBalance(Long id) {
		Account account = loadAccountPort.load(id);
		return account.toString();
	}
	
}

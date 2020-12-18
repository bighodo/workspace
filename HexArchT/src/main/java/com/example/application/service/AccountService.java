package com.example.application.service;

import java.math.BigDecimal;
import java.util.NoSuchElementException;
import java.util.Optional;

import com.example.adapter.repository.AccountRepository;
import com.example.application.domain.Account;
import com.example.application.usecase.DepositUseCase;
import com.example.application.usecase.LoadAccountPort;
import com.example.application.usecase.SaveAccountPort;
import com.example.application.usecase.TestPort;
import com.example.application.usecase.WithdrawUseCase;

public class AccountService implements DepositUseCase, WithdrawUseCase{

	private LoadAccountPort loadAccountUseCase;
	private SaveAccountPort saveAccountUseCase;
	private TestPort testUseCase;
	
	public AccountService(LoadAccountPort loadAccountUseCase, SaveAccountPort saveAccountUseCase, TestPort testUseCase) {
		this.loadAccountUseCase = loadAccountUseCase;
		this.saveAccountUseCase = saveAccountUseCase;
		this.testUseCase = testUseCase;
		System.out.println("테스트시작");
		System.out.println(testUseCase.getList());
	}

	@Override
	public boolean withdraw(Long id, BigDecimal amount) {
		Account account = loadAccountUseCase.load(id)
				.orElseThrow(NoSuchElementException::new);
		if (account.withdraw(amount)) {
			saveAccountUseCase.save(account);
			return true;
		}
		return false;
	}

	@Override
	public void deposit(Long id, BigDecimal amount) {
		Account account = loadAccountUseCase.load(id)
				.orElseThrow(NoSuchElementException::new);
		account.deposit(amount);
		saveAccountUseCase.save(account);
	}
	
}

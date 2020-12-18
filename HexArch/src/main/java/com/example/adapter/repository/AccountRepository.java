package com.example.adapter.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.application.domain.Account;
import com.example.application.usecase.LoadAccountPort;
import com.example.application.usecase.SaveAccountPort;
import com.example.application.usecase.CreateAccountPort;

@Component
public class AccountRepository implements LoadAccountPort, SaveAccountPort, CreateAccountPort{
	
	@Autowired
	private CRUDAccountRepository repository;
	
	@Override
	public Optional<Account> load(Long id) {
		return repository.findById(id);
	}
	
	@Override
	public void save(Account account) {
		repository.save(account);
	}
	
	@Override
	public Account createAccount() {
		System.out.println("여기까지했음");
		Account account = new Account();
		repository.save(account);
		return account;
	}
}

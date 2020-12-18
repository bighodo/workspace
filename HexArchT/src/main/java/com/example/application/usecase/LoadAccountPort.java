package com.example.application.usecase;

import java.util.Optional;

import com.example.application.domain.Account;

public interface LoadAccountPort {
	Optional<Account> load(Long id); 
}

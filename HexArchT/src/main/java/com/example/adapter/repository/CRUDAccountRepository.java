package com.example.adapter.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.example.application.domain.Account;

public interface CRUDAccountRepository extends CrudRepository<Account, Long> {
	public Optional<Account> findById(Long id);
}

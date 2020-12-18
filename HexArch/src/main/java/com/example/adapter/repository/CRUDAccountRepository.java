package com.example.adapter.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.application.domain.Account;

public interface CRUDAccountRepository extends JpaRepository<Account, Long> {
}

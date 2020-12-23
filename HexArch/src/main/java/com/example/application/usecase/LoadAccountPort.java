package com.example.application.usecase;


import com.example.application.domain.Account;

public interface LoadAccountPort {
	Account load(Long id); 
}

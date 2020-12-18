package com.example.adapter.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import com.example.HexArchApplication;
import com.example.adapter.repository.AccountRepository;
import com.example.application.service.AccountService;

@Configuration
@ComponentScan(basePackageClasses = HexArchApplication.class)
public class BeanConfiguration {
	@Bean
	AccountService accountService(AccountRepository repository) {
		return new AccountService(repository, repository, repository);
	}
}

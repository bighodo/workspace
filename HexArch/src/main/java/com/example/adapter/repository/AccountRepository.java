package com.example.adapter.repository;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Component;

import com.example.adapter.exception.cannotFindAccountException;
import com.example.application.domain.Account;
import com.example.application.usecase.LoadAccountPort;
import com.example.application.usecase.SaveAccountPort;
import com.example.application.usecase.CreateAccountPort;

@Component
public class AccountRepository implements LoadAccountPort, SaveAccountPort, CreateAccountPort{
	
	private JdbcTemplate jdbcTemplate;
	
	public AccountRepository(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	

	@Override
	public Account createAccount() {
		
		SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
		jdbcInsert.withTableName("ACCOUNT").usingGeneratedKeyColumns("ID");
		Map<String, BigDecimal> params = new HashMap<>();
		params.put("BALANCE", BigDecimal.ZERO);
		
		Long id = (Long) jdbcInsert.executeAndReturnKey(new MapSqlParameterSource(params));
		
		return load(id);
	}

	@Override
	public void save(Account account) {
		jdbcTemplate.update("UPDATE ACCOUNT SET BALANCE=? WHERE ID=?",account.getBalance(), account.getId());
	}

	@Override
	public Account load(Long id) {
		Account account = null;
		try {
			account = jdbcTemplate.queryForObject(String.format("SELECT ID,BALANCE FROM ACCOUNT WHERE ID = '%s';", id), rowMapper);
		} catch (DataAccessException e) {
			throw new cannotFindAccountException();
		}
		
		return account;
	}
	

	private RowMapper<Account> rowMapper = (ResultSet rs, int rowNum) -> {
		Account account = new Account();
		account.setId(rs.getLong("ID"));
		account.setBalance(rs.getBigDecimal("BALANCE"));
		return account;
	};
}

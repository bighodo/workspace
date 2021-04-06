package com.example.entity;

import java.util.UUID;

import javax.annotation.Generated;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

public class User {
	private String id;
	private String username;
	private String userId;
	private String password;
	private String role;
	
	public User() {
		super();
		this.id = UUID.randomUUID().toString();
	}
	
	public User(String id) {
		super();
		this.id = id;
	}
	
	public User(String username, String userId, String password) {
		super();
		this.id = UUID.randomUUID().toString();
		this.username = username;
		this.userId = userId;
		this.password = password;
		this.role = "ROLE_USER";
	}
	
	public User(String id, String username, String userId, String role) {
		super();
		this.id = id;
		this.username = username;
		this.userId = userId;
		this.role = role;
	}

	public User(String id, String username, String userId, String password, String role) {
		super();
		this.id = id;
		this.username = username;
		this.userId = userId;
		this.password = password;
		this.role = role;
	}

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}
	
	

}

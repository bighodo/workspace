package com.example.entity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Entity
@Table(name="USERS")
public class UserDto {
	static PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
	
	@Id
	private String id;
	private String userId;
	private String username;
	private String password;
	private String role; //USER, ADMIN
	

	public UserDto() {
		this.role = "ROLE_USER";
	}
	
	public UserDto(String username, String userId, String role) {
		super();
		this.username = username;
		this.userId = userId;
		this.role = role;
	}

	public void parse(User user) {
		id = user.getId();
		userId = user.getUserId();
		username = user.getUsername();
		password = user.getPassword();
		role = user.getRole();
	}
	
	public User getUser() {
		User user = new User(id, userId, username, role);
		return user;
	}
	
	public User getUserWithPassword() {
		User user = new User(id, userId, username, password, role);
		return user;
	}
	
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
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

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	public Collection<? extends GrantedAuthority> readAuthorities() {
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		authorities.add(new SimpleGrantedAuthority(this.role));
		return authorities;
	}
	
	public User toUser() {
		User user = new User(this.username, this.userId, passwordEncoder.encode(this.password));
		return user;
	}
}

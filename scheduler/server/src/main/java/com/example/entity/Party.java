package com.example.entity;

import java.util.ArrayList;
import java.util.UUID;

public class Party {
	private String id;
	private String name;
	private int userCount;
	private ArrayList<User> users;
	
	public Party() {
		super();
		this.id = UUID.randomUUID().toString();
		users = new ArrayList<User>();
	}
	
	public Party(String name) {
		super();
		this.id = UUID.randomUUID().toString();
		this.name = name;
		users = new ArrayList<User>();
	}
	
	public Party(String id, String name) {
		super();
		this.id = id;
		this.name = name;
		users = new ArrayList<User>();
	}
	
	public void addUser(User user) {
		users.add(user);
	}
	
	public void removeUser(User user) {
		for (int i = 0; i < userCount; i++) {
			if (users.get(i).getId().equals(user.getId()))
				users.remove(i);
		}
	}
	

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public ArrayList<User> getUsers() {
		return users;
	}

	public void setUsers(ArrayList<User> users) {
		this.users = users;
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getUserCount() {
		return userCount;
	}
	public void setUserCount(int userCount) {
		this.userCount = userCount;
	}
	
}

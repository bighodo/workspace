package com.example.entity;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="PARTY")
public class PartyDto {
	@Id
	private String id;
	private String name;
	private int userCount;
	private String user1, user2, user3, user4, user5, user6;
	
	public PartyDto() {
		super();
	}
	
	public void parse(Party party) {
		id = party.getId();
		name = party.getName();
		List<User> users = party.getUsers();
		userCount = users.size();
		for (int i = 0; i < userCount; i++) {
			syncUser(i, users.get(i).getId());
		}
	}
	
	private void syncUser(int index, String id) {
		switch (index) {
		case 0:
			user1 = id;
			break;
		case 1:
			user2 = id;
			break;
		case 2:
			user3 = id;
			break;
		case 3:
			user4 = id;
			break;
		case 4:
			user5 = id;
			break;
		case 5:
			user6 = id;
			break;
		}
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
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
	public String getUser1() {
		return user1;
	}
	public void setUser1(String user1) {
		this.user1 = user1;
	}
	public String getUser2() {
		return user2;
	}
	public void setUser2(String user2) {
		this.user2 = user2;
	}
	public String getUser3() {
		return user3;
	}
	public void setUser3(String user3) {
		this.user3 = user3;
	}
	public String getUser4() {
		return user4;
	}
	public void setUser4(String user4) {
		this.user4 = user4;
	}
	public String getUser5() {
		return user5;
	}
	public void setUser5(String user5) {
		this.user5 = user5;
	}
	public String getUser6() {
		return user6;
	}
	public void setUser6(String user6) {
		this.user6 = user6;
	}
	
	
}

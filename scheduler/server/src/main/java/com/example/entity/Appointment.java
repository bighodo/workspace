package com.example.entity;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;



public class Appointment {
	
	private String id;
	private Date startDate;
	private Date endDate;
	private String title;
	private String notes;
	private User user;
	
	public Appointment() {
		super();
		this.id = UUID.randomUUID().toString();
		this.title = "";
		this.notes = "";
		
		this.startDate = new Date();
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		cal.add(Calendar.MINUTE, 30);
		this.endDate = cal.getTime();
	}
	
	public Appointment(String title) {
		super();
		this.id = UUID.randomUUID().toString();
		this.title = title;
		this.notes = "";
		
		this.startDate = new Date();
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		cal.add(Calendar.MINUTE, 30);
		this.endDate = cal.getTime();
	}
	
	public Appointment(String title, String notes) {
		super();
		this.id = UUID.randomUUID().toString();
		this.title = title;
		this.notes = notes;
		
		this.startDate = new Date();
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		cal.add(Calendar.MINUTE, 30);
		this.endDate = cal.getTime();
	}

	
	public Appointment(String id, Date start, Date end, String title, String notes) {
		super();
		this.id = id;
		this.startDate = start;
		this.endDate = end;
		this.title = title;
		this.notes = notes;
	}

	public Appointment(Date start, Date end, String title, String notes, User user) {
		super();
		this.id = UUID.randomUUID().toString();
		this.startDate = start;
		this.endDate = end;
		this.title = title;
		this.notes = notes;
		this.user = user;
	}

	public Appointment(String id, Date start, Date end, String title, String notes, User user) {
		super();
		this.id = id;
		this.startDate = start;
		this.endDate = end;
		this.title = title;
		this.notes = notes;
		this.user = user;
	}

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date start) {
		this.startDate = start;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date end) {
		this.endDate = end;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	
}

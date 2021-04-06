package com.example.entity;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;



public class Appointment {
	static String date2String(Date date) {
		return date.getYear()+"-"
			+String.format("%02d",date.getMonth())
			+"-"
			+String.format("%02d",date.getDate())
			+"T"
			+String.format("%02d",date.getHours())
			+":"
			+String.format("%02d",date.getMinutes());
	}
	
	private String id;
	private Date start;
	private Date end;
	private String title;
	private String notes;
	private User user;
	private String startDate;
	private String endDate;
	
	public Appointment() {
		super();
		this.id = UUID.randomUUID().toString();
		this.title = "";
		this.notes = "";
		
		this.setStart(new Date());
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		cal.add(Calendar.MINUTE, 30);
		this.setEnd(cal.getTime());
	}
	
	public Appointment(String title) {
		super();
		this.id = UUID.randomUUID().toString();
		this.title = title;
		this.notes = "";
		
		this.setStart(new Date());
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		cal.add(Calendar.MINUTE, 30);
		this.setEnd(cal.getTime());
	}
	
	public Appointment(String title, String notes) {
		super();
		this.id = UUID.randomUUID().toString();
		this.title = title;
		this.notes = notes;
		
		this.setStart(new Date());
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		cal.add(Calendar.MINUTE, 30);
		this.setEnd(cal.getTime());
	}

	
	public Appointment(String id, Date start, Date end, String title, String notes) {
		super();
		this.id = id;
		this.setStart(start);
		this.setEnd(end);
		this.title = title;
		this.notes = notes;
	}

	public Appointment(Date start, Date end, String title, String notes, User user) {
		super();
		this.id = UUID.randomUUID().toString();
		this.setStart(start);
		this.setEnd(end);
		this.title = title;
		this.notes = notes;
		this.user = user;
	}

	public Appointment(String id, Date start, Date end, String title, String notes, User user) {
		super();
		this.id = id;
		this.setStart(start);
		this.setEnd(end);
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
	public Date getStart() {
		return start;
	}
	public void setStart(Date start) {
		this.start = start;
		this.startDate = Appointment.date2String(this.start);
	}
	public Date getEnd() {
		return end;
	}
	public void setEnd(Date end) {
		this.end = end;
		this.endDate = Appointment.date2String(this.end);
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

	public String getStartDate() {
		return startDate;
	}

	public String getEndDate() {
		return endDate;
	}
	
	
}

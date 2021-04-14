package com.example.entity;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name="APPOINTMENT")
public class AppointmentDto {
	
	@Id
	private String id;
	private Date startDate;
	private Date endDate;
	private String title;
	private String notes;
	private String user;
	
	

	
	public AppointmentDto() {
		super();
		this.id = UUID.randomUUID().toString();
	}
	
	public AppointmentDto(String title) {
		super();
		this.id = UUID.randomUUID().toString();
		this.title = title;
	}
	
	public AppointmentDto(String title, String notes) {
		super();
		this.id = UUID.randomUUID().toString();
		this.title = title;
		this.notes = notes;
	}
	
	public void parse(Appointment appoint) {
		id = appoint.getId();
		startDate = appoint.getStartDate();
		endDate = appoint.getEndDate();
		title = appoint.getTitle();
		notes = appoint.getNotes();
		user = appoint.getUser().getId();
	}
	

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
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

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public Date getStartDate() {
		return startDate;
	}
	
	public void setStart(Date start) {
		this.startDate = start;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEnd(Date end) {
		this.endDate = end;
	}
	
	
	
	
}

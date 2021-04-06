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

	@Id
	private String id;
	private Date start;
	private Date end;
	private String title;
	private String notes;
	private String user;
	
	@Transient
	private String startDate;
	@Transient
	private String endDate;
	

	
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
		start = appoint.getStart();
		end = appoint.getEnd();
		title = appoint.getTitle();
		notes = appoint.getNotes();
		user = appoint.getUser().getId();
		startDate = AppointmentDto.date2String(start);
		endDate = AppointmentDto.date2String(end);
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
		startDate = AppointmentDto.date2String(this.start);
	}
	public Date getEnd() {
		return end;
	}
	public void setEnd(Date end) {
		this.end = end;
		endDate = AppointmentDto.date2String(this.end);
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

	public String getStartDate() {
		return startDate;
	}

	public String getEndDate() {
		return endDate;
	}
	
	
	
	
}

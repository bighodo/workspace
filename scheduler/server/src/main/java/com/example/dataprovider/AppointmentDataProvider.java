package com.example.dataprovider;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.entity.Appointment;
import com.example.entity.AppointmentDto;
import com.example.repository.AppointmentRepository;


@Component
public class AppointmentDataProvider {
	static final int FIRST_DAY_OF_WEEK = 4; // 4 is thursday
	
	@Autowired
	AppointmentRepository repo;
	@Autowired
	CommonDataProvider commonDataProvider;
	
	public Appointment getAppointmentById(String appointId) {
		Optional<AppointmentDto> appointDto = repo.findById(appointId);
		if (appointDto.isPresent()) {
			return commonDataProvider.convert2Entity(appointDto.get());
		}
		return null;
	}
	
	public List<Appointment> getAllAppointment() {
		List<AppointmentDto> appointDtos = repo.findAll();
		List<Appointment> appoints = new ArrayList<Appointment>();
		for (int i = 0; i < appointDtos.size(); i++) {
			appoints.add(commonDataProvider.convert2Entity(appointDtos.get(i)));
		}
		return appoints;
	}
	
	public List<Appointment> getAllAppointmentByUserId(String userId) {
		Date range[] = getRangeOfThisWeek();
		List<AppointmentDto> appointDtos = repo.findAllByUserIdWithRange(userId,range[0],range[1]);
		List<Appointment> appoints = new ArrayList<Appointment>();
		for (int i = 0; i < appointDtos.size(); i++) {
			appoints.add(commonDataProvider.convert2Entity(appointDtos.get(i)));
		}
		return appoints;
	}
	
	public Appointment createAppointment(Appointment appointment) {
		List<AppointmentDto> appointDtos = repo.findAllByUserIdWithRange(appointment.getUser().getId(),appointment.getStartDate(),appointment.getEndDate());
		if (appointDtos.size() > 0) return null;
		AppointmentDto appointDto = new AppointmentDto();
		appointDto.parse(appointment);
		repo.save(appointDto);
		return appointment;
	}
	
	public Appointment updateAppointment(Appointment appoint) {
		List<AppointmentDto> appointDtos = repo.findAllByUserIdWithRange(appoint.getUser().getId(),appoint.getStartDate(),appoint.getEndDate());
		if (appointDtos.size() > 0) return null;
		AppointmentDto appointDto = new AppointmentDto();
		appointDto.parse(appoint);
		repo.save(appointDto);
		return appoint;
	}
	
	private Date[] getRangeOfThisWeek() {
		Date today = new Date();
		int day = today.getDay() - FIRST_DAY_OF_WEEK;
		if (day < 0) day += 7;
		
		Calendar cal = Calendar.getInstance();
		cal.setTime(today);
		cal.add(Calendar.DATE, day * -1);
		Date first = cal.getTime();
		first.setHours(0);
		first.setMinutes(0);
		first.setSeconds(0);
		
		cal.setTime(first);
		cal.add(Calendar.DATE, 6);
		Date end = cal.getTime();
	
		Date result[] = {first, end};
		return result;
	}
	
}

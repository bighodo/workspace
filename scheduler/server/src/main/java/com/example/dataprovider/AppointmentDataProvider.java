package com.example.dataprovider;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.entity.Appointment;
import com.example.entity.AppointmentDto;
import com.example.repository.AppointmentRepository;


@Component
public class AppointmentDataProvider {
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
		List<AppointmentDto> appointDtos = repo.findAllByUserId(userId);
		List<Appointment> appoints = new ArrayList<Appointment>();
		for (int i = 0; i < appointDtos.size(); i++) {
			appoints.add(commonDataProvider.convert2Entity(appointDtos.get(i)));
		}
		return appoints;
	}
	
	public Appointment createAppointment(Appointment appointment) {
		AppointmentDto appointDto = new AppointmentDto();
		appointDto.parse(appointment);
		repo.save(appointDto);
		return appointment;
	}
	
	public Appointment updateAppointment(Appointment appoint) {
		AppointmentDto appointDto = new AppointmentDto();
		appointDto.parse(appoint);
		repo.save(appointDto);
		return appoint;
	}
	
}

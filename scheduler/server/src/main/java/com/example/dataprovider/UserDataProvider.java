package com.example.dataprovider;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.entity.User;
import com.example.entity.UserDto;
import com.example.repository.UserRepository;


@Component
public class UserDataProvider {
	@Autowired
	private UserRepository repo;
	@Autowired
	private AppointmentDataProvider appointmentDataProvider;
	
	public User getUserById(String id) {
		Optional<UserDto> userDto = repo.findById(id);
		if(userDto.isPresent()) {
			return userDto.get().getUser();
		}
		return null;
	}

	public UserDto getUserByIdWithAppointments(String id) {
		Optional<UserDto> userDto = repo.findById(id);
		if(userDto.isPresent()) {
			UserDto user = userDto.get();
			user.addAppointemnts(appointmentDataProvider.getAllAppointmentByUserId(id));
			return user;
		}
		return null;
	}
	
	public User getUserByUserId(String userId) {
		Optional<UserDto> userDto = repo.findByUserId(userId);
		if (userDto.isPresent()) {
			return userDto.get().getUser();
		}
		return null;
	}
	
	public User getUserByUserIdWithPassword(String userId) {
		Optional<UserDto> userDto = repo.findByUserId(userId);
		if (userDto.isPresent()) {
			return userDto.get().getUserWithPassword();
		}
		return null;
	}
	
	public List<User> getAll() {
		List<UserDto> userDtos= repo.findAll();
		List<User> users = new ArrayList<User>();
		for (int i = 0; i < userDtos.size(); i++) {
			users.add(userDtos.get(i).getUser());
		}
		return users;
	}

	public List<UserDto> getAllWithAppointments() {
		List<UserDto> userDtos= repo.findAll();
		for (int i = 0; i < userDtos.size(); i++) {
			UserDto userDto = userDtos.get(i);
			userDto.addAppointemnts(appointmentDataProvider.getAllAppointmentByUserId(userDto.getId()));
		}
		return userDtos;
	}
	
	public int createUser(User user) {
		Optional<UserDto> found = repo.findByUserId(user.getUserId());
		if (found.isPresent()) return -1;
		UserDto userDto = new UserDto();
		userDto.parse(user);
		repo.save(userDto);
		return 1;
	}

}

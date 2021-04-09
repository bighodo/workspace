package com.example.dataprovider;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.entity.Appointment;
import com.example.entity.AppointmentDto;
import com.example.entity.Party;
import com.example.entity.PartyDto;
import com.example.entity.User;
import com.example.entity.UserDto;

@Component
public class CommonDataProvider {
	@Autowired
	UserDataProvider userDataProvider;
	
	
	public Party convert2Entity(PartyDto partyDto) {
		Party party = new Party(partyDto.getId(), partyDto.getName());
		if (partyDto.getUser1() != null)
			party.addUser(userDataProvider.getUserById(partyDto.getUser1()));
		if (partyDto.getUser2() != null)
			party.addUser(userDataProvider.getUserById(partyDto.getUser2()));
		if (partyDto.getUser3() != null)
			party.addUser(userDataProvider.getUserById(partyDto.getUser3()));
		if (partyDto.getUser4() != null)
			party.addUser(userDataProvider.getUserById(partyDto.getUser4()));
		if (partyDto.getUser5() != null)
			party.addUser(userDataProvider.getUserById(partyDto.getUser5()));
		if (partyDto.getUser6() != null)
			party.addUser(userDataProvider.getUserById(partyDto.getUser6()));
		return party;
	}

	public Appointment convert2Entity(AppointmentDto appointDto) {
		Appointment appoint = new Appointment(appointDto.getId(), appointDto.getStart(), appointDto.getEnd(),
				appointDto.getTitle(), appointDto.getNotes());
		appoint.setUser(userDataProvider.getUserById(appointDto.getUser()));
		return appoint;
	}
}
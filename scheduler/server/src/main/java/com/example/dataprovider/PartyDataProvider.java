package com.example.dataprovider;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.entity.Party;
import com.example.entity.PartyDto;
import com.example.repository.PartyRepository;

@Component
public class PartyDataProvider {
	@Autowired
	PartyRepository repo;
	@Autowired
	CommonDataProvider commonDataProvider;
	
	public Party getPartyById(String partyId) {
		Optional<PartyDto> partyDto = repo.findById(partyId);
		if (partyDto.isPresent()) {
			return commonDataProvider.convert2Entity(partyDto.get());
		}
		return null;
	}
	
	public void saveParty(Party party) {
		PartyDto partyDto = new PartyDto();
		partyDto.parse(party);
		repo.save(partyDto);
	}
}

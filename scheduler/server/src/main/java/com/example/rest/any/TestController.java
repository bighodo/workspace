package com.example.rest.any;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.dataprovider.PartyDataProvider;
import com.example.dataprovider.UserDataProvider;
import com.example.entity.Party;
import com.example.entity.User;


@RestController
@RequestMapping(value = "/api/test")
public class TestController {

	@Autowired
	private UserDataProvider userDataProvider;
	@Autowired
	private PartyDataProvider partyDataProvider;
	
	@GetMapping("/test1")
	public void makeParty() {
		Party party = new Party("testParty");
		List<User> users = userDataProvider.getAll();
		for (int i = 0; i < users.size() && i < 6; i++) {
			party.addUser(users.get(i));
		}
		partyDataProvider.saveParty(party);
	}
}

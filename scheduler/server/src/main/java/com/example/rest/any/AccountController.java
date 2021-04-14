package com.example.rest.any;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.dataprovider.JwtDataProvider;
import com.example.dataprovider.UserDataProvider;
import com.example.entity.User;
import com.example.entity.UserDto;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;



@RestController
//@RequestMapping(value = "api/every/account", produces = "application/json; charset=utf8")
@RequestMapping(value = "/api/account")
public class AccountController {
	@Autowired
	private UserDataProvider userDataProvider;
	@Autowired
	private JwtDataProvider jwtDataProivder;
	@Autowired
	PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
	
	ObjectMapper mapper = new ObjectMapper();
	
	
	@PostMapping("/login")
	public JsonNode login(@RequestBody UserDto userDto, HttpServletResponse response) {
		JsonNode jsonNodeMap = null;
		Map<String, Object> resData = new HashMap<String, Object>(); 
		String message;
		
		User user = userDataProvider.getUserByUserIdWithPassword(userDto.getUserId());
		String some = passwordEncoder.encode("1234");
		String none = passwordEncoder.encode(userDto.getPassword());
		if (user != null && passwordEncoder.matches(userDto.getPassword(), user.getPassword())) {

			String token = jwtDataProivder.generateToken(user);
			System.out.println(token);
			Cookie cookie = new Cookie("access-token", token);
			cookie.setPath("/");
			response.addCookie(cookie);
			
			resData.put("result", 1);
			message = "Login Success";
		} else {
			resData.put("result", 0);
			message = "Login failed";
		}
		
		resData.put("message",message);
		jsonNodeMap = mapper.convertValue(resData, JsonNode.class);
		return jsonNodeMap;
	}
	
	@PostMapping("/register")
	public JsonNode register(@RequestBody UserDto userDto) {
		JsonNode jsonNodeMap = null;
		Map<String, Object> resData = new HashMap<String, Object>(); 
		String message = "register failed";
		User user = userDto.toUser();
		switch(userDataProvider.createUser(user)) {
		case 1:
			message = "User id : " + user.getUserId() + " Register Success";
			break;
		case -1:
			message = "ID is duplicated";
			break;
		};
		
		resData.put("message",message);
		jsonNodeMap = mapper.convertValue(resData, JsonNode.class);
		return jsonNodeMap;
	}
}

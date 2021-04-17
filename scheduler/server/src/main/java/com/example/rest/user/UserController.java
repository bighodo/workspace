package com.example.rest.user;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.WebUtils;

import com.example.dataprovider.AppointmentDataProvider;
import com.example.dataprovider.CommonDataProvider;
import com.example.dataprovider.JwtDataProvider;
import com.example.dataprovider.UserDataProvider;
import com.example.entity.Appointment;
import com.example.entity.User;
import com.example.entity.UserDto;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@RequestMapping(value = "/api/user/account", produces = "application/json; charset=utf8")
public class UserController {
	@Autowired
	private UserDataProvider userDataProvider;
	@Autowired
	private JwtDataProvider jwtDataProivder;
	@Autowired
	private CommonDataProvider commonDataProvider;
	
	ObjectMapper mapper = new ObjectMapper();
	
	@GetMapping(value="all")
	public JsonNode getAllUser() {
		JsonNode jsonNodeMap = null;
		Map<String, Object> resData = new HashMap<String, Object>(); 
		
		List<UserDto> userDtos = userDataProvider.getAllWithAppointments();
		resData.put("users", commonDataProvider.convertUserDtos2MapList(userDtos));
		jsonNodeMap = mapper.convertValue(resData, JsonNode.class);
		return jsonNodeMap;
	}
	
	@GetMapping(value="one")
	public JsonNode getUserFromToken(HttpServletRequest request) {
		JsonNode jsonNodeMap = null;
		Map<String, Object> resData = new HashMap<String, Object>(); 

		String token = null;
		Cookie cookie = WebUtils.getCookie(request, JwtDataProvider.KEY_TOKEN_IN_COOKIE);
		if (cookie != null) token = cookie.getValue();
		if (token == null) {
			resData.put("result",0);
		} else {
			String id = jwtDataProivder.getIdFromToken(token);
			UserDto userDto = userDataProvider.getUserByIdWithAppointments(id);
			if (userDto == null) {
				resData.put("result",0);
			} else {
				List<UserDto> userDtos = new ArrayList<UserDto>();
				userDtos.add(userDto);
				resData.put("users", commonDataProvider.convertUserDtos2MapList(userDtos));
				resData.put("result",1);
			}
		}
		
		jsonNodeMap = mapper.convertValue(resData, JsonNode.class);
		return jsonNodeMap;
	}
}

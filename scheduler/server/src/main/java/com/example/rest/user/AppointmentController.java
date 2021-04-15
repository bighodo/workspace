package com.example.rest.user;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
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
import com.example.dataprovider.JwtDataProvider;
import com.example.dataprovider.UserDataProvider;
import com.example.entity.Appointment;
import com.example.entity.User;
import com.example.entity.UserDto;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@RequestMapping(value = "/api/user/appointment", produces = "application/json; charset=utf8")
public class AppointmentController {
	static int JAVA_DATE_START_YEAR = 1900;

	@Autowired
	AppointmentDataProvider appointmentDataProvider;
	@Autowired
	UserDataProvider userDataProvider;
	@Autowired
	private JwtDataProvider jwtDataProivder;
	
	ObjectMapper mapper = new ObjectMapper();
	
	@GetMapping(value="all")
	public JsonNode getAllAppointments(HttpServletRequest request,
			@RequestParam(value="user",required=false,defaultValue="")String user) {
		JsonNode jsonNodeMap = null;
		Map<String, Object> resData = new HashMap<String, Object>(); 
		
		if (user.equals("")) {
			String token = null;
			Cookie cookie = WebUtils.getCookie(request, JwtDataProvider.KEY_TOKEN_IN_COOKIE);
			if (cookie != null) token = cookie.getValue();
			if (token == null) {
				resData.put("result",0);
			} else {
				String id = jwtDataProivder.getIdFromToken(token);
				User userObject = userDataProvider.getUserById(id);
				if (userObject == null) resData.put("result",0);
				else {
					user = userObject.getId();
					resData.put("result", 1);
				}
			}
		} else {
			resData.put("result", 1);
		}
		resData.put("appointments",appointmentDataProvider.getAllAppointmentByUserId(user));
		jsonNodeMap = mapper.convertValue(resData, JsonNode.class);
		return jsonNodeMap;
	}
	
	@PostMapping(value="one")
	public Map<String, Object> createAppointment(HttpServletRequest request, @RequestBody Map<String, Object> requestBody) {
		JsonNode jsonNodeMap = null;
		Map<String, Object> resData = new HashMap<String, Object>(); 
		
		int result = 0;
		String token = null;
		Cookie cookie = WebUtils.getCookie(request, JwtDataProvider.KEY_TOKEN_IN_COOKIE);
		if (cookie != null) token = cookie.getValue();
		if (token != null) {
			String id = jwtDataProivder.getIdFromToken(token);
			User user = userDataProvider.getUserById(id);
			if (user == null) {
				resData.put("appointment",new ArrayList<String>());
			} else {
				Map<String, Integer> start = (Map<String, Integer>) requestBody.get("startDate");
				Map<String, Integer> end = (Map<String, Integer>) requestBody.get("endDate");
				Date startDate = new Date(start.get("year")-JAVA_DATE_START_YEAR,
										  start.get("month"),
										  start.get("date"),
										  start.get("hours"),
										  start.get("minutes"));
				Date endDate = new Date(end.get("year")-JAVA_DATE_START_YEAR,
										end.get("month"),
										end.get("date"),
										end.get("hours"),
										end.get("minutes"));
//				String title = (String)requestBody.get("title");
//				String notes = (String)requestBody.get("notes");
				Appointment appointment = new Appointment(startDate, endDate, user.getUsername(), "", user);
				Appointment resultAppointment = appointmentDataProvider.createAppointment(appointment);
				if (resultAppointment != null) {
					resData.put("appointment",resultAppointment);
					result = 1;
				}
			}
		}
		resData.put("result", result);
		return resData;
//		jsonNodeMap = mapper.convertValue(resData, JsonNode.class);
//		return jsonNodeMap;
	}
	
	
	@PatchMapping(value="one")
	public Map<String, Object> updateAppointment(HttpServletRequest request, @RequestBody Map<String, Object> requestBody) {
		JsonNode jsonNodeMap = null;
		Map<String, Object> resData = new HashMap<String, Object>(); 

		String appointId = (String)requestBody.get("id");
		Appointment appoint = appointmentDataProvider.getAppointmentById(appointId);
		
		int result = 0;
		String token = null;
		Cookie cookie = WebUtils.getCookie(request, JwtDataProvider.KEY_TOKEN_IN_COOKIE);
		if (cookie != null || appoint == null) {
			token = cookie.getValue();
		}
		if (token != null) {
			String id = jwtDataProivder.getIdFromToken(token);
			User user = userDataProvider.getUserById(id);
			if (user == null) {
				resData.put("appointment",new ArrayList<String>());
			} else {

				Map<String, Integer> start = (Map<String, Integer>) requestBody.get("startDate");
				Map<String, Integer> end = (Map<String, Integer>) requestBody.get("endDate");
				Date startDate = new Date(start.get("year")-JAVA_DATE_START_YEAR,
										  start.get("month"),
										  start.get("date"),
										  start.get("hours"),
										  start.get("minutes"));
				Date endDate = new Date(end.get("year")-JAVA_DATE_START_YEAR,
										end.get("month"),
										end.get("date"),
										end.get("hours"),
										end.get("minutes"));
//				String title = (String)requestBody.get("title");
//				String notes = (String)requestBody.get("notes");
				appoint.setTitle(user.getUsername());
				appoint.setNotes("");
				appoint.setStartDate(startDate);
				appoint.setEndDate(endDate);
				Appointment updated = appointmentDataProvider.updateAppointment(appoint);
				if (updated != null) {
					resData.put("appointment",updated);
					result = 1;
				}

			}
		}
		resData.put("result", result);
		
		return resData;
//		jsonNodeMap = mapper.convertValue(resData, JsonNode.class);
//		return jsonNodeMap;
	}
}

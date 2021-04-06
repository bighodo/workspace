package com.example.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.WebUtils;

import com.example.dataprovider.JwtDataProvider;
import com.example.dataprovider.UserDataProvider;
import com.example.entity.User;
import com.example.entity.UserDto;


public class JwtAuthenticationFilter extends OncePerRequestFilter{
	
	private JwtDataProvider jwtDataProvider;
	private UserDataProvider userDataProvider; 
	
	public JwtAuthenticationFilter(JwtDataProvider jwtDataProvider, UserDataProvider userDataProvider) {
		this.jwtDataProvider = jwtDataProvider;
		this.userDataProvider = userDataProvider;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		String token = null;
		Cookie cookie = WebUtils.getCookie(request, JwtDataProvider.KEY_TOKEN_IN_COOKIE);
		if (cookie != null) token = cookie.getValue();
		if (token == null || jwtDataProvider.isTokenExpired(token)) {
			//invalid token
			Cookie cookie4expire = new Cookie(JwtDataProvider.KEY_TOKEN_IN_COOKIE, null);
			cookie4expire.setMaxAge(0);
			response.addCookie(cookie4expire);
		} else {
			//valid token
			String id = jwtDataProvider.getIdFromToken(token);
			User user = userDataProvider.getUserById(id);
			if (user != null) {
				UserDto userDto = new UserDto(
						user.getUsername(),
						user.getUserId(),
						user.getRole());
				Authentication authentication =  new UsernamePasswordAuthenticationToken(userDto, "", userDto.readAuthorities());
				SecurityContextHolder.getContext().setAuthentication(authentication);
			}
		}
		
		filterChain.doFilter(request, response);
	}
}

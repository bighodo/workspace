package com.example.dataprovider;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;
import java.util.function.Function;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.util.WebUtils;

import com.example.entity.User;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Header;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;

@Component
public class JwtDataProvider implements Serializable {
	
	private static final int EXP_HOUR = 24, EXP_MIN = 0, EXP_SEC = 0, TO_MILLISEC = 1000;//만료기간 1시간으로 해둠
	private static final long JWT_TOKEN_VALIDITY =  (EXP_HOUR*60*60) + (EXP_MIN*60) + EXP_SEC;
	public static final String KEY_TOKEN_IN_COOKIE = "access-token";
	
	private String secretKey = "createusersecretkey";
	
	public String generateToken(User user) {

		Claims claims = Jwts.claims().setSubject(user.getId());
		claims.put("userId", user.getUserId());
		claims.put("role", user.getRole());
		
		Header header = Jwts.header();
		header.setType("JWT");
			    
		return Jwts.builder()
				.setHeader((Map<String, Object>) header)
				.setClaims(claims)
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY * TO_MILLISEC))
				.signWith(SignatureAlgorithm.HS256, secretKey).compact();
		
	}
	
	public String refreshToken(String token) {
	    final Date createdDate = new Date();
	    final Date expirationDate = new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY * TO_MILLISEC);

	    final Claims claims = getAllClaimsFromToken(token);
	    claims.setIssuedAt(createdDate);
	    claims.setExpiration(expirationDate);

	    return Jwts.builder().setClaims(claims).signWith(SignatureAlgorithm.HS256, secretKey).compact();
	}

	public String getIdFromToken(String token) {
		return getClaimFromToken(token, Claims::getSubject);
	}

	//retrieve expiration date from jwt token
	public Date getExpirationDateFromToken(String token) {
		return getClaimFromToken(token, Claims::getExpiration);
	}
	
	public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
		final Claims claims = getAllClaimsFromToken(token);
		if (claims == null) return null;
		return claimsResolver.apply(claims);
	}

	//for retrieveing any information from token we will need the secret key
	private Claims getAllClaimsFromToken(String token) {
		try {
			return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody();
		} catch(ExpiredJwtException e) {
			System.out.println("Expired token found in getAllClaimsFromToken() of JwtUtil");
		} catch(MalformedJwtException e) {
			System.out.println("Wrong structure token found in getAllClaimsFromToken() of JwtUtil");
		} catch(SignatureException e) {
			System.out.println("Unmatched signature token found in getAllClaimsFromToken() of JwtUtil");
		}
		return null;
	}
	
	//check if the token has expired
	public Boolean isTokenExpired(String token) {
		final Date expiration = getExpirationDateFromToken(token);
		if (expiration == null) return true;
		return expiration.before(new Date());
	}
	

}
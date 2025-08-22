package edu.remad.tutoring3.helper.jwt;

import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;

public final class Tutoring3JwtHelper {

	private Tutoring3JwtHelper() {
		// do not instantiate
	}
	
	public static String getAccessToken(JwtAuthenticationToken jwtAuthenticationToken) {
		Jwt principal = (Jwt) jwtAuthenticationToken.getPrincipal();
		
		return principal.getTokenValue();
	}
	
}

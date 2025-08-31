package edu.remad.tutoring3.helper.jwt;

import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;

/**
 * a helper for JSON Web Tokens
 * 
 * @author edu.remad
 * @since 2025
 */
public final class Tutoring3JwtHelper {

	private Tutoring3JwtHelper() {
		// do not instantiate
	}
	
	/**
	 * Gets Bearer Token
	 * 
	 * @param jwtAuthenticationToken {@link JwtAuthenticationToken}
	 * @return string-encoded Bearer Access Token
	 */
	public static String getAccessToken(JwtAuthenticationToken jwtAuthenticationToken) {
		Jwt principal = (Jwt) jwtAuthenticationToken.getPrincipal();
		
		return principal.getTokenValue();
	}
	
}

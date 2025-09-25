package edu.remad.tutoring3.events.jwt;

import org.springframework.context.ApplicationEvent;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;

import edu.remad.tutoring3.helper.jwt.JwtAuthenticationTokenHelper;
import edu.remad.tutoring3.helper.jwt.Tutoring3JwtHelper;

/**
 * 
 * Event for authentication via BearerToken is successful
 * 
 * @author edu.remad
 * @since 2025
 */
public class JwtAuthenticationSuccessEvent extends ApplicationEvent {

	/** generated serial version UID */
	private static final long serialVersionUID = -5619240991088474310L;

	/**
	 * Constructor
	 * 
	 * @param jwtAuthenticationToken {@link JwtAuthenticationToken}
	 */
	public JwtAuthenticationSuccessEvent(JwtAuthenticationToken jwtAuthenticationToken) {
		super(jwtAuthenticationToken);
	}

	/**
	 * Gets authentication object
	 * 
	 * @return {@link JwtAuthenticationToken}
	 */
	public JwtAuthenticationToken getAuthentication() {
		return (JwtAuthenticationToken) getSource();
	}
	
	/**
	 * Gets Bearer Access Token
	 * 
	 * @return String-encoded AccessToken
	 */
	public String getAccessToken() {
		return Tutoring3JwtHelper.getAccessToken(getAuthentication());
	}
	
	/**
	 * @return {@link JwtAuthenticationTokenHelper}
	 */
	public JwtAuthenticationTokenHelper getJwtAuthenticationTokenHelper() {
		return (JwtAuthenticationTokenHelper) getSource();
	}

}

package edu.remad.tutoring3.events.jwt;

import org.springframework.context.ApplicationEvent;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;

import edu.remad.tutoring3.helper.jwt.Tutoring3JwtHelper;

public class JwtAuthenticationSuccessEvent extends ApplicationEvent {

	/** generated serial version UID */
	private static final long serialVersionUID = -5619240991088474310L;

	public JwtAuthenticationSuccessEvent(JwtAuthenticationToken jwtAuthenticationToken) {
		super(jwtAuthenticationToken);
	}

	public JwtAuthenticationToken getAuthentication() {
		return (JwtAuthenticationToken) getSource();
	}
	
	public String getAccessToken() {
		return Tutoring3JwtHelper.getAccessToken(getAuthentication());
	}

}

package edu.remad.tutoring3.events.jwt;

import org.springframework.context.ApplicationEvent;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Component;

import edu.remad.tutoring3.helper.jwt.Tutoring3JwtHelper;

@Component
public class JwtAuthenticationSuccessEvent extends ApplicationEvent {

	/** generated serial version UID */
	private static final long serialVersionUID = -5619240991088474310L;
	
	private Tutoring3JwtHelper jwtHelper;

	public JwtAuthenticationSuccessEvent() {
		super("");
		jwtHelper = null;
	}

	public void setJwtAuthenticationToken(JwtAuthenticationToken authentication) {
		setSource(authentication);
	}

	private void setSource(JwtAuthenticationToken authentication) {
		source = authentication;
	}

	public JwtAuthenticationToken getAuthentication() {
		return (JwtAuthenticationToken) getSource();
	}

}

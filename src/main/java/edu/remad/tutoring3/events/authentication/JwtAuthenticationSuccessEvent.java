package edu.remad.tutoring3.events.authentication;

import org.springframework.security.authentication.event.AbstractAuthenticationEvent;
import org.springframework.security.core.Authentication;

public class JwtAuthenticationSuccessEvent extends AbstractAuthenticationEvent {

	/**
	 * serial version UID
	 */
	private static final long serialVersionUID = 1L;

	public JwtAuthenticationSuccessEvent(Authentication authentication) {
		super(authentication);
	}

}

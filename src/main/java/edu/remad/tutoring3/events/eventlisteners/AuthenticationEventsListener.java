package edu.remad.tutoring3.events.eventlisteners;

import org.springframework.context.event.EventListener;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Component;

import edu.remad.tutoring3.events.jwt.JwtAuthenticationSuccessEvent;

@Component
public class AuthenticationEventsListener {

	@EventListener(classes = AuthenticationSuccessEvent.class)
	public JwtAuthenticationSuccessEvent onSuccess(AuthenticationSuccessEvent success) {
		return new JwtAuthenticationSuccessEvent((JwtAuthenticationToken) success.getAuthentication());
	}

}

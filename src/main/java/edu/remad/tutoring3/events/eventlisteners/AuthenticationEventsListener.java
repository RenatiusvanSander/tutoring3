package edu.remad.tutoring3.events.eventlisteners;

import org.springframework.context.event.EventListener;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Component;

import edu.remad.tutoring3.events.jwt.JwtAuthenticationSuccessEvent;

/**
 * Listens for events of {@link AuthenticationSuccessEvent} and propagate
 * created {@link JwtAuthenticationSuccessEvent}
 * 
 * @author edu.remad
 * @since 2025
 */
@Component
public class AuthenticationEventsListener {

	/**
	 * Listens on events of type {@link AuthenticationSuccessEvent}
	 * 
	 * @param successfulAuthenticated {@link AuthenticationSuccessEvent}
	 * @return{@link JwtAuthenticationSuccessEvent}
	 */
	@EventListener(classes = AuthenticationSuccessEvent.class)
	public JwtAuthenticationSuccessEvent onSuccess(AuthenticationSuccessEvent successfulAuthenticated) {
		return new JwtAuthenticationSuccessEvent((JwtAuthenticationToken) successfulAuthenticated.getAuthentication());
	}

}

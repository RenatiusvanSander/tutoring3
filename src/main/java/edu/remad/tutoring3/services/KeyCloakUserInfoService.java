package edu.remad.tutoring3.services;

import org.springframework.context.event.EventListener;

import edu.remad.tutoring3.events.jwt.JwtAuthenticationSuccessEvent;

public interface KeyCloakUserInfoService {

	void listenJwtAuthenticationTokenEventAndProcess(JwtAuthenticationSuccessEvent jwtAuthenticationSuccessEvent);
}

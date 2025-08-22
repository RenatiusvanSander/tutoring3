package edu.remad.tutoring3.services;

import org.springframework.context.event.EventListener;
import org.springframework.web.client.RestClient;

import edu.remad.tutoring3.events.jwt.JwtAuthenticationSuccessEvent;

public interface KeyCloakUserInfoService {

	void listenJwtAuthenticationTokenEventAndProcess(JwtAuthenticationSuccessEvent jwtAuthenticationSuccessEvent);
}

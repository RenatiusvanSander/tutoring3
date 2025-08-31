package edu.remad.tutoring3.services;

import edu.remad.tutoring3.events.jwt.JwtAuthenticationSuccessEvent;
import edu.remad.tutoring3.services.impl.KeyCloakUserInfoServiceImpl;

/**
 * Defines methods for {@link KeyCloakUserInfoServiceImpl}
 * 
 * @author edu.remad
 * @since 2025
 */
public interface KeyCloakUserInfoService {

	void listenJwtAuthenticationTokenEventAndProcess(JwtAuthenticationSuccessEvent jwtAuthenticationSuccessEvent);
}

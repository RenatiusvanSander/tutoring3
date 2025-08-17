package edu.remad.tutoring3.services.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.context.event.EventListener;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import edu.remad.tutoring3.events.jwt.JwtAuthenticationSuccessEvent;
import edu.remad.tutoring3.services.KeyCloakUserInfoService;
import edu.remad.tutoring3.services.impl.dto.UserInfo;

@Service
public class KeyCloakUserInfoServiceImpl implements KeyCloakUserInfoService {

	private final RestClient restClient;
	
	public KeyCloakUserInfoServiceImpl(RestClient.Builder restClientBuilder) {
		restClient = restClientBuilder.baseUrl("https://keycloak.local:8443/realms/ConnectTrial/protocol/openid-connect").build();
	}
	
	@Override
	@EventListener(classes = JwtAuthenticationSuccessEvent.class)
	public void listenJwtAuthenticationTokenEventAndProcess(
			JwtAuthenticationSuccessEvent jwtAuthenticationSuccessEvent) {
		
		JwtAuthenticationToken jwtToken = (JwtAuthenticationToken) jwtAuthenticationSuccessEvent.getAuthentication();
		String token = jwtToken.getPrincipal().toString();
		
		Map<String, Object> uriVariables = new HashMap<>();
		restClient.get().header(HttpHeaders.AUTHORIZATION, "Bearer " + token).header(HttpHeaders.CONTENT_TYPE, "application/x-www-form-urlencoded");
		
		//restClient.get().uri("/userinfo", uriVariables).retrieve().body(UserInfo.class);

	}

}

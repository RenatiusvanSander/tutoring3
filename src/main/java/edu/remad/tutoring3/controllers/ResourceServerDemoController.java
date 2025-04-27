package edu.remad.tutoring3.controllers;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ResourceServerDemoController {
	
	@PreAuthorize("hasRole('client_user')")
	@GetMapping("/v2/demo")
	public String answerDemo(JwtAuthenticationToken jwtAuthenticationToken) {
		return "Spring 6 and Keycloak works";
	}
	
	@PreAuthorize("hasRole('client_admin')")
	@GetMapping("/v2/demoAdmin")
	public String answerDemoAdmin(JwtAuthenticationToken jwtAuthenticationToken) {
		return "Spring 6 and Keycloaks Admin";
	}
}

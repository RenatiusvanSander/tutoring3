package edu.remad.tutoring3.controllers;

import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import edu.remad.tutoring3.persistence.models.UserEntity;
import edu.remad.tutoring3.services.UserEntityService;

/**
 * Controlls API REST Endpoints for users 
 * 
 * @author edu.remad
 * @since 2025
 */
@RequestMapping("/api/user")
@RestController
public class ApiUserEntityController {
	
	private final UserEntityService userService;
	
	public ApiUserEntityController(UserEntityService userEntityService) {
		userService = userEntityService;
	}

	@GetMapping(value = "/get-users/by-user-id/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<UserEntity> getUserById(@PathVariable("id") String id) {
		UserEntity user = new UserEntity();
		
		return new ResponseEntity<>(user, HttpStatusCode.valueOf(200));
	}
}

package edu.remad.tutoring3.controllers;

import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import edu.remad.tutoring3.model.UserEntity;
import edu.remad.tutoring3.services.UserEntityService;

/**
 * Controls API REST Endpoints for users 
 * 
 * @author edu.remad
 * @since 2025
 */
@RequestMapping("/api/user")
@RestController
public class ApiUserEntityController {
	
	/**
	 * user service
	 */
	private final UserEntityService userService;
	
	/**
	 * Constructor
	 * 
	 * @param userEntityService {@link UserEntityService}
	 */
	public ApiUserEntityController(UserEntityService userEntityService) {
		userService = userEntityService;
	}

	/**
	 * Gets a suer by id
	 * 
	 * @param id Identifier to find a user
	 * @return {@link UserEntity}
	 */
	@GetMapping(value = "/get-users/by-user-id/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<UserEntity> getUserById(@PathVariable("id") String id) {
		UserEntity user = userService.getUserEntityById(id);
		
		return new ResponseEntity<>(user, HttpStatusCode.valueOf(200));
	}
	
}

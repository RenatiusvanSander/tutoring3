package edu.remad.tutoring3.controllers;

import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import edu.remad.tutoring3.dto.UserDto;
import edu.remad.tutoring3.helper.jwt.JwtAuthenticationTokenHelper;
import edu.remad.tutoring3.persistence.models.UserEntity;
import edu.remad.tutoring3.services.UserEntityService;

/**
 * Controls API REST Endpoints for users
 * 
 * @author edu.remad
 * @since 2025
 */
@RequestMapping("/api/users")
@RestController
public class ApiUserEntityController {

	/** user service */
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
	 * Gets a user by id
	 * 
	 * @param id Identifier to find a user
	 * @return {@link UserEntity}
	 */
	@GetMapping("/get-users/by-user-id/{id}")
	public ResponseEntity<UserDto> getUserById(@PathVariable("id") Long id) {
		UserEntity user = userService.getUserEntityById(id);

		return new ResponseEntity<>(new UserDto(user), HttpStatusCode.valueOf(200));
	}

	/**
	 * Gets a user via sub
	 * 
	 * @param authentication {@link Authentication}
	 * @return {@link UserEntity}
	 */
	@GetMapping("/get-users/get-user")
	public ResponseEntity<UserDto> getUserViaSub(Authentication authentication) {
		JwtAuthenticationTokenHelper jwtHelper = (JwtAuthenticationTokenHelper) authentication;
		UserEntity user = userService.getUserEntityBySub(jwtHelper.getSub());

		return new ResponseEntity<>(new UserDto(user), HttpStatusCode.valueOf(200));
	}

}

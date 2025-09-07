package edu.remad.tutoring3.controllers;

import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import edu.remad.tutoring3.model.AddressEntity;
import edu.remad.tutoring3.services.AddressEntityService;

/**
 * Controlls API REST Endpoints for user's addresses
 * 
 * @author edu.remad
 * @since 2025
 */
@RequestMapping("/api/address")
@RestController
public class ApiAddressEntity {
	
	private final AddressEntityService addressService;
	
	public ApiAddressEntity(AddressEntityService addressEntityService) {
		addressService = addressEntityService;
	}

	@GetMapping(value = "/get-addresses/by-address-id/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<AddressEntity> getAddressById(@PathVariable("id") Long id) {
		AddressEntity adress = new AddressEntity();
		
		return new ResponseEntity<>(adress, HttpStatusCode.valueOf(200));
	}
}

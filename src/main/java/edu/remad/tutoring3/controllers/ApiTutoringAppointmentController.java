package edu.remad.tutoring3.controllers;

import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import edu.remad.tutoring3.dto.AddressDto;
import edu.remad.tutoring3.services.TutoringAppointmentEntityService;

/**
 * Controls API REST Endpoints for tutoring appointments'
 * 
 * @author edu.remad
 * @since 2025
 */
@RequestMapping("/api/tutoring-appointments")
@RestController
public class ApiTutoringAppointmentController {

	private final TutoringAppointmentEntityService appointmentService;
	
	public ApiTutoringAppointmentController(TutoringAppointmentEntityService appointmentEntityService) {
		appointmentService = appointmentEntityService;
	}
	
	@PostMapping("/save")
	public ResponseEntity<Object> saveTutoringAppointment(@RequestBody AddressDto addressDto) {
		return new ResponseEntity<>("", HttpStatusCode.valueOf(201));
	}
}

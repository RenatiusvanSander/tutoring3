package edu.remad.tutoring3.controllers;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import edu.remad.tutoring3.dto.TutoringAppointmentDto;
import edu.remad.tutoring3.persistence.models.TutoringAppointmentEntity;
import edu.remad.tutoring3.persistence.models.UserEntity;
import edu.remad.tutoring3.services.TutoringAppointmentEntityService;
import edu.remad.tutoring3.services.UserEntityService;

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

	private final UserEntityService userService;

	/**
	 * Constructor
	 * 
	 * @param appointmentEntityService {@link TutoringAppointmentEntityService}
	 * @param userEntityService        {@link UserEntityService}
	 */
	public ApiTutoringAppointmentController(TutoringAppointmentEntityService appointmentEntityService,
			UserEntityService userEntityService) {
		appointmentService = appointmentEntityService;
		userService = userEntityService;
	}

	/**
	 * Saves a tutoring appointment
	 * 
	 * @param tutoringAppointmentDto
	 * @return json-encoded {@link TutoringAppointmentDto}
	 */
	@PostMapping("/save")
	public ResponseEntity<TutoringAppointmentDto> saveTutoringAppointment(
			@RequestBody TutoringAppointmentDto tutoringAppointmentDto) {
		UserEntity loadedUser = userService.getReferencedUserEntityById(tutoringAppointmentDto.getUserId());
		TutoringAppointmentEntity newAppointment = new TutoringAppointmentEntity(tutoringAppointmentDto, loadedUser);
		newAppointment.setTutoringAppointmentCreationDate(LocalDateTime.now());
		TutoringAppointmentEntity savedAppointment = appointmentService.saveTutoringApointment(newAppointment);

		return new ResponseEntity<>(new TutoringAppointmentDto(savedAppointment), HttpStatusCode.valueOf(201));
	}

	/**
	 * Loads a tutoring appointment
	 * 
	 * @param id Tutoring Appointment's identifier
	 * @return json-encoded loaded {@link TutoringAppointmentDto}
	 */
	@GetMapping("/get/{id}")
	public ResponseEntity<TutoringAppointmentDto> loadTutoringAppointment(@PathVariable("id") Long id) {
		TutoringAppointmentEntity loadedAppointment = appointmentService.loadTutoringApointment(id);

		return new ResponseEntity<TutoringAppointmentDto>(new TutoringAppointmentDto(loadedAppointment),
				HttpStatusCode.valueOf(200));
	}
}

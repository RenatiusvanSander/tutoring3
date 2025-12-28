package edu.remad.tutoring3.controllers;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import edu.remad.tutoring3.dto.TutoringAppointmentDto;
import edu.remad.tutoring3.helper.LocalDateTimeHelper;
import edu.remad.tutoring3.persistence.models.ServiceContractEntity;
import edu.remad.tutoring3.persistence.models.TutoringAppointmentEntity;
import edu.remad.tutoring3.persistence.models.UserEntity;
import edu.remad.tutoring3.services.ServiceContractService;
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

	private final ServiceContractService serviceContractService;

	/**
	 * Constructor
	 * 
	 * @param appointmentEntityService {@link TutoringAppointmentEntityService}
	 * @param userEntityService        {@link UserEntityService}
	 * @param serviceContractService   {@link ServiceContractService}
	 */
	public ApiTutoringAppointmentController(TutoringAppointmentEntityService appointmentEntityService,
			UserEntityService userEntityService, ServiceContractService serviceContractService) {
		appointmentService = appointmentEntityService;
		userService = userEntityService;
		this.serviceContractService = serviceContractService;
	}

	/**
	 * Saves a tutoring appointment
	 * 
	 * @param tutoringAppointmentDto {@link TutoringAppointmentDto}
	 * @return json-encoded {@link TutoringAppointmentDto}
	 */
	@PostMapping("/save")
	public ResponseEntity<TutoringAppointmentDto> saveTutoringAppointment(
			@RequestBody TutoringAppointmentDto tutoringAppointmentDto) {
		UserEntity loadedUser = userService
				.getReferencedUserEntityById(tutoringAppointmentDto.getTutoringAppointmentUser());
		ServiceContractEntity loadeServiceContract = serviceContractService
				.getReferencedServiceContractById(tutoringAppointmentDto.getServiceContractId());
		TutoringAppointmentEntity newAppointment = new TutoringAppointmentEntity(tutoringAppointmentDto, loadedUser,
				loadeServiceContract);
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

	/**
	 * Gets tutoring appointments of user
	 * 
	 * @param userId user's identifier
	 * @return json-encoded {@link TutoringAppointmentDto}
	 */
	@GetMapping("/get/by-user-id/{userId}")
	public ResponseEntity<List<TutoringAppointmentDto>> getTutoringAppointmentsByUserId(
			@PathVariable("userId") Long userId) {
		List<TutoringAppointmentEntity> loadedAppointments = appointmentService.loadTutoringApointmentByUserId(userId);
		List<TutoringAppointmentDto> appointments = loadedAppointments.stream().map(TutoringAppointmentDto::new)
				.collect(Collectors.toList());

		return new ResponseEntity<>(appointments, HttpStatusCode.valueOf(200));
	}

	/**
	 * Gets service contracts by ids
	 * 
	 * @param ids tutoring appointments' identifiers
	 * @return json-encoded {@link TutoringAppointmentDto}
	 */
	@GetMapping("/get/by-ids")
	public ResponseEntity<List<TutoringAppointmentDto>> getTutoringAppointmentByIds(
			@RequestParam(value = "id") List<Long> ids) {
		List<TutoringAppointmentEntity> loadedTutoringAppointments = appointmentService
				.loadTutoringApointmentByIds(ids);
		List<TutoringAppointmentDto> tutoringAppointments = loadedTutoringAppointments.stream()
				.map(TutoringAppointmentDto::new).collect(Collectors.toList());

		return new ResponseEntity<>(tutoringAppointments, HttpStatusCode.valueOf(200));
	}

	/**
	 * Updates single appointment
	 * 
	 * @param tutoringAppointmentDto {@link TutoringAppointmentDto}
	 * @return json-encoded {@link TutoringAppointmentDto}
	 */
	@PutMapping("/update/single-appointment")
	public ResponseEntity<TutoringAppointmentDto> updateSingleTutoringAppointment(
			@RequestBody TutoringAppointmentDto tutoringAppointmentDto) {
		TutoringAppointmentEntity loadedAppointment = appointmentService
				.loadTutoringApointment(tutoringAppointmentDto.getTutoringAppointmentNo());
		ServiceContractEntity loadedServiceContract = serviceContractService
				.findServiceContractById(tutoringAppointmentDto.getServiceContractId());

		if (loadedAppointment != null && loadedServiceContract != null) {
			loadedAppointment.setServiceContractId(loadedServiceContract);
			loadedAppointment.setAccomplished(tutoringAppointmentDto.isAccomplished());
			loadedAppointment.setTutoringAppointmentDate(LocalDateTimeHelper
					.convertIsoTimeWithoutZToLocalDateTime(tutoringAppointmentDto.getTutoringAppointmentDate()));
			loadedAppointment
					.setTutoringAppointmentStartDateTime(LocalDateTimeHelper.convertIsoTimeWithoutZToLocalDateTime(
							tutoringAppointmentDto.getTutoringAppointmentStartDateTime()));
			loadedAppointment.setTutoringAppointmentEndDateTime(LocalDateTimeHelper
					.convertIsoTimeWithoutZToLocalDateTime(tutoringAppointmentDto.getTutoringAppointmentEndDateTime()));

			loadedAppointment = appointmentService.updateSingleTutoringAppointment(loadedAppointment);
		}

		return new ResponseEntity<>(new TutoringAppointmentDto(loadedAppointment), HttpStatusCode.valueOf(200));
	}

	/**
	 * Updates multiple appointments
	 * 
	 * @param tutoringAppointmentDtos {@link List}
	 * @return json-encoded array of {@link TutoringAppointmentDto}
	 */
	@PutMapping("/update/multiple-appointments")
	public ResponseEntity<List<TutoringAppointmentDto>> updateMultipleTutoringAppointments(
			@RequestBody List<TutoringAppointmentDto> tutoringAppointmentDtos) {
		List<Long> tutoringAppointmentIds = tutoringAppointmentDtos.stream()
				.mapToLong(TutoringAppointmentDto::getTutoringAppointmentNo).boxed().collect(Collectors.toList());
		List<TutoringAppointmentEntity> loadedAppointments = appointmentService
				.loadTutoringApointmentByIds(tutoringAppointmentIds);
		List<Long> serviceContractIds = tutoringAppointmentDtos.stream()
				.mapToLong(TutoringAppointmentDto::getServiceContractId).boxed().collect(Collectors.toList());
		List<ServiceContractEntity> loadedServiceContracts = serviceContractService
				.findServiceContractsByIds(serviceContractIds);

		List<TutoringAppointmentEntity> updatedAppointments = new ArrayList<>();
		List<TutoringAppointmentDto> updatedAppointmentDtos = new ArrayList<>();
		if (loadedAppointments != null && !loadedAppointments.isEmpty() && loadedServiceContracts != null
				&& !loadedServiceContracts.isEmpty() && tutoringAppointmentDtos.size() == loadedAppointments.size()) {

			Iterator<TutoringAppointmentEntity> loadedAppointmentsIterator = loadedAppointments.iterator();
			Map<Long, ServiceContractEntity> serviceContractsMap = loadedServiceContracts.stream()
					.collect(Collectors.toMap(ServiceContractEntity::getServiceContractNo, Function.identity()));
			Iterator<TutoringAppointmentDto> tutoringAppointmentDtosIterator = tutoringAppointmentDtos.iterator();
			while (loadedAppointmentsIterator.hasNext() && tutoringAppointmentDtosIterator.hasNext()) {
				TutoringAppointmentDto appointmentDto = tutoringAppointmentDtosIterator.next();
				ServiceContractEntity serviceContract = serviceContractsMap.get(appointmentDto.getServiceContractId());

				TutoringAppointmentEntity updatedAppointment = loadedAppointmentsIterator.next();
				updatedAppointment.setServiceContractId(serviceContract);
				updatedAppointment.setAccomplished(appointmentDto.isAccomplished());
				updatedAppointment.setTutoringAppointmentDate(LocalDateTimeHelper
						.convertIsoTimeWithoutZToLocalDateTime(appointmentDto.getTutoringAppointmentDate()));
				updatedAppointment.setTutoringAppointmentStartDateTime(LocalDateTimeHelper
						.convertIsoTimeWithoutZToLocalDateTime(appointmentDto.getTutoringAppointmentStartDateTime()));
				updatedAppointment.setTutoringAppointmentEndDateTime(LocalDateTimeHelper
						.convertIsoTimeWithoutZToLocalDateTime(appointmentDto.getTutoringAppointmentEndDateTime()));
				updatedAppointments.add(updatedAppointment);
			}
		}

		if (!updatedAppointments.isEmpty()) {
			updatedAppointments = appointmentService.updateMultipleTutoringAppointments(updatedAppointments);
		}

		for (TutoringAppointmentEntity updatedAppointment : updatedAppointments) {
			updatedAppointmentDtos.add(new TutoringAppointmentDto(updatedAppointment));
		}

		return new ResponseEntity<>(updatedAppointmentDtos, HttpStatusCode.valueOf(200));
	}
}

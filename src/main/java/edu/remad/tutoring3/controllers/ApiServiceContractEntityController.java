package edu.remad.tutoring3.controllers;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import edu.remad.tutoring3.dto.ServiceContractDto;
import edu.remad.tutoring3.persistence.models.ServiceContractEntity;
import edu.remad.tutoring3.services.ServiceContractService;

/**
 * Controls API REST Endpoints for service contracts
 * 
 * @author edu.remad
 * @since 2025
 */
@RequestMapping("/api/servicecontracts")
@RestController
public class ApiServiceContractEntityController {

	private final ServiceContractService serviceContractService;

	/**
	 * Constructor
	 * 
	 * @param serviceContractPriceEntityService {@link ServiceContractService}
	 */
	public ApiServiceContractEntityController(ServiceContractService serviceContractService) {
		this.serviceContractService = serviceContractService;
	}

	/**
	 * Saves service contract
	 * 
	 * @param serviceContract {@link ServiceContractDto}
	 * @return json-encoded {@link ServiceContractDto}
	 */
	@PostMapping("/save-service-conract")
	public ResponseEntity<ServiceContractDto> saveServiceContract(@RequestBody ServiceContractDto serviceContract) {
		ServiceContractEntity loadedServiceContract = serviceContractService
				.createServiceContract(new ServiceContractEntity(serviceContract));

		return new ResponseEntity<>(new ServiceContractDto(loadedServiceContract),
				HttpStatusCode.valueOf(200));
	}

	/**
	 * Gets service contract by identifier
	 * 
	 * @param id service conract's identifier
	 * @return json-encoded {@link ServiceContractDto}
	 */
	@GetMapping("/get-service-contract-by-id/{id}")
	public ResponseEntity<ServiceContractDto> getServiceContractById(@PathVariable("id") Long id) {
		ServiceContractEntity loadeServiceContract = serviceContractService.getServiceContract(id);

		return new ResponseEntity<>(new ServiceContractDto(loadeServiceContract),
				HttpStatusCode.valueOf(200));
	}

	/**
	 * Gets all service contracts
	 * 
	 * @return json-encoded array of {@link ServiceContractDto}
	 */
	@GetMapping("/get-service-conracts")
	public ResponseEntity<List<ServiceContractDto>> getServiceContracts() {
		List<ServiceContractEntity> loadedServiceContracts = serviceContractService.getAllServiceContracts();
		List<ServiceContractDto> serviceContracts = loadedServiceContracts.stream()
				.map(item -> new ServiceContractDto(item)).collect(Collectors.toList());

		return new ResponseEntity<>(serviceContracts, HttpStatusCode.valueOf(200));
	}
}

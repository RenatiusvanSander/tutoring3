package edu.remad.tutoring3.controllers;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import edu.remad.tutoring3.dto.ServiceContractPriceDto;
import edu.remad.tutoring3.persistence.models.PriceEntity;
import edu.remad.tutoring3.persistence.models.ServiceContractEntity;
import edu.remad.tutoring3.persistence.models.ServiceContractPriceEntity;
import edu.remad.tutoring3.persistence.models.UserEntity;
import edu.remad.tutoring3.services.PriceEntityService;
import edu.remad.tutoring3.services.ServiceContractPriceEntityService;
import edu.remad.tutoring3.services.ServiceContractService;
import edu.remad.tutoring3.services.UserEntityService;

/**
 * Controls API REST Endpoints for service contract prices
 * 
 * @author edu.remad
 * @since 2025
 */
@RestController
@RequestMapping("/api/servicecontractprices")
public class ApiServiceContractPriceEntityController {

	private final PriceEntityService priceService;
	private final ServiceContractService serviceContractService;
	private final UserEntityService userService;
	private final ServiceContractPriceEntityService serviceContractPriceService;

	/**
	 * Constructor
	 * 
	 * @param priceService                {@link PriceEntityService}
	 * @param serviceContractService      {@link ServiceContractService}
	 * @param serviceContractPriceService {@link ServiceContractPriceEntityService}
	 * @param userEntityService           {@link UserEntityService}
	 */
	public ApiServiceContractPriceEntityController(PriceEntityService priceService,
			ServiceContractService serviceContractService,
			ServiceContractPriceEntityService serviceContractPriceService, UserEntityService userEntityService) {
		this.priceService = priceService;
		this.serviceContractService = serviceContractService;
		this.serviceContractPriceService = serviceContractPriceService;
		userService = userEntityService;
	}

	/**
	 * Saves service contract price
	 * 
	 * @param serviceContractPrice {@link ServiceContractPriceDto}
	 * @return json-encoded {@link ServiceContractPriceDto}
	 */
	@PostMapping("/save-service-contract-price")
	public ResponseEntity<ServiceContractPriceDto> saveServieContractPrice(
			@RequestBody ServiceContractPriceDto serviceContractPrice) {
		PriceEntity loadedPrice = priceService.getReferencedPrice(serviceContractPrice.getPriceId());
		ServiceContractEntity loadedServiceContract = serviceContractService
				.getReferencedServiceContractById(serviceContractPrice.getServiceContractId());
		UserEntity loadedUser = userService.getReferencedUserEntityById(serviceContractPrice.getUserId());

		if (loadedPrice == null || loadedServiceContract == null || loadedUser == null) {
			throw new IllegalArgumentException();
		}

		ServiceContractPriceEntity newServiceContractPrice = new ServiceContractPriceEntity(0, loadedUser,
				loadedServiceContract, loadedPrice, false, LocalDateTime.now());
		ServiceContractPriceEntity savedServiceContractPrice = serviceContractPriceService
				.saveServiceContractPrice(newServiceContractPrice);

		return new ResponseEntity<>(new ServiceContractPriceDto(savedServiceContractPrice),
				HttpStatusCode.valueOf(200));
	}

	/**
	 * Gets service contract prices by user's id
	 * 
	 * @param userId user's identifier
	 * @return list of {@link ServiceContractPriceDto}
	 */
	@GetMapping("/get-service-contract-by-user-id/{id}")
	public ResponseEntity<List<ServiceContractPriceDto>> getServiceContracstPriceByUserId(
			@PathVariable("id") Long userId) {
		UserEntity loadedUser = userService.getUserEntityById(userId);
		if (loadedUser == null) {
			return new ResponseEntity<>(HttpStatusCode.valueOf(404));
		}

		List<ServiceContractPriceEntity> serviceContractPrices = serviceContractPriceService
				.getUsersServiceContractPrices(loadedUser.getUserId());
		List<ServiceContractPriceDto> serviceContractPriceDtos = serviceContractPrices.stream()
				.map(item -> new ServiceContractPriceDto(item)).toList();

		return new ResponseEntity<>(serviceContractPriceDtos, HttpStatusCode.valueOf(200));
	}

	/**
	 * Gets not confirmed service contract prices
	 * 
	 * @return list of {@link ServiceContractPriceDto}
	 */
	@GetMapping("/get-not-confirmed-service-contract-prices")
	public ResponseEntity<List<ServiceContractPriceDto>> getNotConfirmedServiceContractPrices() {
		List<ServiceContractPriceEntity> loadedNotCofirmedServiceContractPrices = serviceContractPriceService
				.findNotConfirmedServiceContractPrices();
		List<ServiceContractPriceDto> notCofirmedServiceContractPrices = loadedNotCofirmedServiceContractPrices.stream()
				.map(item -> new ServiceContractPriceDto(item)).toList();

		return new ResponseEntity<>(notCofirmedServiceContractPrices,
				HttpStatusCode.valueOf(200));
	}
	
	/**
	 * Updates {@link ServiceContractEntity}
	 * 
	 * @param serviceContractPrice {@link ServiceContractPriceDto}
	 * @return json-encoded {@link ServiceContractEntity}
	 */
	@PutMapping("/update-service-contract-price")
	public ResponseEntity<ServiceContractPriceDto> updateServiceContractPrice(@RequestBody ServiceContractPriceDto serviceContractPrice) {
		ServiceContractPriceEntity loadedSCP = serviceContractPriceService.getServiceContractPrice(serviceContractPrice.getId());
		loadedSCP.setConfirmed(serviceContractPrice.isConfirmed());
		
		if(serviceContractPrice.getPriceId() != loadedSCP.getPriceId().getId()) {
			PriceEntity loadedPrice = priceService.getPrice(serviceContractPrice.getPriceId());
			loadedSCP.setPriceId(loadedPrice);
		}
		
		if(serviceContractPrice.getServiceContractId() != loadedSCP.getServiceContractId().getServiceContractNo()) {
			ServiceContractEntity loadedServiceContract = serviceContractService.findServiceContractById(serviceContractPrice.getServiceContractId());
			loadedSCP.setServiceContractId(loadedServiceContract);			
		}
		
		ServiceContractPriceEntity updatedServiceContractPrice = serviceContractPriceService.updateServiceContractPrice(loadedSCP);
		
		return new ResponseEntity<>(new ServiceContractPriceDto(updatedServiceContractPrice), HttpStatusCode.valueOf(200));
	}
}

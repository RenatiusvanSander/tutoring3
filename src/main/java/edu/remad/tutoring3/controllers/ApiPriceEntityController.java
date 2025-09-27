package edu.remad.tutoring3.controllers;

import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import edu.remad.tutoring3.dto.PriceDto;
import edu.remad.tutoring3.persistence.models.PriceEntity;
import edu.remad.tutoring3.services.PriceEntityService;

/**
 * Controls API REST Endpoints for service contracts
 * 
 * @author edu.remad
 * @since 2025
 */
@RequestMapping("/api/prices")
@RestController
public class ApiPriceEntityController {

	private final PriceEntityService priceService;

	/**
	 * Constructor
	 * 
	 * @param priceService {@link PriceEntityService}
	 */
	public ApiPriceEntityController(PriceEntityService priceService) {
		super();
		this.priceService = priceService;
	}

	/**
	 * Saves price
	 * 
	 * @param price {@link PriceDto}
	 * @return json-encoded {@link PriceDto}
	 */
	@PostMapping("/save-price")
	public ResponseEntity<PriceDto> savePrice(@RequestBody PriceDto price) {
		PriceEntity savedPrice = priceService.savePrice(new PriceEntity(price));

		return new ResponseEntity<>(new PriceDto(savedPrice), HttpStatusCode.valueOf(200));
	}

	/**
	 * Gets price
	 * 
	 * @param id price's identifier
	 * @return json-encoded {@link PriceDto}
	 */
	@GetMapping("/get-price/{id}")
	public ResponseEntity<PriceDto> getPrice(@PathVariable("id") Long id) {
		PriceEntity foundPrice = priceService.getPrice(id);

		return new ResponseEntity<>(new PriceDto(foundPrice), HttpStatusCode.valueOf(200));
	}

	/**
	 * Updates price
	 * 
	 * @param price {@link PriceDto}
	 * @return json-encoded {@link PriceDto}
	 */
	@PutMapping("/update-price")
	public ResponseEntity<PriceDto> updatePrice(@RequestBody PriceDto price) {
		PriceEntity updatedPrice = priceService.updatePrice(new PriceEntity(price));

		return new ResponseEntity<>(new PriceDto(updatedPrice), HttpStatusCode.valueOf(200));
	}

}

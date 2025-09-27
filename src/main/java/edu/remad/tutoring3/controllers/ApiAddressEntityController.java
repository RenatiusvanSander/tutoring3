package edu.remad.tutoring3.controllers;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import edu.remad.tutoring3.dto.AddressDto;
import edu.remad.tutoring3.persistence.models.AddressEntity;
import edu.remad.tutoring3.persistence.models.UserEntity;
import edu.remad.tutoring3.services.AddressEntityService;
import edu.remad.tutoring3.services.UserEntityService;

/**
 * Controls API REST Endpoints for user's addresses
 * 
 * @author edu.remad
 * @since 2025
 */
@RequestMapping("/api/addresses")
@RestController
public class ApiAddressEntityController {

	/** address service for {@link AddressEntity} actions */
	private final AddressEntityService addressService;

	/** user service for action concerning {@link UserEntity} */
	private final UserEntityService userService;

	/**
	 * Constructor
	 * 
	 * @param addressEntityService {@link AddressEntityService}
	 * @param userService          {@link UserEntityService}
	 */
	public ApiAddressEntityController(AddressEntityService addressEntityService, UserEntityService userService) {
		addressService = addressEntityService;
		this.userService = userService;
	}

	/**
	 * Gets addresses by user identifier
	 * 
	 * @param userId user's identifier
	 * @return list of {@link AddressDto}
	 */
	@GetMapping("/get-addresses/by-user-id/{userId}")
	public ResponseEntity<List<AddressDto>> getAddressByUserId(@PathVariable("userId") Long userId) {
		List<AddressEntity> addresses = addressService.findAddressesByUserId(userId);
		List<AddressDto> addressesDtos = addresses.stream().map(address -> new AddressDto(address))
				.collect(Collectors.toList());

		return new ResponseEntity<>(addressesDtos, HttpStatusCode.valueOf(200));
	}

	/**
	 * Saves address entity
	 * 
	 * @param addressDto {@link AddressDto}
	 * @return json-encoded {@link AddressDto}{}
	 */
	@PostMapping("/save-address")
	public ResponseEntity<AddressDto> saveAddressEntity(@RequestBody AddressDto addressDto) {
		UserEntity userFoundByuserId = userService.getUserEntityById(addressDto.getUserId());
		AddressEntity address = new AddressEntity(addressDto);
		address.setUserEntity(userFoundByuserId);
		address.setCreationDate(LocalDateTime.now());

		AddressEntity savedAddress = addressService.saveAddress(address);
		AddressDto savedAddressDto = new AddressDto(savedAddress);

		return new ResponseEntity<>(savedAddressDto, HttpStatusCode.valueOf(201));
	}

	/**
	 * Updates an address
	 * 
	 * @param addressDto {@link AddressDto}
	 * @return json-encoded {@link updatedAddressDto}
	 */
	@PutMapping("/update-an-address")
	public ResponseEntity<AddressDto> updateAnAddress(@RequestBody AddressDto addressDto) {
		AddressEntity loadedAddress = addressService.findByAddressId(addressDto.getId());
		loadedAddress.setAddressZipCode(addressDto.getAddressZipCode());
		loadedAddress.setAddressHouseNo(addressDto.getAddressHouseNo());
		loadedAddress.setAddressStreet(addressDto.getAddressStreet());
		loadedAddress.setPlace(addressDto.getPlace());

		AddressEntity updatedAddress = addressService.saveAddress(loadedAddress);
		AddressDto updatedAddressDto = new AddressDto(updatedAddress);

		return new ResponseEntity<>(updatedAddressDto, HttpStatusCode.valueOf(200));
	}

	/**
	 * Gets address by its identifier
	 * 
	 * @param id address' id
	 * @return json-encoded {@link AddressDto}
	 */
	@GetMapping("/get-address/{id}")
	public ResponseEntity<AddressDto> getAddressById(@PathVariable("id") Long id) {
		AddressEntity address = addressService.findByAddressId(id);
		AddressDto addressDto = new AddressDto(address);

		return new ResponseEntity<>(addressDto, HttpStatusCode.valueOf(200));
	}

	/**
	 * Deletes address
	 * 
	 * @param id address' identifier as number
	 * @return json-encoded {@link AddressDto}
	 */
	@DeleteMapping("/delete-address-by-id/{id}")
	public ResponseEntity<AddressDto> deleteAddressById(@PathVariable("id") Long id) {
		AddressEntity bufferAddress = addressService.findByAddressId(id);
		addressService.deleteAddressById(id);
		AddressDto addressDto = new AddressDto(bufferAddress);

		return new ResponseEntity<>(addressDto, HttpStatusCode.valueOf(200));
	}
}

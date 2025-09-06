package edu.remad.tutoring3.services.impl;

import org.springframework.stereotype.Service;

import edu.remad.tutoring3.repositories.AddressEntityRepository;
import edu.remad.tutoring3.services.AddressEntityService;

/**
 * Serves action on addresses
 * 
 * @author edu.remad
 * @since 2025
 */
@Service
public class AddressEntityServiceImpl implements AddressEntityService {

	/**
	 * repository for addresses
	 */
	private final AddressEntityRepository addressRepository;
	
	/**
	 * Constructor
	 * 
	 * @param addressEntityRepository {@link AddressEntityRepository}
	 */
	public AddressEntityServiceImpl(AddressEntityRepository addressEntityRepository) {
		addressRepository = addressEntityRepository;
	}
}

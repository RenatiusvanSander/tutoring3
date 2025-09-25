package edu.remad.tutoring3.services;

import java.util.List;

import edu.remad.tutoring3.persistence.models.AddressEntity;

/**
 * Defines methods to read, delete, update and persist an {@link AddressEntity}
 * 
 * @author edu.remad
 * @since 2025
 */
public interface AddressEntityService {

	/**
	 * Finds address by id
	 * 
	 * @param id address' id
	 * @return {@link AddressEntity}
	 */
	AddressEntity findByAddressId(Long id);
	
	/**
	 * Saves address
	 * 
	 * @param address address
	 * @return saved {@link AddressEntity}
	 */
	AddressEntity saveAddress(AddressEntity address);
	
	/**
	 * Finds addresses by ids
	 * 
	 * @param ids list of address ids
	 * @return List of {@link AddressEntity}
	 */
	List<AddressEntity> findByAdressIds(List<Long> ids);

	/**
	 * Patches address
	 * 
	 * @param address {@link AddressEntity} to patch
	 * @return {@link AddressEntity}
	 */
	AddressEntity patchAddress(AddressEntity address);
	
	/**
	 * Finds addresses by user id
	 * 
	 * @param userId the user Identifier
	 * @return a list of {@link AddressEntity}
	 */
	List<AddressEntity> findAddressesByUserId(Long userId);

	/**
	 * Deletes address by id
	 * 
	 * @param id address' identifier as number
	 */
	void deleteAddressById(Long id);
	
}

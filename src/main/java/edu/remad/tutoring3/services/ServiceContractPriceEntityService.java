package edu.remad.tutoring3.services;

import java.util.List;

import edu.remad.tutoring3.persistence.models.ServiceContractEntity;
import edu.remad.tutoring3.persistence.models.ServiceContractPriceEntity;
import edu.remad.tutoring3.persistence.models.UserEntity;

/**
 * Defines methods to read, delete, update and persist an {@link ServiceContractPriceEntity}
 * 
 * @author edu.remad
 * @since 2025
 */
public interface ServiceContractPriceEntityService {
	
	/**
	 * Saves service contract price
	 * 
	 * @param serviceContractPrice {@link ServiceContractPriceEntity}
	 * @return {@link ServiceContractPriceEntity}
	 */
	ServiceContractPriceEntity saveServiceContractPrice(ServiceContractPriceEntity serviceContractPrice);
	
	/**
	 * Gets User's service contract prices
	 * @param usersId user's identifier
	 * @return list of {@link ServiceContractPriceEntity}
	 */
	List<ServiceContractPriceEntity> getUsersServiceContractPrices(Long usersId);
	
	/**
	 * Updates service contract price
	 * 
	 * @param serviceContractPrice {@link ServiceContractPriceEntity}
	 * @return {@link ServiceContractPriceEntity}
	 */
	ServiceContractPriceEntity updateServiceContractPrice(ServiceContractPriceEntity serviceContractPrice);

	/**
	 * Gets service contract price
	 * 
	 * @param id service contract prrice's identifier
	 * @return {@link ServiceContractPriceEntity}
	 */
	ServiceContractPriceEntity getServiceContractPrice(Long id);

	/**
	 * Finds not confirmed {@link ServiceContractPriceEntity}
	 * 
	 * @return list of {@link ServiceContractPriceEntity}
	 */
	List<ServiceContractPriceEntity> findNotConfirmedServiceContractPrices();
	
	/**
	 * Finds {@link ServiceContractPriceEntity} by user's id and service contract's id
	 * 
	 * @param userId {@link UserEntity}
	 * @param serviceContractId {@link ServiceContractEntity}
	 * @return {@link ServiceContractPriceEntity}
	 */
	ServiceContractPriceEntity findByUserIdAndServiceContractId(UserEntity userId, ServiceContractEntity serviceContractId);

}

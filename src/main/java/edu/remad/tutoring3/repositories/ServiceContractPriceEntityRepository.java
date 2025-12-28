package edu.remad.tutoring3.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import edu.remad.tutoring3.persistence.models.ServiceContractEntity;
import edu.remad.tutoring3.persistence.models.ServiceContractPriceEntity;
import edu.remad.tutoring3.persistence.models.UserEntity;

/**
 * repo for service contract prices
 * 
 * @author edu.remad
 * @since 2025
 */
public interface ServiceContractPriceEntityRepository extends JpaRepository<ServiceContractPriceEntity, Long> {

	/**
	 * Finds {@link ServiceContractPriceEntity} via user's identifier
	 * 
	 * @param userId
	 * @return list {@link ServiceContractPriceEntity}
	 */
	List<ServiceContractPriceEntity> findByUserId_UserId(Long userId);
	
	/**
	 * Finds not confirmed {@link ServiceContractPriceEntity}s
	 * 
	 * @return list {@link ServiceContractPriceEntity}
	 */
	List<ServiceContractPriceEntity> findByConfirmedFalse();
	
	/**
	 * Finds {@link ServiceContractPriceEntity} by user's id and service contract's id
	 * 
	 * @param userId {@link UserEntity}
	 * @param serviceContractId {@link ServiceContractEntity}
	 * @return {@link ServiceContractPriceEntity}
	 */
	ServiceContractPriceEntity findByUserIdAndServiceContractId(UserEntity userId, ServiceContractEntity serviceContractId);
	
	/**
	 * Finds {@link ServiceContractPriceEntity} by user's id and service contract's id
	 * 
	 * @param userId user's id
	 * @param serviceContractId service contract's id
	 * @return {@link ServiceContractPriceEntity}
	 */
	ServiceContractPriceEntity findByUserId_UserIdAndServiceContractId_serviceContractNo(long userId, long serviceContractId);
}

package edu.remad.tutoring3.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import edu.remad.tutoring3.persistence.models.ServiceContractPriceEntity;

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
	 * @return
	 */
	List<ServiceContractPriceEntity> findByUserId_UserId(Long userId);
	
	/**
	 * Finds not confirmed {@link ServiceContractPriceEntity}s
	 * 
	 * @return list {@link ServiceContractPriceEntity}
	 */
	List<ServiceContractPriceEntity> findByConfirmedFalse();
}

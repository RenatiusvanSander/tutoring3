package edu.remad.tutoring3.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import edu.remad.tutoring3.persistence.models.ServiceContractEntity;

/**
 * repo for service contracts
 * 
 * @author edu.remad
 * @since 2025
 */
public interface ServiceContractEntityRepository extends JpaRepository<ServiceContractEntity, Long> {

}

package edu.remad.tutoring3.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import edu.remad.tutoring3.model.AddressEntity;

/**
 * Repo for addresses
 * 
 * @author edu.remad
 * @since 2025
 */
@Repository
public interface AddressEntityRepository extends JpaRepository<AddressEntity, Long> {

}

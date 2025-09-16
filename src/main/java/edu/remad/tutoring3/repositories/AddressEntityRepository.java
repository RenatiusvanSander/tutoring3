package edu.remad.tutoring3.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import edu.remad.tutoring3.persistence.models.AddressEntity;
import edu.remad.tutoring3.persistence.models.UserEntity;

/**
 * Repo for addresses
 * 
 * @author edu.remad
 * @since 2025
 */
@Repository
public interface AddressEntityRepository extends JpaRepository<AddressEntity, Long> {

	/**
	 * Finds {@link AddressEntity} by userId
	 * 
	 * @param user {@link UserEntity}
	 * @return list of {@link AddressEntity}
	 */
	List<AddressEntity> findByUser(UserEntity user);
}

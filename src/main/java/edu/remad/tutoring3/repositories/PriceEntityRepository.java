package edu.remad.tutoring3.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import edu.remad.tutoring3.persistence.models.PriceEntity;

/**
 * Repo for {@link PriceEntity}
 * 
 * @author edu.remad
 * @since 2025
 */
@Repository
public interface PriceEntityRepository extends JpaRepository<PriceEntity, Long> {

}

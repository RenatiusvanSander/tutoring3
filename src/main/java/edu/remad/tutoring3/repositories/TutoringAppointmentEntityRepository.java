package edu.remad.tutoring3.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import edu.remad.tutoring3.persistence.models.TutoringAppointmentEntity;

/**
 * Repo for {@link TutoringAppointmentEntity}
 * 
 * @author edu.remad
 * @since 2025
 */
@Repository
public interface TutoringAppointmentEntityRepository extends JpaRepository<TutoringAppointmentEntity, Long> {

	/**
	 * Finds tutoring appointments by user id
	 * 
	 * @param userId user's identifier
	 * @return list of {@link TutoringAppointmentEntity}
	 */
	List<TutoringAppointmentEntity> findByTutoringAppointmentUser_UserId(Long userId);
}

package edu.remad.tutoring3.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import edu.remad.tutoring3.persistence.models.TutoringAppointmentEntity;

/**
 * Repo for TutoringAppointment
 * 
 * @author edu.remad
 * @since 2025
 */
@Repository
public interface TutoringAppointmentEntityRepository extends JpaRepository<TutoringAppointmentEntity, Long> {

	/**
	 * Saves a Tutoring Appointment
	 * 
	 * @param appointment tutoring appointment to save, persisting on data base
	 * @return saved {@link TutoringAppointmentEntity}
	 */
	TutoringAppointmentEntity saveTutoringApointment(TutoringAppointmentEntity appointment);
}

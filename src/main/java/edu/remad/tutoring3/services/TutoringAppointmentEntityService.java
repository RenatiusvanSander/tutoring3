package edu.remad.tutoring3.services;

import java.util.List;

import edu.remad.tutoring3.persistence.models.TutoringAppointmentEntity;

/**
 * Defines methods for implementation of this interface
 * 
 * @author edu.remad
 * @since 2025
 */
public interface TutoringAppointmentEntityService {

	/**
	 * Saves a Tutoring Appointment
	 * 
	 * @param appointment tutoring appointment to save, persisting on data base
	 * @return saved {@link TutoringAppointmentEntity}
	 */
	TutoringAppointmentEntity saveTutoringApointment(TutoringAppointmentEntity appointment);
	
	/**
	 * Loads tutoring appointment
	 * 
	 * @param id Tutoring appontment's identifier to load appointment
	 * @return loaded {@link TutoringAppointmentEntity}
	 */
	TutoringAppointmentEntity loadTutoringApointment(Long id);

	/**
	 * Loads tutoring appointment by a list of identifiers
	 * 
	 * @param ids identifiers as number
	 * @return collection of {@link TutoringAppointmentEntity}
	 */
	List<TutoringAppointmentEntity> loadTutoringApointmentByIds(List<Long> ids);

	/**
	 * Loads tutoring appointments by user's identifier
	 * 
	 * @param userId user' identifier to load his appointments
	 */
	List<TutoringAppointmentEntity> loadTutoringApointmentByUserId(Long userId);
	
	/**
	 * Updates single {@link TutoringAppointmentEntity}.
	 * 
	 * @param appointmentToUpdate {@link TutoringAppointment} with update values
	 * @return updated {@link TutoringAppointment}
	 */
	TutoringAppointmentEntity updateSingleTutoringAppointment(TutoringAppointmentEntity appointmentToUpdate);
	
}

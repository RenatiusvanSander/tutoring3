package edu.remad.tutoring3.services;

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
}

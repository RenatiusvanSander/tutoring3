package edu.remad.tutoring3.services.impl;

import edu.remad.tutoring3.persistence.models.TutoringAppointmentEntity;
import edu.remad.tutoring3.repositories.TutoringAppointmentEntityRepository;
import edu.remad.tutoring3.services.TutoringAppointmentEntityService;

/**
 * Serves to load, save, get, put, patch, delete a tutoring appointment
 * 
 * @author edu.remad
 * @since 2025
 */
public class TutoringAppointmentEntityServiceImpl implements TutoringAppointmentEntityService {

	private final TutoringAppointmentEntityRepository tutoringAppointmentEntityRepository;
	
	/**
	 * Constructor
	 * 
	 * @param appointmentRepository {@link TutoringAppointmentEntityRepository}
	 */
	public TutoringAppointmentEntityServiceImpl(TutoringAppointmentEntityRepository appointmentRepository) {
		tutoringAppointmentEntityRepository = appointmentRepository;
	}
	
	@Override
	public TutoringAppointmentEntity saveTutoringApointment(TutoringAppointmentEntity appointment) {
		return tutoringAppointmentEntityRepository.save(appointment);
	}

	@Override
	public TutoringAppointmentEntity loadTutoringApointment(Long id) {
		return tutoringAppointmentEntityRepository.findById(id).get();
	}

}

package edu.remad.tutoring3.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import edu.remad.tutoring3.persistence.models.TutoringAppointmentEntity;
import edu.remad.tutoring3.repositories.TutoringAppointmentEntityRepository;
import edu.remad.tutoring3.services.TutoringAppointmentEntityService;

/**
 * Serves to load, save, get, put, patch, delete a tutoring appointment
 * 
 * @author edu.remad
 * @since 2025
 */
@Service
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
		Optional<TutoringAppointmentEntity> optional = tutoringAppointmentEntityRepository.findById(id);
		
		return optional.get();
	}

	@Override
	public List<TutoringAppointmentEntity> loadTutoringApointmentByIds(List<Long> ids) {
		return tutoringAppointmentEntityRepository.findAllById(ids);
	}

	@Override
	public List<TutoringAppointmentEntity> loadTutoringApointmentByUserId(Long userId) {
		return tutoringAppointmentEntityRepository.findByTutoringAppointmentUser_UserId(userId);
	}

	@Override
	public TutoringAppointmentEntity updateSingleTutoringAppointment(TutoringAppointmentEntity appointmentToUpdate) {
		return tutoringAppointmentEntityRepository.save(appointmentToUpdate);
	}

	@Override
	public List<TutoringAppointmentEntity> updateMultipleTutoringAppointments(List<TutoringAppointmentEntity> updatedAppointments) {
		return tutoringAppointmentEntityRepository.saveAll(updatedAppointments);
	}

}

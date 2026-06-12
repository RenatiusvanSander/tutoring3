package edu.remad.tutoring3.services.impl;

import edu.remad.tutoring3.persistence.models.TutoringAppointmentEntity;
import edu.remad.tutoring3.persistence.models.UserEntity;
import edu.remad.tutoring3.repositories.TutoringAppointmentEntityRepository;
import edu.remad.tutoring3.services.AllowTutoringAppointmentEntityCreation;
import edu.remad.tutoring3.services.AllowTutoringAppointmentEntityCreationException;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.WeekFields;
import java.util.List;
import java.util.Locale;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service that checks whether a given {@link TutoringAppointmentEntity} may be
 * created according to business rules: - Maximum 2 appointments per day -
 * Maximum total of 10 hours per calendar week (existing appointments +
 * candidate)
 *
 * This implementation is transactional and logs unexpected errors at ERROR
 * level.
 * 
 * @author edu.remad
 * @since 2026
 */
@Service
@Transactional
public class AllowTutoringAppointmentEntityCreationImpl implements AllowTutoringAppointmentEntityCreation {

	private static final Logger LOG = LoggerFactory.getLogger(AllowTutoringAppointmentEntityCreationImpl.class);
	private static final int MAX_APPOINTMENTS_PER_DAY = 2; // maximum 2 tutoring appointments per day
	private static final long MAX_WEEK_MINUTES = 10L * 60L; // 10 hours in minutes
	
	private final TutoringAppointmentEntityRepository repository;

	/**
	 * Constructor.
	 *
	 * @param repository repository used to fetch existing appointments
	 */
	public AllowTutoringAppointmentEntityCreationImpl(TutoringAppointmentEntityRepository repository) {
		this.repository = repository;
	}

	/**
	 * Checks whether the provided {@code appointment} may be created according to
	 * the rules.
	 *
	 * @param appointment candidate appointment (not persisted)
	 * @return {@code true} when creation is allowed, {@code false} when a business
	 *         rule is violated
	 * @throws AllowTutoringAppointmentEntityCreationException on unexpected errors
	 *                                                         or invalid input
	 */
	@Override
	public boolean isCreationAllowed(TutoringAppointmentEntity appointment) {
		validateAppointment(appointment);

		try {
			UserEntity user = appointment.getTutoringAppointmentUser();
			LocalDateTime candidateStart = appointment.getTutoringAppointmentStartDateTime();

			// Day check
			List<TutoringAppointmentEntity> sameDay = findAppointmentsForDay(user, candidateStart.toLocalDate());
			if (isDayLimitExceeded(sameDay)) {
				return false;
			}

			// Week check
			List<TutoringAppointmentEntity> sameWeek = findAppointmentsForWeek(user, candidateStart);
			long existingMinutes = calculateTotalMinutes(sameWeek);
			long candidateMinutes = mapDurationToLong(appointment);

			return !isWeekLimitExceeded(existingMinutes, candidateMinutes);

		} catch (AllowTutoringAppointmentEntityCreationException ex) {
			// rethrow our own exceptions unchanged
			throw ex;
		} catch (Exception ex) {
			// Unexpected error -> log ERROR and wrap in custom runtime exception
			LOG.error("Error while checking creation allowance", ex);
			throw new AllowTutoringAppointmentEntityCreationException(
					"Error checking tutoring appointment creation allowance", ex);
		}
	}

	/**
	 * Validates the basic invariants of the appointment and throws a descriptive
	 * exception when invalid.
	 *
	 * @param appointment candidate appointment
	 * @throws AllowTutoringAppointmentEntityCreationException if appointment is
	 *                                                         null, missing user,
	 *                                                         or has invalid times
	 */
	private void validateAppointment(TutoringAppointmentEntity appointment) {
		if (appointment == null) {
			LOG.error("TutoringAppointmentEntity must not be null");
			throw new AllowTutoringAppointmentEntityCreationException("TutoringAppointmentEntity must not be null");
		}

		UserEntity user = appointment.getTutoringAppointmentUser();
		if (user == null) {
			LOG.error("TutoringAppointmentEntity must have an assigned user");
			throw new AllowTutoringAppointmentEntityCreationException(
					"TutoringAppointmentEntity must have an assigned user");
		}

		LocalDateTime candidateStart = appointment.getTutoringAppointmentStartDateTime();
		LocalDateTime candidateEnd = appointment.getTutoringAppointmentEndDateTime();
		if (candidateStart == null || candidateEnd == null || !candidateEnd.isAfter(candidateStart)) {
			LOG.error("TutoringAppointmentEntity must have valid start and end times (end must be after start)");
			throw new AllowTutoringAppointmentEntityCreationException(
					"TutoringAppointmentEntity must have valid start and end times (end must be after start)");
		}
	}

	/**
	 * Finds all appointments for the same user that start on the provided day.
	 *
	 * @param user the appointment owner
	 * @param day  the local date to search for
	 * @return list of appointments starting on that day
	 */
	private List<TutoringAppointmentEntity> findAppointmentsForDay(UserEntity user, LocalDate day) {
		LocalDateTime dayStart = day.atStartOfDay();
		LocalDateTime dayEnd = day.plusDays(1).atStartOfDay().minusNanos(1);
		return repository.findAllByTutoringAppointmentUserAndTutoringAppointmentStartDateTimeBetween(user, dayStart,
				dayEnd);
	}

	/**
	 * Finds all appointments for the same user that start within the calendar week
	 * of the candidateStart. The week boundaries are determined using
	 * {@link WeekFields} with the default locale (can be changed to ISO if
	 * desired).
	 *
	 * @param user           the appointment owner
	 * @param candidateStart a date-time within the week to search
	 * @return list of appointments starting within the same week
	 */
	private List<TutoringAppointmentEntity> findAppointmentsForWeek(UserEntity user, LocalDateTime candidateStart) {
		WeekFields wf = WeekFields.of(Locale.getDefault());
		// compute week start and end using WeekFields
		LocalDate weekStartDate = candidateStart.toLocalDate().with(wf.dayOfWeek(), 1);
		LocalDateTime weekStart = weekStartDate.atStartOfDay();
		LocalDateTime weekEnd = weekStart.plusDays(7).minusNanos(1);
		return repository.findAllByTutoringAppointmentUserAndTutoringAppointmentStartDateTimeBetween(user, weekStart,
				weekEnd);
	}

	/**
	 * Returns true if the number of appointments for the day is already at or above
	 * the configured limit.
	 *
	 * @param appointments appointments on the same day
	 * @return {@code true} when the day limit is exceeded or reached, {@code false}
	 *         otherwise
	 */
	private boolean isDayLimitExceeded(List<TutoringAppointmentEntity> appointments) {
		return appointments != null && appointments.size() >= MAX_APPOINTMENTS_PER_DAY;
	}

	/**
	 * Calculates the sum of durations (in minutes) for the given appointments.
	 *
	 * @param appointments list of appointments
	 * @return total duration in minutes
	 */
	private long calculateTotalMinutes(List<TutoringAppointmentEntity> appointments) {
		if (appointments == null || appointments.isEmpty()) {
			return 0L;
		}
		return appointments.stream().mapToLong(this::mapDurationToLong).sum();
	}

	/**
	 * Returns true when the weekly limit would be exceeded by adding the candidate
	 * duration to existing minutes.
	 *
	 * @param existingMinutes  sum of existing appointment minutes in the week
	 * @param candidateMinutes candidate appointment minutes
	 * @return {@code true} when the limit would be exceeded, {@code false}
	 *         otherwise
	 */
	private boolean isWeekLimitExceeded(long existingMinutes, long candidateMinutes) {
		long totalMinutes = existingMinutes + candidateMinutes;
		return totalMinutes > MAX_WEEK_MINUTES;
	}

	/**
	 * Maps a single appointment to its duration in minutes. Invalid or malformed
	 * appointments produce 0.
	 *
	 * @param a appointment
	 * @return duration in minutes, or 0 if start/end are invalid
	 */
	private long mapDurationToLong(TutoringAppointmentEntity a) {
		LocalDateTime s = a.getTutoringAppointmentStartDateTime();
		LocalDateTime e = a.getTutoringAppointmentEndDateTime();
		if (s == null || e == null || !e.isAfter(s)) {
			return 0L;
		}
		return Duration.between(s, e).toMinutes();
	}
}

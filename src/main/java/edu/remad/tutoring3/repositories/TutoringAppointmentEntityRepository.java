package edu.remad.tutoring3.repositories;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import edu.remad.tutoring3.persistence.models.TutoringAppointmentEntity;
import edu.remad.tutoring3.persistence.models.UserEntity;

/**
 * Repository for {@link TutoringAppointmentEntity}.
 *
 * Provides query methods to find appointments by user id, unfinished appointments,
 * and to retrieve appointments for a user within a specific date/time range
 * (useful for day/week queries).
 *
 * @author edu.remad
 * @since 2025
 */
@Repository
public interface TutoringAppointmentEntityRepository extends JpaRepository<TutoringAppointmentEntity, Long> {

	/**
	 * Finds tutoring appointments by the user's identifier.
	 *
	 * @param userId the user's identifier
	 * @return list of {@link TutoringAppointmentEntity} belonging to the user; never null
	 */
	List<TutoringAppointmentEntity> findByTutoringAppointmentUser_UserId(Long userId);

	/**
	 * Finds tutoring appointments that are not yet accomplished.
	 *
	 * @return list of {@link TutoringAppointmentEntity} where {@code isAccomplished == false}; never null
	 */
	List<TutoringAppointmentEntity> findByisAccomplishedFalse();

	/**
	 * Finds all appointments for the given user whose start date/time falls between the provided start and end.
	 *
	 * The time range is intended to be used for inclusive searches (day/week ranges). Callers should
	 * provide appropriate start and end boundaries (e.g. start.atStartOfDay() / nextDay.atStartOfDay().minusNanos(1))
	 * if inclusive behavior is required.
	 *
	 * @param user  the user entity owning the appointments
	 * @param start the inclusive lower bound of the appointment start date/time
	 * @param end   the inclusive upper bound of the appointment start date/time
	 * @return list of matching {@link TutoringAppointmentEntity}; never null
	 */
	List<TutoringAppointmentEntity> findAllByTutoringAppointmentUserAndTutoringAppointmentStartDateTimeBetween(
			UserEntity user, LocalDateTime start, LocalDateTime end);

	/**
	 * Finds all appointments for the given user whose end date/time falls between the provided start and end.
	 *
	 * This is useful when callers want to query based on appointment end times instead of start times.
	 *
	 * @param user  the user entity owning the appointments
	 * @param start the inclusive lower bound of the appointment end date/time
	 * @param end   the inclusive upper bound of the appointment end date/time
	 * @return list of matching {@link TutoringAppointmentEntity}; never null
	 */
	List<TutoringAppointmentEntity> findAllByTutoringAppointmentUserAndTutoringAppointmentEndDateTimeBetween(
			UserEntity user, LocalDateTime start, LocalDateTime end);
}

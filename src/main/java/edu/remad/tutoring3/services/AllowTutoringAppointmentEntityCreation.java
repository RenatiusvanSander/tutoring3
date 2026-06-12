package edu.remad.tutoring3.services;

import edu.remad.tutoring3.persistence.models.TutoringAppointmentEntity;

/**
 * Contract for a service that validates whether a
 * {@link TutoringAppointmentEntity} may be created according to business rules.
 *
 * <p>
 * Typical rules enforced by implementations:
 * <ul>
 * <li>Maximum number of appointments per day</li>
 * <li>Maximum total duration per calendar week (existing appointments +
 * candidate)</li>
 * </ul>
 *
 * <p>
 * Implementations are expected to consult the persistence layer (repositories)
 * to evaluate existing appointments. Implementations may be transactional and
 * should log unexpected errors. Validation failures due to invalid input or
 * unexpected runtime conditions are signaled by throwing
 * {@link AllowTutoringAppointmentEntityCreationException}.
 *
 * @author edu.remad
 * @since 2025
 */
public interface AllowTutoringAppointmentEntityCreation {

	/**
	 * Checks whether the provided candidate appointment may be created according to
	 * the configured business rules.
	 *
	 * <p>
	 * The {@code appointment} parameter represents a candidate that is not yet
	 * persisted. The method should evaluate existing appointments for the same user
	 * (for example, by querying appointments on the same day and within the same
	 * calendar week) and determine whether creating the candidate would violate any
	 * rules.
	 *
	 * @param appointment the candidate appointment to validate (not persisted)
	 * @return {@code true} when creation is allowed; {@code false} when a business
	 *         rule is violated
	 * @throws AllowTutoringAppointmentEntityCreationException when the input is
	 *                                                         invalid or when an
	 *                                                         unexpected error
	 *                                                         occurs while
	 *                                                         evaluating the rules
	 */
	boolean isCreationAllowed(TutoringAppointmentEntity appointment);

}

package edu.remad.tutoring3.services;

/**
 * Runtime exception thrown when an unexpected error occurs or when invalid
 * input prevents evaluation of whether a {@code TutoringAppointmentEntity} may
 * be created.
 *
 * <p>
 * This is an unchecked exception used by the
 * {@link AllowTutoringAppointmentEntityCreation} service contract to signal
 * validation problems or failures during rule evaluation (for example,
 * repository access errors). Callers may catch this exception when they want to
 * map it to a specific HTTP response or other error handling policy.
 *
 * @author edu.remad
 * @since 2026
 */
public class AllowTutoringAppointmentEntityCreationException extends RuntimeException {

	/**
	 * generated serial version UID for serialization compatibility. This is a best
	 * practice for all Serializable classes, including exceptions, to ensure that
	 * deserialization works correctly even if the class definition changes in the
	 * future.
	 */
	private static final long serialVersionUID = 3417601607953161050L;

	/**
	 * Constructs a new exception with the specified detail message.
	 *
	 * @param message the detail message (which is saved for later retrieval by the
	 *                {@link #getMessage()} method)
	 */
	public AllowTutoringAppointmentEntityCreationException(String message) {
		super(message);
	}

	/**
	 * Constructs a new exception with the specified detail message and cause.
	 *
	 * @param message the detail message (which is saved for later retrieval by the
	 *                {@link #getMessage()} method)
	 * @param cause   the cause (which is saved for later retrieval by the
	 *                {@link #getCause()} method). (A null value is permitted, and
	 *                indicates that the cause is nonexistent or unknown.)
	 */
	public AllowTutoringAppointmentEntityCreationException(String message, Throwable cause) {
		super(message, cause);
	}
}
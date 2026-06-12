package edu.remad.tutoring3.services.impl;

import edu.remad.tutoring3.persistence.models.TutoringAppointmentEntity;
import edu.remad.tutoring3.persistence.models.UserEntity;
import edu.remad.tutoring3.repositories.TutoringAppointmentEntityRepository;
import edu.remad.tutoring3.services.AllowTutoringAppointmentEntityCreationException;

import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Unit tests for {@link AllowTutoringAppointmentEntityCreationImpl}
 * 
 * @author edu.remad
 * @since 2026
 */
@ExtendWith(MockitoExtension.class)
class AllowTutoringAppointmentEntityCreationImplTest {

	@Mock
	private TutoringAppointmentEntityRepository repository;

	private AllowTutoringAppointmentEntityCreationImpl service;

	private UserEntity user;

	@BeforeEach
	void setUp() {
		service = new AllowTutoringAppointmentEntityCreationImpl(repository);
		user = new UserEntity();
		user.setUserId(1L);
	}

	private TutoringAppointmentEntity buildAppointment(LocalDateTime start, LocalDateTime end) {
		TutoringAppointmentEntity a = new TutoringAppointmentEntity();
		a.setTutoringAppointmentUser(user);
		a.setTutoringAppointmentStartDateTime(start);
		a.setTutoringAppointmentEndDateTime(end);
		return a;
	}

	@Test
	void returnsTrue_whenDayUnderLimitAndWeekUnderLimit() {
		LocalDateTime start = LocalDateTime.of(2026, 6, 10, 10, 0);
		LocalDateTime end = start.plusHours(1);

		TutoringAppointmentEntity candidate = buildAppointment(start, end);

		// An existing appointment on the same day (1 existing -> allowed)
		TutoringAppointmentEntity existing = buildAppointment(start.withHour(8), start.withHour(9));

		when(repository.findAllByTutoringAppointmentUserAndTutoringAppointmentStartDateTimeBetween(eq(user),
				any(LocalDateTime.class), any(LocalDateTime.class))).thenReturn(List.of(existing));

		boolean allowed = service.isCreationAllowed(candidate);

		assertTrue(allowed);
	}

	@Test
	void returnsFalse_whenDayLimitExceeded() {
		LocalDateTime start = LocalDateTime.of(2026, 6, 10, 10, 0);
		LocalDateTime end = start.plusHours(1);

		TutoringAppointmentEntity candidate = buildAppointment(start, end);

		// Two existing appointments on the same day -> candidate would be the third ->
		// false
		TutoringAppointmentEntity e1 = buildAppointment(start.withHour(8), start.withHour(9));
		TutoringAppointmentEntity e2 = buildAppointment(start.withHour(9), start.withHour(10));

		when(repository.findAllByTutoringAppointmentUserAndTutoringAppointmentStartDateTimeBetween(eq(user),
				any(LocalDateTime.class), any(LocalDateTime.class))).thenReturn(List.of(e1, e2));

		boolean allowed = service.isCreationAllowed(candidate);

		assertFalse(allowed);
	}

	@Test
	void returnsFalse_whenWeekHoursExceeded() {
		LocalDateTime monday9 = LocalDateTime.of(2026, 6, 8, 9, 0);
		LocalDateTime monday12 = monday9.plusHours(3); // 3h
		LocalDateTime tue9 = LocalDateTime.of(2026, 6, 9, 9, 0);
		LocalDateTime tue18 = tue9.plusHours(6); // 6h
		// existing total = 9h
		TutoringAppointmentEntity e1 = buildAppointment(monday9, monday12);
		TutoringAppointmentEntity e2 = buildAppointment(tue9, tue18);

		// candidate 2h -> total 11h > 10h -> false
		LocalDateTime candidateStart = LocalDateTime.of(2026, 6, 10, 10, 0);
		LocalDateTime candidateEnd = candidateStart.plusHours(2);
		TutoringAppointmentEntity candidate = buildAppointment(candidateStart, candidateEnd);

		when(repository.findAllByTutoringAppointmentUserAndTutoringAppointmentStartDateTimeBetween(eq(user),
				any(LocalDateTime.class), any(LocalDateTime.class))).thenReturn(List.of(e1, e2));

		boolean allowed = service.isCreationAllowed(candidate);

		assertFalse(allowed);
	}

	@Test
	void throwsCustomException_whenRepositoryThrows() {
		LocalDateTime start = LocalDateTime.of(2026, 6, 10, 10, 0);
		LocalDateTime end = start.plusHours(1);
		TutoringAppointmentEntity candidate = buildAppointment(start, end);

		when(repository.findAllByTutoringAppointmentUserAndTutoringAppointmentStartDateTimeBetween(eq(user),
				any(LocalDateTime.class), any(LocalDateTime.class))).thenThrow(new RuntimeException("DB error"));

		assertThrows(AllowTutoringAppointmentEntityCreationException.class, () -> service.isCreationAllowed(candidate));
	}
}

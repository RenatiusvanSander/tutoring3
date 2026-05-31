package edu.remad.tutoring3.services.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import edu.remad.tutoring3.persistence.models.TutoringAppointmentEntity;
import edu.remad.tutoring3.repositories.TutoringAppointmentEntityRepository;

@ExtendWith(MockitoExtension.class)
class TutoringAppointmentEntityServiceImplTest {

    @Mock
    private TutoringAppointmentEntityRepository repo;

    @InjectMocks
    private TutoringAppointmentEntityServiceImpl service;

    @Test
    void saveTutoringAppointment_delegates() {
        TutoringAppointmentEntity a = mock(TutoringAppointmentEntity.class);
        when(repo.save(a)).thenReturn(a);

        TutoringAppointmentEntity saved = service.saveTutoringApointment(a);

        assertSame(a, saved);
        verify(repo).save(a);
    }

    @Test
    void loadTutoringAppointment_found() {
        TutoringAppointmentEntity a = mock(TutoringAppointmentEntity.class);
        when(repo.findById(9L)).thenReturn(Optional.of(a));

        TutoringAppointmentEntity loaded = service.loadTutoringApointment(9L);

        assertSame(a, loaded);
    }

    @Test
    void loadByIds_returnsList() {
        TutoringAppointmentEntity a = mock(TutoringAppointmentEntity.class);
        TutoringAppointmentEntity b = mock(TutoringAppointmentEntity.class);
        when(repo.findAllById(Arrays.asList(1L,2L))).thenReturn(Arrays.asList(a,b));

        List<TutoringAppointmentEntity> result = service.loadTutoringApointmentByIds(Arrays.asList(1L,2L));

        assertEquals(2, result.size());
    }
}

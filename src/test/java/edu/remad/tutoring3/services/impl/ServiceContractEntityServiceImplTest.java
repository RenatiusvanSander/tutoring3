package edu.remad.tutoring3.services.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import edu.remad.tutoring3.persistence.models.ServiceContractEntity;
import edu.remad.tutoring3.repositories.ServiceContractEntityRepository;

@ExtendWith(MockitoExtension.class)
class ServiceContractEntityServiceImplTest {

    @Mock
    private ServiceContractEntityRepository repo;

    @InjectMocks
    private ServiceContractEntityServiceImpl service;

    @Test
    void createServiceContract_saves() {
        ServiceContractEntity sc = mock(ServiceContractEntity.class);
        when(repo.save(sc)).thenReturn(sc);

        ServiceContractEntity saved = service.createServiceContract(sc);

        assertSame(sc, saved);
        verify(repo).save(sc);
    }

    @Test
    void getMultipleServiceContracts_returnsList() {
        ServiceContractEntity a = mock(ServiceContractEntity.class);
        ServiceContractEntity b = mock(ServiceContractEntity.class);
        List<Long> ids = Arrays.asList(1L,2L);
        when(repo.findAllById(ids)).thenReturn(Arrays.asList(a,b));

        List<ServiceContractEntity> result = service.getMultipleServiceContracts(ids);

        assertEquals(2, result.size());
    }
}

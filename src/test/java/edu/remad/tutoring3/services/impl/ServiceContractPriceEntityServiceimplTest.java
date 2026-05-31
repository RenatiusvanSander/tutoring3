package edu.remad.tutoring3.services.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import edu.remad.tutoring3.persistence.models.ServiceContractPriceEntity;
import edu.remad.tutoring3.persistence.models.ServiceContractEntity;
import edu.remad.tutoring3.persistence.models.UserEntity;
import edu.remad.tutoring3.repositories.ServiceContractPriceEntityRepository;

@ExtendWith(MockitoExtension.class)
class ServiceContractPriceEntityServiceimplTest {

    @Mock
    private ServiceContractPriceEntityRepository repo;

    @InjectMocks
    private ServiceContractPriceEntityServiceimpl service;

    @Test
    void saveServiceContractPrice_delegates() {
        ServiceContractPriceEntity p = mock(ServiceContractPriceEntity.class);
        when(repo.save(p)).thenReturn(p);

        ServiceContractPriceEntity saved = service.saveServiceContractPrice(p);

        assertSame(p, saved);
        verify(repo).save(p);
    }

    @Test
    void findByUserAndServiceContract_delegates() {
        UserEntity user = mock(UserEntity.class);
        ServiceContractEntity sc = mock(ServiceContractEntity.class);
        when(repo.findByUserIdAndServiceContractId(user, sc)).thenReturn(null);

        ServiceContractPriceEntity result = service.findByUserIdAndServiceContractId(user, sc);

        assertNull(result);
    }
}

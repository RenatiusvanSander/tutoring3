package edu.remad.tutoring3.services.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import edu.remad.tutoring3.persistence.models.PriceEntity;
import edu.remad.tutoring3.repositories.PriceEntityRepository;

@ExtendWith(MockitoExtension.class)
class PriceEntityServiceImplTest {

    @Mock
    private PriceEntityRepository priceRepository;

    @InjectMocks
    private PriceEntityServiceImpl service;

    @Test
    void savePrice_setsCreationDateAndSaves() {
        PriceEntity price = mock(PriceEntity.class);
        when(priceRepository.save(price)).thenReturn(price);

        PriceEntity saved = service.savePrice(price);

        assertSame(price, saved);
        verify(priceRepository).save(price);
    }

    @Test
    void getPrice_delegatesToRepository() {
        PriceEntity p = mock(PriceEntity.class);
        when(priceRepository.findById(5L)).thenReturn(java.util.Optional.of(p));

        PriceEntity loaded = service.getPrice(5L);

        assertSame(p, loaded);
    }

    @Test
    void getReferencedPrice_delegates() {
        PriceEntity ref = mock(PriceEntity.class);
        when(priceRepository.getReferenceById(7L)).thenReturn(ref);

        PriceEntity result = service.getReferencedPrice(7L);

        assertSame(ref, result);
    }
}

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

import edu.remad.tutoring3.persistence.models.InvoiceEntity;
import edu.remad.tutoring3.repositories.InvoiceEntityRepository;

@ExtendWith(MockitoExtension.class)
class InvoiceEntityServiceImplTest {

    @Mock
    private InvoiceEntityRepository invoiceRepository;

    @InjectMocks
    private InvoiceEntityServiceImpl service;

    @Test
    void saveInvoice_delegates() {
        InvoiceEntity invoice = mock(InvoiceEntity.class);
        when(invoiceRepository.save(invoice)).thenReturn(invoice);

        InvoiceEntity saved = service.saveInvoice(invoice);

        assertSame(invoice, saved);
        verify(invoiceRepository).save(invoice);
    }

    @Test
    void loadInvoiceById_found() {
        InvoiceEntity invoice = mock(InvoiceEntity.class);
        when(invoiceRepository.findById(1L)).thenReturn(Optional.of(invoice));

        InvoiceEntity loaded = service.loadInvoiceById(1L);

        assertSame(invoice, loaded);
    }

    @Test
    void loadInvoicesByIds_returnsList() {
        InvoiceEntity i1 = mock(InvoiceEntity.class);
        InvoiceEntity i2 = mock(InvoiceEntity.class);

        when(invoiceRepository.findAllById(Arrays.asList(1L, 2L))).thenReturn(Arrays.asList(i1, i2));

        List<InvoiceEntity> result = service.loadInvoicesByIds(Arrays.asList(1L, 2L));

        assertEquals(2, result.size());
    }

    @Test
    void updateMultipleInvoices_delegates() {
        List<InvoiceEntity> updated = Arrays.asList(mock(InvoiceEntity.class));
        when(invoiceRepository.saveAllAndFlush(updated)).thenReturn(updated);

        List<InvoiceEntity> result = service.updateMultipleInvoices(updated);

        assertSame(updated, result);
        verify(invoiceRepository).saveAllAndFlush(updated);
    }
}

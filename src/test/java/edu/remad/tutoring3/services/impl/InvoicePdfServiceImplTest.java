package edu.remad.tutoring3.services.impl;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Optional;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import edu.remad.tutoring3.persistence.models.InvoiceEntity;
import edu.remad.tutoring3.repositories.InvoiceEntityRepository;
import edu.remad.tutoring3.services.InvoicePdfCreatorService;

/**
 * Unit tests for {@link InvoicePdfServiceImpl}
 * 
 * @author edu.remad
 * @since 2026
 */
@ExtendWith(MockitoExtension.class)
public class InvoicePdfServiceImplTest {

	private InvoicePdfServiceImpl serviceUnderTest;

	private InvoiceEntityRepository repository;

	private InvoicePdfCreatorService pdfCreatorService;

	@BeforeEach
	public void setUp() {
		repository = mock(InvoiceEntityRepository.class);
		pdfCreatorService = mock(InvoicePdfCreatorService.class);
		serviceUnderTest = new InvoicePdfServiceImpl(repository, pdfCreatorService);
	}

	@Test
	public void testCreateAndSaveInvoiceFile() {
		InvoiceEntity invoice = new InvoiceEntity();
		byte[] expectedBytes = "hshshshsshsh".getBytes();

		when(repository.findById(34L)).thenReturn(Optional.of(invoice));
		when(pdfCreatorService.createInvoicePdf(invoice)).thenReturn(expectedBytes);

		byte[] actualCreatedInvoice = serviceUnderTest.createAndSaveInvoiceFile(34L);
		assertArrayEquals(expectedBytes, actualCreatedInvoice);
	}

	@Test
	public void testLoadInvoiceFile() {
		InvoiceEntity invoice = new InvoiceEntity();
		byte[] expected = "pdfbytes".getBytes();
		invoice.setInvoiceFile(expected);

		when(repository.findById(12L)).thenReturn(Optional.of(invoice));

		byte[] actual = serviceUnderTest.loadInvoiceFile(12L);
		assertArrayEquals(expected, actual);
	}

	@Test
	public void testLoadInvoiceFileThrowIllegalStateException() {
		assertThrows(IllegalStateException.class, () -> {
			when(repository.findById(12L)).thenReturn(Optional.empty());
			serviceUnderTest.loadInvoiceFile(12L);
		});
	}

	@Test
	public void testLoadInvoiceFiles() {
		InvoiceEntity inv1 = new InvoiceEntity();
		InvoiceEntity inv2 = new InvoiceEntity();
		byte[] b1 = "a".getBytes();
		byte[] b2 = "b".getBytes();
		inv1.setInvoiceFile(b1);
		inv2.setInvoiceFile(b2);

		List<Long> ids = Arrays.asList(1L, 2L);
		when(repository.findAllById(ids)).thenReturn(Arrays.asList(inv1, inv2));

		List<byte[]> actual = serviceUnderTest.loadInvoiceFiles(ids);
		assertEquals(2, actual.size());
		assertArrayEquals(b1, actual.get(0));
		assertArrayEquals(b2, actual.get(1));
	}

	@Test
	public void testLoadInvoiceFilesThrowIllegalStateException() {
		assertThrows(IllegalStateException.class, () -> {
			List<Long> ids = Arrays.asList(1L);
			when(repository.findAllById(ids)).thenReturn(Collections.emptyList());
			serviceUnderTest.loadInvoiceFiles(ids);
		});
	}

	@Test
	public void testLoadInvoicesAndMergeToOneFile_success() {
		InvoiceEntity inv1 = new InvoiceEntity();
		InvoiceEntity inv2 = new InvoiceEntity();
		byte[] b1 = "p1".getBytes();
		byte[] b2 = "p2".getBytes();
		inv1.setInvoiceFile(b1);
		inv2.setInvoiceFile(b2);

		List<Long> ids = Arrays.asList(5L, 6L);
		List<InvoiceEntity> loaded = Arrays.asList(inv1, inv2);
		when(repository.findAllById(ids)).thenReturn(loaded);
		when(pdfCreatorService.mergeInvoices(Arrays.asList(b1, b2))).thenReturn("merged".getBytes());

		byte[] result = serviceUnderTest.loadInvoicesAndMergeToOneFile(ids);
		assertArrayEquals("merged".getBytes(), result);
	}

	@Test
	public void testLoadInvoicesAndMergeToOneFileThrowIllegalArgumentException() {
		assertThrows(IllegalArgumentException.class, () -> {
			serviceUnderTest.loadInvoicesAndMergeToOneFile(Collections.emptyList());
		});
	}

	@Test
	public void testLoadInvoicesAndMergeToOneFileThrowIllegalStateException() {
		assertThrows(IllegalStateException.class, () -> {
			List<Long> ids = Arrays.asList(9L);
			when(repository.findAllById(ids)).thenReturn(Collections.emptyList());
			serviceUnderTest.loadInvoicesAndMergeToOneFile(ids);
		});
	}

	@Test
	public void testCreateAndSaveInvoiceFiles_success() {
		InvoiceEntity inv1 = new InvoiceEntity();
		InvoiceEntity inv2 = new InvoiceEntity();
		List<Long> ids = Arrays.asList(7L, 8L);
		List<InvoiceEntity> loaded = Arrays.asList(inv1, inv2);
		when(repository.findAllById(ids)).thenReturn(loaded);

		List<byte[]> created = Arrays.asList("x".getBytes(), "y".getBytes());
		when(pdfCreatorService.createInvoicesPdfs(loaded)).thenReturn(created);

		List<byte[]> result = serviceUnderTest.createAndSaveInvoiceFiles(ids);
		assertEquals(2, result.size());
		assertArrayEquals(created.get(0), result.get(0));
		assertArrayEquals(created.get(1), result.get(1));
	}

	@Test
	public void testCreateAndSaveInvoiceFilesThrowIllegalArgumentException() {
		assertThrows(IllegalArgumentException.class, () -> {
			serviceUnderTest.createAndSaveInvoiceFiles(Collections.emptyList());
		});
	}

	@Test
	public void testCreateAndSaveInvoiceFilesThrowIllegalStateException() {
		assertThrows(IllegalStateException.class, () -> {
			List<Long> ids = Arrays.asList(11L);
			when(repository.findAllById(ids)).thenReturn(Collections.emptyList());
			serviceUnderTest.createAndSaveInvoiceFiles(ids);
		});
	}

	@Test
	public void testCreateAndSaveInvoiceFileThrowIllegalStateException() {
		assertThrows(IllegalStateException.class, () -> {
			when(repository.findById(34L)).thenReturn(Optional.empty());
			serviceUnderTest.createAndSaveInvoiceFile(34L);
		});
	}
	
	@Test
	public void testCreateAndSaveInvoiceFileThrowIllegalArgumentException() {
		assertThrows(IllegalArgumentException.class, () -> {
			serviceUnderTest.createAndSaveInvoiceFile(-1L);
		});
	}
}

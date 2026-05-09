package edu.remad.tutoring3.services.impl;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import edu.remad.tutoring3.persistence.models.AddressEntity;
import edu.remad.tutoring3.persistence.models.InvoiceEntity;
import edu.remad.tutoring3.persistence.models.PriceEntity;
import edu.remad.tutoring3.persistence.models.ServiceContractEntity;
import edu.remad.tutoring3.persistence.models.UserEntity;
import edu.remad.tutoring3.services.pdf.exception.PDFComplexInvoiceBuilderException;

/**
 * Unit Tests for {@link InvoicePdfCreatorServiceImpl}
 * 
 * @author edu.remad
 * @since 2026
 */
public class InvoicePdfCreatorServiceImplTest {

	private InvoicePdfCreatorServiceImpl serviceUnderTest;

	@BeforeEach
	public void setUp() {
		serviceUnderTest = new InvoicePdfCreatorServiceImpl();
		assertNotNull(serviceUnderTest);
	}

	private InvoiceEntity createInvoice() {
		InvoiceEntity invoice = new InvoiceEntity();
		invoice.setNo(100L);
		invoice.setTutoringHours(2.0f);
		invoice.setDate(LocalDateTime.of(2026, Month.JANUARY, 28, 0, 0));
		invoice.setTutoringDate(LocalDateTime.of(2026, Month.JANUARY, 28, 0, 0));
		invoice.setCreationDate(LocalDateTime.now());
		invoice.setPriceId(createPrice());
		invoice.setServiceContractId(createServiceContract());
		invoice.setUserId(createUser());

		return invoice;
	}

	private PriceEntity createPrice() {
		return new PriceEntity(1L, new BigDecimal(12.67), "EUR", LocalDateTime.now());
	}

	private ServiceContractEntity createServiceContract() {
		return new ServiceContractEntity(1l, "Elektrotechnik 1", "Grundlagen der ET 1", LocalDateTime.now());
	}

	private UserEntity createUser() {
		UserEntity user = new UserEntity();
		user.setUserId(1L);
		user.setCreationDate(LocalDateTime.now());
		user.setEmail("loanDoe@web.de");
		user.setEmailVerified(true);
		user.setFamilyName("Meier");
		user.setName("Remy");
		user.setGivenName("Remy Maier");
		user.setPreferredUsername("ReMAd");
		user.setSub("454545-545366-543465634-543534534");
		user.setPassword("Password");

		AddressEntity address = createAddress(user);
		user.setAddresses(List.of(address));

		return user;
	}

	private AddressEntity createAddress(UserEntity user) {
		return new AddressEntity(1l, "Poopbuettel Weg", "20D", 22378, "Hammaburg", user, LocalDateTime.now());
	}

	@Test
	public void testCreateInvoicePdfThrowException() {
		assertThrows(PDFComplexInvoiceBuilderException.class, () -> serviceUnderTest.createInvoicePdf(null));
	}

	@Test
	public void testCreateInvoicePdfSuccessful() throws IOException {
		byte[] invoice = serviceUnderTest.createInvoicePdf(createInvoice());
		assertNotNull(invoice, "May not be null!");
		assertTrue(invoice.length > 0, "Must be greater than 0!");
	}

}

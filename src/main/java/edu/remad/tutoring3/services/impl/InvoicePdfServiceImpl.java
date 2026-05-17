package edu.remad.tutoring3.services.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.remad.tutoring3.persistence.models.InvoiceEntity;
import edu.remad.tutoring3.repositories.InvoiceEntityRepository;
import edu.remad.tutoring3.services.InvoicePdfCreatorService;
import edu.remad.tutoring3.services.InvoicePdfService;

/**
 * Service for saving and fetching PDFs Creation of PDF is done here:
 * {@link InvoicePdfCreatorService}
 * 
 * @author edu.remad
 * @since 2026
 */
@Transactional
@Service
public class InvoicePdfServiceImpl implements InvoicePdfService {

	private final InvoiceEntityRepository invoiceEntityRepository;

	private final InvoicePdfCreatorService pdfCreatorService;

	/**
	 * Constructor
	 * 
	 * @param invoiceEntityRepository  {@link InvoiceEntityRepository} for CRUD
	 *                                 operations
	 * @param invoicePdfCreatorService {@link InvoicePdfCreatorService} creates and
	 *                                 merges PDF as byte[]-Arrays to one
	 *                                 byte[]-Array
	 */
	public InvoicePdfServiceImpl(InvoiceEntityRepository invoiceEntityRepository,
			InvoicePdfCreatorService invoicePdfCreatorService) {
		this.invoiceEntityRepository = invoiceEntityRepository;
		pdfCreatorService = invoicePdfCreatorService;
	}

	@Override
	public byte[] createAndSaveInvoiceFile(long invoiceNo) {
		if(invoiceNo < 0) {
			throw new IllegalArgumentException("invoiceNo of smaller than 0 is forbidden.");
		}
		
		Optional<InvoiceEntity> loadedInvoice = invoiceEntityRepository.findById(invoiceNo);
		byte[] invoicePdf = new byte[0];

		if (loadedInvoice.isPresent()) {
			InvoiceEntity invoice = loadedInvoice.get();

			byte[] loadedInvoicePdf = pdfCreatorService.createInvoicePdf(invoice);
			if (loadedInvoicePdf != null && loadedInvoicePdf.length > 0) {
				invoicePdf = loadedInvoicePdf;
			}

			invoice.setInvoiceFile(invoicePdf);
			invoiceEntityRepository.saveAndFlush(invoice);
		} else {
			throw new IllegalStateException(
					"loadedInvoice shalls not be empty and means no invoice for that id was loaded!");
		}

		return invoicePdf;
	}

	@Override
	public byte[] loadInvoiceFile(Long id) {
		byte[] invoicePdf = new byte[0];

		Optional<InvoiceEntity> loadedInvoice = invoiceEntityRepository.findById(id);
		if (loadedInvoice.isPresent()) {
			invoicePdf = loadedInvoice.get().getInvoiceFile();
		} else {
			throw new IllegalStateException(
					"loadedInvoice shalls not be empty and means no invoice for that id was loaded!");
		}

		return invoicePdf;
	}

	@Override
	public List<byte[]> loadInvoiceFiles(List<Long> ids) {
		List<InvoiceEntity> loadedInvoices = invoiceEntityRepository.findAllById(ids);

		List<byte[]> invoices = new ArrayList<>();
		if (!loadedInvoices.isEmpty()) {
			for (InvoiceEntity invoice : loadedInvoices) {
				invoices.add(invoice.getInvoiceFile());
			}

			return invoices;
		} else {
			throw new IllegalStateException(
					"loadedInvoice shalls not be empty and means no invoice for that id was loaded!");
		}
	}

	@Override
	public byte[] loadInvoicesAndMergeToOneFile(List<Long> ids) {
		if (ids == null || ids.isEmpty()) {
			throw new IllegalArgumentException("ids shalls not be null or empty.");
		}

		List<InvoiceEntity> loadedInvoices = invoiceEntityRepository.findAllById(ids);
		if (loadedInvoices != null && loadedInvoices.isEmpty()) {
			throw new IllegalStateException("");
		}

		List<byte[]> invoices = new ArrayList<>();
		if (!loadedInvoices.isEmpty()) {
			for (InvoiceEntity invoice : loadedInvoices) {
				invoices.add(invoice.getInvoiceFile());
			}
		} else {
			throw new IllegalStateException(
					"loadedInvoices shall not be empty and means no invoice for these ids were loaded!");
		}

		byte[] invoicePdf = new byte[0];
		if (!invoices.isEmpty()) {
			invoicePdf = pdfCreatorService.mergeInvoices(invoices);
		}

		return invoicePdf;
	}

	@Override
	public List<byte[]> createAndSaveInvoiceFiles(List<Long> ids) {
		if (ids == null || ids.isEmpty()) {
			throw new IllegalArgumentException("ids shalls not be null or empty.");
		}
		List<InvoiceEntity> loadedInvoices = invoiceEntityRepository.findAllById(ids);

		List<byte[]> createdPdfs = new ArrayList<>();
		if (!loadedInvoices.isEmpty()) {
			createdPdfs = pdfCreatorService.createInvoicesPdfs(loadedInvoices);
		} else {
			throw new IllegalStateException("ids shall not be empty and means no invoice for these ids were loaded!");
		}

		return createdPdfs;
	}

}

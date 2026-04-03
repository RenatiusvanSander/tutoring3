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

	public InvoicePdfServiceImpl(InvoiceEntityRepository invoiceEntityRepository,
			InvoicePdfCreatorService invoicePdfCreatorService) {
		this.invoiceEntityRepository = invoiceEntityRepository;
		pdfCreatorService = invoicePdfCreatorService;
	}

	@Override
	public byte[] createAndSaveInvoiceFile(long invoiceNo) {
		Optional<InvoiceEntity> loadedInvoice = invoiceEntityRepository.findById(invoiceNo);

		byte[] invoicePdf = "PDF".getBytes();
		if (loadedInvoice.isPresent()) {
			InvoiceEntity invoice = loadedInvoice.get();
			
			byte[] loadedInvoicePdf = pdfCreatorService.createInvoicePdf(invoice);
			if (loadedInvoicePdf != null && loadedInvoicePdf.length > 0) {
				invoicePdf = loadedInvoicePdf;
			}
			
			invoice.setInvoiceFile(invoicePdf);
			invoiceEntityRepository.saveAndFlush(invoice);
		}

		return invoicePdf;
	}

	@Override
	public byte[] loadInvoiceFile(Long id) {
		byte[] invociePdf = "empty".getBytes();

		Optional<InvoiceEntity> loadedInvoice = invoiceEntityRepository.findById(id);
		if (loadedInvoice.isPresent()) {
			invociePdf = loadedInvoice.get().getInvoiceFile();
		}

		return invociePdf;
	}

	@Override
	public List<byte[]> loadInvoiceFiles(List<Long> ids) {
		List<InvoiceEntity> loadedInvoices = invoiceEntityRepository.findAllById(ids);

		List<byte[]> invoices = new ArrayList<>();
		if (!loadedInvoices.isEmpty()) {
			for (InvoiceEntity invoice : loadedInvoices) {
				invoices.add(invoice.getInvoiceFile());
			}
		}

		return invoices;
	}

	@Override
	public byte[] loadInvoicesAndMergeToOneFile(List<Long> ids) {
		List<InvoiceEntity> loadedInvoices = invoiceEntityRepository.findAllById(ids);

		List<byte[]> invoices = new ArrayList<>();
		if (!loadedInvoices.isEmpty()) {
			for (InvoiceEntity invoice : loadedInvoices) {
				invoices.add(invoice.getInvoiceFile());
			}
		}

		byte[] invoicePdf = new byte[3];
		if (!invoices.isEmpty()) {
			invoicePdf = pdfCreatorService.mergeInvoices(invoices);
		}

		return invoicePdf;
	}

	@Override
	public List<byte[]> createAndSaveInvoiceFiles(List<Long> ids) {
		List<InvoiceEntity> loadedInvoices = invoiceEntityRepository.findAllById(ids);

		List<byte[]> createdPdfs = new ArrayList<>();
		if (!loadedInvoices.isEmpty()) {
			createdPdfs = pdfCreatorService.createInvoicesPdfs(loadedInvoices);
		}

		return createdPdfs;
	}

}

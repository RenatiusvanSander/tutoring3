package edu.remad.tutoring3.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.remad.tutoring3.persistence.models.InvoiceEntity;
import edu.remad.tutoring3.repositories.InvoiceEntityRepository;
import edu.remad.tutoring3.services.InvoicePdfCreatorService;
import edu.remad.tutoring3.services.InvoicePdfService;

/**
 * Service for saving and fetching pdf 
 * 
 * @author edu.remad
 * @since 2026
 */
@Transactional
@Service
public class InvoicePdfServiceImpl implements InvoicePdfService {

	private final InvoiceEntityRepository invoiceEntityRepository;
	
	private final InvoicePdfCreatorService pdfCreatorService;
	
	public InvoicePdfServiceImpl(InvoiceEntityRepository invoiceEntityRepository, InvoicePdfCreatorService invoicePdfCreatorService ) {
		this.invoiceEntityRepository = invoiceEntityRepository;
		pdfCreatorService = invoicePdfCreatorService;
	}
	
	@Override
	public byte[] createAndSaveInvoiceFile(long invoiceNo) {
		Optional<InvoiceEntity> loadedInvoice = invoiceEntityRepository.findById(invoiceNo);
		
		byte[] invoicePdf = new byte[0];
		if(loadedInvoice.isPresent()) {
			InvoiceEntity invoice = loadedInvoice.get();
			invoicePdf = pdfCreatorService.createInvoicePdf(invoice);
			invoice.setInvoiceFile(invoicePdf);
			
			invoiceEntityRepository.saveAndFlush(invoice);
		}

		return invoicePdf;
	}

	@Override
	public byte[] loadInvoiceFile(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<byte[]> loadInvoiceFiles(List<Long> ids) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<byte[]> loadInvoicesAndMergeToOneFile(List<Long> ids) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<byte[]> createAndSaveInvoiceFiles(List<Long> ids) {
		// TODO Auto-generated method stub
		return null;
	}

}

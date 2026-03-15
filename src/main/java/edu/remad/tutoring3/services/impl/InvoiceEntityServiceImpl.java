package edu.remad.tutoring3.services.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.remad.tutoring3.persistence.models.InvoiceEntity;
import edu.remad.tutoring3.repositories.InvoiceEntityRepository;
import edu.remad.tutoring3.services.InvoiceEntityService;

/**
 * Service for loading and saving invoices
 * 
 * @author edu.remad
 * @since 2025
 */
@Transactional
@Service
public class InvoiceEntityServiceImpl implements InvoiceEntityService {
	
	private final InvoiceEntityRepository invoiceRepository;
	
	/**
	 * Constructor
	 * 
	 * @param invoiceEntityRepository {@link InvoiceEntityRepository}
	 */
	public InvoiceEntityServiceImpl(InvoiceEntityRepository invoiceEntityRepository) {
		invoiceRepository = invoiceEntityRepository;
	}

	@Override
	public InvoiceEntity saveInvoice(InvoiceEntity invoice) {
		return invoiceRepository.save(invoice);
	}

	@Override
	public InvoiceEntity loadInvoiceById(Long id) {
		return invoiceRepository.findById(id).orElseThrow();
	}

	@Override
	public List<InvoiceEntity> loadInvoicesByUserId(Long userId) {
		List<InvoiceEntity> usersInvoices = invoiceRepository.findByUserId_UserId(userId);
		unproxyInvoices(usersInvoices);
		
		return usersInvoices;
	}

	@Override
	public List<InvoiceEntity> loadInvoicesByInvoiceFileIsNull() {
		List<InvoiceEntity>invoicesWithoutPdf = invoiceRepository.findByInvoiceFileIsNull();
		unproxyInvoices(invoicesWithoutPdf);
		
		return invoicesWithoutPdf;
	}

	private void unproxyInvoices(List<InvoiceEntity> invoicesWithoutPdf) {
		if(!invoicesWithoutPdf.isEmpty()) {
			invoicesWithoutPdf.get(0).getPriceId();
			invoicesWithoutPdf.get(0).getServiceContractId();
			invoicesWithoutPdf.get(0).getUserId();
		}
	}

	@Override
	public InvoiceEntity updateSingleInvoice(InvoiceEntity invoice) {
		return invoiceRepository.save(invoice);
	}

	@Override
	public List<InvoiceEntity> updateMultipleInvoices(List<InvoiceEntity> updatedInvoices) {
		List<InvoiceEntity> savedUpdatedInvoices = invoiceRepository.saveAllAndFlush(updatedInvoices);
		
		return savedUpdatedInvoices;
	}

	@Override
	public List<InvoiceEntity> loadInvoicesByIds(List<Long> ids) {
		List<InvoiceEntity> loadedInvoices = invoiceRepository.findAllById(ids);
		
		return loadedInvoices;
	}

}

package edu.remad.tutoring3.services;

import java.util.List;

import edu.remad.tutoring3.persistence.models.InvoiceEntity;

/**
 * Defines methods to read, delete, update and persist an {@link InvoiceEntity}
 * 
 * @author edu.remad
 * @since 2025
 */
public interface InvoiceEntityService {

	/**
	 * Saves invoice
	 * 
	 * @param invoice {@link InvoiceEntity}
	 * @return {@link InvoiceEntity}
	 */
	InvoiceEntity saveInvoice(InvoiceEntity invoice);
	
	/**
	 * Loads invoice by its identifier
	 * 
	 * @param id invoice's id
	 * @return {@link InvoiceEntity}
	 */
	InvoiceEntity loadInvoiceById(Long id);
	
	/**
	 * Loads invoices by user's id
	 * 
	 * @param userId user's identifier to load all invoices
	 * @return list of {@link InvoiceEntity}
	 */
	List<InvoiceEntity> loadInvoicesByUserId(Long userId);
	
	/**
	 * Loads all invoices, which have not a pdf file stored in database
	 * 
	 * @return list of {@link InvoiceEntity}
	 */
	List<InvoiceEntity> loadInvoicesByInvoiceFileIsNull();
	
	/**
	 * Updates single invoice
	 * 
	 * @param invoice {@link InvoiceEntity}
	 * @return {@link InvoiceEntity}
	 */
	InvoiceEntity updateSingleInvoice(InvoiceEntity invoice);
	
	/**
	 * Updates multiple invoices
	 * 
	 * @param updatedInvoices Collection of {@link InvoiceEntity}
	 * @return list of {@link InvoiceEntity}
	 */
	List<InvoiceEntity> updateMultipleInvoices(List<InvoiceEntity> updatedInvoices);

	/**
	 * Load 
	 * 
	 * @param ids invoices' ids to load invoices for
	 * @return list of {@link InvoiceEntity}
	 */
	List<InvoiceEntity> loadInvoicesByIds(List<Long> ids);
}

package edu.remad.tutoring3.services;

import java.util.List;

/**
 * Defines methods for implementation of this interface
 * 
 * @author edu.remad
 * @since 2026
 */
public interface InvoicePdfService {

	/**
	 * Creates and save invoice file
	 * 
	 * @param invoiceNo invoice number is the identifier of invoice
	 * @return byte array, which is an invoice as PDF
	 */
	byte[] createAndSaveInvoiceFile(long invoiceNo);

	/**
	 * Loads invoice file
	 * 
	 * @param id invoice's id to 
	 * @return byte array, which is an invoice as PDF
	 */
	byte[] loadInvoiceFile(Long id);

	/**
	 * Load invoices files
	 * 
	 * @param ids invoices' identifiers to load invoice PDFs for
	 * @return byte array, which is an invoice as PDF
	 */
	List<byte[]> loadInvoiceFiles(List<Long> ids);

	/**
	 * Load invoices and merge them to one PDF File
	 * 
	 * @param ids invoices' identifiers to merge to one PDF file
	 * @return byte array, which is an invoice as PDF
	 */
	byte[] loadInvoicesAndMergeToOneFile(List<Long> ids);

	/**
	 * Create and save invoice files 
	 * 
	 * @param ids invoices' identifier to create invoice PDFs
	 * @return byte array, which is an invoice as PDF
	 */
	List<byte[]> createAndSaveInvoiceFiles(List<Long> ids);

}

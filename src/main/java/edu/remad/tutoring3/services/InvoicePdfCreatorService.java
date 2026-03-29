package edu.remad.tutoring3.services;

import java.util.List;

import edu.remad.tutoring3.persistence.models.InvoiceEntity;

/**
 * Defines methods for implementation of this interface
 * 
 * @author edu.remad
 * @since 2026
 */
public interface InvoicePdfCreatorService {

	/**
	 * Creates invoice as PDF
	 * 
	 * @param invoice {@link InvoiceEntity} to create PDF file to
	 * @return byte array, which is a PDF
	 */
	byte[] createInvoicePdf(InvoiceEntity invoice);
	
	/**
	 * Create invoices as PDFs
	 * 
	 * @param invoices list of {@link InvoiceEntity} to create PDFs for
	 * @return list of byte arrays, which are PDFs
	 */
	List<byte[]> createInvoicesPdfs(List<InvoiceEntity> invoices);
	
	/**
	 * Merge invoices PDFs to one PDF
	 * 
	 * @param invoicesToMerge list of byte arrays, every array is one PDF, and merged to one PDF together
	 * @return byte array, which is a PDF
	 */
	byte[] mergeInvoices(List<byte[]> invoicesToMerge);
	
}

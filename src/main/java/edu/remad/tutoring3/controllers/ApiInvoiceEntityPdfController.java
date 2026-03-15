package edu.remad.tutoring3.controllers;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import edu.remad.tutoring3.helper.DownloadHelper;
import edu.remad.tutoring3.persistence.models.InvoiceEntity;
import edu.remad.tutoring3.services.InvoiceEntityService;

/**
 * Controls API REST Endpoints for invoices's PDF file to download
 * 
 * @author edu.remad
 * @since 2026
 */
@RequestMapping("/api/pdf-invoices")
@RestController
public class ApiInvoiceEntityPdfController {

	/**
	 * invoice entity service
	 */
	private final InvoiceEntityService invoiceEntityService;
	
	/**
	 * Constructor
	 * 
	 * @param invoiceService {@link InvoiceEntityService}
	 */
	public ApiInvoiceEntityPdfController(InvoiceEntityService invoiceService) {
		invoiceEntityService = invoiceService;
	}
	
	@GetMapping(value = "/getPdfInvoice/{id}", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
	public ResponseEntity<byte[]> getPdfInvoice(@PathVariable("id") long invoiceId) {
		InvoiceEntity loadedInvoice = invoiceEntityService.loadInvoiceById(invoiceId);
		byte[] pdfInvoiceFile = null;//pdfCreatorService.createInvoicePdf(loadedInvoice);
		HttpHeaders httpHeaders = DownloadHelper.createHttpHeaders("invoice-" + invoiceId + ".pdf");

		return ResponseEntity.ok().contentLength(pdfInvoiceFile.length).contentType(MediaType.APPLICATION_OCTET_STREAM)
				.headers(httpHeaders).body(pdfInvoiceFile);
	}

	@GetMapping(value = "/getPdfInvoicesInOneFile/{ids}", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
	public ResponseEntity<byte[]> getPdfInvoices(@PathVariable("ids") List<Long> ids) {
		List<InvoiceEntity> invoiceEntities = invoiceEntityService.loadInvoicesByIds(ids);
		byte[] infoicesPfs = null; // pdfCreatorService.createInvoicesPdfs(invoiceEntities);
		String joinedIds = invoiceEntities.stream().map(String::valueOf).collect(Collectors.joining("_"));
		HttpHeaders httpHeaders = DownloadHelper.createHttpHeaders("invoices-" + joinedIds + ".pdf");

		return ResponseEntity.ok().contentLength(infoicesPfs.length).contentType(MediaType.APPLICATION_OCTET_STREAM)
				.headers(httpHeaders).body(infoicesPfs);
	}

}

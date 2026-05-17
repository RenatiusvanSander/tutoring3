package edu.remad.tutoring3.services.impl;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.pdfbox.io.MemoryUsageSetting;
import org.apache.pdfbox.multipdf.PDFMergerUtility.DocumentMergeMode;
import org.apache.pdfbox.pdmodel.PDDocumentInformation;
import org.springframework.stereotype.Service;

import edu.remad.tutoring3.persistence.models.InvoiceEntity;
import edu.remad.tutoring3.services.InvoicePdfCreatorService;
import edu.remad.tutoring3.services.pdf.PDFComplexInvoiceBuilder;
import edu.remad.tutoring3.services.pdf.documentinformation.DocumentInformationMultiplePagesBuilder;
import edu.remad.tutoring3.services.pdf.pdffilemerger.PDFMergerBuilder;
import edu.remad.tutoring3.services.pdf.utilities.PdfUtilities;

/**
 * Service for loading and saving invoices
 * 
 * @author edu.remad
 * @since 2026
 */
//@Transactional
@Service
public class InvoicePdfCreatorServiceImpl implements InvoicePdfCreatorService {

	@Override
	public byte[] createInvoicePdf(InvoiceEntity invoice) {
		if (invoice == null) {
			throw new IllegalArgumentException("Invoice shall not be null!");
		}

		return new PDFComplexInvoiceBuilder().invoice(invoice).build();
	}

	@Override
	public List<byte[]> createInvoicesPdfs(List<InvoiceEntity> invoices) {
		if (invoices == null || invoices.isEmpty()) {
			throw new IllegalArgumentException("Invoices shall not be null or empty!");
		}

		List<byte[]> createdInvoicePdfs = new ArrayList<>();
		for (InvoiceEntity invoice : invoices) {
			createdInvoicePdfs.add(createInvoicePdf(invoice));
		}

		return createdInvoicePdfs;
	}

	@Override
	public byte[] mergeInvoices(List<byte[]> invoicesToMerge) {
		try {
			if (invoicesToMerge == null || invoicesToMerge.isEmpty()) {
				throw new IllegalArgumentException("invoicesToMerge shall not be null or empty!");
			}

			byte[] mergedInvoicesAsOnePdf = new byte[0];
			ByteArrayOutputStream os = new ByteArrayOutputStream();
			PDDocumentInformation documentInformation = new DocumentInformationMultiplePagesBuilder()
					.inputStreams(PdfUtilities.convertByteArraysToInputStreams(invoicesToMerge)).build();
			new PDFMergerBuilder().addSources(PdfUtilities.convertByteArraysToInputStreams(invoicesToMerge))
					.destinationStream(os).documentMergeMode(DocumentMergeMode.OPTIMIZE_RESOURCES_MODE)
					.customizedMemoryUsageSetting(MemoryUsageSetting.setupMainMemoryOnly())
					.destinationPDDocumentInformation(documentInformation).build();
			mergedInvoicesAsOnePdf = os.toByteArray();

			return mergedInvoicesAsOnePdf;
		} catch (Exception e) {
			throw new RuntimeException("Merger of invoices caused error.", e);
		}
	}

}

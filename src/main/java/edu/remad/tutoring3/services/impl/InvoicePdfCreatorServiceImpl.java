package edu.remad.tutoring3.services.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.remad.tutoring3.persistence.models.InvoiceEntity;
import edu.remad.tutoring3.services.InvoicePdfCreatorService;

//@Transactional
@Service
public class InvoicePdfCreatorServiceImpl implements InvoicePdfCreatorService {

	@Override
	public byte[] createInvoicePdf(InvoiceEntity invoice) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public byte[] createInvoicesPdfs(List<InvoiceEntity> invoices) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public byte[] mergeInvoices(List<byte[]> invoicesToMerge) {
		// TODO Auto-generated method stub
		return null;
	}

}

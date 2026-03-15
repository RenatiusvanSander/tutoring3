package edu.remad.tutoring3.controllers;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import edu.remad.tutoring3.dto.InvoiceDto;
import edu.remad.tutoring3.helper.LocalDateTimeHelper;
import edu.remad.tutoring3.persistence.models.InvoiceEntity;
import edu.remad.tutoring3.persistence.models.PriceEntity;
import edu.remad.tutoring3.persistence.models.ServiceContractEntity;
import edu.remad.tutoring3.persistence.models.UserEntity;
import edu.remad.tutoring3.services.InvoiceEntityService;
import edu.remad.tutoring3.services.PriceEntityService;
import edu.remad.tutoring3.services.ServiceContractService;
import edu.remad.tutoring3.services.UserEntityService;

/**
 * Controls API REST Endpoints for all concerning invoices
 * 
 * @author edu.remad
 * @since 2025
 */
@RequestMapping("/api/invoices")
@RestController
public class ApiInvoiceEntityController {

	private final InvoiceEntityService invoiceEntityService;
	private final ServiceContractService serviceContractService;
	private final UserEntityService userService;
	private final PriceEntityService priceService;

	/**
	 * Constructor
	 * 
	 * @param invoiceService         {@link InvoiceEntityService}
	 * @param serviceContractService {@link ServiceContractService}
	 * @param userEntityService      {@link UserEntityService}
	 * @param priceEntityService     {@link PriceEntityService}
	 */
	public ApiInvoiceEntityController(InvoiceEntityService invoiceService,
			ServiceContractService serviceContractService, UserEntityService userEntityService,
			PriceEntityService priceEntityService) {
		invoiceEntityService = invoiceService;
		this.serviceContractService = serviceContractService;
		userService = userEntityService;
		priceService = priceEntityService;
	}

	/**
	 * Saves a single {@link InvoiceEntity}
	 * 
	 * @param invoiceDto {@link InvoiceDto}
	 * @return json-encoded invoice
	 */
	@PostMapping("/save/single-invoice")
	public ResponseEntity<InvoiceDto> saveSingleInvoice(@RequestBody InvoiceDto invoiceDto) {
		ServiceContractEntity sce = serviceContractService
				.getReferencedServiceContractById(invoiceDto.getServiceContractId());
		UserEntity user = userService.getReferencedUserEntityById(invoiceDto.getUserId());
		PriceEntity price = priceService.getReferencedPrice(invoiceDto.getPriceId());
		InvoiceEntity invoice = new InvoiceEntity(invoiceDto, sce, user, price, LocalDateTime.now());
		InvoiceEntity savedInvoice = invoiceEntityService.saveInvoice(invoice);

		return new ResponseEntity<>(new InvoiceDto(savedInvoice), HttpStatusCode.valueOf(201));
	}

	/**
	 * Loads single invoice by its identifier
	 * 
	 * @param id invoice's identifier
	 * @return json-encoded invoice
	 */
	@GetMapping("/load/single-invoice/{id}")
	public ResponseEntity<InvoiceDto> loadSingleInvoiceById(@PathVariable("id") Long id) {
		InvoiceEntity loadedInvoice = invoiceEntityService.loadInvoiceById(id);

		return new ResponseEntity<InvoiceDto>(new InvoiceDto(loadedInvoice), HttpStatusCode.valueOf(200));
	}

	/**
	 * Loads multiple invoices, which are without PDF file.
	 * 
	 * @return json-array containing invoices
	 */
	@GetMapping("/load/multiple-invoices/invoice-file-is-null")
	public ResponseEntity<List<InvoiceDto>> loadInvoicesByInvoiceFileIsNull() {
		List<InvoiceEntity> loadedInvoices = invoiceEntityService.loadInvoicesByInvoiceFileIsNull();
		List<InvoiceDto> invoicesWithOutPdfFile = loadedInvoices.stream().map(InvoiceDto::new)
				.collect(Collectors.toList());

		return new ResponseEntity<>(invoicesWithOutPdfFile, HttpStatusCode.valueOf(200));
	}

	/**
	 * Loads invoices by user's id.
	 * 
	 * @param userId user's identifier to load invoices for
	 * @return json-array containing invoices
	 */
	@GetMapping("/load/multiple-invoices/invoice-by-user-id/{userId}")
	public ResponseEntity<List<InvoiceDto>> loadInvoicesByUserId(@PathVariable("userId") Long userId) {
		List<InvoiceEntity> loadedInvoices = invoiceEntityService.loadInvoicesByUserId(userId);
		List<InvoiceDto> invoicesWithOutPdfFile = loadedInvoices.stream().map(InvoiceDto::new)
				.collect(Collectors.toList());

		return new ResponseEntity<>(invoicesWithOutPdfFile, HttpStatusCode.valueOf(200));
	}

	/**
	 * Update a single {@link InvoiceEntity}
	 * 
	 * @param invoiceUpdatedValues data object has updated invoice values
	 * @return json-encoded invoice
	 */
	@PutMapping("/update/single-invoice")
	public ResponseEntity<InvoiceDto> updateSingleInvoice(@RequestBody InvoiceDto invoiceUpdatedValues) {
		InvoiceEntity loadedInvoice = invoiceEntityService.loadInvoiceById(invoiceUpdatedValues.getNo());

		if (loadedInvoice != null) {
			PriceEntity price = priceService.getPrice(invoiceUpdatedValues.getPriceId());
			ServiceContractEntity serviceContractId = serviceContractService
					.getReferencedServiceContractById(invoiceUpdatedValues.getServiceContractId());

			loadedInvoice
					.setDate(LocalDateTimeHelper.convertIsoTimeWithoutZToLocalDateTime(invoiceUpdatedValues.getDate()));
			if (price != null) {
				loadedInvoice.setPriceId(price);
			}
			if (serviceContractId != null) {
				loadedInvoice.setServiceContractId(serviceContractId);
			}
			loadedInvoice.setTutoringDate(
					LocalDateTimeHelper.convertIsoTimeWithoutZToLocalDateTime(invoiceUpdatedValues.getTutoringDate()));
			loadedInvoice.setTutoringHours(invoiceUpdatedValues.getTutoringHours());
		}

		InvoiceEntity updatedInvoice = invoiceEntityService.updateSingleInvoice(loadedInvoice);

		return new ResponseEntity<>(new InvoiceDto(updatedInvoice), HttpStatusCode.valueOf(200));
	}

	@PutMapping("/update/several-invoices")
	public ResponseEntity<List<InvoiceDto>> updateSeveralInvoices(@RequestBody List<InvoiceDto> updatedInvoices) {
		List<InvoiceEntity> loadedInvoices = Collections
				.singletonList(invoiceEntityService.loadInvoiceById(updatedInvoices.get(0).getNo()));

		// update
		List<InvoiceEntity> updatedInvoices2 = new ArrayList<>();
		if (updatedInvoices.size() == loadedInvoices.size()) {
			InvoiceDto dto = updatedInvoices.get(0);
			InvoiceEntity updatedInvoice = loadedInvoices.get(0);
			updatedInvoice.setDate(LocalDateTimeHelper.convertIsoTimeWithoutZToLocalDateTime(dto.getDate()));

			PriceEntity loadedPriceId = priceService.getPrice(dto.getPriceId());
			updatedInvoice.setPriceId(loadedPriceId);

			ServiceContractEntity serviceContractId = serviceContractService
					.getReferencedServiceContractById(dto.getServiceContractId());
			updatedInvoice.setServiceContractId(serviceContractId);

			updatedInvoice
					.setTutoringDate(LocalDateTimeHelper.convertIsoTimeWithoutZToLocalDateTime(dto.getTutoringDate()));

			updatedInvoice.setTutoringHours(dto.getTutoringHours());
			updatedInvoices2.add(updatedInvoice);
		}

		List<InvoiceEntity> savedInvoices = invoiceEntityService.updateMultipleInvoices(updatedInvoices2);
		List<InvoiceDto> savedInvoiceDtos = savedInvoices.stream().map(item -> new InvoiceDto(item))
				.collect(Collectors.toList());

		return new ResponseEntity<List<InvoiceDto>>(savedInvoiceDtos, HttpStatusCode.valueOf(200));
	}

}

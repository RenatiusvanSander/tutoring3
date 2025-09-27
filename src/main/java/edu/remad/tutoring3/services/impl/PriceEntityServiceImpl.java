package edu.remad.tutoring3.services.impl;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import edu.remad.tutoring3.persistence.models.PriceEntity;
import edu.remad.tutoring3.repositories.PriceEntityRepository;
import edu.remad.tutoring3.services.PriceEntityService;

/**
 * Service persists, updates loads {@link PriceEntity}
 * user info, when it is not persisted
 * 
 * @author edu.remad
 * @since 2025
 */
@Service
public class PriceEntityServiceImpl implements PriceEntityService {

	/**
	 * Constructor
	 * 
	 * @param priceRepository {@link PriceEntityRepository}
	 */
	public PriceEntityServiceImpl(PriceEntityRepository priceRepository) {
		this.priceRepository = priceRepository;
	}

	private final PriceEntityRepository priceRepository;
	
	@Override
	public PriceEntity savePrice(PriceEntity price) {
		price.setCreationDate(LocalDateTime.now());
		PriceEntity savedPrice = priceRepository.save(price);
		
		return savedPrice;
	}

	@Override
	public PriceEntity getPrice(Long priceId) {
		PriceEntity loadedPrice = priceRepository.findById(priceId).get();
		
		return loadedPrice;
	}

	@Override
	public PriceEntity updatePrice(PriceEntity updatePriceValues) {
		PriceEntity updatePrice = updatePriceValues;
		
		return updatePrice;
	}

	@Override
	public PriceEntity getReferencedPrice(Long priceId) {
		return priceRepository.getReferenceById(priceId);
	}

}

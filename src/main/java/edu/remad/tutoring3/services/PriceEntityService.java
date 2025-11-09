package edu.remad.tutoring3.services;

import edu.remad.tutoring3.persistence.models.PriceEntity;

/**
 * Defines methods to read, delete, update and persist an {@link PriceEntity}
 * 
 * @author edu.remad
 * @since 2025
 */
public interface PriceEntityService {

	/**
	 * Saves Price
	 * 
	 * @param price {@link PriceEntity}
	 * @return {@link PriceEntity}
	 */
	PriceEntity savePrice(PriceEntity price);

	/**
	 * Gets price
	 * 
	 * @param priceId price's identifier
	 * @return {@link PriceEntity}
	 */
	PriceEntity getPrice(Long priceId);

	/**
	 * Updates price
	 * 
	 * @param updatePriceValues {@link PriceEntity}
	 * @return {@link PriceEntity}
	 */
	PriceEntity updatePrice(PriceEntity updatePriceValues);
	
	/**
	 * Gets referenced price
	 * 
	 * @param priceId price's identifier
	 * @return {@link PriceEntity}
	 */
	PriceEntity getReferencedPrice(Long priceId);

}

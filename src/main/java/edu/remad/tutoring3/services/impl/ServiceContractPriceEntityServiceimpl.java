package edu.remad.tutoring3.services.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.remad.tutoring3.persistence.models.ServiceContractPriceEntity;
import edu.remad.tutoring3.repositories.ServiceContractPriceEntityRepository;
import edu.remad.tutoring3.services.ServiceContractPriceEntityService;

/**
 * Implementation of {@link ServiceContractPriceEntityService}
 * 
 * @author edu.remad
 * @since 2025
 */
@Transactional
@Service
public class ServiceContractPriceEntityServiceimpl implements ServiceContractPriceEntityService {

	private final ServiceContractPriceEntityRepository serviceContractPriceRepository;

	/**
	 * Constructor
	 * 
	 * @param serviceContractPriceRepository {@link ServiceContractPriceEntityRepository}
	 */
	public ServiceContractPriceEntityServiceimpl(ServiceContractPriceEntityRepository serviceContractPriceRepository) {
		this.serviceContractPriceRepository = serviceContractPriceRepository;
	}

	@Override
	public ServiceContractPriceEntity saveServiceContractPrice(ServiceContractPriceEntity serviceContractPrice) {
		return serviceContractPriceRepository.save(serviceContractPrice);
	}

	@Override
	public ServiceContractPriceEntity getServiceContractPrice(Long id) {
		return serviceContractPriceRepository.findById(id).get();
	}

	@Override
	public List<ServiceContractPriceEntity> getUsersServiceContractPrices(Long usersId) {
		return serviceContractPriceRepository.findByUserId_UserId(usersId);
	}

	@Override
	public ServiceContractPriceEntity updateServiceContractPrice(ServiceContractPriceEntity serviceContractPrice) {
		return serviceContractPriceRepository.save(serviceContractPrice);
	}

	@Override
	public List<ServiceContractPriceEntity> findNotConfirmedServiceContractPrices() {
		return serviceContractPriceRepository.findByConfirmedFalse();
	}

}

package edu.remad.tutoring3.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.remad.tutoring3.persistence.models.AddressEntity;
import edu.remad.tutoring3.persistence.models.UserEntity;
import edu.remad.tutoring3.repositories.AddressEntityRepository;
import edu.remad.tutoring3.services.AddressEntityService;
import edu.remad.tutoring3.services.UserEntityService;

/**
 * Serves action on addresses
 * 
 * @author edu.remad
 * @since 2025
 */
@Service
@Transactional
public class AddressEntityServiceImpl implements AddressEntityService {

	/** repository for addresses */
	private final AddressEntityRepository addressRepository;
	
	/** repository for users */
	private final UserEntityService userEntityService;

	/**
	 * Constructor
	 * 
	 * @param addressEntityRepository {@link AddressEntityRepository}
	 * @param userEntityService {@link UserEntityService}
	 */
	public AddressEntityServiceImpl(AddressEntityRepository addressEntityRepository, UserEntityService userEntityService) {
		addressRepository = addressEntityRepository;
		this.userEntityService = userEntityService;
	}

	@Override
	public AddressEntity findByAddressId(Long id) {
		AddressEntity foundAddress = addressRepository.findById(id).get();

		return foundAddress;
	}

	@Override
	public AddressEntity saveAddress(AddressEntity address) {
		AddressEntity savedAddress = addressRepository.save(address);

		return savedAddress;
	}

	@Override
	public List<AddressEntity> findByAdressIds(List<Long> ids) {
		List<AddressEntity> foundAddresses = addressRepository.findAllById(ids);

		return foundAddresses;
	}

	@Override
	public AddressEntity patchAddress(AddressEntity address) {
		Optional<AddressEntity> optional = addressRepository.findById(address.getId());
		AddressEntity unpatchedAddress = optional.get();

		if (!unpatchedAddress.getAddressStreet().equals(address.getAddressStreet())) {
			unpatchedAddress.setAddressStreet(address.getAddressStreet());
		}
		if (!unpatchedAddress.getAddressHouseNo().equals(address.getAddressHouseNo())) {
			unpatchedAddress.setAddressHouseNo(address.getAddressHouseNo());
		}
		if (unpatchedAddress.getAddressZipCode() != address.getAddressZipCode()) {
			unpatchedAddress.setAddressZipCode(address.getAddressZipCode());
		}
		if (!unpatchedAddress.getPlace().equals(address.getPlace())) {
			unpatchedAddress.setPlace(address.getPlace());
		}

		AddressEntity patchedAddress = addressRepository.save(unpatchedAddress);

		return patchedAddress;
	}

	@Override
	public List<AddressEntity> findAddressesByUserId(Long userId) {
		UserEntity user = userEntityService.getUserEntityById(userId);
		
		return addressRepository.findByUser(user);
	}

	@Override
	public void deleteAddressById(Long id) {
		addressRepository.deleteById(id);		
	}
}

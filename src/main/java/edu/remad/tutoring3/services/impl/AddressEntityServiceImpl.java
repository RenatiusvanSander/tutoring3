package edu.remad.tutoring3.services.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
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
	 * @param userEntityService       {@link UserEntityService}
	 */
	public AddressEntityServiceImpl(AddressEntityRepository addressEntityRepository,
			UserEntityService userEntityService) {
		addressRepository = addressEntityRepository;
		this.userEntityService = userEntityService;
	}

	@Override
	public AddressEntity findByAddressId(Long id) {
		Optional<AddressEntity> foundAddress = addressRepository.findById(id);

		if (foundAddress.isPresent()) {
			return foundAddress.get();
		}

		throw new IllegalArgumentException("No address found with id: " + id);
	}

	@Override
	public AddressEntity saveAddress(AddressEntity address) {
		return addressRepository.save(address);
	}

	@Override
	public List<AddressEntity> findByAdressIds(List<Long> ids) {
		List<AddressEntity> foundAddresses = addressRepository.findAllById(ids);

		return new ArrayList<>(foundAddresses);
	}

	@Override
	public AddressEntity patchAddress(AddressEntity address) {
		Optional<AddressEntity> optional = addressRepository.findById(address.getId());
		AddressEntity unpatchedAddress;

		if (optional.isEmpty()) {
			throw new IllegalStateException(
					"optional is empty and an address entity is required for patching an address");
		} else {
			unpatchedAddress = optional.get();
		}

		if (!Objects.equals(unpatchedAddress.getAddressStreet(), address.getAddressStreet())) {
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

		return addressRepository.save(unpatchedAddress);
	}

	@Override
	public List<AddressEntity> findAddressesByUserId(Long userId) {
		UserEntity user = userEntityService.getUserEntityById(userId);
		List<AddressEntity> addresses = addressRepository.findByUser(user);

		return new ArrayList<>(addresses);
	}

	@Override
	public void deleteAddressById(Long id) {
		addressRepository.deleteById(id);
	}
}

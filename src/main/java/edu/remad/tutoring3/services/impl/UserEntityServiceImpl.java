package edu.remad.tutoring3.services.impl;

import org.springframework.stereotype.Service;

import edu.remad.tutoring3.persistence.models.UserEntity;
import edu.remad.tutoring3.repositories.UserEntityRepository;
import edu.remad.tutoring3.services.UserEntityService;

/**
 * Serves to load, save, get, put, patch, delete a user
 * 
 * @author edu.remad
 * @since 2025
 */
@Service
public class UserEntityServiceImpl implements UserEntityService {

	/** user entity repository to do persistence actions */
	private final UserEntityRepository userEntityRepository;
	
	/**
	 * Constructor
	 * 
	 * @param userEntityRepo UserEntity Repository
	 */
	public UserEntityServiceImpl(UserEntityRepository userEntityRepo) {
		userEntityRepository = userEntityRepo;
	}
	
	@Override
	public UserEntity saveUserEntity(UserEntity user) {
		return userEntityRepository.saveAndFlush(user);
	}

	@Override
	public UserEntity getUserEntityById(String id) {
		return userEntityRepository.getReferenceById(id);
	}

}

package edu.remad.tutoring3.services.impl;

import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
@Transactional
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
		return userEntityRepository.save(user);
	}

	@Override
	public UserEntity getUserEntityBySub(String sub) {
		return userEntityRepository.findBySub(sub);
	}

	@Override
	public UserEntity getUserEntityById(Long id) {
		UserEntity loadedUser = userEntityRepository.findById(id).get();
		
		return loadedUser;
	}
	
	@Override
	public UserEntity getReferencedUserEntityById(Long id) {
		UserEntity loadedUser = userEntityRepository.getReferenceById(id);
		
		return loadedUser;
	}

}

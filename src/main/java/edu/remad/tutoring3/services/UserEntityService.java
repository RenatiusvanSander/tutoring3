package edu.remad.tutoring3.services;

import edu.remad.tutoring3.persistence.models.UserEntity;

/**
 * Defines methods for implementation of this interface
 * 
 * @author edu.remad
 * @since 2025
 */
public interface UserEntityService {
	
	/**
	 * Saves user
	 * 
	 * @param user {@link UserEntity} to save
	 * @return saved user as {@link UserEntity}
	 */
	UserEntity saveUserEntity(UserEntity user);
	
	/**
	 * Gets user by sub
	 * 
	 * @param sub user's id
	 * @return matching user as {@link UserEntity}
	 */
	UserEntity getUserEntityBySub(String sub);
	
	/**
	 * Gets user by id
	 * 
	 * @param id user's id
	 * @return matching user as {@link UserEntity}
	 */
	UserEntity getUserEntityById(Long id);

	/**
	 * Gets user by id reference
	 * 
	 * @param id user's id
	 * @return {@link UserEntity}
	 */
	UserEntity getReferencedUserEntityById(Long id);

}

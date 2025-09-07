package edu.remad.tutoring3.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import edu.remad.tutoring3.model.UserEntity;

/**
 * Repo for users
 * 
 * @author edu.remad
 * @since 2025
 */
@Repository
public interface UserEntityRepository extends JpaRepository<UserEntity, String> {

}

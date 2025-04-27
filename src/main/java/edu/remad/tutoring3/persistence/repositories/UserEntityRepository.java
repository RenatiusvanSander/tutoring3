package edu.remad.tutoring3.persistence.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import edu.remad.tutoring3.persistence.models.UserEntity;

@Repository
public interface UserEntityRepository extends JpaRepository<UserEntity, String> {

	UserEntity findByEmail(String email);

	UserEntity findByUsername(String username);

	UserEntity findFirstByUsername(String username);
}
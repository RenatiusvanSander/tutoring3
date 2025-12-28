package edu.remad.tutoring3.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import edu.remad.tutoring3.persistence.models.InvoiceEntity;

/**
 * Repo for invoices, which stores also PDF
 * 
 * @author edu.remad
 * @since 2025
 */
@Repository
public interface InvoiceEntityRepository extends JpaRepository<InvoiceEntity, Long> {
	
	/**
	 * Finds {@link InvoiceEntity} by field InvoiceFile, which is null.
	 * 
	 * @return a list of {@link InvoiceEntity}s
	 */
	 List<InvoiceEntity> findByInvoiceFileIsNull();
	 
	 /**
	  * Find user' invoices by user's identifier
	  * 
	  * @param userId user's id
	  * @return a list of {@link InvoiceEntity}s
	  */
	 List<InvoiceEntity> findByUserId_UserId(Long userId);

}

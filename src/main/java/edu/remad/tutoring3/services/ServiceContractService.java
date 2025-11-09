package edu.remad.tutoring3.services;

import java.util.List;

import edu.remad.tutoring3.persistence.models.ServiceContractEntity;

/**
 * Service for Service Contracts
 * 
 * @author edu.remad
 * @since 2025
 */
public interface ServiceContractService {

	/**
	 * Creates {@link ServiceContractEntity}
	 * 
	 * @param serviceContract {@link ServiceContractEntity}
	 * @return {@link ServiceContractEntity}
	 */
	ServiceContractEntity createServiceContract(ServiceContractEntity serviceContract);

	/**
	 * Creates several {@link ServiceContractEntity}s
	 * 
	 * @param serviceContracts {@link ServiceContractEntity}
	 * @return list of {@link ServiceContractEntity}
	 */
	List<ServiceContractEntity> createMultipleServiceContract(List<ServiceContractEntity> serviceContracts);

	/**
	 * Renames a {@link ServiceContractEntity}
	 * 
	 * @param serviceContractToRename {@link ServiceContractEntity}
	 * @return {@link ServiceContractEntity}
	 */
	ServiceContractEntity renameServiceContract(ServiceContractEntity serviceContractToRename);

	/**
	 * rRenames several {@link ServiceContractEntity}s
	 * 
	 * @param serviceContractsToRename {@link ServiceContractEntity}
	 * @return list of {@link ServiceContractEntity}
	 */
	List<ServiceContractEntity> renameMultipleServiceContract(List<ServiceContractEntity> serviceContractsToRename);

	/**
	 * Deletes a {@link ServiceContractEntity}
	 * 
	 * @param serviceContractToDelete {@link ServiceContractEntity}
	 * @return {@link ServiceContractEntity}
	 */
	ServiceContractEntity deleteServiceContract(ServiceContractEntity serviceContractToDelete);

	/**
	 * Deletes several {@link ServiceContractEntity}s
	 * 
	 * @param serviceContractsToDelete list of {@link ServiceContractEntity} to
	 *                                 delete
	 * @return list of deleted {@link ServiceContractEntity}
	 */
	List<ServiceContractEntity> deleteMultipleServiceContracts(List<ServiceContractEntity> serviceContractsToDelete);

	/**
	 * Gets a {@link ServiceContractEntity} via id
	 * 
	 * @param id service contract's id
	 * @return {@link ServiceContractEntity}
	 */
	ServiceContractEntity getServiceContract(Long id);

	/**
	 * Gets several {@link ServiceContractEntity}
	 * 
	 * @param Ids list of {@link ServiceContractEntity}'s id
	 * @return list of {@link ServiceContractEntity}
	 */
	List<ServiceContractEntity> getMultipleServiceContracts(List<Long> Ids);

	/**
	 * Gets all {@link ServiceContractEntity}
	 * 
	 * @return list of {@link ServiceContractEntity}
	 */
	List<ServiceContractEntity> getAllServiceContracts();

	/**
	 * Gets a {@link ServiceContractEntity}
	 * 
	 * @param serviceContract {@link ServiceContractEntity}
	 * @return {@link ServiceContractEntity}
	 */
	ServiceContractEntity getServiceContract(ServiceContractEntity serviceContract);

	/**
	 * Gets several {@link ServiceContractEntity}s
	 * 
	 * @param serviceContracts list of {@link ServiceContractEntity}
	 * @return list of {@link ServiceContractEntity}
	 */
	List<ServiceContractEntity> getServiceContracts(List<ServiceContractEntity> serviceContracts);

	/**
	 * Finds {@link ServiceContractEntity} via identifier
	 * 
	 * @param id service contract's id
	 * @return {@link ServiceContractEntity}
	 */
	ServiceContractEntity findServiceContractById(Long id);

	/**
	 * Finds a {@link ServiceContractEntity} via id
	 * 
	 * @param ids list of service contract identifiers
	 * @return list of {@link ServiceContractEntity}
	 */
	List<ServiceContractEntity> findServiceContractsByIds(List<Long> ids);

	/**
	 * Gets referenced service contract
	 * 
	 * @param id service contract's id
	 * @return {@link ServiceContractEntity}
	 */
	ServiceContractEntity getReferencedServiceContractById(long id);

}

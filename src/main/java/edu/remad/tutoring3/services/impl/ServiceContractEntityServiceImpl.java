package edu.remad.tutoring3.services.impl;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.remad.tutoring3.persistence.models.ServiceContractEntity;
import edu.remad.tutoring3.repositories.ServiceContractEntityRepository;
import edu.remad.tutoring3.services.ServiceContractService;

/**
 * Implementation of {@link ServiceContractService}
 * 
 * @author edu.remad
 * @since 2025
 */
@Transactional
@Service
public class ServiceContractEntityServiceImpl implements ServiceContractService {

	/** Repository for service contracts  */
	private final ServiceContractEntityRepository serviceContractRepo;

	/**
	 * Constructor
	 * 
	 * @param repository {@link ServiceContractEntityRepository}
	 */
	public ServiceContractEntityServiceImpl(ServiceContractEntityRepository repository) {
		serviceContractRepo = repository;
	}

	@Override
	public ServiceContractEntity createServiceContract(ServiceContractEntity serviceContract) {
		serviceContract.setServiceContractCreationDate(LocalDateTime.now());
		ServiceContractEntity savedServiceContract = serviceContractRepo.save(serviceContract);

		return savedServiceContract;
	}

	@Override
	public List<ServiceContractEntity> createMultipleServiceContract(List<ServiceContractEntity> serviceContracts) {
		List<ServiceContractEntity> savedServiceContracts = serviceContractRepo.saveAll(serviceContracts);

		return savedServiceContracts;
	}

	@Override
	public ServiceContractEntity renameServiceContract(ServiceContractEntity serviceContractToRename) {
		ServiceContractEntity savedServiceContract = serviceContractRepo.save(serviceContractToRename);

		return savedServiceContract;
	}

	@Override
	public List<ServiceContractEntity> renameMultipleServiceContract(
			List<ServiceContractEntity> serviceContractsToRename) {
		List<ServiceContractEntity> savedServiceContracts = serviceContractRepo.saveAll(serviceContractsToRename);

		return savedServiceContracts;
	}

	@Override
	public ServiceContractEntity deleteServiceContract(ServiceContractEntity serviceContractToDelete) {
		ServiceContractEntity deletedSerserviceContract = serviceContractRepo
				.findById(serviceContractToDelete.getServiceContractNo()).get();
		serviceContractRepo.delete(deletedSerserviceContract);

		return deletedSerserviceContract;
	}

	@Override
	public List<ServiceContractEntity> deleteMultipleServiceContracts(
			List<ServiceContractEntity> serviceContractsToDelete) {
		List<Long> ids = serviceContractsToDelete.stream()
				.mapToLong(serviceContract -> serviceContract.getServiceContractNo()).boxed().toList();
		List<ServiceContractEntity> deletedServiceContracts = serviceContractRepo.findAllById(ids);
		serviceContractRepo.deleteAll(serviceContractsToDelete);

		return deletedServiceContracts;
	}

	@Override
	public ServiceContractEntity getServiceContract(Long id) {
		ServiceContractEntity loadedServiceContract = serviceContractRepo.findById(id).get();

		return loadedServiceContract;
	}

	@Override
	public List<ServiceContractEntity> getMultipleServiceContracts(List<Long> Ids) {
		List<ServiceContractEntity> loadedServiceContracts = serviceContractRepo.findAllById(Ids);

		return loadedServiceContracts;
	}

	@Override
	public List<ServiceContractEntity> getAllServiceContracts() {
		List<ServiceContractEntity> loadedServiceContracts = serviceContractRepo.findAll();

		return loadedServiceContracts;
	}

	@Override
	public ServiceContractEntity getServiceContract(ServiceContractEntity serviceContract) {
		ServiceContractEntity loadedServiceContract = serviceContractRepo
				.findById(serviceContract.getServiceContractNo()).get();

		return loadedServiceContract;
	}

	@Override
	public List<ServiceContractEntity> getServiceContracts(List<ServiceContractEntity> serviceContracts) {
		List<Long> ids = serviceContracts.stream().mapToLong(serviceContract -> serviceContract.getServiceContractNo())
				.boxed().toList();
		List<ServiceContractEntity> loadedServiceContracts = serviceContractRepo.findAllById(ids);

		return loadedServiceContracts;
	}

	@Override
	public ServiceContractEntity findServiceContractById(Long id) {
		ServiceContractEntity foundServiceContract = serviceContractRepo.findById(id).get();

		return foundServiceContract;
	}

	@Override
	public List<ServiceContractEntity> findServiceContractsByIds(List<Long> ids) {
		List<ServiceContractEntity> foundServiceContracts = serviceContractRepo.findAllById(ids);
		
		return foundServiceContracts;
	}

	@Override
	public ServiceContractEntity getReferencedServiceContractById(long id) {
		return serviceContractRepo.getReferenceById(id);
	}

}

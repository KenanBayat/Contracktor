package de.contracktor;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import de.contracktor.model.BillingItem;
import de.contracktor.model.BillingUnit;
import de.contracktor.model.Contract;
import de.contracktor.model.Project;
import de.contracktor.repository.BillingItemRepository;
import de.contracktor.repository.BillingUnitRepository;
import de.contracktor.repository.ContractRepository;
import de.contracktor.repository.ProjectRepository;

@Service
public class DatabaseService {
	
	@Autowired
	private ProjectRepository projectRepo;
	
	@Autowired
	private BillingItemRepository billingItemRepo;
	
	@Autowired
	private ContractRepository contractRepo;
	
	@Autowired
	private BillingUnitRepository billingUnitRepo;
	
	/**
	 * Returns all billingItems of a project
	 * 
	 * @param project the given project
	 * @return all billingItems of the given project
	 */
	public List<BillingItem> getAllBillingItemsOfProject(Project project) {
		if(!projectRepo.existsById(project.getId())) {
			throw new IllegalArgumentException("Project doesnt exists!");
		}
		
		List<BillingItem> billingItems = new ArrayList<BillingItem>();
		
		List<Contract> contracts = contractRepo.findAllByProject(project);
		
		for(Contract contract : contracts) {
			billingItems.addAll(getAllBillingItemsOfContract(contract));
		}		
		return billingItems;
	}

	/**
	 * Returns all billingItems of a contract
	 * 
	 * @param contract the given contract
	 * @return all billingItems of the given contract
	 */
	public List<BillingItem> getAllBillingItemsOfContract(Contract contract) {
		if(!contractRepo.existsById(contract.getId())) {
			throw new IllegalArgumentException("Contract doesnt exists!");
		}
		
		List<BillingItem> billingItems = new ArrayList<BillingItem>();
		
		List<BillingUnit> billingUnits = billingUnitRepo.findAllByContract(contract);
		
		for(BillingUnit billingUnit : billingUnits) {
			for(BillingItem billingItem : billingUnit.getBillingItems()) {
				getBillingItemsOfBillingItem(billingItems, billingItem);
			}
		}		
		return billingItems;
	}
	
	
	/**
	 * Returns the billingItems of a billingItem
	 * 
	 * @param billingItem the given billingItem
	 * @return the billingItems that belong to the given billingItem
	 */
	private void getBillingItemsOfBillingItem(List<BillingItem> billingItems, BillingItem billingItem) {
		billingItems.add(billingItem);
		
		if(billingItem.getBillingItems() != null && !billingItem.getBillingItems().isEmpty()) {
			for(BillingItem billingItemChild : billingItem.getBillingItems()) {
				getBillingItemsOfBillingItem(billingItems, billingItemChild);
			}
		}	
	}
	
	/**
	 * Return all billingItems in the database
	 * 
	 * @return all billingItems in the database
	 */
	public List<BillingItem> getAllBillingItems() {
		return billingItemRepo.findAll();
	}	
	
	
	/**
	 * Finds all projects, which contain the given substring
	 * 
	 * @param search the given substring
	 * @return the projects, that were founded
	 */
	public List<Project> findByProjectNameContains(String search) {
		return projectRepo.findByNameContains(search);
	}
	
	/**
	 * Finds all contracts, which contain the given substring
	 * 
	 * @param search the given substring
	 * @return the contracts, that were founded
	 */
	public List<Contract> findByContractNameContains(String search) {
		return contractRepo.findByNameContains(search);
	}
	
	/**
	 * Finds all the billingItems, which contain the given substring
	 * 
	 * @param search the given substring
	 * @return the billingItems, that were founded
	 */
	public List<BillingItem> findByBillingItemIDContains(String search) {
		return billingItemRepo.findByBillingItemIDContains(search);
	}
}

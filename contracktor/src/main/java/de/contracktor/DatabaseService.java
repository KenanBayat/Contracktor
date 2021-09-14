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
	public List<BillingItem> getAllBillingItemsOfBillingItem(BillingItem billingItem) {
		List<BillingItem> billingItems = new ArrayList<BillingItem>();
		
		getBillingItemsOfBillingItem(billingItems, billingItem);
		
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
	 * Return all projects in the database
	 *
	 * @return all projects in the database
	 */
	public List<Project> getAllProjects() {
		return projectRepo.findAll();
	}

	/**
	 * Return all contracts in the database
	 *
	 * @return all contracts in the database
	 */
	public List<Contract> getAllContracts() {
		return contractRepo.findAll();
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
	
	
	/**
	 * Returns the childs of a billingItem to the depth 1
	 * 
	 * @param billingItem the given billingItem
	 * @return the childs of the given billingItem
	 */
	public List<BillingItem> getChildOfBillingItem(BillingItem billingItem) {
		List<BillingItem> billingItems = new ArrayList<BillingItem>();
		
		for(BillingItem billingItemChild : billingItem.getBillingItems()) {
			billingItems.add(billingItemChild);
		}	
		return billingItems;
	}
	
	/**
	 * Returns the contract, to which the given billingItem belongs
	 * 
	 * @param billingItem the given billingItem
	 * @return the Contract, to which the given billingItem belongs
	 */
	public Contract getContractOfBillingItem(BillingItem billingItem) {
		if(!billingUnitRepo.existsByBillingUnitID(billingItem.getBillingUnit_ID())) {
			throw new IllegalArgumentException("The billingItem has no billingUnit");
		}
		
		BillingUnit billingUnit = billingUnitRepo.findByBillingUnitID(billingItem.getBillingUnit_ID());
		return billingUnit.getContract();
	}
	
	/**
	 * Find a project by the given project id.
	 * 
	 * @return the project that has the given project id
	 */
	public Project getProjectByID(int id) {
		return projectRepo.findById(id).orElse(null);
	}
	
	/**
	 * Find a contract by the given contract id.
	 * 
	 * @return the contract that has the given contract id
	 */
	public Contract getContractByID(int id) {
		return contractRepo.findById(id).get();
	}
	
	/**
	 * Find a billingItem by the given billingItem id.
	 * 
	 * @return the billingItem that has the given billingItem id
	 */
	public BillingItem getBillingItemByID(int id) {
		return billingItemRepo.findById(id).orElse(null);
	}
	
	/**
	 * Find a project by the given project id.
	 * 
	 * @return the project that has the given project id
	 */
	public Project getProjectByProjectID(int id) {
		return projectRepo.findByProjectID(id);
	}
	
	/**
	 * Find a contract by the given contract id.
	 * 
	 * @return the contract that has the given contract id
	 */
	public Contract getContractByContractID(int id) {
		return contractRepo.findByContractID(id);
	}


	/**
	 * Find a billingItem by the given billingItem id.
	 * 
	 * @return the billingItem that has the given billingItem id
	 */
	public BillingItem getBillingItemByBillingItemID(String id) {
		return billingItemRepo.findByBillingItemID(id).orElse(null);
	}

	/**
	 * returns a list of all contracts of a project
	 * @param project: specific Project
	 * @return all contracts that are included in the project
	 */
	public List<Contract> getContractsOfProject(Project project) {
		return contractRepo.findByProject(project);
	}
}

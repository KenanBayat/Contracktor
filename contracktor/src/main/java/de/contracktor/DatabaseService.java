package de.contracktor;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

import javax.security.sasl.AuthenticationException;

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

	@Autowired
	private UserManager userManager;
	
	/**
	 * Returns all billingItems of a project
	 * 
	 * @param project the given project
	 * @return all billingItems of the given project
	 */
	public List<BillingItem> getAllBillingItemsOfProject(Project project) {
		if(!projectRepo.existsById(project.getId())) {
			throw new IllegalArgumentException("Project doesnt exists!");
		} else if (!getOrg(project).equals( userManager.getCurrentOrganisation())
				|| !userManager.hasCurrentUserReadPerm()) {
			throw new IllegalArgumentException("No access to this resource!");
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
		} else if (!getOrg(contract).contains(userManager.getCurrentOrganisation())
				|| !userManager.hasCurrentUserReadPerm()) {
			throw new IllegalArgumentException("No access to this resource!");
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
		if (!getOrg(billingItem).contains(userManager.getCurrentOrganisation())) {
			throw new IllegalArgumentException("No access to this resource!");
		}
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
	 * Returns all billingUnits the user has access to.
	 * @return a list of billingUnits
	 * @throws AuthenticationException
	 */
	public List<BillingUnit> getAllBillingUnits() {
		return billingUnitRepo.findByContractIsIn(getAllContracts());
	}


	/**
	 * Returns all billingItems the user has access to.
	 * @return a list of billingItems
	 * @throws AuthenticationException
	 */
	public List<BillingItem> getAllBillingItems() {
		if(userManager.hasCurrentUserReadPerm()) {
			return billingItemRepo.findAll().stream().filter(new java.util.function.Predicate<BillingItem>() {

				@Override
				public boolean test(BillingItem t) {
					return getOrg(t).contains(userManager.getCurrentOrganisation());
				}
	          
	        }).collect(Collectors.toList());
		}
		return new ArrayList<BillingItem>(); 
	}

	/**
	 * Returns all projects the user has access to.
	 * @return a list of projects
	 * @throws AuthenticationException
	 */
	public List<Project> getAllProjects() {
		if (userManager.hasCurrentUserReadPerm()) {
			return projectRepo.findByOwner_OrganisationNameIgnoreCase(userManager.getCurrentOrganisation());
		} else {
			return new ArrayList<Project>();
		}
	}

	/**
	 * Returns all contracts the user has access to.
	 * @return a list of contracts
	 * @throws AuthenticationException
	 */
	public List<Contract> getAllContracts()  {
		String orgName = userManager.getCurrentOrganisation();
		if(userManager.hasCurrentUserReadPerm()) {
			return contractRepo.findByContractorIgnoreCaseOrConsigneeIgnoreCase(orgName, orgName);
		} else {
			return new ArrayList<Contract>();
		}
	}


	/**
	 * Finds all projects, which contain the given substring
	 * 
	 * @param search the given substring
	 * @return the projects, that were founded
	 */
	public List<Project> findByProjectNameContains(String search) {
		if (!userManager.hasCurrentUserReadPerm()) {
			return new ArrayList<Project>();
		}
		return projectRepo.findByNameContainsIgnoreCaseAndOwner_OrganisationNameIgnoreCase(search, userManager.getCurrentOrganisation());
	}
	
	/**
	 * Finds all contracts, which contain the given substring
	 * 
	 * @param search the given substring
	 * @return the contracts, that were founded
	 */
	public List<Contract> findByContractNameContains(String search) {
		if (!userManager.hasCurrentUserReadPerm()) {
			return new ArrayList<Contract>();
		}
		return contractRepo.findByNameContainsIgnoreCaseAndProject_Owner_OrganisationNameIgnoreCase(search, userManager.getCurrentOrganisation());
	}
	
	/**
	 * Finds all the billingItems, which contain the given substring
	 * 
	 * @param search the given substring
	 * @return the billingItems, that were founded
	 */
	public List<BillingItem> findByBillingItemIDContains(String search) {
		if (!userManager.hasCurrentUserReadPerm()) {
			return new ArrayList<BillingItem>();
		}
		List<BillingItem> billingItems =  billingItemRepo.findByBillingItemIDContains(search);
		for (BillingItem billingItem : billingItems) {
			if (!getOrg(billingItem).contains(userManager.getCurrentOrganisation())) {
				billingItems.remove(billingItem);
			}
		}
		return billingItems;
	}
	
	
	/**
	 * Returns the childs of a billingItem to the depth 1
	 * 
	 * @param billingItem the given billingItem
	 * @return the childs of the given billingItem
	 */
	public List<BillingItem> getChildOfBillingItem(BillingItem billingItem) {
		if (!getOrg(billingItem).contains(userManager.getCurrentOrganisation()) || !userManager.hasCurrentUserReadPerm()) {
			throw new IllegalArgumentException("No access to this resource!");
		}
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
		} else if (!userManager.hasCurrentUserReadPerm()) {
			throw new IllegalArgumentException("No access to this resource!");
		}
		
		Contract contract = billingUnitRepo.findByBillingUnitID(billingItem.getBillingUnit_ID()).getContract();
		if(!getOrg(contract).contains(userManager.getCurrentOrganisation())) {
			throw new IllegalArgumentException("No access to this resource!");
		}
		return contract;
	}
	
	/**
	 * Find a project by the given project id.
	 * 
	 * @return the project that has the given project id
	 */
	public Project getProjectByID(int id) {
		Optional<Project> project = projectRepo.findById(id);
		if (project.isEmpty()) {
			return null;
		} else if (!getOrg(project.get()).equals(userManager.getCurrentOrganisation()) || !userManager.hasCurrentUserReadPerm()) {
			throw new IllegalArgumentException("No access to this resource!");
		}
		return project.get();
	}

	/**
	 * Find a contract by the given contract id.
	 * 
	 * @return the contract that has the given contract id
	 */

	public Contract getContractByID(int id) {
		Optional<Contract> contract = contractRepo.findById(id);
		if (contract.isEmpty()) {
			return null;
		} else if (!getOrg(contract.get()).contains(userManager.getCurrentOrganisation()) ||
				!userManager.hasCurrentUserReadPerm()) {
			throw new IllegalArgumentException("No access to this resource!");
		}
		return contract.get();

	}
	/**
	 * Find a billingItem by the given billingItem id.
	 * 
	 * @return the billingItem that has the given billingItem id
	 */
	public BillingItem getBillingItemByID(int id) {
		Optional<BillingItem> billingItem = billingItemRepo.findById(id);
		if (billingItem.isEmpty()) {
			return null;
		} else if (!getOrg(billingItem.get()).contains(userManager.getCurrentOrganisation()) || !userManager.hasCurrentUserReadPerm()) {
			throw new IllegalArgumentException("No access to this resource!");
		}
		return billingItem.get();
	}
	
	/**
	 * Find a project by the given project id.
	 * 
	 * @return the project that has the given project id
	 */
	public Project getProjectByProjectID(int id) {
		Optional<Project> project = projectRepo.findById(id);
		if (project.isEmpty()) {
			return null;
		} else if (!getOrg(project.get()).equals(userManager.getCurrentOrganisation()) || !userManager.hasCurrentUserReadPerm()) {
			throw new IllegalArgumentException("No access to this resource!");
		}
		return project.get();
	}
	
	/**
	 * Find a contract by the given contract id.
	 * 
	 * @return the contract that has the given contract id
	 */
	public Contract getContractByContractID(int id) {
		Optional<Contract> contract = contractRepo.findById(id);
		if (contract.isEmpty()) {
			return null;
		} else if (!getOrg(contract.get()).contains(userManager.getCurrentOrganisation()) || !userManager.hasCurrentUserReadPerm()) {
			throw new IllegalArgumentException("No access to this resource!");
		}
		return contract.get();
	}


	/**
	 * Find a billingItem by the given billingItem id.
	 * 
	 * @return the billingItem that has the given billingItem id
	 */
	public BillingItem getBillingItemByBillingItemID(String id) {
		Optional<BillingItem> billingItem = billingItemRepo.findByBillingItemID(id);
		if (billingItem.isEmpty()) {
			return null;
		} else if (!getOrg(billingItem.get()).contains(userManager.getCurrentOrganisation()) || !userManager.hasCurrentUserReadPerm()) {
			throw new IllegalArgumentException("No access to this resource!");
		}
		return billingItem.get();
	}

	/**
	 * returns a list of all contracts of a project
	 * @param project: specific Project
	 * @return all contracts that are included in the project
	 */
	public List<Contract> getContractsOfProject(Project project) {
		if (!getOrg(project).equals(userManager.getCurrentOrganisation()) || !userManager.hasCurrentUserReadPerm()) {
			throw new IllegalArgumentException("No access to this resource!");
		}
		return contractRepo.findByProject(project);
	}

	private String getOrg(Project project) {
		return project.getOwner().getOrganisationName();
	}

	private List<String> getOrg(Contract contract) {
		return new ArrayList<>(List.of(contract.getConsignee(),contract.getContractor()));
	}

	private List<String> getOrg(BillingUnit billingUnit) {
		return getOrg(billingUnit.getContract());
	}

	private List<String> getOrg(BillingItem billingItem) {
		return getOrg(billingUnitRepo.findByBillingUnitID(billingItem.getBillingUnit_ID()).getContract());
	}
}

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
	ProjectRepository projectRepo;
	
	@Autowired
	BillingItemRepository billingItemRepo;
	
	@Autowired
	ContractRepository contractRepo;
	
	@Autowired
	BillingUnitRepository billingUnitRepo;
	
	/**
	 * Returns all billingItems of a project
	 * 
	 * @param project the given project
	 * @return all billingItems of the given project
	 */
	public List<BillingItem> getAllBillingItemsOfProject(Project project) {
		List<BillingItem> billingItems = new ArrayList<BillingItem>();
		
		
		
		return billingItems;
	}

	/**
	 * Returns all billingItems of a contract
	 * 
	 * @param contract the given contract
	 * @return all billingItems of the given contract
	 */
	public List<BillingItem> getAllBillingItemsOfContract(Contract contract) {
		List<BillingItem> billingItems = new ArrayList<BillingItem>();
		
		List<BillingUnit> billingUnits = billingUnitRepo.findAllByContract(contract);
		
		for( ) {
			
		}
		
		return billingItems;
		
	}
	
	/**
	 * Return all billingItems in the database
	 * 
	 * @return all billingItems in the database
	 */
	public List<BillingItem> getAllBillingItems() {
		return billingItemRepo.findAll();
	}
}

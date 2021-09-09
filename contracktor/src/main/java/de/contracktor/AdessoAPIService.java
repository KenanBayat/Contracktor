package de.contracktor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import de.contracktor.model.BillingItem;
import de.contracktor.model.Contract;
import de.contracktor.model.Project;
import de.contracktor.repository.BillingItemRepository;
import de.contracktor.repository.BillingUnitCompletionReportRepository;
import de.contracktor.repository.BillingUnitRepository;
import de.contracktor.repository.ContractRepository;
import de.contracktor.repository.OrganisationRepository;
import de.contracktor.repository.ProjectRepository;
import de.contracktor.repository.RoleRepository;
import de.contracktor.repository.StateRepository;
import de.contracktor.repository.StateTransitionRepository;

@Service
public class AdessoAPIService {
	
	@Autowired
	private ProjectRepository projectRepo;
	
	@Autowired
	private ContractRepository contractRepo;
	
	@Autowired
	private BillingItemRepository billingItemRepo;
	
	@Autowired
	private BillingUnitRepository billingUnitRepo;
	
	@Autowired
	private BillingUnitCompletionReportRepository billingUnitCompletionReportRepo;
		
	@Autowired
	private StateRepository stateRepo;
	
	@Autowired
	private StateTransitionRepository stateTransitionRepo;
	
	/**
	 * Saves a project if it is not existing in the repository.
	 * 
	 * @param project the project to be saved
	 */
	public void save(Project project) {
		
		
		if(!projectRepo.existsById(project.getId())) {
			projectRepo.save(project);
		}
	}
	
	/**
	 * Saves a project if it is not existing in the repository.
	 * 
	 * @param project the project to be saved
	 */
	public void save(Contract contract) {
		//Project project = projectRepo.findAllById(contract.getProjectID());
		//contract.setProject(project);
		if(!contractRepo.existsById(contract.getId())) {
			contractRepo.save(contract);
		}
	}
	
	/**
	 * Saves a billingItem if it is not existing in the repository.
	 * 
	 * @param billingItem the billingitem to be saved. 
	 */
	public void save(BillingItem billingItem) {
		
	}
}

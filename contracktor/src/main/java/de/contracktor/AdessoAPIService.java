package de.contracktor;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import de.contracktor.model.BillingItem;
import de.contracktor.model.BillingUnit;
import de.contracktor.model.BillingUnitCompletionReport;
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
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		LocalDate creationDate = LocalDate.parse(project.getCreationDateString, formatter);
		LocalDate completionDate = LocalDate.parse(project.getCompletionDateString, formatter);
		project.setCreationDate(creationDate);
		project.setCompletionDate(completionDate);
		
		if(!projectRepo.existsById(project.getId())) {
			projectRepo.save(project);
		}
	}
	
	/**
	 * Saves a contract if it is not existing in the repository.
	 * 
	 * @param contract the contract to be saved
	 */
	public void save(Contract contract) {
		Status state = stateRepo.findByStateName(contract.getStateName);
		contract.setStatus(state);
		Project project = projectRepo.findById(contract.getProjectId());
		contract.setProject(project);
		if(!contractRepo.existsById(contract.getId())) {
			contractRepo.save(contract);
		}
	}
	
	/**
	 * Saves a billingItem if it is not existing in the repository.
	 * 
	 * @param billingItem the billingItem to be saved. 
	 */
	public void save(BillingItem billingItem) {
		if(stateRepo.existsByStateName(billingItem.getStateName)) {
			State state = stateRepo.findByStateName(billingItem.getStateName);
			billingItem.setStatus(state);
			if(!billingItemRepo.existsById(billingItem.getId())) {
				billingItemRepo.save(billingItem);
			}
		}
	}
	
	
	/**
	 * Saves a billingUnit if it is not existing in the repository.
	 * 
	 * @param billingUnit the billingUnit to be saved. 
	 */
	public void save(BillingUnit billingUnit, int contractID) {
		if(contractRepo.existsById(contractID)) {
			Contract contract = contractRepo.findById(contractID).orElse(null);
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
			LocalDate completionDate = LocalDate.parse(billingUnit.getCompletionDateString, formatter);
			billingUnit.setCompletionDate(completionDate);
			billingUnit.setContract(contract);
			
			if(!billingUnitRepo.existsById(billingUnit.getId())) {
				billingUnitRepo.save(billingUnit);
			}
		}
	}
	
	/**
	 * Save a billingUnitCompletionReport if it is not existing in the repository.
	 * 
	 * @param billingUnitCR the billingUnitCompletionReport to be saved.
	 */
	public void save(BillingUnitCompletionReport billingUnitCR) {
		if(projectRepo.existsById(billingUnitCR.getContractId) && 
				contractRepo.existsById(billingUnitCR.getProjectId)) {
			Contract contract = projectRepo.findById(billingUnitCR.getContractId);
			Project project = contractRepo.findById(billingUnitCR.getProjectId);
			
			billingUnitCR.setContract(contract);
			billingUnitCR.setProject(project);
			if(!billingUnitCompletionReportRepo.existsById(billingUnitCR.getId())) {
				billingUnitCompletionReportRepo.save(billingUnitCR);
			}
		}
	}
}

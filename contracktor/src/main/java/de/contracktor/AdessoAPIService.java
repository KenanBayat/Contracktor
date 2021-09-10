package de.contracktor;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import de.contracktor.model.BillingItem;
import de.contracktor.model.BillingUnit;
import de.contracktor.model.BillingUnitCompletionReport;
import de.contracktor.model.Contract;
import de.contracktor.model.Organisation;
import de.contracktor.model.Project;
import de.contracktor.model.State;
import de.contracktor.repository.AddressRepository;
import de.contracktor.repository.BillingItemRepository;
import de.contracktor.repository.BillingUnitCompletionReportRepository;
import de.contracktor.repository.BillingUnitRepository;
import de.contracktor.repository.ContractRepository;
import de.contracktor.repository.OrganisationRepository;
import de.contracktor.repository.ProjectRepository;
import de.contracktor.repository.StateRepository;

@Service
public class AdessoAPIService {

	@Autowired
	private ProjectRepository projectRepo;
	
	@Autowired
	private OrganisationRepository organisationRepo;

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
	private AddressRepository addressRepo;

	/**
	 * Saves a project if it is not existing in the repository.
	 * 
	 * @param project the project to be saved
	 */
	public void save(Project project) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		LocalDate creationDate = LocalDate.parse(project.getCreationDateString(), formatter);
		LocalDate completionDate = LocalDate.parse(project.getCompletionDateString(), formatter);
		project.setCreationDate(creationDate);
		project.setCompletionDate(completionDate);
		Organisation organisation;
		if(!organisationRepo.existsByOrganisationName(project.getOwnerGroupIdentifier())) {
			organisation = new Organisation(project.getOwnerGroupIdentifier());
			organisationRepo.save(organisation);
		}else {
			organisation = organisationRepo.findByOrganisationName(project.getOwnerGroupIdentifier());
		}
		
		if(!addressRepo.existsById(project.getAddress().getId())) {
			addressRepo.save(project.getAddress());
		} else {
			project.setAddress(addressRepo.findByAddressId(project.getAddress().getAddressId()));
		}
					
		if(!stateRepo.existsByStateName(project.getStatus().getStateName())) {
			stateRepo.save(project.getStatus());
		} else {
			State state = stateRepo.findByStateName(project.getStatus().getStateName());
			project.setStatus(state);
		}
		
		project.setOwner(organisation);
		if (!projectRepo.existsById(project.getId())) {
			projectRepo.save(project);
		}
	}

	/**
	 * Saves a contract if it is not existing in the repository.
	 * 
	 * @param contract the contract to be saved
	 */
	public void save(Contract contract) {
		if (projectRepo.existsById(contract.getProjectId())
				&& stateRepo.existsByStateName(contract.getStatusString())) {
			State state = stateRepo.findByStateName(contract.getStatusString());
			contract.setStatus(state);
			Project project = projectRepo.findById(contract.getProjectId()).orElse(null);
			contract.setProject(project);
			if (!contractRepo.existsById(contract.getId())) {
				contractRepo.save(contract);
			}
		}

	}

	/**
	 * Saves a billingItem if it is not existing in the repository.
	 * 
	 * @param billingItem the billingItem to be saved.
	 */
	public void save(BillingItem billingItem) {
		if (stateRepo.existsByStateName(billingItem.getStatusName())) {
			State state = stateRepo.findByStateName(billingItem.getStatusName());
			billingItem.setStatus(state);
			if (!billingItemRepo.existsById(billingItem.getId())) {
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
		if (contractRepo.existsById(contractID)) {
			Contract contract = contractRepo.findById(contractID).orElse(null);
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
			LocalDate completionDate = LocalDate.parse(billingUnit.getCompletionDateString(), formatter);
			billingUnit.setCompletionDate(completionDate);
			billingUnit.setContract(contract);

			if (!billingUnitRepo.existsById(billingUnit.getId())) {
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
		if (projectRepo.existsById(billingUnitCR.getContractId())
				&& contractRepo.existsById(billingUnitCR.getProjectId())) {
			Project project = projectRepo.findById(billingUnitCR.getContractId()).orElse(null);
			Contract contract = contractRepo.findById(billingUnitCR.getProjectId()).orElse(null);

			billingUnitCR.setContract(contract);
			billingUnitCR.setProject(project);
			if (!billingUnitCompletionReportRepo.existsById(billingUnitCR.getId())) {
				billingUnitCompletionReportRepo.save(billingUnitCR);
			}
		}
	}
	
	/**
	 * Save a billingUnitCompletionReport if it is not existing in the repository.
	 * 
	 * @param billingUnitCR the billingUnitCompletionReport to be saved.
	 */
	public void save(String stateName, String ID) {
		State state = new State(stateName);
		if(!stateRepo.existsByStateName(stateName)) {
			stateRepo.save(state);
		}
		
		if(billingUnitRepo.existsByBillingUnitID(ID)) {
			BillingUnit billingUnit = billingUnitRepo.findByBillingUnitID(ID);
			billingUnit.setStatus(state);
			billingUnitRepo.save(billingUnit);
		}
		
		if(billingItemRepo.existsByBillingItemID(ID)) {
			BillingItem billingItem = billingItemRepo.findByBillingItemID(ID).orElse(null);
			billingItem.setStatus(state);
			billingItemRepo.save(billingItem);
		}
	}
	
}

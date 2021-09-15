package de.contracktor;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import de.contracktor.model.BillingItem;
import de.contracktor.model.BillingUnit;
import de.contracktor.model.BillingUnitCompletionReport;
import de.contracktor.model.Contract;
import de.contracktor.model.DateFormatter;
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

	@Autowired
	private AdessoAPIService adessoAPIService;

	/**
	 * Saves a project if it is not existing in the repository.
	 * 
	 * @param project the project to be saved
	 */
	public void save(Project project) {
		Long creationDate = DateFormatter.stringToLong(project.getCreationDateString());
		Long completionDate = DateFormatter.stringToLong(project.getCompletionDateString());
			
		project.setCreationDate(creationDate);
		project.setCompletionDate(completionDate);
		Organisation organisation;
		if(!organisationRepo.existsByOrganisationName(project.getOwnerGroupIdentifier())) {
			organisation = new Organisation(project.getOwnerGroupIdentifier());
			organisationRepo.save(organisation);
		}else {
			organisation = organisationRepo.findByOrganisationName(project.getOwnerGroupIdentifier());
		}
		
		if(!addressRepo.existsByAddressId(project.getAddress().getAddressId())) {
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
		if (!projectRepo.existsByProjectID(project.getProjectID())) {
			projectRepo.save(project);
		}
	}

	/**
	 * Saves a contract if it is not existing in the repository.
	 * 
	 * @param contract the contract to be saved
	 */
	public void save(Contract contract) {

		if (projectRepo.existsByProjectID(contract.getProjectId())
				&& stateRepo.existsByStateName(contract.getStatus().getStateName())) {
			Organisation organisation;
			if(!organisationRepo.existsByOrganisationName(contract.getContractor())) {
				organisation = new Organisation(contract.getContractor());
				organisationRepo.save(organisation);
			}else {
				organisation = organisationRepo.findByOrganisationName(contract.getContractor());
			}
			if(!organisationRepo.existsByOrganisationName(contract.getConsignee())) {
				organisation = new Organisation(contract.getConsignee());
				organisationRepo.save(organisation);
			}else {
				organisation = organisationRepo.findByOrganisationName(contract.getConsignee());
			}
			State state = stateRepo.findByStateName(contract.getStatus().getStateName());
			contract.setStatus(state);
			Project project = projectRepo.findByProjectID(contract.getProjectId());
			contract.setProject(project);
			if (!contractRepo.existsByContractID(contract.getContractID())) {
				contractRepo.save(contract);
			}
		}

	}

	/**
	 * Saves a billingItem if it is not existing in the repository.
	 * 
	 * @param billingItem the billingItem to be saved.
	 */
	public void save(BillingItem billingItem, String billingUnitID) {

		billingItem.setBillingUnit_ID(billingUnitID);
			
		ArrayList<BillingItem> billingItems = new ArrayList<BillingItem>();
		
		if (billingItem.getBillingItems() != null &&
				!billingItem.getBillingItems().isEmpty()) {
			for (BillingItem billingItem1 : billingItem.getBillingItems()) {
				adessoAPIService.save(billingItem1, billingUnitID);
				billingItems.add(billingItem1);
			}
		}

		if (stateRepo.existsByStateName(billingItem.getStatusName())) {
			State state = stateRepo.findByStateName(billingItem.getStatusName());
			billingItem.setStatus(state);
			billingItem.setBillingItems(billingItems);
			if (!billingItemRepo.existsByBillingItemID(billingItem.getBillingItemID())) {
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

		ArrayList<BillingItem> billingItems = new ArrayList<BillingItem>();
		
		for (BillingItem billingItem : billingUnit.getBillingItems()) {
			adessoAPIService.save(billingItem, billingUnit.getBillingUnitID());
			billingItems.add(billingItem);
		}

		if (contractRepo.existsByContractID(contractID)) {
			Contract contract = contractRepo.findByContractID(contractID);
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
			Long completionDate = DateFormatter.stringToLong(billingUnit.getCompletionDateString());
			billingUnit.setCompletionDate(completionDate);
			billingUnit.setContract(contract);
			billingUnit.setBillingItems(billingItems);
			if (!billingUnitRepo.existsByBillingUnitID(billingUnit.getBillingUnitID())) {
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
			if (!billingUnitCompletionReportRepo.existsByCRID(billingUnitCR.getCRID())) {
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
			billingUnit = billingUnitRepo.save(billingUnit);
		}
		
		if(billingItemRepo.existsByBillingItemID(ID)) {
			BillingItem billingItem = billingItemRepo.findByBillingItemID(ID).orElse(null);
			billingItem.setStatus(state);
			billingItemRepo.save(billingItem);
		}
	}
	
}

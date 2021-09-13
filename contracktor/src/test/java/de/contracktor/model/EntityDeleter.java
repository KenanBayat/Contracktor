package de.contracktor.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import de.contracktor.repository.AddressRepository;
import de.contracktor.repository.BillingItemRepository;
import de.contracktor.repository.BillingUnitCompletionReportRepository;
import de.contracktor.repository.BillingUnitRepository;
import de.contracktor.repository.ContractRepository;
import de.contracktor.repository.OrganisationRepository;
import de.contracktor.repository.PermissionRepository;
import de.contracktor.repository.PictureRepository;
import de.contracktor.repository.ProjectRepository;
import de.contracktor.repository.ReportRepository;
import de.contracktor.repository.RoleRepository;
import de.contracktor.repository.StateRepository;
import de.contracktor.repository.StateTransitionRepository;
import de.contracktor.repository.UserRepository;

@Service
public class EntityDeleter {

	@Autowired 
	UserRepository userRepo;
	
	@Autowired
	StateTransitionRepository stateTransitionRepo;
	
	@Autowired
	StateRepository stateRepo;
	
	@Autowired
	RoleRepository roleRepo;
	
	@Autowired
	ReportRepository reportRepo;
	
	@Autowired
	ProjectRepository projectRepo;
	
	@Autowired
	PictureRepository pictureRepo;
	
	@Autowired
	PermissionRepository pemissionRepo;
	
	@Autowired
	OrganisationRepository organisationRepo;
	
	@Autowired 
	ContractRepository contractRepo;
	
	@Autowired
	BillingUnitCompletionReportRepository billingUnitCompletionReportRepo;
	
	@Autowired
	BillingUnitRepository billingUnitRepo;
	
	@Autowired
	BillingItemRepository billingItemRepo;
	
	@Autowired
	AddressRepository addressRepo;
	
	public void deleteAllEntities() {
		billingUnitCompletionReportRepo.deleteAll();
		
		billingUnitRepo.deleteAll();
		
		billingItemRepo.deleteAll();
		
		contractRepo.deleteAll();
		
		projectRepo.deleteAll();
		
		pictureRepo.deleteAll();
		
		reportRepo.deleteAll();
		
		stateTransitionRepo.deleteAll();
		
		stateRepo.deleteAll();
		
		userRepo.deleteAll();
		
		roleRepo.deleteAll();
		
		pemissionRepo.deleteAll();
		
		organisationRepo.deleteAll();
	}
	
}

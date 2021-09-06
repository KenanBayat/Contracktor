package de.contracktor;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import de.contracktor.model.Organisation;
import de.contracktor.model.Permission;
import de.contracktor.model.Role;
import de.contracktor.model.User;
import de.contracktor.repository.BillingItemRepository;
import de.contracktor.repository.BillingUnitCompletionReportRepository;
import de.contracktor.repository.BillingUnitRepository;
import de.contracktor.repository.ContractRepository;
import de.contracktor.repository.OrganisationRepository;
import de.contracktor.repository.PermissionRepository;
import de.contracktor.repository.ProjectRepository;
import de.contracktor.repository.ReportRepository;
import de.contracktor.repository.RoleRepository;
import de.contracktor.repository.StateRepository;
import de.contracktor.repository.StateTransitionRepository;
import de.contracktor.repository.UserRepository;

@Component
@Service
public class InitDatabaseService {

	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private OrganisationRepository organisationRepo;
	
	@Autowired
	private ProjectRepository projectRepo;
	
	@Autowired
	private ContractRepository contractRepo;
	
	@Autowired
	private PermissionRepository permissionRepo;
	
	@Autowired
	private BillingItemRepository billingItemRepo;
	
	@Autowired
	private BillingUnitRepository billingUnitRepo;
	
	@Autowired
	private BillingUnitCompletionReportRepository billingUnitCompletionReportRepo;
	
	@Autowired
	private ReportRepository reportRepo;
	
	@Autowired
	private RoleRepository roleRepo;
	
	@Autowired
	private StateRepository stateRepo;
	
	@Autowired
	private StateTransitionRepository stateTransitionRepo;
	
	
	private Permission read;
	private Permission write;
	
	public void init() {
		if(userRepo.count() == 0 && 
		   organisationRepo.count() == 0 && 
		   projectRepo.count() == 0 && 
		   contractRepo.count() == 0 && 
		   permissionRepo.count() == 0 && 
		   billingItemRepo.count() == 0 && 
		   billingUnitRepo.count() == 0 &&
		   billingUnitCompletionReportRepo.count() == 0 &&
		   reportRepo.count() == 0 &&
		   roleRepo.count() == 0 &&
		   stateRepo.count() == 0 &&
		   stateTransitionRepo.count() == 0
			) 
		{
			initPermissions();
			initApplicationAdmin();

			
		}
	}
	
	private void initPermissions() {
		read = new Permission("Nur lesen");
		write = new Permission("Lesen und schreiben");
		permissionRepo.save(read);
		permissionRepo.save(write);
	}
	
	private void initApplicationAdmin() {
		Organisation organisation = new Organisation("Mehiko", "mafio", "42", "Mehiko City", "1234", "Columbia");
		organisationRepo.save(organisation);

		Role applicationAdminRole = new Role("Applikations-Admin", write, organisation);
		roleRepo.save(applicationAdminRole);
		
		ArrayList<Role> applicationAdminRoles = new ArrayList<Role>();
		applicationAdminRoles.add(applicationAdminRole);
		
		User applicationAdmin = new User("password", "Pablo", "Cocaine", organisation, true, true, applicationAdminRoles);
		userRepo.save(applicationAdmin);
	}
}

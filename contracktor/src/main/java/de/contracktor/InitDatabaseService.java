package de.contracktor;

import java.util.ArrayList;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import de.contracktor.model.Organisation;
import de.contracktor.model.Permission;
import de.contracktor.model.Role;
import de.contracktor.model.State;
import de.contracktor.model.StateTransition;
import de.contracktor.model.UserAccount;
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
	
	@Autowired
	private UserManager userManager;
		
	private Permission read;
	private Permission write;
	
	private State nostatus;
	private State open;
	private State ok;
	private State deny;
	
	private StateTransition noStatusOpen;
	private StateTransition openOk;
	private StateTransition openDeny;
	
	private UserAccount applicationAdmin;
	private Organisation applicationAdminOrganisation;
	private Role applicationAdminRole;
	private ArrayList<Role> applicationAdminRoles;
	
	
	@PostConstruct
	public void init() {
		
		if(permissionRepo.count() == 0) 
			initPermissions();
		
		if(organisationRepo.count() == 0) 
			initApplicationOrganisation();
		
		if(roleRepo.count() == 0)
			initApplicationRole();
		
		if(userRepo.count()==0)
			initApplicationAdmin();	
		
		if(stateRepo.count() == 0) 
			initStates();
		
		if(stateTransitionRepo.count() == 0) 
			initStateTransitions();
		
		if(projectRepo.count() == 0 && 
		   contractRepo.count() == 0 && 
		   billingItemRepo.count() == 0 && 
		   billingUnitRepo.count() == 0 &&
		   billingUnitCompletionReportRepo.count() == 0 &&
		   reportRepo.count() == 0){}
		
	}
	
	private void initPermissions() {
		read = new Permission("r");
		write = new Permission("w");
		permissionRepo.save(read);
		permissionRepo.save(write);
	}
	
	private void initApplicationOrganisation() {
		applicationAdminOrganisation = new Organisation("Mehiko");
		organisationRepo.save(applicationAdminOrganisation);
	}
	
	private void initApplicationRole() {
		applicationAdminRole = new Role("Applikations-Admin", write, applicationAdminOrganisation);
		roleRepo.save(applicationAdminRole);
	}
	
	private void initApplicationAdmin() {
		applicationAdminRoles = new ArrayList<Role>();
		applicationAdminRoles.add(applicationAdminRole);
		applicationAdmin = new UserAccount("Pablo", "Cocaine", "Pablo", "Coca Cola", applicationAdminOrganisation, true, true, applicationAdminRoles);
		
		userManager.addUser(applicationAdmin);
	}
	
	private void initStates() {
		nostatus = new State("NO_STATUS");
		open = new State("OPEN");
		ok = new State("OK");
	    deny = new State("DENY");
		stateRepo.save(nostatus);
		stateRepo.save(open);
		stateRepo.save(ok);
		stateRepo.save(deny);
	}
	
	private void initStateTransitions() {
		noStatusOpen = new StateTransition(nostatus, open,false,true);
		openOk = new StateTransition(open, ok,true,false);
		openDeny = new StateTransition(open, deny,false,true);
		stateTransitionRepo.save(noStatusOpen);
		stateTransitionRepo.save(openOk);
		stateTransitionRepo.save(openDeny);
	}
}

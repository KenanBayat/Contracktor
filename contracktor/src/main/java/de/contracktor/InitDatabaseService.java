package de.contracktor;

import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import de.contracktor.model.Address;
import de.contracktor.model.Organisation;
import de.contracktor.model.Permission;
import de.contracktor.model.Role;
import de.contracktor.model.State;
import de.contracktor.model.StateTransition;
import de.contracktor.model.UserAccount;
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
	private PictureRepository pictureRepo;
	
	@Autowired
    private PasswordEncoder encoder;
		
	private Permission read;
	private Permission write;
	
	private State nostatus;
	private State open;
	private State ok;
	private State deny;
	
	private StateTransition noStatusOpen;
	private StateTransition openOk;
	private StateTransition openDeny;
	
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
			initStates();
			initStateTransitions();
			
		}
	}
	
	private void initPermissions() {
		read = new Permission("r");
		write = new Permission("w");
		permissionRepo.save(read);
		permissionRepo.save(write);
	}
	
	private void initApplicationAdmin() {
		Organisation organisation = new Organisation("Mehiko");
		organisationRepo.save(organisation);

		Role applicationAdminRole = new Role("Applikations-Admin", write, organisation);
		roleRepo.save(applicationAdminRole);
		
		ArrayList<Role> applicationAdminRoles = new ArrayList<Role>();
		applicationAdminRoles.add(applicationAdminRole);
		
		UserAccount applicationAdmin = new UserAccount("Pablo", encoder.encode("Cocaine"), "Pablo", "Cocaine", organisation, true, true, applicationAdminRoles);
		userRepo.save(applicationAdmin);
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
		noStatusOpen = new StateTransition(nostatus, open);
		openOk = new StateTransition(open, ok);
		openDeny = new StateTransition(open, deny);
		stateTransitionRepo.save(noStatusOpen);
		stateTransitionRepo.save(openOk);
		stateTransitionRepo.save(openDeny);
	}
}

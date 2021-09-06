package de.contracktor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import de.contracktor.model.Permission;
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
	
	
	

	
	public void init() {
		/*
		if(permissionRepository.count() == 0) {
			Permission permission = new Permission("Read-Only");
			permissionRepository.save(permission);
		}*/
	}
}

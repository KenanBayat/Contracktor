package de.contracktor.model;

import java.time.LocalDate;
import java.util.ArrayList;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import de.contracktor.repository.BillingItemRepository;
import de.contracktor.repository.BillingUnitRepository;
import de.contracktor.repository.ContractRepository;
import de.contracktor.repository.OrganisationRepository;
import de.contracktor.repository.PictureRepository;
import de.contracktor.repository.ProjectRepository;
import de.contracktor.repository.StateRepository;

@SpringBootTest
public class TestBillingUnit {

	
	@Autowired
	BillingItemRepository billingItemRepo;
	
	@Autowired
	StateRepository stateRepo;
	
	@Autowired
	BillingUnitRepository billingUnitRepo;
	
	@Autowired
	ContractRepository contractRepo;
	
	@Autowired
	ProjectRepository projectRepo;
	
	@Autowired
	OrganisationRepository organisationRepo;
	
	@Autowired
	PictureRepository pictureRepo;
	
	private final LocalDate creationDate = LocalDate.of(2021, 9, 8);
	private final LocalDate completionDate = LocalDate.of(2022, 12, 12);
	
	BillingItem billingItem1;
	BillingItem billingItem2;
	BillingItem billingItem3;
	ArrayList<BillingItem> billingItems;
	
	State state;
	
	BillingUnit billingUnit;

	Contract contract;
	
	Organisation organisation;
	
	Project project;
	
	Picture picture;
		
	@BeforeEach
	public void init() {
		state = new State("BillingUnitState");
		stateRepo.save(state);
		
		billingItem1 =  new BillingItem("ID_3346_2929_38", "meter", 1000.0, 105.0, 100050.0, 
                "3m5_6h4uXAXvBoFEtks_QE", state, "", new ArrayList<BillingItem>());
		billingItem2 = new BillingItem("ID_3346_2929_39", "meter", 1000.0, 105.0, 100050.0, 
                "3m6_6h4uXAXvBoFEtks_QE", state, "", new ArrayList<BillingItem>());
		billingItems = new ArrayList<BillingItem>();
		billingItems.add(billingItem1);
		billingItems.add(billingItem2);
		billingItem3 = new BillingItem("ID_3346_2929_37", "meter", 1000.0, 105.0, 100050.0, "3m7_6h4uXAXvBoFEtks_QE", state, "", billingItems);
		
		billingItemRepo.save(billingItem1);
		billingItemRepo.save(billingItem2);
		billingItemRepo.save(billingItem3);
		
		organisation = new Organisation("organisation1", "straße", "houseNumber", "city", "12345", "country");
		organisationRepo.save(organisation);
		
		picture = new Picture(null,null);
		
		project = new Project(2, "project", creationDate, completionDate, "street", "42", "hamburg", "187",
	              "de", 100.0, organisation, "hans", state, picture, "");
		projectRepo.save(project);
		
	}
	
	@AfterEach
	public void delete() {
		projectRepo.delete(project);
		
		organisationRepo.delete(organisation);
		
		billingItemRepo.delete(billingItem1);
		billingItemRepo.delete(billingItem2);
		billingItemRepo.delete(billingItem3);
		
		stateRepo.delete(state);
	}
}

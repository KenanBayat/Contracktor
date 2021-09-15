package de.contracktor.model;

import static org.junit.jupiter.api.Assertions.assertThrows;
import java.time.LocalDate;
import java.util.ArrayList;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import de.contracktor.repository.AddressRepository;
import de.contracktor.repository.BillingItemRepository;
import de.contracktor.repository.BillingUnitCompletionReportRepository;
import de.contracktor.repository.BillingUnitRepository;
import de.contracktor.repository.ContractRepository;
import de.contracktor.repository.OrganisationRepository;
import de.contracktor.repository.PictureRepository;
import de.contracktor.repository.ProjectRepository;
import de.contracktor.repository.StateRepository;

@DataJpaTest
@AutoConfigureTestDatabase(replace=Replace.NONE)
@Transactional(propagation = Propagation.NOT_SUPPORTED)
public class TestBillingUnitCompletionReport {

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

	
	private final long creationDate = 12345678;
	private final long completionDate = 12345678;
	
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
	
	BillingUnitCompletionReport billingUnitCR;
	
	@Autowired
	BillingUnitCompletionReportRepository billingUnitCRRepo;
	
	@Autowired
	private AddressRepository addressRepo;
	
	Address address;
	
	@BeforeEach
	public void init() {
		address = new Address(10000, "strasse", "42", "city", "12345", "Land");
		addressRepo.save(address);
		
		state = new State("BillingUnitState");
		stateRepo.save(state);
		
		billingItem1 =  new BillingItem("ID_3346_2929_38", "id", "meter", 1000.0, 105.0, 100050.0, 
                "3m5_6h4uXAXvBoFEtks_QE", state, "", new ArrayList<BillingItem>());
		billingItem2 = new BillingItem("ID_3346_2929_39", "id", "meter", 1000.0, 105.0, 100050.0, 
                "3m6_6h4uXAXvBoFEtks_QE", state, "", new ArrayList<BillingItem>());
		billingItems = new ArrayList<BillingItem>();
		billingItems.add(billingItem1);
		billingItems.add(billingItem2);
		billingItem3 = new BillingItem("ID_3346_2929_37", "id", "meter", 1000.0, 105.0, 100050.0, 
				"3m7_6h4uXAXvBoFEtks_QE", state, "", billingItems);
		
		billingItemRepo.save(billingItem1);
		billingItemRepo.save(billingItem2);
		billingItemRepo.save(billingItem3);
		
		organisation = new Organisation("organisation1");
		organisationRepo.save(organisation);
		
		picture = new Picture(null,null);
		pictureRepo.save(picture);
		
		project = new Project(4000, "project", creationDate, completionDate, address, 
				100.0, organisation, "hans", state, picture, "");
		projectRepo.save(project);
		
		contract = new Contract(3000, project, "contract", "consignee", state, "Contractor", "test");
		contractRepo.save(contract);
	}
	
	@AfterEach
	public void delete() {
		
		contractRepo.delete(contract);
		
		projectRepo.delete(project);
		
		pictureRepo.delete(picture);
		
		organisationRepo.delete(organisation);
		
		billingItemRepo.delete(billingItem3);
		
		stateRepo.delete(state);
		
		addressRepo.delete(address);
	}
	
	@Test
	public void testNullComment() {
		billingUnitCR = new BillingUnitCompletionReport(contract, project, null, "hans",
				                   new ArrayList<BillingUnit>(), new ArrayList<Picture>());
		assertThrows(Exception.class, () -> billingUnitCRRepo.save(billingUnitCR));
	}
	
	@Test
	public void testNullUsername() {
		billingUnitCR = new BillingUnitCompletionReport(contract, project, "", null,
                new ArrayList<BillingUnit>(), new ArrayList<Picture>());
		assertThrows(Exception.class, () -> billingUnitCRRepo.save(billingUnitCR));
	}
	
	@Test
	public void testEmptyUsername() {
		billingUnitCR = new BillingUnitCompletionReport(contract, project, "", "",
                new ArrayList<BillingUnit>(), new ArrayList<Picture>());
		assertThrows(Exception.class, () -> billingUnitCRRepo.save(billingUnitCR));
	}
}

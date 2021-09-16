package de.contracktor.model;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.DirtiesContext;
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
import de.contracktor.repository.ReportRepository;
import de.contracktor.repository.StateRepository;

@DataJpaTest(properties = {
        "spring.datasource.url=jdbc:h2:mem:testdb;MODE=MySQL;DB_CLOSE_DELAY=-1;IGNORECASE=TRUE;DB_CLOSE_ON_EXIT=FALSE;",
        "spring.jpa.hibernate.ddl-auto=create-drop"
})
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
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
	
	@Autowired
	BillingUnitCompletionReportRepository billingUnitCRRepo;
	
	@Autowired
	private AddressRepository addressRepo;
	
	@Autowired
	private ReportRepository reportRepo;

	
	private final long creationDate = 12345678;
	private final long completionDate = 12345678;
	private final long date = 12345678;
	
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
	
	Report report;
	
	
	Address address;
	
	@BeforeEach
	public void init() {
		byte[] image = "Nice picture".getBytes(StandardCharsets.UTF_8);
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
		
		report = new Report(200,billingItem1, organisation, date, "hans", "jio");
		reportRepo.save(report);
		picture = new Picture(1,image,report);
		pictureRepo.save(picture);
		
		project = new Project(4000, "project", creationDate, completionDate, address, 
				100.0, organisation, "hans", state, image, "");
		projectRepo.save(project);
		
		contract = new Contract(3000, project, "contract", "consignee", state, "Contractor", "test");
		contractRepo.save(contract);
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

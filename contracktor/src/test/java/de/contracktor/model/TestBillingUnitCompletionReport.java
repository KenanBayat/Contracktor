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
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
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
	private TestEntityManager em;
	
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
	
	BillingUnitCompletionReport billingUnitCR;
	
	@Autowired
	BillingUnitCompletionReportRepository billingUnitCRRepo;
	
	@Autowired
	private AddressRepository addressRepo;
	
	Address address;
	
	@BeforeEach
	public void init() {
		address = new Address(10000, "straße", "42", "city", "12345", "Land");
		em.persistAndFlush(address);
		
		state = new State("BillingUnitState");
		em.persistAndFlush(state);
		
		billingItem1 =  new BillingItem("ID_3346_2929_38", "id", "meter", 1000.0, 105.0, 100050.0, 
                "3m5_6h4uXAXvBoFEtks_QE", state, "", new ArrayList<BillingItem>());
		billingItem2 = new BillingItem("ID_3346_2929_39", "id", "meter", 1000.0, 105.0, 100050.0, 
                "3m6_6h4uXAXvBoFEtks_QE", state, "", new ArrayList<BillingItem>());
		billingItems = new ArrayList<BillingItem>();
		billingItems.add(billingItem1);
		billingItems.add(billingItem2);
		billingItem3 = new BillingItem("ID_3346_2929_37", "id", "meter", 1000.0, 105.0, 100050.0, 
				"3m7_6h4uXAXvBoFEtks_QE", state, "", billingItems);
		
		em.persistAndFlush(billingItem1);
		em.persistAndFlush(billingItem2);
		em.persistAndFlush(billingItem3);
		
		organisation = new Organisation("organisation1");
		em.persistAndFlush(organisation);
		
		picture = new Picture(null,null);
		em.persistAndFlush(picture);
		
		project = new Project(4000, "project", creationDate, completionDate, address, 
				100.0, organisation, "hans", state, picture, "");
		em.persistAndFlush(project);
		
		contract = new Contract(3000, project, "contract", "consignee", state, "Contractor", "test");
		em.persistAndFlush(contract);
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
		assertThrows(Exception.class, () -> em.persistAndFlush(billingUnitCR));
	}
	
	@Test
	public void testNullUsername() {
		billingUnitCR = new BillingUnitCompletionReport(contract, project, "", null,
                new ArrayList<BillingUnit>(), new ArrayList<Picture>());
		assertThrows(Exception.class, () -> em.persistAndFlush(billingUnitCR));
	}
	
	@Test
	public void testEmptyUsername() {
		billingUnitCR = new BillingUnitCompletionReport(contract, project, "", "",
                new ArrayList<BillingUnit>(), new ArrayList<Picture>());
		assertThrows(Exception.class, () -> em.persistAndFlush(billingUnitCR));
	}
}

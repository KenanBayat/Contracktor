package de.contracktor.model;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.ArrayList;

import org.junit.jupiter.api.AfterEach;
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
@AutoConfigureTestDatabase(replace=Replace.NONE)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@Transactional(propagation = Propagation.NOT_SUPPORTED)
public class TestContract {

	@Autowired
	private ContractRepository contractRepo;
	
	@Autowired
	private ProjectRepository projectRepo;
	
	@Autowired
	private StateRepository stateRepo;
	
	@Autowired
	private OrganisationRepository organisationRepo;
	
	@Autowired
	private PictureRepository pictureRepo;
	
	@Autowired
	private ReportRepository reportRepo;
	
	@Autowired
	private BillingItemRepository billingItemRepo;
	
	@Autowired
	private AddressRepository addressRepo;
	
	private Picture picture;
	
	private Organisation organisation;
	
	private State state;
	
	private Project project;
	
	private Contract contract;
	
	private Report report;
	
	private long creationDate = 22112211;
	private long completionDate = 34567890;
	
	private long date = 20210707;
	
	BillingItem billingItem;
	
	Address address;
	
	@BeforeEach
	public void init() {
		 byte[] image = "Nice picture".getBytes(StandardCharsets.UTF_8);
		address = new Address(2000, "strasse", "42", "city", "12345", "Land");
		addressRepo.save(address);
		state = new State("state");
		stateRepo.save(state);
		billingItem =  new BillingItem("ID_3346_2929_38", "id", "meter", 1000.0, 105.0, 100050.0, 
                "3m5_6h4uXAXvBoFEtks_QE", state, "", new ArrayList<BillingItem>());
		billingItemRepo.save(billingItem);
		organisation = new Organisation("orga");
		organisationRepo.save(organisation);
		report = new Report(200,billingItem, organisation, date, "hans", "jio");
		reportRepo.save(report);
		picture = new Picture(1,image,report);
		pictureRepo.save(picture);
		project = new Project(5000, "project", creationDate, completionDate, address, 
				100.0, organisation, "hans", state, image, "");
		projectRepo.save(project);
	}
	
	@Test
	public void testNullName() {
		// Test null name.
		contract = new Contract(42, project, null, "consignee", state, "contractor", "");
		assertThrows(Exception.class, () -> contractRepo.save(contract));
	}
	
	@Test
	public void testNullConsignee() {
		// Test null consignee.
		contract = new Contract(42, project, "contract", null, state, "contractor", "");
		assertThrows(Exception.class, () -> contractRepo.save(contract));
	}
	
	@Test
	public void testNullContractor() {
		// Test null contractor.
		contract = new Contract(42, project, "contract", "consignee", state, null, "");
		assertThrows(Exception.class, () -> contractRepo.save(contract));
	}
	
	@Test
	public void testNullDescription() {
		// Test null description.
		contract = new Contract(42, project, "contract", "consignee", state, "contractor", null);
		assertThrows(Exception.class, () -> contractRepo.save(contract));
	}
	
	@Test
	public void testEmptyConsignee() {
		// Test empty consignee.
		contract = new Contract(42, project, "contract", "", state, "contractor", "");
		assertThrows(Exception.class, () -> contractRepo.save(contract));
	}
	
	@Test
	public void testEmptyContractor() {
		// Test empty contractor.
		contract = new Contract(42, project, "contract", "consignee", state, "", "");
		assertThrows(Exception.class, () -> contractRepo.save(contract));
	}
}

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
import de.contracktor.repository.OrganisationRepository;
import de.contracktor.repository.PictureRepository;
import de.contracktor.repository.ProjectRepository;
import de.contracktor.repository.ReportRepository;
import de.contracktor.repository.StateRepository;

@DataJpaTest(properties = {
        "spring.datasource.url=jdbc:h2:mem:testdb;MODE=MySQL;DB_CLOSE_DELAY=-1;IGNORECASE=TRUE;DB_CLOSE_ON_EXIT=FALSE;",
        "spring.jpa.hibernate.ddl-auto=create-drop"
})
@Transactional(propagation = Propagation.NOT_SUPPORTED)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@AutoConfigureTestDatabase(replace=Replace.NONE)
public class TestProject {

	@Autowired
	private ProjectRepository projectRepo;
	
	@Autowired
	private StateRepository stateRepo;
	
	@Autowired
	private OrganisationRepository organisationRepo;
	
	@Autowired
	private PictureRepository pictureRepo;
	
	@Autowired
	AddressRepository addressRepo;
	
	@Autowired
	ReportRepository reportRepo;
	
	@Autowired
	BillingItemRepository billingItemRepo;
	
	Address address;
	
	private Picture picture;
	
	private Organisation organisation;
	
	private State state;
	
	private Project project;
	
	private Report report;
	
	private BillingItem billingItem;
	
	private final long creationDate = 22222222;
	private final long completionDate = 33333333;
	
	private final long date = 33333333;
	
	 byte[] image = "Nice picture".getBytes(StandardCharsets.UTF_8);
	
	@BeforeEach
	public void init() {
		address = new Address(200000, "strasse", "houseNumber", "city", "12345", "country");
		addressRepo.save(address);
		state = new State("status");
		stateRepo.save(state);
		organisation = new Organisation("orga");
		organisationRepo.save(organisation);
		billingItem =  new BillingItem("ID_3346_2929_38", "id", "meter", 1000.0, 105.0, 100050.0, 
                "3m5_6h4uXAXvBoFEtks_QE", state, "", new ArrayList<BillingItem>());
		billingItemRepo.save(billingItem);
		report = new Report(200,billingItem, organisation, date, "hans", "jio");
		reportRepo.save(report);
		picture = new Picture(1,image,report);
		pictureRepo.save(picture);
	}
	
	
	@Test
	public void testNullProjectName() {
		// Test null projectName.
		project = new Project(200000, null, creationDate, completionDate, address,
				100.0, organisation, "hans", state, image, "");
		assertThrows(Exception.class, () -> projectRepo.save(project));
	}
	
	@Test
	public void testNullCreationDate() {
		// Test null creationDate.
		project = new Project(200000, "project", null, completionDate, address, 
				100.0, organisation, "hans", state, image, "");
				  assertThrows(Exception.class, () -> projectRepo.save(project));
	}
	
	@Test
	public void testNullCompletionDate() {
		// Test null completionDate.
		project = new Project(200000, "project", creationDate, null, address,
				100.0, organisation, "hans", state, image, "");
						  assertThrows(Exception.class, () -> projectRepo.save(project));
	}
	
	@Test
	public void testNullCreator() {
		// Test null creator.
		project = new Project(2000000, "project", creationDate, completionDate, address, 
				100.0, organisation, null, state, image, "");
						  assertThrows(Exception.class, () -> projectRepo.save(project));
	}
	
	@Test
	public void testNullDescription() {
		// Test null description.
		project = new Project(200000, "project", creationDate, completionDate, address,
				100.0, organisation, "hans", state, image, null);
						  assertThrows(Exception.class, () -> projectRepo.save(project));
	}
	
	@Test
	public void testEmptyName() {
		// Test empty name.
		project = new Project(2000000, "", creationDate, completionDate, address, 
				100.0, organisation, "hans", state, image, "");
						  assertThrows(Exception.class, () -> projectRepo.save(project));
	}
	
	@Test
	public void testEmptyCreator() {
		// Test empty creator.
		project = new Project(2000000, "project", creationDate, completionDate, address,
				100.0, organisation, "", state, image, "");
						  assertThrows(Exception.class, () -> projectRepo.save(project));
	}
}

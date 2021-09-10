package de.contracktor.model;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDate;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import de.contracktor.repository.AddressRepository;
import de.contracktor.repository.ContractRepository;
import de.contracktor.repository.OrganisationRepository;
import de.contracktor.repository.PictureRepository;
import de.contracktor.repository.ProjectRepository;
import de.contracktor.repository.StateRepository;

@SpringBootTest
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
	
	private Picture picture;
	
	private Organisation organisation;
	
	private State state;
	
	private Project project;
	
	private Contract contract;
	
	private LocalDate creationDate;
	private LocalDate completionDate;
	
	@Autowired
	private AddressRepository addressRepo;
	
	Address address;
	
	@BeforeEach
	public void init() {
		address = new Address(2, "straße", "42", "city", "12345", "Land");
		addressRepo.save(address);
		creationDate = LocalDate.of(2022, 12, 12);
		completionDate = LocalDate.of(2022, 12, 12);
		state = new State("state");
		stateRepo.save(state);
		organisation = new Organisation("orga");
		organisationRepo.save(organisation);
		picture = new Picture(null,null);
		pictureRepo.save(picture);
		project = new Project(2, "project", creationDate, completionDate, address, 
				100.0, organisation, "hans", state, picture, "");
		projectRepo.save(project);
	}
	
	@AfterEach
	public void delete() {
		contractRepo.delete(contract);
		projectRepo.delete(project);
		stateRepo.delete(state);
		organisationRepo.delete(organisation);
		pictureRepo.delete(picture);
		addressRepo.delete(address);
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

package de.contracktor.model;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDate;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import de.contracktor.repository.AddressRepository;
import de.contracktor.repository.OrganisationRepository;
import de.contracktor.repository.PictureRepository;
import de.contracktor.repository.ProjectRepository;
import de.contracktor.repository.StateRepository;

@SpringBootTest
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
	
	Address address;
	
	private Picture picture;
	
	private Organisation organisation;
	
	private State state;
	
	private Project project;
	
	private final LocalDate creationDate = LocalDate.of(2021, 9, 8);
	private final LocalDate completionDate = LocalDate.of(2022, 12, 12);
	
	@BeforeEach
	public void init() {
		address = new Address(2, "straße", "houseNumber", "city", "12345", "country");
		addressRepo.save(address);
		state = new State("status");
		stateRepo.save(state);
		organisation = new Organisation("orga");
		organisationRepo.save(organisation);
		picture = new Picture(null,null);
		pictureRepo.save(picture);
	}
	
	@AfterEach
	public void delete() {
		projectRepo.delete(project);
		stateRepo.delete(state);
		organisationRepo.delete(organisation);
		pictureRepo.delete(picture);
	}
	
	@Test
	public void testNullProjectName() {
		// Test null projectName.
		project = new Project(2, null, creationDate, completionDate, address,
				100.0, organisation, "hans", state, picture, "");
		assertThrows(Exception.class, () -> projectRepo.save(project));
	}
	
	@Test
	public void testNullCreationDate() {
		// Test null creationDate.
		project = new Project(2, "project", null, completionDate, address, 
				100.0, organisation, "hans", state, picture, "");
				  assertThrows(Exception.class, () -> projectRepo.save(project));
	}
	
	@Test
	public void testNullCompletionDate() {
		// Test null completionDate.
		project = new Project(2, "project", creationDate, null, address,
				100.0, organisation, "hans", state, picture, "");
						  assertThrows(Exception.class, () -> projectRepo.save(project));
	}
	
	@Test
	public void testNullCreator() {
		// Test null creator.
		project = new Project(2, "project", creationDate, completionDate, address, 
				100.0, organisation, null, state, picture, "");
						  assertThrows(Exception.class, () -> projectRepo.save(project));
	}
	
	@Test
	public void testNullDescription() {
		// Test null description.
		project = new Project(2, "project", creationDate, completionDate, address,
				100.0, organisation, "hans", state, picture, null);
						  assertThrows(Exception.class, () -> projectRepo.save(project));
	}
	
	@Test
	public void testEmptyName() {
		// Test empty name.
		project = new Project(2, "", creationDate, completionDate, address, 
				100.0, organisation, "hans", state, picture, "");
						  assertThrows(Exception.class, () -> projectRepo.save(project));
	}
	
	@Test
	public void testEmptyCreator() {
		// Test empty creator.
		project = new Project(2, "project", creationDate, completionDate, address,
				100.0, organisation, "", state, picture, "");
						  assertThrows(Exception.class, () -> projectRepo.save(project));
	}
}

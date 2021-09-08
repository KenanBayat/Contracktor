package de.contracktor.model;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDate;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

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
	
	private Picture picture;
	
	private Organisation organisation;
	
	private State state;
	
	private Project project;
	
	private final LocalDate creationDate = LocalDate.of(2021, 9, 8);
	private final LocalDate completionDate = LocalDate.of(2022, 12, 12);
	
	@BeforeEach
	public void init() {
		state = new State("status");
		stateRepo.save(state);
		organisation = new Organisation("orga", "straße", "houseNumber", "city", "12345", "blabla");
		organisationRepo.save(organisation);
		picture = new Picture(null);
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
		project = new Project(2, null, creationDate, completionDate, "street", "42", "hamburg", "12345",
				              "de", 100.0, organisation, "hans", state, picture, "");
		assertThrows(Exception.class, () -> projectRepo.save(project));
	}
	
	@Test
	public void testNullCreationDate() {
		// Test null creationDate.
		project = new Project(2, "project", null, completionDate, "street", "42", "hamburg", "12345",
	              "de", 100.0, organisation, "hans", state, picture, "");
				  assertThrows(Exception.class, () -> projectRepo.save(project));
	}
	
	@Test
	public void testNullCompletionDate() {
		// Test null completionDate.
		project = new Project(2, "project", creationDate, null, "street", "42", "hamburg", "12345",
			              "de", 100.0, organisation, "hans", state, picture, "");
						  assertThrows(Exception.class, () -> projectRepo.save(project));
	}
	
	@Test
	public void testNullStreet() {
		// Test null street.
		project = new Project(2, "project", creationDate, completionDate, null, "42", "hamburg", "12345",
			              "de", 100.0, organisation, "hans", state, picture, "");
						  assertThrows(Exception.class, () -> projectRepo.save(project));
	}
	
	@Test
	public void testNullHouseNumber() {
		// Test null street.
		project = new Project(2, "project", creationDate, completionDate, "street", null, "hamburg", "12345",
			              "de", 100.0, organisation, "hans", state, picture, "");
						  assertThrows(Exception.class, () -> projectRepo.save(project));
	}
	
	
	@Test
	public void testNullCity() {
		// Test null city.
		project = new Project(2, "project", creationDate, completionDate, "street", "42", null, "12345",
			              "de", 100.0, organisation, "hans", state, picture, "");
						  assertThrows(Exception.class, () -> projectRepo.save(project));
	}
	
	@Test
	public void testPostCode() {
		// Test null postcode.
		project = new Project(2, "project", creationDate, completionDate, "street", "42", "hamburg", null,
			              "de", 100.0, organisation, "hans", state, picture, "");
						  assertThrows(Exception.class, () -> projectRepo.save(project));
	}
	
	@Test
	public void testNullCountry() {
		// Test null country.
		project = new Project(2, "project", creationDate, completionDate, "street", "42", "hamburg", "12345",
			              null, 100.0, organisation, "hans", state, picture, "");
						  assertThrows(Exception.class, () -> projectRepo.save(project));
	}
	
	@Test
	public void testNullCreator() {
		// Test null creator.
		project = new Project(2, "project", creationDate, completionDate, "street", "42", "hamburg", "12345",
				 		  "de", 100.0, organisation, null, state, picture, "");
						  assertThrows(Exception.class, () -> projectRepo.save(project));
	}
	
	@Test
	public void testNullDescription() {
		// Test null description.
		project = new Project(2, "project", creationDate, completionDate, "street", "42", "hamburg", "12345",
				          "de", 100.0, organisation, "hans", state, picture, null);
						  assertThrows(Exception.class, () -> projectRepo.save(project));
	}
	
	@Test
	public void testEmptyName() {
		// Test empty name.
		project = new Project(2, "", creationDate, completionDate, "street", "42", "hamburg", "12345",
			              "de", 100.0, organisation, "hans", state, picture, "");
						  assertThrows(Exception.class, () -> projectRepo.save(project));
	}
	
	@Test
	public void testEmptyStreet() {
		// Test empty street.
		project = new Project(2, "project", creationDate, completionDate, "", "42", "hamburg", "12345",
			              "de", 100.0, organisation, "hans", state, picture, "");
						  assertThrows(Exception.class, () -> projectRepo.save(project));
	}
	
	@Test
	public void testEmptyHouseNumber() {
		// Test empty house number.
		project = new Project(2, "project", creationDate, completionDate, "street", "", "hamburg", "12345",
			              "de", 100.0, organisation, "hans", state, picture, "");
						  assertThrows(Exception.class, () -> projectRepo.save(project));
	}
	
	@Test
	public void testEmptyCity() {
		// Test empty city.
		project = new Project(2, "project", creationDate, completionDate, "street", "42", "", "12345",
			              "de", 100.0, organisation, "hans", state, picture, "");
						  assertThrows(Exception.class, () -> projectRepo.save(project));
	}
	
	@Test
	public void testEmptyPostCode() {
		// Test empty city.
		project = new Project(2, "project", creationDate, completionDate, "street", "42", "hamburg", "",
			              "de", 100.0, organisation, "hans", state, picture, "");
						  assertThrows(Exception.class, () -> projectRepo.save(project));
	}
	
	@Test
	public void testEmptyCountry() {
		// Test empty country.
		project = new Project(2, "project", creationDate, completionDate, "street", "42", "hamburg", "",
			              "", 100.0, organisation, "hans", state, picture, "");
						  assertThrows(Exception.class, () -> projectRepo.save(project));
	}
	
	@Test
	public void testEmptyCreator() {
		// Test empty creator.
		project = new Project(2, "project", creationDate, completionDate, "street", "42", "hamburg", "",
			              "de", 100.0, organisation, "", state, picture, "");
						  assertThrows(Exception.class, () -> projectRepo.save(project));
	}
}

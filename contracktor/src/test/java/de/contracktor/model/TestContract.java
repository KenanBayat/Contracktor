package de.contracktor.model;

import java.time.LocalDate;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

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
	public void test
}

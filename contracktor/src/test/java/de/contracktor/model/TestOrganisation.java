package de.contracktor.model;

import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;

import de.contracktor.repository.OrganisationRepository;
import de.contracktor.repository.UserRepository;

public class TestOrganisation {

	@Autowired
	private OrganisationRepository organisationRepo;
	
	private Organisation organisation1;
	private Organisation organisation2;
	
	@Autowired
	private UserRepository userRepo;
	
	private User user1;
	
	@BeforeEach
	public void init() {
		user1 =  new User(null, "hans", "peter", organisation, true, true, null);
	}
}

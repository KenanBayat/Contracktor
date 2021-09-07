package de.contracktor.model;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import de.contracktor.repository.OrganisationRepository;
import de.contracktor.repository.UserRepository;

@SpringBootTest
public class TestUser {

	private User user1;
	private User user2;
	
	private Organisation organisation;
	
	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private OrganisationRepository organisationRepo;
	
	@BeforeEach
	private void init() {
		organisation = new Organisation("organisation", "straße", "42", "city", "12345", "Land");
		organisationRepo.save(organisation);
	}
	
	@Test
	public void testNullValues() {
		// Test null password.
		user1 = new User(null, "", "", organisation, true, true, null);	
		assertThrows(Exception.class, () -> userRepo.save(user1));
		
		// Test null forename.
		user1 = new User("", "", "", organisation, true, true, null);
	}
}

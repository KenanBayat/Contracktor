package de.contracktor.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
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
		// Test null username.
		user1 = new User(null, "password", "hans", "peter", organisation, true, true, null);	
		assertThrows(Exception.class, () -> userRepo.save(user1));
		
		// Test null password.
		user1 = new User("username", null, "hans", "peter", organisation, true, true, null);	
		assertThrows(Exception.class, () -> userRepo.save(user1));
		
		// Test null forename.
		user1 = new User("username", "password", null, "peter", organisation, true, true, null);
		assertThrows(Exception.class, () -> userRepo.save(user1));
		
		// Test null surname.
		user1 = new User("username", "password", "hans", null, organisation, true, true, null);
		assertThrows(Exception.class, () -> userRepo.save(user1));
		
		// Test null organisation.
		user1 = new User("username", "password", "hans", "peter", null, true, true, null);
		assertThrows(Exception.class, () -> userRepo.save(user1));
		
		// Test null isAdmin.
		user1 = new User("username", "password", "hans", "peter", organisation, null, true, null);
		assertThrows(Exception.class, () -> userRepo.save(user1));
		
		// Test null isAdmin.
		user1 = new User("username", "password", "hans", "peter", organisation, true, null, null);
		assertThrows(Exception.class, () -> userRepo.save(user1));
	}
	
	@Test
	public void testEmptyValues() {
		//Test empty username.
		user1 = new User("", "password", "hans", "peter", organisation, true, true, null);	
		assertThrows(Exception.class, () -> userRepo.save(user1));
		
		// Test empty password.
		user1 = new User("username", "", "hans", "peter", organisation, true, true, null);	
		assertThrows(Exception.class, () -> userRepo.save(user1));
		
		// Test empty forename.
		user1 = new User("username", "password", "", "peter", organisation, true, true, null);
		assertThrows(Exception.class, () -> userRepo.save(user1));
		
		// Test empty surname.
		user1 = new User("username", "password", "hans", "", organisation, true, true, null);
		assertThrows(Exception.class, () -> userRepo.save(user1));
	}
	
	@Test 
	public void testSaveUser() {
		
		user1 = new User("hansPeter", "password", "hans", "peter", organisation, true, true, null);
		user2 = new User("hansMeier", "betterpassword", "hans", "meier", organisation, true, true, null);
		
		assertEquals(user1.getId(), 0);
		assertEquals(user2.getId(), 0);
		
		user1 = userRepo.save(user1);
		user2 = userRepo.save(user2);
		
		// User should not get same loginID.
		assertNotEquals(user1.getId(), user2.getId());		
	}
}

package de.contracktor.model;

import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import de.contracktor.repository.AddressRepository;
import de.contracktor.repository.OrganisationRepository;
import de.contracktor.repository.UserRepository;

@SpringBootTest
public class TestUser {

	private UserAccount user1;
	private UserAccount user2;
	
	private Organisation organisation;
	
	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private OrganisationRepository organisationRepo;
		
	@BeforeEach
	private void init() {
		organisation = new Organisation("organisation");
		organisationRepo.save(organisation);
	}
	
	@AfterEach
	public void delete() {
		organisationRepo.delete(organisation);
	}
		
	@Test
	public void testNullUsername() {
		// Test null username.
		user1 = new UserAccount(null, "password", "hans", "peter", organisation, true, true, null);	
		assertThrows(Exception.class, () -> userRepo.save(user1));
	}
	
	@Test
	public void testNullPassword() {
		// Test null password.
		user1 = new UserAccount("username", null, "hans", "peter", organisation, true, true, null);	
		assertThrows(Exception.class, () -> userRepo.save(user1));
	}
	
	@Test
	public void testNullForename() {
		// Test null forename.
		user1 = new UserAccount("username", "password", null, "peter", organisation, true, true, null);
		assertThrows(Exception.class, () -> userRepo.save(user1));
	}
	
	@Test
	public void testNullSurname() {
		// Test null surname.
		user1 = new UserAccount("username", "password", "hans", null, organisation, true, true, null);
		assertThrows(Exception.class, () -> userRepo.save(user1));
	}
	
	@Test
	public void testNullOrganisation() {
		// Test null organisation.
		user1 = new UserAccount("username", "password", "hans", "peter", null, true, true, null);
		assertThrows(Exception.class, () -> userRepo.save(user1));
	}
	
	@Test
	public void testNullIsAdmin() {
		// Test null isAdmin.
		user1 = new UserAccount("username", "password", "hans", "peter", organisation, true, null, null);
		assertThrows(Exception.class, () -> userRepo.save(user1));
	}
	
	@Test
	public void testNullIsApplicatonAdmin() {
		// Test null isAdmin.
		user1 = new UserAccount("username", "password", "hans", "peter", organisation, null, true, null);
		assertThrows(Exception.class, () -> userRepo.save(user1));
	}
	
	@Test
	public void testEmptyValues() {
		//Test empty username.
		user1 = new UserAccount("", "password", "hans", "peter", organisation, true, true, null);	
		assertThrows(Exception.class, () -> userRepo.save(user1));
		
		// Test empty password.
		user1 = new UserAccount("username", "", "hans", "peter", organisation, true, true, null);	
		assertThrows(Exception.class, () -> userRepo.save(user1));
		
		// Test empty forename.
		user1 = new UserAccount("username", "password", "", "peter", organisation, true, true, null);
		assertThrows(Exception.class, () -> userRepo.save(user1));
		
		// Test empty surname.
		user1 = new UserAccount("username", "password", "hans", "", organisation, true, true, null);
		assertThrows(Exception.class, () -> userRepo.save(user1));
	}
	
	@Test 
	public void testSaveUser() {		
		user1 = new UserAccount("hansPeter", "password", "hans", "peter", organisation, true, true, null);
		user2 = new UserAccount("hansMeier", "betterpassword", "hans", "meier", organisation, true, true, null);
		
		user1 = userRepo.save(user1);
		user2 = userRepo.save(user2);
		
		// User should not get same loginID.
		assertNotEquals(user1.getId(), user2.getId());	
		
		userRepo.delete(user1);
		userRepo.delete(user2);
	}
}

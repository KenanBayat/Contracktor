package de.contracktor.model;

import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import de.contracktor.repository.OrganisationRepository;
import de.contracktor.repository.UserRepository;

@DataJpaTest
@AutoConfigureTestDatabase(replace=Replace.NONE)
public class TestUserAccount {

	private UserAccount user1;
	private UserAccount user2;
	
	private Organisation organisation;
	
	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private OrganisationRepository organisationRepo;
		
	@Autowired
	private TestEntityManager em;
	
	@BeforeEach
	private void init() {
		organisation = new Organisation("organisation");
		em.persistAndFlush(organisation);
	}
	
	@AfterEach
	public void delete() {
		em.remove(user1);
		em.remove(user2);
		organisationRepo.delete(organisation);
	}
		
	@Test
	public void testNullUsername() {
		// Test null username.
		user1 = new UserAccount(null, "password", "hans", "peter", organisation, true, true, null);	
		assertThrows(Exception.class, () -> em.persistAndFlush(user1));
	}
	
	@Test
	public void testNullPassword() {
		// Test null password.
		user1 = new UserAccount("username", null, "hans", "peter", organisation, true, true, null);	
		assertThrows(Exception.class, () -> em.persistAndFlush(user1));
	}
	
	@Test
	public void testNullForename() {
		// Test null forename.
		user1 = new UserAccount("username", "password", null, "peter", organisation, true, true, null);
		assertThrows(Exception.class, () -> em.persistAndFlush(user1));
	}
	
	@Test
	public void testNullSurname() {
		// Test null surname.
		user1 = new UserAccount("username", "password", "hans", null, organisation, true, true, null);
		assertThrows(Exception.class, () -> em.persistAndFlush(user1));
	}
	
	@Test
	public void testNullOrganisation() {
		// Test null organisation.
		user1 = new UserAccount("username", "password", "hans", "peter", null, true, true, null);
		assertThrows(Exception.class, () -> em.persistAndFlush(user1));
	}
	
	@Test
	public void testNullIsAdmin() {
		// Test null isAdmin.
		user1 = new UserAccount("username", "password", "hans", "peter", organisation, true, null, null);
		assertThrows(Exception.class, () -> em.persistAndFlush(user1));
	}
	
	@Test
	public void testNullIsApplicatonAdmin() {
		// Test null isAdmin.
		user1 = new UserAccount("username", "password", "hans", "peter", organisation, null, true, null);
		assertThrows(Exception.class, () -> em.persistAndFlush(user1));
	}
	
	@Test
	public void testEmptyValues() {
		//Test empty username.
		user1 = new UserAccount("", "password", "hans", "peter", organisation, true, true, null);	
		assertThrows(Exception.class, () -> em.persistAndFlush(user1));
		
		// Test empty password.
		user1 = new UserAccount("username", "", "hans", "peter", organisation, true, true, null);	
		assertThrows(Exception.class, () -> em.persistAndFlush(user1));
		
		// Test empty forename.
		user1 = new UserAccount("username", "password", "", "peter", organisation, true, true, null);
		assertThrows(Exception.class, () -> em.persistAndFlush(user1));
		
		// Test empty surname.
		user1 = new UserAccount("username", "password", "hans", "", organisation, true, true, null);
		assertThrows(Exception.class, () -> em.persistAndFlush(user1));
	}
	
	@Test 
	public void testSaveUser() {		
		user1 = new UserAccount("hansPeter", "password", "hans", "peter", organisation, true, true, null);
		user2 = new UserAccount("hansMeier", "betterpassword", "hans", "meier", organisation, true, true, null);
		
		user1 = em.persistAndFlush(user1);
		user2 = em.persistAndFlush(user2);
		
		// User should not get same loginID.
		assertNotEquals(user1.getId(), user2.getId());	
		
		userRepo.delete(user1);
		userRepo.delete(user2);
	}
}

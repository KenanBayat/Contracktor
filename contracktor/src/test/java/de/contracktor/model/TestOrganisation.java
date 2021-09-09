package de.contracktor.model;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import javax.validation.ConstraintViolationException;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import de.contracktor.repository.AddressRepository;
import de.contracktor.repository.OrganisationRepository;
import de.contracktor.repository.UserRepository;

@SpringBootTest
public class TestOrganisation {

	@Autowired
	private OrganisationRepository organisationRepo;
	
	private Organisation organisation1;
	
	@Autowired
	private UserRepository userRepo;
	
	private UserAccount user1;
				
	@Test
	public void testNullName() {
		// Test null organisationName.
		organisation1 = new Organisation(null);
		assertThrows(Exception.class, () -> organisationRepo.save(organisation1));	
	}
	
	@Test
	public void testEmptyOrganisationName() {
		// Test null organisationName.
		organisation1 = new Organisation("");
		assertThrows(Exception.class, () -> organisationRepo.save(organisation1));		
	}
	
	@Test 
	public void testSaveOrganisation() {
		
		organisation1 = new Organisation("organisation1");
		organisation1 = organisationRepo.save(organisation1);
		
		user1 =  new UserAccount("hansPeter", "password", "hans", "peter", organisation1, true, true, null);
		user1 = userRepo.save(user1);
		int userID = user1.getId();
		
		userRepo.delete(user1);
		assertFalse(userRepo.existsById(userID));
		organisationRepo.delete(organisation1);
	}
	
}

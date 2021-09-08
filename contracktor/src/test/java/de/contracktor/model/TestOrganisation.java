package de.contracktor.model;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import javax.validation.ConstraintViolationException;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import de.contracktor.repository.OrganisationRepository;
import de.contracktor.repository.UserRepository;

@SpringBootTest
public class TestOrganisation {

	@Autowired
	private OrganisationRepository organisationRepo;
	
	private Organisation organisation1;
	
	@Autowired
	private UserRepository userRepo;
	
	private User user1;
		
	
	@Test
	public void testNullName() {
		// Test null organisationName.
		organisation1 = new Organisation(null, "straße", "houseNumber", "city", "12345", "country");
		assertThrows(Exception.class, () -> organisationRepo.save(organisation1));	
	}
	
	@Test
	public void testNullStreet() {
		// Test null street
		organisation1 = new Organisation("organisation1", null, "houseNumber", "city", "12345", "country");
		assertThrows(Exception.class, () -> organisationRepo.save(organisation1));
	}
	
	@Test
	public void testNullHouseNumber() {
		// Test null houseNumber
		organisation1 = new Organisation("organisation1", "straße", null, "city", "12345", "country");
		assertThrows(Exception.class, () -> organisationRepo.save(organisation1));
	}
	
	@Test
	public void testNullCity() {
		// Test null city
		organisation1 = new Organisation("organisation1", "straße", "houseNumber", null, "12345", "country");
		assertThrows(Exception.class, () -> organisationRepo.save(organisation1));
	}
	
	@Test
	public void testNullPostCode() {
		// Test null postcode
		organisation1 = new Organisation("organisation1", "straße", "houseNumber", "city", null, "country");
		assertThrows(Exception.class, () -> organisationRepo.save(organisation1));
	}
	
	public void testNullCountry() {
		// Test null country
		organisation1 = new Organisation("organisation1", "straße", "houseNumber", "city", "12345", null);
		assertThrows(Exception.class, () -> organisationRepo.save(organisation1));
	}
	
	@Test
	public void testEmptyValues() {
		// Test null organisationName.
		organisation1 = new Organisation("", "straße", "houseNumber", "city", "12345", "country");
		assertThrows(Exception.class, () -> organisationRepo.save(organisation1));
		
		// Test null street
		organisation1 = new Organisation("organisation1", "", "houseNumber", "city", "12345", "country");
		assertThrows(Exception.class, () -> organisationRepo.save(organisation1));
				
				
		// Test null houseNumber
		organisation1 = new Organisation("organisation1", "straße", "", "city", "12345", "country");
		assertThrows(Exception.class, () -> organisationRepo.save(organisation1));
				
				
		// Test null city
		organisation1 = new Organisation("organisation1", "straße", "houseNumber", "", "12345", "country");
		assertThrows(Exception.class, () -> organisationRepo.save(organisation1));
				
				
		// Test null postcode
		organisation1 = new Organisation("organisation1", "straße", "houseNumber", "city", "", "country");
		assertThrows(Exception.class, () -> organisationRepo.save(organisation1));
				
						
		// Test null country
		organisation1 = new Organisation("organisation1", "straße", "houseNumber", "city", "12345", "");
		assertThrows(Exception.class, () -> organisationRepo.save(organisation1));		
	}
	
	@Test 
	public void testSaveOrganisation() {
		
		organisation1 = new Organisation("organisation1", "straße", "houseNumber", "city", "12345", "country");
		organisation1 = organisationRepo.save(organisation1);
		
		user1 =  new User("hansPeter", "password", "hans", "peter", organisation1, true, true, null);
		user1 = userRepo.save(user1);
		int userID = user1.getId();
		
		userRepo.delete(user1);
		assertFalse(userRepo.existsById(userID));
		organisationRepo.delete(organisation1);
	}
}

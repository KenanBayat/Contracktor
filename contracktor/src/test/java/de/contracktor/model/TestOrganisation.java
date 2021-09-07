package de.contracktor.model;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
	private Organisation organisation2;
	
	@Autowired
	private UserRepository userRepo;
	
	private User user1;
		
	@Test
	public void testNullValues() {
		// Test null organisationName.
		organisation1 = new Organisation(null, "straße", "houseNumber", "city", "12345", "country");
		assertThrows(Exception.class, () -> organisationRepo.save(organisation1));
		
		// Test null street
		organisation1 = new Organisation("organisation1", null, "houseNumber", "city", "12345", "country");
		assertThrows(Exception.class, () -> organisationRepo.save(organisation1));
		
		
		// Test null houseNumber
		organisation1 = new Organisation("organisation1", "straße", null, "city", "12345", "country");
		assertThrows(Exception.class, () -> organisationRepo.save(organisation1));
		
		
		// Test null city
		organisation1 = new Organisation("organisation1", "straße", "houseNumber", null, "12345", "country");
		assertThrows(Exception.class, () -> organisationRepo.save(organisation1));
		
		
		// Test null postcode
		organisation1 = new Organisation("organisation1", "straße", "houseNumber", "city", null, "country");
		assertThrows(Exception.class, () -> organisationRepo.save(organisation1));
		
				
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
		organisation2 = new Organisation("organisation2", "straße", "houseNumber", "city", "12345", "country");
		organisation1 = organisationRepo.save(organisation1);
		organisation2 = organisationRepo.save(organisation2);
		
		user1 =  new User("password", "hans", "peter", organisation1, true, true, null);
		userRepo.save(user1);
		
		assertTrue(organisation1.getUsers().contains(user1));
	}
}

package de.contracktor.model;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import de.contracktor.repository.AddressRepository;
import de.contracktor.repository.OrganisationRepository;
import de.contracktor.repository.PermissionRepository;
import de.contracktor.repository.RoleRepository;

@SpringBootTest(properties = {
        "spring.datasource.url=jdbc:h2:mem:testdb;MODE=MySQL;DB_CLOSE_DELAY=-1;IGNORECASE=TRUE;DB_CLOSE_ON_EXIT=FALSE;",
        "spring.jpa.hibernate.ddl-auto=create-drop"
})
@AutoConfigureTestDatabase(replace=Replace.NONE)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@Transactional(propagation = Propagation.NOT_SUPPORTED)
public class TestRole {

	@Autowired
	RoleRepository roleRepo;
	
	@Autowired
	PermissionRepository permissionRepo;
	
	@Autowired
	OrganisationRepository organisationRepo;
			
	Role role1;
	
	Permission permission1;
	Permission permission2;
	
	Organisation organisation;
	
	@Autowired
	private AddressRepository addressRepo;
	
	Address address;
	
	@BeforeEach
	public void init() {
		permission1 = new Permission("blabla");
		permission2 = new Permission("bla");
		permissionRepo.save(permission1);
		permissionRepo.save(permission2);
		
		address = new Address(1, "straße", "42", "city", "12345", "Land");
		addressRepo.save(address);
		organisation = new Organisation("organisation1"); 
		organisationRepo.save(organisation);
	}
	
	
	@Test
	public void testNullValue() {
		// Test with null rolename.
		role1 = new Role(null, permission1, organisation);
		assertThrows(Exception.class, () -> roleRepo.save(role1));
		
		// Test with null permission.
		role1 = new Role("role", null, organisation);
		assertThrows(Exception.class, () -> roleRepo.save(role1));
		
		// Test with null organisation.
		role1 = new Role("role", permission1, null);
		assertThrows(Exception.class, () -> roleRepo.save(role1));
	}
	
	@Test
	public void testEmptyValue() {
		// Test with empty rolename.
		role1 = new Role("", permission1, organisation);
	}
	
	@Test 
	public void testSaveRole() {
		role1 = new Role("Bauarbeiter", permission1, organisation);
		roleRepo.save(role1);
		int role1ID = role1.getId();
		assertTrue(roleRepo.existsById(role1ID));
		roleRepo.delete(role1);
		assertFalse(roleRepo.existsById(role1ID));
	}
}

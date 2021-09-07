package de.contracktor.model;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import de.contracktor.repository.OrganisationRepository;
import de.contracktor.repository.PermissionRepository;
import de.contracktor.repository.RoleRepository;

@SpringBootTest
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
	
	@BeforeEach
	public void init() {
		permission1 = new Permission("blabla");
		permission2 = new Permission("bla");
		permissionRepo.save(permission1);
		permissionRepo.save(permission2);
		
		organisation = new Organisation("organisation1", "straße", "houseNumber", "city", "12345", "country"); 
		organisationRepo.save(organisation);
	}
	
	@AfterEach
	public void delete() {
		permissionRepo.delete(permission1);
		permissionRepo.delete(permission2);
		organisationRepo.delete(organisation);
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

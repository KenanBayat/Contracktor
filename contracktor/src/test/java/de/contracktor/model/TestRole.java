package de.contracktor.model;

import static org.junit.jupiter.api.Assertions.assertThrows;

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
	Role role2;
	
	Permission permission1;
	Permission permission2;
	
	Organisation organisation;
	
	/*
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
	public void init() {
		permission1 = new Permission("blabla");
		permission2 = new Permission("bla");
		permissionRepo.save(permission1);
		permissionRepo.save(permission2);
		
		organisation = new Organisation("organisation1", "straße", "houseNumber", "city", "12345", "country"); 
		organisationRepo.save(organisation);
	}
	*/
	
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
}

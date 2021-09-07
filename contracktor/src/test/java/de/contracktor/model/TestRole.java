package de.contracktor.model;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import de.contracktor.repository.PermissionRepository;
import de.contracktor.repository.RoleRepository;

@SpringBootTest
public class TestRole {

	@Autowired
	RoleRepository roleRepo;
	
	@Autowired
	PermissionRepository permissionRepo;
		
	
	Role role1;
	Role role2;
	
	Permission permission1;
	Permission permission2;
	
	@BeforeEach
	public void init() {
		permission1 = new Permission("r");
		permission2 = new Permission("w");
		
		permissionRepo.save(permission1);
		permissionRepo.save(permission2);
	}
	
	@Test
	public void testNullValue() {
		role1 = new Role(null, null, null);
		assertThrows(Exception.class, () -> roleRepo.save(role1));
		
		// Test with null permission
		role1 = new Role(null, null, null);
		assertThrows(Exception.class, () -> roleRepo.save(role1));
		
		// Test with null organisation.
		role1 = new Role("role", permission1, null);
		assertThrows(Exception.class, () -> roleRepo.save(role1));
	}
}

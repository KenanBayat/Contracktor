package de.contracktor.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import de.contracktor.repository.UserRepository;

@SpringBootTest
public class TestUser {

	private User user1;
	private User user2;
	
	@Autowired
	private UserRepository userRepo;
	
	@BeforeEach
	public void init() {
		User user1 = new User("password", "Meier", "Hans", "organisation", true, true, null);
		User user2 = new User("betterpassword", "person", "test", "besteorganisation", false, false, null);
	}
	
	@Test
	public void saveUser() {
		user1 = userRepo.save(user1);
	}
}

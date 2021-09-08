package de.contracktor.model;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import de.contracktor.repository.ProjectRepository;

@SpringBootTest
public class TestProject {

	@Autowired
	ProjectRepository projectRepo;
	
	Project project;
	
	@Test
	public void testNullValues() {
		// Test
	}
}

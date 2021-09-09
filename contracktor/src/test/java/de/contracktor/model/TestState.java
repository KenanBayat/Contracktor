package de.contracktor.model;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import de.contracktor.repository.StateRepository;

@SpringBootTest
public class TestState {

	@Autowired
	StateRepository stateRepo;
	
	State state1;
	State state2;
	
	@Test
	public void testSaveState() {
		state1 = new State("start");
		state2 = new State("start");
		state1 = stateRepo.save(state1);
		
		// Test if state with same name was not added.
		assertThrows(Exception.class, () -> stateRepo.save(state2));
	}
	
	@Test
	public void testDeleteState() {
		state1 = new State("start");
		state2 = new State("end");
		
		state1 = stateRepo.save(state1);
		state2 = stateRepo.save(state2);
		
		stateRepo.deleteById(state1.getId());
		
		// Test if state was deleted.
		assertFalse(stateRepo.existsByStateName(state1.getStateName()));
	}
	
	@Test
	public void testNullValue() {
		state1 = new State(null);
		assertThrows(Exception.class, () -> stateRepo.save(state1));
	}
	
	@Test
	public void testEmptyValue() {
		state1 = new State("");
		assertThrows(Exception.class, () -> stateRepo.save(state1));
	}
}

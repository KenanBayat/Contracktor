package de.contracktor.model;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import de.contracktor.repository.StateRepository;
import de.contracktor.repository.StateTransitionRepository;

@SpringBootTest
public class TestStateTransition {

	@Autowired
	StateRepository stateRepo;
	
	@Autowired
	StateTransitionRepository stateTransitionRepo;
	
	State state1;
	State state2;
	
	StateTransition stateTransition1;
	StateTransition stateTransition2;
	
	@BeforeEach
	public void init() {
		state1 = new State("start");
		state2 = new State("end");
		stateRepo.save(state1);
		stateRepo.save(state2);
	}
	
	@AfterEach
	public void delete() {
		stateTransitionRepo.delete(stateTransition1);
		stateTransitionRepo.delete(stateTransition2);
		stateRepo.delete(state1);
		stateRepo.delete(state2);
	}
	
	@Test
	public void testSaveStateTransition() {	
		stateTransition1 = new StateTransition(state1, state2);
		stateTransition2 = new StateTransition(state1, state2);
		
		stateTransitionRepo.save(stateTransition1);
		//assertThrows(Exception.class, () -> stateTransitionRepo.save(stateTransition2));
		
		stateTransition2 = new StateTransition(state1, state2);
		stateTransitionRepo.save(stateTransition2);
		assertTrue(stateTransitionRepo.existsById(stateTransition1.getId()));
		stateTransitionRepo.delete(stateTransition1);
		assertFalse(stateTransitionRepo.existsById(stateTransition1.getId()));
	}
}

package de.contracktor.model;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import de.contracktor.repository.StateRepository;
import de.contracktor.repository.StateTransitionRepository;

@SpringBootTest
public class StateTransitionTest {

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
	
	@Test
	public void testNullValue() {
		stateTransition1 = new StateTransition(null, null);
		assertThrows(Exception.class, () -> stateTransitionRepo.save(stateTransition1));		
	}
	
	@Test
	public void testSaveStateTransition() {	
		stateTransition1 = new StateTransition(state1, state2);
		stateTransition2 = new StateTransition(state1, state2);
		
		stateTransitionRepo.save(stateTransition1);
		assertThrows(Exception.class, () -> stateTransitionRepo.save(stateTransition2));
		
		stateTransition2 = new StateTransition(state1, state2);
		stateTransitionRepo.save(stateTransition2);
	}
}

package de.contracktor.model;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import de.contracktor.repository.StateRepository;
import de.contracktor.repository.StateTransitionRepository;

@DataJpaTest(properties = {
        "spring.datasource.url=jdbc:h2:mem:testdb;MODE=MySQL;DB_CLOSE_DELAY=-1;IGNORECASE=TRUE;DB_CLOSE_ON_EXIT=FALSE;",
        "spring.jpa.hibernate.ddl-auto=create-drop"
})
@AutoConfigureTestDatabase(replace=Replace.NONE)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@Transactional(propagation = Propagation.NOT_SUPPORTED)
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
	
	
	@Test
	public void testSaveStateTransition() {	
		stateTransition1 = new StateTransition(state1, state2, false, true);
		stateTransition2 = new StateTransition(state1, state2, true, false);
		
		stateTransitionRepo.save(stateTransition1);
		//assertThrows(Exception.class, () -> stateTransitionRepo.save(stateTransition2));
		
		stateTransition2 = new StateTransition(state1, state2, false, true);
		stateTransitionRepo.save(stateTransition2);
		assertTrue(stateTransitionRepo.existsById(stateTransition1.getId()));
		stateTransitionRepo.delete(stateTransition1);
		assertFalse(stateTransitionRepo.existsById(stateTransition1.getId()));
	}
}

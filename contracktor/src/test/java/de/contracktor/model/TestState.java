package de.contracktor.model;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import de.contracktor.repository.StateRepository;

@DataJpaTest
@AutoConfigureTestDatabase(replace=Replace.NONE)
public class TestState {

	@Autowired
	StateRepository stateRepo;
	
	@Autowired
	private TestEntityManager em;
	
	State state1;
	State state2;
	
	@Test
	public void testSaveState() {
		state1 = new State("s");
		state2 = new State("s");
		state1 = em.persistAndFlush(state1);
		
		// Test if state with same name was not added.
		assertThrows(Exception.class, () -> em.persistAndFlush(state2));
		
		state1.setStateName("test");
		state1 = em.persistAndFlush(state1);
		state2 = em.persistAndFlush(state2);
		
		assertTrue(stateRepo.existsById(state1.getId()));
		assertTrue(stateRepo.existsById(state2.getId()));
		
		stateRepo.delete(state1);
	}
	
	@Test
	public void testDeleteState() {
		state1 = new State("testState");
		
		state1 = em.persistAndFlush(state1);
		
		stateRepo.deleteById(state1.getId());
		
		// Test if state was deleted.
		assertFalse(stateRepo.existsByStateName(state1.getStateName()));
	}
	
	@Test
	public void testNullValue() {
		state1 = new State(null);
		assertThrows(Exception.class, () -> em.persistAndFlush(state1));
	}
	
	@Test
	public void testEmptyValue() {
		state1 = new State("");
		assertThrows(Exception.class, () -> em.persistAndFlush(state1));
	}
}

package de.contracktor.repository;

import de.contracktor.model.State;
import org.springframework.data.repository.CrudRepository;

import de.contracktor.model.StateTransition;

import java.util.List;

public interface StateTransitionRepository extends CrudRepository<StateTransition, Integer>{
    List<StateTransition> findAll();
    List<StateTransition> findByStartState(State start);
}

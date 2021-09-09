package de.contracktor.repository;

import org.springframework.data.repository.CrudRepository;

import de.contracktor.model.StateTransition;

public interface StateTransitionRepository extends CrudRepository<StateTransition, Integer>{

}

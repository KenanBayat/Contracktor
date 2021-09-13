package de.contracktor.repository;

import org.springframework.data.repository.CrudRepository;

import de.contracktor.model.StateTransition;

import java.util.List;

public interface StateTransitionRepository extends CrudRepository<StateTransition, Integer>{
    List<StateTransition> findAll();
}

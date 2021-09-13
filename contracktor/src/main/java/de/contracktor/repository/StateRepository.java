package de.contracktor.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import de.contracktor.model.State;

import java.util.List;


@Repository
public interface StateRepository extends CrudRepository<State, Integer> {

	State findByStateName(String stateName);
	
	boolean existsByStateName(String stateName);
	
	void deleteByStateName(String stateName);

	List<State> findAll();
}

package de.contracktor.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import de.contracktor.model.State;


@Repository
public interface StateRepository extends CrudRepository<State, String> {

}

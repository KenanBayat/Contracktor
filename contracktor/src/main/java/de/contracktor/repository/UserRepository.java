package de.contracktor.repository;

import org.springframework.data.repository.CrudRepository;

import de.contracktor.model.User;

public interface UserRepository extends CrudRepository<User, Integer> {

}

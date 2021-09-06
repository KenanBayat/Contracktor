package de.contracktor.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.NoRepositoryBean;

import de.contracktor.model.User;

@NoRepositoryBean
public interface UserRepository extends CrudRepository<User, Integer> {

}

package de.contracktor.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.stereotype.Component;

import de.contracktor.model.User;

@Component
@NoRepositoryBean
public interface UserRepository extends CrudRepository<User, Integer> {

}

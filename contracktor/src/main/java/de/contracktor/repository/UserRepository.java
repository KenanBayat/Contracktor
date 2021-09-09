package de.contracktor.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import de.contracktor.model.UserAccount;

import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<UserAccount, Integer> {
    Optional<UserAccount> findByUsername(String username);

    boolean existsByUsername(String username);

	
}

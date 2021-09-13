package de.contracktor.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import de.contracktor.model.UserAccount;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<UserAccount, Integer> {
    Optional<UserAccount> findByUsername(String username);
    List<UserAccount> findAll();

    boolean existsByUsername(String username);

	
}

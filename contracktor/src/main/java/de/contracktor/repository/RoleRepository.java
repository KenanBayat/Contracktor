package de.contracktor.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import de.contracktor.model.Role;

@Repository
public interface RoleRepository extends CrudRepository<Role, Integer> {

}

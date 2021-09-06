package de.contracktor.repository;

import org.springframework.data.repository.CrudRepository;

import de.contracktor.model.Role;

public interface RoleRepository extends CrudRepository<Role, String> {

}

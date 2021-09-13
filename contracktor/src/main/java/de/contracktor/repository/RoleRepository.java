package de.contracktor.repository;

import de.contracktor.model.Organisation;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import de.contracktor.model.Role;

public interface RoleRepository extends CrudRepository<Role, Integer> {
    List<Role> findAll();
    Role save(Role newRole);
}

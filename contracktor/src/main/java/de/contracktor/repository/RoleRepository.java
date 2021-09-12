package de.contracktor.repository;

import de.contracktor.model.Organisation;
import org.springframework.data.repository.CrudRepository;

import de.contracktor.model.Role;

import java.util.List;

public interface RoleRepository extends CrudRepository<Role, Integer> {
    List<Role> findAll();
    Role save(Role newRole);
}
